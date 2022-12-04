package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import src.drivers.AppPackages;
import src.drivers.DriverFactory;
import src.drivers.Platform;

public class HandleMultipleApps {

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

            //Run app in background
//            appiumDriver.runAppInBackground(Duration.ofMillis(3000));

            //Open setting app
            appiumDriver.activateApp(AppPackages.SETTINGS);
            appiumDriver.findElement(MobileBy.xpath("//*[@text='Network & internet']")).click();
            appiumDriver.findElement(MobileBy.xpath("//*[@text='Internet']")).click();

            boolean isWifiOff = appiumDriver.findElements(MobileBy.xpath("//*[@text='Add network']")).isEmpty();
            int timeToToggle = isWifiOff ? 1 : 2;
            MobileElement toggleElem = appiumDriver.findElement(MobileBy.id("android:id/switch_widget"));
            for (int toggleTime = 0; toggleTime < timeToToggle; toggleTime++) {
                toggleElem.click();
            }

            //Come back to the main app
            appiumDriver.activateApp(AppPackages.WEBDRIVER_IO);
            appiumDriver.findElement(MobileBy.xpath("//*[@text='OK']")).click();

            //Debug only
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appiumDriver.quit();
    }
}
