package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Elements {

	private WebDriver driver;
	private ReportManager report;

	public Elements(WebDriver driver) {

		this.driver = driver;
		report = ReportManager.getInstance();
	}

	public void click(By locator) {

		try {

			driver.findElement(locator).click();
		} catch (Exception e) {

			report.takeScreenshot(driver, "Failed to click on Element.");
		}
		report.log("Successfully Clicked on Element.");
	}

	public void write(By locator, String text) {

		try {
			driver.findElement(locator).sendKeys(text);
		} catch (Exception e) {

			report.takeScreenshot(driver, "Failed to write '" + text + "'.");
		}
		report.log("Successfully Wrote '" + text + "'");
	}

	public String getText(By locator) {
		String text = "";
		try {
			text = driver.findElement(locator).getText();
		} catch (Exception e) {

			report.takeScreenshot(driver, "Failed to get text from Element with locator:" + locator);
		}
		return text;
	}
}
