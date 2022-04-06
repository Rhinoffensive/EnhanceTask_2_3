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

Test başlamadan önce property fileden okuduğum api keyi ile OAuth 1.0 protokolü oluşturuyorum. 
Başta restassured kullanacaktım ama kolayca beceremedim. Bunun yerine scribejava adlı bir kütüphane kullandım. scribejava sık kullanılan 50+ api hazır geliyor. api implemantasyonu kolayca yapılabiliyor. ben de şu dosyada yaptım

    /sandboxapii/src/main/java/sandboxapii/TradeMeApi.java

```java
	service = new ServiceBuilder(prop.getProperty("oauth.consumerKey"))
				.apiSecret(prop.getProperty("oauth.consumerSecret")).build(TradeMeApi.instance());
		final OAuth1RequestToken requestToken = service.getRequestToken();
```