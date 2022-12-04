package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.contexts.Contexts;
import src.contexts.WaitMoreThanOneContext;
import src.drivers.DriverFactory;
import src.drivers.Platform;

import java.util.List;

public class HydridContext {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);

        try {
            //Navigate to webview
            appiumDriver.findElement(MobileBy.AccessibilityId("Webview")).click();

            WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
            wait.until(new WaitMoreThanOneContext(appiumDriver));

            //Print all contexts
            for (String context : appiumDriver.getContextHandles()) {
                System.out.println("Context: " + context);
            }

            //Switch webview context
            appiumDriver.context(Contexts.WEBVIEW);
            WebElement navToggleBtnElem = appiumDriver.findElementByCssSelector(".navbar__toggle");
            navToggleBtnElem.click();
            List<MobileElement> menuItemsElem = appiumDriver.findElementsByCssSelector(".menu__list li a");
            if (menuItemsElem.isEmpty()) {
                throw new RuntimeException("There is no list item");
            }
            for (MobileElement mobileElement : menuItemsElem) {
                String itemText = mobileElement.getText();
                String itemHref = mobileElement.getAttribute("href");
                if (itemText.isEmpty()) {
                    System.out.println("Github " + itemHref);
                } else {
                    System.out.println(itemText + ": " + itemHref);
                }
            }

            //Switch back to native context
            appiumDriver.context(Contexts.NATIVE);
            appiumDriver.findElement(MobileBy.AccessibilityId("Home")).click();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
