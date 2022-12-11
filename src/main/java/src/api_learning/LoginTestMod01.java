package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import src.drivers.DriverFactory;
import src.drivers.Platform;
import src.models.components.login.LoginFormComponent;
import src.models.pages.LoginScreenMod01;

public class LoginTestMod01 {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);
        try {
            LoginScreenMod01 loginScreenMod01 = new LoginScreenMod01(appiumDriver);
            loginScreenMod01.bottomNavComponent().loginIconElem().click();
            LoginFormComponent loginFormComponent = loginScreenMod01.loginFormComponent();

            loginFormComponent.usernameElem().sendKeys("email@email.com");
            loginFormComponent.passwordElem().sendKeys("password");
            loginFormComponent.loginBtnElem().click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
