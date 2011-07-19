package org.twuni.money.common;

import org.twuni.common.crypto.rsa.PrivateKey;

public interface Token extends Comparable<Token> {

	public PrivateKey getActionKey();

	public PrivateKey getOwnerKey();

	public String getTreasury();

	public int getValue();

}
