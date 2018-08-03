package tv.banban.appsautotest.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lixm on 2017/12/1.
 */

public class CommonMethod extends UiActions {
    private String path;

//    public UiDevice uiDevice;

//
//    //构造器
//    private CommonMethod(UiDevice uiDevice){
//        this.uiDevice = uiDevice;
//    }
//    private CommonMethod() {
//        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//    }

//    public CommonMethod(Instrumentation instrument,UiDevice uiDevice){
//        this.uiDevice = uiDevice;
//        this.instrument = instrument;
//

    protected UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
        List<UiObject2> tabTitles = device.findObjects(By.res("com.bestv.ott:id/title"));
        Assert.assertTrue("Verify tabs on launcher home.", tabTitles.size() > 0);
        UiObject2 retTitle = null;
        for (UiObject2 title : tabTitles) {
            if (tabText.equals(title.getText())) {
                retTitle = title;
            }
        }
        Assert.assertNotNull(retTitle);
        return retTitle;
    }

    //列表页随机选择电影
    protected void randomPlayFilm(){
        int i =1+(int)(Math.random()*30); //随机数
        if(i < 15){
            pressDown(i);
            systemWait(1);
        }else {
            pressDown(i-10);
            systemWait(1);
        }
    }

    //等待时间设定
    public static void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    }

    //将xx:xx:xx格式的时间转化为xxx秒
    protected int timeTrans(String sTime){
        int iTime;
        int iHour = 0;
        int iMins = 0;
        int iSecs;
        String[] lsTime = sTime.split(":");

        if( lsTime.length == 3){
            iHour = Integer.parseInt(lsTime[0]);
            iMins = Integer.parseInt(lsTime[1]);
            iSecs = Integer.parseInt(lsTime[2]);
        }
        else if(lsTime.length == 2) {
            iMins = Integer.parseInt(lsTime[0]);
            iSecs = Integer.parseInt(lsTime[1]);
        }
        else{
            iSecs = Integer.parseInt(lsTime[0]);
        }
        iTime = 3600 * iHour + 60 * iMins + iSecs;
        return iTime;
    }

    //等待对象出现
    protected void waitForObjExists(String res,long time){
        UiObject obj = uiDevice.findObject(new UiSelector().resourceId(res));
        obj.waitForExists(time);
        if(obj.exists()){
            return ;
        }
        sleep();
    }

    private void init() throws NullPointerException{
        Context context= InstrumentationRegistry.getTargetContext();
        path=context.getExternalCacheDir().getPath();
        Log.i(TAG, "init: path = " + path);
    }

    @Nullable
    private File createFile(String path, String fileName){
        File file=new File(path,fileName);
        try {
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    protected void ScreenShot(String name){
        //取得当前时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        String dat = calendar.get(Calendar.HOUR_OF_DAY) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND);
        init();
        File file =createFile(path,name);
        systemWait(2);
        uiDevice.takeScreenshot(file,1.0f,10);
        uiDevice.waitForIdle();
    }

    protected String getAppVersion(Context context,String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname, 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packname;
    }

    /**
     * 获取程序的名字
     * @param context
     * @param packname
     * @return
     */
    protected String getAppName(Context context,String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packname;
    }

    //等待对象消失
    public void waitUntilObjGone(String str,int time){
        UiObject obj = uiDevice.findObject(new UiSelector().resourceId(str));
        obj.waitUntilGone(time);//在一定时间内判断控件是否消失
        if(!obj.exists()){
            return;
        }
        sleep();
    }

    //获取页面某种控件的个数
    public int getCountByResourceId(String id,int instance) {
        int num = 0;
        for(int i=0;i<100;i++){
            try {
                new UiObject(new UiSelector().resourceId(id).instance(instance)).getText();
//                getUiObjectByResourceIdAndIntance("com.gaotu100.superclass:id/assignmentitemview_upload_text", i).getText();
            } catch (UiObjectNotFoundException e) {
               e.printStackTrace();
                num = i;
                break;
            }
        }
        return num;
    }

    /**
     * 百分百图片相同断言-.-
     */
    public static void AssertBitmapEqual(Bitmap bitmap0, Bitmap bitmap1){

        Assert.assertEquals(100, ImageCompare(bitmap0,bitmap1));
    }

    /**
     * 百分之零图片相同断言
     */
    public static void AssertBitmapNotEqual(Bitmap bitmap0, Bitmap bitmap1){

        Assert.assertEquals(0, ImageCompare(bitmap0,bitmap1));
    }

    /**
     * 提供根据路径直接比较
     */
    public static int ImageCompare(String path0,String path1) {

        Bitmap bitmap0 = BitmapFactory.decodeFile(path0);
        Bitmap bitmap1 = BitmapFactory.decodeFile(path1);
        return ImageCompare(bitmap0,bitmap1);
    }

    /**
     * 裁剪子图并比较，主要是为了解决拉取动态数据不同，但是局部提示不变的比较场景。
     */
    public static int ImageCompareChild(Bitmap bitmap0, Bitmap bitmap1,int x,int y,int width,int height) {

        Bitmap bitmap00 = bitmap0.createBitmap(bitmap0, x, y, width, height);
        Bitmap bitmap01 = bitmap1.createBitmap(bitmap1, x, y, width, height);
        return ImageCompare(bitmap00,bitmap01);
    }

    /**
     * 比较的主函数
     * 只能比较相同长宽的图片，不相等返回-1失败
     * 相似度为1~100
     * 原理是提取每一个像素点比较，整张图相似度取决于像素点相同个数，所以还是比较准确的
     */
    private static int ImageCompare(Bitmap bitmap0, Bitmap bitmap1) {
        int picPct = 0;
        int picCount = 0;
        int picCountAll = 0;
        if (bitmap0 == null || bitmap1 == null) {
            //null bitmap
            return -1;
        }
        if (bitmap0.getWidth() != bitmap1.getWidth()
                || bitmap0.getHeight() != bitmap1.getHeight()) {
            return -1;
        }
//        new Println("宽度为:" + bitmap1.getWidth() + "高度为:" + bitmap1.getHeight());
        for (int j = 0; j < bitmap1.getWidth(); j++) {
            for (int i = 0; i < bitmap0.getHeight(); i++) {
                if (bitmap0.getPixel(j, i) == bitmap1.getPixel(j, i)) {
                    picCount++;
                }
                picCountAll++;
            }
        }
        int result = (int) (((float) picCount) / picCountAll * 100);
//        new Println(picCount + "/" + picCountAll);
//        new Println("相似度为:" + result);
        return result;
    }

    //获取某一坐标点的颜色值
    public int getColorPixel(int x, int y) {
        takeScreenToFile(uiDevice,"test");//截图
        String path = "/mnt/sdcard/autotest/test.png";
        Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
        int color = bitmap.getPixel(x, y);//获取坐标点像素颜色
        return color;
    }

    //删除截图文件夹
    public void deleteScreenShot() {
        File file = new File("/mnt/sdcard/autotest/");
        if (file.exists()) {//如果file存在
            File[] files = file.listFiles();//获取文件夹下文件列表
            for (int i = 0; i < files.length; i++) {//遍历删除
                files[i].delete();
            }
            file.delete();//最后删除文件夹，如果不存在直接删除文件夹
        } else {
            System.out.print("文件夹不存在！");
        }

    }

    //获取随机数
    public int getRandomInt(int num) {

        return new Random().nextInt(num);
    }

    //通过包名启动app
    public void startWishApp(String packName) throws IOException {
        Runtime.getRuntime().exec("am start -n"+packName);
    }

    //把string类型转化为int
    public int changeStringToInt(String text) {

        return Integer.valueOf(text);
    }

    //把string类型转化为double
    public Double changeStringToDouble(String text) {

        return Double.valueOf(text);
    }

    //获取电视剧列表页Tab数量
    public void getListPageTabCount() throws UiObjectNotFoundException{
        UiObject tabList = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/indicator"));
        UiObject tab = tabList.getChild(new UiSelector().className("android.widget.LinearLayout"));
        int tabShowCount = tab.getChildCount(); //电视剧=8
    }

    //搜索显示RightPage
    public void getSearchPageView() throws UiObjectNotFoundException{
        UiObject2 searchText = uiDevice.findObject(By.res("com.funshion.xiriassist:id/hot_search_txt_hint")
        .pkg("com.funshion.xiriassist"));
        if(searchText != null){
            UiObject searchList = uiDevice.findObject(new UiSelector().resourceId("com.funshion.xiriassist:id/hot_search_list"));
            UiObject view = searchList.getChild(new UiSelector().className("android.view.View"));
            int viewShowCount = view.getChildCount(); //8
            UiObject searchCell = uiDevice.findObject(new UiSelector().resourceId("com.funshion.xiriassist:id/hot_search_cell")
            .index(3));
            int hotVideo = searchCell.getChildCount();//3
            if(viewShowCount == 8&&hotVideo == 3){
                output("True");
            }
        }else{
            output("大家都在搜显示为空");
        }
    }

    //NEWS24页面判断
    public void getNewsPageViewAndClick(int i)throws UiObjectNotFoundException{
        UiObject grid = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/kankan_grid"));
        int window = grid.getChildCount();//7
        Assert.assertTrue("page has seven window",window == 7);
        UiObject win = uiDevice.findObject(new UiSelector().index(i).className("android.widget.RelativeLayout")
                .packageName("android.widget.RelativeLayout"));
        win.clickAndWaitForNewWindow();
        if(i >window-1){
            output("please input 0-6");
        }
    }

    //获取文件MD5值
    public static String getMd5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    //时间比较
    public int timeCompare(String str1, String str2) {
        String[] strArr1 = str1.split("/");
        String[] strArr2 = str2.split("/");
        String[] formerArr = strArr1[0].trim().split(":");
        String[] latterArr = strArr2[0].trim().split(":");

        int time1 = Integer.parseInt(formerArr[0].trim()) * 3600
                + Integer.parseInt(formerArr[01].trim()) * 60
                + Integer.parseInt(formerArr[2].trim());
        int time2 = Integer.parseInt(latterArr[0].trim()) * 3600
                + Integer.parseInt(latterArr[01].trim()) * 60
                + Integer.parseInt(latterArr[2].trim());
        return (time2 - time1);
    }

    //图片比较
    public boolean imageComapre() throws UiObjectNotFoundException {

        String path = "/data/local/tmp/";
        String imageName1 = "image1.png";
        File image1 = new File(path + imageName1);
        getDevice().takeScreenshot(image1, 0.5f, 30);

        String imageName2 = "image2.png";
        File image2 = new File(path + imageName2);
        getDevice().takeScreenshot(image2, 0.5f, 30);

        Bitmap bitmap1 = BitmapFactory.decodeFile(path + imageName1);
        Bitmap bitmap2 = BitmapFactory.decodeFile(path + imageName2);

        boolean isSame = Bitmap.createBitmap(bitmap1, 100, 200, 50, 50).sameAs(
                Bitmap.createBitmap(bitmap2, 100, 200, 50, 50));

        return isSame;
    }

    //存在&&可用
    public boolean isExist(UiObject uiObj) {
        try {
            return uiObj.exists() && uiObj.isEnabled() ? true : false;//三元
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //获取某种搜索条件组件的数量
    public void collectCount() throws UiObjectNotFoundException{
        UiCollection collection = new UiCollection(new UiSelector().resourceId("com.bestv.ott:id/live_against_list"));
        int we = collection.getChildCount(new UiSelector().resourceId("com.bestv.ott:id/container"));
        Log.d(TAG, "selectLocation: "+we);
    }

    public void testImageCapture() throws UiObjectNotFoundException {
        UiObject record = uiDevice.findObject(new UiSelector().text("电影").resourceId("com.bestv.ott:id/title"));
        //获取区域
        Rect rect = record.getBounds();
        //截图
        String path = "/mnt/sdcard/test.jpg";
        File file = new File(path);
        UiDevice.getInstance().takeScreenshot(file);
        Bitmap m = BitmapFactory.decodeFile(path);
        //截取图片
        ImageCapture(rect,path);
        //获取某个点的像素值
        int pixel = m.getPixel(rect.centerX(),rect.centerY());
        Log.d(TAG, "testImageCapture: "+pixel);
        //不用的Bitmap及时回收
        if(!m.isRecycled()){
            m.recycle();
            System.gc();
        }
    }

    //截图一张保存
    private void ImageCapture(Rect rect,String path) throws UiObjectNotFoundException{
        //新建一张图片
        Bitmap image = BitmapFactory.decodeFile(path);
        //通过坐标截取图片
        image = image.createBitmap(image,rect.left,rect.top,rect.width(),rect.height());
        //保存图片
        saveBitmap(image,"Image");
    }

    private void saveBitmap(Bitmap bitmap,String name){
        FileOutputStream out = null;
        try{
            out = new FileOutputStream("/mnt/sdcard/"+name+".jpg");
            if(out != null){
                //压缩图片
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
                out.close();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //获取某点的像素值
    public int getPixel(int x,int y){
        String path = "/mnt/sdcard/image11.png";
        File file = new File(path);
        UiDevice.getInstance().takeScreenshot(file);
        Bitmap m = BitmapFactory.decodeFile(path);
        int color = m.getPixel(x,y);
        return color;
    }

    //Environment类提供了Android环境变量的访问和获取
    public void environment(){
        Log.d(TAG, "test1: "+Environment.isExternalStorageEmulated());//是否有外部存储设备
        Log.d(TAG, "test1: "+Environment.isExternalStorageRemovable());//外部存储设备是否可移除
        Log.d(TAG, "test1: "+Environment.getDataDirectory());//获取数据目录
        Log.d(TAG, "test1: "+Environment.getDownloadCacheDirectory());//获取下载缓存目录
        Log.d(TAG, "test1: "+Environment.getExternalStorageState());//获取外部存储状态
        Log.d(TAG, "test1: "+Environment.getRootDirectory());//获取root目录
        //列出目录下的所有文件
        File downFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //新建文件列表，用于存储所有文件的文件名
        File[] files = downFile.listFiles();
        for (File f:files) {
            Log.d(TAG, "test1: "+f.getAbsolutePath()); //打印绝对路径
        }
    }

    //启动应用
    protected void startApp(String packageName,String className) {
        Context context = InstrumentationRegistry.getContext();
        Intent launchIntent = new Intent();
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动应用
        launchIntent.setComponent(new ComponentName(packageName,className));
        context.startActivity(launchIntent);
        systemWait(8);
    }

    //清理图片
    protected static void clearFiles(String filePath){
        File scFileDir = new File(filePath);
        File TrxFiles[] = scFileDir.listFiles();
        for(File curFile:TrxFiles ){
            curFile.delete();
        }
    }

    //得到当前路径下文件
    protected static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (File file1:tempList) {
            if (file1.isFile()) {
                files.add(file1.toString());
            }
        }
        return files;
    }

    //根据坐标
    protected void clickByCoordinate(int x, int y, int waitTime){
        uiDevice.click(x,y);
        uiDevice.waitForIdle();
        systemWait(waitTime);
    }

    //text和Clazz定位
    protected void clickByTextAndClazz(String text,String clazz){
        UiObject2 obj  = uiDevice.findObject(By.text(text).clazz(clazz));
        obj.click();
        waitForIdle(1);
    }

    //判断模拟器app状态并重启进程
    protected void stopApp(){
        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
        UiObject2 settings = uiDevice.findObject(By.text("设置"));
        settings.clickAndWait(Until.newWindow(),3000);
        systemWait(SHORT_WAIT);
        clickByTextAndClazz("应用","android.widget.TextView");
        systemWait(SHORT_WAIT);
        UiObject2 ban = uiDevice.findObject(By.text("伴伴").res("com.android.settings:id/app_name")
                .pkg("com.android.settings"));
        ban.clickAndWait(Until.newWindow(),3000);
        systemWait(SHORT_WAIT);
        if(uiDevice.hasObject(By.text("应用信息").res("android:id/action_bar_title"))){
            UiObject2 forceStop = uiDevice.findObject(By.text("强行停止").res("com.android.settings:id/left_button"));
            if(forceStop.isFocusable()){
                forceStop.clickAndWait(Until.newWindow(),3000);
                systemWait(SHORT_WAIT);
                UiObject2 sure = uiDevice.findObject(By.text("确定"));
                sure.click();
                systemWait(SHORT_WAIT);
            }else {
                uiDevice.pressHome();
            }
        }
        uiDevice.pressHome();
    }

    //判断在app
    protected void appIsRunning(){
        if(uiDevice.hasObject(By.text("广场").clazz("android.widget.TextView"))){
            uiDevice.waitForIdle();
        }else{
            stopApp();
            startApp(x86_PACKNAME,x86_CLASSNAME);
        }
    }

    //验证
    protected void Verification(String str){
        UiObject2 rec = uiDevice.findObject(By.text(str));
        Assert.assertEquals(str, rec.getText());
        systemWait(2);
    }

    //页面Idle + 等待时间限制
    protected void waitForIdle(int time) {
        if (time > 3) {
            systemWait(3);
        }
        systemWait(time);
        uiDevice.waitForIdle();
    }

    //滚动
    public void scroll() throws UiObjectNotFoundException{
        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        UiSelector data = new UiSelector().text("个人资料");
        UiSelector gift = new UiSelector().text("收到的礼物");
        UiSelector video = new UiSelector().text("视频动态");
        UiSelector[] list = {data,gift,video};
        for(int i = 0;i<=list.length-1;i++){
            scroll.scrollIntoView(list[i]);
            systemWait(1);
        }
    }

    // 滚动到指定位置
    protected void scrollToView(String text) throws UiObjectNotFoundException{
        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        UiSelector reception = new UiSelector().text(text);
        scroll.scrollIntoView(reception);
        if(reception != null){
            Verification(text);
        }
    }


}