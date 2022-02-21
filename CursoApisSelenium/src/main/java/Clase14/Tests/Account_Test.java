package Clase14.Tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Account_Test {

    @Given("I got the access token and instance url")
    public void i_got_the_access_token_and_instance_url() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("GIVEN");
    }
    @Given("I got a new account")
    public void i_got_a_new_account() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("GIVEN");
    }
    @When("I send a request to create an account")
    public void i_send_a_request_to_create_an_account() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("WHEN");
    }
    @Then("an account has been created")
    public void an_account_has_been_created() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("THEN");
    }


}
