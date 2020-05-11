package pageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Configurations;
import utils.Elements;

public class LoginPage {

	Elements elements;
	WebDriver driver;

	By userNameTxt = By.name("username");
	By passwordTxt = By.name("password");
	By loginBtn = By.cssSelector(".btn.btn-primary.btn-lg.btn-block.loginbtn");

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		elements = new Elements(driver);
	}

	public void goToPage() {
		Configurations.goToUrl(driver, "login");
	}

	public void setEmail(String userName) {
		elements.write(userNameTxt, userName);
	}

	public void setPassword(String pass) {
		elements.write(passwordTxt, pass);
	}

	public void clickLoginBtn() {
		elements.click(loginBtn);
	}

	public String getAccountName() {
		return new AccountPage(driver).getAccountName();
	}

}
