package com.example.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class People {
	int jmlRecordPersonAwal, jmlNoTelpAwal;
	
	@Given("^Ambil jumlah record person saat ini$")
	public void givenAddPerson() throws Throwable {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/all")
				.header("Accept", "application/json")
				.asJson();
		JSONArray arrPerson = jsonResponse.getBody().getArray();
		jmlRecordPersonAwal = arrPerson.length();
	}
	
	@When("^Ditambahkan record baru dengan first name: (.+), last name: (.+), age: (\\d+) years, phone number: (\\d+), no regis: (\\d*)$")
	public void whenAddPerson(String firstName, String lastName, int age, String phoneNumber, String noRegis) throws Throwable {
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("{\"firstName\": \"" + firstName + "\",").
				append("\"lastName\": \"" + lastName + "\",").
				append("\"age\": " + age + ",").
				append("\"regis\": {").
				append("\"noregis\": \"" + noRegis + "\"").
				append("}, ").
				append("\"phones\": [{\"phonenumber\": \"" + phoneNumber + "\"}]").
				append("}");
		
		@SuppressWarnings("unused")
		HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:8080/persons/add")
				  .header("Content-Type", "application/json")
				  .header("Accept", "application/json")
				  .body(jsonStr.toString())
				  .asJson();
	}
	
	@Then("^Jumlah record person menjadi jumlah record awal \\+ (\\d+)$")
	public void thenAddPerson(int jmlPenambahanRecord) throws Throwable {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/all")
				.header("Accept", "application/json")
				.asJson();
		JSONArray arrPerson = jsonResponse.getBody().getArray();
		assertThat(arrPerson.length(), equalTo(jmlRecordPersonAwal+jmlPenambahanRecord));
	}
}