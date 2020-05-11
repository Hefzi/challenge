package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageModels.LoginPage;
import pageModels.RegisterationPage;
import utils.Configurations;
import utils.ExcelReader;
import utils.ReportManager;

public class ChallengeTest {

	private WebDriver driver;
	private RegisterationPage registerPage;
	private LoginPage loginPage;
	private ExcelReader dataReader;
	private Configurations config;
	private ReportManager report;

	@Test(priority = 0, description = "Testing that user can register successfully")
	public void userRegister() {

		report.startTest("User Registeration");

		registerPage.goToPage();
		registerPage.setFirstName(dataReader.getCellData(0, 1));
		registerPage.setlastName(dataReader.getCellData(1, 1));
		registerPage.setMobileNo(dataReader.getCellData(4, 1));
		registerPage.setEmail(dataReader.getCellData(2, 1));
		registerPage.setPassword(dataReader.getCellData(3, 1));
		registerPage.setConfirmPassword(dataReader.getCellData(3, 1));
		registerPage.clickSignUpBtn();

		Configurations.getLogs(driver);

		Assert.assertEquals(driver.getTitle(), dataReader.getCellData(5, 1), "Failed to register successfully");
		Assert.assertEquals(registerPage.getAccountName(),
				dataReader.getCellData(0, 1) + " " + dataReader.getCellData(1, 1),
				"Failed to register with the right credentials");

		report.endTest();
	}

	@Test(priority = 1, description = "Testing that user can login successfully using the same credentials used in registeration")
	public void userLoginAfterRegisteration() {

		report.startTest("User Login");

		loginPage.setEmail(dataReader.getCellData(2, 1));
		loginPage.setPassword(dataReader.getCellData(3, 1));
		loginPage.clickLoginBtn();

		Assert.assertEquals(driver.getTitle(), dataReader.getCellData(5, 1), "Failed to register successfully");
		Assert.assertEquals(registerPage.getAccountName(),
				dataReader.getCellData(0, 1) + " " + dataReader.getCellData(1, 1),
				"Failed to register with the right credentials");

		report.endTest();
	}

	@BeforeTest
	public void setUp() {
		config = Configurations.getInstance();
		config.intializeDriver(driver);
		registerPage = new RegisterationPage(driver);
		loginPage = new LoginPage(driver);
		dataReader = new ExcelReader(Configurations.getDataFolderPath() + "RegisterationTest.xlsx");
		report = ReportManager.getInstance();
	}

	@AfterTest
	public void wrapUp() {
		config.closeSession(driver);
	}
}
