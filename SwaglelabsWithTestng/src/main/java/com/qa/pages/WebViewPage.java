package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class WebViewPage extends BaseTest{		
	@AndroidFindBy (xpath="//android.widget.EditText") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeTextField")	
	private MobileElement enterUrlField;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='test-GO TO SITE']")
	@iOSXCUITFindBy(id="test-GO TO SITE")
	private MobileElement goToSiteBtn;
	
	@AndroidFindBy (id = "com.swaglabsmobileapp:id/action_bar_root") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"Google\"]")	
	private MobileElement webPageCommonId;
	
	@AndroidFindBy (className = "android.widget.Image") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeImage[@name=\"Google\"]")	
	private MobileElement googleLogoId;

	public WebViewPage enterUrl(String url) throws InterruptedException {
		sendKeys(enterUrlField,url, "enter url is " + url);
		return this;
	}

	public WebViewPage pressGoToSiteBtn() throws InterruptedException {
		click(goToSiteBtn, "click go to site button");
		return this;
	}

	public WebViewPage pressWebPageCommonId() throws InterruptedException {
		click(webPageCommonId, "click web page common Id");
		return this;
	}

	public String getGoogleTxt() throws InterruptedException {
		String err = getText(googleLogoId, "actually text is - ");
		return err;
	}
	
	public WebViewPage clickEnterUrlField() throws InterruptedException {
		click(enterUrlField, "click enter url field");
		return this;
	}
	
	public WebViewPage clearEnterUrlField() throws InterruptedException {
		click(enterUrlField, "clear enter url field");
		return this;
	}
}