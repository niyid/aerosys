/**
 * 
 */
package com.vasworks.struts.action.admin;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.vasworks.service.Indexer;

/**
 * Lucene reindexing action
 * 
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class ReindexAction extends ActionSupport {
	private String tableName = null;

	/** Indexer */
	private Indexer indexer = null;

	/**
	 * Indexer must be passed as constructor argument. There will be only one instance of Indexer per application.
	 * 
	 * @param indexer
	 */
	public ReindexAction(Indexer indexer) {
		this.indexer = indexer;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String execute() {
		return Action.SUCCESS;
	}

	public String reindex() {
		if (this.indexer == null) {
			this.addActionError("No indexer in this application");
			return Action.ERROR;
		}

		if (!this.indexer.getRunning()) {
			this.indexer.start(this.tableName, com.vasworks.struts.PersistenceUtil.getEntityManager());
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
		}

		return "redirect";
	}

	public String stop() {
		if (this.indexer == null) {
			this.addActionError("No indexer in this application");
			return Action.ERROR;
		}

		if (this.indexer.getRunning()) {
			this.indexer.stop();
		}

		return "redirect";
	}
}
