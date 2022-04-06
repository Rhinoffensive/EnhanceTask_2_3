# EnhanceTask_Questions_2_3

### Questions ###

Using the TradeMe Sandbox environment API, write automation code which does the following:

2. As an API test: Retrieve a list of charities and confirm that St John is included in the list.
3. As an API test: Create a new listing with any relevant details in the category 0002-0356-0032-2273-
and confirm the listing was created successfully.
 - - - -
#### *Disclaimer* : #### 

I have used  Eclipse as an IDE and  Maven as the package manager.

There is a single project for both of the questions written above.

I used Cucumber Framework for Q2 and TestNG for Q3.

- - - - 
# QUESTION 2

## Approach

I used the method described in the link below: 

https://developer.trademe.co.nz/api-reference/catalogue-methods/retrieve-list-of-charities 

This method have public permission request.

- - - -

## How to use it
Run TestRunner.java as JUnit test.

    /sandboxapii/src/test/java/com/enhance/rtt/runner/TestRunner.java

It will run this test:

    /sandboxapii/src/test/java/com/enhance/rtt/stepdefiniton/StepDefinition.java

I created StepDefinition.java using this feature file:

    /sandboxapii/FeatureFiles/RetrievingListOfCharities.feature

- - - - 
# QUESTION 3

## Approach

This question requires Trademe API key for both of these two API methods below:

1. https://developer.trademe.co.nz/api-reference/selling-methods/list-an-item
2. https://developer.trademe.co.nz/api-reference/listing-methods/retrieve-the-details-of-a-single-listing 

Note: Although the permission required is given as none in the second API method, it asks for authorization.

Note: Trademe.co.nz blocks the account when a sign-in attempt comes from offshore. I contacted the developers and they unblocked my account.

- - - -

## How to use it

First you need to enter your API key to the file below:

    /sandboxapii/ConfigFiles/trademe.properties

```
    oauth.consumerKey=<Your api key>
    oauth.consumerSecret=<Your api secret>
```
Run TC_001_TradeMeApi.java as TestNG test.

    /sandboxapii/src/test/java/com/enhance/rtt/testcases/TC_001_TradeMeApi.java


I created an OAuth 1.0 protocole using the API key in the property file. At first I was going to use restassured for this question as well, but it's hard to use when it comes to authentication. So I used a library called scribejava instead. More than 50 API comes with Scribejava and new API implementation can be easily done. I implemented TrateMe API in this file:

    /sandboxapii/src/main/java/sandboxapii/TradeMeApi.java

```java
	service = new ServiceBuilder(prop.getProperty("oauth.consumerKey"))
				.apiSecret(prop.getProperty("oauth.consumerSecret")).build(TradeMeApi.instance());
		final OAuth1RequestToken requestToken = service.getRequestToken();
```
Note: service.getRequestToken() method uses the following endpoint with read and write rights: https://secure.tmsandbox.co.nz/Oauth/RequestToken?scope=MyTradeMeRead,MyTradeMeWrite

~~~
Go to URL: https://secure.tmsandbox.co.nz/Oauth/Authorize?oauth_token=F00F80B3EEC998F450ED900980503616 ,and Enter 7 Digit Code!
Code: 
~~~

#### Verifier Code
![picture alt](https://github.com/Rhinoffensive/EnhanceTask_2_3/blob/master/code.PNG?raw=true "Code")


Enter the 7 digit verifier code in the console.

~~~
... Enter 7 Digit Code!
Code: 6235441
~~~

I created AppleLaptop.json file as a template using [Retrieve detailed information about a single category](https://developer.trademe.co.nz/api-reference/catalogue-methods/retrieve-detailed-information-about-a-single-category).

I created a class that modifies the fields such as memory, screen size, hard drive size, etc.

```java
	cj = new ComputerJson("src/test/resources/AppleLaptop.json");
	String body = cj.setTitle("Almost new apple pc")
    .setDuration(6)
    .setAttribute("Memory", "4 GB")
    .setAttribute("Hard Drive Size", "2 TB")
    .getJsonString();

```
I post this json string to the server. If it creates the listing successfully, its response body contains a Listing ID.

```java
	OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.tmsandbox.co.nz/v1/Selling.json");
	request.addHeader("Content-type", "application/json");
	request.setPayload(body);
	service.signRequest(accessToken, request);
	Response response = service.execute(request);
	assertTrue(response.isSuccessful());

    JSONParser parser = new JSONParser();
	JSONObject json = new JSONObject(response.getBody());
	createdListingId = (long) json.get("ListingId");
```

I get the details of the particular listing using the following endpoint: [Retrieve the details of a single listing
](https://developer.trademe.co.nz/api-reference/listing-methods/retrieve-the-details-of-a-single-listing)

```java
	OAuthRequest get_request = new OAuthRequest(Verb.GET,
				String.format("https://api.tmsandbox.co.nz/v1/Listings/%d.json", createdListingId));
		request.addHeader("Content-type", "application/json");
		service.signRequest(accessToken, get_request);
		get_response = service.execute(get_request);
```

Now we have published and requested the json objects to compare.
```java

	@Test
	public void ValidateTitle() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating Title");
		Object expected = JsonPath.read(cj.getJsonString(), "$.Title");
		Object actual = JsonPath.read(get_response.getBody(), "$.Title");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}
```

