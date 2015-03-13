/*
 */
package se.kth.view;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


/**
 *
 * @author work
 */
public class RecruiterManagerTest {
    
    
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.se/";//"http://localhost:8080/";/iv1201/
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    System.out.println("=================================>" + driver.getTitle()); 
  }

  @Test
  public void testRecruiter() throws Exception {
    driver.get(baseUrl + "");
    driver.findElement(By.linkText("Arbeta hos oss")).click();
    driver.findElement(By.id("login_value")).clear();
    driver.findElement(By.id("login_value")).sendKeys("amore@kth.se");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("1");
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.linkText("Andre More")).click();
    driver.findElement(By.linkText("Logga ut")).click();
    driver.findElement(By.linkText("Arbeta hos oss")).click();
    driver.findElement(By.linkText("Hem")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }

    
}
