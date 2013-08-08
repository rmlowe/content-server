package com.netdimensions.servlet;

import java.io.Serializable;

public class RelayState implements Serializable {
	private static final long serialVersionUID = 1L;
	public final String nonce;
	public final String target;

	public RelayState(String nonce, String target) {
		this.nonce = nonce;
		this.target = target;
	}
}
