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

/******************************
 * Created Lixm on 2016/10/10
 * Test Playing VIP Video
 * Test Case: 14
 ******************************/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder
public final class TestVipVideo {
    Instrumentation instrument;
    UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 15;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    boolean m_Pass = false;
    String m_Expect = "";
    String m_Actual = "";
    String m_ObjId = "";
    UiObject2 m_uiObj = null;
    String resultStr = "";
    Boolean resultFlag = true;
    long m_Time;

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

//    @Test //获取用例名
//    public void test(){
//
//        TvCommon.printAllMethods(this.getClass().getName());
//    }

    @Test //进入金卡会员页
    public void LC_VIP_01_EnterVipPage() {
        System.out.println("进入金卡会员页面，开启金卡专区的自动化测试了..");
        try {
            systemWait(WAIT);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/vip_title"));
            m_ObjId = "com.bestv.ott:id/vip_title";
            Utils.writeCaseResult("跳转页面失败", m_uiObj != null, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //全屏观看金卡会员频道小窗口
    public void LC_VIP_03_HatchToFullScreenPlay() {
        try {
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);//加入断言判定是否全屏播放,via全屏播放时唯一的标示
//        UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
//        Assert.assertNotNull("小窗口全屏播放", ResId);
            m_ObjId = Infos.S_LC_VIP_FULLSCREEN_BUTTON_ID;
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
            Utils.writeCaseResult("进入全屏播放时失败，无法抓取到控件", m_uiObj != null, m_Time);
            uiDevice.pressEnter();
            systemWait(WAIT);
            uiDevice.pressBack();
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员页会员特权页面
    public void LC_VIP_07_VipMemberPage() {
        systemWait(SHORT_WAIT);
        this.RightRightRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        try {
            m_ObjId = "com.bestv.ott:id/detail_background";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_background"));
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //开通金卡会员选项跳转
    public void LC_VIP_10_PersonalSkipVipPage() {
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        try {
            UiObject2 tabView1 = uiDevice.findObject(text("请选择金卡会员套餐"));
//        Assert.assertEquals("成功跳转至开通金卡会员页面","请选择金卡会员套餐",tabView1.getText());
            m_Expect = "请选择金卡会员套餐";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
            systemWait(WAIT);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员页面右上角兑换选项
    public void LC_VIP_11_ExchangeButtonVipPage() {
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        try {
            UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
//        Assert.assertEquals("成功进入兑换页面","兑换",tabView1.getText());
            m_Expect = "兑换";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入兑换页面时失败：未抓到页面关键字段", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //兑换中验证输入错误的兑换券
    public void LC_VIP_11_1_InputErrorCharacterOnExchangePage() {
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        try {
            UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
            Assert.assertEquals("成功进入兑换页面", "兑换", tabView1.getText());
            systemWait(WAIT);
            UiObject2 editText = uiDevice.findObject(text("请输入兑换券编码"));
            editText.setText("5177sddd");
            systemWait(WAIT);
            uiDevice.pressBack();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 textView1 = uiDevice.findObject(text("兑换码输入错误，请重试！"));
//          Assert.assertEquals("兑换失败提示","兑换码输入错误，请重试！",textView1.getText());
            m_Expect = "兑换码输入错误，请重试！";
            m_Actual = textView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("兑换系统出现问题：错误兑换码显示成功", m_Pass, m_Time);
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
        }catch(Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员页进入个人中心
    public void LC_VIP_12_ButtonSkipPersonalPage() {
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        try {
            UiObject2 TextView = uiDevice.findObject(text("福利领取"));
//        Assert.assertEquals("个人中心跳转成功","卡券兑换",TextView.getText());
            m_Actual = TextView.getText();
            m_Expect = "福利领取";
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入个人中心页面时跳转错误：未抓到关键字段", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //即将上映中影片的收藏
    public void LC_VIP_15_ComingMoviesCollectAndPlay() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        UiObject2 tabView2 = this.getTabFromLauncherHomeByText(uiDevice, "即将上映");
        //进入即将上映页面
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView2);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        try {
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
                Utils.writeCaseResult("详情页收藏显示错误：收藏后应变为已收藏", m_Pass, m_Time);
                systemWait(WAIT);
            } else {
                uiDevice.pressBack();
                uiDevice.pressBack();
                systemWait(WAIT);
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultStr =e.toString();
            resultFlag=false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员专区播放金卡电影
    public void LC_VIP_16_PlayVipVideo() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
        this.RandomPlayFilm();
        uiDevice.pressEnter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        try {
           /* UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Assert.assertNotNull(VideoClazz);*/
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败",m_uiObj !=null,m_Time );
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员专区播放金卡电视剧
    public void LC_VIP_17_PlayVipTVVideo(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        this.RandomPlayTVVideo();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")), 15000);
        systemWait(LONG_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(PlayVideoTime);
        try {
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //金卡会员专区进入4K专区
    public void LC_VIP_18_Play4KVideo() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        this.RandomPlayFilm();
        systemWait(WAIT);
        uiDevice.pressEnter();
        try {
            uiDevice.wait(Until.findObject(By.text("金卡专享")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("4K专区内任选视频播放失败",m_uiObj !=null,m_Time);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员专区进入金卡纪实
    public void LC_VIP_19_PlayDocumentaryVideo() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")), 15000);
        systemWait(LONG_WAIT);
        try {
            UiObject2 TextViewer = uiDevice.findObject(By.text("相关推荐"));
            m_Expect = "相关推荐";
            m_Actual = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入详情页失败", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡会员专区进入金卡少儿
    public void LC_VIP_20_PlayKidVideo() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        this.RandomPlayFilm();
        systemWait(LONG_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("金卡专享")), 15000);
        systemWait(LONG_WAIT);
        try {
            UiObject2 TextViewer = uiDevice.findObject(By.text("相关推荐"));
            m_Expect = "相关推荐";
            m_Actual = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入详情页失败", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //视频分类页入口进入金卡列表页
    public void LC_VIP_21_VideoClassifySkipVip() {
        uiDevice.pressHome();
        systemWait(WAIT);
        this.EnterVideoClassifyPage();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        try {
            UiObject2 TabView = uiDevice.findObject(By.text("金卡电影"));
            m_Actual = TabView.getText();
            m_Expect = "金卡电影";
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入列表页失败", m_Pass, m_Time);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressEnter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
//          UiObject2 videoPlayer = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//          Assert.assertNotNull(videoPlayer);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("视频播放失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
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

    private void openTabFromLauncherHomeByTextView(UiDevice device, UiObject2 TabView) {
        TabView.click();
        systemWait(SHORT_WAIT);
        device.pressEnter();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间设定

    private void RandomPlayFilm(){
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i=moveTimes.nextInt(9);
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
        i=moveTimes.nextInt(6);
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
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
    } //进入视频分类页面

    private void RightRightRight(){
        int i = 0;
        while( i <= 10){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //R*10

}