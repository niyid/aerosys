/**
 * notifications.Struts Jan 30, 2010
 */
package com.vasworks.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Daemon service will run an additional thread and process business method {@link #businessMethod()}
 * 
 * @author developer
 */
public abstract class DaemonService implements Runnable, ApplicationListener {
	private static final Log LOG = LogFactory.getLog(DaemonService.class);

	protected boolean running = true;
	protected Thread thread;

	/**
	 * 
	 */
	public DaemonService() {
		super();
	}

	protected void start(String threadName) {
		this.thread = new Thread(this, threadName);
		this.thread.start();
	}

	/**
	 * Thread stop conditions
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		LOG.info("Application event " + event + " received in DaemonService.");
		if (event instanceof org.springframework.context.event.ContextRefreshedEvent) {

		}

		if (event instanceof org.springframework.context.event.ContextClosedEvent) {
			LOG.warn("ContextClosed. Stopping daemon thread.");
			this.stop();
		}
	}

	protected synchronized void stop() {
		this.running = false;
		notifyAll();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (this.running) {
			businessMethod();
			try {
				LOG.debug(Thread.currentThread().getName() + " sleeping...");
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
				LOG.debug(Thread.currentThread().getName() + " interrupted!");
			}
		}
		LOG.info(Thread.currentThread().getName() + " quitting.");
	}

	/**
	 * Business method should process the queued tasks and return as soon as there are no other tasks to do
	 */
	protected abstract void businessMethod();
}