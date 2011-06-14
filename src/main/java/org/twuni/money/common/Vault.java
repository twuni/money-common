package org.twuni.money.common;

import java.util.Collection;

public interface Vault {

	public void save( Collection<Token> tokens );

	public Collection<Token> load();

}
