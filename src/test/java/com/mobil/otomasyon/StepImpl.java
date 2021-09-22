package com.mobil.otomasyon;

import com.mobil.otomasyon.helper.*;
import com.mobil.otomasyon.model.SelectorInfo;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import java.time.Duration;
import java.util.List;


public class StepImpl extends HookImpl {


    protected Logger logger = LoggerFactory.getLogger(getClass());


    public List<MobileElement> findElements(By by) throws Exception {
        List<MobileElement> webElementList = null;
        try {
            webElementList = appiumFluentWait.until(new ExpectedCondition<List<MobileElement>>() {
                @Nullable
                @Override
                public List<MobileElement> apply(@Nullable WebDriver driver) {
                    List<MobileElement> elements = driver.findElements(by);
                    return elements.size() > 0 ? elements : null;
                }
            });
            if (webElementList == null) {
                throw new NullPointerException(String.format("by = %s Web element list not found", by.toString()));
            }
        } catch (Exception e) {
            throw e;
        }
        return webElementList;
    }

    public void waitForElement(WebElement element, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public List<MobileElement> findElementsWithoutAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
        }
        return mobileElements;
    }

    public List<MobileElement> findElementsWithAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
            Assertions.fail("by = %s Elements not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElements;
    }


    public MobileElement findElement(By by) throws Exception {
        MobileElement mobileElement;
        try {
            mobileElement = findElements(by).get(0);
        } catch (Exception e) {
            throw e;
        }
        return mobileElement;
    }

    public MobileElement findElementWithoutAssert(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            //   e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementWithAssertion(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            Assertions.fail("by = %s Element not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElement;
    }


    public MobileElement findElementByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Element not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElement;
    }


    public List<MobileElement> findElemenstByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Elements not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElements;
    }






    @Step({"Does <key> element Exist"})
    public void doesExistElement(String key) {

        Assert.assertTrue("Element Does Not Exist!", findElementByKey(key).isDisplayed());
        logger.info(key + " element exists.");
        System.out.println("element exists.");
    }



    @Step({"Swipe up <key> times"})
    public void swipeUp(int key) {


        for (int i=0;i<key;i++) {
            TouchAction action = new TouchAction(appiumDriver);
            action.longPress(PointOption.point(510, 1910))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(510, 500))
                    .release()
                    .perform();

            waitBySecond(2);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("SWIPE EDILDI");
            System.out.println("-----------------------------------------------------------------");
        }

    }


    @Step("Go To Android Home")
    public void androidHomeButton() throws InterruptedException {
        Thread.sleep(4000);

        logger.info("First Screen");
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.HOME));
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));

        Thread.sleep(2000);
        if (appiumDriver.findElements(By.id("com.huawei.android.launcher:id/clear_all_recents_image_button")).size() > 0) {
            appiumDriver.findElement(By.id("com.huawei.android.launcher:id/clear_all_recents_image_button")).click();

            logger.info("Closed all recent apps..!");

            Thread.sleep(2000);

            ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.HOME));
            Thread.sleep(1000);

        } else {
            ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.BACK));

            logger.info("There isn't any recent app..!");

        }

    }


    @Step({"Click element by <key>"})
    public void clickByKey(String key) {
        findElementByKey(key).click();
        logger.info(key + " elementine tıklandı");
        System.out.println(key + "elementine tiklandi");
    }



    @Step("Find element by <key> and send keys <text>")
    public void sendKeysByKey(String key, String text) {
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        webElement.sendKeys(text);
    }




    @Step({"<seconds> saniye bekle", "Wait <second> seconds"})
    public void waitBySecond(int seconds) {
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000);
            logger.info(seconds + " saniye beklendi.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Step({"Set the date"})
    public void setTheDate() throws InterruptedException {

        clickByKey("Trendyol_dateBar");

        waitBySecond(1);
        clickByKey("Trendyol_headerYear");

        waitBySecond(1);

        while(!(appiumDriver.findElements(By.xpath("//android.widget.TextView[@text='1980']")).size() > 0) ){

            TouchAction action = new TouchAction(appiumDriver);
            action.longPress(PointOption.point(545, 860))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(545, 1480))
                    .release()
                    .perform();

        }

        clickByKey("Trendyol_year_1980");

        while(!(appiumDriver.findElements(By.xpath("//android.view.View[@content-desc='03 November 1980']")).size() > 0) ){

            TouchAction action = new TouchAction(appiumDriver);
            action.longPress(PointOption.point(930, 1170))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(60, 1170))
                    .release()
                    .perform();

            if(appiumDriver.findElements(By.xpath("//android.view.View[contains(@content-desc,'1981')]")).size()>0){

                while(!(appiumDriver.findElements(By.xpath("//android.view.View[@content-desc='03 November 1980']")).size() > 0) ){

                    TouchAction action1 = new TouchAction(appiumDriver);
                    action1.longPress(PointOption.point(224, 1170))
                            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                            .moveTo(PointOption.point(990, 1170))
                            .release()
                            .perform();
            }

        }

    }
        clickByKey("Trendyol_year_3Nov1980");
        waitBySecond(2);
        clickByKey("Trendyol_OK_Btn");
        waitBySecond(2);

    }




}


