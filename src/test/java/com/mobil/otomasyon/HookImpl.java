package com.mobil.otomasyon;


import com.mobil.otomasyon.selector.Selector;
import com.mobil.otomasyon.selector.SelectorFactory;
import com.mobil.otomasyon.selector.SelectorType;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;





public class HookImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected static AppiumDriver<MobileElement> appiumDriver;
    protected static FluentWait<AppiumDriver<MobileElement>> appiumFluentWait;
    protected boolean localAndroid = true;
    protected static Selector selector;


    @BeforeScenario
    public void beforeScenario() throws MalformedURLException, InterruptedException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Test basliyor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (StringUtils.isEmpty(System.getenv("key"))) {
            if (localAndroid) {

                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                desiredCapabilities.setCapability(MobileCapabilityType.UDID, "XPH5T19910002801");
                desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.qaotomation");
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Rooted p30");
                desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.example.qaotomation.MainActivity");
                //desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
                desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3000);
                desiredCapabilities.setCapability("unicodeKeyboard", true);
                desiredCapabilities.setCapability("resetKeyboard", true);

                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                appiumDriver = new AndroidDriver(url, desiredCapabilities);
                Thread.sleep(2000);

            } else {

                System.out.println("Bu alan ios icin");
            }
        }



        selector = SelectorFactory.createElementHelper(localAndroid ? SelectorType.ANDROID : SelectorType.IOS);
        appiumFluentWait = new FluentWait<AppiumDriver<MobileElement>>(appiumDriver);
        appiumFluentWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(450))
                .ignoring(NoSuchElementException.class);


        }


    @AfterScenario
    public void afterScenario() throws InterruptedException {
        Thread.sleep(3000);
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.HOME));
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        if (appiumDriver.findElements(By.id("com.huawei.android.launcher:id/clear_all_recents_image_button")).size() > 0) {
            appiumDriver.findElement(By.id("com.huawei.android.launcher:id/clear_all_recents_image_button")).click();
            logger.info("Closed all recent apps..!");
            Thread.sleep(2000);
            ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.HOME));
            Thread.sleep(2000);
        } else {
            ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.BACK));
            logger.info("There isn't any recent app..!");
        }
        if (appiumDriver != null) {
            appiumDriver.quit();
        }
    }

}
