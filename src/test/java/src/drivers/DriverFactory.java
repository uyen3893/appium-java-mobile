package src.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory implements MobileCapabilityTypeEx, AppPackages {

    private AppiumDriver<MobileElement> appiumDriver;

    public static AppiumDriver<MobileElement> getDriver(Platform platform) {
        AppiumDriver<MobileElement>driver;

        //Create desired Capabilities
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "android");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "UIautomator2");
        desiredCapabilities.setCapability(UDID,"emulator-5554");
        desiredCapabilities.setCapability(APP_PACKAGE, WEBDRIVER_IO);
        desiredCapabilities.setCapability(APP_ACTIVITY, "com.wdiodemoapp.MainActivity");

        // Specify Appium Server URL
        URL appiumServer = null;
        try {
            appiumServer = new URL("http://localhost:4723/wd/hub");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (appiumServer == null) {
            throw new RuntimeException("[ERR] Somehow, we couldn't construct appium server URL");
        }

        switch (platform) {
            case android:
                driver = new AndroidDriver<>(appiumServer,desiredCapabilities);
                break;
            case ios:
                driver = new IOSDriver<>(appiumServer,desiredCapabilities);
                break;
            default:
                throw new IllegalArgumentException("Platform type can't be null!");
        }

        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        return driver;
    }

    public AppiumDriver<MobileElement> getDriver(Platform platform, String udid, String systemPort, String platformVersion) {
        //Get environment variable
        String remoteInfoVarEnvVar = System.getenv("env");
        String remoteInfoVarCommandVar = System.getProperty("env");
        String isRemote = remoteInfoVarEnvVar == null ? remoteInfoVarCommandVar : remoteInfoVarEnvVar;

        if (isRemote == null) {
            throw new IllegalArgumentException("Please provide env variable [env]!");
        }

        String targetServer = "https://localhost:4723/wd/hub";
        if (isRemote.equals("true")) {
            String hubIdAddress = System.getenv("hub");
            if (hubIdAddress == null) {
                hubIdAddress = System.getProperty("hub");
            }
            if (hubIdAddress == null) {
                throw new IllegalArgumentException("Please provide hub id address via env variable [hub]!");
            }
            targetServer = hubIdAddress + ":4444/wd/hub";
        }

        if (appiumDriver == null) {

            // Specify Appium Server URL
            URL appiumServer = null;
            try {
                appiumServer = new URL(targetServer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (appiumServer == null) {
                throw new RuntimeException("[ERR] Somehow, we couldn't construct Appium server URL");
            }
            //Desired Capabilities
            DesiredCapabilities desiredCaps = new DesiredCapabilities();
            desiredCaps.setCapability(PLATFORM_NAME, platform);

            switch (platform) {
                case android:
                    desiredCaps.setCapability(AUTOMATION_NAME, "uiautomator2");
                    desiredCaps.setCapability(UDID, udid);
                    desiredCaps.setCapability(APP_PACKAGE, WEBDRIVER_IO);
                    desiredCaps.setCapability(APP_ACTIVITY, "com.wdiodemoapp.MainActivity");
                    desiredCaps.setCapability(SYSTEM_PORT, systemPort);
                    appiumDriver = new AndroidDriver<>(appiumServer,desiredCaps);
                    break;
                case ios:
                    desiredCaps.setCapability(AUTOMATION_NAME, "XCUITest");
                    desiredCaps.setCapability(DEVICE_NAME, udid);
                    desiredCaps.setCapability(PLATFORM_VERSION, platformVersion);
                    desiredCaps.setCapability(BUNDLE_ID, "ord.wdioNativeDemoApp");
                    desiredCaps.setCapability(WDA_LOCAL_PORT, systemPort);
                    appiumDriver = new IOSDriver<>(appiumServer,desiredCaps);
                    break;
                default:
                    throw new IllegalArgumentException("Platform type can't be null!");
            }

            appiumDriver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        }

        return appiumDriver;
    }

    public void quitAppiumSession() {
        if (appiumDriver != null) appiumDriver.quit();
    }
}
