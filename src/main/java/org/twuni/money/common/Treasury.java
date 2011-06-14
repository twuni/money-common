package org.twuni.money.common;

import java.util.Set;

public interface Treasury {

	public Set<Token> split( Token token, int amount );

	public Token merge( Token a, Token b );

	public int getValue( Token token );

}
