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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.appsettingstest.common.TaskCommonSettings;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.CLASS_TEXT_VIEW;
import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;
import static tv.fun.common.Constants.SETTINGS_HOME_ACTIVITY;
import static tv.fun.common.Constants.SETTINGS_PKG_NAME;

/**
 * Created by zhengjin on 2016/10/25.
 * <p>
 * TestCommonSettings
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestCommonSettings {

    private UiDevice mDevice;
    private TaskCommonSettings mTask;
    private long mExecTime;
    private String mErrorStack = null;

    private String[] TEXT_WALLPAPERS = {"神秘紫光", "霞光黄昏", "静谧月夜", "朦胧山色"};

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
    public void SET_Common_01_01_SettingsPageTitle() {
        try {
            UiObject2 settingsTitle =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_title"));
            Utils.writeCaseResult("Verify the title name of common settings page.",
                    "通用设置".equals(settingsTitle.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_01_02_DeviceNameDefaultValue() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_01_03_DeviceNameSubValues() {
        try {
            mDevice.pressEnter();
            SystemClock.sleep(Constants.SHORT_WAIT);

            String message = "Verify the item %s in device name menu.";
            String[] subDeviceNames = {"风行电视", "客厅的电视", "卧室的电视", "书房的电视", "自定义"};
            for (String deviceName : subDeviceNames) {
                UiObject2 subDeviceName = mDevice.findObject(By.text(deviceName));
                Utils.writeCaseResult(String.format(message, deviceName),
                        subDeviceName != null, mExecTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_01_04_SelectDeviceName() {
        try {
            mDevice.pressEnter();
            SystemClock.sleep(Constants.SHORT_WAIT);

            String subDeviceName = "书房的电视";
            UiObject2 deviceName = mDevice.findObject(By.text(subDeviceName));
            deviceName.click();
            SystemClock.sleep(Constants.SHORT_WAIT);

            UiObject2 deviceNameContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_name"));
            UiObject2 deviceNameValue = deviceNameContainer.findObject(By.text(subDeviceName));
            Utils.writeCaseResult("Verify select a pre-defined device name.",
                    deviceNameValue != null, mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Ignore
    public void SET_Common_05_01_DefaultLocationOnSettings() {
        try {
            UiObject2 locationItemContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_locate"));
            UiObject2 locationItemKey =
                    locationItemContainer.findObject(By.res("tv.fun.settings:id/item_title"));
            Utils.writeCaseResult("Verify the location item key text.",
                    "天气位置".equals(locationItemKey.getText()), mExecTime);

            UiObject2 locationItemValue =
                    locationItemContainer.findObject(By.res("tv.fun.settings:id/item_value"));
            Utils.writeCaseResult("Verify the location item default value text on Common Settings.",
                    "湖北 武汉".equals(locationItemValue.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Ignore
    public void SET_Common_05_02_OpenWeatherAppAndBackToSettings() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_locate"));
            mDevice.pressEnter();
            Utils.waitForPackageOpened(mDevice, "tv.fun.weather");
            Utils.writeCaseResult("Verify open Weather home.",
                    "tv.fun.weather".equals(mDevice.getCurrentPackageName()), mExecTime);

            mDevice.pressBack();
            Utils.waitForPackageOpened(mDevice, "tv.fun.settings");
            Utils.writeCaseResult("Verify back to Settings home.",
                    "tv.fun.settings".equals(mDevice.getCurrentPackageName()), mExecTime);

        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_09_01_SleepTimeDefaultValue() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_advanced"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 sleepSettingContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_sleep"));
            UiObject2 itemKey =
                    sleepSettingContainer.findObject(By.res("tv.fun.settings:id/item_title"));
            Utils.writeCaseResult("Verify the key text of sleep setting.",
                    "休眠设置".equals(itemKey.getText()), mExecTime);

            UiObject2 itemValueContainer = sleepSettingContainer.findObject(
                    By.res("tv.fun.settings:id/setting_item_value"));
            UiObject2 itemValue =
                    itemValueContainer.findObject(By.clazz("android.widget.TextView"));
            Utils.writeCaseResult("Verify the default value of sleep setting.",
                    "永不休眠".equals(itemValue.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_09_02_SelectSleepTime() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_advanced"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);
            UiObject2 sleepSettingContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_sleep"));
            UiObject2 itemValue = mTask.getTextViewOfSwitcher(sleepSettingContainer);

            Utils.writeCaseResult("Verify select the sleep time.",
                    "30分钟".equals(itemValue.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_17_01_DefaultWallpaper() {
        try {
            UiObject2 itemWallpaper =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_wallpaper"));

            String message = "Verify the item key for wallpaper setting item.";
            UiObject2 itemKey = itemWallpaper.findObject(By.res("tv.fun.settings:id/item_title"));
            Assert.assertEquals(message, "壁纸", itemKey.getText());
            message = "Verify the default wallpaper.";
            UiObject2 itemValue = itemWallpaper.findObject(By.res("tv.fun.settings:id/item_value"));
            Assert.assertEquals(message, TEXT_WALLPAPERS[0], itemValue.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_18_01_SubWallpapersOnSelectPage() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_wallpaper"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            String message = "Verify there are 4 sub wallpapers on wallpaper select page.";
            List<UiObject2> wallpapers = mDevice.findObjects(By.clazz(CLASS_TEXT_VIEW));
            Assert.assertEquals(message, TEXT_WALLPAPERS.length, wallpapers.size());

            message = "Verify the 1st sub wallpaper is default selected.";
            UiObject2 defaultWallpaper =
                    mDevice.findObject(By.text(TEXT_WALLPAPERS[0])).getParent();
            Assert.assertTrue(message, defaultWallpaper.isSelected());

            for (UiObject2 wallpaper : wallpapers) {
                String title = wallpaper.getText();
                message = String.format("Verify the sub wallpaper %s is shown.", title);
                Assert.assertTrue(message, this.IsSubWallpaperIncluded(title));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_18_02_SelectWallpaper() {
        try {
            final String selectWallpaper = TEXT_WALLPAPERS[2];

            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_wallpaper"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);
            mTask.selectSpecifiedSubWallpaper(selectWallpaper);

            String message = "Verify setting item value is changed to the selected wallpaper.";
            mDevice.pressBack();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 itemWallpaper =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_wallpaper"));
            UiObject2 itemValue = itemWallpaper.findObject(By.res("tv.fun.settings:id/item_value"));
            Assert.assertEquals(message, selectWallpaper, itemValue.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_19_01_ScreenSaverDefaultValue() {
        try {
            UiObject2 screenSaverContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_screen_saver"));
            UiObject2 itemValue = mTask.getTextViewOfSwitcher(screenSaverContainer);

            Utils.writeCaseResult("Verify the default value of screen saver.",
                    "5分钟（默认）".equals(itemValue.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_19_02_SelectScreenSaver() {
        try {
            mTask.moveToSpecifiedSettingsItem(
                    By.res("tv.fun.settings:id/setting_item_screen_saver"));
            this.mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 screenSaverContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_screen_saver"));
            UiObject2 itemValue = mTask.getTextViewOfSwitcher(screenSaverContainer);
            Utils.writeCaseResult("Verify the selected value of screen saver.",
                    "关闭".equals(itemValue.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_24_01_InstallUnknownAppDefaultValue() {
        try {
            UiObject2 installUnknownAppItemContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_unkown_source"));
            UiObject2 valueText = mTask.getTextViewOfSwitcher(installUnknownAppItemContainer);

            Utils.writeCaseResult(
                    "Verify the default value text of install unknown app settings item.",
                    "禁止".equals(valueText.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_24_02_SelectAllowedInstallUnknownAppAndCancel() {
        try {
            mTask.moveToSpecifiedSettingsItem(
                    By.res("tv.fun.settings:id/setting_item_unkown_source"));
            this.mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            UiObject2 dialogTitle = mDevice.findObject(By.res("tv.fun.settings:id/dialog_title"));
            Utils.writeCaseResult("Verify the text of common dialog title.",
                    "安装未知应用".equals(dialogTitle.getText()), mExecTime);

            UiObject2 cancelBtn =
                    mDevice.findObject(By.res("tv.fun.settings:id/dialog_btn_cancel"));
            cancelBtn.click();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 installUnknownAppItemContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_unkown_source"));
            UiObject2 valueText = mTask.getTextViewOfSwitcher(installUnknownAppItemContainer);
            Utils.writeCaseResult("Verify the value text of install unknown app settings item.",
                    "禁止".equals(valueText.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_25_01_SelectAllowedInstallUnknownAppAndConfirm() {
        try {
            mTask.moveToSpecifiedSettingsItem(
                    By.res("tv.fun.settings:id/setting_item_unkown_source"));
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.settings:id/dialog_btn_confirm"));
            confirmBtn.click();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 installUnknownAppItemContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_unkown_source"));
            UiObject2 valueText = mTask.getTextViewOfSwitcher(installUnknownAppItemContainer);
            Utils.writeCaseResult(
                    "Verify the value text of install unknown app settings item after allowed.",
                    "允许".equals(valueText.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_26_01_SelectForbiddenInstallUnknownApp() {
        try {
            mTask.moveToSpecifiedSettingsItem(
                    By.res("tv.fun.settings:id/setting_item_unkown_source"));
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 installUnknownAppItemContainer =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_unkown_source"));
            UiObject2 valueText = mTask.getTextViewOfSwitcher(installUnknownAppItemContainer);
            Utils.writeCaseResult(
                    "Verify the value text of install unknown app settings item after forbidden.",
                    "禁止".equals(valueText.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_27_01_SystemRecoverDialogAndClickCancel() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_recovery"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 title = mDevice.findObject(By.res("tv.fun.settings:id/recovery_title"));
            Utils.writeCaseResult("Verify the content of recover dialog.",
                    "您的设备将恢复出厂设置".equals(title.getText()), mExecTime);

            UiObject2 cancelBtn =
                    mDevice.findObject(By.res("tv.fun.settings:id/recovery_btn_cancel"));
            Utils.writeCaseResult("Verify the cancel button in recover dialog.",
                    cancelBtn != null, this.mExecTime);

            if (cancelBtn != null) {
                cancelBtn.click();
            }
            SystemClock.sleep(3000L);
            UiObject2 recoverItemOfBack =
                    mDevice.findObject(By.res("tv.fun.settings:id/setting_item_recovery"));
            Utils.writeCaseResult(
                    "Verify back to common settings page after click the cancel button",
                    recoverItemOfBack != null, mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void SET_Common_27_02_SaveInfoOnSystemRecoverDialog() {
        try {
            mTask.moveToSpecifiedSettingsItem(By.res("tv.fun.settings:id/setting_item_recovery"));
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.settings:id/recovery_btn_confirm"));
            Utils.writeCaseResult("Verify the confirm button is default focused.",
                    confirmBtn.isFocused(), mExecTime);

            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 saveInfoCheckbox =
                    mDevice.findObject(By.res("tv.fun.settings:id/recovery_cbx_save_network_info"));
            Utils.writeCaseResult("Verify the saver information checkbox is focused.",
                    saveInfoCheckbox.isFocused(), mExecTime);

            mDevice.pressEnter();
            SystemClock.sleep(Constants.SHORT_WAIT);
            Utils.writeCaseResult("Verify the saver information checkbox is checked.",
                    saveInfoCheckbox.isChecked(), mExecTime);

            mDevice.pressBack();
            SystemClock.sleep(Constants.SHORT_WAIT);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

//    @Test
//    public void test() {
//        TvCommon.printAllMethods(this.getClass().getName());
//    }

    private boolean IsSubWallpaperIncluded(String wallpaper) {
        for (String text : TEXT_WALLPAPERS) {
            if (text.equals(wallpaper)) {
                return true;
            }
        }
        return false;
    }
}
