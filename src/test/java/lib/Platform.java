package lib;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Platform {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance; //превратим класс Platform в синглтон - чтобы объект класса создавался только один раз и хранился на протяжении всего выполнения кода
    //в одном из полей класса, при этом вне класса создать instance не возможно засчет приватного коструктора

    private Platform() {
    } //приватный конструктор

    public static Platform getInstance() {  //для вызова Platform
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()) {
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/arman/appium/mobile_automation_java/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone X");
        capabilities.setCapability("platformVersion", "11.4");
        capabilities.setCapability("app", "/Users/arman/appium/mobile_automation_java/apks/Wikipedia.app");
        return capabilities;
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}

