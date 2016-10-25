package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.fun.appsettingstest.common.TaskCommonSettings;
import tv.fun.common.Common;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;
import static tv.fun.common.Constants.SETTINGS_HOME_ACTIVITY;
import static tv.fun.common.Constants.SETTINGS_PKG_NAME;
import static tv.fun.common.Constants.SHORT_NAME_SETTINGS;

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
    private Common mComm;

    @BeforeClass
    public static void classSetup() {
        Utils.stopAndClearProcess(SETTINGS_PKG_NAME);
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskCommonSettings.getInstance();
        mComm = new Common();

        mComm.Home();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(SETTINGS_PKG_NAME, SETTINGS_HOME_ACTIVITY);
        Utils.waitForPackageOpened(mDevice, SETTINGS_PKG_NAME);

        mComm.Up();
        SystemClock.sleep(1000);
    }

    @After
    public void clearUp() {
        Utils.stopProcess(SETTINGS_PKG_NAME);
    }

    @Test
    public void SET_Common_01_01_testSettingsPageTitle() {
        UiObject2 settingsTitle = mDevice.findObject(By.res("tv.fun.settings:id/setting_title"));
        Assert.assertNotNull(settingsTitle);
        String message = "Verify the title name of common settings page.";
//        Utils.writeCaseResult(SHORT_NAME_SETTINGS, );
        Assert.assertEquals(message, "通用设置", settingsTitle.getText());
    }


}
