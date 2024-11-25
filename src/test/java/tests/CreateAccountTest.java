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

import java.time.Duration;

public class CreateAccountTest {

    public WebDriver driver;

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

        driver = new ChromeDriver();
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();

        WebElement usernameElement = driver.findElement(By.id("userName"));
        usernameElement.sendKeys(requestBody.getUserName());

        WebElement passwordElement = driver.findElement(By.id("password"));
        passwordElement.sendKeys(requestBody.getPassword());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,450)", "");

        WebElement loginElement = driver.findElement(By.id("login"));
        loginElement.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName-value")));

        WebElement usernameTextElement = driver.findElement(By.id("userName-value"));
        String actualUserName = usernameTextElement.getText();
        Assert.assertEquals(actualUserName,requestBody.getUserName(),"The user name is not logged in with succes.");


    }
}
