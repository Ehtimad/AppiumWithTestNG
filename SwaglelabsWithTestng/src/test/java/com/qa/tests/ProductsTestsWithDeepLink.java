package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductPage;
import com.qa.utils.TestUtils;
import com.qa.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ProductsTestsWithDeepLink extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	MenuPage menuPage;
	ProductPage productPage;
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
	public void beforeMethod(Method m) throws InterruptedException {
		homePage = new HomePage();
		utils.log().info("***** Started test: " + m.getName() + " *****");
	}

	@AfterMethod
	public void afterMethod(Method m) throws InterruptedException {
		utils.log().info("***** Finished test: " + m.getName() + " *****");

		homePage.pressMenuBtn();
		menuPage.pressLogoutBtn();	
	}

	String actualTxt;	

	@Test
	public void productPage() throws InterruptedException {

		OpenAppWith("swaglabs://swag-overview/0,5");

		menuPage = homePage.pressMenuBtn();

		menuPage.pressAllItemsBtn();

		homePage.scroolToTermsTxt("up");

		homePage.scroolToProductBtn("down");
		
		
		productPage=homePage.pressProductBtn();		
		String expectedProductName = productPage.getProductNameTxt();	

		homePage.scroolToTermsTxt("up");
		
		homePage.pressAddToCartBtn();					
		String expectedProductPrice = productPage.getPriceTxt();		
		homePage.pressCardBtn();		

		homePage.scroolToremoveBtn("up");
		
		productPage.pressCheckoutBtn();


		productPage.clickFirstNameTxtField();
		productPage.clearFirstNameField();
		productPage.typeFirstName(testData.getJSONObject("allText").getString("firstName"));		

		pressSpace();

		productPage.typeFirstName(testData.getJSONObject("allText").getString("secondFirstName"));

		pressDel(testData.getJSONObject("allText").getString("secondFirstName"));

		productPage.clickLastNameTxtField();
		productPage.clearLastNameField();
		productPage.typeLastName(testData.getJSONObject("allText").getString("lastName"));

		productPage.clickZipTxtField();
		productPage.clearZipField();
		productPage.typeZip(testData.getJSONObject("allText").getString("zip"));
		pressHideKeyboard();

		productPage.pressContinueBtn();		

		String actualProductName = productPage.getProductNameTxt();	

		productPage.scroolToProductPriceFinishPageTxt("up");		

		String actualProductPrice = productPage.getProductPriceFinishPageTxt().split(":")[1].trim();
		
		Assert.assertEquals(expectedProductName, actualProductName);

		Assert.assertEquals(expectedProductPrice, actualProductPrice);		

		String taxPrice = productPage.getProductTaxPriceFinishPageTxt().split(":")[1].trim();	
		utils.log().info("taxPrice: " + taxPrice);	

		String actualAllPrice = productPage.getProductTotalPriceFinishPageTxt().split(":")[1].trim();	
		utils.log().info("actualAllPrice: " + actualAllPrice);	

		String expectedAllPrice = "$" + String.valueOf(Float.parseFloat(actualProductPrice.substring(1,actualProductPrice.length())) + Float.parseFloat(taxPrice.substring(1,taxPrice.length())));
		
		Assert.assertEquals(expectedAllPrice, actualAllPrice);

		homePage.scroolToTermsTxt("up");
	}
}
