package tv.fun.appstoretest.TestCases;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiCollection;
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


/**
 * Created by liuqing on 2016/8/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAppStore extends AppStorePage {
    private static final String AppStore_PACKAGE = "tv.fun.appstore";

    @Category({LevelP1Tests.class})
    @Test
    public void App_Home_01_testLauncherAppUI() throws UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        //得到Launcher应用页面元素
        UiObject appStoreIcon =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
        UiObject tvMasterIcon =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        UiObject myAppIcon =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/maintitle").text(myAppIconName));
        UiObject myAppSubTitle =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));//(22个)
        String appStoreStr = appStoreIcon.getText();

        //断言
        Assert.assertTrue("应用市场卡片没有显示在Launcher应用页面", appStoreIcon.exists());
        Assert.assertTrue("在Launcher应用页面应用市场卡片显示不正确", appStoreIcon.getText().equalsIgnoreCase(appStoreIconName));
        Assert.assertTrue("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon.exists());
        Assert.assertTrue("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon.exists());
        Assert.assertTrue("我的应用卡片没有显示在Launcher应用页面", myAppIcon.exists());
        Assert.assertTrue("我的应用卡片下方的应用数量没有显示在Launcher应用页面", myAppSubTitle.getText().contains(myAppCountUnit));
    }

    @Category(LevelP2Tests.class)
    @Test
    public void App_Home_07_testEnterAppDetailFromLauncher() throws RemoteException, UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        device.pressDPadDown();
        UiObject firstApp = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/poster").index(0));
        firstApp.clickAndWaitForNewWindow();
        //断言
        UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
        UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
        Assert.assertTrue("详情页中海报图没有显示", pic.exists());
        Assert.assertTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
    }

    @Category(LevelP2Tests.class)
    @Test
    public void App_Home_07_testInstallAppFromLauncherAndBack() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        device.pressDPadDown();
        UiObject firstApp = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/poster").index(0));
        firstApp.clickAndWaitForNewWindow();

        UiObject installBtn = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").text("安装"));
        int i = 0;
        //如果第一个应用已安装，回到首页再选择另一应用安装
        if("打开".equalsIgnoreCase(installBtn.getText())){
            device.pressBack();
            device.pressDPadRight();
            device.pressEnter();
        }
        //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
        device.pressDPadDown();
        device.pressDPadUp();

        device.pressEnter();
        Thread.sleep(50000);
        UiObject2 openBtn = device.findObject(By.clazz("android.widget.Button"));

        //断言
        Assert.assertTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase("打开"));
        device.pressBack();
        UiObject appTab = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("应用"));
        Assert.assertTrue("Doesn't back to Launcher App page", appTab.exists());
    }

    @Test
    public void App_Home_43_testAppStoreUIDisplay() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //断言
        UiObject tjTab =  device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        UiObject yxTab =  device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("游戏"));
        UiObject ylTab =  device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("娱乐"));
        UiObject shTab =  device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("生活"));
        UiObject jyTab =  device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("教育"));
        UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));

        Assert.assertTrue("The tuijian tab in AppStore page is not displayed", tjTab.exists());
        Assert.assertTrue("The game tab in AppStore page is not displayed", yxTab.exists());
        Assert.assertTrue("The yule tab in AppStore page is not displayed", ylTab.exists());
        Assert.assertTrue("The shenghuo tab in AppStore page is not displayed", shTab.exists());
        Assert.assertTrue("The jiaoyu tab in AppStore page is not displayed", jyTab.exists());
        Assert.assertTrue("The search icon in AppStore page is not displayed", searchObj.exists());
        Assert.assertTrue("The appManage tab in AppStore page is not displayed", appManageTab.exists());
    }

    /**
     * test go to hot topic page under suggest tab
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_TJ_7_testGotoHotTopicPageAndBack() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
        if(!hotTopic.isSelected()){
            moveToTargetTab("推荐");
            device.pressDPadDown();
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
        Assert.assertTrue("The count of app listed in hot topic page is not more than 1", num>1);
    }

    /**
     * test enter app detail from app store page
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_TJ_64_testEnterAppDetailFromAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToTargetTab("推荐");
        UiObject firstSuggestCard = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
        if(firstSuggestCard.isFocusable()){
            device.pressDPadDown();
        }
        device.pressDPadRight();

        UiObject firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
        if(!firstApp.isSelected()){
            device.pressDPadRight();
        }
        //点击应用卡片
        firstApp.clickAndWaitForNewWindow();
        //Assert
        UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
        UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
        Assert.assertTrue("详情页中海报图没有显示", pic.exists());
        Assert.assertTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
    }

    @Category(LevelP1Tests.class)
    @Test
    public void App_Game_15_testInstallAppFromAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToTargetTab("推荐");
        UiObject firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
        firstApp.clickAndWaitForNewWindow();
        //Assert
        UiObject installBtn = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").text("安装"));
        int i = 0;

        if(!"安装".equalsIgnoreCase(installBtn.getText())){
            device.pressBack();
            device.pressDPadRight();
            device.pressEnter();
        }
        //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
        device.pressDPadDown();
        device.pressDPadUp();

        device.pressEnter();
        Thread.sleep(50000);
        UiObject2 openBtn = device.findObject(By.clazz("android.widget.Button"));
        Assert.assertTrue("详情页中应用未安装成功", openBtn.getText().equalsIgnoreCase("打开"));
    }

    /**
     * test the UI of App Store search page displayed correctly
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
     @Test
    public void App_Search_1_testSearchAppUIDisplay() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        device.pressMenu();
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();
        UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
        UiObject delBtn = findElementByID("tv.fun.appstore:id/search_del_key");
        //Assert
        Assert.assertTrue("搜索页面中输入框未显示", inputBox.exists());
        Assert.assertTrue("搜索页面中删除按钮未显示", delBtn.exists());
        String[] textInSearchWindow = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        UiObject keyboardInSearch = findElementByID("tv.fun.appstore:id/keyboard");
        int eleCount = keyboardInSearch.getChildCount();
        for(int i = 2; i<eleCount; i++){
            if(i==eleCount-1){
                String msgText = keyboardInSearch.getChild(new UiSelector().className("android.widget.TextView").index(eleCount-1)).getText();
                Assert.assertTrue("The indicate message behind search keyboard is incorrect", msgText.equalsIgnoreCase("首字母搜索，例如\"贝瓦儿歌\"输入BWEG"));
            }else {
                UiObject key = keyboardInSearch.getChild(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").index(i));
                String keyWord = key.getText();
                Assert.assertTrue("The key displayed in search keyboard is incorrect", keyWord.equalsIgnoreCase(textInSearchWindow[i - 2]));
            }
        }
        String resultInitTitle = findElementByID("tv.fun.appstore:id/search_result_title").getText();
        Assert.assertTrue("The title of suggest area in search page is displayed incorrectly",resultInitTitle.equalsIgnoreCase("热门搜索："));
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
    public void App_Search_2_testSearchAppInAppStore() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        device.pressMenu();
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();

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
        Assert.assertTrue("搜索页面中输入关键字搜索无搜索结果显示", resultCount>1);
        Assert.assertTrue("搜索页面中无搜索结果显示", appInResult.exists());
        Assert.assertTrue("搜索页面中搜索结果title显示错误", resultTitle.equalsIgnoreCase("搜索结果："));
    }

    @Category(LevelP1Tests.class)
    @Test
    public void App_All_2_testEnterChildListFromAllCategories() throws UiObjectNotFoundException, RemoteException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        device.pressDPadRight();
        device.pressMenu();
        UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCategoryIcon.clickAndWaitForNewWindow();
        //点击第一个子入口
        UiObject shChildEnter = findElementByID("tv.fun.appstore:id/grid").getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
        String childEnterName = shChildEnter.getText();
        shChildEnter.clickAndWaitForNewWindow();
        //获取子列表页面元素
        UiObject title = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
        String actualPageTitle = title.getText();
        UiObject appList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
        UiObject scrollBar = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
        String actualAppC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
        int actualAppCount =  stringToInt(actualAppC);
        //Assert
        Assert.assertTrue("The title of Child List page is expected [" + childEnterName +"], but actual is [" + actualPageTitle + "]", actualPageTitle.equalsIgnoreCase(childEnterName));
        Assert.assertTrue("App List page is Not displayed.", appList.exists());
        Assert.assertTrue("ScrollBar is Not displayed.", scrollBar.exists());
        Assert.assertTrue("There are more than one app in list: " + actualAppCount, actualAppCount > 1);

    }

    @Test
    public void App_All_1_testAllCategoriesPageDisplay() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();

        //进入全部分类
        device.pressMenu();
        Thread.sleep(20000);
        UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        allCategoryIcon.clickAndWaitForNewWindow();

        UiObject allCategoryTitle = device.findObject(new UiSelector().className("android.widget.TextView").index(0));
        Assert.assertTrue("All Category page title is displayed incorrectly", allCategoryTitle.getText().equalsIgnoreCase("全部分类"));

        UiObject gameTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text("游戏"));
        UiObject ylTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text("娱乐"));
        UiObject shTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text("生活"));
        UiObject jyTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text("教育"));

        Assert.assertTrue("Game title is not displayed in all category page", gameTitle.exists());
        Assert.assertTrue("YL title is not displayed in all category page", ylTitle.exists());
        Assert.assertTrue("Life title is not displayed in all category page", shTitle.exists());
        Assert.assertTrue("Education title is not displayed in all category page", jyTitle.exists());
        UiObject gameChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(0));
        int gameCldCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getChildCount();
//        Assert.assertTrue(gameChildCount==);
        Assert.assertTrue("test".equalsIgnoreCase("test"));
    }

    /**
     * test the app can be uninstalled succeccfully from AppUninstall page
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_34_testUninstallAppFromAppUnintallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        UiObject appMTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if(!appMTab.isSelected()){
            if(!tjTab.isSelected()){
                moveToTop(4);
            }
            moveToTargetTab("应用管理");
        }
        device.pressDPadDown();
        device.pressDPadRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        Boolean appFlag = checkWhetherNoAppInAppUninstall();
        if(appFlag){
            device.pressBack();
            device.pressDPadUp();
            device.pressDPadRight();
            device.pressEnter();
            waitForElementPresentByText("tv.fun.appstore:id/search_result_title", "热门搜索：");
            //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
            //Input keyword in input textfield, to search app
            UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
            AKey.click();
            UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
            int resultCount = Integer.parseInt(resultCountObj.getText());
            if(resultCount>0){
                device.pressDPadRight();
                device.pressDPadRight();
//                device.pressDPadDown();
//                device.pressDPadUp();
                device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                installAppInDetailPageAndBack();
                device.pressBack();
                device.pressDPadDown();
                device.pressDPadRight();
                device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall")).clickAndWaitForNewWindow();
            }
        }
        //得到已安装应用数量
        String installedAPP = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
        int installedAppCount = stringToInt(installedAPP);
        //在应用卸载页面，点击应用卡片
        UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
        firstApp.click();
        Thread.sleep(5);
        //卸载弹框弹出后，点击“确定”
        UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
        UiObject msgObj = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/uninstall_confirm"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", confirmBtn.getText().equalsIgnoreCase("确定"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", cancelBtn.getText().equalsIgnoreCase("取消"));
        Assert.assertTrue("Uninstall pop-up window is not displayed", msgObj.getText().equalsIgnoreCase("要卸载此应用吗？"));
        confirmBtn.click();
        Thread.sleep(10);
        if(installedAppCount>1) {
            List<UiObject2> appLists = device.findObjects(By.clazz("android.widget.RelativeLayout"));
            int appCount = appLists.size();
            Assert.assertTrue("The left app num is not correct after uninstalling one app", appCount==installedAppCount-1);
            String leftApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
            int leftAppCount = Integer.parseInt(leftApp);
            Assert.assertTrue("The left app num in title is not correct after uninstalling one app", leftAppCount==installedAppCount-1);
        }else{
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
        //移动焦点到导航条
        device.pressDPadUp();
        UiObject appMTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if(!appMTab.isSelected()){
            if(!tjTab.isSelected()){
                moveToTop(4);
            }
            moveToTargetTab("应用管理");
        }
        device.pressDPadDown();
        UiObject myAppCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_local"));
        myAppCard.clickAndWaitForNewWindow();
        //在我的应用页面，按遥控器Menu键
        device.pressDPadDown();
        device.pressMenu();
        UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        //断言
        Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
        device.pressDPadRight();
        appCleanBtn.clickAndWaitForNewWindow(50);
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
        //移动焦点到导航条
        device.pressDPadUp();
        UiObject appMTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if(!appMTab.isSelected()){
            if(!tjTab.isSelected()){
                moveToTop(4);
            }
            moveToTargetTab("应用管理");
        }
        device.pressDPadDown();
        device.pressDPadRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        //在应用卸载页面，按遥控器Menu键
        device.pressDPadDown();
        device.pressMenu();
        UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        //断言
        Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
        device.pressDPadRight();
        device.pressDPadRight();
        appCleanBtn.clickAndWaitForNewWindow();
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
     * test goto About page
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_73_testEnterAppStoreAboutPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //移动焦点到导航条
        device.pressDPadUp();
        UiObject appMTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if(!appMTab.isSelected()){
            if(!tjTab.isSelected()){
                moveToTop(4);
            }
            moveToTargetTab("应用管理");
        }
        String appManageTabName = "应用管理";
        UiObject2 appManageTab = device.findObject(By.text(appManageTabName));
        Assert.assertTrue("The focus is not on the appManage tab", appManageTab.isSelected());
        device.pressDPadDown();

        //Move to About card
        moveToRightForMultiple(2);
        device.pressDPadDown();
        UiObject aboutCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_about"));
        aboutCard.clickAndWaitForNewWindow();
        UiObject aboutPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/main"));
        UiObject detailInfo = device.findObject(new UiSelector().text("应用市场交流反馈QQ群：110155617\n" +"扫一扫二维码加入该群"));
        Assert.assertTrue("AppStore About page title is displayed incorrectly", aboutPageTitle.getText().equalsIgnoreCase("应用市场"));
        Assert.assertTrue("AppStore About page detail info is displayed incorrectly", detailInfo.exists());
    }
}
