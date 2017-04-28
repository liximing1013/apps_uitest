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
import android.util.Log;

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

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created by lixm on 2017/4/26
 * 测试menu键在各个页面的操作
 * TestCase:
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMenuFunctionOnEveryPageV3 {
    private Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 12;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    //初始化
    private String m_ObjId = "";
    private long m_Time;
    private UiObject2 m_uiObj = null;
    private boolean m_Pass = false;
    private boolean resultFlag = true;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";

    @BeforeClass
    public static void BeforeClass(){

        System.out.println("各个页面的menu键的响应测试开始了...");
    }

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time= Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        uiDevice.waitForIdle();
        systemWait(WAIT);
    }

    @After //运行脚本之后回到launcher
    public void clearUp() {
        backToLauncherHome(uiDevice);
        systemWait(WAIT);
    }

    @AfterClass
    public static void AfterClass(){

        System.out.println("See You Later");
    }

    @Test  //获取用例名
    public void test(){

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test  //电视Tab页面menu操作
    public void LC_MENU_02_TVTabMenuOperation() {
        try {
            UpMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("电视"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
                Assert.assertEquals("搜索",Menu.getText());
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //视频Tab页面menu操作
    public void LC_MENU_03_VideoTabMenuOperation() {
        try {
            UpMoveMethod(1);
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("视频"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("视频分类"));
                Assert.assertEquals("视频分类",Menu.getText());
                RightMoveMethod(1);
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("最新"));
                m_Actual = Classify.getText();
                m_Expect = "最新";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //体育Tab页面menu操作
    public void LC_MENU_04_SportsTabMenuOperation(){
        try {
            UpMoveMethod(1);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("体育"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                Assert.assertEquals("设置",Menu.getText());
                RightMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("设 置"));
                m_Actual = Classify.getText();
                m_Expect = "设 置";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转设置页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //生活Tab页面menu操作
    public void LC_MENU_05_LifeTabMenuOperation(){
        try {
            UpMoveMethod(1);
            RightMoveMethod(2);
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("生活"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("视频分类"));
                Assert.assertEquals("视频分类",Menu.getText());
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("最新"));
                m_Actual = Classify.getText();
                m_Expect = "最新";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //会员Tab页面menu操作
    public void LC_MENU_06_VipTabMenuOperation(){
        try {
            UpMoveMethod(1);
            RightMoveMethod(3);
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("会员"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                Assert.assertEquals("设置",Menu.getText());
                RightMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("设 置"));
                m_Actual = Classify.getText();
                m_Expect = "设 置";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转设置页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //应用Tab页面menu操作
    public void LC_MENU_07_AppTabMenuOperation() {
        try {
            UpMoveMethod(1);
            RightMoveMethod(4);
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("应用"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
                Assert.assertEquals("搜索",Menu.getText());
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //游戏Tab页面menu操作
    public void LC_MENU_08_AppTabMenuOperation(){
        try {
            UpMoveMethod(1);
            RightMoveMethod(5);
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("游戏"));
            if(TabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                Assert.assertEquals("设置",Menu.getText());
                RightMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("设 置"));
                m_Actual = Classify.getText();
                m_Expect = "设 置";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转设置页面错误",m_Pass,m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //播放记录生成记录
    public void LC_MENU_11_DeleteVideoRecordInPlayRecord() {
        try {
            RightMoveMethod(2);
            systemWait(WAIT);
            UiObject Title1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressBack();
            UiObject2 PlayRecord = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
            this.openTabFromLauncherHomeByTextView(uiDevice, PlayRecord);
            UiObject Title2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            if(Title1.getText().equals(Title2.getText())){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录卡片menu-搜索
    public void LC_MENU_12_SearchInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = SearchPage.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录卡片menu-播放
    public void LC_MENU_13_PlayingInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("视频播放失败",m_uiObj !=null,m_Time);
            BackPageMethod();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录卡片menu-删除单个
    public void LC_MENU_14_DelOnlyOneInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            UiObject2 Recycle1 = uiDevice.findObject(By.res("com.bestv.ott:id/recyclerview"));
            List<UiObject2> Cards1 = Recycle1.findObjects(By.clazz("android.widget.RelativeLayout"));
            int RecordCount1 = Cards1.size();//获取初次进入时页面卡片数量
            uiDevice.pressMenu();
            systemWait(WAIT);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 Recycle2 = uiDevice.findObject(By.res("com.bestv.ott:id/recyclerview"));
            List<UiObject2> Cards2 = Recycle2.findObjects(By.clazz("android.widget.RelativeLayout"));
            int RecordCount2 = Cards2.size();//获取删除单个后页面卡片数量
            if(RecordCount2 != RecordCount1){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录页tab menu-搜索
    public void LC_MENU_15_SearchInPlayRecordCardTab(){
        try {
            EnterPlayRecordPage();
            UpMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = SearchPage.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
            BackPageMethod();
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录页Tab menu-清空全部
    public void LC_MENU_16_EmptyVideoRecordInPlayRecordTab() {
        try {
            EnterPlayRecordPage();
            UpMoveMethod(1);
            uiDevice.pressMenu();
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();    //加入判定是否已清空播放记录页面
            systemWait(SHORT_WAIT);
            UiObject2 TextView = uiDevice.findObject(text("您还没有看过任何影片，去看几部片子再回来吧"));
            m_Expect = "您还没有看过任何影片，去看几部片子再回来吧";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("清空跳转失败or清空后提示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录页Tab menu-视频分类
    public void LC_MENU_17_EmptyRecordInPlayRecordTab() {
        try {

        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //猜你喜欢内视频menu收藏
    public void LC_MENU_18_WouldYouLikeCollectRecord() {
        try {
            EnterPlayRecordPage();   //猜你喜欢进入时会重新刷新数据
            UpMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            DownMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);  //通过menu键收藏该影片
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            DownMoveMethod(1);
            uiDevice.pressMenu();
            UiObject2 TextViewer1 = uiDevice.findObject(text("已收藏"));
            Assert.assertEquals("选择menu后应显示已收藏", "已收藏", TextViewer1.getText());
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressEnter();
            uiDevice.waitForIdle(18000);
            systemWait(LONG_WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("已收藏"));
            m_Expect = "已收藏";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("详情页收藏选项显示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //我的收藏卡片menu-已收藏
    public void LC_MENU_18_HadCollectInMyCollectPageCard(){
        try {
            EnterMyCollectPage();
            UiObject2 Recycle1 = uiDevice.findObject(By.res("com.bestv.ott:id/recyclerview"));
            List<UiObject2> Cards1 = Recycle1.findObjects(By.clazz("android.widget.RelativeLayout"));
            if(Cards1.size() >= 1) {
                DownMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 ColText = uiDevice.findObject(By.text("已收藏"));
                Assert.assertEquals("已收藏",ColText.getText());
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
            }else {
                Log.d("LXM", "LC_MENU_18_HadCollectInMyCollectPageCard: 我的收藏无记录");
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

    @Test  //我的收藏卡片menu-清空全部
    public void LC_MENU_18_EmptyAllCollectInMyCollectPageCard(){

    }

    @Test  //赛事预约页面menu-取消预约
    public void LC_MENU_19_CancelAppointInMatchPageCard(){

    }

    @Test  //赛事预约Tab menu操作
    public void LC_MENU_20_LiveBookingPageMenuOperation(){
        try{
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadUp();
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            UiObject ZhiBoOrder = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title"));
            if(ZhiBoOrder.getText().equals("赛事预约")){
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadRight();
                systemWait(WAIT);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 SouSuo = uiDevice.findObject(By.text("取消全部"));
                m_Expect = "取消全部";
                m_Actual = SouSuo.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("menu选项错误",m_Pass,m_Time);
            }else{
                uiDevice.pressBack();
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //赛事预约页面清空全部比赛
    public void LC_MENU_21_EmptyMatchOrderInterface() {
        try {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 TabViewer= uiDevice.findObject(By.text("赛事预约"));
            if(TabViewer == null){
                System.out.println("没有预约赛事Tab，么么哒！Please go order");
            }else {
                uiDevice.pressDPadUp();
                uiDevice.pressDPadRight();
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                UiObject2 TextViewer = uiDevice.findObject(text("赛事预约"));
                //赛事预约界面清空时会Tab消失&TextViewer为空值时不能使用get取值
                Assert.assertNull(TextViewer);
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

    @Test  //列表页Tab Menu操作-搜索
    public void LC_MENU_31_ListTabMenuOperationTab1(){
        try {
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("搜索"));
            m_Actual = TextView.getText();
            m_Expect = "搜索";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //列表页Tab Menu操作-筛选
    public void LC_MENU_32_ListTabMenuOperationTab2(){

    }

    @Test  //列表页卡片-搜索
    public void LC_MENU_33_ListTabMenuOperationCard1(){

    }

    @Test  //列表页卡片-筛选
    public void LC_MENU_34_ListTabMenuOperationCard2(){

    }

    @Test  //列表页卡片-收藏
    public void LC_MENU_35_ListTabMenuOperationCard3(){

    }

    @Test //详情页menu-搜索
    public void LC_MENU_36_DetailsPageMenuOperation1(){

    }

    @Test //详情页menu-视频分类
    public void LC_MENU_37_DetailsPageMenuOperation2(){

    }

    @Test //轮播menu-查看详情
    public void LC_MENU_41_CarouselMenuOperation1(){

    }

    @Test //轮播menu-清晰度
    public void LC_MENU_42_CarouselMenuOperation2(){

    }

    @Test //轮播menu-画面比例
    public void LC_MENU_43_CarouselMenuOperation3(){

    }

    @Test //我的应用-清理数据
    public void LC_MENU_51_MyAppMenuOperation1InTopBar(){

    }

    @Test //我的应用-卸载
    public void LC_MENU_52_MyAppMenuOperation2InTopBar(){

    }

    @Test //消息中心-删除单条
    public void LC_MENU_53_MessageCenterMenuOperation2InTopBar(){

    }

    @Test //消息中心-清空全部
    public void LC_MENU_54_MessageCenterMenuOperation2InTopBar(){

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

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void EnterPlayRecordPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        UiObject2 Record = uiDevice.findObject(By.text("播放记录"));
        Assert.assertEquals("播放记录",Record.getText());
    } //进入播放记录页面

    private void EnterMyCollectPage(){
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        UpMoveMethod(1);
        RightMoveMethod(1);
        UiObject2 Collect = uiDevice.findObject(By.text("我的收藏"));
        if(Collect.isSelected()){
            Assert.assertTrue(true);
        }else {
            Assert.assertTrue(false);
        }
    }  //进入我的收藏页

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
