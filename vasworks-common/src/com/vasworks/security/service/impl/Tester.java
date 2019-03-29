package com.vasworks.security.service.impl;

import org.springframework.security.core.token.Sha512DigestUtils;

public class Tester {

	public static void main(String[] args) {
		System.out.println(Sha512DigestUtils.shaHex("password"));
	}

}
