package org.twuni.money.common.exception;

import java.io.IOException;

public class NetworkException extends RuntimeException {

	public NetworkException( IOException exception ) {
		super( exception );
	}

	@Override
	public String getMessage() {
		return String.format( "[%s] %s", getCause().getClass().getSimpleName(), getCause().getMessage() );
	}

}
