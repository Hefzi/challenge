package pageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Configurations;
import utils.Elements;

public class RegisterationPage {

	private Elements elements;
	private WebDriver driver;

	By firstNameTxt = By.name("firstname");
	By lastNameTxt = By.name("lastname");
	By mobileNoTxt = By.name("phone");
	By emailTxt = By.name("email");
	By passwordTxt = By.name("password");
	By confirmPassTxt = By.name("confirmpassword");
	By signUpBtn = By.cssSelector(".signupbtn.btn_full.btn.btn-success.btn-block.btn-lg");

	public RegisterationPage(WebDriver driver) {
		this.driver = driver;
		elements = new Elements(driver);
	}

	public void goToPage() {
		Configurations.goToUrl(driver, "register");
	}

	public void setFirstName(String firstName) {
		elements.write(firstNameTxt, firstName);
	}

	public void setlastName(String lastName) {
		elements.write(lastNameTxt, lastName);
	}

	public void setMobileNo(String mobileNo) {
		elements.write(mobileNoTxt, mobileNo);
	}

	public void setEmail(String email) {
		elements.write(emailTxt, email);
	}

	public void setPassword(String password) {
		elements.write(passwordTxt, password);
	}

	public void setConfirmPassword(String confirmPass) {
		elements.write(confirmPassTxt, confirmPass);
	}

	public void clickSignUpBtn() {
		elements.click(signUpBtn);
	}

	public String getAccountName() {
		return new AccountPage(driver).getAccountName();
	}
}
