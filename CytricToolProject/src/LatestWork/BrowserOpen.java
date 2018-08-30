package LatestWork;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserOpen {
	private static final String WebElement = null;
	 static WebDriver driver;
	 static String ApplicationLocation = getJarPath(); //D:\reportingTool\bin

	public static void main(String[] args)throws InterruptedException,IOException {
		// TODO Auto-generated method stub

	}
public static void launchApp(String baseUrl )throws InterruptedException, IOException{
		 
		
		String ExcelFileLocation = ApplicationLocation +"\\InputXpathNewCytric.xlsx";   
		
		
		 driver = BrowserOption(ApplicationLocation);
		
		driver.get(baseUrl);
		
		waitforlogin(driver, baseUrl);
	
		checkOptions(driver);
		
					
		ExcelData1 excel = new ExcelData1(ExcelFileLocation, 0);
		ReadLinesExcel(excel);
		
		Thread.sleep(1000);
		
		driver.close();	
		    

	}
	
	private static void checkOptions(WebDriver driver) throws InterruptedException,IOException {
		// TODO Auto-generated method stub
		List<WebElement>str=driver.findElements(By.xpath("//ul[@class='dropdown menu align-right']//li//a[contains(text(),'')]"));
		for(WebElement elem:str)
		{
			String options=elem.getText();
			System.out.println(options+" ");
			if(elem.getText().equals("Management"))
			{
				GoToPath(driver,"//a[text()='Management']");
				
				GoToPath(driver,"//ul[@class='dropdown menu align-right']/descendant::a[text()='Travel Management System']");
				
				break;
			}
			else if(elem.getText().equals("Travel Management System"))
			{
				GoToPath(driver,"//li[@class='skin-hover']//a[contains(text(),'Travel Management System')]");
				break;
				
			}
		}
		
	}

	public static void ReadLinesExcel(ExcelData1 excel) throws InterruptedException {
		String PreviousPath = "";
		for (int i = 1; i <= excel.getLastRowNum(); i++) {
			
			String CurrentPath = excel.getData(i,1);
			String xPath = excel.getData(i,2);
			String type = excel.getData(i,3);
			 
			GoToWebPath.GoToWebPath(PreviousPath, CurrentPath, driver);
			PreviousPath = CurrentPath;
			
			
		
		
			String OutputData = WhichFunctionToUse(type, xPath);
			
			
			try {
				System.out.println(OutputData);
				excel.WriteExcel(OutputData,i);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			excel.CloseExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			
		} 
		driver.close();
	}
	 
	
	
	private static String WhichFunctionToUse(String type, String xPath) throws InterruptedException {
		String OutputData ="";
		if (WaitFor(driver, xPath)){
			switch (type)
			{
			
			  case "List":
				OutputData = ListFunction(driver,xPath);
			    break;
			    
			 case "Checkbox":
				OutputData =Checkboxfunction(driver,xPath);
				break;
			 
			 
			case "MultiList":
				  OutputData = MultiListFunction(driver,xPath);
				  break;
			 case "TextBox":
				 OutputData = TextBoxFunction(driver,xPath);
				 break;
			 case "TextBox1":
				 OutputData = TextBoxFunction1(driver,xPath);
				 break;
			  case "RadioButton":
				  OutputData = RadioButtonFunction(driver,xPath);
				  break;
			  default:
			    System.out.println("function not found");
			}
		}else{
			OutputData = "X-Path not found";
		}
			
		return OutputData;
	}
	

	public static String ListFunction(WebDriver driver, String Xpath) throws InterruptedException{
		String output=null;
		List<WebElement> Check = driver.findElements(By.xpath(Xpath +"/descendant::option"));
		for (WebElement elem: Check) 
		{
			
			if(elem.getAttribute("selected")!=null)
			{
				
					output=elem.getAttribute("innerHTML");
			}
			
		}

		return output;
	}

	public static String MultiListFunction(WebDriver driver, String Xpath) throws InterruptedException{
		String output=null;
		
		List<WebElement> Check = driver.findElements(By.xpath(Xpath +"/descendant::option"));
		
		for (WebElement elem: Check) {
		
			
		
			if(elem.getAttribute("selected")!=null)
			{
				
				if(output==null)
				{
					output=elem.getAttribute("innerHTML");
				}
				else
				{
					output=output+ "/" + elem.getAttribute("innerHTML");
				}
			}
			
		}

		return output;
	}
	
	/* this function TextBox is to get only text because the value attribute is not present in the HTNL tag */
	public static String TextBoxFunction(WebDriver driver,String Xpath) throws InterruptedException 
	{
		String output=null;
	
		WebElement Textboxes = driver.findElement(By.xpath(Xpath));
		output = (Textboxes.getText());
		return output;
		
	}
	/* both text and numeric value are present in the "value" attribute of the HTMl tag so TextBoxFunction1 */
	public static String TextBoxFunction1(WebDriver driver,String Xpath) throws InterruptedException {
		String output=null;
		
		WebElement Textbox = driver.findElement(By.xpath(Xpath));
		
			if(Textbox.getAttribute("value")==null){
			
				output=null;
			}
			else{
				output = (Textbox.getAttribute("value"));
				
				}
			return output;	
		}
		
	
	public static String Checkboxfunction(WebDriver driver,String Xpath) throws InterruptedException{ 
		String output=null; 
		
		WebElement check=driver.findElement(By.xpath(Xpath));
	
	
		if(check.getAttribute("checked")==null)
		{
			output="Not checked";
		}else{
			output="checked";
		}
		return output;
		
		
	}

	public static String RadioButtonFunction(WebDriver driver, String Xpath) throws InterruptedException{ 
		String output=null; 
		
		WebElement check=driver.findElement(By.xpath(Xpath));
		
		
		
		if(check.getAttribute("checked")==null)
		{
			output="Not checked";
		}else{
			output="checked";
		}
		System.out.println("output is: "+output);
		return output;
	}
	
		
	public static WebDriver BrowserOption(String ApplicationLocation) throws IOException {

		System.setProperty("webdriver.gecko.driver", ApplicationLocation + "\\geckodriver.exe");
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		//firefoxBinary.addCommandLineOptions("--headless");


		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(firefoxBinary).setProfile(profile);
		driver = new FirefoxDriver(firefoxOptions);
		return driver;
	}

	 public static void waitforlogin(WebDriver driver, String baseUrl) {
		    WebDriverWait wait = new WebDriverWait(driver, 1000);
		    String url =  driver.getCurrentUrl();
		    wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
		   
		    System.out.println(url +"                  " + baseUrl);
	 }
	 
	 public static void GoToPath(WebDriver driver, String Xpath) throws InterruptedException {
		 	
		 	WaitFor(driver, Xpath);
		 
			WebElement check=driver.findElement(By.xpath(Xpath));
			check.click();
			  
	 }
	 
	 public static boolean WaitFor(WebDriver driver, String Xpath)  {
		 try {
			WebDriverWait wait = new WebDriverWait(driver,5);
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Xpath))); 
			 return true;
		} catch (Exception e) {
			
			e.getMessage();
			
			return false;
		}
	 }
	
	 public static String getJarPath() {
			String path = BrowserOpen.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String pathName = "";

			try {
				pathName = URLDecoder.decode(path, "UTF-8");
				pathName = pathName.substring(1, pathName.lastIndexOf("/"));
				pathName = pathName.replaceAll("/", "\\\\");
				System.out.println("pathName "+pathName);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pathName;
		}


}
