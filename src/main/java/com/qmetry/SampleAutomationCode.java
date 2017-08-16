
/**
 * Vaibhavsinh Vaghela
 * July 14th, 2017
 * --
 * Automated program for Google Chrome on macOS.
 * Navigates to Google and queries for QMetry on Google.com.
 */

package com.qmetry;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleAutomationCode {

	public static ChromeDriver browser;
	public static String chromeDriverPath = "/Users/dhara/Downloads/chromedriver";

	/**
	 * Navigate Chrome to Amazon's homepage.
	 * 
	 * @return the title of the website we just navigated to, for TestNG.
	 * @throws InterruptedException
	 *             if interrupted during sleep.
	 */
	public static String navigateToQMetry() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		browser = new ChromeDriver();
		browser.navigate().to("http://www.qmetry.com");
		TimeUnit.SECONDS.sleep(2);
		return browser.getTitle();
	}

	/**
	 * Query Amazon for a product through the search box.
	 * 
	 * @return the title of the search-results page, for TestNG.
	 * @throws InterruptedException
	 *             if interrupted during sleep.
	 */
	public static void queryText(String text) throws InterruptedException {

		WebElement searchBox = browser.findElement(By.id("s"));
		searchBox.sendKeys(text); // Enter text into the box.
		searchBox.submit(); // Submit the query -- hitting the return key.
		TimeUnit.SECONDS.sleep(2);

	}

}
