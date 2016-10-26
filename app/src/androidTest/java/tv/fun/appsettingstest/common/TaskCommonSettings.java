package tv.fun.appsettingstest.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import tv.fun.common.Constants;

import static tv.fun.common.Constants.CLASS_TEXT_VIEW;

/**
 * Created by zhengjin on 2016/10/25.
 *
 * TaskCommonSettings
 */

public final class TaskCommonSettings {

    private static TaskCommonSettings instance;
    private UiDevice device;

    private TaskCommonSettings() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public static synchronized TaskCommonSettings getInstance() {
        if (instance == null) {
            instance = new TaskCommonSettings();
        }
        return instance;
    }

    public void moveToSpecifiedSettingsItem(BySelector selector) {
        UiObject2 item = device.findObject(selector);  // find the item from top
        if (item != null) {
            this.moveDownUntilSettingsItemFocused(item);
        } else {  // find the item from bottom
            this.moveToBottomOnCommonSettingsPage();
            item = device.findObject(selector);
            this.moveUpUntilSettingsItemFocused(item);
        }
    }

    private void moveToBottomOnCommonSettingsPage() {
        for (int i = 0; i < 10; i++) {
            device.pressDPadDown();
        }
    }

    private void moveDownUntilSettingsItemFocused(UiObject2 item) {
        this.moveUntilSettingsItemFocused(item, true);
    }

    private void moveUpUntilSettingsItemFocused(UiObject2 item) {
        this.moveUntilSettingsItemFocused(item, false);
    }

    private void moveUntilSettingsItemFocused(UiObject2 item, boolean flagDirectionDown) {
        for (int i = 0, maxMoveTimes = 15; i < maxMoveTimes; i++) {
            if (item.isFocused()) {
                return;
            }
            if (flagDirectionDown) {
                device.pressDPadDown();
            } else {
                device.pressDPadUp();
            }
            SystemClock.sleep(Constants.SHORT_WAIT);
        }
    }

    public UiObject2 getTextViewOfSwitcher(UiObject2 container) {
        UiObject2 switcher =
                container.findObject(By.res("tv.fun.settings:id/setting_item_value"));
        UiObject2 text = switcher.findObject(By.clazz(CLASS_TEXT_VIEW));
        return text;
    }

}
