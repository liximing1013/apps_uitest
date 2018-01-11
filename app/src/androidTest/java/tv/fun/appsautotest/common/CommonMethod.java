package tv.fun.appsautotest.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

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
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lixm on 2017/12/1.
 */

public class CommonMethod extends UiActions {
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
//    }

    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
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

    public void openTabFromLauncherHomeByText(UiDevice device, UiObject2 tabText) {
        tabText.getParent().click();
        sleep(3000);
        device.pressEnter();
        device.waitForIdle();
        sleep(2000);
    }

    //列表页随机选择电影
    public void randomPlayFilm(){
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

    //验证是否进入媒体页
    public void verifyEnterDetailsPage(long time) {
        getDevice().wait(Until.findObject(By.res("com.bestv.ott:id/detail_karma")), time*1000);
        if (getDevice().hasObject(By.text("全屏").res("com.bestv.ott:id/discripse"))) {
            output("Enter Details Page Success");
        } else {
            output("Enter Details Page Failed");
            takeScreenToFile(getDevice(),"DetailsPage");
            Assert.assertTrue(false);
        }
    }

    //视频播放时间,获取时间轴时间转化为秒，然后等待时间替代播放时间
    public void playVideoTime(){
        try {
            pressEnter(1);//
            verifyEnterDetailsPage(15);
            pressLeft(1);
            UiObject2 tryWatch = getDevice().findObject(By.text("精彩预告，付费看正片").res("com.bestv.ott:id/watch_try_text"));
            if(tryWatch != null){
                pressEnter(1);
                pressUp(1);
                pressEnter(1);
                UiObject2 totalTime = getDevice().findObject(By.res("com.bestv.ott:id/time_total"));
                int endTime = timeTrans(totalTime.getText());
                pressEnter(1);
                systemWait(endTime+30);
            }else {
                pressEnter(2);
                UiObject2 totalTime = getDevice().findObject(By.res("com.bestv.ott:id/time_total"));
                int endTime = timeTrans(totalTime.getText());
                pressEnter(1);
                systemWait(endTime+30);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //将xx:xx:xx格式的时间转化为xxx秒
    private int timeTrans(String sTime){
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
    public void waitForObjExists(String res,long time){
        UiObject obj = uiDevice.findObject(new UiSelector().resourceId(res));
        obj.waitForExists(time);
        if(obj.exists()){
            return ;
        }
        sleep(1000);
    }

    //等待对象消失
    public void waitUntilObjGone(String str,int time){
        UiObject obj = uiDevice.findObject(new UiSelector().resourceId(str));
        obj.waitUntilGone(time);//在一定时间内判断控件是否消失
        if(!obj.exists()){
            return;
        }
        sleep(1000);
    }

    //等待页面刷新结果
    public void waitPageRefresh(String text1,String text2) throws UiObjectNotFoundException{
        UiObject object = new UiObject(new UiSelector().text(text1));
        object.clickAndWaitForNewWindow(); //点击等待页面跳转
        UiObject object1 = new UiObject(new UiSelector().className(text2));//需要出现的页面元素
        while (!object1.exists()){
            UiObject object2 = new UiObject(new UiSelector().className(text2));
            if(!object2.exists()){
                systemWait(1);
            }
        }
        UiActions.takeScreenToFile(uiDevice,"object");
    }

    //获取当前时间
    protected String getNow() {//获取当前时间
        Date time = new Date();
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(time);
        return timeNow;
    }

    //播放时间
    public void playVideo(int playtime){
        try {
            UiObject2 funshionPlayer = uiDevice.findObject(By.clazz
                    ("com.funshion.player.play.funshionplayer.VideoViewPlayer").pkg("com.bestv.ott"));
            if(funshionPlayer != null){
                systemWait(playtime*1000);
            }else
                Assert.assertTrue("未找到播放器",false);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    //判断电视是否是会员（launcher）
    public String checkVip() {
        UiObject2 vipTip = uiDevice.findObject(By.res("com.bestv.ott:id/vip_tip")
                .clazz("android.widget.TextView").pkg("com.bestv.ott"));
        return vipTip.getText();
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
    public  static void AssertBitmapEqual(Bitmap bitmap0, Bitmap bitmap1){

        Assert.assertEquals(100, ImageCompare(bitmap0,bitmap1));
    }
    /**
     * 百分之零图片相同断言
     */
    public  static void AssertBitmapNotEqual(Bitmap bitmap0, Bitmap bitmap1){

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
    public static int ImageCompare(Bitmap bitmap0, Bitmap bitmap1) {

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
        screenShot("test");//截图
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

    //获取launcher首页视频Tab卡片数=23
    public void getCardCount() throws UiObjectNotFoundException {
        UiObject grid = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/scrollview")
                .packageName("com.bestv.ott"));
        UiObject card = grid.getChild(new UiSelector().className("android.widget.FrameLayout"));
        int tabShowCount = card.getChildCount();
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

    //无限循环安装卸载应用
    public void test11() throws UiObjectNotFoundException{
        for(int i =0;i<=50;i++){
            UiObject aqiyi = uiDevice.findObject(new UiSelector().text("爱奇艺TV版"));
            aqiyi.clickAndWaitForNewWindow();
            uiDevice.pressDPadCenter();
            systemWait(3);
            UiObject ins = uiDevice.findObject(new UiSelector().text("安装").resourceId("tv.fun.appstore:id/titleContainer"));
            if(ins.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(20);
            }
            UiObject ins1 = uiDevice.findObject(new UiSelector().text("打开").className("android.widget.Button"));
            if ("打开".equals(ins1.getText())) {
                pressBack(1);
            }
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("我的应用")), 15000);
            uiDevice.pressMenu();
            systemWait(3);
            pressRight(1);
            pressEnter(2);
            pressBack(1);
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

    public boolean assertLocation(String locat) throws UiObjectNotFoundException{
        UiObject hotLocat  = new UiObject(new UiSelector().text(locat));
        int x = hotLocat.getBounds().centerX();
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+x);
        int H = UiDevice.getInstance().getDisplayHeight();
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+H);
        int W = UiDevice.getInstance().getDisplayWidth(); //1920
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+W);
        int max = (int)(W+W*0.05);
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+max);
        int min = (int)(W-W*0.05);
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+min);
        return (max>x & x>min);
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
    public void ImageCapture(Rect rect,String path) throws UiObjectNotFoundException{
        //新建一张图片
        Bitmap image = BitmapFactory.decodeFile(path);
        //通过坐标截取图片
        image = image.createBitmap(image,rect.left,rect.top,rect.width(),rect.height());
        //保存图片
        saveBitmap(image,"Image");
    }

    public void saveBitmap(Bitmap bitmap,String name){
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

}
