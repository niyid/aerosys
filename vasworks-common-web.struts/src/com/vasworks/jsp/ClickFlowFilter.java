/**
 * vasworks-common-web.struts Oct 31, 2009
 */
package com.vasworks.jsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author developer
 * 
 */
public class ClickFlowFilter implements Filter {
	/**
	 * Attribute name used for storing ClickFlow data in HTTP Session
	 */
	public static final String CLICKFLOW_SESSION_ATTR = "__CLICKFLOW_DATA";
	/**
	 * Name used for storing click-flow source in HTTP request attributes
	 */
	public static final String CLICKFLOW_REQUEST_ATTR = "__CLICK_FLOW_PARENT";
	public static final String CLICKFLOW_REQUEST_ATTR2 = "__CLICK_FLOW_PARENT2";

	private static final Log LOG = LogFactory.getLog(ClickFlowFilter.class);
	private boolean createSessions = true;

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		LOG.info("Destroying ClickFlow filter");
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rs;

		String uri = request.getRequestURI();

		if (acceptForTracking(uri)) {
			FlowTree rootFlow = setupFlow(request);

			String hostUrl = getHostUrl(request);
			String referer = getReferrer(request, hostUrl);

			String queryString = request.getQueryString();
			if (queryString != null)
				LOG.debug("Query string: " + queryString);

			String fullURI = uri + (queryString == null ? "" : "?" + queryString);

			// find current referer flow
			FlowTree refererFlow = getRefererFlow(request, rootFlow, referer);
			if (refererFlow != null) {
				request.setAttribute(CLICKFLOW_REQUEST_ATTR, refererFlow.URI);
				if (refererFlow.parent!=null)
					request.setAttribute(CLICKFLOW_REQUEST_ATTR2, refererFlow.parent.URI);
			}

			// call next filter
			FlowResponse flowResponse = new FlowResponse(response);
			chain.doFilter(rq, flowResponse);

			/**
			 * If we have rootFlow object, then we can manupulate it. When sessions are not enabled, this will be null
			 */
			if (rootFlow != null) {
				String httpMethod = request.getMethod();

				if (flowResponse.getStatus() == HttpServletResponse.SC_MOVED_TEMPORARILY) {

					// we shall not track such URIs that return SC_MOVED_TEMPORARILY
					LOG.info("Moved temporarily, prunning tree");
					// we will remove all children of referrer
					pruneTree(request, rootFlow, flowResponse.getRedirectLocation());

				} else if (rootFlow != null && httpMethod.equalsIgnoreCase("GET")) {

					LOG.debug("Tracking HTTP status: " + flowResponse.getStatus() + " " + uri);

					// track URI
					trackURI(rootFlow, refererFlow, fullURI);
				}
			}
		} else {
			// call next filter
			chain.doFilter(rq, rs);
		}
	}

	/**
	 * @param request
	 * @param flowTree
	 * @param referer
	 * @return
	 */
	private FlowTree getRefererFlow(HttpServletRequest request, FlowTree flowTree, String referer) {
		FlowTree refererTree = flowTree.find(referer);
		return refererTree;
	}

	/**
	 * @param request
	 * @param flowTree
	 * @param destination
	 */
	private void pruneTree(HttpServletRequest request, FlowTree flowTree, String destination) {
		FlowTree refererTree = flowTree.find(destination);
		if (refererTree == null) {
			LOG.warn("No source flow found, nothing to prune.");
		} else {
			refererTree.prune();
		}
		LOG.info("Pruned ROOT flow:\n" + flowTree);
	}

	private void trackURI(FlowTree rootTree, FlowTree refererTree, String fullURI) {
		if (refererTree == null) {
			LOG.warn("No source flow found, adding to root.");
			rootTree.addSubFlow(fullURI);
		} else {
			LOG.info("Found referer flow: " + refererTree.toStringParents());
			refererTree.addSubFlow(fullURI);
		}
		LOG.info("Updated ROOT flow:\n" + rootTree);
	}

	private FlowTree setupFlow(HttpServletRequest request) {
		FlowTree flowTree = getFlowTree(request);

		// could be null if session is not established and we are not allowed to create sessions
		if (flowTree != null) {
			LOG.info("ROOT flow:\n" + flowTree);
		}

		return flowTree;
	}

	private String getHostUrl(HttpServletRequest request) {
		String hostHeader = request.getLocalName();
		LOG.debug("Local name: " + hostHeader);
		int port = request.getLocalPort();
		LOG.debug("Port: " + port);

		String hostUrl = "http://" + hostHeader + (port == 80 ? "" : ":" + port);
		return hostUrl;
	}

	private String getReferrer(HttpServletRequest request, String hostUrl) {
		String referer = request.getHeader("Referer");
		if (referer == null || !referer.startsWith(hostUrl)) {
			LOG.info("No or external referer");
			referer = null;
		} else {
			referer = referer.substring(hostUrl.length());
			LOG.debug("Referrer: " + referer);
		}
		return referer;
	}

	/**
	 * @param uri
	 * @return
	 */
	private boolean acceptForTracking(String uri) {
		return uri.endsWith(".jspx") || uri.endsWith("/");
	}

	/**
	 * @param request
	 * @return
	 */
	private FlowTree getFlowTree(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		FlowTree flowTree = null;
		if (session == null && this.createSessions) {
			// only triggered if we're allowed to create sessions
			flowTree = new FlowTree();
			session = request.getSession(true);
			session.setAttribute(CLICKFLOW_SESSION_ATTR, flowTree);
		} else {
			// we have existing session
			Object x = session.getAttribute(CLICKFLOW_SESSION_ATTR);

			if (x != null && x instanceof FlowTree)
				flowTree = (FlowTree) x;
			else {
				// if not of correct type, create new and re-assign
				flowTree = new FlowTree();
				session.setAttribute(CLICKFLOW_SESSION_ATTR, flowTree);
			}
		}

		// return sessioned flowtree, or null
		return flowTree;
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig config) throws ServletException {
		LOG.info("ClickFlow filter configuration");
		Enumeration paramNames = config.getInitParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			LOG.debug("ClickFlow parameter: " + paramName + " = " + config.getInitParameter(paramName));
		}
	}

	private class FlowResponse extends ProxiedResponse {

		private int status = SC_OK;
		private Dictionary<String, String> headers = new Hashtable<String, String>();
		private String redirectLocation;

		/**
		 * @param originalResponse
		 */
		public FlowResponse(HttpServletResponse originalResponse) {
			super(originalResponse);
		}

		/**
		 * @return
		 */
		public String getRedirectLocation() {
			return this.redirectLocation;
		}

		/**
		 * Get HTTP response header
		 * 
		 * @param string
		 * @return
		 */
		public String getHeader(String key) {
			return this.headers.get(key);
		}

		/**
		 * Return HTTP response status
		 * 
		 * @return the status
		 */
		public int getStatus() {
			return this.status;
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#addHeader(java.lang.String, java.lang.String)
		 */
		@Override
		public void addHeader(String arg0, String arg1) {
			LOG.debug("Header " + arg0 + ": " + arg1);
			this.headers.put(arg0, arg1);
			super.addHeader(arg0, arg1);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#setHeader(java.lang.String, java.lang.String)
		 */
		@Override
		public void setHeader(String arg0, String arg1) {
			LOG.debug("Header " + arg0 + ": " + arg1);
			this.headers.put(arg0, arg1);
			super.setHeader(arg0, arg1);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#addDateHeader(java.lang.String, long)
		 */
		@Override
		public void addDateHeader(String arg0, long arg1) {
			this.headers.put(arg0, "" + arg1);
			super.addDateHeader(arg0, arg1);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#addIntHeader(java.lang.String, int)
		 */
		@Override
		public void addIntHeader(String arg0, int arg1) {
			this.headers.put(arg0, "" + arg1);
			super.addIntHeader(arg0, arg1);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#setStatus(int)
		 */
		@Override
		public void setStatus(int arg0) {
			this.status = arg0;
			super.setStatus(arg0);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#setStatus(int, java.lang.String)
		 */
		@Override
		public void setStatus(int arg0, String arg1) {
			this.status = arg0;
			super.setStatus(arg0, arg1);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#sendRedirect(java.lang.String)
		 */
		@Override
		public void sendRedirect(String arg0) throws IOException {
			LOG.debug("Redirected to: " + arg0);
			this.redirectLocation = arg0;
			this.status = SC_MOVED_TEMPORARILY;
			super.sendRedirect(arg0);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#sendError(int)
		 */
		@Override
		public void sendError(int arg0) throws IOException {
			LOG.debug("Error: " + arg0);
			this.status = arg0;
			super.sendError(arg0);
		}

		/**
		 * @see com.vasworks.jsp.ProxiedResponse#sendError(int, java.lang.String)
		 */
		@Override
		public void sendError(int arg0, String arg1) throws IOException {
			LOG.debug("Error: " + arg0 + " " + arg1);
			this.status = arg0;
			super.sendError(arg0, arg1);
		}
	}

	private class FlowTree {
		public String URI;
		public List<FlowTree> subFlows;
		public FlowTree parent;

		private FlowTree() {

		}

		/**
		 * Remove all children and references
		 */
		public void prune() {
			if (this.subFlows != null)
				for (FlowTree sub : this.subFlows) {
					sub.prune();
					sub.parent = null;
				}
			this.subFlows = null;
		}

		/**
		 * @return
		 */
		public String toStringParents() {
			return (this.parent == null ? "" : this.parent.toStringParents() + " ") + this.URI;
		}

		/**
		 * @param referer
		 * @return
		 */
		public FlowTree find(String referer) {
			if (this.URI != null && this.URI.equalsIgnoreCase(referer)) {
				return this;
			} else {
				if (this.subFlows != null)
					for (FlowTree sub : this.subFlows) {
						FlowTree res = sub.find(referer);
						if (res != null)
							return res;
					}
			}
			return null;
		}

		/**
		 * @param subURI
		 */
		public FlowTree(String URI) {
			this.URI = URI;
		}

		public FlowTree addSubFlow(String subURI) {
			FlowTree subFlow = new FlowTree(subURI);
			if (this.subFlows == null)
				this.subFlows = new ArrayList<FlowTree>();
			else {
				// if we have it already, don't bother
				FlowTree foundFlow = this.find(subURI);
				if (foundFlow != null)
					return foundFlow;
			}
			subFlow.parent = this;
			this.subFlows.add(subFlow);
			return subFlow;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			printTree(0, sb);
			return sb.toString();
		}

		/**
		 * @param sb
		 */
		private void printTree(int depth, StringBuffer sb) {
			for (int i = depth; i > 0; i--)
				sb.append("    ");
			if (depth == 0 && this.URI == null) {
				if (this.subFlows != null)
					for (FlowTree sub : this.subFlows)
						sub.printTree(0, sb);
			} else {
				sb.append(this.URI == null ? "NULL" : this.URI);
				sb.append("\n");
				if (this.subFlows != null)
					for (FlowTree sub : this.subFlows)
						sub.printTree(depth + 1, sb);
			}
		}
	}
}
