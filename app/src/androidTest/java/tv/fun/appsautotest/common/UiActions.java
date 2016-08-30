package tv.fun.appsautotest.common;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by zhengjin on 2016/8/30.
 *
 * Include the common used UI acitons
 */
public final class UiActions {

    public static final int SHORT_WAIT = 1;
    public static final int WAIT = 5;
    public static final int LONG_WAIT = 8;

    public static void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        Utils.systemWait(WAIT);
    }

    public static void verifyOnLauncherHome(UiDevice device) {
        boolean ret = device.wait(Until.hasObject(By.pkg("com.bestv.ott").depth(0)), WAIT);
        Assert.assertTrue("Verify back to launcher home.", ret);
    }

    public static UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
        List<UiObject2> tabTitles = device.findObjects(By.res("com.bestv.ott:id/title"));
        Assert.assertTrue("Verify tabs on launcher home.", tabTitles.size() > 0);

        UiObject2 retTitle = null;
        for (UiObject2 title : tabTitles) {
            if (tabText.equals(title.getText())) {
                retTitle = title;
            }
        }
        Assert.assertNotNull(retTitle);

        return retTitle;
    }

    public static void openTabFromLauncherHome(UiDevice device, UiObject2 tabText) {
        tabText.getParent().click();
        Utils.systemWait(SHORT_WAIT);

        device.pressEnter();
        device.waitForIdle();
        Utils.systemWait(LONG_WAIT);
    }

}
