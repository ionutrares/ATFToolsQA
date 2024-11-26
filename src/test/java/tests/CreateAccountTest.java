package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import modelRequest.RequestAccount;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfilePage;
import sharedData.Hooks;

import java.time.Duration;

public class CreateAccountTest extends Hooks {

    @Test
    public void testMethod(){
        //definim comunicarea cu clientul
        RestAssured.baseURI="https://demoqa.com";

        //definim un nou request
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        //definim un request body
        RequestAccount requestBody = new RequestAccount("RaresAutomation", "RaresAutomation123!");

        //executam acest request la un endpoint
        request.body(requestBody);
        Response response = request.post("/Account/v1/User");
        
        //interactiunea cu response
        System.out.println(response.getStatusLine());
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asString());

        //deschidem un browser si ne logam cu contul creat
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginProcess(requestBody);

        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.validateLoginProcess(requestBody);

    }
}
