package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import java.util.HashMap;

/**
 * Created by liuqing on 2016/10/24.
 */
public class AppStorePage extends Common{
    public String appStoreBtn = "应用市场";
    public String appStoreTabID = "tv.fun.appstore:id/column_title";

    /**
     * Method for navigating to Launcher App tab page
     */
    public void navigateToLauncherAppTab() throws UiObjectNotFoundException {
        device.pressHome();
        //按遥控器上键,移动焦点到视频tab
        device.pressDPadUp();
        moveToLauncherTargetTab(appTab);
    }

    /**
     * Method for entering AppStore page from Launcher
     * @throws UiObjectNotFoundException
     */
    public void enterAppStorePage() throws UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        Boolean selectFlag = device.findObject(new UiSelector().text("应用").resourceId("com.bestv.ott:id/tab_title")).isSelected();
        if(!selectFlag){
            navigateToLauncherAppTab();
        }
        UiObject appStoreCard =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
        device.pressDPadDown();
        device.pressDPadDown();
        if(!appStoreCard.isSelected()){
            device.pressDPadLeft();
            device.pressDPadLeft();
        }
        device.pressEnter();
        waitForElementPresentByIDAndText("tv.fun.appstore:id/column_title", "应用管理");
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToSuggestTabFromAppManageTab(String targetTab) throws UiObjectNotFoundException {
        UiObject currentSelectedTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        if(currentSelectedTab.isSelected()){
            moveToUpForMultiple(4);//Move to navBar to avoid that the current focus not in narBar
            if(targetTab.equalsIgnoreCase("推荐")){
                moveToLeftForMultiple(5);
            }
        }
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public Boolean moveToAppStoreTargetTab(String targetTab) throws UiObjectNotFoundException {
        moveToTargetTab(appStoreTabs,targetTab, appStoreTabID, 6);
        moveToUpForMultiple(4);//Move to navBar to avoid that the current focus not in narBar
        Boolean selectedFlag = findElementByText(targetTab, appStoreTabID).isSelected();
        return selectedFlag;
    }

    /**
     * Sometimes, the default tab is not the APP tab. This method is used to move the focus to the target tab in launcher home page
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToLauncherTargetTab(String targetTab) throws UiObjectNotFoundException {
        moveToTargetTab(launcherTabs,targetTab, launcherTabID, 4);
    }

    /**
     * 在Appstore首页，移动到导航条
     *
     * step  连续向右移的次数
     */
    public void moveToTopOnAppStoreHomePage(){
        for(int i=1; i<=6; i++){
            device.pressDPadUp();
        }
    }

    /**
     * 连续按遥控器左键
     *
     * step  连续向左移的次数
     */
    public void moveToTheMostLeftOfTabPage(){
        for(int i=1; i<=5; i++){
            device.pressDPadLeft();
        }
    }

    /**
     * Check whether no app installed in myApp or AppUninstall page except the app using for auto.Usually, there will be 1 app for auto displayed in MyApp page, 2 apps for auto displayed in AppUninstall page
     *
     * @return
     */
    public Boolean checkWhetherNoAppInstalled(){
        Boolean flag = false;
        try{
            if(device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView")).exists()){
                flag=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Check whether no app installed in myApp or AppUninstall page except the app using for auto.Usually, there will be 1 app for auto displayed in MyApp page, 2 apps for auto displayed in AppUninstall page
     *
     * @param limitAppCount
     * @return
     */
    public Boolean checkWhetherNoAppInstalledExceptAutoAPP(int limitAppCount) throws UiObjectNotFoundException {
        Boolean flag = false;
        try{
            flag = checkWhetherNoAppInstalled();
            if(!flag){
                String app = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
                int appCount = stringToInt(app);
                if(appCount<=limitAppCount){
                    flag=true;
                }else{
                    String fApp = findElementByID("tv.fun.appstore:id/appName").getText();
                    if(fApp.contains("auto")||fApp.contains("test")){
                        flag=true;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Check whether no app in MyApp page except 1 app for auto
     * @return
     */
    public Boolean checkWhetherNoAppInMyApp() throws UiObjectNotFoundException {
        Boolean f = checkWhetherNoAppInstalledExceptAutoAPP(1);
        return f;
    }

    /**
     * Check whether no app in AppUninstall page except 2 app for auto
     * @return
     */
    public Boolean checkWhetherNoAppInAppUninstall() throws UiObjectNotFoundException {
        Boolean f = checkWhetherNoAppInstalledExceptAutoAPP(2);
        return f;
    }

    /**
     * Install App in detail page
     * @throws InterruptedException
     */
    public void installAppInDetailPage() throws InterruptedException {
        device.pressDPadDown();
        device.pressDPadUp();
        device.pressEnter();
        Thread.sleep(5000);
        waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
        waitForElementPresentByIDAndText("tv.fun.appstore:id/titleContainer","打开");
    }

    /**
     * Install App in detail page and back
     * @throws InterruptedException
     */
    public void installAppInDetailPageAndBack() throws InterruptedException {
        device.pressDPadDown();
        device.pressDPadUp();
        device.pressEnter();
        Thread.sleep(5000);
        waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
        waitForElementPresentByIDAndText("tv.fun.appstore:id/titleContainer","打开");
        device.pressBack();
    }

    /**
     * Goto App Clean page
     * @throws InterruptedException
     */
    public void gotoAppCleanPageFromAppUninstallPage(Boolean NeedToAppMTab) throws InterruptedException, UiObjectNotFoundException {
        if(NeedToAppMTab){
            moveToAppStoreTargetTab(appStoreTabs[5]);
        }
        gotoAppUninstallPage();
        moveToDown();
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_text");
        UiObject appCleanObj = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        appCleanObj.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.master:id/uninstall");
        waitForElementPresentByID("tv.fun.master:id/clearData");
    }

    /**
     * Uninstall App from App Clean page and back
     * @throws InterruptedException
     */
    public void uninstallAppFromAppCleanPage(String targetAppName) throws InterruptedException, UiObjectNotFoundException {
        String appNumStr = findElementByID("tv.fun.master:id/appNumber").getText().replace("应用数量 ", "").replace(" 个", "");
        int appNum = stringToInt(appNumStr);
        int targetAppIndex = 0;
        UiObject eachAppObj = null;
        moveToRightForMultiple(2);
        for(int loopTime=0; loopTime<appNum;){
            UiObject appList = findElementByID("tv.fun.master:id/listView");
            int displayedAppCount = appList.getChildCount();
            for(int k=0; k<displayedAppCount; k++){
                loopTime++;
                UiObject appListObj = findElementByID("tv.fun.master:id/listView");
                eachAppObj = appListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(k));
                UiObject appObj = eachAppObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView"));
                String appName = appObj.getText();
                if(targetAppName.equalsIgnoreCase(appName)){
                    targetAppIndex = k;
                    break;
                }else if(loopTime<appNum&&k==displayedAppCount-1){
                    k--;
                }
                moveToDown();
            }
            break;
        }
        if(targetAppIndex!=0){
            UiObject uninstallBtnOfApp = eachAppObj.getChild(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);//cannot use click method in here
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            //卸载弹框弹出后，点击“确定”
            UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
            UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
            confirmBtn.click();
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        }
        device.pressBack();
    }

    /**
     * Method for going to app uninstall page
     * @throws UiObjectNotFoundException
     */
    public void gotoAppUninstallPage() throws UiObjectNotFoundException, InterruptedException {
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        waitForElementPresentByID("tv.fun.appstore:id/appName");
    }

    /**
     * Uninstall App in App Uninstall page and back
     * @throws InterruptedException
     */
    public void uninstallAppInAppUninstallPage(String targetApp) throws InterruptedException, UiObjectNotFoundException {
        gotoAppUninstallPage();
        uninstallAppFromAppCleanPage(targetApp);
    }

    /**
     * wait for app detail page display
     */
    public void waitForAppDetailPageDisplay() throws InterruptedException {
        waitForElementPresentByClassAndText("android.widget.TextView", "操控设备：");
    }

    /**
     * Method for going to
     * @throws UiObjectNotFoundException
     */
    public void goToAppUninstallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if(!tjTab.isSelected()){
            moveToUpForMultiple(4);
            device.pressDPadUp();
        }
        moveToRightForMultiple(5);
        device.pressDPadDown();
        device.pressDPadRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
    }

    /**
     * Move to the first card in App Store page
     */
    public void moveToFirstCardUnderSuggestTab() throws UiObjectNotFoundException {
        moveToTopOnAppStoreHomePage();
        moveToAppStoreTargetTab(appStoreTabs[0]);
        moveToDown();
    }

    /**
     * Move to the first app in App Store page
     */
    public void moveToFirstAppUnderSuggestTab() throws UiObjectNotFoundException {
        moveToTopOnAppStoreHomePage();
        moveToAppStoreTargetTab(appStoreTabs[0]);
        moveToDown();
        moveToTheMostLeftOfTabPage();
        moveToRight();
    }
}