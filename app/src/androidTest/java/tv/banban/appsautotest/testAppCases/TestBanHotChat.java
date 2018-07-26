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

    }

    // 模拟器
    @Before
    public void setUp(){
    }

    @After
    public void cleanUp() {
        pressBack(1); //返回我的页面
        uiDevice.waitForIdle();
    }

    @Test
    public void test_01_HotChatPage(){
        try {
            startApp(x86_PACKNAME,x86_CLASSNAME);
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

    @Test //聊天页面循环发送消息
    public void test(){
        UiObject2 edit = uiDevice.findObject(By.text("请输入内容...").clazz("android.widget.EditText"));
        edit.clear();
        for(int i = 0; i<=100; i++){
            edit.click();
            edit.setText("123");
            UiObject2 enter = uiDevice.findObject(By.text("发送"));
            enter.click(1000);
            systemWait(5);
            Log.i(TAG, ""+i);
        }
    }

}