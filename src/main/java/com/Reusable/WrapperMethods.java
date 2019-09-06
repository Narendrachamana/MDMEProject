package com.Reusable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.Base.BaseSetUp;
import com.Utilities.DateForUse;
import com.Utilities.Locator;
import com.Utilities.ReadProperty;
import com.Utilities.TestData;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.base.BaseSetup;
//import com.utils.DateForUse;
/*import com.reusable.ExtentTest;
import com.reusable.JavascriptExecutor;
import com.reusable.SeleniumWrapper;
import com.utils.Locator;*/

//import com.qa.ExtentReports.ExtentReporterNG;
//import com.utilities.TestData;



public class WrapperMethods extends TestData
{
    public static String CountryUsed= "india";
	public static WebDriverWait wait;
	static ReadProperty pr = new ReadProperty("config");
	static long shortwait=Long.valueOf(pr.getProperty("shortwait"));
	static long waittime = Long.valueOf(pr.getProperty("wait"));
	static long longwait=Long.valueOf(pr.getProperty("longwait"));

	static String screenshotDir=System.getProperty("user.dir")+File.separator+pr.getProperty("screenShotPath");

	static long Thwait=Long.valueOf(pr.getProperty("Mwait"));
	public static WebDriver driver;

	
	public static  void initializedriver()
	{ 
		ReadProperty pr=new ReadProperty("config");
		String browser = pr.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome"))

		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator
					+ "Drivers" + File.separator + "chromedriver.exe");

