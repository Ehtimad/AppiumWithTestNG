package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BaseTest{	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
	private MobileElement homeTxtTitle;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")	
	@iOSXCUITFindBy(id="test-Menu")
	private MobileElement menuBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='test-ADD TO CART']") 
	@iOSXCUITFindBy(id="ADD TO CART")	
	private MobileElement addToCartBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-REMOVE\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-REMOVE")
	private MobileElement removeBtn;
	
	@AndroidFindBy (xpath="//android.widget.TextView[@content-desc='test-Item title']") 
	@iOSXCUITFindBy(xpath="(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[5]")
	private MobileElement productsBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='test-Cart']") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"test-Cart\"]/XCUIElementTypeOther")	
	private MobileElement cardBtn;
	
	@AndroidFindBy (xpath="//android.widget.TextView[@index=\"5\"]") 
	@iOSXCUITFindBy(id="Terms of Service | Privacy Policy")
	private MobileElement termsTxt;	
	
	public String getTitle() throws InterruptedException {
		String err = getText(homeTxtTitle, "actually text is - ");
		return err;
	}

	public MenuPage pressMenuBtn() throws InterruptedException {
		click(menuBtn, "click menu button");
		return new MenuPage();
	}		

	public HomePage scroolToProductBtn(String direction) throws InterruptedException {
		scrollToElement(termsTxt, direction, "scrool " + direction + " to terms text");
		return this;
	}	

	public ProductPage pressProductBtn() throws InterruptedException {
		click(productsBtn, "click product button");
		return new ProductPage();
	}

	public HomePage pressAddToCartBtn() throws InterruptedException {
		click(addToCartBtn, "click ad to card button");
		return this;
	}	

	public HomePage scroolToremoveBtn(String direction) throws InterruptedException {
		scrollToElement(removeBtn, direction, "scrool " + direction + " to remove button");
		return this;
	}

	public ProductPage pressCardBtn() throws InterruptedException {
		click(cardBtn, "click card button");
		return new ProductPage();
	}		

	public HomePage scroolToTermsTxt(String direction) throws InterruptedException {
		scrollToElement(termsTxt, direction, "scrool " + direction + " to terms text");
		return this;
	}	
}