package org.twuni.money.common;

public interface Locator<K, V> {

	public V lookup( K key );

}
