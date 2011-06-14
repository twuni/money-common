package org.twuni.money.common.exception;

public class InsufficientFundsException extends RuntimeException {

	public InsufficientFundsException() {
		super( "Insufficient funds available to complete this transaction." );
	}

}
