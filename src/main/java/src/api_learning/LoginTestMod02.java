package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import src.drivers.DriverFactory;
import src.drivers.Platform;
import src.models.components.login.LoginFormComponentMod02;
import src.models.pages.LoginScreenMod02;

public class LoginTestMod02 {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.android);
        try {
            LoginScreenMod02 loginScreenMod02 = new LoginScreenMod02(appiumDriver);
            loginScreenMod02.bottomNavComponent().loginIconElem().click();
            LoginFormComponentMod02 loginFormComponentMod02 = loginScreenMod02.loginFormComponentMod02();
            loginFormComponentMod02.inputUsername("email@email.com");
            loginFormComponentMod02.inputPassword("password");
            loginFormComponentMod02.clickOnLoginBtn();

        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
