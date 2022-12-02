package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.utils.TestUtils;
import com.qa.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	MenuPage menuPage;
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
			throw e;
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
	public void beforeMethod(Method m) {
		loginPage = new LoginPage();
		utils.log().info("***** Started test: " + m.getName() + " *****");
	}

	@AfterMethod
	public void afterMethod(Method m) {
		utils.log().info("***** Finished test: " + m.getName() + " *****");
	}

	String actualTxt;

	@Test
	public void emptyUsernamePassword() throws InterruptedException {
		String expectedTxt=getStrings().get("err_empty_username_and_password");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("emptyUsernamePassword").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("emptyUsernamePassword").getString("password"));
		loginPage.pressLoginBtn();
		actualTxt = loginPage.getErrorTxt();		
		Assert.assertEquals(actualTxt, expectedTxt);
	}
	
	@Test
	public void emptyUsername() throws InterruptedException {	
		String expectedTxt=getStrings().get("err_empty_username");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("emptyUsername").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("emptyUsername").getString("password"));
		loginPage.pressLoginBtn();
		actualTxt = loginPage.getErrorTxt();		
		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@Test
	public void emptyPassword() throws InterruptedException {
		String expectedTxt=getStrings().get("err_empty_password");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("emptyPassword").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("emptyPassword").getString("password"));
		loginPage.pressLoginBtn();
		actualTxt = loginPage.getErrorTxt();		
		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@Test
	public void invalidUserName() throws InterruptedException {
		String expectedTxt=getStrings().get("invalid_username");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("invalidUserName").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUserName").getString("password"));
		loginPage.pressLoginBtn();
		actualTxt = loginPage.getErrorTxt();		
		Assert.assertEquals(actualTxt, expectedTxt);
	}

	@Test
	public void invalidPassword() throws InterruptedException {
		String expectedTxt=getStrings().get("invalid_password");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPage.pressLoginBtn();
		actualTxt = loginPage.getErrorTxt();		
		Assert.assertEquals(actualTxt, expectedTxt);
	}
	
	@Test
	public void successLoginStandartUserWithSendKeys() throws InterruptedException {
		String expectedTxt=getStrings().get("product_title");		
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.enterUserName(loginUsers.getJSONObject("successLoginStandartUser").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.enterPassword(loginUsers.getJSONObject("successLoginStandartUser").getString("password"));
		homePage = loginPage.pressLoginBtn();
		actualTxt = homePage.getTitle();		
		Assert.assertEquals(actualTxt, expectedTxt);

		menuPage = homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();		
	}

	@Test
	public void successLoginStandartUserWithTypeKeyboard() throws InterruptedException {
		String expectedTxt=getStrings().get("product_title");
		loginPage.clickUserNameTxtField();
		loginPage.clearUserNameTxtField();
		loginPage.typeUserName(loginUsers.getJSONObject("successLoginStandartUser").getString("username"));
		loginPage.clickPasswordTxtField();
		loginPage.clearPasswordTxtField();
		loginPage.typePassword(loginUsers.getJSONObject("successLoginStandartUser").getString("password"));		
		homePage = loginPage.pressLoginBtn();
		actualTxt = homePage.getTitle();		
		Assert.assertEquals(actualTxt, expectedTxt);

		menuPage = homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();			
	}
	
	@Test
	public void successLoginStandartUserPressTap() throws InterruptedException {	
		String expectedTxt=getStrings().get("product_title");	
		loginPage.scroolToStandartBtn("up");		
		loginPage.chooseUsertype();			
		loginPage.scroolTologinBtn("down");		
		homePage = loginPage.pressLoginBtn();		
		actualTxt = homePage.getTitle();			
		assertText(actualTxt, expectedTxt);	

		menuPage = homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();		
	}
}
