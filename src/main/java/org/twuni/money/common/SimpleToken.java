package org.twuni.money.common;

import org.twuni.common.crypto.rsa.PrivateKey;

public class SimpleToken implements Token {

	private String treasury;
	private PrivateKey actionKey;
	private PrivateKey ownerKey;
	private int value;

	public SimpleToken() {
	}

	public SimpleToken( PrivateKey actionKey, PrivateKey ownerKey ) {
		this.actionKey = actionKey;
		this.ownerKey = ownerKey;
	}

	public SimpleToken( String treasury, PrivateKey actionKey, PrivateKey ownerKey, int value ) {
		this( actionKey, ownerKey );
		this.treasury = treasury;
		this.value = value;
	}

	public SimpleToken( Token token ) {
		this( token.getTreasury(), token.getActionKey(), token.getOwnerKey(), token.getValue() );
	}

	@Override
	public PrivateKey getActionKey() {
		return actionKey;
	}

	@Override
	public PrivateKey getOwnerKey() {
		return ownerKey;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getTreasury() {
		return treasury;
	}

	@Override
	public int hashCode() {
		return actionKey == null ? super.hashCode() : actionKey.getPublicKey().serialize().hashCode();
	}

	@Override
	public int compareTo( Token token ) {
		return Integer.valueOf( value ).compareTo( Integer.valueOf( token.getValue() ) );
	}

	@Override
	public boolean equals( Object object ) {

		if( !getClass().isInstance( object ) ) {
			return false;
		}

		Token token = getClass().cast( object );

		return actionKey.equals( token.getActionKey() ) && ownerKey.equals( token.getOwnerKey() );

	}

	public void setActionKey( PrivateKey actionKey ) {
		this.actionKey = actionKey;
	}

	public void setOwnerKey( PrivateKey ownerKey ) {
		this.ownerKey = ownerKey;
	}

	public void setValue( int value ) {
		if( 0 >= value ) {
			throw new IllegalArgumentException( "Value must be greater than zero." );
		}
		this.value = value;
	}

	public void setTreasury( String treasury ) {
		this.treasury = treasury;
	}

}
