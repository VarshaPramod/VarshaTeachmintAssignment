package main;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCase001 {

	public static void main(String[] args) throws Exception {
		//Test data
		String otp = "120992";
		String url = "https://accounts.teachmint.com/";
		
		//Step 1 : Launch the application and login with OTP
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		WebDriverWait explicitWait=new WebDriverWait(driver,Duration.ofSeconds(15));
		driver.get(url);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("send-otp-btn-id")));
		WebElement phonneNumberTextBox = driver.findElement(By.id("user-input"));
		phonneNumberTextBox.clear();
		phonneNumberTextBox.sendKeys("0000020232");
		driver.findElement(By.id("send-otp-btn-id")).click();
		List<WebElement> otptextField = driver.findElements(By.xpath("//input[@class='otp-digit-input']"));
		for (int i=0; i<otptextField.size(); i++) {
			otptextField.get(i).sendKeys(""+otp.charAt(i));
		}
		driver.findElement(By.id("submit-otp-btn-id")).click();

		//Step 2 : click on the profile and handling the popup
		
		driver.findElement(By.xpath("//div[text()='@Automation-2']")).click();
		WebElement hostElement = driver.findElement(By.tagName("releases-panel"));
		SearchContext shadowRootElement = hostElement.getShadowRoot();
		shadowRootElement.findElement(By.cssSelector("svg[class='close-btn']")).click();

		//Step 3 : Click on Administrator in Dashboard
		
		driver.findElement(By.xpath("//span[@data-qa='icon-administrator']")).click();
		driver.findElement(By.linkText("Certificates")).click();
		driver.findElement(By.xpath("//h6[text()='School leaving certificate']/ancestor::div[@data-qa='plain-card']")).click();
		driver.findElement(By.xpath("//button[.='Generate']")).click();
		driver.findElement(By.name("search")).sendKeys("sam");
		driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
		driver.findElement(By.xpath("//button[.='Generate']")).click();
		
		//Step 4 :Enter Details to Complete Document
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("canvas")));
		WebElement remarkTextField = driver.findElement(By.xpath("//input[@placeholder='Remarks']"));
		remarkTextField.clear();
		remarkTextField.sendKeys("Not  Satisfied");
		WebElement showChangesInPreviewButton = driver.findElement(By.xpath("//button[.='Show changes in preview']"));
		showChangesInPreviewButton.click();
		
		Thread.sleep(5000);
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Generate']")));
		driver.findElement(By.xpath("//div[text()='Generate']")).click();
		
		//Step 5 :To Download the Document
		
		driver.findElement(By.xpath("//div[text()='Download']")).click();
		explicitWait.until(ExpectedConditions.numberOfWindowsToBe(2));
		String parentWindowId = driver.getWindowHandle();
		Set<String> allwindowIds = driver.getWindowHandles();
		allwindowIds.remove(parentWindowId);
		for(String windID : allwindowIds) {
			driver.switchTo().window(windID);
		}
		
		Robot keybardAction = new Robot();
		for(int i=0; i<9; i++) {
			keybardAction.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
		}
		keybardAction.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		keybardAction.keyPress(KeyEvent.VK_ENTER);
		keybardAction.keyRelease(KeyEvent.VK_ENTER);
		keybardAction.keyRelease(KeyEvent.VK_TAB);
		driver.close();
		
		//Step 6 :To Save the Downloaded Document
		
		driver.switchTo().window(parentWindowId);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[@data-qa='icon-administrator']")).click();
		driver.findElement(By.xpath("//span[@data-qa='icon-administrator']")).click();
		driver.findElement(By.linkText("Certificates")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[last()]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[last()]//div[text()='Preview']")).click();
		
	}

}
