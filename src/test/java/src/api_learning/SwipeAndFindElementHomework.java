package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.drivers.DriverFactory;
import src.drivers.Platform;

import java.time.Duration;
import java.util.List;

public class SwipeAndFindElementHomework {

    public static void main(String[] args) {

        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);

        try {
            //Navigate to the Swipe page
            appiumDriver.findElement(MobileBy.AccessibilityId("Swipe")).click();

            //Wait for loading page
            WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator(
                    "new UiSelector().textContains(\"Swipe Horizontal\")")));

            //Get screen size
            Dimension screenSize = appiumDriver.manage().window().getSize();
            int height = screenSize.getHeight();
            int width = screenSize.getWidth();

            int xStartPoint = 90 * width / 100;
            int xEndPoint = 10 * width / 100;
            int yStartPoint = 70 * height / 100;
            int yEndPoint = 70 * height / 100;

            PointOption startPoint = new PointOption<>().withCoordinates(xStartPoint, yStartPoint);
            PointOption endPoint = new PointOption<>().withCoordinates(xEndPoint, yEndPoint);

            TouchAction touchAction = new TouchAction<>(appiumDriver);

            for (int index = 1; index <= 6; index++) {
                //Get text of each card
                List<MobileElement> cardText = appiumDriver.findElements(MobileBy.xpath("" +
                        "(//android.view.ViewGroup[@content-desc=\"slideTextContainer\"])/android.widget.TextView[1]"));
                if(cardText.isEmpty()) {
                    throw new RuntimeException("There is no card!");
                }
                String text = cardText.get(cardText.size() - 1).getText();
                System.out.println("Current card " + text);

                //Compare text with the target
                String expectedText = "EXTENDABLE";
                if (text.contains(expectedText)) {
                    System.out.println("Target Card named " + text + " appears at " + index + "th place");
                    break;
                }

                //Swipe if text is not found
                if (index <= 5) {
                    touchAction
                            .press(startPoint)
                            .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                            .moveTo(endPoint)
                            .release()
                            .perform();
                // Log error after swipe 5 times
                } else {
                    System.out.println("Cannot found the target card named " + expectedText);
                }
            }

            //Only debug purpose
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
