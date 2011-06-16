Overview
========

This library contains the core components of the Digital Cash API. Base implementations are provided wherever
possible to simplify extension.


The Breakdown
=============

The `Token` object represents a single currency unit. It contains a public identifier, a private
secret known only to its owner, the domain name of the treasury that issued it, and a numeric value.
This token is how value is represented throughout the system. A basic implementation of this is `SimpleToken`.

The `Treasury` object is the authority that processes all financial transactions for currency it issues.
It can split a `Token` in two, merge two `Token`s together, or evaluate a `Token`'s worth.

The `Bank` object allows one to interact with a collection of `Token`s via a familiar banking interface.
Much like an ATM, it can deposit a `Token`, withdraw a `Token` worth a specific value, or provide the 
total value of all `Token`s in the collection.


How to use this library
=======================

In the interest of learning by example, a rudimentary instantiation of a `Bank` is as follows:

	// This repository simply stores tokens in memory. When the application stops running, the tokens are lost.
	Repository<String, Token> repository = new SimpleRepository<Token>();
	
	// This constructs an HTTP client with the default configuration. Should be suitable for most cases.
	HttpClient client = new DefaultHttpClient();
	
	// This treasury client communicates exclusively with money.twuni.org.
	Treasury treasury = new TreasuryClient( client, "money.twuni.org" );
	
	// Finally, construct the Bank using the repository and treasury you just created.
	Bank bank = new Bank( repository, treasury );

Your application will probably use the same configuration for each bank, differing from each other only
by the treasury domain. You may want to extend the Bank object and have your subclass configure itself.
Thus, whenever you want to instantiate a new Bank, you might run something akin to this:

    Bank bank = new FooBank( "money.twuni.org" );


[Treasury REST API]: https://sites.google.com/a/twuni.org/digital-currency/treasury/rest-api
