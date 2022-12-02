package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{	
	@AndroidFindBy (accessibility = "test-LOGIN") 
	@iOSXCUITFindBy(id="test-LOGIN")
	private MobileElement loginBtn;
	
	@AndroidFindBy (accessibility = "test-Username")
	@iOSXCUITFindBy(id="test-Username")
	private MobileElement userNameTxtField;
	
	@AndroidFindBy (accessibility = "test-Password")
	@iOSXCUITFindBy(id="test-Password")
	private MobileElement passwordTxtField;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc='test-standard_user']")
	@iOSXCUITFindBy(id="test-standard_user")
	private MobileElement standardUserBtn;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	@iOSXCUITFindBy(id="test-Error message") 
	private MobileElement errorMsgTxt;	

	public HomePage pressLoginBtn() throws InterruptedException {
//		click(loginBtn);
		click(loginBtn, "press login button");
		return new HomePage();
	}
	
	public HomePage login(String userName, String password) throws InterruptedException {		
		clickUserNameTxtField();
		clearUserNameTxtField();
		enterUserName(userName);
		clickPasswordTxtField();
		clearPasswordTxtField();
		enterPassword(password);
		pressLoginBtn();		
		
		return new HomePage();
	}

	public LoginPage chooseUsertype() throws InterruptedException {	
		click(standardUserBtn, "press standard user button");
		return this;
	}
	
	public LoginPage scroolToStandartBtn(String direction) throws InterruptedException {
		scrollToElement(standardUserBtn, direction, "scrool " + direction + " to standard user button");
		return this;
	}
	
	public LoginPage scroolTologinBtn(String direction) throws InterruptedException {
		scrollToElement(loginBtn, direction, "scrool " + direction + " to login button");
		return this;
	}


	public LoginPage enterUserName(String userName) throws InterruptedException {
		sendKeys(userNameTxtField,userName, "enter userName is " + userName);
		return this;
	}
	
	public LoginPage clickUserNameTxtField() throws InterruptedException {
		click(userNameTxtField, "click user name text field");
		return this;
	}
	
	public LoginPage typeUserName(String userName) throws InterruptedException {
//		typeWithKeyboard(userNameTxtField,txt);
		sendKeys(userNameTxtField,userName, "type userName is " + userName);
		return this;
	}
	
	public LoginPage clickPasswordTxtField() throws InterruptedException {
		click(passwordTxtField, "click password text field");
		return this;
	}
	
	public LoginPage typePassword(String password) throws InterruptedException {
//		typeWithKeyboard(passwordTxtField,password, "type password is " + password);
		sendKeys(passwordTxtField,password, "type password is " + password);
		return this;
	}

	public LoginPage enterPassword(String password) throws InterruptedException {
		sendKeys(passwordTxtField,password, "enter password is " + password);
		return this;
	}

	public String getErrorTxt() throws InterruptedException {
		String err = getText(errorMsgTxt, "actually text is - ");
		return err;
	}
	
	public LoginPage clearUserNameTxtField() throws InterruptedException {
		clearField(userNameTxtField, "clear user name text field");
		return this;
	}
	
	public LoginPage clearPasswordTxtField() throws InterruptedException {
		clearField(passwordTxtField, "clear user password text field");
		return this;
	}
}