package com.enhance.rtt.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.jayway.jsonpath.JsonPath;

import sandboxapii.ComputerJson;
import sandboxapii.ComputerJson.Price;
import sandboxapii.PropertyReader;
import sandboxapii.TradeMeApi;

public class TC_001_TradeMeApi {
	OAuth1AccessToken accessToken = null;
	OAuth10aService service;
	long createdListingId = 0;
	Response get_response;
	ComputerJson cj;

	@BeforeTest
	public void Auth() throws IOException, InterruptedException, ExecutionException, ParseException {
		PropertyReader prop = new PropertyReader();
		service = new ServiceBuilder(prop.getProperty("oauth.consumerKey"))
				.apiSecret(prop.getProperty("oauth.consumerSecret")).build(TradeMeApi.instance());
		final OAuth1RequestToken requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);

		String authCode = null;
		try {
			System.out.println("Go to URL: " + authUrl + " ,and Enter 7 Digit Code!");
			System.out.print("Code: ");
			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			authCode = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		accessToken = service.getAccessToken(requestToken, authCode);

		cj = new ComputerJson("src/test/resources/AppleLaptop.json");
		String body = cj.setTitle("Almost new apple pc").setDuration(6).setAttribute("Memory", "4 GB")
				.setAttribute("Hard Drive Size", "2 TB").getJsonString();

		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.tmsandbox.co.nz/v1/Selling.json");
		request.addHeader("Content-type", "application/json");
		request.setPayload(body);
		service.signRequest(accessToken, request);
		Response response = service.execute(request);
		assertTrue(response.isSuccessful());

		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject(response.getBody());
		createdListingId = (long) json.get("ListingId");

		OAuthRequest get_request = new OAuthRequest(Verb.GET,
				String.format("https://api.tmsandbox.co.nz/v1/Listings/%d.json", createdListingId));
		request.addHeader("Content-type", "application/json");
		service.signRequest(accessToken, get_request);
		get_response = service.execute(get_request);


	}
	
	@Test
	public void ValidateTitle() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating Title");
		Object expected = JsonPath.read(cj.getJsonString(), "$.Title");
		Object actual = JsonPath.read(get_response.getBody(), "$.Title");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}
	
	@Test
	public void ValidateStartPrice() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating StartPrice value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.StartPrice");
		Object actual = JsonPath.read(get_response.getBody(), "$.StartPrice");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}
	
	@Test
	public void ValidateReservePrice() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating ReservePrice value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.ReservePrice");
		Object actual = JsonPath.read(get_response.getBody(), "$.ReservePrice");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void ValidateBuyNowPrice() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating BuyNowPrice value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.BuyNowPrice");
		Object actual = JsonPath.read(get_response.getBody(), "$.BuyNowPrice");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);
	}
	
	

	@Test
	public void ValidateMemory() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating memory value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.Attributes[?(@.DisplayName== 'Memory')]..Value");
		Object actual = JsonPath.read(get_response.getBody(), "$.Attributes[?(@.DisplayName== 'Memory')]..Value");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}

	@Test
	public void ValidateHardDriveSize() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating Hard Drive Size value");
		Object expected = JsonPath.read(cj.getJsonString(),
				"$.Attributes[?(@.DisplayName== 'Hard Drive Size')]..Value");
		Object actual = JsonPath.read(get_response.getBody(),
				"$.Attributes[?(@.DisplayName== 'Hard Drive Size')]..Value");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}
	@Test
	public void ValidateScreenSize() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating Screen Size value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.Attributes[?(@.DisplayName== 'Screen Size')]..Value");
		Object actual = JsonPath.read(get_response.getBody(), "$.Attributes[?(@.DisplayName== 'Screen Size')]..Value");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}
	@Test
	public void ValidateCores() throws InterruptedException, ExecutionException, IOException {
		Reporter.log("Validating Cores value");
		Object expected = JsonPath.read(cj.getJsonString(), "$.Attributes[?(@.DisplayName== 'Cores')]..Value");
		Object actual = JsonPath.read(get_response.getBody(), "$.Attributes[?(@.DisplayName== 'Cores')]..Value");
		Reporter.log("Expected value: " + expected);
		Reporter.log("Actual value: " + actual);
		assertEquals(expected, actual);

	}

}