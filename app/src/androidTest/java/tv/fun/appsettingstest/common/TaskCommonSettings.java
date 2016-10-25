package tv.fun.appsettingstest.common;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

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

}
