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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created by lixm on 2016/10/28
 * Mix
 *
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder
public class TestVideoPlayCollectAndRecord {
    Instrumentation instrument;
    UiDevice uiDevice;
    private static final String TAG = TestVipVideo.class.getSimpleName();
    private static final String CAPTURES_PATH = "/data/local/tmp/captures";
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
    String resultStr = "";
    Boolean resultFlag = true;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        uiDevice.pressHome();
//        uiDevice.waitForIdle(2000);
        m_Time= Utils.getCurSecond();   //耗时
    }

    @After //运行脚本之后回到launcher
    public void clearUp() {
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
        backToLauncherHome(uiDevice);
        systemWait(WAIT);
    }

    @Test //获取用例名
    public void test(){

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test //体育Tab下小视频收藏
    public void LC_SV_10_SmallVideoCollectRecord() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 Text1 = uiDevice.findObject(By.res("com.bestv.ott:id/special_player_favorite"));
           if(Text1.getText().equals("已收藏")){
                uiDevice.pressBack();
                System.out.println("此小视频专题右上角选项显示:"+Text1.getText());
            }else {
               uiDevice.pressDPadUp();
               systemWait(SHORT_WAIT);
               uiDevice.pressDPadCenter();
               systemWait(SHORT_WAIT);
               uiDevice.pressBack();
               systemWait(SHORT_WAIT);
               uiDevice.pressDPadCenter();
               systemWait(WAIT);
               UiObject Text2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/special_player_favorite").text("已收藏"));
               m_Expect = "已收藏";
               m_Actual = Text2.getText();
               m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
               Utils.writeCaseResult("小视频收藏失败",m_Pass,m_Time);
           }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //进入视频分类页直播大厅
    public void LC_BL_01_EnterBesTVLivePage() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/live_hall_title"));
            m_ObjId = "com.bestv.ott:id/live_hall_title";
            Utils.writeCaseResult("进入直播大厅失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //进入NBA首页全屏播放小窗口
    public void LC_NBA_01_EnterNBAHomePage() {
        try {
            systemWait(SHORT_WAIT);
            this.EnterNBAHomePage();
            uiDevice.wait(Until.findObject(By.text("赛程表 >")), 10000);
            systemWait(LONG_WAIT);
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);
//          UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//          Assert.assertNotNull(VideoClazz);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("NBA小窗口播放失败", m_uiObj != null, m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA赛程表页面
    public void LC_NBA_26_EnterNBARaceCard() {
        try {
            this.EnterNBAHomePage();
            uiDevice.wait(Until.findObject(By.text("赛程表 >")), 10000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("NBA全部赛程"));
            m_Actual = AllRace.getText();
            m_Expect = "NBA全部赛程";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA全部赛程页面失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA一键预约页面
    public void LC_NBA_26_EnterNBAKeyAppointment(){
        try {
            this.EnterNBAHomePage();
            uiDevice.wait(Until.findObject(By.text("赛程表 >")), 10000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("全部球队"));
            m_Actual = AllRace.getText();
            m_Expect = "全部球队";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA一键预约页面失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA购买NBA包
    public void LC_NBA_26_PayNBA(){
        try {
            this.EnterNBAHomePage();
//          systemWait(SHORT_WAIT);
//          uiDevice.wait(Until.findObject(By.text("赛程表 >")), 10000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("NBA包年"));
            m_Actual = AllRace.getText();
            m_Expect = "NBA包年";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA付费包页面失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA球队赛程
    public void LC_NBA_26_EnterNBATeamSchedule(){
        try {
            this.EnterNBAHomePage();
            uiDevice.wait(Until.findObject(By.text("赛程表 >")), 10000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("NBA球队赛程"));
            m_Actual = AllRace.getText();
            m_Expect = "NBA球队赛程";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA球队赛程页面失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }

    }

    @Test //进入新闻大厅随机全屏播放一新闻
    public void LC_NEWS_11_FullScreenPlayRandomNews() {
        try {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            //随机选择一新闻播放
            this.RandomPlayNews();
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            //选择二级标题播放
            uiDevice.pressEnter();
            systemWait(SHORT_WAIT);
            //焦点移至小窗口
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            //全屏播放
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);
//          UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//          Assert.assertNotNull(VideoClazz);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj =uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("新闻大厅播放新闻失败",m_uiObj !=null,m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr= e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //看看新闻直播正常播放
    public void LC_NEWS_09_KanKanNewsNormalPlay() {
        try {
            UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "视频分类");
            //进入视频分类看看新闻固定位进入播放
            this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressEnter();
            //新闻播放60s
            systemWait(PlayVideoTime);
//          UiObject2 VideoClass = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//          Assert.assertNotNull(VideoClass);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("看看新闻无法播放", m_uiObj != null, m_Time);
            //返回新闻大厅
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //排行榜任选一影片播放
    public void LC_PHB_04_RankListPlayVideo() {
        try {
            //排行榜数据每天更新一次，每天运行脚本时，播放影片不同
            this.EnterVideoClassifyPage();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            uiDevice.pressDPadDown();
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.res("com.bestv.ott:id/detail_karma")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            //判断播放器是否播放
            UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Assert.assertNotNull(VideoClazz);
            systemWait(WAIT);
            uiDevice.pressBack();
            uiDevice.pressBack();
            systemWait(WAIT);
            //判断是否退出后回到详情页
            UiObject2 TextView = uiDevice.findObject(text("相关推荐"));
//          Assert.assertNotNull(TextView);
            m_Expect = "相关推荐";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("显示错误", m_Pass, m_Time);
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr =e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //进入视频分类页排行榜
    public void LC_PHB_02_EnterRankListPage() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextViewer = uiDevice.findObject(By.text("TOP1"));
//        Assert.assertEquals("TOP1",TextViewer.getText());
            m_Actual = "TOP1";
            m_Expect = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("排行榜页面显示正确", m_Pass, m_Time);
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("选集")), 15000);
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("成功进入详情页", "相关推荐", TextView.getText());
            m_Expect = "相关推荐";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("无法进入媒体详情页", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr =e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //进入顶部快捷栏&逻辑判断电视上是否插有U盘
    public void LC_TB_03_EnterEveryTopBars() {
        try {
            uiDevice.pressHome();
            //进入天气APP
            uiDevice.pressDPadUp();
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId1 = uiDevice.findObject(By.res("com.bestv.ott:id/weather"));
            this.openTopBarFromLauncherHomeByresId(uiDevice, ResId1);
            systemWait(WAIT);
            UiObject2 TextViewer1 = uiDevice.findObject(text("按 \uE693 菜单键管理城市"));
//        Assert.assertEquals("按 \uE693 菜单键管理城市",TextViewer1.getText());
            m_Expect = "按 \uE693 菜单键管理城市";
            m_Actual = TextViewer1.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入天气页面失败！", m_Pass, m_Time);
            uiDevice.pressBack();
            systemWait(WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId2 = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId2);
            systemWait(WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("我的应用"));
//        Assert.assertEquals("我的应用",TextViewer2.getText());
            m_Expect = "我的应用";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入我的应用失败", m_Pass, m_Time);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/history"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId3);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("播放记录"));
//            Assert.assertEquals("播放记录", TextViewer3.getText());
            m_Expect = "播放记录";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入播放记录页失败",m_Pass,m_Time);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId4 = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId4);
            systemWait(WAIT);
            UiObject2 TextViewer4 = uiDevice.findObject(text("消息中心"));
            Assert.assertEquals("消息中心", TextViewer4.getText());
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId5 = uiDevice.findObject(By.res("com.bestv.ott:id/setting"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId5);
            systemWait(WAIT);
            UiObject2 TextViewer5 = uiDevice.findObject(text("通用设置"));
            Assert.assertEquals("通用设置", TextViewer5.getText());
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId6 = uiDevice.findObject(By.res("com.bestv.ott:id/network"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId6);
            systemWait(LONG_WAIT);
            UiObject2 TextViewer6 = uiDevice.findObject(text("网络设置"));
            Assert.assertEquals("网络设置", TextViewer6.getText());
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject2 ResId7 = uiDevice.findObject(By.res("com.bestv.ott:id/storage"));
            if (ResId7 == null) {
                uiDevice.pressHome();
                uiDevice.waitForIdle();
            } else {
                this.openTabFromLauncherHomeByresId(uiDevice, ResId7);
                systemWait(WAIT);
                uiDevice.pressDPadDown();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadDown();//进入音乐模块
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 TextViewer = uiDevice.findObject(text("按 \uE693 键查看更多操作"));
                Assert.assertNotNull("按 \uE693 键查看更多操作", TextViewer.getText());
                uiDevice.pressBack();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //英超首页播放小视频专题
    public void LC_PL_03_PremierLeaguePlaySmallVideo() {
        try {
            this.EnterPLHomePage();
            systemWait(WAIT);
            uiDevice.pressEnter();
            uiDevice.wait(Until.findObject(By.text("赛程表")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
//       UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//       Assert.assertNotNull(VideoClazz);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("英超首页小视频播放失败", m_uiObj != null, m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr =e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //购买英超包页面跳转
    public void LC_PL_04_PayPremierLeague() {
        this.EnterPLHomePage();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("赛程表")), 15000);
        systemWait(LONG_WAIT);
        uiDevice.pressDPadUp();
        try {
            UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/pay_la_txt"));
            if (ResId.getText() == null) {
                System.out.println("电视已开通英超付费包");
            } else {
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                UiObject2 TextViewer = uiDevice.findObject(By.text("英超包年"));
                m_Expect = "英超包年";
                m_Actual = TextViewer.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("付费包页面跳转成功",m_Pass,m_Time);
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

    @Test //进入英超首页全部轮次新增选项-查看历史赛季
    public void LC_PL_05_EnterPremierLeagueAllRound(){
        this.EnterPLHomePage();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("赛程表")), 15000);
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadLeft();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        try {
           UiObject2 TextView = uiDevice.findObject(By.text("查看历史赛季"));
            m_Actual = TextView.getText();
            m_Expect = "查看历史赛季";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("页面未发现查看历史赛季选项",m_Pass,m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //英超查看历史赛季切换对比
    public void LC_PL_06_PremierLeagueAllRoundContrast(){
        this.EnterPLHomePage();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("赛程表")), 15000);
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadLeft();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject SaiJi = uiDevice.findObject(new UiSelector().text("2016-2017赛季").resourceId("com.bestv.ott:id/fa_round_title"));
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject SaiJi2 = uiDevice.findObject(new UiSelector().text("2015-2016赛季").resourceId("com.bestv.ott:id/fa_round_title"));
            if(SaiJi == SaiJi2){
                Utils.writeLogs("修改成功后历史赛季显示应不相同","历史赛季更改成功");
            }else{
                Utils.Print("历史赛季未更改成功");
                uiDevice.pressBack();
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //进入英超节目
    public void LC_PL_07_PremierLeagueProgram(){
        this.EnterPLHomePage();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("赛程表")), 15000);
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        try{
            UiObject TabTitle = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("节目"));
            m_Actual = TabTitle.getText();
            m_Expect = "节目";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入英超节目页面失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //进入视频分类一周更新视频
    public void LC_VC_01_EnterNewVideo() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("相关推荐")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败or初次进入页面时重试", m_uiObj != null, m_Time);
            uiDevice.pressBack();
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

    @Test //焦点大图切换
    public void LC_SY_01_FocusImageThreePoint() {
        try {
            systemWait(WAIT);
            m_ObjId = "com.bestv.ott:id/indicator";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/indicator"));
            Utils.writeCaseResult("Launcher首页焦点大图显示正确",m_uiObj !=null,m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr =e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //免费大片清晰度引导开通金卡会员
    public void LC_VIP_22_VipViaDefinition() {
        try {
            uiDevice.pressDPadDown();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("免费大片")), 15000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("开通金卡会员"));
            m_Actual = TextView.getText();
            m_Expect = "开通金卡会员";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框失败", m_Pass, m_Time);
            uiDevice.pressBack();
        }catch(Throwable e){
            e.printStackTrace();
            resultStr =e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //Launcher首页点击搜索框
    public void LC_SY_02_ClickSearchBar() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 Text = uiDevice.findObject(By.text("输入影片首字母或全拼"));
            m_Actual = Text.getText();
            m_Expect = "输入影片首字母或全拼";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("搜索页面跳转失败",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
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

    private void openTabFromLauncherHomeByresId(UiDevice device, UiObject2 resourceId) {
        resourceId.click();
        //resourceId.getParent().click();
        systemWait(SHORT_WAIT);
        device.pressDPadCenter();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void openTopBarFromLauncherHomeByresId(UiDevice device,UiObject2 resourceId){
        resourceId.click();
        systemWait(SHORT_WAIT);
        device.pressDPadCenter();
        device.waitForIdle();
        systemWait(WAIT);

    }

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    }

    private void RandomPlayNews(){//随机选择新闻的一级标题进入后播放
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random();
        int i;
        i=moveTimes.nextInt(16);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(WAIT);
        }
    } //随机播放新闻

    private void EnterNBAHomePage(){
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
//        UiObject2 NBAText = uiDevice.findObject(text("NBA"));
//        NBAText.click();
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
    } //进入NBA首页

    private void EnterPLHomePage(){
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
    } //进入英超首页

    private void EnterVideoClassifyPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
    } //进入视频分类

}
