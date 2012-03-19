package com.anzymus.neogeo.hiscores.common;

public class DirectLinkNotFoundException extends Exception {

	private static final long serialVersionUID = 1816140071477085986L;

	public DirectLinkNotFoundException(String message, Throwable t) {
		super(message, t);
	}

	public DirectLinkNotFoundException(String message) {
		super(message);
	}
}
