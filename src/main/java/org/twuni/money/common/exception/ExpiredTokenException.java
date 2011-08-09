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
		return String.format( "A ¤%s token issued by %s has expired.", Integer.valueOf( token.getValue() ), token.getTreasury() );
	}

}
