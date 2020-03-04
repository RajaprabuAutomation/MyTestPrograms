package com.test.EDCwallet;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

public class EDC_Login {
		
	static Inputs inputs = new Inputs();
	
	static WebElement pwd_element, repeat_pwd_element, brainkey_element, login, retry, login_reg, login_title;
	
	static WebElement done, EDC_balance, logout, logout_1, close_1;
	
	static WebElement lease_1, fullamount, lease_2, lease_enter, confirm, close;
	
	static WebDriver driver;
	
	static String balance;
	
	static double balance_edc = 0.00;
	
		@BeforeTest
		public static void browserStart() throws InterruptedException {
	
        // declaration and instantiation of objects/variables
			
			// New Change
			
		
    	System.setProperty("webdriver.gecko.driver","D://Rajaprabu//eclipse-workspace//EDCwallet//drivers//geckodriver.exe");
    	String baseUrl = "https://wallet.blockchain.mn/login";
    	driver = new FirefoxDriver();

    	//To Run on Remote Machine using selenium Grid
    	
		/*String nodeURL = "http://172.20.49.23:4444/wd/hub";
		
		DesiredCapabilities cap = DesiredCapabilities.firefox();
		cap.setBrowserName("firefox");
		cap.setPlatform(Platform.WIN10);
		FirefoxOptions options = new FirefoxOptions();
		driver = new RemoteWebDriver(new URL(nodeURL), options);*/
		
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String Title = driver.getTitle();
		
		String text = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div/section/div/div[1]/div/span")).getText();
			
		if(text.equalsIgnoreCase("AT THE TIME THE CONNECTION WITH THE SERVER IS UNSTABLE.")){

			System.out.println("Error: Retry Page is Present");
			
			retry = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div/section/div/div[2]/form/div/button"));
			retry.click();
			Thread.sleep(2000);
			
			login_reg = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[1]/div/div/div/div[3]/a/span/span"));
			login_reg.click();

			}else{

			System.out.println("Error: Retry Page is Absent");

			}
		
		Assert.assertEquals("EDC Blockchain", Title);
		}
		
		@Test
		public static void login() throws InterruptedException {
		
		pwd_element = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[1]/section/div/div[2]/form/label[1]/span[2]/input"));
		pwd_element.sendKeys(inputs.getPassword());
		
		repeat_pwd_element = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[1]/section/div/div[2]/form/label[2]/span[2]/input"));
		repeat_pwd_element.sendKeys(inputs.getRepeatPassword());
		
		brainkey_element = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[1]/section/div/div[2]/form/label[3]/span[2]/textarea"));
		brainkey_element.sendKeys(inputs.getBrainkey());
		
		login = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[1]/section/div/div[2]/form/div[3]/div[2]/button"));
		login.click();
		Thread.sleep(2000);
		
		//Close button in extra prompt after login
		
		close_1= driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/span/span"));
		close_1.click();
		
		login_title = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/section/div/div[2]/div/span"));
		
		String text = login_title.getText();
		System.out.println(text);
		
		Assert.assertEquals("Welcome back to EDC Blockchain!", login_title.getText());
		
		}
		
		@Test(dependsOnMethods = { "login" })
		public static void getEDCBalance() throws InterruptedException {
			
		done = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[3]/section/div/div[2]/form/div/button"));
		done.click();
		
		EDC_balance = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[2]/div/div[1]/span[2]/span[1]"));
		balance = EDC_balance.getText();
		balance_edc = Double.parseDouble(balance);
		
		System.out.println("Balance is:" + balance_edc);
		
		
		Thread.sleep(3000);
		}
		
		@Test(dependsOnMethods = { "getEDCBalance" })
		public static void lease() throws InterruptedException {
			
			if(balance_edc > 100)
			{
				
				lease_1 = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[1]/div/ul/li[2]/a"));
				lease_1.click();
				
				lease_2 = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[3]/section/div/div/div/div[1]/header/button"));
				lease_2.click();
				
				fullamount = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[3]/section/div/div/div/div/div[1]/div[3]/div/p"));
				fullamount.click();
				
				lease_enter = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/section/div/div/div/div/button"));
				lease_enter.click();
				
				confirm = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/section/div/div/div/div[2]/button"));
				confirm.click();
				
				Thread.sleep(10000);
				
				EDC_balance = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div[1]/div/div[2]/div/div[1]/span[2]/span[1]"));
				balance = EDC_balance.getText();
				
				System.out.println("After Lease value is: " + balance);
				
				File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				try {
				FileUtils.copyFile(src, new File("D:\\Rajaprabu\\eclipse-workspace\\EDCwallet\\Screenshot\\Recipt.png"));
				}
				 
				catch (IOException e)
				 {
				  System.out.println(e.getMessage());
				 
				 }
				
				close = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/section/div/div/div/div[2]/div[2]/div[6]/button"));
				close.click();
	
			}
			else {
				System.out.println("EDC balance is Low: " + balance_edc);
				File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				try {
				FileUtils.copyFile(src, new File("D:\\Rajaprabu\\eclipse-workspace\\EDCwallet\\Screenshot\\Balance.png"));
				}
				 
				catch (IOException e)
				 {
				  System.out.println(e.getMessage());
				 
				 }
			}
		
		}
		
		@AfterTest
		public static void logout() {
		
		logout = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/div/div/div/div[3]/a/span/span"));
		logout.click();
		
		logout_1 = driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[3]/section/div/div[2]/div/div/div[3]/button"));
		logout_1.click();
		
		driver.quit();
			
		}

	}

