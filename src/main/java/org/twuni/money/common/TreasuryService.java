package org.twuni.money.common;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

public class TreasuryService implements Treasury {

	private final SecureRandom random = new SecureRandom();

	protected int idLength;
	protected String treasury;
	protected Repository<String, Token> repository;

	public TreasuryService( int idLength, String treasury, Repository<String, Token> repository ) {
		this.idLength = idLength;
		this.treasury = treasury;
		this.repository = repository;
	}

	@Override
	public SimpleToken create( int value ) {

		if( value <= 0 ) {
			throw new IllegalArgumentException( "A token's worth must be greater than zero." );
		}

		String id = generateUniqueId( idLength );
		String secret = generateRandomBase64String( idLength );

		Token token = new SimpleToken( treasury, id, secret, value );

		save( token );

		return new SimpleToken( token );

	}

	private String generateRandomBase64String( int length ) {
		byte [] buffer = new byte [length];
		random.nextBytes( buffer );
		String result = Base64.encodeBase64String( buffer );
		return result;
	}

	private String generateUniqueId( int length ) {
		String id;
		do {
			id = generateRandomBase64String( length );
		} while( findById( id ) != null );
		return id;
	}

	@Override
	public Set<Token> split( Token original, int amount ) {
		original = lookup( original );
		Token a = create( amount );
		Token b = create( original.getValue() - amount );
		delete( original );
		return new HashSet<Token>( Arrays.asList( a, b ) );
	}

	@Override
	public Token merge( Token a, Token b ) {
		a = lookup( a );
		b = lookup( b );
		if( a.equals( b ) ) {
			throw new IllegalArgumentException( "Cannot merge a token with itself." );
		}
		delete( a );
		delete( b );
		return create( a.getValue() + b.getValue() );
	}

	@Override
	public int getValue( Token token ) {
		token = findById( token.getId() );
		return token == null ? 0 : token.getValue();
	}

	protected Token lookup( Token token ) {
		Token actual = null;
		try {
			actual = findById( token.getId() );
		} catch( Throwable exception ) {
			throw new IllegalArgumentException( exception.getMessage() );
		}
		if( actual == null || !actual.getSecret().equals( token.getSecret() ) ) {
			throw new IllegalArgumentException( String.format( "Token %s is invalid.", token.getId() ) );
		}
		return actual;
	}

	private void delete( Token token ) {
		repository.delete( token );
	}

	private void save( Token token ) {
		repository.save( token );
	}

	private Token findById( String id ) {
		return repository.findById( id );
	}

}
