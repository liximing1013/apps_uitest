package tv.fun.appsautotest.common;

import android.support.test.uiautomator.UiDevice;

/**
 * Created by zhengjin on 2016/8/30.
 *
 * Include the common used UI acitons
 */
public final class UiActions {

    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 8;

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        Utils.systemWait(WAIT);
    }
}
