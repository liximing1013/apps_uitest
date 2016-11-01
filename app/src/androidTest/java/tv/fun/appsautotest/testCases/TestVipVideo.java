package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;
/**
 * Created Lixm on 2016/10/10.
 * Test playing VIP Video.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder
public final class TestVipVideo {
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
    boolean m_Pass = false;
    String m_Expect = "";
    String m_Actual = "";
    long m_Time;
    String m_ObjId = "";
    UiObject2 m_uiObj = null;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time=Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        UiObject2 tabView = this.getTabFromLauncherHomeByText(uiDevice, "金卡会员");
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView);
        systemWait(SHORT_WAIT);
    }

    @After
    public void clearUp() {

        systemWait(WAIT);
    }

    @Test //全屏观看金卡会员频道小窗口
    public void LC_VIP_03_HatchToFullScreenPlay() {
        uiDevice.pressEnter();
        systemWait(PlayVideoTime);
        //加入断言判定是否全屏播放,via全屏播放时唯一的标示
//        UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
//        Assert.assertNotNull("小窗口全屏播放", ResId);
        m_ObjId = Infos.S_LC_VIP_FULLSCREEN_BUTTON_ID;
        m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
        Utils.writeCaseResult("全屏播放失败",m_uiObj !=null,m_Time);
        uiDevice.pressEnter();
        systemWait(WAIT);
        uiDevice.pressBack();
    }

    @Test //即将上映内影片的收藏和播放
    public void LC_VIP_16_ComingMoviesCollectAndPlay() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        UiObject2 tabView2 = this.getTabFromLauncherHomeByText(uiDevice, "即将上映");
        //进入即将上映页面
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView2);
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/fav_state"));
        //通过Res_id来获取Text值，判断当前button选项
        if (ResId.getText().equals("加入收藏")) {
            //点击确定加入收藏
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            //加入断言判定详情页是否变为已收藏
            UiObject2 Text = uiDevice.findObject(text("已收藏"));
//            Assert.assertEquals("详情页内收藏选项变为已收藏", "已收藏", Text.getText());
            m_Expect = "已收藏";
            m_Actual = Text.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("详情页显示错误",m_Pass,m_Time);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressEnter();
        }else {
                uiDevice.pressBack();
                uiDevice.pressBack();
                systemWait(WAIT);
        }
    }

    @Test //金卡会员页面右上角选项跳转
    public void LC_VIP_10_PersonalSkipVipPage(){
        uiDevice.pressDPadUp();
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 tabView1 = uiDevice.findObject(text("请选择金卡会员套餐"));
//        Assert.assertEquals("成功跳转至开通金卡会员页面","请选择金卡会员套餐",tabView1.getText());
        m_Expect = "请选择金卡会员套餐";
        m_Actual = tabView1.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("金卡会员页面跳转失败",m_Pass,m_Time);
        systemWait(WAIT);
        uiDevice.pressBack();
    }

    @Test //金卡会员页面右上角兑换选项
    public void LC_VIP_11_ExchangeButtonVipPage(){
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
//        Assert.assertEquals("成功进入兑换页面","兑换",tabView1.getText());
        m_Expect = "兑换";
        m_Actual = tabView1.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("进入页面失败",m_Pass,m_Time);
    }

    @Test //验证输入错误的兑换券
    public void LC_VIP_11_1_InputErrorCharacterOnExchangePage(){
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
        Assert.assertEquals("成功进入兑换页面","兑换",tabView1.getText());
        systemWait(WAIT);
        UiObject2 editText = uiDevice.findObject(text("请输入兑换券编码"));
        editText.setText("51asddd");
        systemWait(WAIT);
        uiDevice.pressBack();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        UiObject2 textView1 = uiDevice.findObject(text("兑换码输入错误，请重试！"));
//        Assert.assertEquals("兑换失败提示","兑换码输入错误，请重试！",textView1.getText());
        m_Expect = "兑换码输入错误，请重试！";
        m_Actual = textView1.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("兑换码出现问题",m_Pass,m_Time);
        uiDevice.pressDPadCenter();
    }

    @Test //金卡会员页进入个人中心
    public void LC_VIP_12_ButtonSkipPersonalPage(){
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 TextView = uiDevice.findObject(text("卡券兑换"));
//        Assert.assertEquals("个人中心跳转成功","卡券兑换",TextView.getText());
        m_Actual = TextView.getText();
        m_Expect = "卡券兑换";
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("进入页面错误",m_Pass,m_Time);
    }

    @Test //金卡会员专区播放金卡电影
    public void LC_VIP_16_PlayVipVideo(){
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
        this.RandomPlayFilm();
        uiDevice.pressEnter();
       // uiDevice.wait(Until.findObject(By.text("金卡专享")),10000);
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
    }

    @Test //金卡会员专区播放金卡电视剧
    public void LC_VIP_17_PlayVipTVVideo(){
        uiDevice.pressDPadRight();
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        this.RandomPlayTVVideo();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")),10000);
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
    }

    @Test //金卡会员专区进入4K专区
    public void LC_VIP_18_Play4KVideo(){
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
        this.RandomPlayFilm();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")),15000);
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
    }

    @Test //金卡会员专区进入金卡纪实
    public void LC_VIP_19_PlayDocumentaryVideo(){
        uiDevice.pressDPadRight();
        uiDevice.pressDPadDown();
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")),15000);
        systemWait(WAIT);
        UiObject2 TextViewer = uiDevice.findObject(By.text("相关推荐"));
        m_Expect = "相关推荐";
        m_Actual = TextViewer.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("进入详情页失败",m_Pass,m_Time);
    }

    @Test //金卡会员专区进入金卡少儿
    public void LC_VIP_20_PlayKidVideo(){
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        this.RandomPlayFilm();
        systemWait(WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")),15000);
        systemWait(WAIT);
        UiObject2 TextViewer = uiDevice.findObject(By.text("相关推荐"));
        m_Expect = "相关推荐";
        m_Actual = TextViewer.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("进入详情页失败",m_Pass,m_Time);
    }

    @Test //视频分类页进入金卡专区
    public void LC_VIP_21_VideoClassifySkipVip(){
        uiDevice.pressHome();
        systemWait(WAIT);
        this.EnterVideoClassifyPage();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 TabView = uiDevice.findObject(By.text("金卡电影"));
        m_Actual = TabView.getText();
        m_Expect = "金卡电影";
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("进入列表页失败",m_Pass,m_Time);
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")),15000);
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        UiObject2 videoPlayer = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(videoPlayer);
    }

//    @Test
//    public void test(){
//
//        TvCommon.printAllMethods(this.getClass().getName());
//    }

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

    private void openTabFromLauncherHomeByTextView(UiDevice device, UiObject2 TabView) {
        TabView.click();
        systemWait(SHORT_WAIT);
        device.pressEnter();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {
        SystemClock.sleep(seconds * 1000);
    }

    private void RandomPlayFilm(){
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i=moveTimes.nextInt(12);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电影

    private void RandomPlayTVVideo(){
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i=moveTimes.nextInt(4);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电视剧

    private void EnterVideoClassifyPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadDown();
        uiDevice.pressEnter();
        systemWait(WAIT);
    } //进入视频分类
}