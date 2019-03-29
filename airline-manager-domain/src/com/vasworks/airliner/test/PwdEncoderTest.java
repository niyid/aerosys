package com.vasworks.airliner.test;

import org.springframework.security.core.token.Sha512DigestUtils;

public class PwdEncoderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Sha512DigestUtils.shaHex("agidi"));
	}

}
