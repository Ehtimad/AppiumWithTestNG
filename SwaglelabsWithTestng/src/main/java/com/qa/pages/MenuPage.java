package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuPage extends BaseTest{
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-ALL ITEMS\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-ALL ITEMS")	
	private MobileElement allItemsBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='test-WEBVIEW']")
	@iOSXCUITFindBy(id="test-WEBVIEW")
	private MobileElement webViewBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-GEO LOCATION\"]/android.widget.TextView")
	@iOSXCUITFindBy(id="test-GEO LOCATION")
	private MobileElement geoLocationBtn;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGOUT\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-LOGOUT")
	private MobileElement logOutBtn;	

	public ProductPage pressAllItemsBtn() throws InterruptedException {
		click(allItemsBtn, "click all items button");
		return new ProductPage();
	}
	
	public WebViewPage pressWebViewBtn() throws InterruptedException {
		click(webViewBtn, "click web view button");
		return new WebViewPage();
	}
	
	public GeoLocationPage pressGeoLocationBtn() throws InterruptedException {
		click(geoLocationBtn, "click geo location button");
		return new GeoLocationPage();
	}
	
	public LoginPage pressLogoutBtn() throws InterruptedException {
		click(logOutBtn, "click Logout button");
		return new LoginPage();
	}	
}