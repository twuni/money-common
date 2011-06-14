package org.twuni.money.common;

public interface Bank {

	public Token withdraw( int amount );

	public void deposit( Token token );

	public int getBalance();

}
