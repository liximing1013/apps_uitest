package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

/**
 * Created by liuqing on 2016/10/24.
 */
public class MasterApp extends AppStorePage {
    public String[] masterCards = {"一键优化", "内存加速", "垃圾清理", "网络测速", "应用清理", "自启动管理", "网络诊断"};

    /**
     * 在Launcher应用tab页面，点击“电视助手”卡片
     */
    public void enterTVMasterPage() throws UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        moveToTargetTab(launcherTabs, appTab, launcherTabID, 4);
        //点击“电视助手”卡片
        UiObject tvMasterCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        device.pressDPadDown();
        device.pressDPadDown();
        if (!tvMasterCard.isSelected()) {
            device.pressDPadRight();
            device.pressDPadRight();
        }
        device.pressEnter();
        waitForElementPresentByID("tv.fun.master:id/iv_settings");
    }

    /**
     * Get the app size in app clean page
     */
    public float getAppSizeInAppClean(int index) throws UiObjectNotFoundException {
        UiObject listView = findElementByID("tv.fun.master:id/listView");
        UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(index));
        UiObject sizeOfFirstApp = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/totalSize"));
        String firstAppSizeStr = sizeOfFirstApp.getText().replace("MB", "").replace("总计 ", "");//总计 41.6MB
        float firstAppSize = stringToFloat(firstAppSizeStr);
        return firstAppSize;
    }

    /**
     * Goto App Clean page in TV Master
     */
    public void gotoAppCleanPageInMaster() throws UiObjectNotFoundException, InterruptedException {
        //点击"应用清理"卡片
        UiObject cleanCard = findElementByText(masterCards[4], "tv.fun.master:id/home_item_title");
        cleanCard.clickAndWaitForNewWindow();
        waitForElementPresentByIDAndText("tv.fun.master:id/title", masterCards[4]);
    }

    /**
     * Get the app number in App Clean page by top msg
     */
    public int getAppNumInAppCleanPage() throws UiObjectNotFoundException, InterruptedException {
        //在应用清理页面，通过头部的信息得到已安装的应用个数
        UiObject appNumObj = findElementByID("tv.fun.master:id/appNumber");
        String appNumTxt = appNumObj.getText().replace("应用数量 ", "").replace(" 个", "");
        int appNum = stringToInt(appNumTxt);
        return appNum;
    }

    /**
     * Get the app total size in App Clean page by top msg
     */
    public float getAppTotalSizeInAppCleanPage() throws UiObjectNotFoundException, InterruptedException {
        //在应用清理页面，通过头部的信息得到已安装的应用占用空间
        UiObject appSizeObj = findElementByID("tv.fun.master:id/spaceUsage");
        String appSizemTxt = appSizeObj.getText().replace("占用空间 ", "").replace(" MB", "");
        float appSize = stringToFloat(appSizemTxt);
        return appSize;
    }

    /**
     * Get the app name in App Clean list
     */
    public String getAppNameInAppCleanPage(int index) throws UiObjectNotFoundException {
        UiObject listView = findElementByID("tv.fun.master:id/listView");
        UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(index));
        String firstAppName = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView")).getText();
        return firstAppName;
    }
}
