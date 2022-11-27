package src.models.components.login;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginFormComponentMod03 {

    private final AppiumDriver<MobileElement> appiumDriver;
    private static final By usernameSel = MobileBy.AccessibilityId("input-email");
    private static final By incorrectEmailTextSel = MobileBy.xpath("//*[contains(@text,'Please enter a valid email address')]");
    private static final By passwordSel = MobileBy.AccessibilityId("input-password");
    private static final By incorrectPasswordTextSel = MobileBy.xpath("//*[contains(@text, 'Please enter at least 8 characters')]");

    private static final By loginBtnSel = MobileBy.AccessibilityId("button-LOGIN");

    public LoginFormComponentMod03(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    @Step("Input username as {username}")
    public LoginFormComponentMod03 inputUsername(String username) {
        appiumDriver.findElement(usernameSel).sendKeys(username);
        return this;
    }

    public String invalidEmailText() {
        return appiumDriver.findElement(incorrectEmailTextSel).getText();
    }

    @Step("Input password as {password}")
    public LoginFormComponentMod03 inputPassword(String password) {
        appiumDriver.findElement(passwordSel).sendKeys(password);
        return this;
    }

    public String invalidPasswordText() {
        return appiumDriver.findElement(incorrectPasswordTextSel).getText();
    }

    @Step("Click on login button")
    public LoginFormComponentMod03 clickOnLoginBtn() {
        appiumDriver.findElement(loginBtnSel).click();
        return this;
    }
}
