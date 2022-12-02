package com.qa;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Driver;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;



public class BaseTest {
	protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>> ();

	protected static ThreadLocal<String> platform = new ThreadLocal<String>();
	protected static ThreadLocal<String> platformVersion = new ThreadLocal<String>();
	protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal<String> packageName = new ThreadLocal<String>();
	private static AppiumDriverLocalService server;

	TestUtils utils = new TestUtils();
	

    public static AppiumDriver getDrivers(){
        return driver.get();
    }

    public static void setDrivers(AppiumDriver driver1){
        driver.set(driver1);
    }

	public AppiumDriver getDriver() {	  
		return driver.get();		
	}

	public void setDriver(AppiumDriver driver2) {	  
		driver.set(driver2);		
	}

	public Properties getProps() {	  
		return props.get();		
	}

	public void setProps(Properties props2) {	  
		props.set(props2);		
	}

	public HashMap<String, String> getStrings() {	  
		return strings.get();		
	}

	public void setStrings(HashMap<String, String> strings2) {	  
		strings.set(strings2);		
	}
	
	public static String getPlatforms() {	  
		return platform.get();		
	}

	public static void setPlatforms(String platform2) {	  
		platform.set(platform2);		
	}

	public String getPlatform() {	  
		return platform.get();		
	}

	public void setPlatform(String platform2) {	  
		platform.set(platform2);		
	}

	public String getPlatformVersion() {	  
		return platformVersion.get();		
	}

	public void setPlatformVersion(String platformVersion2) {	  
		platformVersion.set(platformVersion2);		
	}

	public String getDateTime() {	  
		return dateTime.get();		
	}

	public void setDateTime(String dateTime2) {	  
		dateTime.set(dateTime2);		
	}

	public String getDeviceName() {	  
		return deviceName.get();		
	}

	public void setDeviceName(String deviceName2) {	  
		deviceName.set(deviceName2);		
	}
	
	public static String getPackageName() {	  
		return packageName.get();		
	}

