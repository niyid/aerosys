package com.vasworks.struts.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.vasworks.struts.BaseAction;

/**
 * Action to display JRE status on memory usage and threads.
 * 
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class JavaStatusAction extends BaseAction {
	private static final Log LOG = LogFactory.getLog(JavaStatusAction.class);
	private List<? extends Thread> threads;

	public int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	public long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	public long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	public long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Action method to run Garbage Collector
	 * 
	 * @return
	 */
	public String gc() {
		LOG.debug("Running Garbage Collector");
		Runtime.getRuntime().gc();
		LOG.debug("Done running Garbage collector");
		return "refresh";
	}

	public List<? extends Thread> getThreads() {
		return this.threads;
	}

	public static List<? extends Thread> visit(ThreadGroup group, int level) {
		List<Thread> threadlist = null;
		// Get threads in `group'
		int numThreads = group.activeCount();
		Thread[] threads = new Thread[numThreads * 2];
		numThreads = group.enumerate(threads, false);
		// Enumerate each thread in `group'
		for (int i = 0; i < numThreads; i++) {
			// Get thread
			Thread thread = threads[i];
			if (threadlist == null)
				threadlist = new ArrayList<Thread>();
			threadlist.add(thread);
		}

		// Get thread subgroups of `group'
		int numGroups = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[numGroups * 2];
		numGroups = group.enumerate(groups, false);
		// Recursively visit each subgroup
		for (int i = 0; i < numGroups; i++) {
			if (threadlist == null)
				threadlist = new ArrayList<Thread>();
			List<? extends Thread> sub = visit(groups[i], level + 1);
			if (sub != null)
				threadlist.addAll(sub);
		}

		return threadlist;
	}

	@Override
	public String execute() {
		// Find the root thread group
		ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
		while (root.getParent() != null) {
			root = root.getParent();
		}
		// Visit each thread group
		this.threads = visit(root, 0);
		return Action.SUCCESS;
	}
}
