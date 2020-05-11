package pageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Elements;

public class AccountPage {

	Elements elements;
	WebDriver driver;

	By accountName = By.xpath("//h3[@class =\"text-align-left\"]");

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		elements = new Elements(driver);
	}

	public String getAccountName() {
		return elements.getText(accountName);
	}
}
