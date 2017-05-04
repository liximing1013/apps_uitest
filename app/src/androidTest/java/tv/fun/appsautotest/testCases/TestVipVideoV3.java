package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Random;

import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created lxm on 2016/4/26
 * TestCase:24
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestVipVideoV3 {
    public Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 2;
    private static final int WAIT = 6;
    private static final int LONG_WAIT = 15;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    //设定播放视频时间（长）
    private static final int PlayVideoLongTime = 660;
    //初始化
    String m_ObjId = "";
    private UiObject2 m_uiObj = null;
    private long m_Time;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean resultFlag = true;
    private boolean m_Pass = false;

    @BeforeClass
    public static void BeforeClass() {

        System.out.println("进入金卡会员页面，开启金卡专区的自动化测试了..");
    }

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time=Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        uiDevice.waitForIdle(5000);
        UiObject2 VipArea = this.getTabFromLauncherHomeByText(uiDevice, "金卡会员");
        this.openTabFromLauncherHomeByTextView(uiDevice, VipArea);
        systemWait(WAIT);
    }

    @After
    public void clearUp() {
        BackPageMethod();
        Configurator configurator = Configurator.getInstance();
        configurator.setActionAcknowledgmentTimeout(5000);
    }

    @AfterClass
    public static void AfterClass(){

        System.out.println("See You Later");
    }

    @Test //初进金卡会员页
    public void LC_VIP_01_EnterVipPage() {
        try {
            systemWait(WAIT);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/vip_title"));
            m_ObjId = "com.bestv.ott:id/vip_title";
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj != null ,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag= false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //全屏播放会员小窗口
    public void LC_VIP_02_HatchToFullScreenPlay() {
        try {
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);//加入断言判定是否全屏播放,via全屏播放时唯一的标示
            m_ObjId = Infos.S_LC_VIP_FULLSCREEN_BUTTON_ID;
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
            Utils.writeCaseResult("进入全屏播放时失败，无法抓取到控件", m_uiObj != null, m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //小窗口跳转观看完整影片
    public void LC_VIP_03_PlayCompleteVideo() {
        try {
            uiDevice.pressEnter();
            systemWait(LONG_WAIT);
            UiObject2 DetEnter = uiDevice.findObject(By.res("com.bestv.ott:id/detail_enter"));
            Assert.assertNotNull(DetEnter);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoLongTime);
                UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = PayMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //会员特权页面
    public void LC_VIP_04_VipMemberPage() {
        try {
            RightMoveMethod(10);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            m_ObjId = "com.bestv.ott:id/detail_background";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_background"));
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //开通金卡会员选项跳转
    public void LC_VIP_05_PersonalSkipVipPage() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView1 = uiDevice.findObject(text("金卡会员30天"));
            m_Expect = "金卡会员30天";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //兑换页面
    public void LC_VIP_06_ExchangeButtonVipPage() {
        try {
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            RightMoveMethod(2);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
            m_Expect = "兑换";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入兑换页面时失败：未抓到页面关键字段", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //兑换测试--输入错误的兑换券页面
    public void LC_VIP_07_InputErrorCharacterOnExchangePage() {
        try {
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            RightMoveMethod(2);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
            Assert.assertEquals("成功进入兑换页面", "兑换", tabView1.getText());
            UiObject2 editText = uiDevice.findObject(text("请输入兑换券编码"));
            Configurator configurator = Configurator.getInstance();
            configurator.setKeyInjectionDelay(1000);
            editText.setText("testdata");
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 textView1 = uiDevice.findObject(text("兑换码输入错误，请重试！"));
            m_Expect = "兑换码输入错误，请重试！";
            m_Actual = textView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("兑换系统出现问题：错误兑换码显示成功", m_Pass, m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //个人中心页面
    public void LC_VIP_08_ButtonSkipPersonalPage() {
        try {
            EnterPersonalCenterPage();
            UiObject2 TextView = uiDevice.findObject(text("福利领取"));
            m_Actual = TextView.getText();
            m_Expect = "福利领取";
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入个人中心页面时跳转错误：未抓到关键字段", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //会员开通or会员续费页面
    public void LC_VIP_09_VipDisplayCardInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView1 = uiDevice.findObject(text("金卡会员30天"));
            m_Expect = "金卡会员30天";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //支付代扣页面
    public void LC_VIP_10_PaymentWithHoldingInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            UiObject2 PayCard = uiDevice.findObject(By.text("支付代扣"));
            this.openTabFromLauncherHomeByTextView(uiDevice,PayCard);
            UiObject2 tabView1 = uiDevice.findObject(text("取消代扣"));
            m_Expect = "取消代扣";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入取消代扣页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //福利领取
    public void LC_VIP_11_WelfareCollectionInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            UiObject2 WelCard = uiDevice.findObject(By.text("福利领取"));
            m_Expect = "福利领取";
            m_Actual = WelCard.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("个人中心未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //卡券兑换
    public void LC_VIP_12_CardExchangeInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            UiObject2 CardEx = uiDevice.findObject(By.text("卡券兑换"));
            this.openTabFromLauncherHomeByTextView(uiDevice,CardEx);
            UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
            m_Expect = "兑换";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入卡券兑换页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //消费记录
    public void LC_VIP_13_PayRecordInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            UiObject2 Record = uiDevice.findObject(By.text("消费记录"));
            this.openTabFromLauncherHomeByTextView(uiDevice,Record);
            UiObject2 tabView1 = uiDevice.findObject(text("客服电话：400 600 6258"));
            m_Expect = "客服电话：400 600 6258";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入消费记录页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //观影券
    public void LC_VIP_14_ViewingCouponInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            UiObject2 Coupon = uiDevice.findObject(By.text("观影券"));
            this.openTabFromLauncherHomeByTextView(uiDevice,Coupon);
            UiObject2 tabView1 = uiDevice.findObject(text("客服电话：400 600 6258"));
            m_Expect = "客服电话：400 600 6258";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入观影券页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //会员状态页
    public void LC_VIP_15_MemberStatusInPersonalPage(){
        try {
            EnterPersonalCenterPage();
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView1 = uiDevice.findObject(text("会员头像设置"));
            m_Expect = "会员头像设置";
            m_Actual = tabView1.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入会员头像设置页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //即将上映--影片收藏
    public void LC_VIP_16_ComingMoviesCollectAndPlay() {
        try {
            RightMoveMethod(3);
            systemWait(WAIT);
            UiObject2 tabView2 = this.getTabFromLauncherHomeByText(uiDevice, "即将上映");
            this.openTabFromLauncherHomeByTextView(uiDevice, tabView2); //进入即将上映页面
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/fav_state"));//通过Res_id来获取Text值，判断当前button选项
            if (ResId.getText().equals("加入收藏")) {
                uiDevice.pressDPadCenter();   //点击确定加入收藏
                systemWait(SHORT_WAIT);
                UpMoveMethod(1);
                uiDevice.pressDPadCenter();
                uiDevice.waitForIdle(20000);
                systemWait(LONG_WAIT);
                UiObject2 Text = uiDevice.findObject(text("已收藏"));  //加入断言判定详情页是否变为已收藏
                m_Expect = "已收藏";
                m_Actual = Text.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("详情页收藏显示错误：收藏后应变为已收藏", m_Pass, m_Time);
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放金卡电影
    public void LC_VIP_17_PlayVipVideo() {
        try {
            RightMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("最新院线")),10000);
            systemWait(WAIT);
            RandomPlayFilm(18);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoLongTime);
                UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = PayMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
                BackPageMethod();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放金卡电视剧
    public void LC_VIP_18_PlayVipTVVideo(){
        try {
            uiDevice.waitForIdle();
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-电视剧").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            this.RandomPlayFilm(9);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //4K专区
    public void LC_VIP_19_Play4KVideo() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("4K")),18000);
            systemWait(WAIT);
            RandomPlayFilm(19);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("4K视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoLongTime);
                UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = PayMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
                BackPageMethod();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡纪实
    public void LC_VIP_20_PlayDocumentaryVideo() {
        try {
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-纪实").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            RandomPlayFilm(20);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 TextViewer = uiDevice.findObject(By.text("相关推荐"));
            m_Expect = "相关推荐";
            m_Actual = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入详情页失败", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡少儿
    public void LC_VIP_21_PlayKidVideo() {
        try {
            uiDevice.waitForIdle();
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("动画电影")),18000);
            systemWait(WAIT);
            RandomPlayFilm(27);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("4K视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡少儿播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡动漫
    public void LC_VIP_22_PlayComicVideo() {
        try {
            uiDevice.waitForIdle();
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-动漫").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            this.RandomPlayFilm(3);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡动漫视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡动漫视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }
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
    public void LC_VIP_23_VideoClassifySkipVip() {
        try {
            BackToLauncherHome(uiDevice);
            this.EnterVideoClassifyPage();
            DownMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("金卡电影")),18000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.waitForIdle(20000);
            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
                uiDevice.pressDPadLeft(); //防止详情页活动
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
                BackPageMethod();
            }else {
                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
                systemWait(PlayVideoLongTime);
                UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = PayMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
                BackPageMethod();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //会员包月支付流程
    public void LC_VIP_24_PersonalPagePayVip() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 TextView = uiDevice.findObject(text("金卡会员30天").res("com.bestv.ott:id/commodity_type"));
            this.openTabFromLauncherHomeByTextView(uiDevice,TextView);
            uiDevice.waitForIdle(8000);
            systemWait(WAIT);
            UiObject ZhiFuBao = uiDevice.findObject(new UiSelector().className("android.widget.ImageView")
                    .resourceId("com.bestv.mediapay:id/pay_image"));
            Assert.assertNotNull(ZhiFuBao);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
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

    private void openTabFromLauncherHomeByVipText(UiDevice device, UiObject2 TabView) {
        TabView.click();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void BackToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间设定

    private void RandomPlayFilm(int RandomMove){
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random();
        int i;
        i=moveTimes.nextInt(RandomMove);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            if(i > 18){
                break;
            }
        }
    } //随机播放电影

    private void EnterVideoClassifyPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        DownMoveMethod(2);
        uiDevice.pressEnter();
        systemWait(WAIT);
        UiObject2 Classify = uiDevice.findObject(By.text("最新"));
        Assert.assertEquals("最新",Classify.getText());
        systemWait(WAIT);
    } //进入视频分类页面

    private void EnterPersonalCenterPage(){
        uiDevice.pressDPadUp();
        systemWait(WAIT);
        RightMoveMethod(1);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 PerPage = uiDevice.findObject(By.text("个人中心"));
        Assert.assertEquals("个人中心",PerPage.getText());
        systemWait(WAIT);
    } //进入个人中心页面

    private void RightMoveMethod(int RightMove){
        int i = 1;
        while (i <= RightMove){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //Right*

    private void DownMoveMethod(int DownMove){
        int i = 1;
        while (i <= DownMove){
            i++;
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
        }
    } //Down*

    private void UpMoveMethod(int UpMove){
        int i = 1;
        while (i <= UpMove){
            i++;
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
        }
    } //Up*

    private void BackPageMethod(){
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
    } //Back*
}