package Admin_Setting;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TeamMembersAdd {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String AppURL = "https://app-dev.trytemelio.com/foundation/41e6600d-6cb5-4314-a5e0-3e59442940cc";
	public static String DriverURL = "/Users/kavinkumar/eclipse-workspace/Temelio_Testing/Driver/chromedriver";

	public void loginfun() throws InterruptedException {

		driver.navigate().to(AppURL);
		WebElement email = driver.findElement(By.id("username"));
		email.sendKeys("Username");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Pwd");
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signin-button")));
		WebElement Signin = driver.findElement(By.id("signin-button"));
		Signin.click();
		WebElement element2 = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Dashboard")));
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

	public void AdminSetting() throws InterruptedException {
		// need to click profile dropdown at top of the page
		Thread.sleep(1000);
		WebElement expand = driver.findElement(By.xpath("//button[@aria-label=\"Open entity switcher\"]"));
		expand.click();
		WebElement admsetting = driver.findElement(By.xpath("//div[contains(text(),\"Admin Settings\")]/../parent::a"));
		Thread.sleep(1000);
		admsetting.click();
		Thread.sleep(1000);
	}

	public void TeamMemberAdd() throws InterruptedException {

		for (int i = 0; i < 4; i++) {
			WebElement ATM = driver.findElement(By.xpath("//button[contains(text(),\"Add Team Member +\")]"));
			ATM.click();
			WebElement ATM_name = driver
					.findElement(By.xpath("//span[contains(text(),\"Name\")]/ancestor::label/following-sibling::*"));
			ATM_name.sendKeys("kavin" + i);
			WebElement ATM_Email = driver
					.findElement(By.xpath("//span[contains(text(),\"Email\")]/ancestor::label/following-sibling::*"));
			ATM_Email.sendKeys("Kavin123" + i + "@gmail.com");
			WebElement ATM_Title = driver
					.findElement(By.xpath("//span[contains(text(),\"Title\")]/ancestor::label/following-sibling::*"));
			ATM_Title.sendKeys("Sample" + i);
			WebElement dropdownElement = driver.findElement(
					By.xpath("//span[contains(text(),\"Access Type\")]/ancestor::label/following-sibling::*/select"));

			// Create an instance of the Select class
			Select dropdown = new Select(dropdownElement);
			dropdown.selectByIndex(i);
			WebElement addButton = driver.findElement(By.xpath("(//button[text()='Add'])[4]"));
			Thread.sleep(500);
			addButton.click();
			Thread.sleep(500);
		}

	}

	public void FoundationTage() throws InterruptedException {
		for (int i = 1; i <= 5; i++) {
			WebElement tag = driver.findElement(By.xpath("//input[@placeholder=\"Tag name...\"]"));
			tag.clear();
			tag.sendKeys("tag" + i);
			WebElement tagcolor = driver.findElement(
					By.xpath("(//input[@placeholder=\"Tag name...\"]/../following-sibling::*/div)[" + i + "]"));
			tagcolor.click();
			WebElement tagaddButton = driver
					.findElement(By.xpath("//input[@placeholder=\"Tag name...\"]/following-sibling::*"));
			tagaddButton.click();
			Thread.sleep(1000);

		}
	}

	public void Budgeting() throws InterruptedException {
		Thread.sleep(1000);
		WebElement budget = driver.findElement(By.xpath(
				"//p[contains(text(),\"Total fiscal year grant budget (Planned Giving)\")]//following-sibling::*"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", budget);

		int inputLength = budget.getAttribute("value").length();
		System.out.println(inputLength);
		Actions a = new Actions(driver);

		for (int i = 1; i < inputLength; i++) {
			a.click(budget).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
			Thread.sleep(500);
		}
		Thread.sleep(1000);
		a.moveToElement(budget).click().sendKeys("10000").perform();
		Thread.sleep(500);
		WebElement AddBudget = driver.findElement(By.xpath("//button[@aria-label=\"Add Budgeting Allocation\"]"));
		AddBudget.click();
		a.click(AddBudget);
		Thread.sleep(500);
		WebElement errormsg = driver
				.findElement(By.xpath("//p[contains(text(),\"You must have an allocation name.\")]"));
		String msg = errormsg.getText();
		System.out.println(msg);

		Assert.assertEquals(msg, "You must have an allocation name.");
		Thread.sleep(1000);

		WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"((//p[contains(text(),\"You must have an allocation name.\")]/ancestor::div)[8]//child::*)[3]")));
		WebElement nameElement = driver.findElement(By.xpath(
				"((//p[contains(text(),\"You must have an allocation name.\")]/ancestor::div)[8]//child::*)[3]"));
		WebElement valueElement = driver.findElement(By.xpath(
				"((//p[contains(text(),\"You must have an allocation name.\")]/ancestor::div)[8]//child::*)[4]"));
		nameElement.sendKeys("sample");
		valueElement.sendKeys("1000");
		Thread.sleep(1000);
		
		
		WebElement ATM = driver.findElement(By.xpath("//button[contains(text(),\"Add Team Member +\")]"));
		js.executeScript("arguments[0].scrollIntoView(true);", ATM);
		Thread.sleep(1000);
		js.executeScript("arguments[0].scrollIntoView(true);", budget);

	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		TeamMembersAdd Tel = new TeamMembersAdd();
		Tel.driverSetup();
		Tel.loginfun();
		Tel.AdminSetting();
		Tel.TeamMemberAdd();
		Tel.FoundationTage();
		Tel.Budgeting();
		
		
		System.out.println("All Steps Executed");
		driver.quit();

	}

}
