package tv.banban.appsautotest.testCases;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.banban.appsautotest.common.CommonMethod;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBan extends CommonMethod {


    @Test
    public void banBan(){
        startBanBan();
        systemWait(6);
        UiObject2 gc = uiDevice.findObject(By.text("广场"));
        if(gc != null){
            UiObject2 me = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
            me.click();
            systemWait(1);
            UiObject2 gcc = uiDevice.findObject(By.text("交友动态"));
            gcc.clickAndWait(Until.newWindow(), 3000);
        }
    }

    private void startBanBan() {
        Context context = InstrumentationRegistry.getContext();
        Intent launchIntent = new Intent();
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动应用
        launchIntent.setComponent(new ComponentName("com.imbb.banban.android", "com.imbb.banban.android.MainActivity"));
        context.startActivity(launchIntent);
    }
}
