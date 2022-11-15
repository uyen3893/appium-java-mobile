package src.tests.authen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.Test;
import src.drivers.DriverFactory;
import src.drivers.Platform;
import src.test_data.models.LoginCred;
import src.test_flows.authentication.LoginFlow;

import java.util.ArrayList;
import java.util.List;

public class LoginTest {

    @Test
    public void testLogin() {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        List<LoginCred> loginCredList = new ArrayList<>();
        loginCredList.add(new LoginCred("", ""));
        loginCredList.add(new LoginCred("email@email.com", "123"));
        loginCredList.add(new LoginCred("email@", "password"));
        loginCredList.add(new LoginCred("email@email.com", "password"));
        try {
            for (LoginCred loginCred : loginCredList) {
                LoginFlow loginFlow = new LoginFlow(appiumDriver, loginCred.getUsername(), loginCred.getPassword());
                loginFlow. goToLoginScreen();
                loginFlow.login();
                loginFlow.verifyLogin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}