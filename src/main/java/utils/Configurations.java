package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Configurations {

	enum DriverType {
		CHROME, FIREFOX;
	}

	private static String URL;
	private static DriverType driverType;
	private static Configurations config;
	private static Properties prop;
	private static String dataFolderPath;
	private static String projectPath;
	private static ReportManager report;

	public Configurations() {

		prop = new Properties();
		try {
			prop.load(new FileInputStream("src/test/resources/execution.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		URL = prop.getProperty("url");
		dataFolderPath = prop.getProperty("dataFolderPath");
		setDriverType();
		projectPath = new File(".").getAbsolutePath();
		report = ReportManager.getInstance();
	}

	public static Configurations getInstance() {

		if (config == null) {
			config = new Configurations();
		}
		return config;
	}

	@SuppressWarnings("deprecation")
	public void intializeDriver(WebDriver driver) {

		switch (driverType) {

		case CHROME:
			System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/drivers/chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);

			// set performance logger
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

			driver = new ChromeDriver(cap);
			break;

		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", projectPath + "/src/test/resources/drivers/geckodriver.exe");

			FirefoxOptions FFOptions = new FirefoxOptions();
			DesiredCapabilities FFCap = DesiredCapabilities.firefox();
			FFCap.setCapability(ChromeOptions.CAPABILITY, FFOptions);

			// set performance logger
			LoggingPreferences logPref = new LoggingPreferences();
			logPref.enable(LogType.PERFORMANCE, Level.ALL);
			FFCap.setCapability(CapabilityType.LOGGING_PREFS, logPref);
			driver = new FirefoxDriver(FFCap);
			break;

		default:
			System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().fullscreen();
		driver.get(URL);
	}

	private void setDriverType() {

		switch (prop.getProperty("driver").toUpperCase()) {

		case "CHROME":
			driverType = DriverType.CHROME;
			break;
		case "FIREFOX":
			driverType = DriverType.FIREFOX;
			break;

		default:
			driverType = DriverType.CHROME;
			break;
		}
	}

	public static String getDataFolderPath() {
		return projectPath + dataFolderPath;
	}

	public static String getOutputFolderPath() {
		return projectPath + prop.getProperty("outputFolderPath");
	}

	public static String getProjectPath() {
		return projectPath;
	}

	public String getURL() {
		return URL;
	}

	public void closeSession(WebDriver driver) {

		if (driver != null)
			driver.quit();

		report.quit();
	}

	public static void goToUrl(WebDriver driver, String urlPath) {

		driver.get(URL + urlPath);
	}

	public static String getLogs(WebDriver driver) {

		String result = "";
		// and capture the last recorded url (it may be a redirect, or the
		// original url)
		String currentURL = driver.getCurrentUrl();

		// then ask for all the performance logs from this request
		// one of them will contain the Network.responseReceived method
		// and we shall find the "last recorded url" response
		LogEntries logs = driver.manage().logs().get("performance");

		System.out.println("\nList of log entries:\n");

		for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();) {
			LogEntry entry = it.next();

			try {
				JSONObject json = new JSONObject(entry.getMessage());

				System.out.println(json.toString());

				JSONObject message = json.getJSONObject("message");
				String method = message.getString("method");

				if (method != null && "Network.responseReceived".equals(method)) {
					JSONObject params = message.getJSONObject("params");

					JSONObject response = params.getJSONObject("response");
					String messageUrl = response.getString("url");

					if (currentURL.equals(messageUrl)) {
						result = response.toString();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
