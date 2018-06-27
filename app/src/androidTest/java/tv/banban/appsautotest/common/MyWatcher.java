package tv.banban.appsautotest.common;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiWatcher;

/**
 * Created by lixm on 2018/1/16.
 */

public class MyWatcher implements UiWatcher {
    private UiDevice uiDevice;
    public MyWatcher(UiDevice device){
        uiDevice = device;
    }
    @Override
    public boolean checkForCondition() {

        if(uiDevice.hasObject(By.text("删除安装包"))){
            uiDevice.pressBack();
            return true;
        }
        return false;
    }

}
