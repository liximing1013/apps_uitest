package tv.fun.appstoretest.Common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import java.util.HashMap;

/**
 * Created by liuqing on 2016/10/24.
 */
public class AppStorePage extends Common{
    public int timeout = 60;
    public int nextPageTime = 3000;
    public int sleepInterval = 500;
    public String[] appStoreTabs = {"推荐", "游戏", "娱乐", "生活", "教育", "应用管理"};

    /**
     * Change int type to the string type
     */
    public int stringToInt(String intstr){
        Integer integer;
        integer = Integer.valueOf(intstr);
        return integer.intValue();
    }

    /**
     *  Change int type to the string type
     */
    public static String intToString(int value)
    {
        Integer integer = new Integer(value);
        return integer.toString();
    }

    /**
     * Click and wait for some time
     */
    public void clickAndWait(UiObject Obj, int waitTime) throws UiObjectNotFoundException, InterruptedException {
       Obj.click();
        Thread.sleep(waitTime);
    }

    /**
     * Method for navigating to Launcher App tab page
     */
    public void navigateToLauncherAppTab(){
        device.pressHome();
        //按遥控器上键,移动焦点到视频tab
        device.pressDPadUp();
        device.pressDPadRight();
        device.pressDPadRight();
        device.pressDPadRight();
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
        waitForElementPresentByText("tv.fun.appstore:id/column_title", "应用管理");
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToSuggestTabFromAppManageTab(String targetTab) throws UiObjectNotFoundException {
        UiObject currentSelectedTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        if(currentSelectedTab.isSelected()){
            moveToTop(4);//Move to navBar to avoid that the current focus not in narBar
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
    public void moveToTargetTab(String targetTab) throws UiObjectNotFoundException {
        UiObject tab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(targetTab));
        if(!tab.isSelected()){
            moveToTop(4);//Move to navBar to avoid that the current focusot in narBar
            UiObject currentTabObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").selected(true));
            String currentTab = currentTabObj.getText();
            int needStep = stepFrom1stTabToTargetTab(currentTab, targetTab);
            if(needStep>0){
                moveToRightForMultiple(needStep);
            }else {
                moveToLeftForMultiple(needStep);
            }
        }
    }

    /**
     * Use to get the step need to move from the first tab to target tab
     *
     * @param targetTab
     * @return
     */
    public int stepFrom1stTabToTargetTab(String currentTab, String targetTab){
        HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
        int tabCount = appStoreTabs.length;
        for(int i=0; i<tabCount; i++){
            tabMap.put(appStoreTabs[i], i+1);
        }
        int startStep = tabMap.get(currentTab);
        int targetTabStep = tabMap.get(targetTab);
        int step = targetTabStep - startStep;
        return step;
    }
    /**
     * 连续按遥控器向上键
     *
     * step  连续向右移的次数
     */
    public void moveToTop(int step){
        for(int i=1; i<=step; i++){
            device.pressDPadUp();
        }
    }

    /**
     * 连续按遥控器右键
     *
     * step  连续向右移的次数
     */
    public void moveToRightForMultiple(int step){
        for(int i=1; i<=step; i++){
            device.pressDPadRight();
        }
    }

    /**
     * 连续按遥控器左键
     *
     * step  连续向左移的次数
     */
    public void moveToLeftForMultiple(int step){
        for(int i=1; i<=step; i++){
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
        waitForElementPresentByText("tv.fun.appstore:id/titleContainer","打开");
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
        waitForElementPresentByText("tv.fun.appstore:id/titleContainer","打开");
        device.pressBack();
    }

//    public void waitForElementNotPresentByText(){
//        UiObject installBtn = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").text("安装"));
//
//    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator
     *            an element locator
     * @throws InterruptedException
     */
    public void waitForElementPresentByID(String locator)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + ">");
                break;
            }
            if (device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator
     *            an element locator
     *@param textStr
     *            text of element
     * @throws InterruptedException
     */
    public void waitForElementPresentByText(String locator, String textStr)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + "> with text (" + textStr + ")" );
                break;
            }
            if (device.findObject(new UiSelector().resourceId(locator).text(textStr)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator
     *            an element locator
     * @throws InterruptedException
     */
    public void waitForElementNotPresentByID(String locator)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + ">");
                break;
            }
            if (!device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element not present. Wait for an element form "exist" to
     * "disappear" in page.
     *
     * @param locator
     *            an element locator
     * @throws InterruptedException
     */
    public void waitForElementNotPresent(String locator)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element not present <"
                        + locator + ">");
                break;
            }
            if (!device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Find Elements by Class Name
     *
     */
    public void findElementsByClassName(){

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
            moveToTop(4);
            device.pressDPadUp();
        }
        moveToRightForMultiple(5);
        device.pressDPadDown();
        device.pressDPadRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
    }

    /****************************************************Method for TVMaster Module*******************************************/

    /**
     * 在Launcher应用tab页面，点击“电视助手”卡片
     */
    public void enterTVMasterPage() throws UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        navigateToLauncherAppTab();
        //点击“电视助手”卡片
        UiObject tvMasterCard =  device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        device.pressDPadDown();
        device.pressDPadDown();
        if(!tvMasterCard.isSelected()){
            device.pressDPadRight();
            device.pressDPadRight();
        }
        device.pressEnter();
    }
}
