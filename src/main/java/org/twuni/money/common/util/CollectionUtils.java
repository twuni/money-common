package org.twuni.money.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

	public static <T> List<T> sort( Collection<T> unsorted ) {

		@SuppressWarnings( "unchecked" )
		T [] array = (T []) unsorted.toArray();

		Arrays.sort( array );

		List<T> sorted = new ArrayList<T>();
		for( T token : array ) {
			sorted.add( token );
		}

		return sorted;

	}

}
