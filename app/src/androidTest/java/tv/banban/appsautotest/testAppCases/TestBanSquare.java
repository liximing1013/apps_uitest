package tv.banban.appsautotest.testAppCases;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
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
            stopApp(); //重启进程操作
            startApp(x86_PACKNAME, x86_CLASSNAME); //启动应用
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                rec.clickAndWait(Until.newWindow(),3000);
                systemWait(2);
                ScreenShot("test_square_01.png");
                UiObject2 page = uiDevice.findObject(By.text("热门").clazz("android.widget.TextView"));
                m_Actual = page.getText();
                m_Expect = "热门";
                Utils.writeCaseResult("跳转页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
            }else {
                ScreenShot("test_square_01_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
            clickByTextAndClazz("交友","android.widget.TextView");
        }
    }

    @Test //交友Tab-接待大厅跳转
    public void test_square_02_ReceptionHallJump(){
        try{
            scrollToView("接待大厅");
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
            clickByTextAndClazz("广场","android.widget.TextView");
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
            clickByTextAndClazz("广场","android.widget.TextView");
            clickByTextAndClazz("交友","android.widget.TextView");
        }
    }

    @Test //用户技能页-分享
    public void test_square_04_SkillPageToShare(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_04.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){
                    clickByCoordinate(630,77,2);
                    UiObject2 page = uiDevice.findObject(By.text("选择分享方式"));
                    m_Actual = page.getText();
                    m_Expect = "选择分享方式";
                    Utils.writeCaseResult("弹出分享框错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                    pressBack(1);
                }
            }else {
                ScreenShot("test_square_04_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-操作选项
    public void test_square_05_SkillPageToOperate(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_05.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){ //技能关闭
                    clickByCoordinate(685,81,2);
                    if(uiDevice.hasObject(By.text("操作选项"))){
                        UiObject2 report = uiDevice.findObject(By.text("举报"));
                        if(report != null){
                            clickByTextAndClazz("举报","android.widget.TextView");
                            UiObject2 page = uiDevice.findObject(By.text("上传证据截图"));
                            m_Actual = page.getText();
                            m_Expect = "上传证据截图";
                            Utils.writeCaseResult("弹出举报页错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                        }
                        pressBack(1);
                    }else{
                        ScreenShot("test_square_05_error_1.png");
                        Assert.fail("操作选项不能为空");
                    }
                }else {
                    ScreenShot("test_square_05_error_2.png");
                    Assert.fail("为你推荐不推荐关闭订单大神");
                }
            }else {
                ScreenShot("test_square_05_error_3.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-跳转个人页
    public void test_square_06_SkillPageJumpPersonalPage(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_06.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){
                    scrollToView("邀约方式"); //针对不同分辨率
                    if(uiDevice.hasObject(By.textContains("当前在线"))||uiDevice.hasObject
                            (By.textContains("技能"))||uiDevice.hasObject(By.textContains("邀约"))){
                        UiObject2 skill = uiDevice.findObject(By.textContains("技能"));
                        skill.clickAndWait(Until.newWindow(),3000);
                        systemWait(2); //针对不同分辨率
                        scrollToView("个人资料");
                        UiObject2 page = uiDevice.findObject(By.text("个人资料"));
                        m_Actual = page.getText();
                        m_Expect = "个人资料";
                        Utils.writeCaseResult("进入个人页错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                        pressBack(1);
                    }
                }
            }else {
                ScreenShot("test_square_06_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-跳转个人页-语音约战
    public void test_square_07_SkillPageJumpPersonalPageGame(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_07.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){
                    scrollToView("邀约方式"); //针对不同分辨率
                    if(uiDevice.hasObject(By.textContains("当前在线"))||uiDevice.hasObject
                            (By.textContains("技能"))||uiDevice.hasObject(By.textContains("邀约"))){
                        UiObject2 skill = uiDevice.findObject(By.textContains("技能"));
                        skill.clickAndWait(Until.newWindow(),3000);
                        systemWait(2);
                        scrollToView("语音约战");
                        UiObject2 page = uiDevice.findObject(By.textContains("语音约战"));
                        m_Actual = page.getText();
                        m_Expect = "语音约战";
                        Utils.writeCaseResult("语音约战模块", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                        pressBack(1);
                    }
                }
            }else {
                ScreenShot("test_square_07_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-跳转个人页-超能力
    public void test_square_08_SkillPageJumpPersonalPageSuperpowers(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_08.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){
                    scrollToView("邀约方式"); //针对不同分辨率
                    if(uiDevice.hasObject(By.textContains("当前在线"))||uiDevice.hasObject
                            (By.textContains("技能"))||uiDevice.hasObject(By.textContains("邀约"))){
                        UiObject2 skill = uiDevice.findObject(By.textContains("技能"));
                        skill.clickAndWait(Until.newWindow(),3000);
                        systemWait(2);
                        scrollToView("人气等级");
                        UiObject2 page = uiDevice.findObject(By.textContains("超能力"));
                        m_Actual = page.getText();
                        m_Expect = "超能力";
                        Utils.writeCaseResult("超能力模块", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                        pressBack(1);
                    }
                }
            }else {
                ScreenShot("test_square_08_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-跳转聊天页
    public void test_square_09_SkillPageJumpChatPage(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_09.png");
                UiObject2 chat = uiDevice.findObject(By.text("聊天"));
                if(chat != null){
                    chat.clickAndWait(Until.newWindow(),3000);
                    systemWait(2);
                    UiObject2 edit = uiDevice.findObject(By.text("请输入内容...").clazz("android.widget.EditText"));
                    m_Actual = edit.getText();
                    m_Expect = "请输入内容...";
                    Utils.writeCaseResult("进入聊天页错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                }
                pressBack(1);
            }else {
                ScreenShot("test_square_09_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //用户技能页-邀约页面
    public void test_square_10_SkillPageJumpInvitePage(){
        try{
            UiObject2 rec = uiDevice.findObject(By.text("为你推荐"));
            if(rec != null){
                clickByCoordinate(200,750,2);
                ScreenShot("test_square_10.png");
                UiObject2 invitation = uiDevice.findObject(By.text("邀约"));
                if(invitation != null){
                    invitation.clickAndWait(Until.newWindow(),3000);
                    systemWait(2);
                    UiObject2 invite = uiDevice.findObject(By.text("立即邀约"));
                    m_Actual = invite.getText();
                    m_Expect = "立即邀约";
                    Utils.writeCaseResult("进入邀约页错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
                }
                pressBack(1);
            }else {
                ScreenShot("test_square_10_error.png");
                Assert.fail("为你推荐模块不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
        }
    }

    @Test //同城Tab
    public void test_square_11_WideCity(){
        try{
            clickByTextAndClazz("同城","android.widget.TextView");
            clickByCoordinate(150,280,2); //模拟器分辨率
            ScreenShot("test_square_11.png");
            UiObject2 page = uiDevice.findObject(By.text("所在城市").clazz("android.widget.TextView"));
            m_Actual = page.getText();
            m_Expect = "所在城市";
            Utils.writeCaseResult("跳转城市定位页面错误", m_Actual.equalsIgnoreCase(m_Expect), m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
            pressBack(1);
            clickByTextAndClazz("交友","android.widget.TextView");
        }
    }

}
