/**
 * 
 */
package com.vasworks.struts;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;

/**
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class StrictParamsInterceptor extends ParametersInterceptor {
	private ArrayList<Pattern> regexps = null;
	private String strutsActionMethod = null;
	private boolean allowSimpleNames = true;

	public StrictParamsInterceptor() {
		log.debug("Creating instance of StrictParamsInterceptor");
	}

	/**
	 * @param allowSimpleNames the allowSimpleNames to set
	 */
	public void setAllowSimpleNames(boolean allowSimpleNames) {
		this.allowSimpleNames = allowSimpleNames;
	}

	@Override
	protected boolean acceptableName(String name) {
		if (regexps == null || regexps.size() == 0)
			return super.acceptableName(name);

		if (this.allowSimpleNames && name.indexOf('.') < 0)
			return super.acceptableName(name);

		for (Pattern pattern : regexps) {
			log.debug("Matching parameter \"" + name + "\" against \"" + pattern.toString() + "\"");
			if (pattern.matcher(name).matches()) {
				log.debug("Parameter \"" + name + "\" matches \"" + pattern.toString() + "\"");
				return true;
			}
		}

		log.warn("Ignored parameter \"" + name + "\" does not match any patterns for action " + this.strutsActionMethod);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#applyInterceptor (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected boolean applyInterceptor(ActionInvocation invocation) {
		this.regexps = null;
		String methodName = invocation.getProxy().getMethod();
		Class<? extends Object> actionobject = invocation.getAction().getClass();
		log.debug("Action class: " + (actionobject == null ? "NULL" : actionobject.getName()));
		Method method = null;
		try {
			method = actionobject.getMethod(methodName);
			log.debug("Method: " + (method == null ? "NULL" : method.getName()));
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		if (method != null) {
			this.strutsActionMethod = actionobject.getName() + "." + method.getName() + "()";
			AllowedParameters annotation = method.getAnnotation(AllowedParameters.class);
			log.debug("Annotation: " + (annotation == null ? "NULL" : "Got it"));

			if (annotation != null) {
				ArrayList<Pattern> newregexps = new ArrayList<Pattern>();
				for (String allowed : annotation.value()) {
					try {
						// force start and end!
						allowed = "^" + allowed + "$";
						log.debug("Compiling parameter name regexp: \"" + allowed + "\"");
						newregexps.add(Pattern.compile(allowed));
					} catch (PatternSyntaxException e) {
						log.error("Could not compile expression: \"" + allowed + "\" of method " + this.strutsActionMethod);
					}
				}
				if (newregexps.size() > 0)
					this.regexps = newregexps;
			}
		}
		return super.applyInterceptor(invocation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#doIntercept (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String doIntercept(ActionInvocation arg0) throws Exception {
		return super.doIntercept(arg0);
	}
}