			driver = new ChromeDriver();

		}

		else if (browser.equalsIgnoreCase("ff"))

		{
			System.setProperty("webdriver.gecko.driver", System
					.getProperty("user.dir" + File.separator + "Drivers" + File.separator + "geckodriver.exe"));

			driver = new FirefoxDriver();
		}

		else if (browser.equalsIgnoreCase("ie"))

		{
			System.setProperty("webdriver.ie.driver", System
					.getProperty("user.dir" + File.separator + "Drivers" + File.separator + "IEDriverServer.exe"));

			driver = new InternetExplorerDriver();
		}


		driver.manage().window().maximize();
		WrapperMethods.implicitwait();
		driver.get(pr.getProperty("url"));

	}

	
	public static void Closebrowser()
	{
		driver.close();
	}		


	public static  void geturl(String expected ){
		String actual =driver.getCurrentUrl();
		Assert.assertEquals(expected, actual,"url not matched");
	}


	public  static void getTitle(String expected)

	{
		String actual=driver.getTitle();
		Assert.assertEquals(actual, expected, "title not matched" );
	}


	public static  void implicitwait()
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/// To click Element 
	public  void Click(WebElement element)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			System.out.println(element);
			//	 ScreenShot(removeSpecialCharacters(element.toString()));
			//	 ScreenShot(element.);
		}
		catch(Exception e)
		{
			System.out.println("unable to click element.Exception:" +e);
			//ScreenShot(removeSpecialCharacters(element.toString()));
		}
	}

	
	public static void javaScriptClick(WebDriver driver, String elementName, String page, ExtentTest extlogger) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60L);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator.getBy(page, elementName, extlogger)));
				WebElement element = driver.findElement(Locator.getBy(page, elementName, extlogger));
			if (isAvalable(element)) {

				if (element.isDisplayed() && element.isEnabled()) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", element);
					extlogger.log(LogStatus.PASS, "Successfully clicked on : " + elementName);
				} else {
					System.out.println(elementName + ": is not displayed or enabled on "+page+" page");
					extlogger.log(LogStatus.FAIL,
							elementName + ": Element is not displayed or enabled on "+page+" page");
				}
			} else {
				System.out.println(elementName + ": is not found");
				extlogger.log(LogStatus.FAIL, elementName + ": is not found on "+page+" page");
				String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
				extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
			}
		} catch (Exception e) {
			System.out.println("Click Method Exception Message: " + e);
			extlogger.log(LogStatus.FAIL, elementName + ": is not found on "+page+" page");
			extlogger.log(LogStatus.FAIL, e);
			String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
			extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
		}
	}
	
	
	// To perform actions click on given element
		public static void hoverClick(WebDriver driver, String elementName, String page, ExtentTest extlogger) {

			try {

				WebDriverWait wait = new WebDriverWait(driver, 60L);
				wait.until(ExpectedConditions.presenceOfElementLocated(Locator.getBy(page, elementName, extlogger)));
				WebElement element = driver.findElement(Locator.getBy(page, elementName, extlogger));
				if (isAvalable(element)) {
					if (element.isDisplayed() && element.isEnabled()) {
						Actions act = new Actions(driver);
						act.moveToElement(element).click().build().perform();
						extlogger.log(LogStatus.PASS, "Successfully clicked on : " + elementName);
					} else {
						System.out.println(elementName + " is not displayed or enabled on "+page+" page");
						extlogger.log(LogStatus.FAIL, elementName + "  is not displayed or on "+page+" page");
					}
				} else {
					System.out.println(elementName + " is not found on "+page+" page");
					extlogger.log(LogStatus.FAIL, elementName + " is not displayed or enabled on "+page+" page");
					String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
					extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
				}
			} catch (Exception e) {
				System.out.println("Click Method Exception Message: " + e);
				extlogger.log(LogStatus.FAIL, elementName + ": is not found on "+page+" page");
				extlogger.log(LogStatus.INFO, e);
				String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
				extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
			}
		}
	
	public boolean assertText(WebElement element, String text) {
		try{
			wait.until(ExpectedConditions.visibilityOf(element));	
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));	
			Assert.assertEquals(element.getText().replaceAll("\\n", " "), text);
			return true;

		}catch(TimeoutException e){

		}catch(WebDriverException e){

		}catch(Exception e){
		} finally{
		
		}
		return false;

	}	

	
	// To check given Element is available or not
		public static boolean isAvalable(WebElement element) {

			try {
				element.getTagName();
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}

		}
	
	/// To Enter Data in Text Box 

	public   boolean EnterData(WebElement element, String data){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10) ;
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.sendKeys(data);

		if(!data.equals("")){
				wait.until(ExpectedConditions.attributeContains(element, "Value", data));
			}
			else{
				element.clear();
			}
			return true;
		}
		catch(TimeoutException e){
		}
		catch(WebDriverException e){
		}
		catch(Exception e){
		}finally{
		}
		return false;
	}

	/// To Clear data 

	public  boolean clearData(WebElement element, String data){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			return true;
		}
		catch(TimeoutException e){
		}
		catch(WebDriverException e){
		}
		catch(Exception e){
		}finally{
		}
		return false;
	}

	///Select element Using Index From DropDown

	public boolean selectusingIndex(WebElement element, int Index) {
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			new Select(element).selectByIndex(Index);
			return true;

		}catch(TimeoutException e){

		}catch(WebDriverException e){

		}catch(Exception e){
		}finally{
		}
		return false;

	}
	
	///select element using text from dropdown
	
	public boolean selectUsingText(WebElement element, int Text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			new Select(element).selectByIndex(Text);
			return true;
		} catch (TimeoutException e) {
		} catch (WebDriverException e) {
		} catch (Exception e) {
		} finally {
			//waitForPageLoad();
		}
		return false;
	}

	///Select value From DropDown

	public boolean selectByValue(WebElement element, String value) {
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			new Select(element).selectByValue(value);
			return true;

		}catch(TimeoutException e){

		}catch(WebDriverException e){

		}catch(Exception e){
		}finally{
		}

		return false;

	}	
	///Select Text From DropDown

	public boolean selectByText(WebElement element, String text) {
		try{
			//System.out.println("select123");
			wait.until(ExpectedConditions.visibilityOf(element));
			//System.out.println("select123");
			new Select(element).selectByVisibleText(text);
			//System.out.println("select123");

			
			return true;

		}catch(TimeoutException e){

		}catch(WebDriverException e){

		}catch(Exception e){
		}finally{
		}

		return false;

	}	

	/// To Verify element is Displayed

	public boolean assertIsDisplayed(WebElement element)
	{
		try
		{
			Assert.assertTrue(element.isDisplayed());
			return true;
		}
		catch(TimeoutException e)
		{
		}catch(WebDriverException e){

		}catch(Exception e) {

		}
		return false;

	}

	/// To Verify element is  Not Displayed

	public boolean assertIsNotDisplayed(WebElement element)
	{
		try
		{
			Assert.assertFalse(element.isDisplayed());
			return true;
		}
		catch(TimeoutException e)
		{
		}catch(WebDriverException e){

		}catch(Exception e) {

		}
		return false;

	}

	/// To Verify element is Selected

	public boolean assertIsSelected(WebElement element)
	{
		try
		{
			Assert.assertTrue(element.isSelected());
			return true;
		}
		catch(TimeoutException e)
		{
		}catch(WebDriverException e){

		}catch(Exception e) {

		}
		return false;

	}

	/// To Verify element is Not Selected

	public boolean assertIsNotSelected(WebElement element)
	{
		try
		{
			Assert.assertFalse(element.isSelected());
			return true;
		}
		catch(TimeoutException e)
		{
		}catch(WebDriverException e){

		}catch(Exception e) {

		}
		return false;

	}



	public static void Alerts() {

		Alert a= driver.switchTo().alert();
		a.accept();
		a.dismiss();	
	}



	public static String removeSpecialCharacters(String name) {

		String plainName=name.replaceAll(":", "").replaceAll(">", "").replaceAll("/", "");
		return plainName;

	}


	//for window handles

	public static void windowhandles(String window1,String window2){

		Set<String> handler=driver.getWindowHandles();
		Iterator<String> it=handler.iterator();
		window1=it.next();
		window2=it.next();
		driver.switchTo().window(window2);
	}

	//for screen shot

	/*public static void ScreenShot(String filename) 
	{
		try {
			File file =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(file,new File(screenshotDir+File.separator+filename+".jpg"));
		}
		catch(Exception e)
		{
			System.out.println("unable to take screen shot for :"+filename   +" due to :" +e);
		}
	}*/


	//for  auto it

	public static void Autoit(String path) throws IOException
	{

		Runtime.getRuntime().exec(path);  		

	}


	//for thread.sleep

	public static void Threadsleep() throws InterruptedException
	{

		Thread.sleep(3000);

	}
	
	
	
	
	
	
	
	public static void enterText(WebElement element, String textToenter)
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10) ;
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isEnabled())
			{
				element.sendKeys(textToenter);
			}
			else
			{
				System.out.println("element is not enabled");
			}
		}
			catch(Exception e)
			{
				System.out.println("unable to given text:"+textToenter+ "in element.Exception;" +e);
			}
			
	}
	
	
		//To take screen shot
		public static String takeScreenShot(WebDriver driver, String screenShotName){
			String screenShotPath="";
			try{
				 screenShotPath = System.getProperty("user.dir")+File.separator+"Reports"+File.separator+BaseSetUp.repoDate+File.separator +"Screenshots"+File.separator+screenShotName+"_"+DateForUse.getDateTimeAsFormat()+".png";
				 TakesScreenshot ts = (TakesScreenshot)driver;
			     File source = ts.getScreenshotAs(OutputType.FILE);
			     File destination = new File(screenShotPath);
			     FileUtils.copyFile(source, destination);
			     return screenShotPath;
			}
			catch(Exception e){
				
				System.out.println("Unable to take screen shot due to "+e);
				return screenShotPath;
			}
		}
		
		//To verify element is exist
		public static boolean verifyExist(WebDriver driver, String elementName, String page, ExtentTest extlogger){
			boolean verifyStatus= false;
			
			try{
				WebDriverWait wait = new WebDriverWait(driver, 10L);
				By objLocator=Locator.getBy(page, elementName, extlogger);
				wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
				List <WebElement> elementList=driver.findElements(objLocator);
				if(elementList.size()>0){
					verifyStatus=true;
				}
				
			}
			catch(Exception e){
				System.out.println("VeriftExist Method Exception Message: " + e);
				extlogger.log(LogStatus.FAIL,elementName+" is not found on "+page+" page");
				extlogger.log(LogStatus.INFO,e);
				String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
				extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
				verifyStatus=false;
			}
			return verifyStatus;
			
		}
		
		
		//To get text
		public static String getText(WebDriver driver,String elementName,String page, ExtentTest extlogger){
			String text=null;
			try{
			WebDriverWait wait = new WebDriverWait(driver, 60L);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator.getBy(page, elementName, extlogger)));
			WebElement element = driver.findElement(Locator.getBy(page, elementName, extlogger));
			if (element.isDisplayed() && element.isEnabled()) {
				text=element.getText();	
				if(text.equalsIgnoreCase("")){
					text=element.getAttribute("innerHtml");
				}
			 }
			else{
				System.out.println(elementName + " is not displayed or enabled on "+page+" page");
				extlogger.log(LogStatus.FAIL, elementName + "  is not displayed or on "+page+" page");
			}
			return text;
			}	
			catch(Exception e){
				System.out.println("Get text method exception "+e);
				extlogger.log(LogStatus.FAIL, elementName + ": is not found on "+page+" page");
				extlogger.log(LogStatus.INFO, e);
				String screenShotPath = WrapperMethods.takeScreenShot(driver, elementName);
				extlogger.log(LogStatus.FAIL, "Snapshot below: "+extlogger.addScreenCapture(screenShotPath));
				return text;	
			}
			
		}
   
		
		
		//To scroll page to the element
		public static void pageScrollDown(WebDriver driver,String elementName,String page,ExtentTest extlogger)

		{
			try
			{
				WebElement element = driver.findElement(Locator.getBy(page, elementName, extlogger));
//				if (element.isDisplayed() || element.isEnabled()) {
					JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("arguments[0].scrollIntoView(true);", element);
					extlogger.log(LogStatus.INFO, "Page is scrolled To down to view the element");	
//				 }
//				else{
//					System.out.println(elementName + " is not displayed or enabled on "+page+" page");
//					extlogger.log(LogStatus.FAIL, elementName + "  is not displayed or on "+page+" page");
//				}
			
		 }

			catch (Exception e) {
				System.out.println("Scroll method exception "+e);
				extlogger.log(LogStatus.ERROR, "Unable to Scroll the page. Reason:"+e);
			}
		}

	////// To locate Web Element    
	
	/*public WebElement locateElement(String locator,String locValue) {
		
		try {
			switch(locator) {
			case("id"):
				return driver.findElement(By.id(locValue));
			
			case("name"):
				return driver.findElement(By.name(locValue));
			
			case("class"):
				return driver.findElement(By.className(locValue));

			case("link"):
				return driver.findElement(By.linkText(locValue));

			case("tag"):
				return driver.findElement(By.tagName(locValue));

			case("xpath"):
				return driver.findElement(By.xpath(locValue));
			default:
				return null;
			}
		}catch(NoSuchElementException e) {
			
		}catch(WebDriverException e) {
			
		}finally {
			//waitforpageload();
	    }
		return null;
	    }*/
	
	
//////To locate Web Element    
	
	/*public List<WebElement> locateElements(String locator,String locValue) {
		
		try {
			switch(locator) {
			case("id"):
				return driver.findElements(By.id(locValue));
			
			case("name"):
				return driver.findElements(By.name(locValue));
			
			case("class"):
				return driver.findElements(By.className(locValue));

			case("link"):
				return driver.findElements(By.linkText(locValue));

			case("tag"):
				return driver.findElements(By.tagName(locValue));

			case("xpath"):
				return driver.findElements(By.xpath(locValue));
			default:
				return null;
			}
		}catch(NoSuchElementException e) {
			
		}catch(WebDriverException e) {
			
		}finally {
			//waitforpageload();
	    }
		return null;
	    }*/

	
	
	
	
	

}





















		











