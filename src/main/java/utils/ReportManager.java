package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportManager {

	private ExtentTest test;
	private ExtentReports report;
	private static ReportManager manager;

	private ReportManager() {

		report = new ExtentReports(Configurations.getOutputFolderPath() + "\\ExtentReportResults.html");
		report.loadConfig(new File(Configurations.getProjectPath() + "extent-config.xml"));
	}

	public static ReportManager getInstance() {

		if (manager == null) {
			manager = new ReportManager();
		}
		return manager;
	}

	public void startTest(String testName) {
		report.startTest(testName);
	}

	public void endTest() {

		report.endTest(test);
	}

	public void quit() {
		report.flush();
	}

	public void log(String log) {
		test.log(LogStatus.INFO, log);
	}

	private String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(Configurations.getOutputFolderPath() + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

	public void takeScreenshot(WebDriver driver, String failureMessage) {

		try {
			test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)) + failureMessage);
		} catch (IOException e) {
			test.log(LogStatus.FAIL, "Failed to take screenshot.");
		}
	}

}
