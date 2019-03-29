/**
 * 
 */
package com.vasworks.util;

import java.util.List;

/**
 * Provides a common way of providing paged resultsets from back-end services to front-end.
 * 
 * In the service, the following snippet can be used:
 * 
 * <pre>
 * PagedResult&lt;MyObject&gt; paged=new PagedResult&lt;MyObject&gt;();
 * paged.setResults(this.entityManager.createQuery(&quot;from MyObject m where m...&quot;)...setFirstResult(startAt).setMaxResults(maxResults).getResultList());
 * paged.setStartAt(startAt);
 * paged.setMaxResults(maxResults);
 * paged.setTotalHits(this.entityManager.createQuery(&quot;count(*) from MyObject m ..&quot;).getSingleResult());
 * ...
 * </pre>
 * 
 * @author developer
 * 
 */
public class PagedResult<T> {
	// / Resulting list containing maximum of <i>maxResults</i> records
	private List<T> results;
	// / Total hits (excluding pagination)
	private long totalHits = 0;
	// / Starting position of results list in persistence store
	private int startAt = 0;
	// / Maximum number of results (a.k.a. page size)
	private int maxResults = 0;

	/**
	 * ... 
	 */
	public PagedResult() {

	}

	/**
	 * Construct with startAt and maxResults prefilled
	 * @param startAt
	 * @param maxResults
	 */
	public PagedResult(int startAt, int maxResults) {
		this.startAt = startAt;
		this.maxResults = maxResults;
	}

	/**
	 * Resulting list containing maximum of <i>maxResults</i> records
	 * 
	 * @see PagedResult#getMaxResults()
	 * @see PagedResult#getPages()
	 * @return the results
	 */
	public List<T> getResults() {
		return this.results;
	}

	/**
	 * @return the totalHits
	 */
	public long getTotalHits() {
		return this.totalHits;
	}

	/**
	 * @return the startAt
	 */
	public int getStartAt() {
		return this.startAt;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return this.maxResults;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}

	/**
	 * @param totalHits the totalHits to set
	 */
	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * Get current page number.
	 * 
	 * @return page number of this PagedResult
	 */
	public int getPage() {
		return 1 + (int) Math.ceil((double) startAt / (double) maxResults);
	}

	/**
	 * Get total number of available pages
	 * 
	 * @return number of all available pages
	 */
	public int getPages() {
		return (int) Math.ceil((double) totalHits / (double) maxResults);
	}

	/**
	 * Get page size
	 * 
	 * @return page size
	 */
	public int getPageSize() {
		return getMaxResults();
	}
}
