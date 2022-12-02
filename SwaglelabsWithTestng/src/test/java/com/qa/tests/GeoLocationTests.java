package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductPage;
import com.qa.pages.WebViewPage;
import com.qa.utils.TestUtils;
import com.qa.pages.GeoLocationPage;
import com.qa.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class GeoLocationTests extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	MenuPage menuPage;
	GeoLocationPage geoLocationPage;
	TestUtils utils = new TestUtils();

	JSONObject loginUsers;	

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream dataIsLogin = null;
		try {
			String dataFileNameLogin = "data/loginUsers.json";
			dataIsLogin = getClass().getClassLoader().getResourceAsStream(dataFileNameLogin);
			JSONTokener tokenerLogin = new JSONTokener(dataIsLogin);
			loginUsers = new JSONObject(tokenerLogin);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
	public void beforeMethod(Method m) throws InterruptedException {
		loginPage = new LoginPage();
		utils.log().info("***** Started test: " + m.getName() + " *****");

		homePage=loginPage.login(
				loginUsers.getJSONObject("successLoginStandartUser").getString("username"), 
				loginUsers.getJSONObject("successLoginStandartUser").getString("password")
				);
	}

	@AfterMethod
	public void afterMethod(Method m) throws InterruptedException {
		utils.log().info("***** Started test: " + m.getName() + " *****");	

		homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();
	}

	String actualTxt;

	@Test
	public void geoLocationPage() throws InterruptedException {	
		
		menuPage=homePage.pressMenuBtn();	
		
		geoLocationPage=menuPage.pressGeoLocationBtn();
		
		geoLocationPage.alloOnlyThisTime();
		
		String latitude= geoLocationPage.getLatitudeTxt();
		String longitude=geoLocationPage.getLongitudeTxt();
		utils.log().info("latitude: " + latitude + ", longitude: " + longitude);
	}
}
