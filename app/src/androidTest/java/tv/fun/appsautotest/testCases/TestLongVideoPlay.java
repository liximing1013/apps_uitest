package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 *
 *----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━神兽出没━━━━━━━━━by:Lixm
 **/

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
    private static final int PauseTime = 30;
    //设定播放视频时间（短）
    private static final int PlayVideoShortTime = 60;
    //设定播放视频时间（长）
    private static final int PlayVideoLongTime = 660;
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
        try {
            this.EnterTVVideoCommendHall();//进入电视剧列表页全部
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            this.RandomPlayTVVideo();
            systemWait(SHORT_WAIT);
            for (int i = 0; i <= 15; i++) {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.waitForIdle();
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadLeft(); //左键从头播放
                systemWait(PlayVideoShortTime);
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                m_uiObj = uiDevice.findObject
                        (By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
                try {
                    uiDevice.pressDPadCenter();//暂停
                    systemWait(PauseTime); //暂停时间
                    m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                    m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                    Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
                } catch (Throwable e) {
                    System.out.println("视频暂停失败");
                    e.printStackTrace();
                    resultFlag = false;
                    resultStr = e.toString();
                } finally {
                    Utils.writeCaseResult(resultStr, resultFlag, m_Time);
                }
                try {
                    uiDevice.pressDPadCenter();
                    uiDevice.waitForIdle();
                    systemWait(WAIT);
                    this.RightRightSpeedSpeed();
                    this.ProgressBarExists(10000);
                    systemWait(PlayVideoLongTime);
                    uiDevice.waitForIdle();
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.waitForIdle();
                    uiDevice.pressDPadRight();
                } catch (Throwable e) {
                    System.out.println("视频快进失败");
                    e.printStackTrace();
                    resultFlag = false;
                    resultStr = e.toString();
                } finally {
                    Utils.writeCaseResult(resultStr, resultFlag, m_Time);
                }
            }
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadRight();
            UiObject Cell = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tv_cell")
                    .className("android.widget.TextView").text("3"));
            Cell.clickAndWaitForNewWindow(15000);
            systemWait(PlayVideoLongTime);
            uiDevice.pressDPadCenter();
            UiObject Time1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/time_current")
                    .className("android.widget.TextView"));
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoLongTime);
            uiDevice.pressDPadCenter();
            UiObject Time2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/time_current")
                    .className("android.widget.TextView"));
            if(Time1.getText().equals(Time2.getText())){
                uiDevice.pressHome();
                System.out.println("时间戳无变化，播放器问题");
            }else {
                uiDevice.pressDPadCenter();
                this.LeftLeftSpeedSpeed();
            }
        }catch (Throwable e){
            System.out.println("！！！！！");
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //电影播放
    public void LC_PLAY_02_Test100FilmPlay() throws InterruptedException {
        final UiObject2 Player = uiDevice.findObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER));
        //注册监听器
        uiDevice.registerWatcher("testWatcher", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                if(uiDevice.hasObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER))){
                    Player.isSelected();
                    Log.i("testWatcher", "监听器被触发了");
                    return true;
                }
                Log.i("testWatcher", "监听器未被触发");
                return false;
            }
        });
        System.out.println("测试电影播放的case开始了，萌萌哒......");
        try {
            this.EnterFilmCommendHall();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            this.RandomPlayFilm();
            systemWait(SHORT_WAIT);
            for (int j = 0; j <= 100; j++){
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.waitForIdle();
                UiObject2 PayButton = uiDevice.findObject(By.text("付费"));
                if(PayButton != null){
                    System.out.println("本片为Vip影片,请去购买金卡会员好吗,萌萌哒.....");
                    uiDevice.resetWatcherTriggers();
                    Log.i("testWatcher", "重置监听器成功");
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    uiDevice.pressDPadLeft();
                    systemWait(PlayVideoLongTime);
                    UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                    m_Actual = PayMedia.getText();
                    m_Expect = "请选择要购买的媒体或服务";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("非金卡会员试看影片时跳转开通会员页失败",m_Pass,m_Time);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.pressBack();
                    uiDevice.pressDPadRight();
                    systemWait(WAIT);
                }else {
                    System.out.println("本片为免费影片，请尽情欣赏，呵呵哒.....");
                    uiDevice.pressDPadCenter();
                    systemWait(LONG_WAIT);
                    uiDevice.pressDPadCenter();//暂停
                    systemWait(PauseTime); //暂停时间
                    m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                    m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                    Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
                    uiDevice.pressDPadCenter();
                    systemWait(LONG_WAIT);
                    this.RightRightSpeedSpeed();
                    systemWait(LONG_WAIT);
                    this.LeftLeftSpeedSpeed();
                    systemWait(LONG_WAIT);
                    uiDevice.pressBack();
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.pressDPadRight();
                    systemWait(WAIT);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test // 测试Demo
    public void TestDemo(){
        final UiObject2 Player = uiDevice.findObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER));
        //注册监听器
        uiDevice.registerWatcher("testWatcher", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                if(uiDevice.hasObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER))){
                    Player.isSelected();
                    Log.i("testWatcher", "监听器被触发了");
                    return true;
                }
                Log.i("testWatcher", "监听器未被触发");
                return false;
            }
        });
        //运行用例步骤
        uiDevice.wait(Until.findObject(By.text("写短信")), 2000);
        UiObject2 btn = uiDevice.findObject(By.text("写短信"));
        btn.click();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();

        //重置监听器
        uiDevice.resetWatcherTriggers();
        uiDevice.wait(Until.findObject(By.text("写短信")), 2000);
        btn.click();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
        Log.i("testWatcher", "重置监听器成功");

        //移除监听器
        uiDevice.removeWatcher("testWatcher");
        Log.i("testWatcher", "移除监听器成功");
        uiDevice.wait(Until.findObject(By.text("写短信")), 2000);
        btn.click();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
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
    } //进入电视剧全部列表页

    private void EnterFilmCommendHall() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadLeft();
        systemWait(LONG_WAIT);
    } //进入电视剧全部列表页

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void RightRightSpeedSpeed() throws UiObjectNotFoundException {
        uiDevice.pressDPadRight();
        UiObject Swipe = uiDevice.findObject(new UiSelector().className("android.widget.SeekBar")
                .resourceId("com.bestv.ott:id/media_progress"));
        Swipe.swipeRight(10);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 4 4 458831", false, false);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 1 106 1", false, false);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 0 0 0", false, false);
//        Utils.execCommand("adb shell /system/bin/sleep " + iSeconds, false, false);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 4 4 458831", false, false);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 1 106 0", false, false);
//        Utils.execCommand("adb shell sendevent /dev/input/event3 0 0 0", false, false);
    } //快速滑动右移

    private void LeftLeftSpeedSpeed() throws UiObjectNotFoundException {
        uiDevice.pressDPadRight();
        UiObject Swipe = uiDevice.findObject(new UiSelector().className("android.widget.SeekBar")
                .resourceId("com.bestv.ott:id/media_progress"));
        Swipe.swipeLeft(10);
    } //快速滑动左移

    private void RandomPlayTVVideo() {
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i = moveTimes.nextInt(3);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电视剧

    private void RandomPlayFilm(){
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i = moveTimes.nextInt(3);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电影

    private void ProgressBarExists(int Second){
        UiObject ProBar = uiDevice.findObject(new UiSelector().resourceId(Infos.S_PROCESS_BAR_ID));
        ProBar.waitUntilGone(Second);
        systemWait(SHORT_WAIT);
    } //等待loading消失

}
