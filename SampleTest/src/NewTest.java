import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class NewTest {
	WebDriver driver;
	String h2FirstText;
	 Response response = null;
	
	 @BeforeTest
	  public void beforeTest() {
		 // System.setProperty("webdriver.chrome.driver", "D:\\Selenium driver\\chromedriver_win32\\chromedriver.exe");
		  driver = new ChromeDriver();
		  RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";	  
		  PageFactory.initElements(driver, this);
	  }
  
 @Test(priority=1)
  public void navigatetoFeelingLuck() {
	  
	  driver.get("https://www.google.com"); 
	  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	
	  driver.findElement(By.xpath("(//input[@value=\"I'm Feeling Lucky\"])[2]")).click();
	  List<WebElement> h2 = driver.findElements(By.xpath("//h2"));
	  if(h2.size()>0){
	  h2FirstText= h2.get(0).getText();
	  }
	  else{
		  System.out.println("Website doesn't contains data with H2 tag");
		  Random rand = new Random();
		  h2FirstText="Kundan"+rand.nextInt();
	  }
	  System.out.println(h2FirstText);
	 
  	  
  }
 @Test(priority=2)
 public void createPostReq() {
		 
	 String requestBody = "{\n" + "  \"name\": \""+h2FirstText+"\",\n" +
	 "  \"salary\": \"5000\",\n" +
	 "  \"age\": \"20\"\n" +
	 "}";

	 
	
	 
	 try {
		 
		 response = RestAssured.given()
		 .contentType(ContentType.JSON)
		 .body(requestBody)
		 .post("/create");
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 
		 System.out.println("Response :" + response.asString());
		 System.out.println("Status Code :" + response.getStatusCode());
		  
		 
	 } 	 	  
 @Test(priority=3)	  
 public void getReq() {
	 try {
		 RequestSpecification httpRequest = RestAssured.given();
		 Response getResponse = httpRequest.get("/employees/"+h2FirstText);
		 JsonPath jsonPathEvaluator = response.jsonPath();
		 String empName = jsonPathEvaluator.get("name");
		 System.out.println("Employee name in Response " + empName);
		 Assert.assertEquals(empName, h2FirstText, "Correct Employee name received in the Response"); 
	 }catch (Exception e) {
		 e.printStackTrace();
		 }
 }
 
 
  @AfterTest
  public void afterTest() {
	  driver.close();
  }

}
