package src.models.components.login;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginFormComponentMod03 {

    private final AppiumDriver<MobileElement> appiumDriver;
    private static final By usernameSel = MobileBy.AccessibilityId("input-email");
    private static final By passwordSel = MobileBy.AccessibilityId("input-password");
    private static final By loginBtnSel = MobileBy.AccessibilityId("button-LOGIN");

    public LoginFormComponentMod03(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(15)), this);
    }

    @Step("Input username as {username}")
    public LoginFormComponentMod03 inputUsername(String username) {
        appiumDriver.findElement(usernameSel).sendKeys(username);
        return this;
    }

    @AndroidFindBy(xpath = "//*[contains(@text,'Please enter a valid email address')]")
    @iOSXCUITFindBy(iOSNsPredicate = "label == \"Please enter a valid email address\"")
    private MobileElement incorrectEmailTxtElem;

    public String invalidEmailText() {
        return incorrectEmailTxtElem.getText().trim();
    }

    @AndroidFindBy(xpath = "//*[contains(@text, 'Please enter at least 8 characters')]")
    @iOSXCUITFindBy(iOSNsPredicate = "label == \"Please enter at least 8 characters\"")
    private MobileElement incorrectPasswordTxtElem;

    @Step("Input password as {password}")
    public LoginFormComponentMod03 inputPassword(String password) {
        appiumDriver.findElement(passwordSel).sendKeys(password);
        return this;
    }

    public String invalidPasswordText() {
        return incorrectPasswordTxtElem.getText().trim();
    }

    @Step("Click on login button")
    public LoginFormComponentMod03 clickOnLoginBtn() {
        appiumDriver.findElement(loginBtnSel).click();
        return this;
    }
}
