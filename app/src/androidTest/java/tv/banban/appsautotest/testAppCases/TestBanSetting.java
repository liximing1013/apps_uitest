package tv.banban.appsautotest.testAppCases;

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

import com.squareup.okhttp.internal.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.List;

import tv.banban.appsautotest.common.CommonMethod;
import tv.banban.common.Utils;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBanSetting extends CommonMethod {

    @BeforeClass
    public static void BeforeClass(){
        // 清理当前路径下图片
        clearFiles(filePath);
    }

    // 模拟器
    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getContext();
        Log.i(TAG, "setUp: "+getAppVersion(context, "com.ee.xianshi.android")); //1.0.1.4
        Log.i(TAG, "setUp: "+getAppName(context, "com.ee.xianshi.android")); //伴伴
    }

    @After
    public void cleanUp(){
        pressBack(1); //返回我的页面
        uiDevice.waitForIdle();
        UiObject2 me = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
        if(me != null){
            systemWait(1);
        }else {
            Assert.fail("未返回我的页面");
        }
    }

    @Test //个人资料页面
    public void test_01_MyPagePersonInformation(){
        try {
            //adb shell dumpsys window w |findstr \/ |findstr name=
            stopApp();
            startApp("com.ee.xianshi.android", "com.imbb.banban.android.MainActivity", 8);
            if("com.ee.xianshi.android".equals(uiDevice.getCurrentPackageName())){
                clickByTextAndClazz("我","android.widget.TextView");
                systemWait(1);
                clickByCoordinate(433,112,2);
                ScreenShot("_test_1_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
                UiObject2 page = uiDevice.findObject(By.text("个人资料").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "个人资料";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转个人资料页面错误", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //交友动态页面
    public void test_02_EditPersonInformation() {
        try {
            clickByTextAndClazz("交友动态","android.widget.TextView");
            ScreenShot("_test_2_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("标签"));
            m_Actual = page.getText();
            m_Expect = "标签";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转交友动态页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //活动礼包页面
    public void test_03_ActivityPackagePage(){
        try {
            clickByTextAndClazz("活动礼包","android.widget.TextView");
            ScreenShot("_test_3_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("活动"));
            m_Actual = page.getText();
            m_Expect = "活动";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转活动页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //钱包页面
    public void test_04_WalletAndRecharge(){
        try {
            clickByTextAndClazz("钱包","android.widget.TextView");
            ScreenShot("_test_4_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("立即充值").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "立即充值";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转活动页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //会员页面
    public void test_05_VipAndNobilityPage(){
        try {
            clickByTextAndClazz("会员","android.widget.TextView");
            ScreenShot("_test_5_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("贵族身份").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "贵族身份";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转会员页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //邀约页面
    public void test_06_InviteAndOrderPage(){
        try {
            clickByTextAndClazz("邀约","android.widget.TextView");
            ScreenShot("_test_6_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("接单记录").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "接单记录";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转邀约页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //资质页面
    public void test_07_AptitudePage(){
        try {
            clickByTextAndClazz("资质","android.widget.TextView");
            ScreenShot("_test_7_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("资质设置").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "资质设置";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转资质页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //账号设置页面
    public void test_08_AccountSettingsPage(){
        try {
            clickByTextAndClazz("账号设置","android.widget.TextView");
            ScreenShot("_test_8_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("手机号").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "手机号";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转账号设置页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //在线客服弹框
    public void test_09_ServicePage(){
        try {
            clickByTextAndClazz("在线客服","android.widget.TextView");
            ScreenShot("_test_9_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("客服(咨询)").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "客服(咨询)";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹出客服弹框错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //分享
    public void test_10_ShareToThirdApp(){
        try {
            clickByTextAndClazz("分享","android.widget.TextView");
            ScreenShot("_test_10_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("选择分享方式").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "选择分享方式";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("弹出分享弹框错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //帮助
    public void test_11_HelpDocs(){
        try {
            clickByTextAndClazz("帮助","android.widget.TextView");
            ScreenShot("_test_11_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("帮助分类").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "帮助分类";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转到帮助页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //关于我们
    public void test_12_AboutUs(){
        try {
            clickByTextAndClazz("关于我们","android.widget.TextView");
            ScreenShot("_test_12_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("伴伴").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "伴伴";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转关于我们页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //编辑个人资料页面
    public void test_13_EditPersonalDataPage(){
        try {
            clickByCoordinate(600,150,2);
            UiObject2 edit = uiDevice.findObject(By.text("编辑"));
            edit.clickAndWait(Until.newWindow(), 3000);
            ScreenShot("_test_13_.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
            UiObject2 page = uiDevice.findObject(By.text("编辑个人资料").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "编辑个人资料";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转个人资料页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            pressBack(1); //回到最上一级页面
        }
    }

    @Test //开启接单
    public void test_14_OpenOrderButton() {
        try {
            UiObject2 recoveryOrder = uiDevice.findObject(By.text("恢复接单"));
            if (recoveryOrder == null) {
                UiObject2 Switch = uiDevice.findObject(By.clazz("android.widget.Switch"));
                Switch.click();
                uiDevice.waitForIdle();
                systemWait(2);
                UiObject2 recovery = uiDevice.findObject(By.text("不自动恢复"));
                recovery.clickAndWait(Until.newWindow(), 3000);
                ScreenShot("_test_14_1.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
                UiObject2 page = uiDevice.findObject(By.text("选择多久后自动恢复").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "选择多久后自动恢复";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("错误", m_Pass, m_Time);
            } else {
                UiObject2 recovery = uiDevice.findObject(By.text("不自动恢复"));
                recovery.clickAndWait(Until.newWindow(), 3000);
                ScreenShot("_test_14_2.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
                UiObject2 page = uiDevice.findObject(By.text("选择多久后自动恢复").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "选择多久后自动恢复";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("错误", m_Pass, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //提现到余额
    public void test_15_PutForwardToBalance(){
        try {
            UiObject2 wallet = uiDevice.findObject(By.text("钱包").clazz("android.widget.TextView"));
            wallet.clickAndWait(Until.newWindow(),2000);
            systemWait(2);
            UiObject2 putForward = uiDevice.findObject(By.textContains("可提现余额"));
            if(putForward != null){
                clickByCoordinate(400,320,2);
                UiObject2 make = uiDevice.findObject(By.text("可消费余额"));
                make.clickAndWait(Until.newWindow(),2000);
                systemWait(2);
                ScreenShot("_test_15_1.png");
                UiObject2 page1 = uiDevice.findObject(By.text("提现到可消费余额").clazz("android.widget.TextView"));
                m_Actual = page1.getText();
                m_Expect = "提现到可消费余额";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转页面错误", m_Pass, m_Time);
                pressBack(1);
                clickByCoordinate(400,320,2);
                UiObject2 card = uiDevice.findObject(By.text("银行卡/支付宝"));
                card.clickAndWait(Until.newWindow(),2000);
                systemWait(2);
                ScreenShot("_test_15_2.png");
                UiObject2 page2 = uiDevice.findObject(By.text("提现到银行卡/支付宝").clazz("android.widget.TextView"));
                m_Actual = page2.getText();
                m_Expect = "提现到银行卡/支付宝";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转页面错误", m_Pass, m_Time);
            }else {
                output(" don't have money");
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            pressBack(1);
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    private void banBan(){
        UiObject2 gc = uiDevice.findObject(By.text("广场"));
        if(gc != null){
            UiObject2 me = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
            me.click();
            systemWait(1);
            UiObject2 gcc = uiDevice.findObject(By.text("交友动态"));
            gcc.clickAndWait(Until.newWindow(), 3000);
        }
    }

    private void stopBanBan() throws RemoteException,UiObjectNotFoundException {
        uiDevice.pressRecentApps();
        UiObject recentapp = new UiObject(new UiSelector().resourceId("com.android.systemui:id/dismiss_task"));
        do{
            recentapp.waitForExists(2000);
            if(recentapp.exists()){
                recentapp.swipeLeft(5);
            }
        }while(recentapp.exists());
    }

}
