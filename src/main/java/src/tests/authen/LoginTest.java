package src.tests.authen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.Test;
import src.test_data.models.LoginCred;
import src.test_flows.authentication.LoginFlow;
import src.tests.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        AppiumDriver<MobileElement> appiumDriver = getDriver();
        for (LoginCred loginCred : loginCredDataSets()) {
            LoginFlow loginFlow = new LoginFlow(appiumDriver, loginCred.getUsername(), loginCred.getPassword());
            loginFlow. goToLoginScreen();
            loginFlow.login();
            loginFlow.verifyLogin();
        }
    }

    private List<LoginCred> loginCredDataSets() {
        List<LoginCred> loginCredList = new ArrayList<>();
        loginCredList.add(new LoginCred("", ""));
        loginCredList.add(new LoginCred("email@email.com", "123"));
        loginCredList.add(new LoginCred("email@", "password"));
        loginCredList.add(new LoginCred("email@email.com", "password"));
        return loginCredList;
    }
}
