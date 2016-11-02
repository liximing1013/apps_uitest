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

import tv.fun.appstoretest.common.AppStorePage;
import tv.fun.appstoretest.common.MasterApp;
import tv.fun.common.Utils;

/**
 * Created by liuqing on 2016/9/7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVMaster extends MasterApp {

    /**
     * 验证可以通过点击Launcher应用页面“电视助手”卡片，进入电视助手页面
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category({LevelP2Tests.class, LevelP1Tests.class})
    @Test
    public void Master_Home_01_testEnterTVMasterFromLauncher() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //Assert
            UiObject tvMasterPageTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title"));
            String tvMasterPageTitle = tvMasterPageTitleObj.getText();
            UiObject settingBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/iv_settings"));
//            Assert.assertTrue("电视助手页面中标题显示不正确", tvMasterPageTitle.equalsIgnoreCase(tvMasterIconName));
            verifyString("电视助手页面中标题显示不正确", tvMasterPageTitle, tvMasterIconName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();//Means to error log
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, execTime);
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
//            Assert.assertTrue("电视助手页面中设置按钮没有显示", settingBtn.exists());
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

    //        @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}
