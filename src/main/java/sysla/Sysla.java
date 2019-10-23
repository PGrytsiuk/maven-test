package sysla;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



public class Sysla {
	
	 @Test
	  public void testResponsecode() throws IOException {
		  
		  Response resp = RestAssured.get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
		  
		 int code = resp.getStatusCode();
		 
		 ExtentHtmlReporter reporter = new ExtentHtmlReporter("C:/Automation/Report/Sysla.html");
		 
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 
		 ExtentTest logger=extent.createTest("StatusCode test");
		 
		 logger.log(Status.INFO, "Status code");
		 logger.log(Status.PASS, "Status code is 200");
		 
		 logger.addScreenCaptureFromPath("C:/Automation/Report/13770152967026.jpg");
		 
		 extent.flush();
		  
		  System.out.println("Status code is " + code);
		  
		  Assert.assertEquals(code,200);
		  
		 
		  
	  }
	  
	 @Test
	  public void testbody() throws IOException {
		  
		  Response resp = RestAssured.get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
		  
		 String data =resp.asString();
	
     
		  System.out.println("Data is " + data);
		  
		  System.out.println("Response time " + resp.getTime());
		  
		  
		  
		  	  	  
	  }
	 
	 @Test(priority=-1)
	  public void tesåWeather() {
		  
		 RestAssured.baseURI ="http://samples.openweathermap.org/data/2.5/weather";
		  		  
		 String response = RestAssured.given().param("q", "London"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400").
	     when().get().then().extract().asString();
		 
		 System.out.println("Response is : -" +response);
		 
		 Reporter.log("Response is: "+ response, true);
		 
		 ValidatableResponse res = RestAssured.given().param("q", "London"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400").
			     when().get().then();
		 
		 res.statusCode(200);
		  	  
		 Reporter.log("Verified the Status code seccesfuly: ", true);
		 
		 res.contentType(ContentType.JSON);
		 
		 Object countryName = res.extract().response().path("sys.country");
		 
		 System.out.println("Country Name is : " +countryName);
		 
		 
		 JsonPath path = new JsonPath(response);
		 Object countryNm = path.get("sys.country");
		 System.out.println("Country Name  : " +countryNm );
		 
		 res.body("sys.country", Matchers.notNullValue());
		 res.body("sys.country", Matchers.equalToIgnoringCase("GB"));
		 
		 Reporter.log("Country code has verified successfulyy: ", true); 
		 
		 res.body("name", Matchers.notNullValue());
		 res.body("name", Matchers.equalToIgnoringCase("London"));
		
		 Reporter.log("City code has verified successfulyy: ", true);
		 
		 
		 
 	  }
	 
	 
	  @Test
	  
	  public void getWeatherInfoOfLondonWithInValidData1(){
		  
		  ValidatableResponse res = RestAssured.given().param("q", "Londonn"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400").
				     when().get().then();
	  Reporter.log("Response is: " +res.extract().asString(), true);
	  
	  res.statusCode(404);
	  
	  res.body("message", Matchers.notNullValue());
	  res.body("message", Matchers.equalToIgnoringCase("city not found"));
	  Reporter.log("Verified the error message successfully", true);
	  }
	  
  @Test
	  
	  public void getWeatherInfoOfLondonWithInValidData2(){
		  
		  ValidatableResponse res = RestAssured.given().param("q", "Londonn"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400A").
				     when().get().then();
	  Reporter.log("Response is: " +res.extract().asString(), true);
	  
	  res.statusCode(401);
	  
	  res.body("message", Matchers.notNullValue());
	  res.body("message", Matchers.containsString("Invalid API key"));
	  Reporter.log("Verified the error message successfully", true);
	  }
	 
		
	  
  @Test
  public void getWeatherInfoOfDelhiWithValidData() {
	  
	 RestAssured.baseURI ="http://api.openweathermap.org/data/2.5/weather";
	  		  
	 String response = RestAssured.given().param("q", "Delhi"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400").
     when().get().then().extract().asString();
	 
	 System.out.println("Response is : -" +response);
	 
	 Reporter.log("Response is: "+ response, true);
	 
	 ValidatableResponse res = RestAssured.given().param("q", "Delhi"). param("appid", "17e5c69afcef0f16365a6c3b0cba4400").
		     when().get().then();
	 
	 RestAssured.given().auth().preemptive().basic("userName","password").param("grant_type","password");
	 
	 Header first = new Header("Authorization", "access Token key");
	 Header second = new Header("X-Remote-User", "userName");
	 Header third = new Header("Content-Type", "application/json");
	 Headers headers = new Headers(first, second, third);
	 
	 RestAssured.given().headers(headers);
	 
	 res.statusCode(200);
	  	  
	 Reporter.log("Verified the Status code seccesfuly: ", true);
	 
	 res.contentType(ContentType.JSON);
		
	 res.body("sys.country", Matchers.notNullValue());
	 res.body("sys.country", Matchers.equalToIgnoringCase("IN"));
	 
	 Reporter.log("Country code has verified successfulyy: ", true); 
	 
	 res.body("name", Matchers.notNullValue());
	 res.body("name", Matchers.equalToIgnoringCase("Delhi"));
	
	 Reporter.log("City code has verified successfulyy: ", true);
	 
	 
	 
	  }
 

}
