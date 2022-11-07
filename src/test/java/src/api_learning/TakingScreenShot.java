package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.internal.CapabilityHelpers;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import src.drivers.DriverFactory;
import src.drivers.Platform;

import java.io.File;

public class TakingScreenShot {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        try {
            MobileElement navLoginBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("Login"));
            navLoginBtnElem.click();

            //Whole screen
            File base64ScreenshotData = appiumDriver.getScreenshotAs(OutputType.FILE);
            String fileLocation = System.getProperty("user.dir").concat("/screenshots/").concat("Loginscreen.png");
            FileUtils.copyFile(base64ScreenshotData, new File(fileLocation));

            //An area
            MobileElement loginFormElem = appiumDriver.findElement(MobileBy.AccessibilityId("Login-screen"));
            File base64LoginFormData = loginFormElem.getScreenshotAs(OutputType.FILE);
            String loginFormFileLocation = System.getProperty("user.dir").concat("/screenshots/").concat("LoginFormScreen.png");
            FileUtils.copyFile(base64LoginFormData, new File(loginFormFileLocation));

            //An element
            MobileElement loginBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("button-LOGIN"));
            File base64LoginBtnData = loginBtnElem.getScreenshotAs(OutputType.FILE);
            String loginBtnFileLocation = System.getProperty("user.dir").concat("/screenshots/").concat("LoginBtnScreen.png");
            FileUtils.copyFile(base64LoginBtnData, new File(loginBtnFileLocation));

            //Detect platform (Variant behavior)
            Capabilities capabilities = appiumDriver.getCapabilities();
            String platform = CapabilityHelpers.getCapability(capabilities, "platformName", String.class);
            System.out.println("Current platform: " + platform);

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
