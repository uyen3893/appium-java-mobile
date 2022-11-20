package src.tests.gson;

import com.google.gson.Gson;
import src.test_data.models.LoginCred;
import src.test_data.utils.DataObjectBuilder;
import sun.rmi.runtime.Log;

import java.util.Arrays;
import java.util.List;

public class TestGson {

    public static void main(String[] args) {
        Gson gson = new Gson();

        //From Json to Object
//        String jsonStr = "{\n" +
//                " \"username\": \"Teo\", \n" +
//                " \"password\": \"password\"\n" +
//                "}";
//        LoginCred loginCred = gson.fromJson(jsonStr, LoginCred.class);

        String fileLocation = "/src/test/java/src/tests/gson/login.json";
//        LoginCred loginCred = DataObjectBuilder.buildDataObject(fileLocation, LoginCred.class);
//        System.out.println(loginCred);

        //An Array
        LoginCred[] loginCreds = DataObjectBuilder.buildDataObject(fileLocation, LoginCred[].class);
        for (LoginCred loginCred : loginCreds) {
            System.out.println(loginCred);
        }

        //A list
        List<LoginCred> loginCreds1 = Arrays.asList(DataObjectBuilder.buildDataObject(fileLocation, LoginCred[].class));

        //From Object to Json
        LoginCred loginCred1 = new LoginCred("Ti", "password1");
        System.out.println(gson.toJson(loginCred1));
    }
}
