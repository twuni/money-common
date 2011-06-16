package org.twuni.money.common;

import java.util.List;

public interface Repository<K, V> {

	public V findById( K key );

	public void save( V value );

	public void delete( V value );
	
	public List<V> list( int limit );
	
	public List<V> list();

}
