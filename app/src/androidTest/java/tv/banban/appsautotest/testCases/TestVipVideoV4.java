package tv.banban.appsautotest.testCases;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import tv.banban.appsautotest.common.CommonMethod;
import tv.banban.appsautotest.common.TvCommon;
import tv.banban.common.Utils;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestVipVideoV4 extends CommonMethod{

    @BeforeClass
    public static void BeforeClass() {

    }
//
//    @Before
//    @Override
//    public void setUp() {
//        super.setUp();
//        m_Time = Utils.getCurSecond();   //耗时
//        output("用例开始执行");
//        myWatcher = new MyWatcher(uiDevice);
//        uiDevice.registerWatcher("testwatcher",myWatcher);
//
////        uiDevice.pressHome();
//        uiDevice.waitForIdle();
//        Configurator configurator = Configurator.getInstance();
//        configurator.setActionAcknowledgmentTimeout(5000);
//    }
//
//
//    @After
//    @Override
//    public void tearDown() {
//        super.tearDown();
//        output("用例执行完毕");
//    }

//    @AfterClass
//    public static void AfterClass(){
//
//        System.gc();
//    }

    @Test  //获取用例名
    public void test(){

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test //fix
    public void LC_VIP_01_EnterVipPage() {
        try {
            uiDevice.wakeUp();
            Thread.sleep(1000);
//            Runtime.getRuntime().exec("am force-stop tv.fun.weather");
//            uiDevice.waitForWindowUpdate("com.bestv.ott",12000);
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getLauncherPackageName());//com.bestv.ott
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getProductName());//aosp_almond
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getDisplaySizeDp());//Point(1280, 720)
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getCurrentPackageName());//tv.fun.weather
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getCurrentActivityName());//风行天气
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getDisplayHeight());//1080
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getDisplayWidth());//1920
            Log.d("lxm", "LC_VIP_01_EnterVipPage: "+uiDevice.getLastTraversedText());//null
            Log.d(TAG, "LC_VIP_01_EnterVipPage: "+getCurDate());
            Log.d(TAG, "LC_VIP_01_EnterVipPage: "+getCurMonthOfDay(2018,1));
            Log.d(TAG, "LC_VIP_01_EnterVipPage: "+getCurWeekOfDayInYear(2018,1,25));
            Log.d(TAG, "LC_VIP_01_EnterVipPage: "+"李".equals("李"));