	public static void setPackageName(String packageName2) {	  
		packageName.set(packageName2);		
	}
	

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	} 	

	@BeforeMethod
	public void beforeMethod(ITestResult result) {	
		((CanRecordScreen) getDriver()).startRecordingScreen();
	} 	

	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws Exception{
		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

		Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		String dirPath = "Videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + 
				File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

		File videoDir = new File(dirPath);
		synchronized(videoDir) {
			if(!videoDir.exists()) {
				videoDir.mkdirs();
			}
		}
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
			stream.write(Base64.decodeBase64(media));
			stream.close();
			utils.log().info("Video path: " + videoDir +File.separator + result.getName() + ".mp4");
		} catch (Exception e) {
			utils.log().error("Error during vidoe capture: " + e.toString());
		}finally{
			if(stream != null) {
				stream.close();
			}
		}
	}

	//	For the kill port at the cmd 
	//	lsof -P | grep ':4723' | awk '{print $2}' | xargs kill -9

	@BeforeSuite
	public void beforeSuite() throws Exception, Exception{
		ThreadContext.put("ROUTINGKEY", "ServerLogs");
		server = getAppiumService();

		if(!checkIfAppiumServerIsRunning(4723)) {
			server.start();
			server.clearOutPutStreams();
			utils.log().info("Appium server started");
		}else {
			utils.log().info("Appium server already running");
		}

	}

	public boolean checkIfAppiumServerIsRunning(int port) throws Exception{
		boolean isAppiumServerRunning = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		}catch(IOException e){
			System.out.println("1");
			isAppiumServerRunning=true;
		}finally {
			socket=null;
		}
		return isAppiumServerRunning;
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite(){
		if(server.isRunning()) {
			server.stop();
			utils.log().info("Appium server stoped");
		}
	}

	public AppiumDriverLocalService getAppiumService(){
		HashMap <String, String> environment = new HashMap <String, String>();
		environment.put("PATH", 
				"/usr/local/share/android-sdk/platform-tools:/Library/scala3-3.1.3/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin:/Applications/apache-maven-3.8.6/bin:/usr/local/share/android-sdk/platform-tools/:/Library/Java/JavaVirtualMachines/jdk1.8.0_333.jdk/Contents/Home:/usr/local/share/android-sdk:/usr/local/share/android-sdk/tools:/usr/local/share/android-sdk/tools/bin:/usr/local/share/android-sdk/emulator" + System.getenv("PAHT"));
		environment.put("ANDROID_HOME", "/Users/ehtimad/Library/Android/sdk");

		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File ("/usr/local/bin/node"))
				.withAppiumJS(new File ("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				.usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withEnvironment(environment)
				.withLogFile(new File("ServerLogs/server.log")));				
	}

	@Parameters({"emulator", "platformName", "platformVersion", "deviceName", "udId", "avd", "avdLaunchTimeout", "unlockType", "unlockKey", "newCommandTimeOut", "systemPort", "chromeDriverPort", "wdaLocalPort", 	"webkitDebugProxyPort"})
	@BeforeTest
	public void beforeTest(String emulator, String platformName, String platformVersion, String deviceName, 	String udId, @Optional("androidOnly") String avd, @Optional("androidOnly") String avdLaunchTimeout, String unlockType, String unlockKey, @Optional("androidOnly") String newCommandTimeOut, @Optional("androidOnly") String systemPort,  @Optional("androidOnly") String chromeDriverPort, 
			@Optional("iOSOnly") String wdaLocalPort, @Optional("iOSOnly") String webkitDebugProxyPort) throws Exception {

		setPlatform(platformName);
		setDateTime(utils.dateTime());
		setPlatformVersion(platformVersion);
		setDeviceName(deviceName);

		URL url; 

		InputStream inputStream = null;
		InputStream stringsis = null;
		Properties props = new Properties();
		AppiumDriver driver;
		setPackageName(props.getProperty("androidAppPackage"));

		String strFile = "Logs" + File.separator + getPlatform() + "_" + getDeviceName();
		File logFile = new File(strFile);
		if(!logFile.exists()) {
			logFile.mkdirs();
		}		
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("Log path: " + strFile);

		try {		
			props = new Properties();
			String propFileName = "config.properties";
			String xmlFileName="strings/strings.xml";

			utils.log().info("Load prop file name: " + propFileName);			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);
			setProps(props);

			utils.log().info("Load xml file name: " + xmlFileName);
			stringsis = getClass().getClassLoader().getSystemResourceAsStream(xmlFileName);
			setStrings(utils.parseStringXml(stringsis));

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName); 
			cap.setCapability(MobileCapabilityType.UDID, udId);			
			url = new URL (props.getProperty("appiumUrl"));	

			utils.log().info("Platform name is : " + platformName);

			if((platformName).equalsIgnoreCase("Android")) {
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));	
				cap.setCapability("appPackage", props.getProperty("androidAppPackage"));
				cap.setCapability("appActivity", props.getProperty("androidAppActivity"));

				int systemPorts=Integer.parseInt(systemPort);  
				int chromeDriverPorts=Integer.parseInt(chromeDriverPort); 
				cap.setCapability("systemPort", systemPorts);	
				cap.setCapability("chromeDriverPort", chromeDriverPorts);	

				cap.setCapability("unlockType", unlockType);
				cap.setCapability("unlockKey", unlockKey);

				String andAppPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
						+ File.separator + "resources" + File.separator + "app" + File.separator + "AndroidDemoApp" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
				utils.log().info("Android applicatin url is: " + andAppPath);
				cap.setCapability(MobileCapabilityType.APP, andAppPath);

				boolean appiumNoReset=Boolean.parseBoolean(props.getProperty("appiumNoReset")); 
				cap.setCapability(MobileCapabilityType.NO_RESET, appiumNoReset);

				int newCommandTimeOuts=Integer.parseInt(newCommandTimeOut); 			
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, newCommandTimeOuts);

				if(emulator.equalsIgnoreCase("true")) {
					cap.setCapability("avd", avd);
					int avdLaunchTimeouts=Integer.parseInt(avdLaunchTimeout); 
					cap.setCapability("avdLaunchTimeout", avdLaunchTimeouts);
				}

				driver = new AndroidDriver(url,cap);	
			}		
			else if((platformName).equalsIgnoreCase("iOS")){
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("iOSAutomationName"));
				cap.setCapability("bundleId", props.getProperty("iOSBundleId"));

				int wdaLocalPorts=Integer.parseInt(wdaLocalPort);  
				cap.setCapability("wdaLocalPort", wdaLocalPorts);	
				int webkitDebugProxyPorts=Integer.parseInt(webkitDebugProxyPort); 
				cap.setCapability("chromeDriverPort", webkitDebugProxyPorts);				
				boolean appiumNoReset=Boolean.parseBoolean(props.getProperty("appiumNoReset"));					
				cap.setCapability(MobileCapabilityType.NO_RESET, appiumNoReset);
