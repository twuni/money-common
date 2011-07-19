package org.twuni.money.common;

import java.math.BigInteger;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.twuni.common.crypto.rsa.PrivateKey;

public class TreasuryServiceTest {

	private TreasuryService treasury;

	@Before
	public void setUp() {
		treasury = new TreasuryService( 32, "test", new SimpleRepository<Token>() );
	}

	@Test
	public void shouldCreateTwoValidTokensOfTheProperAmountsWhenAskedToSplitOneTokenGivenSplitValueBetweenZeroAndItsWorth() {

		Token originalToken = treasury.create( 100 );
		int amount = 30;

		Set<Token> tokens = treasury.split( originalToken, amount );

		Assert.assertEquals( 2, tokens.size() );

		int sum = 0;

		for( Token token : tokens ) {

			Assert.assertTrue( token.getValue() > 0 );
			Assert.assertTrue( token.getValue() == amount || token.getValue() == originalToken.getValue() - amount );

			sum += token.getValue();

		}

		Assert.assertEquals( 100, sum );

	}

	@Test
	public void shouldCreateValidTokenOfTheProperAmountWhenAskedToMergeTwoValidTokens() {

		Token a = treasury.create( 30 );
		Token b = treasury.create( 70 );

		Token token = treasury.merge( a, b );

		Assert.assertEquals( a.getValue() + b.getValue(), token.getValue() );

	}

	@Test
	public void shouldVoidTokenWhenItHasBeenSplit() {

		Token hundred = treasury.create( 100 );

		treasury.split( hundred, 30 );

		Assert.assertEquals( 0, treasury.getValue( hundred ) );

	}

	@Test
	public void shouldVoidTokensWhenTheyHaveBeenMergedIntoAnotherToken() {

		Token a = treasury.create( 30 );
		Token b = treasury.create( 70 );

		treasury.merge( a, b );

		Assert.assertEquals( 0, treasury.getValue( a ) );
		Assert.assertEquals( 0, treasury.getValue( b ) );

	}

	@Test
	public void shouldReturnTheProperAmountWhenAskedForTokensWorthGivenValidTokenId() {

		Token token = treasury.create( 30 );

		int worth = treasury.getValue( token );

		Assert.assertEquals( 30, worth );

	}

	@Test
	public void shouldReturnZeroWhenAskedForTokensWorthGivenAnUnknownTokenId() {

		Token token = new SimpleToken( new PrivateKey( BigInteger.valueOf( 2 ), BigInteger.valueOf( 3 ) ), null );

		int worth = treasury.getValue( token );

		Assert.assertEquals( 0, worth );

	}

	@Test
	public void shouldReturnZeroWhenAskedForTokensWorthGivenIdForVoidedToken() {

		Token hundred = treasury.create( 100 );
		treasury.split( hundred, 50 );

		int worth = treasury.getValue( hundred );

		Assert.assertEquals( 0, worth );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToMergeTokensWhenNotGivenTheSecretToEitherToken() {

		Token a = treasury.create( 30 );
		Token b = treasury.create( 70 );

		( (SimpleToken) a ).setOwnerKey( null );

		treasury.merge( a, b );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToSplitTokenWhenNotGivenItsSecret() {

		Token token = treasury.create( 100 );

		( (SimpleToken) token ).setOwnerKey( null );

		treasury.split( token, 30 );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToMergeTokenWithItself() {

		Token token = treasury.create( 100 );

		treasury.merge( token, token );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToCreateTokenWhenGivenZeroWorth() {
		treasury.create( 0 );
	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToCreateTokenWhenGivenNegativeWorth() {
		treasury.create( -1 );
	}

}