//            String ss = "com.funshion.xiriassist/com.funshion.xiriassist.MainActivity";
//            startApp(ss);
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

    @Test //全屏播放会员小窗口fix
    public void LC_VIP_02_HatchToFullScreenPlay() throws UiObjectNotFoundException,RemoteException,NullPointerException{
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
//        UiDevice uiDevice = UiDevice.getInstance(instrumentation);
        Context context = instrumentation.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("tv.fun.weather");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Test
    public void tabChoose() {
        try {
            UiObject tab = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/indicator"));
            UiObject tabShow = tab.getChild(new UiSelector().className("android.widget.LinearLayout"));
            int tabShowCount = tabShow.getChildCount();
            super.pressRight(tabShowCount - 3);
            uiDevice.pressDPadCenter();
            systemWait(2);
            UiObject2 tabSin = uiDevice.findObject(By.clazz("android.widget.RelativeLayout").focused(true));
            int tabView = tabSin.getChildCount();
            for (int i = 2; i <= tabShowCount - 3; i++) {
                systemWait(1);
                if (tabView == 3) {
                    uiDevice.pressEnter();
                }
                uiDevice.pressBack();
                uiDevice.waitForIdle();
                uiDevice.pressEnter();
            }
            if (tabView == 1) {
                uiDevice.waitForIdle();
                uiDevice.pressBack();
            } else {
                UiObject2 tabMan = uiDevice.findObject(By.text("设置桌面频道"));
                if (tabMan != null) {
                    uiDevice.pressBack();
                } else {
                    output("launcher首页" + getNow());
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            uiDevice.pressHome();
        }
    }

    @Test
    public void testUrl() throws NullPointerException{
        String url1 = "http://jm.funtv.bestv.com.cn/media/detailtab/v3?id=316267&mac=28:76:CD:00:00:01";
       try{
        URL url = new URL(url1);
//        Log.d(TAG, "URL: "+url.toString());
//        Log.d(TAG, "协议: "+url.getProtocol());
//        Log.d(TAG, "验证信息: "+url.getAuthority());
//        Log.d(TAG, "文件名: "+url.getFile());
//        Log.d(TAG, "主机名: "+url.getHost());
//        Log.d(TAG, "路径：" + url.getPath());
//        Log.d(TAG, "端口：" + url.getPort());
//        Log.d(TAG, "默认端口：" + url.getDefaultPort());
//        Log.d(TAG, "请求参数：" + url.getQuery());
//        Log.d(TAG, "定位位置：" + url.getRef());
        try {
            //通过URL的openStream方法获取URL对象所表示的资源字节输入流
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");

            //字符输入流添加缓冲
            BufferedReader br = new BufferedReader(isr);
            String data = br.readLine();//读取数据
            while (data != null) {//循环读取数据
                Log.d(TAG, "testUrl: "+data); //输出数据
                Log.d(TAG, "testUrl: "+data.length());
                Log.d(TAG, "testUrl: "+data.indexOf("u76f8"));
                if(data.contains("200")){
                    Log.d(TAG, "testUrl: "+data.substring(data.indexOf("u76f8")-1,data.indexOf("u76f8")+24));
                    String q1 = data.substring(data.indexOf("u76f8")-1,data.indexOf("u76f8")+23);
                    Log.d(TAG, "testUrl: "+ascii2Native(q1));
                }
                data = br.readLine();
            }
            br.close();
            isr.close();
            is.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Test //新闻title
    public void testIndex() throws UiObjectNotFoundException {
        if(uiDevice.hasObject(By.res("com.bestv.ott:id/icon"))){
            UiObject container = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/container").index(2));
            UiObject title = container.getChild(new UiSelector().resourceId("com.bestv.ott:id/title").className("android.widget.TextView"));
            Log.d(TAG, "testIndex: "+title.getText());
        }
    }

    @Test
    public void my() throws UiObjectNotFoundException{
        UiObject view = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/list_view"));
        int card = view.getChildCount();
        Log.d(TAG, "my: "+card); //9
        UiObject qq = view.getChild(new UiSelector().text("网络设置"));
        qq.clickAndWaitForNewWindow();
    }

    @Test //电视
    public void down(){
        if(uiDevice.hasObject(By.res("com.bestv.ott:id/add_icon"))){
            pressDown(3);
        }
    }

    @Test //搜索
    public void down1(){
//        if(uiDevice.hasObject(By.res("com.bestv.ott:id/search").text("搜索片名、演员、导演"))){
//            pressDown(3);
//            pressEnter(1);

        int total = 0;
        int[] qwe = new int[3];
        qwe[0]= 1;
        qwe[1]= 12;
        qwe[2]= 3;
        Log.d(TAG, "down1: "+qwe.length);
//        for(int i =0;i<qwe.length;i++){
//            total += qwe[i];
//        }
//        for (int i:qwe) {
//            total += qwe[i];
//        }
        Log.d(TAG, "down1: "+total);
    }

    @Test
    public void stringTest(){
        String s1 = "Hello world";
        String s2 = "Hello java";
//        //判断两个字符串是否相等
//        if(s1.equals(s2)){
//            output("equal");
//        }
//        output("not equal");
//
//        //判断s1是否以He开头
//        if(s1.startsWith("He")){
//            output("begin with He");
//        }
//        //判断s1是否以world结尾
//        if(s1.endsWith("world")){
//            output("end with World");
//        }

        //返回S1中字母d的下标
        output("s1 中字母d的索引为： "+ s1.indexOf('d'));

//        //返回索引从3到8的子字符串
//        output("索引从3到8的子字符串： "+ s1.substring(3,8));
//
//        //拼接
//        output("拼接到后面： "+ s1.concat("abcd"));
//
//        //替换单个字母
//        output("替换单个字母： "+ s1.replace('H','z'));
//
//        //替换第一个字母
//        output("替换第一个字母： "+ s2.replaceFirst("H","AABB"));
//
//        //是否包含world
//        output("是否包含world： "+ s1.contains("world"));
//
//        //根据空格切割
//        output("根据空格切割： "+ s1.split(" ")[0] +" " + s1.split(" ")[1]);
    }

    @Test
    public void banBan(){
        Context context = InstrumentationRegistry.getContext();
        Intent launchIntent = new Intent();
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动应用
        launchIntent.setComponent(new ComponentName("com.imbb.banban.android", "com.imbb.banban.android.MainActivity"));
        context.startActivity(launchIntent);
        systemWait(6);
        UiObject2 gc = uiDevice.findObject(By.text("广场"));
        if(gc != null){
            UiObject2 me = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
            me.click();
            systemWait(1);
            UiObject2 gcc = uiDevice.findObject(By.text("上广场"));
            gcc.clickAndWait(Until.newWindow(), 3000);
        }
    }

    public void startBanBan() {
        Context context = InstrumentationRegistry.getContext();
        Intent launchIntent = new Intent();
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动应用
        launchIntent.setComponent(new ComponentName("com.imbb.banban.android", "com.imbb.banban.android.MainActivity"));
        context.startActivity(launchIntent);
    }

    @Test
    public void test1() throws RemoteException,UiObjectNotFoundException{
        uiDevice.pressRecentApps();
        UiObject recApp = uiDevice.findObject(new UiSelector().resourceId("com.android.systemui:id/img"));
        do{
            recApp.waitForExists(2000);
            if(recApp.exists()){
                recApp.swipeUp(1);
            }
        }while (recApp.exists());
    }

    @Test
    public void login(){
        try{
//            startBanBan();
            UiObject2 me = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
            me.click();
            UiObject viewList = uiDevice.findObject(new UiSelector().className("android.view.ViewGroup"));
            UiObject view = viewList.getChild(new UiSelector().className("android.widget.ImageView"));
            int viewCount = view.getChildCount();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}

//    @Test //小窗口跳转媒体页
//    public void LC_VIP_03_PlayCompleteVideo() {
//        try {
//            UiObject2 playTitle = uiDevice.findObject(By.res("").clazz(""));
//            uiActions.pressEnter(1);
//            commonMethod.playVideo(3);
//            uiDevice.pressDPadCenter();
//            commonMethod.waitForNewWindowIdle(18000);
//            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
//            if(payButton.exists()){
////                systemWait(PlayVideoMidTime);
//                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
//                m_Actual = payMedia.getText();
//                m_Expect = "请选择要购买的媒体或服务";
//                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
//                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
//            }else {
//                UiObject2 fullScreen = uiDevice.findObject(By.text("全屏"));
////                this.openTabFromLauncherHomeByVipText(uiDevice,fullScreen);
//                uiDevice.pressDPadCenter();
////                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
//                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        } finally {
//            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
//        }
//    }

//    @Test //会员特权页面
//    public void LC_VIP_04_VipMemberPage() {
//        try {
//            rightMoveMethod(10);
//            uiDevice.pressDPadCenter();
//            systemWait(WAIT);
//            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_background"));
//            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj !=null,m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //开通金卡会员选项跳转
//    public void LC_VIP_05_PersonalSkipVipPage() {
//        try {
//            upMoveMethod(1);
//            UiObject vipText = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/vip"));
//            if(vipText.exists()){
//                uiDevice.pressDPadCenter();
//                systemWait(WAIT);
//                UiObject2 tabView = uiDevice.findObject(text("金卡会员30天"));
//                m_Expect = "金卡会员30天";
//                m_Actual = tabView.getText();
//                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//                Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
//            }else {
//                Assert.assertTrue(false);
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //兑换页面
//    public void LC_VIP_06_ExchangeButtonVipPage() {
//        try {
//            uiDevice.pressDPadUp();
//            SystemClock.sleep(3000);
//            rightMoveMethod(2);
//            UiObject exchange = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/exchange"));
//            if(exchange.exists()) {
//                uiDevice.pressDPadCenter();
//                systemWait(WAIT);
//                UiObject2 tabView = uiDevice.findObject(text("兑换"));
//                m_Expect = "兑换";
//                m_Actual = tabView.getText();
//                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//                Utils.writeCaseResult("进入兑换页面时失败：未抓到页面关键字段", m_Pass, m_Time);
//            }else {
//                Assert.assertTrue(false);
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //兑换测试--输入错误的兑换券
//    public void LC_VIP_07_InputErrorCharacterOnExchangePage() {
//        try {
//            uiDevice.pressDPadUp();
//            SystemClock.sleep(3000);
//            rightMoveMethod(2);
//            UiObject exchange = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/exchange"));
//            if(exchange.exists()) {
//                uiDevice.pressDPadCenter();
//                systemWait(WAIT);
//                UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
//                Assert.assertEquals("成功进入兑换页面", "兑换", tabView1.getText());
//                UiObject2 editText = uiDevice.findObject(text("请输入兑换券编码"));
//                Configurator configurator = Configurator.getInstance();
//                configurator.setKeyInjectionDelay(1000);
//                editText.setText("TestData");
//                SystemClock.sleep(1000);
//                uiDevice.pressBack();
//                SystemClock.sleep(1000);
//                downMoveMethod(1);
//                uiDevice.pressDPadCenter();
//                systemWait(WAIT);
//                UiObject2 textView = uiDevice.findObject(text("兑换码输入错误，请重试！"));
//                m_Expect = "兑换码输入错误，请重试！";
//                m_Actual = textView.getText();
//                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//                Utils.writeCaseResult("兑换系统出现问题：错误兑换码兑换成功", m_Pass, m_Time);
//            }else {
//                Assert.assertTrue(false);
//            }
//        }catch(Throwable e){
//            e.printStackTrace();
//            resultStr = e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //个人中心页面
//    public void LC_VIP_08_ButtonSkipPersonalPage() {
//        try {
//            enterPersonalCenterPage();
//        }catch (Exception e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    @Test //会员开通or会员续费页面
//    public void LC_VIP_09_VipDisplayCardInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            uiDevice.pressDPadCenter();
//            systemWait(WAIT);
//            UiObject2 cardNum = uiDevice.findObject(By.res("com.bestv.ott:id/scollview"));
//            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
//            Log.d(TAG, "lxm: "+cardCount.size());  //==5
//            if(cardCount.size() >= 4){
//                UiObject2 tabView = uiDevice.findObject(text("金卡会员30天"));
//                m_Expect = "金卡会员30天";
//                m_Actual = tabView.getText();
//                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//                Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
//            }else {
//                Assert.assertTrue(false);
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //支付代扣页面
//    public void LC_VIP_10_PaymentWithHoldingInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            UiObject2 payCard = uiDevice.findObject(By.text("支付代扣"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,payCard);
//            UiObject2 tabView = uiDevice.findObject(text("取消代扣"));
//            m_Expect = "取消代扣";
//            m_Actual = tabView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult("进入取消代扣页面时跳转失败：未抓到关键字", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    @Test //福利领取
//    public void LC_VIP_11_WelfareCollectionInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            UiObject2 cardNum = uiDevice.findObject(By.res("com.bestv.ott:id/grid"));
//            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
//            Log.d(TAG, "lxm: "+cardCount.size());
//            if(cardCount.size() == 7){
//                UiObject2 WelCard = uiDevice.findObject(By.text("福利领取"));
//                m_Expect = "福利领取";
//                m_Actual = WelCard.getText();
//                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//                Utils.writeCaseResult("个人中心未抓到关键字", m_Pass, m_Time);
//            }else {
//                Assert.assertTrue(false);
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //卡券兑换
//    public void LC_VIP_12_CardExchangeInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            UiObject2 cardEx = uiDevice.findObject(By.text("卡券兑换"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,cardEx);
//            UiObject2 tabView = uiDevice.findObject(text("兑换"));
//            m_Expect = "兑换";
//            m_Actual = tabView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult("进入卡券兑换页面时跳转失败：未抓到关键字", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    @Test //消费记录
//    public void LC_VIP_13_PayRecordInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            UiObject2 record = uiDevice.findObject(By.text("消费记录"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,record);
//            UiObject2 tabView = uiDevice.findObject(By.textContains("客服电话"));
//            Log.d(TAG, "lxm: "+tabView.getText());
//            m_Expect = "客服电话：400 600 6258";
//            m_Actual = tabView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult(tabView.getText()+" 显示错误", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    @Test //观影券
//    public void LC_VIP_14_ViewingCouponInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            UiObject2 coupon = uiDevice.findObject(By.text("观影券"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,coupon);
//            UiObject2 tabView = uiDevice.findObject(By.textContains("客服电话"));
//            m_Expect = "客服电话：400 600 6258";
//            m_Actual = tabView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult(tabView.getText()+" 显示错误", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null ){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    @Test //会员状态页
//    public void LC_VIP_15_MemberStatusInPersonalPage(){
//        try {
//            enterPersonalCenterPage();
//            uiDevice.pressDPadLeft();
//            systemWait(SHORT_WAIT);
//            uiDevice.pressDPadCenter();
//            systemWait(WAIT);
//            UiObject2 tabView = uiDevice.findObject(text("会员头像设置"));
//            m_Expect = "会员头像设置";
//            m_Actual = tabView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult("进入会员头像设置页面时跳转失败：未抓到关键字", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if (resultStr != null) {
//                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
//            }
//        }
//    }
//
//    @Test //即将上映--影片收藏
//    public void LC_VIP_16_ComingMoviesCollectAndPlay() {
//        try {
//            rightMoveMethod(3);
//            systemWait(WAIT);
//            UiObject2 tabView = this.getTabFromLauncherHomeByText(uiDevice, "即将上映");
//            this.openTabFromLauncherHomeByTextView(uiDevice, tabView); //进入即将上映页面
//            UiObject2 cardNum = uiDevice.findObject(By.clazz("android.support.v7.widget.RecyclerView"));
//            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
//            SystemClock.sleep(3000);
//            Log.d(TAG, "lxm: "+cardCount.size());
//            for(int i= 0; i<=cardCount.size(); i++){
//                rightMoveMethod(1);
//                UiObject2 resId = uiDevice.findObject(By.res("com.bestv.ott:id/fav_state"));//通过Res_id来获取Text值，判断当前button选项
//                if (resId != null && resId.getText().equals("加入收藏")) {
//                    downMoveMethod(1);
//                    uiDevice.pressDPadCenter();   //点击确定加入收藏
//                    systemWait(SHORT_WAIT);
//                    upMoveMethod(1);
//                    uiDevice.pressDPadCenter();
//                    uiDevice.waitForIdle(18000);
//                    systemWait(LONG_WAIT);
//                    UiObject2 Text = uiDevice.findObject(text("已收藏"));  //加入断言判定详情页是否变为已收藏
//                    m_Expect = "已收藏";
//                    m_Actual = Text.getText();
//                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
//                    Utils.writeCaseResult("详情页收藏显示错误：收藏后应变为已收藏", m_Pass, m_Time);
//                }if(i > 8){
//                    break;
//                }
//                System.out.print("没有更多的上映新片");
//            }
//        }catch(Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //播放金卡电影
//    public void LC_VIP_17_PlayVipVideo() {
//        try {
//            rightMoveMethod(2);
//            uiDevice.pressDPadCenter();
//            uiDevice.wait(Until.findObject(By.text("最新院线")),10000);
//            SystemClock.sleep(8000);
//            randomPlayFilm(18);
//            systemWait(WAIT);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 vipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 watchMin = uiDevice.findObject(text("试看"));
//            if(vipType.getText().equals("播放") || vipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,watchMin);
//                systemWait(PlayVideoLongTime);
//                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
//                m_Actual = payMedia.getText();
//                m_Expect = "请选择要购买的媒体或服务";
//                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
//                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally{
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //播放金卡电视剧
//    public void LC_VIP_18_PlayVipTVVideo(){
//        try {
//            uiDevice.waitForIdle();
//            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-电视剧").res("com.bestv.ott:id/title"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
//            this.randomPlayFilm(9);
//            systemWait(WAIT);
//            uiDevice.pressDPadDown();
//            SystemClock.sleep(1500);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 VipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 WatchMin = uiDevice.findObject(text("试看"));
//            if(VipType.getText().equals("播放") || VipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,WatchMin);
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally{
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //4K专区
//    public void LC_VIP_19_Play4KVideo() {
//        try {
//            rightMoveMethod(2);
//            downMoveMethod(2);
//            uiDevice.pressDPadCenter();
//            uiDevice.wait(Until.findObject(By.text("4K")),18000);
//            SystemClock.sleep(10000);
//            randomPlayFilm(19);
//            systemWait(WAIT);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 vipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 watchMin = uiDevice.findObject(text("试看"));
//            if(vipType.getText().equals("播放") || vipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("4K视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,watchMin);
//                systemWait(PlayVideoLongTime);
//                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
//                m_Actual = payMedia.getText();
//                m_Expect = "请选择要购买的媒体或服务";
//                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
//                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr += e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //金卡纪实
//    public void LC_VIP_20_PlayDocumentaryVideo() {
//        try {
//            UiObject2 vipVideo = uiDevice.findObject(By.text("金卡-纪实").res("com.bestv.ott:id/title"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,vipVideo);
//            randomPlayFilm(20);
//            systemWait(WAIT);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 textView = uiDevice.findObject(By.text("相关推荐"));
//            m_Expect = "相关推荐";
//            m_Actual = textView.getText();
//            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
//            Utils.writeCaseResult("进入详情页失败", m_Pass, m_Time);
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //金卡少儿
//    public void LC_VIP_21_PlayKidVideo() {
//        try {
//            uiDevice.waitForIdle();
//            rightMoveMethod(2);
//            downMoveMethod(1);
//            uiDevice.pressDPadCenter();
//            uiDevice.wait(Until.findObject(By.text("动画电影")),18000);
//            SystemClock.sleep(8000);
//            randomPlayFilm(27);
//            systemWait(WAIT);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 vipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 watchMin = uiDevice.findObject(text("试看"));
//            if(vipType.getText().equals("播放") || vipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,watchMin);
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡少儿播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr = e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //金卡动漫
//    public void LC_VIP_22_PlayComicVideo() {
//        try {
//            uiDevice.waitForIdle();
//            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-动漫").res("com.bestv.ott:id/title"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
//            this.randomPlayFilm(3);
//            systemWait(WAIT);
//            uiDevice.pressDPadDown();
//            systemWait(SHORT_WAIT);
//            uiDevice.pressDPadCenter();
//            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
//            systemWait(LONG_WAIT);
//            UiObject2 vipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 watchMin = uiDevice.findObject(text("试看"));
//            if(vipType.getText().equals("播放") || vipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡动漫视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,watchMin);
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡动漫视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultStr = e.toString();
//            resultFlag = false;
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //视频分类页入口进入金卡列表页
//    public void LC_VIP_23_VideoClassifySkipVip() {
//        try {
//            backToLauncherHome(uiDevice);
//            this.enterVideoClassifyPage();
//            downMoveMethod(2);
//            uiDevice.pressDPadCenter();
//            uiDevice.wait(Until.findObject(By.text("金卡电影")),18000);
//            systemWait(LONG_WAIT);
//            uiDevice.pressDPadDown();
//            SystemClock.sleep(1500);
//            uiDevice.pressDPadCenter();
//            systemWait(LONG_WAIT);
//            uiDevice.waitForIdle(20000);
//            UiObject2 vipType = uiDevice.findObject(By.res("com.bestv.ott:id/discripse"));
//            UiObject2 watchMin = uiDevice.findObject(text("试看"));
//            if(vipType.getText().equals("播放") || vipType.getText().equals("继续播放")){
//                uiDevice.pressDPadLeft(); //防止详情页活动
//                uiDevice.pressDPadCenter();
//                systemWait(PlayVideoTime);
//                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
//                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
//                backPageMethod();
//            }else {
//                this.openTabFromLauncherHomeByVipText(uiDevice,watchMin);
//                systemWait(PlayVideoLongTime);
//                UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
//                m_Actual = PayMedia.getText();
//                m_Expect = "请选择要购买的媒体或服务";
//                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
//                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
//                backPageMethod();
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//        }
//    }
//
//    @Test //会员包月支付流程
//    public void LC_VIP_24_PersonalPagePayVip() {
//        try {
//            upMoveMethod(1);
//            uiDevice.pressDPadCenter();
//            systemWait(LONG_WAIT);
//            UiObject2 textView = uiDevice.findObject(text("金卡会员30天").res("com.bestv.ott:id/commodity_type"));
//            this.openTabFromLauncherHomeByTextView(uiDevice,textView);
//            uiDevice.waitForIdle(6000);
//            systemWait(WAIT);
//            UiObject zhiFuBao = uiDevice.findObject(new UiSelector().className("android.widget.ImageView")
//                    .resourceId("com.bestv.mediapay:id/pay_image"));
//            if(zhiFuBao.exists()){
//                uiDevice.pressBack();
//                systemWait(SHORT_WAIT);
//                uiDevice.pressDPadCenter();
//            }else{
//                Assert.assertTrue(false);
//            }
//        }catch (Throwable e){
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr += e.toString();
//        }
//        finally {
//            if(resultStr != null){
//                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
//            }
//        }
//    }
//
//    private UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
//        List<UiObject2> tabTitles = device.findObjects(By.res("com.bestv.ott:id/title"));
//        Assert.assertTrue("Verify tabs on launcher home.", tabTitles.size() > 0);
//        UiObject2 retTitle = null;
//        for (UiObject2 title : tabTitles) {
//            if (tabText.equals(title.getText())) {
//                retTitle = title;
//            }
//        }
//        Assert.assertNotNull(retTitle);
//        return retTitle;
//    }
//
//    private void openTabFromLauncherHomeByTextView(UiDevice device, UiObject2 TabView) {
//        TabView.click();
//        systemWait(SHORT_WAIT);
//        device.pressEnter();
//        device.waitForIdle();
//        systemWait(WAIT);
//    }
//
//    private void openTabFromLauncherHomeByVipText(UiDevice device, UiObject2 TabView) {
//        TabView.click();
//        device.waitForIdle();
//        systemWait(WAIT);
//    }
//
//    private void enterVideoClassifyPage(){
//        uiDevice.pressDPadRight();
//        SystemClock.sleep(1000);
//        downMoveMethod(2);
//        uiDevice.pressEnter();
//        systemWait(WAIT);
//        UiObject2 Classify = uiDevice.findObject(By.text("最新"));
//        Assert.assertEquals("最新",Classify.getText());
//        systemWait(WAIT);
//    } //进入视频分类页面
//
//    private void enterPersonalCenterPage(){
//        uiDevice.pressDPadUp();
//        systemWait(WAIT);
//        rightMoveMethod(1);
//        UiObject account = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/account"));
//        if(account.exists()){
//            uiDevice.pressDPadCenter();
//            systemWait(WAIT);
//            UiObject2 PerPage = uiDevice.findObject(By.text("个人中心"));
//            Assert.assertEquals("个人中心",PerPage.getText());
//        }else {
//            Assert.assertTrue(false);
//        }
//    } //进入个人中心页面
//
//    private void randomPlayFilm(int RandomMove){
//        uiDevice.pressDPadDown();
//        systemWait(WAIT);
//        Random moveTimes = new Random();
//        int i;
//        i=moveTimes.nextInt(RandomMove);
//        for(int j= 0;j<=i;j++){
//            SystemClock.sleep(1000);
//            uiDevice.pressDPadRight();
//            SystemClock.sleep(1000);
//            if(i > 18){
//                break;
//            }
//        }
//    } //随机播放电影
//
//    private void backToLauncherHome(UiDevice device) {
//        device.pressHome();
//        device.waitForIdle();
//        systemWait(WAIT);
//    }
//
//    public static void sleep(long ms) {
//        long start = uptimeMillis();
//        long duration = ms;
//        boolean interrupted = false;
//        do {
//            try {
//                Thread.sleep(duration);
//            }
//            catch (InterruptedException e) {
//                interrupted = true;
//            }
//            duration = start + ms - uptimeMillis();
//        } while (duration > 0);
//
//        if (interrupted) {
//            // Important: we don't want to quietly eat an interrupt() event,
//            // so we make sure to re-interrupt the thread so that the next
//            // call to Thread.sleep() or Object.wait() will be interrupted.
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private void systemWait(int seconds) {
//
//        SystemClock.sleep(seconds * 1000);
//    } //等待时间设定
//
//    private void rightMoveMethod(int RightMove){
//        int i = 1;
//        while (i <= RightMove){
//            i++;
//            uiDevice.pressDPadRight();
//            SystemClock.sleep(1000);
//        }
//    } //Right*
//
//    private void downMoveMethod(int DownMove){
//        int i = 1;
//        while (i <= DownMove){
//            i++;
//            uiDevice.pressDPadDown();
//            SystemClock.sleep(1000);
//        }
//    } //Down*
//
//    private void upMoveMethod(int UpMove){
//        int i = 1;
//        while (i <= UpMove){
//            i++;
//            uiDevice.pressDPadUp();
//            SystemClock.sleep(1000);
//        }
//    } //Up*
//
//    private void backPageMethod(){
//        uiDevice.pressBack();
//        SystemClock.sleep(1000);
//        uiDevice.pressBack();
//    } //Back*
//