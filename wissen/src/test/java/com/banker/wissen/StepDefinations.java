package com.banker.wissen;


import com.banker.wissen.entity.BankEntity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinations {
    Response response;
    BankEntity bank;


    @When("I call GET \\/banking\\/{int}")
    public void iCallGETBanking(int id) {
        response = RestAssured.get("http://localhost:8080/banking/" + id);
    }
    @Then("the response status should be {int}")
    public void response_status_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("the response should contain {string}")
    public void response_should_contain(String text) {
        assertTrue(response.getBody().asString().contains(text));
    }


    @When("I call GET \\/banking")
    public void iCallGETBanking() {
        response = RestAssured.get("http://localhost:8080/banking");
    }

    @And("the response is not null")
    public void theResponseIsNotNull() {
        assertNotNull(response);
    }


    @And("the response should contain {int} accounts")
    public void theResponseShouldContainAccounts(int counts) {
        List<BankEntity> accounts = response.jsonPath().getList("$");
        assertEquals(counts,accounts.size());
    }

    @And("the response account should contain {string}")
    public void theResponseAccountShouldContain(String accNumber) {
        BankEntity bankEntity =  response.getBody().as(BankEntity.class);
        assertEquals(accNumber,bankEntity.getAccountNumber());
        assertAll(
                ()->assertNotNull(bankEntity.getCustomerName()),
                ()->assertNotEquals(0,bankEntity.getBalance())  );

    }

    @Then("the response status empty should be {int}")
    public void response_status_should_not_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Given("I have the following bank details :")
    public void i_have_the_following_bank_details(DataTable dataTable) {
    List<Map<String,String>> data = dataTable.asMaps();
    for(Map<String,String> accountDetails: data )
    {
        bank = new BankEntity();
        bank.setCustomerName(accountDetails.get("customerName"));
        bank.setAccountNumber(accountDetails.get("accountNumber"));
        bank.setBalance(Long.parseLong(accountDetails.get("balance")));
        System.out.println(bank);
    }
    }

        @When("I send this request to \\/insert with bank details")
    public void i_send_this_request_to_banking_insert_with_bank_details() {

            response = given()
                    .contentType("application/json")
                    .body(bank)
                    .when()
                    .post("http://localhost:8080/banking/insert");

    }
}
