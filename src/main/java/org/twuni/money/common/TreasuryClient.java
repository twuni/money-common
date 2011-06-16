package org.twuni.money.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.twuni.money.common.exception.NetworkException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TreasuryClient implements Treasury {

	private static final String VALUE_URI = "https://%s/treasury/value?id=%s";
	private static final String MERGE_URI = "https://%s/treasury/merge";
	private static final String SPLIT_URI = "https://%s/treasury/split";

	private final String domain;
	private final HttpClient client;

	public TreasuryClient( HttpClient client, String domain ) {
		this.client = client;
		this.domain = domain;
	}

	@Override
	public Set<Token> split( Token token, int amount ) {

		HttpPost post = new HttpPost( String.format( SPLIT_URI, domain ) );

		List<NameValuePair> parameters = new ArrayList<NameValuePair>( 3 );

		parameters.add( new BasicNameValuePair( "id", token.getId().toString() ) );
		parameters.add( new BasicNameValuePair( "secret", token.getSecret().toString() ) );
		parameters.add( new BasicNameValuePair( "value", Integer.toString( amount ) ) );

		try {
			post.setEntity( new UrlEncodedFormEntity( parameters ) );
			return execute( post, new TypeToken<Set<SimpleToken>>() {
			}.getType() );
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}

	}

	@Override
	public Token merge( Token a, Token b ) {

		HttpPost post = new HttpPost( String.format( MERGE_URI, domain ) );

		List<NameValuePair> parameters = new ArrayList<NameValuePair>( 4 );

		parameters.add( new BasicNameValuePair( "id1", a.getId().toString() ) );
		parameters.add( new BasicNameValuePair( "secret1", a.getSecret().toString() ) );

		parameters.add( new BasicNameValuePair( "id2", b.getId().toString() ) );
		parameters.add( new BasicNameValuePair( "secret2", b.getSecret().toString() ) );

		try {
			post.setEntity( new UrlEncodedFormEntity( parameters ) );
			return execute( post, new TypeToken<SimpleToken>() {
			}.getType() );
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}

	}

	@Override
	public int getValue( Token token ) {

		HttpGet get = new HttpGet( String.format( VALUE_URI, domain, encodeUrlComponent( token.getId() ) ) );

		try {
			Integer response = execute( get, new TypeToken<Integer>() {
			}.getType() );
			return response.intValue();
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}

	}

	@SuppressWarnings( "unchecked" )
    private <T> T execute( HttpUriRequest request, Type responseType ) throws ClientProtocolException, IOException {
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		String response = client.execute( request, responseHandler );
		return (T) new Gson().fromJson( response, responseType );
	}

	private String encodeUrlComponent( String id ) {
		if( id == null || "".equals( id ) ) {
			return "";
		}
		try {
			return URLEncoder.encode( id, "UTF-8" );
		} catch( UnsupportedEncodingException exception ) {
		}
		return id;
	}

	@Override
	public boolean equals( Object object ) {
		if( object == null ) {
			return false;
		}
		if( object instanceof TreasuryClient ) {
			TreasuryClient treasury = (TreasuryClient) object;
			return domain.equals( treasury.domain );
		}
		return super.equals( object );
	}

	@Override
	public String toString() {
		return domain;
	}

}
