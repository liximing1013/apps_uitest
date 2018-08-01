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
import android.support.test.uiautomator.UiScrollable;
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
        pressBack(1);
        waitForIdle(2);
        UiObject2 page = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
        if(page != null){
            systemWait(1);
        }else {
            Assert.fail("未返回我的页面");
        }
    }

    @Test //个人资料页面
    public void test_settings_01_MyPagePersonInformation(){
        try {
            stopApp();  //adb shell dumpsys window w |findstr \/ |findstr name=
            startApp(x86_PACKNAME, x86_CLASSNAME); //启动x86模拟器
            appIsRunning();
            if(x86_PACKNAME.equals(uiDevice.getCurrentPackageName())){
                clickByTextAndClazz("我","android.widget.TextView"); //1s
                clickByCoordinate(433,112,2); //2s
                ScreenShot("test_settings_01.png"); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
                UiObject2 page = uiDevice.findObject(By.text("个人资料").clazz("android.widget.TextView"));
                m_Actual = page.getText(); //实际结果
                m_Expect = "个人资料"; //预期结果
                Assert.assertEquals("跳转个人资料页面错误",m_Expect,m_Actual); //断言
                UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector gift = new UiSelector().text("收到的礼物");
                scroll.scrollIntoView(gift);
                UiObject2 giftTotal = uiDevice.findObject(By.textContains("520"));
                m_Actual = giftTotal.getText();
                m_Expect = "520";
                Utils.writeCaseResult("礼物墙模块错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            }
//            if(ANDROID_PACKNAME.equals(uiDevice.getCurrentPackageName())){
//                clickByTextAndClazz("我","android.widget.TextView");
//                clickByCoordinate(433,112,2);
//                ScreenShot("test_settings_01.png");
//                UiObject2 page = uiDevice.findObject(By.text("个人资料").clazz("android.widget.TextView"));
//                m_Actual = page.getText();
//                m_Expect = "个人资料";
//                Assert.assertEquals("跳转个人资料页面错误",m_Expect,m_Actual);
//                UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
//                UiSelector gift = new UiSelector().text("收到的礼物");
//                scroll.scrollIntoView(gift);
//                UiObject2 giftTotal = uiDevice.findObject(By.textContains("520"));
//                m_Actual = giftTotal.getText();
//                Log.i(TAG, "test_settings_01_MyPagePersonInformation: "+m_Actual);
//                m_Expect = "520";
//                Utils.writeCaseResult("礼物墙模块错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
//            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //交友动态页面
    public void test_settings_02_EditPersonInformation() {
        try {
            clickByTextAndClazz("交友动态","android.widget.TextView");
            ScreenShot("test_settings_02.png");
            UiObject2 label = uiDevice.findObject(By.text("标签"));
            m_Actual = label.getText();
            m_Expect = "标签";
            Utils.writeCaseResult("跳转交友动态页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //活动礼包页面
    public void test_settings_03_ActivityPackagePage(){
        try {
            clickByTextAndClazz("活动礼包","android.widget.TextView");
            ScreenShot("test_settings_03.png");
            UiObject2 activityPage = uiDevice.findObject(By.text("活动"));
            m_Actual = activityPage.getText();
            m_Expect = "活动";
            Utils.writeCaseResult("跳转活动页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //钱包页面
    public void test_settings_04_WalletAndRecharge(){
        try {
            clickByTextAndClazz("钱包","android.widget.TextView");
            ScreenShot("test_settings_04.png");
            UiObject2 page = uiDevice.findObject(By.text("立即充值").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "立即充值";
            Utils.writeCaseResult("跳转钱包页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //会员页面
    public void test_settings_05_VipAndNobilityPage(){
        try {
            clickByTextAndClazz("会员","android.widget.TextView");
            ScreenShot("test_settings_05.png");
            // 贵族身份
            UiObject2 page1 = uiDevice.findObject(By.text("贵族身份"));
            Assert.assertEquals("跳转会员页面错误","贵族身份",page1.getText());
            // 大厅Vip
            UiObject2 page2 = uiDevice.findObject(By.text("大厅VIP"));
            Assert.assertEquals("跳转会员页面错误","大厅VIP",page2.getText());
            // 我的礼包
            UiObject2 page3 = uiDevice.findObject(By.text("我的礼包"));
            Assert.assertEquals("跳转会员页面错误","我的礼包",page3.getText());
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //邀约页面
    public void test_settings_06_InviteAndOrderPage(){
        try {
            UiObject2 auth = uiDevice.findObject(By.text("资质认证"));
            if(auth == null){
                clickByTextAndClazz("邀约","android.widget.TextView");
                ScreenShot("test_settings_06.png");
                UiObject2 page = uiDevice.findObject(By.text("接单记录").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "接单记录";
                Utils.writeCaseResult("跳转邀约页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //资质页面
    public void test_settings_07_AptitudePage(){
        try {
            clickByTextAndClazz("资质","android.widget.TextView");
            ScreenShot("test_settings_07.png");
            UiObject2 skillPage = uiDevice.findObject(By.text("资质设置"));
            Assert.assertEquals("跳转资质页面错误","资质设置",skillPage.getText());
            UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            scroll.flingForward();
            systemWait(SHORT_WAIT);
            UiObject2 newSkill = uiDevice.findObject(By.text("新资质"));
            if(newSkill != null){
                clickByTextAndClazz("新资质","android.widget.TextView");
                ScreenShot("test_settings_07_1.png");
                UiObject2 newSkillPage = uiDevice.findObject(By.text("已有资质").clazz("android.widget.TextView"));
                m_Actual = newSkillPage.getText();
                m_Expect = "已有资质";
                Utils.writeCaseResult("跳转申请资质页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                UiObject2 LOl = uiDevice.findObject(By.text("LOL"));
                if(LOl != null){
                    clickByTextAndClazz("LOL","android.widget.TextView");
                    ScreenShot("test_settings_07_2.png");
                    UiObject2 LolSkillPage = uiDevice.findObject(By.text("LOL资质").clazz("android.widget.TextView"));
                    m_Actual = LolSkillPage.getText();
                    m_Expect = "LOL资质";
                    Utils.writeCaseResult("跳转申请资质页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                }
                pressBack(2);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //账号设置页面
    public void test_settings_08_AccountSettingsPage(){
        try {
            clickByTextAndClazz("账号设置","android.widget.TextView");
            ScreenShot("test_settings_08.png");
            UiObject2 accountPage = uiDevice.findObject(By.text("手机号").clazz("android.widget.TextView"));
            Assert.assertEquals("跳转账号设置页面错误","手机号",accountPage.getText());
            UiObject2 cashMoney = uiDevice.findObject(By.text("提现账号"));
            if(cashMoney != null){
                clickByTextAndClazz("提现账号","android.widget.TextView");
                ScreenShot("test_settings_08_1.png");
                UiObject2 cashPage = uiDevice.findObject(By.text("提现到银行卡/支付宝"));
                m_Actual = cashPage.getText();
                m_Expect = "提现到银行卡/支付宝";
                Utils.writeCaseResult("跳转提现页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                pressBack(1);
            }
            UiObject2 Hot = uiDevice.findObject(By.text("红人认证"));
            if(Hot != null){
                clickByTextAndClazz("红人认证","android.widget.TextView");
                ScreenShot("test_settings_08_2.png");
                UiObject2 cashPage = uiDevice.findObject(By.text("通过红人认证后您将享有以下特权"));
                m_Actual = cashPage.getText();
                m_Expect = "通过红人认证后您将享有以下特权";
                Utils.writeCaseResult("跳转红人认证页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                pressBack(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //在线客服弹框
    public void test_settings_09_ServicePage(){
        try {
            clickByTextAndClazz("在线客服","android.widget.TextView");
            ScreenShot("test_settings_09.png");
            UiObject2 page = uiDevice.findObject(By.text("客服(咨询)").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "客服(咨询)";
            Utils.writeCaseResult("弹出客服弹框错误",m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //分享
    public void test_settings_10_ShareToThirdApp() {
        try {
            clickByTextAndClazz("分享", "android.widget.TextView");
            ScreenShot("test_settings_10.png");
            UiObject2 page = uiDevice.findObject(By.text("选择分享方式").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "选择分享方式";
            Utils.writeCaseResult("弹出分享弹框错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        } catch (Exception e) {
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test //帮助
    public void test_settings_11_HelpDocs(){
        try {
            clickByTextAndClazz("帮助","android.widget.TextView");
            ScreenShot("test_settings_11.png");
            UiObject2 page = uiDevice.findObject(By.text("帮助分类").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "帮助分类";
            Utils.writeCaseResult("跳转到帮助页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //关于我们
    public void test_settings_12_AboutUs(){
        try {
            clickByTextAndClazz("关于我们","android.widget.TextView");
            ScreenShot("test_settings_12.png");
            UiObject2 page = uiDevice.findObject(By.text("伴伴").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "伴伴";
            Utils.writeCaseResult("跳转到关于我们页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //编辑个人资料页面
    public void test_settings_13_EditPersonalDataPage(){
        try {
            clickByCoordinate(600,150,2);
            UiObject2 edit = uiDevice.findObject(By.text("编辑"));
            edit.clickAndWait(Until.newWindow(), 3000);
            ScreenShot("test_settings_13.png");
            UiObject2 page = uiDevice.findObject(By.text("编辑个人资料").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "编辑个人资料";
            Utils.writeCaseResult("跳转个人资料页面错误",m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
            pressBack(1);
        }
    }

    @Test //开启接单
    public void test_settings_14_OpenOrderButton() {
        try {
            UiObject2 recoveryOrder = uiDevice.findObject(By.text("恢复接单"));
            if (recoveryOrder == null) {
                UiObject2 Switch = uiDevice.findObject(By.clazz("android.widget.Switch"));
                Switch.click();
                waitForIdle(2);
                UiObject2 recovery = uiDevice.findObject(By.text("不自动恢复"));
                recovery.clickAndWait(Until.newWindow(), 3000);
                ScreenShot("test_settings_14_1.png");
                UiObject2 page = uiDevice.findObject(By.text("选择多久后自动恢复").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "选择多久后自动恢复";
                Utils.writeCaseResult("错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            } else {
                UiObject2 recovery = uiDevice.findObject(By.text("不自动恢复"));
                recovery.clickAndWait(Until.newWindow(), 3000);
                ScreenShot("test_settings_14_2.png");
                UiObject2 page = uiDevice.findObject(By.text("选择多久后自动恢复").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "选择多久后自动恢复";
                Utils.writeCaseResult("错误",m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //提现到余额
    public void test_settings_15_PutForwardToBalance(){
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
                ScreenShot("test_settings_15_1.png");
                UiObject2 page1 = uiDevice.findObject(By.text("提现到可消费余额").clazz("android.widget.TextView"));
                m_Actual = page1.getText();
                m_Expect = "提现到可消费余额";
                Utils.writeCaseResult("跳转页面错误",m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                pressBack(1);
                clickByCoordinate(400,320,2);
                UiObject2 card = uiDevice.findObject(By.text("银行卡/支付宝"));
                card.clickAndWait(Until.newWindow(),2000);
                systemWait(2);
                ScreenShot("test_settings_15_2.png");
                UiObject2 page2 = uiDevice.findObject(By.text("提现到银行卡/支付宝").clazz("android.widget.TextView"));
                m_Actual = page2.getText();
                m_Expect = "提现到银行卡/支付宝";
                Utils.writeCaseResult("跳转页面错误",m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            }else {
                Assert.assertTrue("Don't Have Money",true);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
            pressBack(1);
        }
    }

}
