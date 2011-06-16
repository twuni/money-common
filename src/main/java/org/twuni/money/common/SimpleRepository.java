package org.twuni.money.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRepository<V> implements Repository<String, V> {

	private final Map<Integer, V> map = new HashMap<Integer, V>();

	@Override
	public V findById( String key ) {
		return map.get( Integer.valueOf( key.hashCode() ) );
	}

	@Override
	public void save( V value ) {
		map.put( Integer.valueOf( value.hashCode() ), value );
	}

	@Override
	public void delete( V value ) {
		map.put( Integer.valueOf( value.hashCode() ), null );
	}

	@Override
	public List<V> list( int limit ) {
		ArrayList<V> list = new ArrayList<V>( map.values() );
		return limit > list.size() ? list : list.subList( 0, limit );
	}

	@Override
	public List<V> list() {
		return list( Integer.MAX_VALUE );
	}

}
