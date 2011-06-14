package org.twuni.money.common.exception;

import org.twuni.money.common.Token;

public class ExpiredTokenException extends RuntimeException {

	private final Token token;

	public ExpiredTokenException( Token token ) {
		this.token = token;
	}

	public Token getToken() {
		return token;
	}

	@Override
	public String getMessage() {
		return String.format( "The $%.2f token %s issued by %s has expired.", Double.valueOf( token.getValue() / 100.0 ), token.getId(), token.getTreasury() );
	}

}
