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
 * Created by lixm on 2016/10/28
 * 测试播放记录页的收藏、播放记录、预约
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

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        uiDevice.pressHome();
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

    @Test //播放记录页生成记录
    public void LC_MENU_12_DeleteVideoRecordInPlayRecord() {
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("相关推荐")),15000);
        systemWait(WAIT);
        //推荐位观看一影片生成播放记录
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressBack();
        uiDevice.pressBack();
        uiDevice.pressHome();
        UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
        systemWait(WAIT);
//        UiObject2 TextView = uiDevice.findObject(By.res("com.bestv.ott:id/no_data_title"));
        m_ObjId = "com.bestv.ott:id/no_data_title";
        m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/no_data_title"));
        Utils.writeCaseResult("播放记录页无播放记录",m_uiObj == null,m_Time);
    }

    @Test //我的播放记录页menu键清空全部
    public void LC_MENU_11_EmptyVideoRecordInPlayRecord(){
        UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
        systemWait(WAIT);
        uiDevice.pressDPadUp();
        uiDevice.pressMenu();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadCenter();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        //加入判定是否已清空播放记录页面
        UiObject2 TextView = uiDevice.findObject(text("您还没有看过任何影片，去看几部片子再回来吧"));
//        Assert.assertEquals("播放记录页清空后文字显示","您还没有看过任何影片，去看几部片子再回来吧",TextView.getText());
        m_Expect = "您还没有看过任何影片，去看几部片子再回来吧";
        m_Actual = TextView.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("清空后提示错误",m_Pass,m_Time);
        uiDevice.pressBack();
    }

    @Test //猜你喜欢内视频收藏
    public void LC_SC_12_WouldYouLikeCollectRecord(){
        //猜你喜欢进入时会重新刷新数据
        UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
        systemWait(WAIT);
        uiDevice.pressDPadUp();
        uiDevice.pressDPadLeft();
        systemWait(WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressMenu();
        systemWait(SHORT_WAIT);
        //通过menu键收藏该影片
        uiDevice.pressDPadCenter();
        systemWait(SHORT_WAIT);
        uiDevice.pressMenu();
        UiObject2 TextViewer1 = uiDevice.findObject(text("已收藏"));
        Assert.assertEquals("选择menu后应显示已收藏","已收藏",TextViewer1.getText());
        systemWait(WAIT);
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.res("com.bestv.ott:id/detail_karma")),15000);
        systemWait(WAIT);
        UiObject2 TextViewer2 = uiDevice.findObject(text("已收藏"));
