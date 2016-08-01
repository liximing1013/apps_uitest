package tv.fun.appsautotest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by zhengjin on 2016/7/28.
 *
 * Include the test cases for film.
 */
@RunWith(AndroidJUnit4.class)
public class TestFunTvFilm {

    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testPlayFilm() {
        // back to launcher home
        mDevice.pressHome();
        mDevice.waitForIdle();
        SystemClock.sleep(3000);

        boolean ret = mDevice.wait(Until.hasObject(By.pkg("com.bestv.ott").depth(0)), 8000);
        Assert.assertTrue("Verify back to home", ret);

        // open film page
        List<UiObject2> tabsText = mDevice.findObjects(By.res("com.bestv.ott:id/title"));
        Assert.assertTrue(tabsText.size() > 0);

        UiObject2 filmTabText = null;
        for (UiObject2 text : tabsText) {
            if ("电影".equals(text.getText())) {
                filmTabText = text;
            }
        }
        Assert.assertNotNull(filmTabText);

        filmTabText.getParent().click();
        SystemClock.sleep(1000);
        mDevice.pressEnter();
        mDevice.waitForIdle();
        SystemClock.sleep(5000);

        // select a film, and open film details page
        mDevice.pressDPadLeft();
        mDevice.waitForIdle();
        SystemClock.sleep(3000);

        mDevice.pressDPadDown();
        SystemClock.sleep(1000);
        mDevice.pressDPadLeft();
        SystemClock.sleep(1000);

        mDevice.pressEnter();
        mDevice.waitForIdle();
        SystemClock.sleep(3000);

        UiObject2 filmTitle = mDevice.findObject(By.res("com.bestv.ott:id/detail_title"));
        String filmName = filmTitle.getText();
        Assert.assertFalse(filmName == null || "".equals(filmName));

        // play film
        mDevice.pressEnter();
        SystemClock.sleep(8000);
        ret = mDevice.wait(Until.hasObject(By.clazz(
                "com.funshion.player.play.funshionplayer.VideoViewPlayer")), (15 * 1000));
        Assert.assertTrue(ret);

        // wait 10 seconds and pause
        mDevice.pressEnter();
        SystemClock.sleep(1000);

        String filmNameInPlayer =
                mDevice.findObject(By.res("com.bestv.ott:id/video_player_title")).getText();
        Assert.assertEquals(filmName.trim(), filmNameInPlayer.trim());

        ret = mDevice.wait(Until.hasObject(By.res("com.bestv.ott:id/video_player_time")), 1000);
        Assert.assertTrue(ret);
    }

}
