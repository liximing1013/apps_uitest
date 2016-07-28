package tv.fun.appsautotest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by zhengjin on 2016/7/28.
 *
 * Include the test cases for film.
 */
@RunWith(AndroidJUnit4.class)
public class TestFunTvFilm {

    private UiDevice mDevice;

    @Test
    public void testPlayFilm() {

        // back to launcher home
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();
        SystemClock.sleep(3000);

        mDevice.waitForIdle();
        boolean ret = mDevice.wait(Until.hasObject(By.pkg("com.bestv.ott").depth(0)), 8000);
        Assert.assertTrue("Verify back to home", ret);
    }

}
