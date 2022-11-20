package src.test_flows.authentication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.Assert;
import src.models.components.login.LoginFormComponentMod03;
import src.models.pages.LoginScreenMod03;
import src.test_flows.BaseFlow;

public class LoginFlow extends BaseFlow {
    private String username;
    private String password;

    public LoginFlow(AppiumDriver<MobileElement> appiumDriver, String username, String password) {
        super(appiumDriver);
        this.username = username;
        this.password = password;
    }

    public void login() {
        LoginScreenMod03 loginScreenMod03 = new LoginScreenMod03(appiumDriver);
        LoginFormComponentMod03 loginFormComp = loginScreenMod03.loginFormComponentMod03();

        if (!username.isEmpty()) {
            loginFormComp.inputUsername(username);
        }
        if (!password.isEmpty()) {
            loginFormComp.inputPassword(password);
        }
        loginFormComp.clickOnLoginBtn();
    }

    public void verifyLogin() {
        boolean isEmailValid = isEmailValid();
        boolean isPasswordValid = isPasswordValid();

        LoginFormComponentMod03 loginFormComp = new LoginScreenMod03(appiumDriver).loginFormComponentMod03();
        if (isEmailValid && isPasswordValid) {
            verifyCorrectLoginCreds();
        }

        if (!isEmailValid) {
            verifyIncorrectEmail(loginFormComp);
        }

        if (!isPasswordValid) {
            verifyIncorrectPassword(loginFormComp);
        }
    }

    private boolean isEmailValid() {
        return EmailValidator.getInstance().isValid(username);
    }

    private boolean isPasswordValid() {
        return password.length() >= 8;
    }

    private void verifyCorrectLoginCreds() {
        boolean isLoginUnsuccessful = appiumDriver.findElements(MobileBy.xpath("//*[@text='Success']")).isEmpty();
        Assert.assertFalse(isLoginUnsuccessful, "[ERR] Login unsuccessfully");
//        if (!isLoginUnsuccessful) {
//            System.out.println("Login Successfully!");
//        } else {
//            System.out.println("[ERR] Login unsuccessfully");
//        }
    }

    private void verifyIncorrectEmail(LoginFormComponentMod03 loginFormComp) {
        String expectedInvalidEmailText = "Please enter a valid email address";
        String actualInvalidEmailText = loginFormComp.invalidEmailText();

        System.out.println("actualInvalidEmailText: " + actualInvalidEmailText);
        System.out.println("expectedInvalidEmailText: " + expectedInvalidEmailText);
        Assert.assertEquals(actualInvalidEmailText, expectedInvalidEmailText, "[ERR] ...");
    }

    private void verifyIncorrectPassword(LoginFormComponentMod03 loginFormComp) {
        String expectedInvalidPasswordText = "Please enter at least 8 characters";
        String actualInvalidPasswordText = loginFormComp.invalidPasswordText();

        System.out.println("actualInvalidPasswordText: " + actualInvalidPasswordText);
        System.out.println("expectedInvalidPasswordText: " + expectedInvalidPasswordText);
        Assert.assertEquals(actualInvalidPasswordText, expectedInvalidPasswordText, "[ERR] ...");
    }
}
