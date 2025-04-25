package com.banker.wissen;


import com.banker.wissen.entity.BankEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinations {
    Response response;

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
        assertTrue(response.getBody().asString().contains(accNumber));
    }

    @Then("the response status empty should be {int}")
    public void response_status_should_not_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

}
