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

import java.time.Duration;
import java.util.List;

public class NarrowDownSearchingScope {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            MobileElement navFormsBtnElem = appiumDriver.findElement(MobileBy.AccessibilityId("Forms"));
            navFormsBtnElem.click();

            //Wait until we are on the new screen after navigating
            WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator(
                    "new UiSelector().textContains(\"Form Components\")")));

            Dimension windowSize = appiumDriver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenWidth = windowSize.getWidth();

            int xStartPoint = 50 * screenWidth / 100;
            int yStartPoint = 0;
            int xEndPoint = 50 * screenWidth / 100;
            int yEndPoint = 50 * screenHeight / 100;

            PointOption startPoint = new PointOption<>().withCoordinates(xStartPoint, yStartPoint);
            PointOption endPoint = new PointOption<>().withCoordinates(xEndPoint, yEndPoint);

            TouchAction touchAction = new TouchAction<>(appiumDriver);
            touchAction
                    .press(startPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(endPoint)
                    .release()
                    .perform();

            List<MobileElement> notificationElem = appiumDriver.findElements(MobileBy.xpath("//android.widget.LinearLayout[@resource-id=\"com.android.systemui:id/quick_qs_panel\"]//android.widget.Switch"));
            if(notificationElem.isEmpty()) {
                throw new RuntimeException("There is no notification!");
            } else {
                for (MobileElement notiElem : notificationElem) {
                    MobileElement notiLabelElem = notiElem.findElement(MobileBy.id("com.android.systemui:id/tile_label"));
                    System.out.println(notiLabelElem.getText());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
