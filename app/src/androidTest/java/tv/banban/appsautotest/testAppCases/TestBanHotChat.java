package tv.banban.appsautotest.testAppCases;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.banban.appsautotest.common.CommonMethod;
import tv.banban.common.Utils;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBanHotChat extends CommonMethod{

    @BeforeClass
    public static void BeforeClass(){
        // 清理当前路径下图片
        clearFiles(filePath); //storage/emulated/0/Android/data/tv.fun.appsautotest/cache
    }

    // 模拟器
    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getContext();
        Log.i(TAG, "setUp: "+getAppVersion(context, "com.ee.xianshi.android")); //1.0.1.4
        Log.i(TAG, "setUp: "+getAppName(context, "com.ee.xianshi.android")); //伴伴
    }

    @After
    public void cleanUp() {
        pressBack(1); //返回我的页面
        uiDevice.waitForIdle();
    }

    @Test
    public void test_01_HotChatPage(){
        try {
            startApp("com.ee.xianshi.android", "com.imbb.banban.android.MainActivity", 8);
            if("com.ee.xianshi.android".equals(uiDevice.getCurrentPackageName())){
                clickByTextAndClazz("热聊","android.widget.TextView");
                ScreenShot("_test_11_.png");
                UiObject2 page = uiDevice.findObject(By.text("活跃").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "活跃";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转页面错误", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test
    public void test_02_AddButtonClick(){
        try {
            clickByCoordinate(666,112,1);
            UiObject2 add = uiDevice.findObject(By.text("购买爵位或者通过官方考核才能创建聊天室"));
            m_Actual = add.getText();
            m_Expect = "购买爵位或者通过官方考核才能创建聊天室";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转页面错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test
    public void test_03_RankListPage(){
        try {
            clickByCoordinate(160,230,1);



        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += toString();
        }finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }

    }


}