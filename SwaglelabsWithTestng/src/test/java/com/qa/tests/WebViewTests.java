package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.WebViewPage;
import com.qa.utils.TestUtils;
import com.qa.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class WebViewTests extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	MenuPage menuPage;
	WebViewPage webViewPage;
	TestUtils utils = new TestUtils();

	JSONObject testData;

	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream dataIsData = null;
		InputStream dataIsLogin = null;
		try {
			String dataFileNameData = "data/testData.json";
			dataIsData = getClass().getClassLoader().getResourceAsStream(dataFileNameData);
			JSONTokener tokenerData = new JSONTokener(dataIsData);
			testData = new JSONObject(tokenerData);

			String dataFileNameLogin = "data/loginUsers.json";
			dataIsLogin = getClass().getClassLoader().getResourceAsStream(dataFileNameLogin);
			JSONTokener tokenerLogin = new JSONTokener(dataIsLogin);
			loginUsers = new JSONObject(tokenerLogin);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(dataIsData!=null) {
				dataIsData.close();
			}
			if(dataIsLogin!=null) {
				dataIsLogin.close();
			}
		}

		closeApp();
		launchApp();
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		waitTime();
	}

	@BeforeMethod
	public void beforeMethod(Method m) throws InterruptedException{
		loginPage = new LoginPage();
		utils.log().info("***** Started test: " + m.getName() + " *****");

		homePage=loginPage.login(
				loginUsers.getJSONObject("successLoginStandartUser").getString("username"), 
				loginUsers.getJSONObject("successLoginStandartUser").getString("password")
				);
	}

	@AfterMethod
	public void afterMethod(Method m) throws InterruptedException{
		utils.log().info("***** Finished test: " + m.getName() + " *****");

		homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();	
	}

	String actualTxt;	

	@Test
	public void webViewPage() throws InterruptedException {		
		String expectedTxt=getStrings().get("web_title");	
		
		menuPage = homePage.pressMenuBtn();

		webViewPage=menuPage.pressWebViewBtn();

		webViewPage.clickEnterUrlField();

		webViewPage.clearEnterUrlField();

		webViewPage.enterUrl(testData.getJSONObject("allText").getString("urlText"));	

		webViewPage.pressGoToSiteBtn();

		webViewPage.pressWebPageCommonId();	

		actualTxt = webViewPage.getGoogleTxt();		
		assertText(actualTxt, expectedTxt);		
	}
}
