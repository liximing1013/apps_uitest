package tv.banban.appsautotest.testAppCases;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
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
public class TestBanSquare extends CommonMethod{

    // 模拟器
    @Before
    public void setUp(){

    }

    @After
    public void cleanUp(){

        waitForIdle(2);
    }

    @Test //交友Tab-为你推荐跳转
    public void test_square_01_RecommendedForYouJump(){
        try{
            stopApp();
            startApp(x86_PACKNAME, x86_CLASSNAME);
            Verification("为你推荐");
            clickByCoordinate(687,535,2);
            ScreenShot("test_square_01.png");
            UiObject2 page = uiDevice.findObject(By.text("热门").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "热门";
            Utils.writeCaseResult("跳转页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
            UiObject2 makeFriend = uiDevice.findObject(By.text("交友"));
            makeFriend.clickAndWait(Until.newWindow(), 3000);
        }
    }

    @Test //交友Tab-接待大厅跳转
    public void test_square_02_ReceptionHallJump(){
        try{
            Verification("接待大厅");
            clickByCoordinate(688,1058,2);
            ScreenShot("test_square_02.png");
            UiObject2 page = uiDevice.findObject(By.text("活跃").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "活跃";
            Utils.writeCaseResult("跳转页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test //游戏Tab-点单厅跳转
    public void test_square_03_OrderHallJump(){
        try{
            clickByTextAndClazz("游戏","android.widget.TextView");
            Verification("热门");
            clickByCoordinate(361,328,2);
            ScreenShot("test_square_03.png");
            UiObject2 page = uiDevice.findObject(By.text("活跃").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "活跃";
            Utils.writeCaseResult("跳转页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test // 搜索选项跳转
    public void test_square_04_SearchButtonJump(){
        try{
            clickByCoordinate(660,110,2);

        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }


}
