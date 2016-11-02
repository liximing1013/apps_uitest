package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

/**
 * Created by liuqing on 2016/10/24.
 */
public class MasterApp extends Common {

    /**
     * 在Launcher应用tab页面，点击“电视助手”卡片
     */
    public void enterTVMasterPage() throws UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        moveToTargetTab(launcherTabs,appTab, launcherTabID, 4);
        //点击“电视助手”卡片
        UiObject tvMasterCard =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        device.pressDPadDown();
        device.pressDPadDown();
        if(!tvMasterCard.isSelected()){
            device.pressDPadRight();
            device.pressDPadRight();
        }
        device.pressEnter();
    }


    /**
     * Verify not the string.
     *
     * @param actual
     * @param unexpected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotString(String actual, String unexpected)
            throws IOException {
        if (!unexpected.equals(actual)) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Not expected [" + unexpected + "] is displayed. ";
            return false;
        }
    }

    /**
     * Verify the string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyString(String actual, String expected)
            throws IOException {
        if (expected.equalsIgnoreCase(actual)) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Expected [" + expected + "] but actual [" + actual
                    + "]; ";
            return false;
        }
    }

    /**
     * Verify the string include another string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyIncludeString(String actual, String expected)
            throws IOException {
        if (expected.indexOf(actual) > -1) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Expected [" + expected + "] but actual [" + actual
                    + "]; ";
            return false;
        }
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNumber(int actual, int expected) throws IOException {
        if (actual == expected) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Expected [" + expected + "] but actual [" + actual
                    + "]; ";
            return false;
        }
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotNumber(int actual, int expected) throws IOException {
        if (actual != expected) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Expected not [" + expected + "] but actual ["
                    + actual + "]; ";
            return false;
        }
    }
}
