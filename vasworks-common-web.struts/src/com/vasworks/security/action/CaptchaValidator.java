/**
 * vasworks-common-web.struts Jan 29, 2010
 */
package com.vasworks.security.action;

import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 
 * @author developer
 */
public interface CaptchaValidator {

	/**
	 * @param captchaService the captchaService to set
	 */
	public abstract void setCaptchaService(ImageCaptchaService captchaService);

	public abstract boolean isCaptchaValid();
}