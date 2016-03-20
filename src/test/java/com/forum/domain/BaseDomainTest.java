package com.forum.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseDomainTest {

	@Test
	public void testToString() {
		BaseDomain b=new BaseDomain();
		System.out.println(b.toString());
		assert true;
	}

}