//        Assert.assertEquals("详情页显示已收藏","已收藏",TextViewer2.getText());
        m_Expect = "已收藏";
        m_Actual= TextViewer2.getText();
        m_Pass =m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("详情页显示错误",m_Pass,m_Time);
        uiDevice.pressBack();
    }

    @Test //小视频收藏记录显示&顶部快捷栏进入播放记录页
    public void LC_SV_10_SmallVideoCollectRecord() {
        //回到launcher首页
        uiDevice.pressHome();
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
//      UiObject Index= new UiObject(new UiSelector().className("android.widget.RelativeLayout").index(2));
        uiDevice.click(1200,300); //使用XY轴坐标定位到选择的焦点
        systemWait(SHORT_WAIT);
        uiDevice.pressEnter();
        systemWait(WAIT);
        uiDevice.pressDPadUp();
        //收藏该小视频专题
        uiDevice.pressDPadCenter();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
        systemWait(WAIT);
        //uiDevice.pressDPadUp();
        //点击2次back回到当前Tab
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId = uiDevice.findObject(By.res("com.bestv.ott:id/history"));
        this.openTabFromLauncherHomeByresId(uiDevice,ResId);
        systemWait(WAIT);
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressEnter();
        systemWait(WAIT);
        UiObject2 TextViewer = uiDevice.findObject(text("已收藏"));
//        Assert.assertEquals("已收藏",TextViewer.getText());
        m_Expect = "已收藏";
        m_Actual = TextViewer.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("播放记录错误显示",m_Pass,m_Time);
        uiDevice.pressBack();
    }

    @Test //赛事预约页面清空全部比赛预约
    public void LC_MENU_24_EmptyMatchOrderInterface(){
        UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
        this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
        uiDevice.pressDPadUp();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressMenu();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        UiObject2 TextViewer = uiDevice.findObject(text("赛事预约"));
        //赛事预约界面清空时会Tab消失&TextViewer为空值时不能使用get取值
        Assert.assertNull(TextViewer);
    }

    @Test //进入视频分类页直播大厅小窗口
    public void LC_BL_01_EnterBesTVLivePage(){
        this.EnterVideoClassifyPage();
        systemWait(WAIT);
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.res("com.bestv.ott:id/live_hall_feature_one")),15000);
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.pressEnter();
        systemWait(PauseTime);
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
    }

    @Test  //进入NBA首页全屏播放小窗口
    public void LC_NBA_01_EnterNBAHomePage(){
        systemWait(SHORT_WAIT);
        this.EnterNBAHomePage();
        uiDevice.wait(Until.findObject(By.text("赛程表 >")),10000);
        uiDevice.pressEnter();
        systemWait(PlayVideoTime);
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
        uiDevice.pressBack();
    }

    @Test //进入新闻大厅随机全屏播放一新闻
    public void LC_NEWS_11_FullScreenPlayRandomNews(){
        uiDevice.pressDPadRight();
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
        UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClazz);
        uiDevice.pressBack();
    }

    @Test  //看看新闻直播正常播放
    public void LC_NEWS_09_KanKanNewsNormalPlay(){
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
        UiObject2 VideoClass = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Assert.assertNotNull(VideoClass);
        //返回新闻大厅
        uiDevice.pressBack();
    }

    @Test  //排行榜任选一影片播放
    public void LC_PHB_04_RankListPlayVideo(){
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
        uiDevice.wait(Until.findObject(By.res("com.bestv.ott:id/detail_karma")),15000);
        systemWait(WAIT);
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
//        Assert.assertNotNull(TextView);
        m_Expect = "相关推荐";
        m_Actual = TextView.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("显示错误",m_Pass,m_Time);
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
    }

    @Test //进入视频分类页排行榜
    public void LC_PHB_02_EnterRankListPage(){
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
        Utils.writeCaseResult("排行榜页面显示正确",m_Pass,m_Time);
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        uiDevice.wait(Until.findObject(By.text("选集")),15000);
        systemWait(WAIT);
        UiObject2 TextView = uiDevice.findObject(By.text("相关推荐"));
        Assert.assertEquals("成功进入详情页","相关推荐",TextView.getText());
        m_Expect ="相关推荐";
        m_Actual = TextView.getText();
        m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
        Utils.writeCaseResult("显示错误",m_Pass,m_Time);
    }

    @Test //进入顶部快捷栏&逻辑判断电视上是否插有U盘
    public void LC_TB_03_EnterEveryTopBars() {
        uiDevice.pressHome();
        //进入天气APP
        uiDevice.pressDPadUp();
        uiDevice.pressDPadUp();
        systemWait(WAIT);
        UiObject2 ResId1 = uiDevice.findObject(By.res("com.bestv.ott:id/weather"));
        this.openTopBarFromLauncherHomeByresId(uiDevice,ResId1);
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
        this.openTabFromLauncherHomeByresId(uiDevice,ResId2);
        systemWait(WAIT);
        UiObject2 TextViewer2 = uiDevice.findObject(text("我的应用"));
//        Assert.assertEquals("我的应用",TextViewer2.getText());
        m_Expect = "我的应用";
        m_Actual = TextViewer2.getText();
        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
        Utils.writeCaseResult("进入我的应用失败",m_Pass,m_Time);
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId3 = uiDevice.findObject(By.res("com.bestv.ott:id/history"));
        this.openTabFromLauncherHomeByresId(uiDevice,ResId3);
        systemWait(WAIT);
        UiObject2 TextViewer3 = uiDevice.findObject(text("播放记录"));
        Assert.assertEquals("播放记录",TextViewer3.getText());
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId4 = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
        this.openTabFromLauncherHomeByresId(uiDevice,ResId4);
        systemWait(WAIT);
        UiObject2 TextViewer4 = uiDevice.findObject(text("消息中心"));
        Assert.assertEquals("消息中心",TextViewer4.getText());
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId5 = uiDevice.findObject(By.res("com.bestv.ott:id/setting"));
        this.openTabFromLauncherHomeByresId(uiDevice,ResId5);
        systemWait(WAIT);
        UiObject2 TextViewer5 = uiDevice.findObject(text("通用设置"));
        Assert.assertEquals("通用设置",TextViewer5.getText());
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId6 = uiDevice.findObject(By.res("com.bestv.ott:id/network"));
        this.openTabFromLauncherHomeByresId(uiDevice,ResId6);
        systemWait(LONG_WAIT);
        UiObject2 TextViewer6 = uiDevice.findObject(text("网络设置"));
        Assert.assertEquals("网络设置",TextViewer6.getText());
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        UiObject2 ResId7 = uiDevice.findObject(By.res("com.bestv.ott:id/storage"));
        if(ResId7 == null) {
            uiDevice.pressHome();
            uiDevice.waitForIdle();
        }else{
            this.openTabFromLauncherHomeByresId(uiDevice,ResId7);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            uiDevice.pressDPadDown();//进入音乐模块
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextViewer = uiDevice.findObject(text("按 \uE693 键查看更多操作"));
            Assert.assertNotNull("按 \uE693 键查看更多操作",TextViewer.getText());
            uiDevice.pressBack();
        }
    }

    @Test //英超首页进入后播放小视频专题
    public void LC_PL_01_PremierLeaguePlaySmallVideo(){
        this.EnterPLHomePage();
        uiDevice.pressEnter();
        uiDevice.wait(Until.findObject(By.text("赛程表")),15000);
        systemWait(WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
//       UiObject2 VideoClazz = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//       Assert.assertNotNull(VideoClazz);
        m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
        m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        Utils.writeCaseResult("英超首页小视频播放失败",m_uiObj != null,m_Time);
        uiDevice.pressBack();
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

    private void verifyOnLauncherHome(UiDevice device) {
        boolean ret = device.wait(Until.hasObject(By.pkg("com.bestv.ott").depth(0)), WAIT);
        Assert.assertTrue("Verify back to launcher home.", ret);
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
        Random moveTimes = new Random(1);
        int i;
        i=moveTimes.nextInt(20);
        for(int j= 0;j<=i;j++){
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(WAIT);
        }
    } //随机播放新闻

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

    private void EnterNBAHomePage(){
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        UiObject2 NBAText = uiDevice.findObject(text("NBA"));
        NBAText.click();
    } //进入NBA首页

    private void EnterPLHomePage(){
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadUp();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        UiObject2 PLText = uiDevice.findObject(text("英超"));
        PLText.click();
    } //进入英超首页

    private void EnterVideoClassifyPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadDown();
        uiDevice.pressEnter();
        systemWait(WAIT);
    } //进入视频分类
}
