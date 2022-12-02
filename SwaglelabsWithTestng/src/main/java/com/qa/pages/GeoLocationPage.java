package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class GeoLocationPage extends BaseTest{	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-GEO LOCATION\"]/android.widget.TextView")
	@iOSXCUITFindBy(id="test-GEO LOCATION")
	private MobileElement geoLocationBtn;
	
	@AndroidFindBy (xpath="//android.widget.TextView[@content-desc=\"test-latitude\"]")
	@iOSXCUITFindBy(id="test-latitude")
	private MobileElement latitudeTxt;
	
	@AndroidFindBy (xpath="//android.widget.TextView[@content-desc=\"test-longitude\"]")
	@iOSXCUITFindBy(id="test-longitude")
	private MobileElement longitudeTxt;
	
	@AndroidFindBy (id="com.android.permissioncontroller:id/permission_allow_one_time_button")
	@iOSXCUITFindBy(id="Allow Once")
	private MobileElement alloOnlyThisTime;
	
	
	public GeoLocationPage pressGeoLocationBtn() throws InterruptedException {
		click(geoLocationBtn, "click geo location button");
		return this;
	}

	public String getLatitudeTxt() throws InterruptedException {
		String err = getText(latitudeTxt, "actually text is - ");
		return err;
	}

	public String getLongitudeTxt() throws InterruptedException {
		String err = getText(longitudeTxt, "actually text is - ");
		return err;	
	}

	public GeoLocationPage alloOnlyThisTime() throws InterruptedException {
		click(alloOnlyThisTime, "click allow only this time button");
		return this;
	}
}