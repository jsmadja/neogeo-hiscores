package com.anzymus.neogeo.hiscores.cassandra;

public class NotInsertedException extends Exception {

	private static final long serialVersionUID = -2987570092351879489L;

	public NotInsertedException(String message, Exception cause) {
		super(message, cause);
	}

}
