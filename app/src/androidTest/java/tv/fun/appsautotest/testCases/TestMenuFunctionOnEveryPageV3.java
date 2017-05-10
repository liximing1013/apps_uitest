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
 * Created by lxm on 2017/4/26
 * 测试menu键在各个页面的操作
 * TestCase: 34
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

        backToLauncherHome(uiDevice);
        uiDevice.waitForIdle();
        systemWait(WAIT);
    }

    @After //运行脚本之后回到launcher
    public void clearUp() {

        BackPageMethod();
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
                if(Menu != null) {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                    m_Actual = SearchPage.getText();
                    m_Expect = "用手机搜片";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
                }
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
                if(Menu != null) {
                    RightMoveMethod(1);
                    systemWait(SHORT_WAIT);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 Classify = uiDevice.findObject(By.text("最新"));
                    m_Actual = Classify.getText();
                    m_Expect = "最新";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
                }
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
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //播放记录卡片menu-搜索
    public void LC_MENU_12_SearchInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadLeft();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //播放记录卡片menu-播放
    public void LC_MENU_13_PlayingInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("播放"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
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

    @Test  //播放记录卡片menu-删除单个(播放记录>=2)
    public void LC_MENU_14_DelOnlyOneInPlayRecordCard(){
        try {
            EnterPlayRecordPage();
            UiObject2 Record = uiDevice.findObject(By.text("播放记录").res("com.bestv.ott:id/tab_title"));
            if(Record.isFocused()) {
                Utils.writeCaseResult("播放记录为空",false,m_Time);
            }else {
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
                if (RecordCount2 != RecordCount1) {
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
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
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
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

    @Test  //播放记录页Tab menu-清空全部
    public void LC_MENU_16_EmptyVideoRecordInPlayRecordTab() {
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 MenuIcon = uiDevice.findObject(By.res("android:id/tv_fun_menu"));
            List<UiObject2> Card = MenuIcon.findObjects(By.clazz("android.widget.LinearLayout"));
            if(Card.size() > 3){    //menu选项隐藏一个，所有+1
                uiDevice.pressBack();
                systemWait(SHORT_WAIT);
                UpMoveMethod(1);
                uiDevice.pressMenu();
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("您还没有看过任何影片，去看几部片子再回来吧"));
                m_Actual = SearchPage.getText();
                m_Expect = "您还没有看过任何影片，去看几部片子再回来吧";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("播放记录页面显示错误", m_Pass, m_Time);
            } else {
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("最新"));
                m_Actual = Classify.getText();
                m_Expect = "最新";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
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

    @Test  //播放记录页Tab menu-视频分类
    public void LC_MENU_17_EmptyRecordInPlayRecordTab() {
        try {
            EnterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 MenuIcon = uiDevice.findObject(By.res("android:id/tv_fun_menu"));
            List<UiObject2> Card = MenuIcon.findObjects(By.clazz("android.widget.LinearLayout"));
            if(Card.size() == 3){    //menu选项隐藏一个，所有+1
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Classify = uiDevice.findObject(By.text("最新"));
                m_Actual = Classify.getText();
                m_Expect = "最新";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
            } else {
                Utils.writeCaseResult("播放记录不为空,说明Case18是错误的", false, m_Time);
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
    public void LC_MENU_19_HadCollectInMyCollectPageCard(){
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
    public void LC_MENU_20_EmptyAllCollectInMyCollectPageCard(){
        try {
            EnterMyCollectPage();
            DownMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("清空全部"));
            if(Menu != null) {
                RightMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Col1 = uiDevice.findObject(By.text("亲，您确定要清空所有我的收藏吗？"));
                m_Actual = Col1.getText();
                m_Expect = "亲，您确定要清空所有我的收藏吗？";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Col2 = uiDevice.findObject(By.text("视频分类"));
                m_Actual = Col2.getText();
                m_Expect = "视频分类";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //赛事预约页面menu-取消预约
    public void LC_MENU_21_CancelAppointInMatchPageCard(){
        try{
            EnterMyCollectPage();
            systemWait(WAIT);
            UiObject GameOrder = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("赛事预约"));
            if(GameOrder.exists()){
                RightMoveMethod(1);
                DownMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("取消预约"));
                if(Menu != null) {
                    UiObject2 CancelOrder = uiDevice.findObject(By.text("取消预约"));
                    m_Expect = "取消预约";
                    m_Actual = CancelOrder.getText();
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("menu显示错误", m_Pass, m_Time);
                }else {
                    Assert.assertTrue(false);
                }
            }else{
                Utils.writeCaseResult("未有预约赛事",false,m_Time);
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

    @Test  //赛事预约Tab menu操作
    public void LC_MENU_22_LiveBookingPageMenuOperation(){
        try{
            EnterMyCollectPage();
            systemWait(WAIT);
            UiObject ZhiBoOrder = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("赛事预约"));
            if(ZhiBoOrder.exists()){
                RightMoveMethod(1);
                DownMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("取消全部"));
                if(Menu != null) {
                    RightMoveMethod(2);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 Col1 = uiDevice.findObject(By.text("亲，您确定要清空所有赛事预约吗？"));
                    m_Actual = Col1.getText();
                    m_Expect = "亲，您确定要清空所有赛事预约吗？";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
                }else {
                    Assert.assertTrue(false);
                }
            }else{
                Utils.writeCaseResult("未预约赛事",false,m_Time);
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
    public void LC_MENU_23_EmptyMatchOrderInterface() {
        try {
            EnterMyCollectPage();
            systemWait(WAIT);
            UiObject2 GameOrder= uiDevice.findObject(By.text("赛事预约"));
            if(GameOrder == null){
                Utils.writeCaseResult("未预约赛事",false,m_Time);
            }else {
                RightMoveMethod(1);
                systemWait(WAIT);
                uiDevice.pressMenu();
                systemWait(WAIT);
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 TextViewer = uiDevice.findObject(text("赛事预约"));//赛事预约界面清空时会Tab消失&TextViewer为空值时不能使用get取值
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
            EnterFilmListPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //列表页Tab Menu操作-筛选
    public void LC_MENU_32_ListTabMenuOperationTab2(){
        try {
            EnterFilmListPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 FilterPage = uiDevice.findObject(By.text("地区").res("com.bestv.ott:id/filter_page_title"));
                Assert.assertEquals("地区", FilterPage.getText());
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //列表页卡片-搜索
    public void LC_MENU_33_ListTabMenuOperationCard1(){
        try {
            EnterFilmListPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            DownMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if (Menu != null) {
                LeftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = SearchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //列表页卡片-筛选
    public void LC_MENU_34_ListTabMenuOperationCard2(){
        try {
            this.EnterFilmListPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            DownMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("筛选"));
            if(Menu != null) {
                LeftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 FilterPage = uiDevice.findObject(By.text("地区").res("com.bestv.ott:id/filter_page_title"));
                Assert.assertEquals("地区",FilterPage.getText());
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

    @Test  //列表页卡片-收藏
    public void LC_MENU_35_ListTabMenuOperationCard3(){
        try {
            EnterFilmListPage();
            RightMoveMethod(2);
            systemWait(WAIT);
            DownMoveMethod(1);
            systemWait(WAIT);
            UiObject2 Col = uiDevice.findObject(By.text("收藏"));
            Assert.assertEquals("收藏",Col.getText());
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);
            systemWait(LONG_WAIT);
            UiObject2 DelCol = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
            m_Actual = DelCol.getText();
            m_Expect = "已收藏";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("收藏成功",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //详情页menu-搜索
    public void LC_MENU_36_DetailsPageMenuOperation1(){
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 Rel = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("相关推荐",Rel.getText());
            uiDevice.pressMenu();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = SearchPage.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //详情页menu-视频分类
    public void LC_MENU_37_DetailsPageMenuOperation2(){
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 Rel = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("相关推荐",Rel.getText());
            uiDevice.pressMenu();
            systemWait(WAIT);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 Classify = uiDevice.findObject(By.text("最新"));
            m_Actual = Classify.getText();
            m_Expect = "最新";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //轮播menu-查看详情
    public void LC_MENU_41_CarouselMenuOperation1(){
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            UiObject2 PageTitle = uiDevice.findObject(By.res("com.bestv.ott:id/home_tv_feature_two_subtitle")
                    .clazz("android.widget.TextView"));
            String PT =PageTitle.getText().split("-")[0];  //截断字符
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            UpMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 DelPage = uiDevice.findObject(By.res("com.bestv.ott:id/detail_title")
                    .clazz("android.widget.TextView"));
            if(PT.equals(DelPage.getText())){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(false);
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

    @Test  //轮播menu-清晰度
    public void LC_MENU_42_CarouselMenuOperation2(){
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            LeftMoveMethod(1);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //轮播menu-画面比例
    public void LC_MENU_43_CarouselMenuOperation3(){
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            DownMoveMethod(1);
            LeftMoveMethod(1);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //我的应用-清理数据
    public void LC_MENU_51_MyAppMenuOperation1InTopBar(){
        try {
            UpMoveMethod(2);
            uiDevice.waitForIdle();
            UiObject2 MyApp = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, MyApp);
            systemWait(WAIT);
            UiObject2 AppTitle = uiDevice.findObject(text("我的应用"));
            Assert.assertEquals("我的应用",AppTitle.getText());
            UiObject2 Recycle = uiDevice.findObject(By.res("tv.fun.appstore:id/listview"));
            List<UiObject2> Cards = Recycle.findObjects(By.clazz("android.widget.RelativeLayout"));
            if(Cards.size()>=1){
                uiDevice.pressMenu();
                systemWait(WAIT);
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Uninstall = uiDevice.findObject(By.text("应用清理"));
                m_Actual = Uninstall.getText();
                m_Expect = "应用清理";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转页面错误",m_Pass,m_Time);
            }else {
                Utils.writeCaseResult("无应用存在",false,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //我的应用-卸载
    public void LC_MENU_52_MyAppMenuOperation2InTopBar(){
        try {
            UpMoveMethod(2);
            uiDevice.waitForIdle();
            UiObject2 MyApp = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, MyApp);
            systemWait(WAIT);
            UiObject2 AppTitle = uiDevice.findObject(text("我的应用"));
            Assert.assertEquals("我的应用",AppTitle.getText());
            UiObject2 Recycle = uiDevice.findObject(By.res("tv.fun.appstore:id/listview"));
            List<UiObject2> Cards = Recycle.findObjects(By.clazz("android.widget.RelativeLayout"));
            if(Cards.size()>=1){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Uninstall = uiDevice.findObject(By.text("卸载"));
                m_Actual = Uninstall.getText();
                m_Expect = "卸载";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("menu选项错误",m_Pass,m_Time);
            }else {
                Utils.writeCaseResult("无应用存在",false,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //消息中心-删除单条
    public void LC_MENU_53_MessageCenterMenuOperation2InTopBar(){
        try {
            UpMoveMethod(2);
            uiDevice.waitForIdle();
            systemWait(WAIT);
            UiObject2 Message = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId1(uiDevice, Message);
            UiObject2 AppTitle = uiDevice.findObject(text("消息中心"));
            Assert.assertEquals("消息中心",AppTitle.getText());
            UiObject Title = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title_tip"));
            systemWait(WAIT);
            if(!Title.exists()){
                UiObject2 PageText = uiDevice.findObject(By.text("暂未收到任何消息"));
                m_Actual = PageText.getText();
                m_Expect = "暂未收到任何消息";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }else {
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Delete = uiDevice.findObject(By.text("删除单条"));
                m_Actual = Delete.getText();
                m_Expect = "删除单条";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
                BackPageMethod();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //消息中心-清空全部
    public void LC_MENU_54_MessageCenterMenuOperation2InTopBar(){
        try {
            UpMoveMethod(2);
            uiDevice.waitForIdle();
            systemWait(WAIT);
            UiObject2 Message = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId1(uiDevice, Message);
            UiObject2 AppTitle = uiDevice.findObject(text("消息中心"));
            Assert.assertEquals("消息中心",AppTitle.getText());
            UiObject Title = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title_tip"));
            systemWait(WAIT);
            if(!Title.exists()){
                UiObject2 PageText = uiDevice.findObject(By.text("暂未收到任何消息"));
                m_Actual = PageText.getText();
                m_Expect = "暂未收到任何消息";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }else {
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Empty = uiDevice.findObject(By.text("清空全部"));
                m_Actual = Empty.getText();
                m_Expect = "清空全部";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
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

    private void openTabFromLauncherHomeByresId1(UiDevice device, UiObject2 resourceId) {
        resourceId.click();
        //resourceId.getParent().click();
        systemWait(SHORT_WAIT);
        device.waitForIdle();
        systemWait(WAIT);
    }

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

    private void EnterFilmListPage(){
        DownMoveMethod(1);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.waitForIdle();
        UiObject2 Recommend = uiDevice.findObject(By.text("推荐"));
        Assert.assertEquals("推荐",Recommend.getText());
    }  //进入电影列表页

    private void EnterTVTabPage(){
        UpMoveMethod(1);
        LeftMoveMethod(1);
        systemWait(WAIT);
        UiObject2 TabName = uiDevice.findObject(By.text("电视"));
        if(TabName.isSelected()) {
            DownMoveMethod(1);
            systemWait(WAIT);
        }else {
            Assert.assertTrue(false);
        }
    } //进入电视Tab页面

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void RightMoveMethod(int RightMove){
        int i = 1;
        while (i <= RightMove){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //Right*

    private void LeftMoveMethod(int LeftMove){
        int i = 1;
        while (i <= LeftMove){
            i++;
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
        }
    } //Left*

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
