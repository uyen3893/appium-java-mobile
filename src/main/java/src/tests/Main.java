package src.tests;

import com.google.common.reflect.ClassPath;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import src.drivers.MobileCapabilityTypeEx;
import src.drivers.Platform;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class Main implements MobileCapabilityTypeEx {

    @SuppressWarnings("UnstableApiUsage")
    public static void main(String[] args) throws IOException {

        //Get all test classes
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> testClasses = new ArrayList<>();

        for (ClassPath.ClassInfo info : ClassPath.from(classLoader).getTopLevelClasses()) {
            String classInfoName = info.getName();
            boolean startWithTestDot = classInfoName.startsWith("src.tests.");
            boolean isBaseTestClass = classInfoName.startsWith("src.tests.BaseTest");
            boolean isMainTestClass = classInfoName.startsWith("src.tests.Main");

            if (startWithTestDot && !isBaseTestClass && !isMainTestClass) {
                testClasses.add(info.load());
            }
        }

        //Get platform
        String platformName = System.getProperty("platform");
//        String platformName = System.getenv("platform");
        if (platformName == null) {
            throw new IllegalArgumentException("Please provide platform name via -Dplatform");
        }

        try {
            Platform.valueOf(platformName);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERR] Don't support platform " + platformName + ", supported platform: " + Arrays.toString(Platform.values()));
        }

        //Devices under test
        List<String> iphoneDeviceList = Arrays.asList("iPhone 12", "iPhone 13");
        List<String> androidDeviceList = Arrays.asList("emulator-5554", "emulator-5556");
        List<String> deviceList = platformName.equalsIgnoreCase("ios") ? iphoneDeviceList : androidDeviceList;

        //Assign test class into devices
        final int testNumEachDevice = testClasses.size() / deviceList.size();
        Map<String, List<Class<?>>> desiredCaps = new HashMap<>();

        for (int deviceIndex = 0; deviceIndex < deviceList.size(); deviceIndex++) {
            int startIndex = deviceIndex * testNumEachDevice;
            boolean isLastDevice = deviceIndex == deviceList.size() - 1;
            int endIndex = isLastDevice ? testClasses.size() : (startIndex + testNumEachDevice);
            List<Class<?>> subTestList = testClasses.subList(startIndex, endIndex);
            desiredCaps.put(deviceList.get(deviceIndex), subTestList);
        }

        //Build dynamic test suite
        TestNG testNG = new TestNG();
        XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setName("Regression");

        List<XmlTest> allTests = new ArrayList<>();
        for (String deviceName : desiredCaps.keySet()) {
            XmlTest xmlTest = new XmlTest(xmlSuite);
            xmlTest.setName(deviceName);
            List<XmlClass> xmlClass = new ArrayList<>();
            List<Class<?>> dedicatedClasses = desiredCaps.get(deviceName);
            for (Class<?> dedicatedClass : dedicatedClasses) {
                xmlClass.add(new XmlClass(dedicatedClass.getName()));
            }

            xmlTest.setXmlClasses(xmlClass);
            xmlTest.addParameter(UDID, deviceName);
            xmlTest.addParameter(PLATFORM_NAME, platformName);
            xmlTest.addParameter(PLATFORM_VERSION, "15.0");
            xmlTest.addParameter(SYSTEM_PORT, String.valueOf(new SecureRandom().nextInt(1000) + 8300));
            allTests.add(xmlTest);
        }

        xmlSuite.setTests(allTests);
        xmlSuite.setParallel(XmlSuite.ParallelMode.TESTS);
        xmlSuite.setThreadCount(10);

        System.out.println(xmlSuite.toXml());

        //Add test suite into suite list
        List<XmlSuite> xmlSuites = new ArrayList<>();
        xmlSuites.add(xmlSuite);

        //Invoke run method
        testNG.setXmlSuites(xmlSuites);
        testNG.run();
    }
}
