package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BasePage {

    private static String browserName;
    private static String baseUrl;

    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver(){
        return driver.get();
    }

    private synchronized void loadProperties(){
        try {
            FileInputStream fileInputStream = new FileInputStream("./resources/Environment.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);

            browserName = properties.getProperty("browserName");
            baseUrl = properties.getProperty("applicationUrl");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void openBrowser(){
        if(browserName.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver.set(ThreadGuard.protect(new ChromeDriver()));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().window().maximize();
        }
    }

    public void closeBrowser(){
        getDriver().close();
        driver.remove();
    }

}

