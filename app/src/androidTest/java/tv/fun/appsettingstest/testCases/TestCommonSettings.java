package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.fun.appsettingstest.common.TaskCommonSettings;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;
import static tv.fun.common.Constants.SETTINGS_HOME_ACTIVITY;
import static tv.fun.common.Constants.SETTINGS_PKG_NAME;

/**
 * Created by zhengjin on 2016/10/25.
 *
 * TestCommonSettings
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestCommonSettings {

    private UiDevice mDevice;
    private TaskCommonSettings mTask;
    private long mExecTime;

    @BeforeClass
    public static void classSetup() {
        Utils.stopAndClearProcess(SETTINGS_PKG_NAME);
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskCommonSettings.getInstance();
        mExecTime = Utils.getCurSecond();

        mDevice.pressHome();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(SETTINGS_PKG_NAME, SETTINGS_HOME_ACTIVITY);
        Utils.waitForPackageOpened(mDevice, SETTINGS_PKG_NAME);

        mDevice.pressDPadUp();
        SystemClock.sleep(Constants.SHORT_WAIT);
    }

    @After
    public void clearUp() {
        Utils.stopProcess(SETTINGS_PKG_NAME);
    }

    @Test
    public void SET_Common_01_01_testSettingsPageTitle() {
        UiObject2 settingsTitle = mDevice.findObject(By.res("tv.fun.settings:id/setting_title"));
        Utils.writeCaseResult("Verify the title name of common settings page.",
                "通用设置".equals(settingsTitle.getText()), mExecTime);
    }

    @Test
    public void SET_Common_01_02_testDeviceNameDefaultValue() {
        UiObject2 deviceNameContainer =
                mDevice.findObject(By.res("tv.fun.settings:id/setting_item_name"));
        Utils.writeCaseResult("Verify the device name item is focused as default.",
                deviceNameContainer.isFocused(), mExecTime);

        UiObject2 deviceNameKey = deviceNameContainer.findObject(By.text("设备名称"));
        Utils.writeCaseResult("Verify the device name key text.",
                deviceNameKey != null, mExecTime);

        UiObject2 deviceNameValue = deviceNameContainer.findObject(By.text("风行电视"));
        Utils.writeCaseResult("Verify the device name value text.",
                deviceNameValue != null, mExecTime);
    }

    @Test
    public void SET_Common_01_03_testDeviceNameSubValues() {
        mDevice.pressEnter();
        SystemClock.sleep(Constants.SHORT_WAIT);

        String message = "Verify the item %s in device name menu.";
        String[] subDeviceNames = {"风行电视", "客厅的电视", "卧室的电视", "书房的电视", "自定义"};
        for (String deviceName : subDeviceNames) {
            UiObject2 subDeviceName = mDevice.findObject(By.text(deviceName));
            Utils.writeCaseResult(String.format(message, deviceName),
                    subDeviceName != null, mExecTime);
        }
    }

    @Test
    public void SET_Common_01_04_testSelectDeviceName() {
        mDevice.pressEnter();
        SystemClock.sleep(Constants.SHORT_WAIT);

        // select a sub device name and back
        String subDeviceName = "书房的电视";
        UiObject2 deviceName = mDevice.findObject(By.text(subDeviceName));
        deviceName.click();
        SystemClock.sleep(Constants.SHORT_WAIT);

        UiObject2 deviceNameContainer =
                mDevice.findObject(By.res("tv.fun.settings:id/setting_item_name"));
        UiObject2 deviceNameValue = deviceNameContainer.findObject(By.text(subDeviceName));
        Utils.writeCaseResult("Verify select a pre-defined device name.",
                deviceNameValue != null, mExecTime);
    }

    @Test
    public void SET_Common_09_01_testSleepTimeDefaultValue() {
        UiObject2 sleepSettingContainer =
                mDevice.findObject(By.res("tv.fun.settings:id/setting_item_sleep"));
        UiObject2 itemKey =
                sleepSettingContainer.findObject(By.res("tv.fun.settings:id/item_title"));
        Utils.writeCaseResult("Verify the key text of sleep setting.",
                "休眠设置".equals(itemKey.getText()), mExecTime);

        UiObject2 itemValueContainer =
                sleepSettingContainer.findObject(By.res("tv.fun.settings:id/setting_item_value"));
        UiObject2 itemValue = itemValueContainer.findObject(By.clazz("android.widget.TextView"));
        Utils.writeCaseResult("Verify the default value of sleep setting.",
                "永不休眠".equals(itemValue.getText()), mExecTime);
    }

    @Test
    public void SET_Common_09_02_testSelectSleepTime() {
        mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_sleep"));
        mDevice.pressDPadRight();
        SystemClock.sleep(Constants.SHORT_WAIT);
        mDevice.pressDPadRight();
        SystemClock.sleep(Constants.SHORT_WAIT);

        UiObject2 sleepSettingContainer =
                mDevice.findObject(By.res("tv.fun.settings:id/setting_item_sleep"));
        UiObject2 itemValue = mTask.getTextViewOfSwitcher(sleepSettingContainer);

        Utils.writeCaseResult("Verify select the sleep time.",
                "30分钟".equals(itemValue.getText()), mExecTime);
    }

}
