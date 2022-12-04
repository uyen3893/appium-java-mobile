package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import src.drivers.DriverFactory;
import src.drivers.Platform;

import java.util.List;

public class XpathLearning {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);

        try {
            MobileElement navLoginBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("Login"));
            navLoginBtnElem.click();

            List<MobileElement> credFieldsElem = appiumDriver.findElements(MobileBy.xpath("//android.widget.EditText"));
            final int USERNAME_INDEX = 0;
            final int PASSWORD_INDEX = 1;
            credFieldsElem.get(USERNAME_INDEX).sendKeys("email@email.com");
            credFieldsElem.get(PASSWORD_INDEX).sendKeys("password");

            MobileElement loginInstructionElem = appiumDriver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiSelector().textContains(\"When the device has Touch/FaceID\")"));
            System.out.println(loginInstructionElem.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
