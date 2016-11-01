package tv.fun.appstoretest.TestCases;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.appstoretest.Common.AppStorePage;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/8/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAppStore extends AppStorePage {
    private static final String AppStore_PACKAGE = "tv.fun.appstore";
    public String autoProjectKeyWord = "test";
    public int maxCountOfOneLine = 6;
    public String[] textInSearchWindow = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    /**
     * Test Launcher App tab page UI displays correctly
     *
     * @throws UiObjectNotFoundException
     */
    @Category({LevelP1Tests.class})
    @Test
    public void App_Home_01_testLauncherAppUI() throws UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        //得到Launcher应用页面元素
        UiObject appStoreIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
        UiObject tvMasterIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        UiObject myAppIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/maintitle").text(myAppIconName));
        UiObject myAppSubTitle = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));//(22个)
        String appStoreStr = appStoreIcon.getText();

        //断言
        Assert.assertTrue("应用市场卡片没有显示在Launcher应用页面", appStoreIcon.exists());
        Assert.assertTrue("在Launcher应用页面应用市场卡片显示不正确", appStoreIcon.getText().equalsIgnoreCase(appStoreIconName));
        Assert.assertTrue("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon.exists());
        Assert.assertTrue("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon.exists());
        Assert.assertTrue("我的应用卡片没有显示在Launcher应用页面", myAppIcon.exists());
        Assert.assertTrue("我的应用卡片下方的应用数量没有显示在Launcher应用页面", myAppSubTitle.getText().contains(myAppCountUnit));
        Utils.writeCaseResult("Verify the UI of Launcher App page is not displayed correctly",
                myAppSubTitle.getText().contains(myAppCountUnit), execTime);
    }

    /**
     * Test that can enter App detail page from launcher app tab
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_Home_07_testEnterAppDetailFromLauncher() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        moveToDown();
        UiObject firstApp = findElementByIndex("id", "com.bestv.ott:id/poster", 0);
        firstApp.clickAndWaitForNewWindow();
        waitForAppDetailPageDisplay();
        //断言
        UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
        UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
        Assert.assertTrue("详情页中海报图没有显示", pic.exists());
        Assert.assertTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
        Utils.writeCaseResult("Verify the App detail page is not displayed correctly",
                controlledDevice.getText().contains("下载："), execTime);
    }

    /**
     * test install app under Launcher App tab
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_Home_07_testInstallAppFromLauncherAndBack() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        moveToDown();
        UiObject firstApp = findElementByIndex("id", "com.bestv.ott:id/poster", 0);
        int installedAppNum = stringToInt(findElementByID("com.bestv.ott:id/subtitle").getText().replace("个)", "").replace("(", ""));
        firstApp.clickAndWaitForNewWindow();
        waitForAppDetailPageDisplay();

        //如果第一个应用已安装，回到首页再选择另一应用安装
        if (findElementByClass("android.widget.Button").exists()) {
            if ("打开".equalsIgnoreCase(findElementByClass("android.widget.Button").getText())) {
                String appName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
                device.pressBack();
//                UiObject myAppLink = findElementByID("com.bestv.ott:id/maintitle");
//                myAppLink.clickAndWaitForNewWindow();
//                waitForElementPresentByIDAndText("tv.fun.appstore:id/title", "我的应用");
//                moveToDown();
//                device.pressMenu();
//                UiObject firstInstalledApp = findElementByID("tv.fun.appstore:id/listview").getChild(new UiSelector().className("tv.fun.appstore:id/appName").text(appName));
                moveToRight();
                device.pressEnter();
            }
        }
        //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
        device.pressDPadDown();
        device.pressDPadUp();
        //点击安装按钮，安装应用
        device.pressEnter();
        waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
        waitForElementPresentByClassAndText("android.widget.Button", "打开");
        UiObject openBtn = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").className("android.widget.Button"));

        //断言
        Assert.assertTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase("打开"));
        device.pressBack();
        UiObject appTab = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("应用"));
        Assert.assertTrue("Doesn't back to Launcher App page", appTab.exists());
        int currAppNum = stringToInt(findElementByID("com.bestv.ott:id/subtitle").getText().replace("个)", "").replace("(", ""));
        Assert.assertTrue("The install app num is not increased after install one app", currAppNum == installedAppNum + 1);
        Utils.writeCaseResult("Verify the app can be installed successfully",
                currAppNum == installedAppNum + 1, execTime);
    }

    /**
     * Test App Store UI Displays correctly
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Home_43_testAppStoreUIDisplay() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //断言
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[0]));
        UiObject yxTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[1]));
        UiObject ylTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[2]));
        UiObject shTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[3]));
        UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
        UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
        UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));

        Assert.assertTrue("The tuijian tab in AppStore page is not displayed", tjTab.exists());
        Assert.assertTrue("The game tab in AppStore page is not displayed", yxTab.exists());
        Assert.assertTrue("The yule tab in AppStore page is not displayed", ylTab.exists());
        Assert.assertTrue("The shenghuo tab in AppStore page is not displayed", shTab.exists());
        Assert.assertTrue("The jiaoyu tab in AppStore page is not displayed", jyTab.exists());
        Assert.assertTrue("The search icon in AppStore page is not displayed", searchObj.exists());
        Assert.assertTrue("The appManage tab in AppStore page is not displayed", appManageTab.exists());
        Utils.writeCaseResult("Verify the UI of appstore page displays correctly",
                appManageTab.exists(), execTime);
    }

    /**
     * test go to hot topic page under suggest tab
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_TJ_07_testGotoHotTopicPageAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToFirstCardUnderSuggestTab();
        UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
        if (!hotTopic.isSelected()) {
            moveToFirstCardUnderSuggestTab();
        }

        //点击推荐tab下热门排行topic
        device.pressEnter();
        hotTopic.clickAndWaitForNewWindow();
        UiObject hotTopicImage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_poster"));
        UiObject appListObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_app_list"));
        Thread.sleep(200);
        List<UiObject2> appList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
        int num = appList.size();
        //断言
        Assert.assertTrue("The image of hot topic page is not displayed", hotTopicImage.exists());
        Assert.assertTrue("The app list in hot topic page is not displayed", appListObj.exists());
        Assert.assertTrue("The count of app listed in hot topic page is not more than 1", num > 1);
        Utils.writeCaseResult("Verify the app list in hot topic page is displayed",
                appListObj.exists(), execTime);
    }

    /**
     * test enter app detail from app store page
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_TJ_64_testEnterAppDetailFromAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToFirstAppUnderSuggestTab();
//        UiObject firstSuggestCard = findElementByID("tv.fun.appstore:id/app_pager").getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
//        if(!firstSuggestCard.isFocused()){
//            moveToTopOnAppStoreHomePage();
//            moveToAppStoreTargetTab(appStoreTabs[0]);
//            moveToDown();
//        }
//        moveToRight();
        UiObject firstApp = findElementByID("tv.fun.appstore:id/app_pager").getChild(new UiSelector().className("android.widget.RelativeLayout").index(2));
        if (!firstApp.isSelected()) {
            moveToFirstAppUnderSuggestTab();
        }
        //点击应用卡片
        firstApp.clickAndWaitForNewWindow();
        waitForAppDetailPageDisplay();
        //Assert
        UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
        UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
        Assert.assertTrue("详情页中海报图没有显示", pic.exists());
        Assert.assertTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
    }

    /**
     * Test enter child list page of Game
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_53_testEnterGameChildListPageAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
        device.pressDPadDown();
        UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
        if (!firstGameChildEnter.isSelected()) {
            moveToUpForMultiple(6);
            device.pressDPadRight();
            device.pressDPadLeft();
            device.pressDPadDown();
        }
//        String firstEnterName = firstGameChildEnter.getChild(new UiSelector().className("tv.fun.appstore:id/title")).getText();
        //点击游戏tab下第一个子入口，进入列表页
        firstGameChildEnter.clickAndWaitForNewWindow();

        //断言
        UiObject listPageTitle = findElementByID("tv.fun.appstore:id/all_apps_title");
        UiObject appListView = findElementByID("tv.fun.appstore:id/all_apps_view");
        List<UiObject2> appsList = device.findObjects(By.clazz("android.widget.FrameLayout"));
        int numOfAppList = appsList.size();
        UiObject filterBar = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(2));
        int childCount = filterBar.getChildCount();
        String firstFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(0)).getText();
        String secFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(1)).getText();
        String thirdFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(2)).getText();
        //向下选中一应用
//        device.pressDPadDown();
//        String appInfoOne = findElementByID("tv.fun.appstore:id/all_apps_status_prefix").getText();
//        UiObject appInfoTwo = findElementByID("tv.fun.appstore:id/all_apps_status_divider_ratingbar");
//        String suffixAppInfo = findElementByID("tv.fun.appstore:id/all_apps_status_suffix").getText();
        Assert.assertTrue("Title of the first child enter is incorrect", listPageTitle.getText().equalsIgnoreCase("动作"));
        Assert.assertTrue("App List page is not displayed", appListView.exists());
        Assert.assertTrue("App displayed in List page is not more than 1", numOfAppList > 1);
        Assert.assertTrue("Filter bar is not displayed in list page", filterBar.exists());
        Assert.assertTrue("Filter option is displayed incorrectly in list page", firstFilter.equalsIgnoreCase("全部"));
        Assert.assertTrue("Filter option is displayed incorrectly in list page", secFilter.equalsIgnoreCase("遥控器"));
        Assert.assertTrue("Filter option is displayed incorrectly in list page", thirdFilter.equalsIgnoreCase("手柄"));
    }

    /**
     * Test Filter Child list of Game by Remote Control
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_54_testFilterGameChildListByRemoteControlAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
//        UiScrollable navBar = new UiScrollable(new UiSelector()
//                .resourceId("tv.fun.appstore:id/column_list"));
//        UiObject tjTab = navBar.getChildByText(new UiSelector()
//                .className("tv.fun.appstore:id/column_title"), appStoreTabs[1]);
        device.pressDPadDown();
//        UiObject firstGameChildEnter = findElementByID("tv.fun.appstore:id/list").getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
        if (!firstGameChildEnter.isSelected()) {
            moveToUpForMultiple(6);
            device.pressDPadRight();
            device.pressDPadLeft();
            device.pressDPadDown();
        }
//        String firstEnterName = firstGameChildEnter.getChild(new UiSelector().className("tv.fun.appstore:id/title")).getText();
        //点击游戏tab下第一个子入口，进入列表页
        firstGameChildEnter.clickAndWaitForNewWindow();
        String str = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText();
        String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int startAppNum = stringToInt(startApp);
        Assert.assertTrue("App list is not default to start with NO. 1", startAppNum == 1);
        moveToDownForMultiple(3);
        moveToRight();
//        UiScrollable scroll=new UiScrollable(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
//        scroll.setAsHorizontalList();
//        scroll.scrollForward(5);
//        device.pressDPadDown();
        String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        Assert.assertTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);
        //点击"遥控器"筛选按钮
        UiObject remoteControl = device.findObject(new UiSelector().text("遥控器").className("android.widget.TextView"));
        remoteControl.clickAndWaitForNewWindow();
        //筛选后,再次获取数据
        String totalAppAfterRC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startAppAfterRC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int totalAppNumAfterRC = stringToInt(totalAppAfterRC);
        int startAppNumAfterRC = stringToInt(startAppAfterRC);
        Assert.assertTrue("The start app is not changed to default after filtering", startAppNumAfterRC == 1);
        Assert.assertTrue("The total app num is less than default after filtering by RC", totalAppNumAfterRC <= stringToInt(totalApp));
//        //Check all apps in list have remote control operation after filter by RC
//        UiObject rcAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int rcAppNum = rcAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by RC", rcAppNum == totalAppNumAfterRC);
    }

    /**
     * Test filter child list of game by handle
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_54_testFilterGameChildListByHandleAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
//        UiScrollable navBar = new UiScrollable(new UiSelector()
//                .resourceId("tv.fun.appstore:id/column_list"));
//        UiObject tjTab = navBar.getChildByText(new UiSelector()
//                .className("tv.fun.appstore:id/column_title"), appStoreTabs[1]);
        device.pressDPadDown();
//        UiObject firstGameChildEnter = findElementByID("tv.fun.appstore:id/list").getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
        if (!firstGameChildEnter.isSelected()) {
            moveToUpForMultiple(6);
            device.pressDPadRight();
            device.pressDPadLeft();
            device.pressDPadDown();
        }
//        String firstEnterName = firstGameChildEnter.getChild(new UiSelector().className("tv.fun.appstore:id/title")).getText();
        //点击游戏tab下第一个子入口，进入列表页
        firstGameChildEnter.clickAndWaitForNewWindow();
        String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int startAppNum = stringToInt(startApp);
        Assert.assertTrue("App list is not default to start with NO. 1", startAppNum == 1);
        moveToDownForMultiple(3);
        moveToRight();
        String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        Assert.assertTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);
        //点击"手柄"筛选按钮
        UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
        handlel.clickAndWaitForNewWindow();
        //筛选后,再次获取数据
        String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
        int startAppNumAfterHandlel = stringToInt(startAppAfterHandlel);
        Assert.assertTrue("The start app is not changed to default after filtering", startAppNumAfterHandlel == 1);
        Assert.assertTrue("The total app num is less than default after filtering by Handlel", totalAppNumAfterHandlel <= stringToInt(totalApp));
//        //Check all apps in list have remote control operation after filter by RC
//        UiObject handlelAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int handlelAppNum = handlelAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by RC", handlelAppNum == totalAppNumAfterHandlel);
    }

    /**
     * Test the child App List of game can be filtered by All successfully
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_23_testFilterGameChildListByAllAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
        device.pressDPadDown();
//        UiObject firstGameChildEnter = findElementByID("tv.fun.appstore:id/list").getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
        if (!firstGameChildEnter.isSelected()) {
            moveToUpForMultiple(6);
            device.pressDPadRight();
            device.pressDPadLeft();
            device.pressDPadDown();
        }
//        String firstEnterName = firstGameChildEnter.getChild(new UiSelector().className("tv.fun.appstore:id/title")).getText();
        //点击游戏tab下第一个子入口，进入列表页
        firstGameChildEnter.clickAndWaitForNewWindow();
        String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int startAppNum = stringToInt(startApp);
        int totalAppNum = stringToInt(totalApp);
        Assert.assertTrue("App list is not default to start with NO. 1", startAppNum == 1);

        //点击"手柄"筛选按钮
        UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
        handlel.clickAndWaitForNewWindow();
        //筛选后,再次获取数据
        String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
        Assert.assertTrue("The total app num is not less than default after filtering by Handlel", totalAppNumAfterHandlel <= stringToInt(totalApp));
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
        if (scroll.exists()) {
            moveToDownForMultiple(3);
            moveToRight();
        }
        String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        Assert.assertTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);

        //点击"全部"筛选按钮
        UiObject allFilter = device.findObject(new UiSelector().text("全部").className("android.widget.TextView"));
        allFilter.clickAndWaitForNewWindow();
        //筛选后,再次获取数据
        String totalAppAfterAll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        String startAppAfterAll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
        int totalAppNumAfterAll = stringToInt(totalAppAfterAll);
        int startAppNumAfterAll = stringToInt(startAppAfterAll);
        Assert.assertTrue("The total app num is not more than default after filtering by All", totalAppNumAfterAll == totalAppNum);
        Assert.assertTrue("The start app is not default to 1 after filtering", startAppNumAfterAll == 1);
//        //Check all apps in list have all operation after filter by RC
//        UiObject allAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int allAppNumAfterFilter = allAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by All", allAppNumAfterFilter == totalAppNumAfterAll);
    }

    /**
     * Test that install app successfully
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Game_15_testInstallAppFromAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
        UiObject firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
        if (!firstApp.isSelected()) {
            moveToTopOnAppStoreHomePage();
            moveToDown();
            moveToRight();
        }
        firstApp.clickAndWaitForNewWindow();
        //Assert
        if (!device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").text("安装")).exists()) {
            device.pressBack();
            device.pressDPadRight();
            device.pressEnter();
        }
        //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
        installAppInDetailPage();
        UiObject2 openBtn = device.findObject(By.clazz("android.widget.Button"));
        Assert.assertTrue("详情页中应用未安装成功", openBtn.getText().equalsIgnoreCase("打开"));
    }

    /**
     * Test that can enter all Entertainment Page By Tab.
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_YL_17_testEnterAllEntertainmentPageByTab() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //Move to Entertainment Tab
        moveToAppStoreTargetTab(appStoreTabs[2]);
        UiObject ylTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[2]));
        device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
//        ylTab.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        waitForElementPresentByID("android:id/tv_fun_scrollbar");
        UiObject allylPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String allylPageTitleStr = allylPageTitle.getText();
        Assert.assertTrue("The title of all yl app page is expected" + "【全部娱乐】" + ", but actual 【" + allylPageTitleStr + "】", allylPageTitleStr.equalsIgnoreCase("全部娱乐"));
        UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        Assert.assertTrue("The yule list page is not displayed", allPage.exists());
        device.pressDPadDown();
        UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
        String currentAppName = currentApp.getText();
        String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
        Assert.assertTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
    }

    /**
     * Test that can enter all Entertainment Page By Card
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_YL_17_testEnterAllEntertainmentPageByCard() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[2]);
        //移动到全部娱乐 卡片
        moveToDownForMultiple(2);
        UiObject allylCard = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(7));
        //点击“全部娱乐”卡片，进入全部娱乐列表页
        allylCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
        //断言
        UiObject allylPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String allylPageTitleStr = allylPageTitle.getText();
        Assert.assertTrue("The title of all yl app page is expected" + "【全部娱乐】" + ", but actual 【" + allylPageTitleStr + "】", allylPageTitleStr.equalsIgnoreCase("全部娱乐"));
        UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
        Assert.assertTrue("The card of yule list page is not displayed", iconOfallPage.exists());
        moveToDown();
        if(!allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true)).exists()){
            moveToRight();
            moveToUpForMultiple(2);
            moveToDown();
        }
        UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
        String currentAppName = currentApp.getText();
        String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
        Assert.assertTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
    }

    /**
     * Test that all life page can be entered by clicking the tab
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_SH_19_testEnterAllLifePageByTab() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //Move to 生活 Tab
        moveToAppStoreTargetTab(appStoreTabs[3]);
        //点击"生活"tab，进入全部生活页面
        UiObject shTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[3]));
        device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
//        shTab.clickAndWaitForNewWindow();//Not support click action
        //断言
        UiObject allshPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String allshPageTitleStr = allshPageTitle.getText();
        Assert.assertTrue("The title of all shenghuo app page is expected" + "【全部生活】" + ", but actual 【" + allshPageTitleStr + "】", allshPageTitleStr.equalsIgnoreCase("全部生活"));
        UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
        Assert.assertTrue("The card of shenghuo list page is not displayed", iconOfallPage.exists());
        moveToDown();
        UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
        String currentAppName = currentApp.getText();
        String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
        Assert.assertTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
    }

    /**
     * Test that can enter into All Education Page by clicking 教育 tab
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_JY_20_testEnterAllEducationPageByTab() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //Move to 教育 Tab
        moveToAppStoreTargetTab(appStoreTabs[4]);
        UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
        // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
        device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
        //全部教育页面显示
        UiObject alljyPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String alljyPageTitleStr = alljyPageTitle.getText();
        Assert.assertTrue("The title of all shenghuo app page is expected" + "【全部教育】" + ", but actual 【" + alljyPageTitleStr + "】", alljyPageTitleStr.equalsIgnoreCase("全部教育"));
        moveToDown();
        UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
        Assert.assertTrue("The card of jiaoyu list page is not displayed", iconOfallPage.exists());
        UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
        String currentAppName = currentApp.getText();
        UiObject t = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix"));
        String appInfo = t.getText();
        Assert.assertTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
    }

    /**
     * test the UI of App Store search page displayed correctly
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_01_testSearchAppUIDisplay() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        menu();
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
        UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
        UiObject delBtn = findElementByID("tv.fun.appstore:id/search_del_key");
        //Assert
        Assert.assertTrue("搜索页面中输入框未显示", inputBox.exists());
        Assert.assertTrue("搜索页面中删除按钮未显示", delBtn.exists());
        UiObject keyboardInSearch = findElementByID("tv.fun.appstore:id/keyboard");
        int eleCount = keyboardInSearch.getChildCount();
        for (int i = 2; i < eleCount; i++) {
            if (i == eleCount - 1) {
                String msgText = keyboardInSearch.getChild(new UiSelector().className("android.widget.TextView").index(eleCount - 1)).getText();
                Assert.assertTrue("The indicate message behind search keyboard is incorrect", msgText.equalsIgnoreCase("首字母搜索，例如\"贝瓦儿歌\"输入BWEG"));
            } else {
                UiObject key = keyboardInSearch.getChild(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").index(i));
                String keyWord = key.getText();
                Assert.assertTrue("The key displayed in search keyboard is incorrect", keyWord.equalsIgnoreCase(textInSearchWindow[i - 2]));
            }
        }
        String resultInitTitle = findElementByID("tv.fun.appstore:id/search_result_title").getText();
        Assert.assertTrue("The title of suggest area in search page is displayed incorrectly", resultInitTitle.equalsIgnoreCase("热门搜索："));
        UiObject suggestApp = findElementByID("tv.fun.appstore:id/app_search_reulst_cell").getChild(new UiSelector().resourceId("tv.fun.appstore:id/container").index(0));
        Assert.assertTrue("The suggest app in search page is not displayed", suggestApp.exists());
    }

    /**
     * test search app by keyword in app store
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_02_testSearchAppInAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
        //Input search key and search
        device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("B")).click();
        UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
        String inputText = inputBox.getText();
        Assert.assertTrue("The entered keyword displayed in search input box is expected [" + "B" + "], but actual is [" + inputText + "]", inputText.equalsIgnoreCase("B"));
        String resultTitle = findElementByID("tv.fun.appstore:id/search_result_title").getText();
        int resultCount = stringToInt(findElementByID("tv.fun.appstore:id/search_result_count").getText());

        //Get the actual app num displayed in result list
        UiObject appInResult = findElementByID("tv.fun.appstore:id/search_result_noraml").getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
        //Assert
        Assert.assertTrue("搜索页面中输入关键字搜索无搜索结果显示", resultCount > 1);
        Assert.assertTrue("搜索页面中无搜索结果显示", appInResult.exists());
        Assert.assertTrue("搜索页面中搜索结果title显示错误", resultTitle.equalsIgnoreCase("搜索结果："));
    }

    /**
     * Test that can enter child list from all category page
     *
     * @throws UiObjectNotFoundException
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_All_02_testEnterChildListFromAllCategories() throws UiObjectNotFoundException, RemoteException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCategoryIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        //点击第一个子入口
        UiObject shChildEnter = findElementByID("tv.fun.appstore:id/grid").getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
        String childEnterName = shChildEnter.getText();
        shChildEnter.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        //获取子列表页面元素
        UiObject title = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String actualPageTitle = title.getText();
        UiObject appList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        UiObject scrollBar = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
        String actualAppC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int actualAppCount = stringToInt(actualAppC);
        //Assert
        Assert.assertTrue("The title of Child List page is expected [" + childEnterName + "], but actual is [" + actualPageTitle + "]", actualPageTitle.equalsIgnoreCase(childEnterName));
        Assert.assertTrue("App List page is Not displayed.", appList.exists());
        Assert.assertTrue("ScrollBar is Not displayed.", scrollBar.exists());
        Assert.assertTrue("There are more than one app in list: " + actualAppCount, actualAppCount > 1);
    }

    /**
     * Test that all categories page displays correctly.
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_1_testAllCategoriesPageDisplay() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //进入全部分类
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCategoryIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        UiObject allCategoryTitle = device.findObject(new UiSelector().className("android.widget.TextView").index(0));
        //断言
        Assert.assertTrue("All Category page title is displayed incorrectly", allCategoryTitle.getText().equalsIgnoreCase("全部分类"));
        UiObject gameTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[1]));
        UiObject ylTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[2]));
        UiObject shTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[3]));
        UiObject jyTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[4]));
        Assert.assertTrue("Game title is not displayed in all category page", gameTitle.exists());
        Assert.assertTrue("YL title is not displayed in all category page", ylTitle.exists());
        Assert.assertTrue("Life title is not displayed in all category page", shTitle.exists());
        Assert.assertTrue("Education title is not displayed in all category page", jyTitle.exists());
        UiObject gameChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(0));
        int gameChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
        Assert.assertTrue("The count of game child enter is not more than 1", gameChildCount > 1);
        UiObject ylChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(1));
        int ylChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
        Assert.assertTrue("The count of yule child enter is not more than 1", ylChildCount > 1);
        UiObject shChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(2));
        int shChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
        Assert.assertTrue("The count of shenghuo child enter is not more than 1", shChildCount > 1);
        UiObject jyChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(3));
        int jyChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
        Assert.assertTrue("The count of jiaoyu child enter is not more than 1", jyChildCount > 1);
    }

    /**
     * Test that the child list of life is displayed correctly when entering child list page from all category page after filtering game by handle in one game child list page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_5_testEnterLifeChildListPageAfterFilterGameByHandle() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(appStoreTabs[1]);
        moveToDown();
        UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
        if(!firstGameChildEnter.isSelected()){
            moveToUpForMultiple(6);
            moveToRight();
            moveToLeft();
            moveToDown();
        }
        //点击游戏tab下第一个子入口，进入列表页
        firstGameChildEnter.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        //点击"手柄"筛选按钮
        UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
        handlel.clickAndWaitForNewWindow();
        //筛选后,再次获取数据
        String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
        Boolean handlelStatus = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView")).isSelected();
        Assert.assertTrue("The total app num is not less than default after filtering by Handlel", totalAppNumAfterHandlel<= stringToInt(totalApp));
        Assert.assertTrue("The handle button is not selected", handlelStatus);
        //按遥控器Menu键，点击"全部分类"
        menu();
        UiObject allCateBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCateBtn.clickAndWaitForNewWindow();

        //点击生活下第一个子入口
        UiObject obj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(2));
        UiObject shChildEnter = obj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        String childEnterName = shChildEnter.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
        shChildEnter.clickAndWaitForNewWindow();
        String totalSHChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int totalSHChildAppNum = stringToInt(totalSHChildApp);

        //返回应用市场首页,并点击生活tab下
        device.pressBack();
        moveToAppStoreTargetTab(appStoreTabs[3]);
        moveToDown();
        UiObject childEnter = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(childEnterName));
        childEnter.clickAndWaitForNewWindow();
        String expectedAppList = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int expectedAppNum =  stringToInt(expectedAppList);
        Assert.assertTrue("The app num in sh child enter page is expected [" + expectedAppNum +"], but actaul [" + totalSHChildAppNum + "]", totalSHChildAppNum==expectedAppNum);
    }

    /**
     * Test enter yule child list page after filtering game app by remote control
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_4_testEnterYLChildListPageAfterFilterGameByRemoteControl() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[2]);
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject allCateBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCateBtn.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        //找到第二块 娱乐 区域的子入口
        UiObject obj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(1));
        UiObject childEnter = obj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0)).getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
        //点击娱乐下第一个子入口
        String childEnterName = childEnter.getText();
        childEnter.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        String totalYLChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int totalYLChildAppNum = stringToInt(totalYLChildApp);

        //通过全部分类页面，进入游戏子入口列表页
        menu();
        device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类")).clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        waitForElementPresentByID("tv.fun.appstore:id/container");
        UiObject yxObj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
        //点击游戏下第一个子入口
        UiObject yxChildEnter = yxObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        String yxChildEnterName = yxChildEnter.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
        yxChildEnter.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
        //点击"遥控器"筛选按钮
        UiObject reControl = device.findObject(new UiSelector().text("遥控器").className("android.widget.TextView"));
        reControl.clickAndWaitForNewWindow();
        //再次按Menu键，通过全部分类页面，进入娱乐子入口列表页
        menu();
        device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类")).clickAndWaitForNewWindow();
        UiObject ylCildEnterObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(childEnterName));
        ylCildEnterObj.clickAndWaitForNewWindow();
        String actualTotalYLChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int actualTotalYLChildAppNum = stringToInt(actualTotalYLChildApp);
        //断言
        Assert.assertTrue("Goto yule child list page After filtering by Handle, the app num is expected [" + totalYLChildAppNum + "], but actual is [" + actualTotalYLChildAppNum + "]", actualTotalYLChildAppNum == totalYLChildAppNum);
        String pageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title")).getText();
        Assert.assertTrue("List page title is expected [" + childEnterName + "], but actual is [" + pageTitle + "]", pageTitle.equalsIgnoreCase(childEnterName));
    }

    /**
     * Test that the UI and detail info of App Uninstall page displays correctly.
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_27_testEnterAppUninstallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        moveToRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        moveToDown();
        //断言
        Assert.assertTrue("Title of AppUninstall page is incorrect", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText().equalsIgnoreCase("应用卸载"));
        String subTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText();
        String str = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText();
        String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int totalAppNum = stringToInt(totalApp);
        List<UiObject2> appList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
        int appCardCount = appList.size();
        Assert.assertTrue("The count of app displayed in current page is not more than 1", appCardCount > 1 && appCardCount < totalAppNum);
        Assert.assertTrue("Subtitle of AppUninstall page is incorrect", subTitle.contains("有" + totalAppNum + "个应用 占用空间:") && subTitle.endsWith("M"));
        //验证页面底部有当前应用的名称和大小信息
        String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
        UiObject appInfoBar = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appState"));
        String appInfo = appInfoBar.getText();
        Assert.assertTrue("App info displayed in AppUninstall page is incorrect", appInfo.contains(firstAppName) && appInfo.endsWith("M"));
    }

    /**
     * Test that can enter app upgrade page successfully
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_47_testEnterAppUpgradePage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        UiObject appUpgradeCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_upgrade"));
        appUpgradeCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        //断言
        String pageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
        Assert.assertTrue("Title of AppUpgrade page is incorrect", pageTitle.equalsIgnoreCase("应用更新"));
    }

    /**
     * Test that enter app detail page from my app page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_17_testEnterAppDetailPageFromMyAppPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        moveToRight();
        UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
        myAppCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        Boolean appFlag = checkWhetherNoAppInMyApp();
        if (appFlag) {
            device.pressBack();
            moveToUp();
            moveToRight();
            device.pressEnter();
            waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
            //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
            //Input keyword in input textfield, to search app
            UiObject aKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
            aKey.click();
            UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
            int resultCount = Integer.parseInt(resultCountObj.getText());
            if (resultCount > 0) {
                moveToRightForMultiple(2);
//                device.pressDPadDown();
//                device.pressDPadUp();
                device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                installAppInDetailPageAndBack();
                device.pressBack();
                moveToDown();
                moveToRight();
                device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local")).clickAndWaitForNewWindow();
            }
        }
        //在应用卸载页面，按遥控器Menu键，并点击弹框上“详情”按钮
        UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
        int appCountInList = findElementByID("tv.fun.appstore:id/listview").getChildCount();
        //得到第一个已安装应用名称
        String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
        moveToDown();
        Assert.assertTrue("The first app is not selected", firstApp.isSelected());
        menu();
        int k = 0;
        if (appCountInList <= maxCountOfOneLine) {
            k = appCountInList;
        } else {
            k = maxCountOfOneLine;
        }
        UiObject selectedApp = null;
        UiObject selectApp = null;
        String selectAppName = "";
        for (int i = 0; i < k; i++) {
//           UiObject selectedAppChild =  device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(i));
//            selectApp = selectedAppChild.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName").index(1));
//            selectAppName = selectApp.getText();
            menu();
            if (!device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情")).exists()) {
                device.pressBack();
                moveToRight();
            } else {
                break;
            }
        }
        UiObject detailBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情"));
        Assert.assertTrue("The app detail button is not displayed on the Menu pop-up", detailBtn.exists());
        //点击“详情”按钮，进入应用详情页
        detailBtn.clickAndWaitForNewWindow();
        waitForAppDetailPageDisplay();
        //断言
        String appTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
//        Assert.assertTrue("The detail page is incorrect when entering detail page from app uninstall page", appTitle.equalsIgnoreCase(selectAppName));
        Assert.assertTrue("App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_root")).exists());
        Assert.assertTrue("The brief of App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/brief")).exists());
    }

    /**
     * Test uninstall app on My App page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_18_testUninstallAppFromMyAppPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
        myAppCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        Boolean appFlag = checkWhetherNoAppInMyApp();
        if (appFlag) {
            device.pressBack();
            moveToUp();
            moveToRight();
            device.pressEnter();
            waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
            //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
            //Input keyword in input textfield, to search app
            UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
            AKey.click();
            UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
            int resultCount = Integer.parseInt(resultCountObj.getText());
            if (resultCount > 0) {
                moveToRight();
//                device.pressDPadDown();
//                device.pressDPadUp();
                device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                installAppInDetailPageAndBack();
                device.pressBack();
                moveToDown();
                device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_local")).clickAndWaitForNewWindow();
            }
        }
        //得到已安装应用数量
        String installedAPP = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
        int installedAppCount = stringToInt(installedAPP);
        //在我的应用页面，按遥控Menu键
        UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
        moveToDown();
        String firstAPPName = firstApp.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
        if (firstAPPName.toLowerCase().contains(autoProjectKeyWord)) {
            moveToRight();
        }
        menu();
        UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("卸载"));
        uninstallBtn.click();
        waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        //卸载弹框弹出后，点击“确定”
        UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
        UiObject msgObj = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/uninstall_confirm"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", confirmBtn.getText().equalsIgnoreCase("确定"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", cancelBtn.getText().equalsIgnoreCase("取消"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", msgObj.getText().equalsIgnoreCase("要卸载此应用吗？"));
        confirmBtn.click();
        waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        if (installedAppCount > 1) {
            List<UiObject2> myAppList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
            int appCardCount = myAppList.size();
            Assert.assertTrue("The left app num is not more than 1", appCardCount >= 1);
            String leftApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
            int leftAppCount = Integer.parseInt(leftApp);
            Assert.assertTrue("The left app num in title is not correct after uninstalling one app", leftAppCount == installedAppCount - 1);
        } else {
            UiObject uninstallAppPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView"));
            Assert.assertTrue("My App page dispalys incorrectly after uninstalling", uninstallAppPage.exists());
        }
    }

    @Test
    public void App_AppM_40_testEnterAppDetailPageFromAppUnintallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        moveToRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        Boolean appFlag = checkWhetherNoAppInAppUninstall();
        if (appFlag) {
            device.pressBack();
            moveToUp();
            moveToRight();
            device.pressEnter();
            waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
            //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
            //Input keyword in input textfield, to search app
            UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
            AKey.click();
            UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
            int resultCount = Integer.parseInt(resultCountObj.getText());
            if (resultCount > 0) {
                moveToRightForMultiple(2);
//                device.pressDPadDown();
//                device.pressDPadUp();
                device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                installAppInDetailPageAndBack();
                device.pressBack();
                moveToDown();
                moveToRight();
                device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall")).clickAndWaitForNewWindow();
            }
        }
        //得到第一个已安装应用名称
        String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();

        //在应用卸载页面，点击应用卡片
        moveToDown();
        menu();
        UiObject appDetailBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情"));
        Assert.assertTrue("The app detail button is not displayed on the Menu pop-up", appDetailBtn.exists());
//        device.pressDPadRight();
        //点击“详情”按钮，进入应用详情页
        appDetailBtn.clickAndWaitForNewWindow();
        waitForAppDetailPageDisplay();
        //断言
        String appTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
        Assert.assertTrue("The detail page is incorrect when entering detail page from app uninstall page", appTitle.equalsIgnoreCase(firstAppName));
        Assert.assertTrue("App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_root")).exists());
        Assert.assertTrue("The brief of App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_list")).exists());
        UiObject picObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_list")).getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image").index(0));
        Assert.assertTrue("The poster of App detail page is not displayed", picObj.exists());
    }

    /**
     * test the app can be uninstalled succeccfully from AppUninstall page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_34_testUninstallAppFromAppUnintallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        moveToRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        Boolean appFlag = checkWhetherNoAppInAppUninstall();
        if (appFlag) {
            device.pressBack();
            moveToUp();
            moveToRight();
            device.pressEnter();
            waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
            //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
            //Input keyword in input textfield, to search app
            UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
            AKey.click();
            UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
            int resultCount = Integer.parseInt(resultCountObj.getText());
            if (resultCount > 0) {
                moveToRightForMultiple(2);
//                device.pressDPadDown();
//                device.pressDPadUp();
                device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                installAppInDetailPageAndBack();
                device.pressBack();
                moveToDown();
                moveToRight();
                device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall")).clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.appstore:id/title");
            }
        }
        //得到已安装应用数量
        String installedAPP = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
        int installedAppCount = stringToInt(installedAPP);
        //在应用卸载页面，点击应用卡片
        UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
        firstApp.click();
        waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        //卸载弹框弹出后，点击“确定”
        UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
        UiObject msgObj = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/uninstall_confirm"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", confirmBtn.getText().equalsIgnoreCase("确定"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", cancelBtn.getText().equalsIgnoreCase("取消"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", msgObj.getText().equalsIgnoreCase("要卸载此应用吗？"));
        confirmBtn.click();
        waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        if (installedAppCount > 1) {
            List<UiObject2> appLists = device.findObjects(By.clazz("android.widget.RelativeLayout"));
            int appCount = appLists.size();
            Assert.assertTrue("The left app num is more than 1", appCount >= 1);
            String leftApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
            int leftAppCount = Integer.parseInt(leftApp);
            Assert.assertTrue("The left app num in title is not correct after uninstalling one app", leftAppCount == installedAppCount - 1);
        } else {
            UiObject uninstallAppPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView"));
            Assert.assertTrue("The App uninstall page dispalys incorrectly after uninstalling", uninstallAppPage.exists());
        }
    }

    /**
     * test that it can navigate from MyAPP page to APPClean page by CleanData button
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_22_testGotoAppCleanPageFromMyApp() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        moveToDown();
        UiObject myAppCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_local"));
        if (!myAppCard.isSelected()) {
            moveToRightForMultiple(2);
            if (findElementByID("tv.fun.appstore:id/activity_search_btn").isSelected()) {
                moveToLeft();
                moveToDown();
            } else {
                moveToLeft();
                if (!findElementByID("tv.fun.appstore:id/tool_local").isSelected()) {
                    moveToLeft();
                }
            }
        }
        myAppCard.clickAndWaitForNewWindow();
        //在我的应用页面，按遥控器Menu键
        moveToDown();
        menu();
        UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        //断言
        Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
        device.pressDPadRight();
        appCleanBtn.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.master:id/uninstall");
        waitForElementPresentByID("tv.fun.master:id/clearData");
        UiObject appCleanPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.master:id/title"));
        UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
        UiObject cleanDataBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/clearData"));
        Assert.assertTrue("app clean page title is incorrect", appCleanPageTitle.getText().equalsIgnoreCase("应用清理"));
        Assert.assertTrue(" The uninstall button is not displayed", uninstallBtn.exists());
        Assert.assertTrue(" The name of uninstall button is displayed incorrectly", uninstallBtn.getText().equalsIgnoreCase("卸载"));
        Assert.assertTrue(" The clean date button is not displayed", cleanDataBtn.exists());
        Assert.assertTrue(" The name of clean date is displayed incorrectly", cleanDataBtn.getText().equalsIgnoreCase("清理数据"));
    }

    /**
     * test that it can navigate from AppUninstall page to APPClean page by CleanData button
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_42_testGotoAppCleanPageFromUninstallAppPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //移动焦点到导航条 > 应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        //向下选中应用卸载卡片
        moveToDown();
        moveToRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        waitForElementPresentByID("android:id/tv_fun_scrollbar");
        //在应用卸载页面，按遥控器Menu键
        moveToDown();
        menu();
        UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        //断言
        Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
        moveToRightForMultiple(2);
        appCleanBtn.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.master:id/uninstall");
        waitForElementPresentByID("tv.fun.master:id/clearData");
        UiObject appCleanPageTitle = findElementByID("tv.fun.master:id/title");
        UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
        UiObject cleanDataBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/clearData"));
        Assert.assertTrue("app clean page title is incorrect", appCleanPageTitle.getText().equalsIgnoreCase("应用清理"));
        Assert.assertTrue(" The uninstall button is not displayed", uninstallBtn.exists());
        Assert.assertTrue(" The name of uninstall button is displayed incorrectly", uninstallBtn.getText().equalsIgnoreCase("卸载"));
        Assert.assertTrue(" The clean date button is not displayed", cleanDataBtn.exists());
        Assert.assertTrue(" The name of clean date is displayed incorrectly", cleanDataBtn.getText().equalsIgnoreCase("清理数据"));
    }

    /**
     * test goto About page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_73_testEnterAppStoreAboutPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //移动焦点到导航条 >应用管理tab
        moveToAppStoreTargetTab(appStoreTabs[5]);
        UiObject2 appManageTab = device.findObject(By.text(appStoreTabs[5]));
        Assert.assertTrue("The focus is not on the appManage tab", appManageTab.isSelected());
        moveToDown();
        //Move to About card
        moveToRightForMultiple(2);
        moveToDown();
        UiObject aboutCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_about"));
        aboutCard.clickAndWaitForNewWindow();
        UiObject aboutPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/main"));
        UiObject detailInfo = device.findObject(new UiSelector().text("应用市场交流反馈QQ群：110155617\n" + "扫一扫二维码加入该群"));
        Assert.assertTrue("AppStore About page title is displayed incorrectly", aboutPageTitle.getText().equalsIgnoreCase("应用市场"));
        Assert.assertTrue("AppStore About page detail info is displayed incorrectly", detailInfo.exists());
    }
}