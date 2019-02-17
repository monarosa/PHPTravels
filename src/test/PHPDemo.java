package test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import bsh.util.Util;
import utilities.TestConstants;
import utilities.Utill;

public class PHPDemo {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty(TestConstants.DRIVER_PATH, TestConstants.CHROME_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://phptravels.com/demo/");
	}

	@Test
	public void test() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.id("onesignal-popover-cancel-button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='PopupSignupForm_0']/div[2]/div[1]")).click();
		driver.findElement(By.xpath("//*[.='http://www.phptravels.net']")).click();
		String original = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String str : windowHandles) {
			if (!str.equals(original))
				driver.switchTo().window(str);
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//li[@id='li_myaccount']//b)[2]")).click();
		driver.findElement(By.xpath("(//li[@id='li_myaccount']//b)[2]/../following-sibling::*/li[1]")).click();
		driver.findElement(By.name("username")).sendKeys("user@phptravels.com");
		driver.findElement(By.name("password")).sendKeys("demouser");
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[.='Hotels   ']")).click();
		driver.findElement(By.id("s2id_autogen9")).sendKeys("istanbul");
		driver.findElement(By.xpath("//*[.='Hotels']/../ul/li[1]")).click();
		driver.findElement(By.name("checkin")).sendKeys("02/01/2019");
		driver.findElement(By.name("checkout")).sendKeys("02/05/2019");
		driver.findElement(By.xpath("//*[.=' Search']")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("(//input[@id='roomsCheckbox'])[2]/.."))).perform();
		
		driver.findElement(By.xpath("(//input[@id='roomsCheckbox'])[2]/..")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//button[contains(text(),'Book Now')]")).click();
		driver.findElement(By.name("logged")).click();
	
		
	//	driver.findElement(By.linkText("INVOICE")).click();
//		driver.close();

		Thread.sleep(3000);
		String hotelName = driver
				.findElement(By.xpath("(//table[@id='invoiceTable']/tbody/tr[4]//tbody//tr[2]//tbody/tr[1]/td[1])[1]")).getText()
				.trim();
		System.out.println(hotelName);
		String depositNow = driver.findElement(By.xpath("//table[2]/tbody//td[1]")).getText().trim();
		String tax = driver.findElement(By.xpath("//table[2]/tbody//td[2]")).getText().trim();
		String Total = driver.findElement(By.xpath("//table[2]/tbody//td[3]")).getText().trim();
		String bookingCode=driver.findElement(By.xpath("//*[.='Booking Code']/..")).getText().trim();
		String booking=Utill.onlyNum(bookingCode);
		System.out.println(booking);
		System.out.println(depositNow);
		System.out.println(tax);
		System.out.println(Total);
		System.out.println(bookingCode);
		driver.close();
		
		for (String str : driver.getWindowHandles()) {
		driver.switchTo().window(str);
		if (driver.getTitle().contains("Demo"))
			break;
	}
		
		driver.findElement(By.xpath("//*[.='http://www.phptravels.net/admin']")).click();
		Thread.sleep(2000);
		for (String str : driver.getWindowHandles()) {
		driver.switchTo().window(str);
		if (driver.getTitle().contains("Admin"))
			break;
		}
		Thread.sleep(2000);
		driver.findElement(By.name("email")).sendKeys("admin@phptravels.com");
		driver.findElement(By.name("password")).sendKeys("demoadmin");
		driver.findElement(By.xpath("//*[.='Login']/span[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//table[@class='xcrud-list table table-striped table-hover']/tbody//*[.='"+booking+"']/../td[12]/span/a[2]")).click();
		
		String AdminHname=driver.findElement(By.id("itemtitlesum")).getText();
		String AdminTax=driver.findElement(By.id("displaytax")).getText();
		String AdminTotal=driver.findElement(By.id("grandtotal")).getText();
		String Admindeposit=driver.findElement(By.id("topaytotal")).getText();
		
		SoftAssert soft=new SoftAssert();
		soft.assertEquals( Admindeposit,Utill.onlyMoney(depositNow));
		soft.assertEquals( AdminHname,hotelName);
		soft.assertEquals(AdminTax,Utill.onlyMoney(tax));
		soft.assertEquals(AdminTotal,Utill.onlyMoney(Total));
		
		soft.assertAll();
	}
}
