package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.drivers.DriverFactory;
import src.drivers.Platform;

public class LoginFormTest {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);

        try {
            //Navigate to login form
            MobileElement navLoginBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("Login"));
            navLoginBtnElem.click();

            //Find login form elements
            MobileElement emailBoxElem = appiumDriver.findElement(MobileBy.AccessibilityId("input-email"));
            MobileElement passwordBoxElem = appiumDriver.findElement(MobileBy.AccessibilityId("input-password"));
            MobileElement loginBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("button-LOGIN"));

            //Interact with login form
            emailBoxElem.sendKeys("email@email.com");
            passwordBoxElem.sendKeys("password");
            loginBtnElem.click();

            //Verification
            WebDriverWait wait = new WebDriverWait(appiumDriver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated((MobileBy.id("android:id/alertTitle"))));

            //Print the dialog content
            MobileElement loginDialogElem = appiumDriver.findElement(MobileBy.id("android:id/alertTitle"));
            System.out.println("Dialog Title " + loginDialogElem.getText().trim());

            //Debug purpose
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
