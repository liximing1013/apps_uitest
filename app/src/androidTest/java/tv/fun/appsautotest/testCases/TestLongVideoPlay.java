package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by lixm on 2016/11/9.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder
public class TestLongVideoPlay {
    Instrumentation instrument;
    UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 15;
    //暂停时间
    private static final int PauseTime = 20;
    //设定播放视频时间
    private static final int PlayVideoTime = 60;
    //初始化
    long m_Time;
    String m_ObjId = "";
    UiObject2 m_uiObj = null;
    boolean m_Pass = false;
    String m_Expect = "";
    String m_Actual = "";
    String resultStr = "";
    Boolean resultFlag = true;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time = Utils.getCurSecond();   //耗时
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
    }

    @Test //电视剧播放
    public void LC_PLAY_01_TestLongVideoPlay() {
        this.EnterTVVideoCommendHall();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        this.RandomPlayTVVideo();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        try {
            uiDevice.wait(Until.findObject(By.text("相关推荐")), 15000);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
            uiDevice.pressDPadCenter();
            systemWait(PauseTime);
            m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
            Utils.writeCaseResult("视频暂停失败", m_uiObj != null, m_Time);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            this.RightRightSpeedSpeed();
            systemWait(PlayVideoTime);
            uiDevice.pressDPadDown();
            uiDevice.pressDPadDown();
            systemWait(LONG_WAIT);
            UiObject TvCell = uiDevice.findObject(new UiSelector().text("2").resourceId("com.bestv.ott:id/tv_cell"));
            TvCell.click();
            systemWait(PlayVideoTime);
            uiDevice.pressBack();
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("相关推荐")), 15000);
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("选集"));
            if(TextView == null){

            }


        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    private void EnterTVVideoCommendHall() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressDPadLeft();
        systemWait(LONG_WAIT);
    } //进入电视剧推荐大厅

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    }

    private void RightRightSpeedSpeed() {
        for (int i = 0; i <= 20; i++) {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    }

    private void RandomPlayTVVideo(){
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i=moveTimes.nextInt(8);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电视剧

}
