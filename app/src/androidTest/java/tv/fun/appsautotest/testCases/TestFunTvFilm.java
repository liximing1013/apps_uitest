package tv.fun.appsautotest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by zhengjin on 2016/7/28.
 *
 * Test playing film video.
 */
@RunWith(AndroidJUnit4.class)
public class TestFunTvFilm {

    private static final String TAG = TestFunTvFilm.class.getSimpleName();
    private static final String CAPTURES_PATH = "/data/local/tmp/captures";

    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 8;

    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        backToLauncherHome(mDevice);
    }

    @After
    public void clearUp() {
        backToLauncherHome(mDevice);
    }

    @Test
    public void testPlayFilm() {
        UiObject2 tabText = this.getTabFromLauncherHomeByText(mDevice, "电影");
        this.openTabFromLauncherHomeByText(mDevice, tabText);

        String filmTitle = this.randomSelectFilmAndOpenDetails(mDevice);
        this.verifyOpenVideoPlayer(mDevice);
        this.verifyPauseVideoPlayer(mDevice, filmTitle);

        int replayTimes = 3;
        int playTime = 3 * 60;
        for (int i = 0; i < replayTimes; i++) {
            this.verifyFilmPlaying(mDevice, playTime);
        }

        this.resetFilmProcess(mDevice);
        this.verifyPlayerProcessReset(mDevice);
    }

    private UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
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

    private void openTabFromLauncherHomeByText(UiDevice device, UiObject2 tabText) {
        tabText.getParent().click();
        systemWait(SHORT_WAIT);

        device.pressEnter();
        device.waitForIdle();
        systemWait(LONG_WAIT);
    }

    private String randomSelectFilmAndOpenDetails(UiDevice device) {
        device.pressDPadLeft();
        device.waitForIdle();
        systemWait(LONG_WAIT);

        device.pressDPadDown();
        systemWait(WAIT);

        int moveTimes = new Random().nextInt(15);
        for (int j = 0; j <= moveTimes; j++) {
            device.pressDPadRight();
            systemWait(SHORT_WAIT);
        }

        device.pressEnter();
        device.waitForIdle();
        systemWait(LONG_WAIT);

        String filmTitle = device.findObject(By.res("com.bestv.ott:id/detail_title")).getText();
        boolean ret = ((filmTitle != null) && (!"".equals(filmTitle)));
        assertAndCaptureForFailed(ret, "Verify film title of details page.");

        return filmTitle;
    }

    private void verifyOpenVideoPlayer(UiDevice device) {
        int waitPlayerOnTop = 15;
        device.pressEnter();
        systemWait(waitPlayerOnTop);

        boolean ret = device.wait(Until.hasObject(By.clazz(
                "com.funshion.player.play.funshionplayer.VideoViewPlayer")), waitPlayerOnTop);
        assertAndCaptureForFailed(ret, "Verify player is open and on the top.");
    }

    private void verifyPauseVideoPlayer(UiDevice device, String filmTitle) {
        // pause player
        device.pressEnter();
        systemWait(SHORT_WAIT);

        String title = device.findObject(By.res("com.bestv.ott:id/video_player_title")).getText();
        Log.d(TAG, String.format("Play film: %s", title));
        boolean ret = (title.trim().equals(filmTitle.trim()));
        assertAndCaptureForFailed(ret, "Verify film title when pause player.");

        UiObject2 pauseBtn = device.findObject(
                By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
        assertAndCaptureForFailed((pauseBtn != null), "Verify pause icon when pause player.");

        UiObject2 seekBar = device.findObject(By.res("com.bestv.ott:id/media_progress"));
        assertAndCaptureForFailed((seekBar != null), "Verify seek bar when pause player.");

        UiObject2 totalTime = device.findObject(By.res("com.bestv.ott:id/time_total"));
        ret = totalTime.getText().contains(":");
        assertAndCaptureForFailed(ret, "Verify film total time when pause player.");
    }

    private void verifyFilmPlaying(UiDevice device, int playTime) {
        String curTime = device.findObject(By.res("com.bestv.ott:id/time_current")).getText();
        Log.d(TAG, String.format("Current play time: %s", curTime));
        int startTime = formatFilmPlayTime(curTime);

        // play film
        device.pressEnter();
        systemWait(playTime);

        // pause player
        device.pressEnter();
        systemWait(SHORT_WAIT);
        curTime = device.findObject(By.res("com.bestv.ott:id/time_current")).getText();
        int endTime = formatFilmPlayTime(curTime);

        int buffer = 5;
        boolean ret = ((endTime - startTime) >= (playTime - buffer));
        assertAndCaptureForFailed(ret, "Verify playing film.");
    }

    private void verifyPlayerProcessReset(UiDevice device) {
        // pause player
        device.pressEnter();
        systemWait(SHORT_WAIT);

        String curTime = device.findObject(By.res("com.bestv.ott:id/time_current")).getText();
        Log.d(TAG, String.format("The player process is reset to %s", curTime));

        int timeAfterReset = 30;
        int playTime = this.formatFilmPlayTime(curTime);
        this.assertAndCaptureForFailed(
                (playTime <= timeAfterReset), "Verify player process is reset.");
    }

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);

        boolean ret = device.wait(Until.hasObject(By.pkg("com.bestv.ott").depth(0)), WAIT);
        Assert.assertTrue("Verify back to launcher home.", ret);
    }

    private void resetFilmProcess(UiDevice device) {
        // play film
        device.pressEnter();
        this.systemWait(SHORT_WAIT);

        int moveTimes = 60;
        for (int i = 0; i <= moveTimes; i++) {
            device.pressDPadLeft();
            SystemClock.sleep(500);
        }
    }

    private void assertAndCaptureForFailed(boolean ret, String message) {
        if (!ret) {
            this.takeScreenCapture();
        }
        Assert.assertTrue(message, ret);
    }

    private int formatFilmPlayTime(String paramString) {
        String[] items = paramString.split(":");

        if (items.length == 2) {
            return 60 * convertPlayTimeToInt(items[0]) +
                    Integer.parseInt(items[1]);
        }
        if (items.length == 3) {
            return 60 * (60 * convertPlayTimeToInt(items[0])) +
                    60 * convertPlayTimeToInt(items[1]) +
                    Integer.parseInt(items[2]);
        }
        return 0;
    }

    private int convertPlayTimeToInt(String paramString) {
        if ("00".equals(paramString)) {
            return 0;
        }
        if (paramString.startsWith("0")) {
            return Integer.parseInt(paramString.substring(1));
        }
        return Integer.parseInt(paramString);
    }

    private void systemWait(int paramInt) {
        SystemClock.sleep(paramInt * 1000);
    }

    private void takeScreenCapture() {
        File localFile = new File("/data/local/tmp/captures");
        if (!localFile.exists())
            Assert.assertTrue(localFile.mkdirs());

        String path = String.format("%s/capture_%s.png", CAPTURES_PATH, this.getCurrentTime());

        if (mDevice.takeScreenshot(new File(path))) {
            Log.d(TAG, String.format("Take capture and save at %s", path));
        } else {
            Log.d(TAG, String.format("Failed, take capture and save at %s", path));
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd_hhmmss_SSS", Locale.getDefault());
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

}