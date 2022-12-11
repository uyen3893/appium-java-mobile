package src.api_learning;

import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import src.test_data.models.LoginCred;
import src.test_data.utils.DataObjectBuilder;
import src.test_flows.authentication.LoginFlow;
import src.tests.BaseTest;

public class LoginTestWithBaseTest extends BaseTest {

    @TmsLink("TEST-123")
    @Test(dataProvider = "loginData")
    public void testLogin(LoginCred loginCred) {
        String username = loginCred.getUsername();
        String password = loginCred.getPassword();
        LoginFlow loginFlow = new LoginFlow(getDriver(), username, password);
        loginFlow.goToLoginScreen();
        loginFlow.login();
        loginFlow.verifyLogin();
    }

    @DataProvider(name = "loginData")
    private LoginCred[] loginCredDataSet() {
        String fileLocation = "/src/main/java/src/tests/gson/login.json";
        return DataObjectBuilder.buildDataObject(fileLocation, LoginCred[].class);
    }
}