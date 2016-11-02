package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

/**
 * Created by liuqing on 2016/10/24.
 */
public class MasterApp extends Common {

    /**
     * 在Launcher应用tab页面，点击“电视助手”卡片
     */
    public void enterTVMasterPage() throws UiObjectNotFoundException {
        //移动焦点到Launcher应用tab
        moveToTargetTab(launcherTabs,appTab, launcherTabID, 4);
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
