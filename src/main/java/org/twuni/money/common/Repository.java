package org.twuni.money.common;

public interface Repository<K, V> {

	public V findById( K key );

	public void save( V value );

	public void delete( V value );

}
