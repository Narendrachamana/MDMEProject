package com.Base;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.Reusable.WrapperMethods;
import com.Utilities.DateForUse;
import com.Utilities.ReadProperty;
import com.relevantcodes.extentreports.ExtentReports;
/*import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;*/
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class BaseSetUp {

	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest extlogger;
	public ReadProperty property = new ReadProperty("config");
	public String url ;
	public String browser = property.getProperty("BROWSER");
	public String username = property.getProperty("USERNAME");
	public String password = property.getProperty("PASSWORD");
	public String enviornmentName=property.getProperty("ENVIRONMENT");
	public static String repoDate;

	@BeforeSuite
	public void setUp() throws InterruptedException {
		
		/*//url=System.getenv("MDMEURL");
		if(url == null){
			url=property.getProperty("MDMEURL");
		}
		else{*/
			
		//switch case to pick application url based on environment name from ConfigFile
		switch (enviornmentName) {

		case "SmokeTest-Dev":
			url = property.getProperty("DEVURL");
			break;
		case "SmokeTest-Dev2":
			url = property.getProperty("DEV2URL");
			break;
		case "SmokeTest-QA":
			url = property.getProperty("SITURL");
			
		}
	/*	}*/
		
		repoDate = DateForUse.getDateTimeAsFormat();
		extent = new ExtentReports(System.getProperty("user.dir") + File.separator + "Reports" + File.separator
				+ repoDate + File.separator + "ExtentReport_" + DateForUse.getDateTimeAsFormat() + ".html", true);

		extent.addSystemInfo("Application", "MDME").addSystemInfo("Environment", "QA")
				.addSystemInfo("Browser", browser).addSystemInfo("UserName", username);
		extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extent-config.xml"));
		extlogger = extent.startTest("Launch Browser and Open Website",
				"This test is used to Launch Browser and Open Website");

		try {
			if (browser.equalsIgnoreCase("firefox")) {
				extlogger.log(LogStatus.INFO, "Firefox browser is launching");
				driver = new FirefoxDriver();
			}

			else if (browser.equalsIgnoreCase("chrome")) {
				extlogger.log(LogStatus.INFO, "Chrome browser is launching");
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator
						+ "Drivers" + File.separator + "chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-extensions");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(capabilities);
			} 
			else if (browser.equalsIgnoreCase("ie")) {
				System.out.println("IE browser is launching");
				extlogger.log(LogStatus.INFO, "IE browser is launching");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
				ieCapabilities.setCapability("ignoreZoomSetting", true);
				ieCapabilities.setCapability("requireWindowFocus", true);
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "Drivers"
						+ File.separator + "IEDriverServer.exe");
				driver = new InternetExplorerDriver(ieCapabilities);
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("document.body.style.transform='scale(0.9)'",true);
			} else if (browser.equalsIgnoreCase("edge")) {
				System.out.println("Edge browser is going to launch");
				extlogger.log(LogStatus.INFO, "Edge browser is going to launch");
				DesiredCapabilities edgeCapabilities = DesiredCapabilities.edge();
				edgeCapabilities.setCapability("ignoreZoomSetting", true);
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + File.separator + "Drivers"
						+ File.separator + "MicrosoftWebDriver.exe");
				driver = new EdgeDriver(edgeCapabilities);
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("document.body.style.zoom='100%';");
			}
				
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

				try {
					System.out.println(url + "is going to enter in opened browser");
					driver.get(url);
					Thread.sleep(5000);
					System.out.println("Displayed Url on browser is " + driver.getCurrentUrl());
					extlogger.log(LogStatus.INFO, "Displayed Url on browser is " + driver.getCurrentUrl());

				} catch (Exception e) {
					extlogger.log(LogStatus.ERROR, url + " not entered in browser address bar");
				}/*
				if (WrapperMethods.verifyExist(driver, "CERTIFICATE_ERROR_EDGE", "Common", extlogger)) {
						WrapperMethods.hoverClick(driver, "CERTIFICATE_DETAILS_EDGE", "Common", extlogger);
						WrapperMethods.hoverClick(driver, "CERTIFICATE_GOONTOTHEWEBPAGE_EDGE", "Common", extlogger);
						Thread.sleep(5000);
							if (WrapperMethods.verifyExist(driver, "LOGIN_EMAIL_INPUT", "Common", extlogger)) {
								
					}
*/
				

				try {
					driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					System.out.println("Page is not loaded");
					System.out.println("Going to refresh the page");
					driver.navigate().refresh();
				}
				/* }
			else {
				System.out.println(browser + " browser is not available");
				extlogger.log(LogStatus.INFO, " There is no browser available : " + browser);
				throw new RuntimeException(browser + " : browser not available. Please mention valid browser");
			}*/

			if (WrapperMethods.verifyExist(driver, "HPE EMDM UI", "Common", extlogger)) {
				extlogger.log(LogStatus.PASS, "MDME Home page successfully launched");
				System.out.println("MDME Home page successfully launched");
			
			}
			else{
				extlogger.log(LogStatus.FAIL, "MDME application is not loaded");
				System.out.println("MDME application is not loaded");
				extent.flush();
				System.out.println("Going to close browser");
				driver.quit();
				Assert.fail();
			}

		}

		catch (NullPointerException e) {
			extlogger.log(LogStatus.INFO, "Exception while launching driver:" + e);
			System.out.println("Exception while launching driver:" + e);

		} catch (NoSuchElementException e1) {

			extlogger.log(LogStatus.INFO, "Browser going to refresh because:" + e1);
			System.out.println("Browser going to refresh because:" + e1);
			driver.navigate().refresh();
		}
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			extlogger.log(LogStatus.FAIL, result.getName() + " Test Case Failed");
			extlogger.log(LogStatus.FAIL, result.getThrowable());

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			 extlogger.log(LogStatus.PASS, result.getName()+" Test Case Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			extlogger.log(LogStatus.SKIP, result.getName() + " Test Case Skipped ");
		}

		extent.endTest(extlogger);
	}

	@AfterSuite
	public void endReport() {
		 extlogger = extent.startTest("Verify if user can logout of MDME Application",
		 "User should be logged out of the application");
		// DCIPPCommon.logout(driver, extlogger);
		 extlogger.log(LogStatus.INFO, "Going to close the browser");
		extent.flush();
		System.out.println("Going to close browser");
		driver.quit();
	}
	
	
	
}