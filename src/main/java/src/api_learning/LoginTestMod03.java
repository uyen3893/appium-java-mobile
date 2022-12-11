package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import src.drivers.DriverFactory;
import src.drivers.Platform;
import src.models.components.login.LoginFormComponentMod03;
import src.models.pages.LoginScreenMod03;

public class LoginTestMod03 {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);
        try {
            LoginScreenMod03 loginScreenMod03 = new LoginScreenMod03(appiumDriver);
            loginScreenMod03.bottomNavComponent().loginIconElem().click();
            LoginFormComponentMod03 loginFormComponentMod03 = loginScreenMod03.loginFormComponentMod03();
            loginFormComponentMod03.inputUsername("email@email.com").inputPassword("password").clickOnLoginBtn();

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
