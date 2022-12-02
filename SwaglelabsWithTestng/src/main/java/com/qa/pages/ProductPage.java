package com.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductPage extends BaseTest{	
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='test-CONTINUE SHOPPING']") 
	@iOSXCUITFindBy(id="test-CONTINUE SHOPPING")	
	private MobileElement continueShoppingBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-CHECKOUT\"]") 
	@iOSXCUITFindBy(id="test-CHECKOUT")	
	private MobileElement checkoutBtn;	
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-REMOVE\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-REMOVE")
	private MobileElement removeBtn;
	
	@AndroidFindBy (accessibility = "test-First Name") 
	@iOSXCUITFindBy(id="test-First Name")
	private MobileElement firstNameTxtField;
	
	@AndroidFindBy (accessibility = "test-Last Name") 
	@iOSXCUITFindBy(id="test-Last Name")
	private MobileElement lastNameTxtField;
	
	@AndroidFindBy (accessibility = "test-Zip/Postal Code") 
	@iOSXCUITFindBy(id="test-Zip/Postal Code")
	private MobileElement zipTxtField;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-CONTINUE\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-CONTINUE")	
	private MobileElement continueBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-CANCEL\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-CANCEL")
	private MobileElement cancelBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-FINISH\"]/android.widget.TextView") 
	@iOSXCUITFindBy(id="test-FINISH")	
	private MobileElement finishBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-BACK HOME\"]/android.widget.TextView")
	@iOSXCUITFindBy(id="test-BACK HOME")
	private MobileElement backToHomeBtn;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[@index=\"0\"]") 
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"test-Description\"]/XCUIElementTypeStaticText/preceding-sibling::*[1]")	
	private MobileElement productNameTxt;	
	
	@AndroidFindBy (accessibility="test-Price")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"test-Price\"]")	
	private MobileElement priceTxt;	
	
	@AndroidFindBy (xpath="//android.view.ViewGroup/android.widget.TextView[@index=\"8\"]") 
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[4]/XCUIElementTypeStaticText[1]")
	private MobileElement productPriceFinishPageTxt;
	
	@AndroidFindBy (xpath="//android.view.ViewGroup/android.widget.TextView[@index=\"9\"]") 
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[4]/XCUIElementTypeStaticText[2]")
	private MobileElement productTaxPriceFinishPageTxt;
	
	@AndroidFindBy (xpath="//android.widget.TextView[@index=\"10\"]") 	
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[4]/XCUIElementTypeStaticText[3]")		
	private MobileElement productTotalPriceFinishPageTxt;
			
	public HomePage pressContinueShoppingBtn() throws InterruptedException {
		click(continueShoppingBtn, "click continue shopping button");
		return new HomePage();
	}	

	public ProductPage pressCheckoutBtn() throws InterruptedException {
		click(checkoutBtn, "click checkout button");
		return this;
	}	
	
	public String getProductNameTxt() throws InterruptedException {
		String err = getText(productNameTxt, "actually text is - ");
		return err;	
	}
	
	public String getProductTotalPriceFinishPageTxt() throws InterruptedException {
		String err = getText(productTotalPriceFinishPageTxt, "actually text is - ");
		return err;	
	}
	
	public String getProductTaxPriceFinishPageTxt() throws InterruptedException {
		String err = getText(productTaxPriceFinishPageTxt, "actually text is - ");
		return err;	
	}
	
	public String getProductPriceFinishPageTxt() throws InterruptedException {
		String err = getText(productPriceFinishPageTxt, "actually text is - ");
		return err;	
	}		

	public ProductPage scroolToProductPriceFinishPageTxt(String direction) throws InterruptedException {
		scrollToElement(productPriceFinishPageTxt, direction, "scrool " + direction + " to product price in finish page text");
		return this;
	}	
	
	public String getPriceTxt() throws InterruptedException {
		String err = getText(priceTxt, "actually text is - ");
		return err;	
	}	
	
	public HomePage pressBackToHomeBtn() throws InterruptedException {
		click(backToHomeBtn, "click back to home button");
		return new HomePage();
	}	

	public ProductPage pressFinishBtn() throws InterruptedException {
		click(backToHomeBtn, "click finish button");
		return this;
	}

	public ProductPage clickFirstNameTxtField() throws InterruptedException {
		click(firstNameTxtField, "click first name text field");
		return this;
	}
	public ProductPage clickLastNameTxtField() throws InterruptedException {
		click(lastNameTxtField, "click last name text field");
		return this;
	}
	public ProductPage clickZipTxtField() throws InterruptedException {
		click(zipTxtField, "click zip text field");
		return this;
	}

	public ProductPage typeFirstName(String firstName) throws InterruptedException {
//		typeWithKeyboard(firstNameTxtField,firstName);
		sendKeys(firstNameTxtField,firstName, "enter first name is " + firstName);
		return this;
	}

	public ProductPage typeLastName(String lastName) throws InterruptedException {
//		typeWithKeyboard(lastNameTxtField,lastName);
		sendKeys(lastNameTxtField,lastName, "enter last name is " + lastName);
		return this;
	}

	public ProductPage typeZip(String zip) throws InterruptedException {
//		typeWithKeyboard(zipTxtField,zip);
		sendKeys(zipTxtField,zip, "enter zip is " + zip);
		return this;
	}

	public ProductPage pressContinueBtn() throws InterruptedException {
		click(continueBtn, "click continue button");
		return this;
	}

	public ProductPage pressCancelBtn() throws InterruptedException {
		click(cancelBtn, "click cancel button");
		return this;
	}

	public ProductPage clearFirstNameField() throws InterruptedException {
		clearField(firstNameTxtField, "clear first name text field");
		return this;
	}

	public ProductPage clearLastNameField() throws InterruptedException {
		clearField(lastNameTxtField, "clear last name text field");
		return this;
	}

	public ProductPage clearZipField() throws InterruptedException {
		clearField(zipTxtField, "clear zip text field");
		return this;
	}		
}

