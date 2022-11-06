package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.drivers.DriverFactory;
import src.drivers.Platform;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

public class FillingALongFormHomework {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            //Navigate to the Form page
            appiumDriver.findElement(MobileBy.AccessibilityId("Forms")).click();

            //Wait for the loading page
            WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator(
                    "new UiSelector().textContains(\"Form Components\")")));

            //Input field
            String keyword = "Username";
            appiumDriver.findElement(MobileBy.AccessibilityId("text-input")).sendKeys(keyword);

            //Check you have typed field
            String actualText = appiumDriver.findElement(MobileBy.AccessibilityId("input-text-result")).getText().trim();
            if (actualText.equalsIgnoreCase(keyword) == false) {
                throw new RuntimeException("Value of you have typed field is different than Input field!");
            }

            //Turn off the switch
            String statusSwitch = appiumDriver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiSelector().textContains(\"turn the switch\")")).getText();
            if (statusSwitch.contains("ON")) {
                appiumDriver.findElement(MobileBy.AccessibilityId("switch"));
            }

            //Get Screen size
            Dimension screenSize = appiumDriver.manage().window().getSize();
            int width = screenSize.getWidth();
            int height = screenSize.getHeight();

            int xStartPoint = 50 * width / 100;
            int xEndPoint = 50 * width / 100;
            int yStartPoint = 50 * height / 100;
            int yEndPoint = 10 * height / 100;

            //Swipe up
            PointOption startPoint = new PointOption<>().withCoordinates(xStartPoint, yStartPoint);
            PointOption endPoint = new PointOption<>().withCoordinates(xStartPoint, yEndPoint);

            TouchAction touchAction = new TouchAction<>(appiumDriver);
            touchAction
                    .press(startPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(endPoint)
                    .release()
                    .perform();

            //Click on the dropdown
            By dropdown = MobileBy.AccessibilityId("Dropdown");
            appiumDriver.findElement(dropdown).click();

            //Wait for the dropdown list appears
//            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));

            //Select option
            MobileElement optionPopup = appiumDriver.findElement(MobileBy.id("com.wdiodemoapp:id/select_dialog_listview"));
            List<MobileElement> optionsList = optionPopup.findElements(MobileBy.id("android:id/text1"));
            if (optionsList.isEmpty()) {
                throw new RuntimeException("There is no options on dropdown!");
            } else {
                int random = new SecureRandom().nextInt(optionsList.size());
                if (random == 1) {
                    random += 1;
                }
                optionsList.get(random).click();
            }

            Thread.sleep(3000);

            //Is Inactive button appeared?
            List<MobileElement> inactiveBtnElem = appiumDriver.findElements(MobileBy.AccessibilityId("button-Inactive"));
            if (inactiveBtnElem.isEmpty()) {
                throw new RuntimeException("Inactive button is not appeared!");
            }

            //Click on Active button
            appiumDriver.findElement(MobileBy.AccessibilityId("button-Active")).click();

            //Click on OK button
            appiumDriver.findElement(MobileBy.id("android:id/button1")).click();

            //Only debug purpose
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
