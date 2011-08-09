package org.twuni.money.common.exception;

import java.util.List;

public class ManyExceptions extends RuntimeException {

	private final List<Exception> exceptions;

	public ManyExceptions( List<Exception> exceptions ) {
		this.exceptions = exceptions;
	}

	@Override
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		for( Exception exception : exceptions ) {
			message.append( String.format( "[%s] %s ", exception.getClass().getSimpleName(), exception.getMessage() ) );
		}
		return message.toString().trim();
	}

	public List<Exception> getExceptions() {
		return exceptions;
	}

}
