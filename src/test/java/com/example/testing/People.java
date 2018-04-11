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
	public void givenAddDeletePerson() throws Throwable {
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
	
	@When("^Hapus person dengan id (\\d+)$")
	public void whenDeletePerson(int personId) throws Throwable {
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("{\"id\": ").append(personId).append("}");

		@SuppressWarnings("unused")
		HttpResponse<JsonNode> jsonResponse = Unirest.delete("http://localhost:8080/persons/delete/"+personId)
				  .header("Content-Type", "application/json")
				  .header("Accept", "application/json")
				  .body(jsonStr.toString())
				  .asJson();
	}
	
	@Then("^Jumlah record person menjadi jumlah record awal \\- (\\d+)$")
	public void thenDeletePerson(int jmlPenguranganRecord) throws Throwable {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/all")
				.header("Accept", "application/json")
				.asJson();
		JSONArray arrPerson = jsonResponse.getBody().getArray();
		assertThat(arrPerson.length(), equalTo(jmlRecordPersonAwal-jmlPenguranganRecord));
	}
	
	@Given("^Ambil jumlah nomor telepon milik person dengan id (\\d+) saat ini$")
	public void givenAddPhone(int personId) throws Throwable{
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/"+personId)
				.header("Accept", "application/json")
				.asJson();
		JSONObject objPerson  = jsonResponse.getBody().getObject();
		JSONArray arrPhoneNumber = objPerson.getJSONArray("phones");
		jmlNoTelpAwal = arrPhoneNumber.length();
	}
	
	@When("^Tambahkan nomor telepon (\\d+) untuk person dengan id (\\d+)$")
	public void whenAddPhone(String phoneNumber, int personId) throws Throwable{
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("{\"phonenumber\": \"" + phoneNumber + "\",").
				append("\"person_id\": " + personId + "}");
		
		@SuppressWarnings("unused")
		HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:8080/persons/addphone")
				  .header("Content-Type", "application/json")
				  .header("Accept", "application/json")
				  .body(jsonStr.toString())
				  .asJson();
	}
	
	@Then("^Jumlah nomor telepon milik person dengan id (\\d+) menjadi jumlah awal \\+ (\\d+)$")
	public void thenAddPhone(String personId, int jmlPenambahanNoTelp) throws Throwable{
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/"+personId)
				.header("Accept", "application/json")
				.asJson();
		JSONObject objPerson  = jsonResponse.getBody().getObject();
		JSONArray arrPhoneNumber = objPerson.getJSONArray("phones");
		assertThat(arrPhoneNumber.length(),equalTo(jmlNoTelpAwal+jmlPenambahanNoTelp));
	}
	
	
	//update
	
	@Given("^Akan dilakukan perubahan first name, last name, dan age pada person dengan id (\\d+)$")
	public void givenUpdatePerson(int personId) throws Throwable {}
	
	@When("^Update person dengan id (\\d+), set firstName: (.+), lastName: (.+), age: (\\d+)$")
	public void whenUpdatePerson(int personId, String firstName, String lastName, int age) throws Throwable {
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("{\"firstName\": \"" + firstName + "\",").
				append("\"lastName\": \"" + lastName + "\",").
				append("\"age\": " + age).
				append("}");
		
		@SuppressWarnings("unused")
		HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:8080/persons/update/"+personId)
				  .header("Content-Type", "application/json")
				  .header("Accept", "application/json")
				  .body(jsonStr.toString())
				  .asJson();
	}
	
	@Then("^Record person dengan id (\\d+) menjadi firstName: (.+), lastName: (.+), age: (\\d+)$")
	public void thenUpdatePerson(int personId, String expectedFirstName, String expectedLastName, int expectedAge) throws Throwable {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/persons/"+personId)
				.header("Accept", "application/json")
				.asJson();
		JSONObject objPerson = jsonResponse.getBody().getObject();
		
		String actualFirstName = objPerson.getString("firstName");
		String actualLastName = objPerson.getString("lastName");
		int actualAge = objPerson.getInt("age");
		
		assertThat(actualFirstName,equalTo(expectedFirstName));
		assertThat(actualLastName,equalTo(expectedLastName));
		assertThat(actualAge,equalTo(expectedAge));
	}
}