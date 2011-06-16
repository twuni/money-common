package org.twuni.money.common;

public class SimpleToken implements Token {

	private String treasury;
	private String id;
	private String secret;
	private int value;

	public SimpleToken() {
	}

	public SimpleToken( String id, String secret ) {
		this.id = id;
		this.secret = secret;
	}

	public SimpleToken( String treasury, String id, String secret, int value ) {
		this.treasury = treasury;
		this.id = id;
		this.secret = secret;
		this.value = value;
	}

	public SimpleToken( Token token ) {
		this( token.getTreasury(), token.getId(), token.getSecret(), token.getValue() );
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public String getSecret() {
		return secret;
	}

	public void setSecret( String secret ) {
		this.secret = secret;
	}

	@Override
	public int getValue() {
		return value;
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

	@Override
	public String getTreasury() {
		return treasury;
	}

	@Override
	public int hashCode() {
		return id == null ? super.hashCode() : id.hashCode();
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

		return id.equals( token.getId() );

	}

}
