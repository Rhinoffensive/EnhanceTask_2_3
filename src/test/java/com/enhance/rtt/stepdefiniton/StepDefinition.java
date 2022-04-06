package com.enhance.rtt.stepdefiniton;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.*;

public class StepDefinition {

	String endpoint = "https://api.trademe.co.nz/v1/";
	Response response;
	String sellingEnpoint = "https://api.tmsandbox.co.nz/v1/Selling.xml";


	@Given("Get the endpoint fetch the listing")
	public void get_the_endpoint_fetch_the_listing() {
		response = RestAssured.get(endpoint + "Charities.json");

	}

	@When("API Response")
	public void api_response() {
		response.then().assertThat().statusCode(HttpStatus.SC_OK);

	}

	@Then("Response should contain St.John")
	public void response_should_contain_st_john() {
		response.then().assertThat().body(containsString("St John"));
	}

	

}
