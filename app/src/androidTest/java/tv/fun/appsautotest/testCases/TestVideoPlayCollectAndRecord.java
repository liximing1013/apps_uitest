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
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Random;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created by lixm on 2016/10/28
 * Mix
 * test case: 36
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVideoPlayCollectAndRecord {
    private Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 8;
    private static final int LONG_WAIT = 15;
    //设定播放视频时间
    private static final int PlayVideoTime = 60;
    //初始化
    String m_ObjId = "";
    private long m_Time;
    UiObject2 m_uiObj = null;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean resultFlag = true;
    private boolean m_Pass = false;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);

        uiDevice.pressHome();
        systemWait(WAIT);
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

    @Test //小视频收藏
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
               UiObject Text2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/special_player_favorite")
                       .text("已收藏"));
               m_Expect = "已收藏";
               m_Actual = Text2.getText();
               m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
               Utils.writeCaseResult("小视频收藏失败",m_Pass,m_Time);
           }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //视频分类页直播大厅
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
            Utils.writeCaseResult("进入直播大厅失败",m_uiObj != null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //NBA首页全屏播放小窗口
    public void LC_NBA_01_EnterNBAHomePage() {
        try {
            this.EnterNBAHomePage();
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("NBA小窗口播放小视频专题失败", m_uiObj != null, m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
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
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA一键预约页面
    public void LC_NBA_38_EnterNBAKeyAppointment(){
        try {
            this.EnterNBAHomePage();
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
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //购买NBA包
    public void LC_NBA_58_PayNBAPage(){
        try {
            this.EnterNBAHomePage();
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
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //NBA球队赛程
    public void LC_NBA_19_EnterNBATeamSchedule(){
        try {
            this.EnterNBAHomePage();
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
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }

    }

    @Test //NBA固定位专题类型
    public void LC_NBA_08_EnterNBAFixedLocation(){
        try{
            EnterNBAHomePage();
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject ImageView = uiDevice.findObject(new UiSelector().className("android.widget.ImageView")
                    .resourceId("com.bestv.ott:id/special_player_list_item_status_icon"));
            if(ImageView.exists()){
                System.out.println("小窗口视频播放暂停");
                uiDevice.pressDPadLeft();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                m_uiObj = uiDevice.findObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER));
                Utils.writeCaseResult("视频正常播放",m_uiObj != null,m_Time);
            }else {
                Utils.writeCaseResult("False",false,m_Time);
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

    @Test //新闻大厅全屏播放新闻
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

    @Test //看看新闻直播
    public void LC_NEWS_09_KanKanNewsNormalPlay() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);
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

    @Test //新闻头条生成记录
    public void LC_NEWS_12_AddNewsRecord(){
        try{
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            UiObject NewsTitle = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject NewsTitle2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            if(NewsTitle.getText().equals(NewsTitle2.getText())){
                Utils.writeCaseResult("播放记录页面显示正确",true,m_Time);
            }else {
                Utils.writeCaseResult("播放记录页面显示错误",false,m_Time);
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

    @Test //排行榜任选影片播放
    public void LC_PHB_04_RankListPlayVideo() {
        try {
            //排行榜数据每天更新一次，每天运行脚本时，播放影片不同
            this.EnterVideoClassifyPage();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            this.DownMoveNo1();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            //判断播放器是否播放
            UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Assert.assertNotNull(VideoClazz);
            systemWait(WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
            systemWait(WAIT);
            //判断是否退出后回到详情页
            UiObject2 TextView = uiDevice.findObject(text("相关推荐"));
            m_Expect = "相关推荐";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("视频播放退回到详情页时显示错误", m_Pass, m_Time);
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //视频分类页排行榜
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
            m_Actual = "TOP1";
            m_Expect = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("排行榜页面显示错误", m_Pass, m_Time);
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("成功进入详情页", "相关推荐", TextView.getText());
            m_Expect = "相关推荐";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("无法进入媒体详情页", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //顶部快捷栏--天气
    public void LC_TB_03_EnterWeatherTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 Weather = uiDevice.findObject(By.res("com.bestv.ott:id/weather"));
            this.openTopBarFromLauncherHomeByresId(uiDevice, Weather);
            systemWait(WAIT);
            UiObject2 TextViewer1 = uiDevice.findObject(text("按 \uE693 菜单键管理城市"));
            m_Expect = "按 \uE693 菜单键管理城市";
            m_Actual = TextViewer1.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入天气页面失败！", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //顶部快捷栏--我的应用
    public void LC_TB_04_EnterAppTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId2 = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId2);
            systemWait(WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("我的应用"));
            m_Expect = "我的应用";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入我的应用失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //顶部快捷栏--播放记录
    public void LC_TB_05_EnterHistoryTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/history"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId3);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("播放记录"));
            m_Expect = "播放记录";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入播放记录页失败", m_Pass, m_Time);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //顶部快捷栏--消息中心
    public void LC_TB_06_EnterMessageTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId3);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("消息中心"));
            m_Expect = "消息中心";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入消息中心页面失败", m_Pass, m_Time);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //顶部快捷栏--通用设置
    public void LC_TB_07_EnterSettingsTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/setting"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId3);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("通用设置"));
            m_Expect = "通用设置";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入通用设置页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //顶部快捷栏--网络
    public void LC_TB_08_EnterNetworkTopBars(){
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/network"));
            this.openTabFromLauncherHomeByresId(uiDevice, ResId3);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("网络设置"));
            m_Expect = "网络设置";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入网络页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            Utils.writeCaseResult(resultStr,resultFlag, m_Time);
        }
    }

    @Test //顶部快捷栏--U盘
    public void LC_TB_09_EnterStorageTopBars() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 ResId7 = uiDevice.findObject(By.res("com.bestv.ott:id/storage"));
            if (ResId7 == null) {
                uiDevice.pressHome();
                uiDevice.waitForIdle();
                System.out.println("当前电视未插有U盘，请插入U盘在进行测试...");
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
                m_Actual = TextViewer.getText();
                m_Expect = "按 \uE693 键查看更多操作";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("进入U盘页面失败",m_Pass,m_Time);
            }
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //英超播放小视频专题
    public void LC_PL_03_PremierLeaguePlaySmallVideo() {
        try {
            this.EnterPLHomePage();
            systemWait(WAIT);
            uiDevice.pressEnter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("英超首页小视频播放失败", m_uiObj != null, m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr +=e.toString();
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
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //英超首页全部轮次新增选项--查看历史赛季
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

    @Test //英超--查看历史赛季切换对比
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
        UiObject SaiJi = uiDevice.findObject(new UiSelector().text("2016-2017赛季")
                .resourceId("com.bestv.ott:id/fa_round_title"));
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject SaiJi2 = uiDevice.findObject(new UiSelector().text("2015-2016赛季")
                    .resourceId("com.bestv.ott:id/fa_round_title"));
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

    @Test //英超节目列表页
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
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //英超主页预约比赛
    public void LC_PL_08_PremierLeaguePageOrderMatch(){
        try{
            EnterPLHomePage();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject OrderIcon = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/livehint")
                    .text("预约"));
            if(OrderIcon.exists()){
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadLeft();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 OrderText = uiDevice.findObject(By.text("取消预约"));
                m_Actual = "取消预约";
                m_Expect = OrderText.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("页面选项显示正确",m_Pass,m_Time);
            }else {
                System.out.println("无可预约英超比赛");
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

    @Test //视频分类一周更新视频
    public void LC_VC_01_EnterNewVideo() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败or初次进入页面时重试", m_uiObj != null, m_Time);
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

    @Test //焦点大图5s切换
    public void LC_SY_01_FocusImageThreePoint() {
        try {
            systemWait(WAIT);
            m_ObjId = "com.bestv.ott:id/indicator";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/indicator"));
            Utils.writeCaseResult("Launcher首页焦点大图显示正确",m_uiObj !=null,m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //免费大片--清晰度引导开通金卡会员
    public void LC_VIP_22_VipViaDefinition() {
        try {
            this.EnterFreeFilmPage();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject DetPayButton = uiDevice.findObject(new UiSelector().text("付费"));
            if(DetPayButton.exists()){
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
                Utils.writeCaseResult("开通会员提示弹框失败", m_Pass, m_Time);
                uiDevice.pressBack();
            }else {
                System.out.println("亲爱的风行用户，你已经是金卡会员了，无需引导了.....么么哒");
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultStr =e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //Launcher首页--搜索框
    public void LC_SY_02_ClickSearchBar() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            this.RightMoveNo2();
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

    @Test //Launcher首页页面图片配置
    public void LC_SY_03_LauncherMove() {
        try {
            this.RightMoveNo1();
            UiObject2 Text = uiDevice.findObject(By.focused(true));
            Assert.assertEquals(Text.getText(),true,true);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //列表页筛选页面
    public void LC_SX_01_FilterPageChoose(){
        try{
            EnterFilmFilterPage();
            uiDevice.pressDPadDown();
            systemWait(WAIT);
            UiObject FilterT = uiDevice.findObject(new UiSelector().text("地区"));
            Assert.assertEquals("地区",FilterT.getText());
            List<UiObject2> list = uiDevice.findObjects(By.res("com.bestv.ott:id/filter_page_title"));
            if(list.size() == 5 ){
                Utils.writeCaseResult("True",true,m_Time);
            }else {
                Utils.writeCaseResult("False",false,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag =false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //详情页--演职员表跳转
    public void LC_DETAIL_06_DetailsPagePerformerJump(){
        try{
            RightMoveNo3();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject ActorName1 = uiDevice.findObject(new UiSelector().focused(true));
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 ActorName2 = uiDevice.findObject(By.res("com.bestv.ott:id/top_title"));
            String a1 = ActorName1.getText() + "的相关影片";
            String a2 = ActorName2.getText();
            if (a1 == a2) {
                System.out.print("True" + a2);
            } else {
                System.out.print("false" +a1);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //详情页--付费选项跳转
    public void LC_DETAIL_07_DetailsPagePayPackPageJump() {
        try {
            RightMoveNo3();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject VideoName = uiDevice.findObject(new UiSelector().className("android.widget.TextView")
            .resourceId("com.bestv.ott:id/detail_title"));
            UiObject2 PayVip = uiDevice.findObject(By.text("付费"));
            if(PayVip != null){
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 PayTitle = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                Assert.assertEquals("请选择要购买的媒体或服务",PayTitle.getText());
                uiDevice.pressDPadDown();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject PayVip1 = uiDevice.findObject(new UiSelector().text("付费开通"));
                m_Actual = PayVip1.getText();
                m_Expect = "付费开通";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转至付费包页面失败",m_Pass,m_Time);
            }else {
                System.out.println("您可以直接观看："+VideoName.getText());
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

    @Test //详情页--片花选项
    public void LC_DETAIL_08_DetailsPageTrailers(){
        try{
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject Trail = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/icon"));
            if(Trail.exists()){
                RightMoveNo3();
                UiObject2 TrailText = uiDevice.findObject(By.text("精彩片花"));
                Assert.assertEquals("精彩片花",TrailText.getText());
                systemWait(WAIT);
                uiDevice.pressDPadDown();
                m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_related_vedio_tag"));
                m_ObjId = "com.bestv.ott:id/detail_related_vedio_tag";
                Utils.writeCaseResult("片花显示错误",m_uiObj != null ,m_Time);
            }else {
                Utils.writeCaseResult("本片无精彩片花",false,m_Time);
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

    @Test //详情页--更多选项
    public void LC_DETAIL_09_DetailsPageMoreButton(){
        try{
            RightMoveNo3();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject More = uiDevice.findObject(new UiSelector().text("更多>").resourceId("com.bestv.ott:id/detail_more_text"));
            if(More.exists()){
                uiDevice.pressDPadUp();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Brief = uiDevice.findObject(By.text("简 介"));
                m_Actual = Brief.getText();
                m_Expect = "简 介";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("简介页面跳转失败",m_Pass,m_Time);
            }else {
                System.out.println("本片无更多选项");
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
        systemWait(WAIT);
        device.pressDPadCenter();
        device.waitForIdle();
        systemWait(WAIT);

    }

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void EnterFilmFilterPage() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressDPadLeft();
        systemWait(WAIT);
        uiDevice.pressDPadLeft();
        systemWait(WAIT);
    } //进入电影筛选页面

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

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
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
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

    private void EnterFreeFilmPage(){
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("免费大片")), 15000);
        systemWait(LONG_WAIT);
    }//进入免费大片页面

    private void RightMoveNo1(){
        int i = 0;
        while (i<=24){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //右移步数1

    private void RightMoveNo2(){
        int i = 0;
        while (i<=5){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    }//右移步数2

    private void RightMoveNo3(){
        int i = 0;
        while (i<=2){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    }//右移步数3

    private void LeftMoveNo1(){
        int i = 0;
        while (i<=3){
            i++;
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
        }
    }//右移步数3

    private void DownMoveNo1(){
        int i = 0;
        while (i<=3){
            i++;
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
        }
    }//下移步数1

}
