import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalenderFinal {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "D:\\install\\wDriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://cleartrip.com");
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,900)");
		Thread.sleep(5000);
		String keys = Keys.chord(Keys.CONTROL, Keys.ENTER);
		driver.findElement(By.xpath("//a[@title='About Us']")).sendKeys(keys);
		Thread.sleep(5000);

		Set<String> windows = driver.getWindowHandles();
		Iterator<String> iterator =  windows.iterator();
		String parentWindow = iterator.next();
		String childWindow = iterator.next();
		
		driver.switchTo().window(childWindow);
		Thread.sleep(5000);
		driver.switchTo().window(parentWindow);
		js.executeScript("window.scrollBy(0,-900)");
		Thread.sleep(5000);

		//Round Trip radio 
		driver.findElement(By.xpath("(//div[@class='flex flex-start p-relative flex-middle'])[2]")).click();
		
		//From - autosuggestive dropdown
		driver.findElement(By.cssSelector("input[class*='pb-2 ba br-4 h-8 null']")).sendKeys("Bel");
		Thread.sleep(2000);
		List<WebElement> cities = driver.findElements(By.cssSelector("li[class*='ls-reset br-4 w-100p'] p[class*='to-ellipsis o-hidden ws-nowrap']"));
		for(WebElement city:cities) {
			if(city.getText().equalsIgnoreCase("Belgrade, CS - Belgrade Beograd (BEG)")) {
				city.click();
				break;
			}
		}
		
		//To - autosuggestive dropdown
		driver.findElement(By.xpath("//input[@class='field bw-1 bs-solid w-100p p-2 box-border br-4 fs-2 c-neutral-900 h-8 bc-neutral-100 c-neutral-900 focus:bc-secondary-500 flex flex-middle flex-between t-all fs-2 focus:bc-secondary-500 bg-transparent bc-neutral-100 pr-2 pl-3 pt-2 pb-2 ba br-4 h-8']")).sendKeys("Tiv");
		Thread.sleep(2000);
		List<WebElement> cities2 = driver.findElements(By.cssSelector("li[class*='ls-reset br-4 w-100p'] p[class*='to-ellipsis o-hidden ws-nowrap']"));
		for(WebElement city:cities2) {
			if(city.getText().equalsIgnoreCase("Tivat, ME - Tivat (TIV)")) {
				city.click();
				break;
			}
		}
		//Return on 25.3.2022.
		WebElement returnDate = driver.findElement(By.xpath("(//*[@class='flex flex-middle p-relative homeCalender'] //*[contains(@class,'flex flex-middle flex-between t-all fs-2')])[2]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(returnDate).click().build().perform();
		
		WebDriverWait wdw = new WebDriverWait(driver, 3000);
		wdw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='DayPicker-Month'])[2]")));
		
		while(!driver.findElement(By.xpath("(//*[@class='DayPicker-Month'])[2] //*[@class='DayPicker-Caption']")).getText().contains("March")) {
			WebDriverWait wd = new WebDriverWait(driver, 3000);
			wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-testid='rightArrow']")));
			driver.findElement(By.xpath("//*[@data-testid='rightArrow']")).click();
		}
		driver.findElement(By.xpath("(//*[@class='DayPicker-Month'])[2] //*[@aria-label='Fri Mar 25 2022']")).click();		
		
		// Depart on  22.5.2022.
		driver.findElement(By.xpath("(//*[@class='flex flex-middle p-relative homeCalender'] //*[contains(@class,'flex flex-middle flex-between t-all fs-2')])[1]")).click();
		
		while(!driver.findElement(By.xpath("(//*[@class='DayPicker-Month'])[2] //*[@class='DayPicker-Caption']")).getText().contains("February")) {
			WebDriverWait w = new WebDriverWait(driver, 3000);
			w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-testid='rightArrow']")));
			driver.findElement(By.xpath("//*[@data-testid='rightArrow']")).click();
		}	
		driver.findElement(By.xpath("(//*[@class='DayPicker-Month'])[2] //*[@aria-label='Wed Feb 16 2022']")).click();	
				
		//Children 
		WebElement children = driver.findElement(By.xpath("(//select[@class='bc-neutral-100 bg-transparent'])[2]"));
		Select selectTwo = new Select(children);
		selectTwo.selectByVisibleText("2");
		
		//Adults select list
		WebElement adults = driver.findElement(By.xpath("(//select[@class='bc-neutral-100 bg-transparent'])[1]"));
		Select selectOne = new Select(adults);
		selectOne.selectByValue("2");
				
		//More Options link
		WebElement moreOptions = driver.findElement(By.xpath("//a[@class='td-none hover:td-underline px-4 mt-1 weak']"));
		js.executeScript("arguments[0].click();", moreOptions);
		
		//Preferred Airlines dynamic dropdown
		WebElement travelClass = driver.findElement(By.xpath("(//*[@class='bc-neutral-100 bg-transparent'])[4]"));
		Select selTravel = new Select(travelClass);
		selTravel.selectByValue("First");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[contains(@class,'field bw-1 bs-solid w-100p p-2 box-border br-4 fs-2')])[3]")).sendKeys("Serb");
		Thread.sleep(2000);
		List<WebElement> airlines = driver.findElements(By.xpath("//li[contains(@class,'ls-reset br-4 w-100p')] //p[@class='to-ellipsis o-hidden ws-nowrap c-inherit fs-3']"));
		for(WebElement airline:airlines) {
			if(airline.getText().equalsIgnoreCase("Air Serbia (JU)")) {
				airline.click();
				break;
			}
		}
		
		//Submit
		driver.findElement(By.xpath("(//div[@class='row pt-3 pb-4 p-relative px-4']) [2] //div[@class='col flex flex-middle'] //button")).click();
	}


}
