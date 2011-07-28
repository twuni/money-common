package org.twuni.money.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.twuni.common.crypto.rsa.KeyGenerator;
import org.twuni.common.crypto.rsa.PrivateKey;

public class TreasuryService implements Treasury {

	private final int keyStrength;
	private final KeyGenerator keyGenerator;
	protected final String treasury;
	protected final Repository<String, Token> repository;

	public TreasuryService( int keyStrength, String treasury, Repository<String, Token> repository ) {
		this.keyGenerator = new KeyGenerator();
		this.keyStrength = keyStrength;
		this.treasury = treasury;
		this.repository = repository;
	}

	@Override
	public SimpleToken create( int value ) {

		if( value <= 0 ) {
			throw new IllegalArgumentException( "A token's worth must be greater than zero." );
		}

		PrivateKey actionKey = keyGenerator.generate( keyStrength );
		PrivateKey ownerKey = keyGenerator.generate( keyStrength );

		Token token = new SimpleToken( treasury, actionKey, ownerKey, value );

		save( token );

		return new SimpleToken( token );

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
		try {
			token = findById( token.getActionKey().getPublicKey().serialize() );
		} catch( Exception exception ) {
			// FIXME: This should catch an ObjectNotFoundException instead to prevent false-negatives.
			token = null;
		}
		return token == null ? 0 : token.getValue();
	}

	protected Token lookup( Token token ) {
		Token actual = null;
		try {
			actual = findById( token.getActionKey().getPublicKey().serialize() );
		} catch( Throwable exception ) {
			throw new IllegalArgumentException( exception.getMessage() );
		}
		if( actual == null || !actual.getOwnerKey().equals( token.getOwnerKey() ) ) {
			throw new IllegalArgumentException( String.format( "Token %s is invalid.", token.getActionKey().getPublicKey() ) );
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
