package tv.banban.appsautotest.testAppCases;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.view.Surface;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.banban.appsautotest.common.CommonMethod;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBanBanLogin extends CommonMethod{
    @Before
    public void setUp(){
//        Context context = InstrumentationRegistry.getContext();
//        Log.i(TAG, "setUp: "+getAppVersion(context, "com.imbb.banban.android")); //1.0.0.5
//        Log.i(TAG, "setUp: "+getAppName(context, "com.imbb.banban.android")); //伴伴
//        startApp("com.ee.xianshi.android","com.imbb.banban.android.MainActivity",8);
    }

    @After
    public void cleanUp(){

    }

    @Test
    public void TestWXLogin(){
        if(checkLoginStatus()){
            Log.i(TAG, "TestWXLogin: "+"T");
        }else {
            Log.i(TAG, "TestWXLogin: "+"F");
        }
    }

    @Test
    public void TestQQLogin(){

    }

    @Test
    public void TestPhoneLogin(){

    }

    private boolean checkLoginStatus() {
        Boolean status = true;
        UiObject2 message = uiDevice.findObject(By.text("消息").clazz("android.widget.TextView"));
        message.click();
        systemWait(1);
        if (uiDevice.hasObject(By.text("请使用以下方式登录").clazz("android.widget.TextView"))) {
            return status;
        }
        return false;
    }

    private void loginOut(){
        UiObject2 my = uiDevice.findObject(By.text("我").clazz("android.widget.TextView"));
        my.clickAndWait(Until.newWindow(), 2);
        systemWait(2);
        UiObject2 account = uiDevice.findObject(By.text("账号设置").clazz("android.widget.TextView"));
        account.click();
        systemWait(2);
        UiObject2 loginOut = uiDevice.findObject(By.text("退出登录").clazz("android.widget.TextView"));
        loginOut.click();
        systemWait(2);
        UiObject2 message = uiDevice.findObject(By.text("消息").clazz("android.widget.TextView"));
        message.click();
        systemWait(2);
        UiObject2 text = uiDevice.findObject(By.text("请使用以下方式登录"));
        Assert.assertEquals("请使用以下方式登录",text.getText());
        uiDevice.click(320,100);
        pressBack(2);
    }

    @Test
    public void testOrientation() throws RemoteException{
        //1.模拟设备向左向右旋转

        uiDevice.setOrientationLeft();    //设备向左旋转
        uiDevice.setOrientationRight();   //设备向右旋转

        //2.如果设备处于默认旋转状态，就向左旋转

        if (uiDevice.isNaturalOrientation()){

            uiDevice.setOrientationLeft();

        }

        //3.变量a获取当前新鲜事旋转度数，然后进行相应if判断

        int a=uiDevice.getDisplayRotation();

        if (a==Surface.ROTATION_0){

            uiDevice.setOrientationLeft();

        }

        if (a== Surface.ROTATION_90){

            uiDevice.setOrientationLeft();

        }

        if (a==Surface.ROTATION_180){

            uiDevice.setOrientationLeft();

        }

        if (a==Surface.ROTATION_270){

            uiDevice.setOrientationLeft();

        }
    }
}