//				cap.setCapability("fullReset", true);
//				cap.setCapability("autoWebview", true);
				if(emulator.equalsIgnoreCase("false")) {
					String iosRealDeviceAppPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
						+ File.separator + "resources" + File.separator + "app" + File.separator + "IosDemoApp" + File.separator + "Debug-iphoneos" + File.separator +"iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa";
					
					utils.log().info("Ios real device applicatin url is: " + iosRealDeviceAppPath);
					cap.setCapability(MobileCapabilityType.APP, iosRealDeviceAppPath);
				}

				if(emulator.equalsIgnoreCase("true")) {
					cap.setCapability("simulatorStartupTimeout", props.getProperty("iOSEmulatorSimulatorStartupTimeout"));

					String iosEmulatorAppPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
							+ File.separator + "resources" + File.separator + "app" + File.separator + "IosDemoApp" + File.separator + "Debug-iphonesimulator" + File.separator +"iOS.Simulator.SauceLabs.Mobile.Sample.2.7.1.app";
						
					utils.log().info("Ios simulator applicatin url is: " + iosEmulatorAppPath);
					cap.setCapability(MobileCapabilityType.APP, iosEmulatorAppPath);
				}

				driver = new IOSDriver(url,cap);
			}
			else {
				throw new Exception ("Invalid platform: " + platformName);	
			}
			setDriver(driver);
			utils.log().info("Driver initialized: " + driver);
		}	
		catch(Exception e) {
			  utils.log().fatal("Driver initialization failure. ABORT!!!\n" + e.toString());
			throw e;
		}finally {
			if(inputStream!=null) {
				inputStream.close();
			}
			if(stringsis!=null) {
				stringsis.close();
			}
		}
	}

	public void waitForVisibility(MobileElement e) {	  
		WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));		
	}
	
	  public void waitForVisibility(WebElement e){
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
		  .withTimeout(Duration.ofSeconds(30))
		  .pollingEvery(Duration.ofSeconds(5))
		  .ignoring(NoSuchElementException.class);
		  wait.until(ExpectedConditions.visibilityOf(e));
		  }
	  
	public void click(MobileElement e, String msg) throws InterruptedException {
		waitForVisibility(e);
				utils.log().info(msg);
				ExtentReport.getTest().log(Status.INFO, msg);
		e.click();
	}

	public void clickVisible(MobileElement e, String msg) throws InterruptedException {		
		if (isElementDisplayed(e)==true) {
						utils.log().info(msg);
						ExtentReport.getTest().log(Status.INFO, msg);
			e.click();
		}				
	}

	public boolean isElementDisplayed(final MobileElement e){
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);		
		try{
			WebDriverWait wait = new WebDriverWait(getDriver(), 2);			
			return wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					if(e.isDisplayed()) {
						return true;
					}
					return false;
				}
			});
		}catch(Exception ex){
			return false;
		}
	}

	public void scrollToElement(MobileElement e, String direction, String msg) throws InterruptedException
	{   
		for(int i=0;i<3;i++) {
			if(isElementDisplayed(e)) {
				break;
			}else {				
								utils.log().info(msg);
								ExtentReport.getTest().log(Status.INFO, msg);
				if(direction.equalsIgnoreCase("up")) {						
					scroolUsingTouchAction("up", msg);
				}
				else if(direction.equalsIgnoreCase("down")){
					scroolUsingTouchAction("down", msg);				
				}
			}
		}

	}

	public void scroolUsingTouchAction(String direction, String msg) throws InterruptedException {
		Dimension size = getDriver().manage().window().getSize();
		int x=(int)(size.getWidth()/2);
		int start_y=0;
		int end_y=0;

				utils.log().info(msg);
				ExtentReport.getTest().log(Status.INFO, msg);

		if(direction.equalsIgnoreCase("up")) {
			start_y = (int)(size.getHeight()*0.8);
			end_y = (int)(size.getHeight()*0.2);
		}
		else if(direction.equalsIgnoreCase("up")){			
			start_y = (int)(size.getHeight()*0.2);
			end_y = (int)(size.getHeight()*0.8);	
		}
		TouchAction t = new TouchAction( getDriver());		
		t.press(PointOption.point(x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))		
		.moveTo(PointOption.point(x, end_y)).release().perform();	
	}

	public String getAttribute(MobileElement e, String attribute) throws InterruptedException {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}

	public String getText(MobileElement e, String msg) throws InterruptedException {
		String txt = null;
		waitForVisibility(e);

		if((getPlatform()).equalsIgnoreCase("Android")) {
			txt = getAttribute(e,"text");

						utils.log().info(msg + txt);
						ExtentReport.getTest().log(Status.INFO, msg + txt);
			return txt;
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {
			txt = getAttribute(e,"label");	

						utils.log().info(msg + txt);
						ExtentReport.getTest().log(Status.INFO, msg + txt);
			return txt;			
		}

		return txt;
	}

	public String getLocationLatitudeLongitude(MobileElement e, String attribute) throws InterruptedException {
		Thread.sleep(3000);
		waitForVisibility(e);
		Thread.sleep(5000);			
		return e.getAttribute(attribute);
	}

	public void typeWithKeyboard(MobileElement e, String txt, String msg) throws InterruptedException {	
		waitForVisibility(e);

				utils.log().info(msg);
				ExtentReport.getTest().log(Status.INFO, msg);	

		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).getKeyboard().pressKey(txt);
		}
		else if((getPlatform()).equalsIgnoreCase("iOS")) {						
			for(int i=0;i<txt.length();i++) {	
				String key = String.valueOf(txt.charAt(i));
				boolean isUpperCase =
						(Character.isUpperCase(txt.charAt(i)));
				if(isUpperCase==true) {
					pressShift();
				}

				if(isNumeric(key)==true) {
					pressNumbers();
				}

				if(key.equalsIgnoreCase("{") || key.equalsIgnoreCase("}") || key.equalsIgnoreCase("[") || key.equalsIgnoreCase("]") 
						|| key.equalsIgnoreCase("_") || key.equalsIgnoreCase("#") || key.equalsIgnoreCase("%")) {
					pressNumbers();
					pressSymbols();	
				}

				getDriver().findElement(MobileBy.iOSNsPredicateString("label == \"" + key + "\"")).click();	

				if(key.equalsIgnoreCase("{") || key.equalsIgnoreCase("}") || key.equalsIgnoreCase("[") || key.equalsIgnoreCase("]") 
						|| key.equalsIgnoreCase("_") || key.equalsIgnoreCase("#") || key.equalsIgnoreCase("%"))  {					
					pressLetters();
				}	

				if(isNumeric(key)==true) {				
					pressLetters();
				}
			}
		}
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void pressLetters() {
		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.SPACE));	
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "letters" + "\"")).click();	
		}	
	}

	public void pressShift() {
		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.SHIFT_LEFT));	
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "shift" + "\"")).click();	
		}
	}

	public void assertText(String actualTxt, String expectedTxt) {
		String txt = "actualTxt: " + actualTxt + ", expectedTxt: " + expectedTxt;

				utils.log().info(txt);
				ExtentReport.getTest().log(Status.INFO, txt);

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actualTxt, expectedTxt);
		sa.assertAll();	
	}

	public void clearField(MobileElement e, String msg) throws InterruptedException {	
		waitForVisibility(e);
				utils.log().info(msg);
				ExtentReport.getTest().log(Status.INFO, msg);	
		e.clear();
	}

	public void pressSpace() {
				utils.log().info("press space button");
				ExtentReport.getTest().log(Status.INFO, "press space button");	

		if((getPlatform()).equalsIgnoreCase("Android")) {			
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.SPACE));	
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "space" + "\"")).click();	
		}
	}

	public void pressNumbers() {
		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.SPACE));	
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "numbers" + "\"")).click();	
		}
	}

	public void pressSymbols() {
		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.SPACE));	
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "symbols" + "\"")).click();	
		}
	}

	public void pressDel(String txt) {
				utils.log().info("press delete button");
				ExtentReport.getTest().log(Status.INFO, "press delete button");

		if((getPlatform()).equalsIgnoreCase("Android")) {
			for(int i=0;i<txt.length();i++) {
				((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.DEL));
			}
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {
			for(int i=0;i<txt.length();i++) {										
				getDriver().findElement(MobileBy.iOSNsPredicateString("label CONTAINS \"" + "delete" + "\"")).click();
			}
		}
	}

	public void pressHideKeyboard() {
				utils.log().info("press hide keyboard button");
				ExtentReport.getTest().log(Status.INFO, "press hide keyboard button");

		if((getPlatform()).equalsIgnoreCase("Android")) {			
			((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
		}else if((getPlatform()).equalsIgnoreCase("iOS")) {
			getDriver().findElement(MobileBy.iOSNsPredicateString("label == \"" + "return" + "\"")).click();		
		}
	}

	public void sendKeys(MobileElement e, String txt, String msg) throws InterruptedException {
		waitForVisibility(e);

				utils.log().info(msg);
				ExtentReport.getTest().log(Status.INFO, msg);

		if((getPlatform()).equalsIgnoreCase("Android")) {
			((AndroidDriver) getDriver()).getKeyboard().pressKey(txt);
		}
		else if((getPlatform()).equalsIgnoreCase("iOS")) {	
			e.sendKeys(txt);
		}
	}
	
	public static void OpenAppWith(String url){
		AppiumDriver driver = getDrivers();

		if(Objects.requireNonNull(getPlatforms()).equalsIgnoreCase("Android")){
			HashMap<String, String> deepUrl = new HashMap<String, String>();
			deepUrl.put("url", url);
			deepUrl.put("package", getPackageName());
			driver.executeScript("mobile: deepLink", deepUrl);
		}else {	
			driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
			
			driver.activateApp("com.apple.mobilesafari");
			
			By urlFld = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' && label CONTAINS 'Address'");
			By openBtn = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");      

			WebDriverWait wait = new WebDriverWait(driver, 10);                

			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).sendKeys("Test" + "\uE007");
			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).sendKeys(url + "\uE007");
			wait.until(ExpectedConditions.visibilityOfElementLocated(openBtn)).click();			
		}
	}

	
	public void closeApp() {		
		((InteractsWithApps) getDriver()).closeApp();
	}

	public void launchApp() {		
		((InteractsWithApps) getDriver()).launchApp();
	}

	public void waitTime() throws InterruptedException {
		Thread.sleep(2000);
	}

	@AfterTest
	public void afterTest() {		
		getDriver().quit();
	}

}
