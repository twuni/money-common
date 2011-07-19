package org.twuni.money.common;

public class ShareableToken {

	private String actionKey;
	private String ownerKey;
	private String treasury;
	private int value;

	public ShareableToken() {
	}

	public ShareableToken( Token token ) {
		this.actionKey = token.getActionKey().serialize();
		this.ownerKey = token.getOwnerKey().serialize();
		this.treasury = token.getTreasury();
		this.value = token.getValue();
	}

	public String getActionKey() {
		return actionKey;
	}

	public void setActionKey( String actionKey ) {
		this.actionKey = actionKey;
	}

	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey( String ownerKey ) {
		this.ownerKey = ownerKey;
	}

	public String getTreasury() {
		return treasury;
	}

	public void setTreasury( String treasury ) {
		this.treasury = treasury;
	}

	public int getValue() {
		return value;
	}

	public void setValue( int value ) {
		this.value = value;
	}

	@Override
	public String toString() {

		StringBuilder json = new StringBuilder();

		json.append( "{" );
		json.append( "treasury:\"" ).append( treasury ).append( "\"," );
		json.append( "actionKey:\"" ).append( actionKey ).append( "\"," );
		json.append( "ownerKey:\"" ).append( ownerKey ).append( "\"," );
		json.append( "value:" ).append( Integer.toString( value ) );
		json.append( "}" );

		return json.toString();

	}

}
