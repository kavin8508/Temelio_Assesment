package Contacts;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewContact_adding {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String AppURL = "https://app-dev.trytemelio.com/foundation/41e6600d-6cb5-4314-a5e0-3e59442940cc";
	public static String DriverURL = "/Users/kavinkumar/eclipse-workspace/Temelio_Testing/Driver/chromedriver";

	public void loginfun() throws InterruptedException {

		driver.navigate().to(AppURL);
		WebElement email = driver.findElement(By.id("username"));
		email.sendKeys("username");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("pwd**");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin-button")));
		WebElement Signin = driver.findElement(By.id("signin-button"));
		Signin.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Dashboard")));
		// System.out.println(driver.getTitle());
		String title = driver.getTitle();
		assertEquals(title, "Dashboard | Temelio");
		System.out.println("Login scenario: PASS");

	}

	public void driverSetup() {
		ChromeOptions chrome = new ChromeOptions();
		chrome.addArguments("--remote-allow-origins=*");
		System.setProperty("webdriver.chrome.driver", DriverURL);
		driver = new ChromeDriver(chrome);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void Addcontact() throws InterruptedException {
		WebElement contact = driver.findElement(By.xpath("//div[text()=\"Grantees\"]"));
		contact.click();
		Thread.sleep(1000);
		WebElement newContactButton = driver.findElement(By.xpath("//button[text()='New Contact']"));
		newContactButton.click();

		driver.findElement(By.name("legalName")).sendKeys("Test Organization");
		driver.findElement(By.xpath("//label[text()=\"EIN\"]//following-sibling::*")).sendKeys("12-3456789");
		driver.findElement(By.name("primaryContactName")).sendKeys("sample");
		driver.findElement(By.name("primaryContactEmail")).sendKeys("sample@example.com");
		Thread.sleep(1000);
		WebElement createContactButton = driver.findElement(By.xpath("//button[text()='Create Contact']"));
		createContactButton.click();
		Thread.sleep(1000);
		boolean isContactPresent = driver.getPageSource().contains("Test Organization");
		System.out.println("Contact creation successful: " + isContactPresent);
	}

	public void CustomField() throws InterruptedException {
		WebElement Customize = driver.findElement(By.xpath("//span[text()=\"Customize\"]//parent::button"));
		Customize.click();

		WebElement Addcustom = driver.findElement(By.xpath("//span[text()=\"New Custom Field\"]//parent::button"));
		Addcustom.click();

		WebElement CustomName = driver.findElement(By.xpath("(//label[text()=\"Name\"]//following-sibling::*)[2]"));
		CustomName.sendKeys("Sample");
		WebElement dropdownElement = driver
				.findElement(By.xpath("//label[text()=\"Type\"]//following-sibling::*//select"));

		Select dropdown = new Select(dropdownElement);
		dropdown.selectByIndex(1);
		Thread.sleep(1000);
		WebElement Add = driver.findElement(By.xpath("//button[text()=\"Add Field\"]"));
		Add.click();

		WebElement slider = driver.findElement(By.cssSelector(".os-scrollbar-handle"));

		// Instantiate the Actions class
		Actions actions = new Actions(driver);

		// Define the offset values to drag the slider
		int xOffset = 150; // Positive value to drag to the right, negative to the left
		int yOffset = 0; // No vertical movement needed

		// Perform the drag operation
		actions.clickAndHold(slider).moveByOffset(xOffset, yOffset).release().perform();
	}

	public void Filterdragdrop() throws InterruptedException {
		Actions actions = new Actions(driver);
		WebElement ChooseFil = driver.findElement(By.xpath("//span[contains(text(),\"Filter\")]//parent::button"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),\"Filter\")]//parent::button")));
		Thread.sleep(1000);
		ChooseFil.click();

		WebElement NewFil = driver
				.findElement(By.xpath("(//div[text()=\"New Filter\"]//parent::div[@class=\"css-gmuwbf\"])"));
		NewFil.click();
		Thread.sleep(1000);
		WebElement filtername = driver.findElement(By.xpath("//input[@value=\"New Filter\"]"));
		int inputLength = filtername.getAttribute("value").length();
		System.out.println(inputLength);
		for (int i = 1; i <= inputLength; i++) {
			actions.click(filtername).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE)
					.perform();
			Thread.sleep(300);
		}
		filtername.sendKeys("Demo Filter");

		WebElement sourceElement = driver.findElement(
				By.xpath("(//div[@class=\"chakra-stack css-1igwmid\"]/child::*[@stroke=\"currentColor\"])[1]"));

		WebElement targetElement = driver.findElement(
				By.xpath("(//div[@class=\"chakra-stack css-1igwmid\"]/child::*[@stroke=\"currentColor\"])[2]"));

		actions.clickAndHold(sourceElement).moveToElement(targetElement).pause(1000).build().perform();
		Thread.sleep(1000);
		WebElement Create = driver.findElement(By.xpath("//button[contains(text(),\"Create\")]"));
		Create.click();
		actions.click(Create).perform();
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		NewContact_adding nc = new NewContact_adding();
		nc.driverSetup();
		nc.loginfun();
		nc.Addcontact();
		nc.CustomField();
		nc.Filterdragdrop();
		
		System.out.println("All Steps Executed");
		driver.quit();

	}

}
