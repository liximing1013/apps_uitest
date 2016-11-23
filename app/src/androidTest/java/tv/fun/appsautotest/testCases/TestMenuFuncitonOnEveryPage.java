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

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created by lixm on 2016/11/11.
 * 测试menu键在各个页面的操作
 * test case: 15
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder
public class TestMenuFuncitonOnEveryPage {
    Instrumentation instrument;
    UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 12;
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
        systemWait(SHORT_WAIT);
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

    @Test //电视Tab页面menu键操作
    public void LC_MENU_01_TVTabMenuOperation() {
        System.out.println("各个页面的menu键的响应测试开始了...");
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
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
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //视频Tab页面menu键操作
    public void LC_MENU_02_VideoTabMenuOperation() {
        try {
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView1 = uiDevice.findObject(By.text("搜索"));
            m_Actual = TextView1.getText();
            m_Expect = "搜索";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView2 = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = TextView2.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //体育Tab页面menu操作
    public void LC_MENU_04_SportsTabMenuOperation(){
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView1 = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView1.getText();
            m_Expect = "视频分类";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView2 = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView2.getText();
            m_Expect = "视频分类";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }

    }

    @Test //少儿Tab页面menu操作
    public void LC_MENU_06_ChildrenTabMenuOperation() {
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView1 = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView1.getText();
            m_Expect = "视频分类";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView2 = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView2.getText();
            m_Expect = "视频分类";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //应用Tab页面menu操作
    public void LC_MENU_08_AppTabMenuOperation(){
        try {
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView1 = uiDevice.findObject(By.text("搜索"));
            m_Actual = TextView1.getText();
            m_Expect = "搜索";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView2 = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = TextView2.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }

    }

    @Test //播放记录页menu键清空全部
    public void LC_MENU_11_EmptyVideoRecordInPlayRecord() {
        try {
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
            Utils.writeCaseResult("清空跳转失败or清空后提示错误", m_Pass, m_Time);
            uiDevice.pressBack();
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放记录页生成记录
    public void LC_MENU_12_DeleteVideoRecordInPlayRecord() {
        try {
            this.RightRightRight();
            systemWait(WAIT);
            uiDevice.pressEnter();
            systemWait(LONG_WAIT);
            //推荐位观看一影片生成播放记录
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressBack();
            uiDevice.pressHome();
            systemWait(WAIT);
            UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
            this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
            systemWait(WAIT);
            m_ObjId = "com.bestv.ott:id/no_data_title";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/no_data_title"));
            Utils.writeCaseResult("播放记录页无播放记录", m_uiObj == null, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放记录页视频卡片menu键操作
    public void LC_MENU_13_VideoRecordCardInPlayRecord() {
        try {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressMenu();
            systemWait(LONG_WAIT);
            UiObject2 DelOne = uiDevice.findObject(By.text("删除单个"));
            m_Actual = DelOne.getText();
            m_Expect = "删除单个";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("调起选项错误or未响应",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放记录页删除单个记录menu键操作
    public void LC_MENU_14_DelOneRecordInPlayRecord(){
        try {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            UiObject Title1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject Title2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            if(Title1.getText().equals(Title2.getText())){
                uiDevice.pressMenu();
                systemWait(WAIT);
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
            }else {
                System.out.println("播放记录收藏错误");
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //直播预约Tab menu操作
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
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //赛事预约页面清空全部比赛
    public void LC_MENU_24_EmptyMatchOrderInterface() {
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
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //猜你喜欢内视频menu收藏
    public void LC_MENU_28_WouldYouLikeCollectRecord() {
        try {
            //猜你喜欢进入时会重新刷新数据
            UiObject2 tabView1 = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
            this.openTabFromLauncherHomeByTextView(uiDevice, tabView1);
            systemWait(WAIT);
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            //通过menu键收藏该影片
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            uiDevice.pressMenu();
            UiObject2 TextViewer1 = uiDevice.findObject(text("已收藏"));
            Assert.assertEquals("选择menu后应显示已收藏", "已收藏", TextViewer1.getText());
            systemWait(WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressEnter();
            uiDevice.wait(Until.findObject(By.res("com.bestv.ott:id/detail_karma")), 15000);
            systemWait(LONG_WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("已收藏"));
//        Assert.assertEquals("详情页显示已收藏","已收藏",TextViewer2.getText());
            m_Expect = "已收藏";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("详情页收藏选项显示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //列表页Tab Menu操作
    public void LC_MENU_30_ListTabMenuOperation(){
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
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //列表页视频卡片menu操作
    public void LC_MENU_31_ListVideoCardMenuOperation(){
        try {
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("收藏"));
            m_Actual = TextView.getText();
            m_Expect = "收藏";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //详情页menu键操作
    public void LC_MENU_34_DetailPagesMenuOperation(){
        try {
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView.getText();
            m_Expect = "视频分类";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹框显示错误",m_Pass,m_Time);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView2 = uiDevice.findObject(By.text("视频分类"));
            m_Actual = TextView2.getText();
            m_Expect = "视频分类";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
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

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void RightRightRight(){
        int i = 0;
        while( i <= 2){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //R*3

}
