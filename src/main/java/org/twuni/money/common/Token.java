package org.twuni.money.common;

public interface Token extends Comparable<Token> {

	public String getTreasury();

	public String getId();

	public int getValue();

	public String getSecret();

}
