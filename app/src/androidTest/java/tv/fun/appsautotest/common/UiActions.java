package tv.fun.appsautotest.common;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.UiAutomation;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UiActions{
    public Instrumentation instrument;
    public UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    public final String TAG = "lxm";
    //得到当前方法的名字
    public String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

    private final int CLICK_ID = 1000;
    private final int CLICK_text = 1001;
    private final int CLICK_clazz = 1002;

    public UiDevice getDevice(){
        return uiDevice;
    }

//    public UiActions(UiDevice uiDevice){
//        this.uiDevice = uiDevice;
//    }


    //id
    public boolean clickById(String id){

        return clickByInfo(CLICK_ID,id);
    }

    //text
    public boolean clickByText(String text){

        return clickByInfo(CLICK_text,text);
    }

    //classname
    public boolean clickByClazz(String clazz) {

        return clickByInfo(CLICK_clazz,clazz);
    }

    private boolean clickByInfo(int CLICK,String str){

        UiSelector uiselector;
        //switch根据不同的CLICK标示，创建出UiSelector对象
        switch (CLICK)
        {
            case CLICK_ID:
                uiselector = new UiSelector().resourceId(str);
                break;
            case CLICK_text:
                uiselector = new UiSelector().text(str);
                break;
            case CLICK_clazz:
                uiselector = new UiSelector().className(str);
                break;
            default:
                return false;
        }
        //根据UiSelector对象构造出UiObject对象
        UiObject uiobject = new UiObject(uiselector);
        //判断该控件是否存在
        int i = 0;
        while (!uiobject.exists()&&i<5)
        {
            SolveProblems();
            sleep(500);
            if(i == 4){
                takeScreenToFile(uiDevice,str+"not find");
                return false;
            }
            i++;
        }
        //点击
        try {
            uiobject.click();
        }catch (UiObjectNotFoundException e){
            e.printStackTrace();
        }
        return true;
    }

    //截图
    public static void takeScreenToFile(UiDevice device, String des){
        //取得当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String dat = calendar.get(Calendar.HOUR_OF_DAY)+"_"+calendar.get(Calendar.MINUTE)+"_"+calendar.get(Calendar.SECOND);

        //保存文件
        File file = new File("/mnt/sdcard/"+dat+"_"+des+".jpg");
        if(!file.exists()){
            file.mkdirs();
        }
        device.takeScreenshot(file);
    }

    public String screenShot(String name) {//截图并命名
        File file = new File("/mnt/sdcard/123/");
        if (!file.exists()) {
            file.mkdirs();
        }
        File files = new File("/mnt/sdcard/123/"+name+".png");
        UiDevice.getInstance().takeScreenshot(files);
        String path = "/mnt/sdcard/123/" + name + ".png";
        return path;
    }

    private void SolveProblems(){
        try {
            initToastListener();
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //NULL

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //弹框消息处理
    private void initToastListener(){
        instrument = InstrumentationRegistry.getInstrumentation();
        instrument.getUiAutomation().setOnAccessibilityEventListener(new UiAutomation.OnAccessibilityEventListener() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
                //判断是否是通知事件
                try {
                    if(event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
                        return;
                    }
                    //获取消息来源
                    String sourcePackageName = (String)event.getPackageName();
                    //获取时间具体信息
                    Parcelable parcelable = event.getParcelableData();
                    //如果不是下拉框信息，则为其它通知消息，包括Toast
                    if(!(parcelable instanceof Notification)){
                        String toastMessage = (String)event.getText().get(0);
                        long toastOccurTime = event.getEventTime();
                        Log.i("autotest","信息名称:"+toastMessage);
                        if(toastMessage.contains("")){
                            JSONObject.quote(""); //jsonObject,put("")
                        }else if(toastMessage.contains("")){
                            JSONObject.quote(""); //jsonObject,put("")
                        }else {
                        }
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

    }

     //获取现在时间
     public static String getCurDate(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = formatter.format(currentTime);
        return nowDate;
    }

    //获取输入月份的天数
    public int getCurMonthOfDay(int year,int month){
        Calendar time=Calendar.getInstance();
        time.clear();//clear一下，否则很多信息会继承自系统当前时间
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    //计算某一天是一年中的第几星期
    public int getCurWeekOfDayInYear(int year,int month,int day){
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);
        time.set(Calendar.DAY_OF_MONTH,day-1);
        int week = time.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 通过文本定位的方法
     * @param text
     * @return obj
     */
    public static UiObject getUiObjectByText(String text) {
        UiObject obj = new UiObject(new UiSelector().text(text));
        return obj;
    }


    /**
     * 通过类名定位的方法
     * @param className
     * @return obj
     */
    public static UiObject getUiObjectByClassName(String className) {
        UiObject obj = new UiObject(new UiSelector().className(className));
        return obj;
    }

    /**
     * 通过ID定位的方法
     * @param id
     * @return obj
     */
    public static UiObject getUiObjectByResourceId(String id) {
        UiObject obj = new UiObject(new UiSelector().resourceId(id));
        return obj;
    }

    /**
     * 通过Content Description定位的方法
     * @param des
     * @return obj
     */
    public static UiObject getUiObjectByContentDes(String des)
    {
        UiObject obj = new UiObject(new UiSelector().description(des));
        return obj;
    }

    /**
     * 结合类名和文本一起定位的方法
     * @param className
     * @param text
     * @return obj
     */
    public static UiObject getUiObjectByBothClassNameAndText(String className, String text) {
        UiObject obj = new UiObject(new UiSelector().className(className).text(text));
        return obj;
    }

    /**
     * 拥有相同id的不同控件的获取方法
     * @param id
     * @param index
     * @return obj
     */
    public static UiObject getUiObjectByIdAndInstanceIndex(String id, int index) {

        return new UiObject(new UiSelector().resourceId(id).instance(index));
    }

    /**
     * 获取滚动对象的方法，可以指定水平或者竖直滚动，true表示水平滚动，false表示竖直滚动
     * @param isHorizontal
     * @return scrollView
     * @throws UiObjectNotFoundException
     */
    public static UiScrollable getScrollList(boolean isHorizontal) throws UiObjectNotFoundException {
        UiScrollable scrollView = new UiScrollable(new UiSelector().scrollable(true));
        if (isHorizontal)
            scrollView.setAsHorizontalList();
        else
            scrollView.setAsVerticalList();
        return scrollView;
    }

    /**
     * 获取滚动对象下的子对象的方法
     * @param scrollView
     * @param text
     * @return
     * @throws UiObjectNotFoundException
     */
    public static UiObject scrollToGetChild(UiScrollable scrollView, String text) throws UiObjectNotFoundException {

        return scrollView.getChild(new UiSelector().text(text));
    }

    /**
     * 通过类型名获得指定UiObject
     * @param clsName
     * @param index
     * @return
     */
    public static UiObject getUiObjectByClassNameAndInstanceIndex(String clsName, int index) {

        return new UiObject(new UiSelector().className(clsName).instance(index));
    }

    public void pressRight(int RightMove){
        int i = 1;
        while (i <= RightMove) {
            i++;
            uiDevice.pressDPadRight();
            SystemClock.sleep(500);
        }
    } //Right*

    public void pressLeft(int LeftMove){
        int i = 1;
        while (i <= LeftMove) {
            i++;
            uiDevice.pressDPadLeft();
            SystemClock.sleep(500);
        }
    } //Left*

    public void pressDown(int DownMove){
        int i = 1;
        while (i <= DownMove) {
            i++;
            uiDevice.pressDPadDown();
            SystemClock.sleep(500);
        }
    } //Down*

    public void pressUp(int UpMove){
        int i = 1;
        while (i <= UpMove) {
            i++;
            uiDevice.pressDPadUp();
            SystemClock.sleep(500);
        }
    } //Up*

    public void pressBack(int back) {
        int i = 1;
        while (i <= back) {
            i++;
            uiDevice.pressBack();
            SystemClock.sleep(500);
        }
    } //Back*

    public void pressEnter(int enter) {
        int i = 1;
        while (i <= enter) {
            i++;
            uiDevice.pressDPadCenter();
            SystemClock.sleep(500);
        }
    } //Enter*

    //明显输出
    public void output(String text) {
        System.out.println(text);
    }

}
