package org.twuni.money.common.exception;

import java.util.List;

public class ManyExceptions extends RuntimeException {

	private final List<Exception> exceptions;

	public ManyExceptions( List<Exception> exceptions ) {
		this.exceptions = exceptions;
	}

	@Override
	public String getMessage() {
		return exceptions == null ? "" : exceptions.toString();
	}

	public List<Exception> getExceptions() {
		return exceptions;
	}

}
