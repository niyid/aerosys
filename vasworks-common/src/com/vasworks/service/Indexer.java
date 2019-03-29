/**
 * 
 */
package com.vasworks.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

/**
 * Lucene reindexer
 * 
 * @author developer
 * 
 */
public class Indexer implements Runnable {

	public enum Status {
		Stopped, Running
	}

	private boolean doIndex = false;
	private Thread thread = null;
	private long currentLot = -1;
	private long totalRecordCount = -1;
	private int batchSize = 100;
	private String indexingTable;
	private EntityManager em;


	public boolean getRunning() {
		return this.getStatus() == Status.Running;
	}

	public synchronized Status getStatus() {
		if (this.thread != null && this.thread.isAlive())
			return Status.Running;
		else {
			this.stop();
			return Status.Stopped;
		}
	}

	public synchronized void start(String tableName, EntityManager entityManager) {
		if (this.getStatus() == Status.Running)
			throw new java.lang.IllegalStateException("Indexer already running on " + this.indexingTable);
		if (tableName == null || tableName.length() == 0)
			throw new NullPointerException("Table name not passed to indexer");

		if (this.thread != null && this.thread.isAlive()) {
			throw new java.lang.IllegalStateException("Indexer already running on " + this.indexingTable);
		} else {
			synchronized (this) {
				if (this.thread != null)
					this.stop();

				this.em = entityManager;
				this.indexingTable = tableName;
				this.thread = new Thread(this);
				this.thread.setPriority(Thread.MIN_PRIORITY);
				this.thread.start();
			}
		}
	}

	public void stop() {
		this.doIndex = false;
		if (this.thread == null)
			return;
		else {
			synchronized (this) {
				try {
					this.thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.thread = null;
				this.doIndex = false;
			}
		}
	}

	/**
	 * Runner!
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		doIndex = true;

		// get number of lots
		// TODO SQL Injection problem!
		this.totalRecordCount = (Long) em.createQuery("select count(*) from " + this.indexingTable + " t").getSingleResult();

		System.out.println("Indexer: " + this.totalRecordCount);
		FullTextEntityManager ftEm = Search.getFullTextEntityManager(em);

		this.currentLot = 0;
		// reindex lots in batches
		for (int i = 0; doIndex && i < this.totalRecordCount; i += this.batchSize) {
			ftEm.getTransaction().begin();
			// TODO SQL Injection problem!
			List items = ftEm.createQuery("from " + this.indexingTable + " t").setMaxResults(this.batchSize).setFirstResult(i).getResultList();

			System.out.println("Indexing records: " + i + " - " + (i + batchSize));

			for (Object lot : items) {
				if (!doIndex)
					break;
				ftEm.index(lot);
				this.currentLot++;
			}
			ftEm.getTransaction().commit();
		}
		ftEm.close();

		this.currentLot = -1;
	}

	/**
	 * @return the totalLotCount
	 */
	public long getTotalLotCount() {
		return this.totalRecordCount;
	}

	/**
	 * @return the currentLot
	 */
	public long getCurrentLot() {
		return this.currentLot;
	}

	/**
	 * @return the indexingTable
	 */
	public String getIndexingTable() {
		return this.indexingTable;
	}
}
