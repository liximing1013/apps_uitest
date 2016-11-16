package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.appstoretest.common.AppStorePage;
import tv.fun.appstoretest.common.MasterApp;
import tv.fun.common.Utils;

/**
 * Created by liuqing on 2016/9/7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVMaster extends MasterApp {
    public String[] masterCards = {"一键优化", "内存加速", "垃圾清理", "网络测速", "应用清理", "自启动管理", "网络诊断"};

    /**
     * 验证可以通过点击Launcher应用页面“电视助手”卡片，进入电视助手页面
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category({LevelP2Tests.class, LevelP1Tests.class})
    @Test
    public void Master_Home_01_testEnterTVMasterFromLauncher() throws IOException {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //Assert
            UiObject tvMasterPageTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title"));
            String tvMasterPageTitle = tvMasterPageTitleObj.getText();
            UiObject settingBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/iv_settings"));
            verifyString("电视助手页面中标题显示不正确", tvMasterPageTitle, tvMasterIconName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 验证可以通过点击Launcher应用页面“电视助手”卡片，进入电视助手页面, 电视助手UI显示正确
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void Master_Home_01_testTVMasterUIDisplay() throws UiObjectNotFoundException {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //Assert
            UiObject tvMasterPageTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title"));
            String tvMasterPageTitle = tvMasterPageTitleObj.getText();
            UiObject settingBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/iv_settings"));
            verifyElementPresent("电视助手页面中设置按钮没有显示", settingBtn);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that it can be back to Launcher home page by clicking Home btn on TV Master page
     */
    @Test
    public void Master_Home_04_testClickHomeBtnToLauncherHomePage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject speedCard = findElementByText(masterCards[1], "tv.fun.master:id/home_item_title");
            verifyElementPresent("", speedCard);
            //按遥控器Back键，并验证回到Launcher App page
            device.pressHome();
            waitForElementNotPresentByID("tv.fun.master:id/home_item_title");
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherTabs[1]));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that it can be back from tv master page to Launcher app home page
     */
    @Test
    public void Master_Home_05_testBackFromMasterToLauncherApp() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject speedCard = findElementByText(masterCards[1], "tv.fun.master:id/home_item_title");
            verifyElementPresent("", speedCard);
            //按遥控器Back键，并验证回到Launcher App page
            device.pressBack();
            waitForElementNotPresentByID("tv.fun.master:id/home_item_title");
            UiObject masterObj = findElementByText("电视助手", "com.bestv.ott:id/title");
            UiObject appTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherTabs[4]));
            verifyElementPresent("", masterObj);
            verifyTrue("", appTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the UI of APP Clean page displays correctly
     */
    @Test
    public void Master_Clean_01_testAppCleanUI() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject cleanCard = findElementByText(masterCards[4], "tv.fun.master:id/home_item_title");
            //点击“应用清理”卡片
            cleanCard.clickAndWaitForNewWindow();
            UiObject titleObj = findElementByID("tv.fun.master:id/title");
            verifyElementPresent("", titleObj);
            verifyString("", titleObj.getText(), masterCards[4]);
            if (findElementByID("tv.fun.master:id/appNameView").exists()) {
                UiObject appNumObj = findElementByID("tv.fun.master:id/appNumber");
                UiObject spaceObj = findElementByID("tv.fun.master:id/spaceUsage");
                verifyIncludeString("", appNumObj.getText(), "应用数量");
                verifyIncludeString("", spaceObj.getText(), "占用空间");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

            @Test
    public void test(){
        TvCommon.printAllMethods(this.getClass().getName());
    }
}
