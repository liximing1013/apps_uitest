package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.view.accessibility.AccessibilityNodeInfo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    //设定播放视频时间（短）
    private static final int PlayVideoShortTime = 60;
    //设定播放视频时间（长）
    private static final int PlayVideoLongTime = 600;
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
    public void LC_PLAY_01_TestLongVideoPlay() throws InterruptedException {
        System.out.println("测试电视剧的播放开始了，哇咔咔.....");
        this.EnterTVVideoCommendHall();//进入电视剧列表页全部
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        this.RandomPlayTVVideo();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        try {
            uiDevice.wait(Until.findObject(By.text("相关推荐")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoShortTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
        try {
            uiDevice.pressDPadCenter();//暂停
            systemWait(PauseTime); //暂停时间
            m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
            Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
        try {
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            this.RightRightSpeedSpeed();
            systemWait(PlayVideoShortTime);


        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test
    public void testDemo() throws UiObjectNotFoundException {
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);

    }


    private void EnterTVVideoCommendHall() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
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

    private void RandomPlayTVVideo() {
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i = moveTimes.nextInt(6);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电视剧

    public static boolean longClickWithTime(UiObject obj, long time) throws Exception {

        Class<?> InteractionControllerClass = null;
        Method findAccessibilityNodeInfoMethod = null;
        Method getInteractionControllerMethod = null;
        Method touchDownMethod = null;
        Method touchUpMethod = null;

        Field WAIT_FOR_SELECTOR_TIMEOUT = null;
        findAccessibilityNodeInfoMethod = UiObject.class.getDeclaredMethod("findAccessibilityNodeInfo",long.class);
        findAccessibilityNodeInfoMethod.setAccessible(true);
        WAIT_FOR_SELECTOR_TIMEOUT = UiObject.class.getDeclaredField("WAIT_FOR_SELECTOR_TIMEOUT");
        WAIT_FOR_SELECTOR_TIMEOUT.setAccessible(true);
        AccessibilityNodeInfo node = (AccessibilityNodeInfo) findAccessibilityNodeInfoMethod.invoke(obj, WAIT_FOR_SELECTOR_TIMEOUT.getLong(obj));
        if(node == null) {
            throw new UiObjectNotFoundException(obj.getSelector().toString());
        }
        Rect rect = obj.getVisibleBounds();

        getInteractionControllerMethod = UiObject.class.getDeclaredMethod("getInteractionController");
        getInteractionControllerMethod.setAccessible(true);
        Object interactionController = getInteractionControllerMethod.invoke(obj);

        InteractionControllerClass = Class.forName("com.android.uiautomator.core.InteractionController");
        touchDownMethod = InteractionControllerClass.getDeclaredMethod("touchDown", int.class, int.class);
        touchDownMethod.setAccessible(true);
        touchUpMethod = InteractionControllerClass.getDeclaredMethod("touchUp", int.class, int.class);
        touchUpMethod.setAccessible(true);

        if(Boolean.parseBoolean(touchDownMethod.invoke(interactionController, rect.centerX(), rect.centerY()).toString())) {
            SystemClock.sleep(time);
            if(Boolean.parseBoolean(touchUpMethod.invoke(interactionController, rect.centerX(), rect.centerY()).toString())) {
                return true;
            }
        }
        return false;
    }

}
