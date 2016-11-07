package tv.fun.appstoretest.common;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/8/15.
 */
public class Common {
    public UiDevice device;
    public int timeout = 60;
    public int nextPageTime = 3000;
    public int sleepInterval = 500;
    public String appStoreIconName = "应用市场";
    public String tvMasterIconName = "电视助手";
    public String myAppIconName = "我的应用";
    public String myAppCountUnit = "个)";
    public String[] tabs = {"推荐", "游戏", "娱乐", "生活", "教育"};
    public String appstorePackage = "tv.fun.appstore";
    public String[] appStoreTabs = {"推荐", "游戏", "娱乐", "生活", "教育", "应用管理"};
    public String[] launcherTabs = {"电视", "视频", "体育", "少儿", "应用", "设置"};
    public String appTab = "应用";
    public String launcherTabID = "com.bestv.ott:id/tab_title";
    public String networkIconIDInPopup = "com.bestv.ott:id/network";//launcher悬浮框上网络设置按钮的resource id
    public static boolean resultFlag = true;
    public static String resultStr = "";
    public long execTime;
    public String runTool = "Auto";//Studio or Auto
    public String errorLog = "";

    @Before
    public void setup() throws RemoteException, IOException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        execTime = Utils.getCurSecond();
        resultStr = "";
        resultFlag = true;
        if(!device.isScreenOn()){
            device.wakeUp();
        }
        executeAdbShellCommond("am force-stop tv.fun.appstore");
        device.pressHome();
    }

    @After
    public void tearDown() throws IOException {
//        device.pressHome();
        executeAdbShellCommond("am force-stop tv.fun.appstore");
    }

    /**
     * Find Element By Resource ID
     *
     * @param resouceID
     * @return obj
     */
    public UiObject findElementByID(String resouceID){
        UiObject obj = device.findObject(new UiSelector().resourceId(resouceID));
        return obj;
    }

    /**
     * Find Element By Resource ID
     *@param textStr
     * @param resouceID
     * @return obj
     */
    public UiObject findElementByText(String textStr, String resouceID){
        UiObject obj = device.findObject(new UiSelector().text(textStr).resourceId(resouceID));
        return obj;
    }

    /**
     * Find Element By Resource ID
     * @param  type
     * @param locator
     * @param index
     * @return obj
     */
    public UiObject findElementByIndex(String type, String locator, int index){
        UiObject obj = null;
        if(type.equalsIgnoreCase("id")){
            obj = device.findObject(new UiSelector().resourceId(locator).index(index));
        }else if(type.equalsIgnoreCase("class")){
            obj = device.findObject(new UiSelector().className(locator).index(index));
        }
        return obj;
    }

    /**
     * Find Element By Class Name
     *
     * @param className
     * @return obj
     */
    public UiObject findElementByClass(String className){
        UiObject obj = device.findObject(new UiSelector().className(className));
        return obj;
    }

    /**
     * Find child node of Element By Resource ID
     *
     * @param resID
     * @param  parentResID
     * @return obj
     */
    public UiObject findChildNodeOfElementByID(String parentResID, String resID) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByID(parentResID);
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID));
        return childObj;
    }

    /**
     * Find child node of Element By Class Name
     *
     * @param value
     * @param  parentClass
     * @return obj
     */
    public UiObject findChildNodeOfElementByClass(String parentClass, String value) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByClass(parentClass);
        UiObject childObj = parentObj.getChild(new UiSelector().className(value));
        return childObj;
    }

    /**
     * Find child node of Lisy Element By Class Name and index
     *
     * @param parentClass
     * @param  resID
     * @param indexf
     * @return obj
     */
    public UiObject findChildNodeOfListElementByClass(String parentClass, String resID, int indexf) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByClass(parentClass);
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID).index(indexf));
        return childObj;
    }

    /**
     * Find Element By Resource ID
     * @param parentObj
     * @param resID
     * @param elemIndex
     * @return obj
     */
    public UiObject findChildNodeOfListElementByIDAndIndex(UiObject parentObj, String resID, int elemIndex) throws UiObjectNotFoundException {
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID).index(elemIndex));
        return childObj;
    }

    /**
     * 适用于所有系统
     * @param commondStr
     */
    public void executeAdbShellCommond(String commondStr) throws IOException {
        Runtime.getRuntime().exec(commondStr);
    }

    /**
     * 仅适用于api21以上系统（5.0以上）
     * @param commondStr
     */
    public void executeAdbCommond(String commondStr) throws IOException {
        device.executeShellCommand(commondStr);
    }

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
     * 按遥控器向上键
     */
    public void moveToUp(){
        device.pressDPadUp();
    }

    /**
     * 连续按遥控器向上键
     *
     * step  连续向右移的次数
     */
    public void moveToUpForMultiple(int step){
        for(int i=1; i<=step; i++){
            device.pressDPadUp();
        }
    }

    /**
     * 按遥控器右键
     */
    public void moveToRight(){
        device.pressDPadRight();
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
     * 按遥控器左键
     */
    public void moveToLeft(){
        device.pressDPadLeft();
    }

    /**
     * 连续按遥控器左键
     *
     * step  连续向左移的次数
     */
    public void moveToLeftForMultiple(int step){
        if(step<0){
            step=-step;
        }
        for(int i=1; i<=step; i++){
            device.pressDPadLeft();
        }
    }

    /**
     * 按遥控器向下键
     *
     * 向下移
     */
    public void moveToDown(){
        device.pressDPadDown();
    }

    /**
     * 连续按遥控器下键
     *
     * step  连续向下移的次数
     */
    public void moveToDownForMultiple(int step){
        if(step<0){
            step=-step;
        }
        for(int i=1; i<=step; i++){
            device.pressDPadDown();
        }
    }

    /**
     * 按遥控器Menu键
     */
    public void menu(){
        device.pressMenu();
    }

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
    public void waitForElementPresentByIDAndText(String locator, String textStr)
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
     * @param className
     *            an element locator
     *@param textStr
     *            text of element
     * @throws InterruptedException
     */
    public void waitForElementPresentByClassAndText(String className, String textStr)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + className + "> with text (" + textStr + ")" );
                break;
            }
            if (device.findObject(new UiSelector().text(textStr).className(className)).exists()) {
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
     * Wait for an text note present. The element on the page exists in
     * the pre-page, waiting for the element not exist.
     *
     * @param textStr
     *            a text
     * @throws InterruptedException
     */
    public void waitForTextNotPresent(String textStr)
            throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for text not present <"
                        + textStr + ">");
                break;
            }
            if (!device.findObject(new UiSelector().text(textStr)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Use to get the step need to move from the first tab to target tab
     *
     * @param targetTab
     * @return
     */
    public int stepFrom1stTabToTargetTab(String[] tablist, String currentTab, String targetTab){
        HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
        int tabCount = tablist.length;
        for(int i=0; i<tabCount; i++){
            tabMap.put(tablist[i], i+1);
        }
        int startStep = tabMap.get(currentTab);
        int targetTabStep = tabMap.get(targetTab);
        int step = targetTabStep - startStep;
        return step;
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToTargetTab(String[] tablist, String targetTab, String tabResouceID, int step) throws UiObjectNotFoundException {
        UiObject tab = device.findObject(new UiSelector().resourceId(tabResouceID).text(targetTab));
        if(!tab.isSelected()){
            moveToUpForMultiple(4);//Move to navBar to avoid that the current focusot in narBar
            if(tabResouceID.equalsIgnoreCase(launcherTabID)){
                if(findElementByID(networkIconIDInPopup).exists()){
                    device.pressDPadDown();
                }
            }
            UiObject currentTabObj = device.findObject(new UiSelector().resourceId(tabResouceID).selected(true));
            String currentTab = currentTabObj.getText();
            int needStep = stepFrom1stTabToTargetTab(tablist, currentTab, targetTab);
            if(needStep>0){
                moveToRightForMultiple(needStep);
            }else {
                moveToLeftForMultiple(needStep);
            }
        }
    }


    /**
     * Verify not the string.
     *
     * @param actual
     * @param unexpected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotString(String actual, String unexpected)
            throws IOException {
        if (!unexpected.equals(actual)) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Not expected [" + unexpected + "] is displayed. ";
            return false;
        }
    }

    /**
     * Verify the string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyString(String failMsg, String actual, String expected)
            throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, expected.equalsIgnoreCase(actual));
        } else {
            if (expected.equalsIgnoreCase(actual)) {
                resultFlag = true;
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg+";";
                } else {
                    resultStr += "Expected [" + expected + "] but actual [" + actual
                            + "]; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the string include another string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyIncludeString(String failMsg, String expected, String actual)
            throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, expected.contains(actual));
        } else {
            if (expected.indexOf(actual) > -1) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected [" + expected + "] contains actual [" + actual
                        + "], but actually not contain; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNumber(String failMsg,int actual, int expected) throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actual == expected);
        } else {
            if (actual == expected) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected [" + expected + "] but actual [" + actual
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotNumber(String failMsg,int actual, int expected) throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actual != expected);
        } else {
            if (actual != expected) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected not [" + expected + "] but actual [" + actual
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the input parmeter is true.
     *
     * @param failMsg
     * @param testFlag
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyTrue(String failMsg, Boolean testFlag) throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, testFlag);
        } else {
            if (testFlag) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg+";";
                } else {
                    resultStr += "The return of [" + testFlag + "] is False; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element present.
     *
     * @param obj
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementPresent(String failMsg, UiObject obj) throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, obj.exists());
        } else {
            if (obj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg+";";
                } else {
                    resultStr += "Element [" + obj + "] is NOT present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element present.
     *
     * @param obj
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementNotPresent(UiObject obj, String failMsg) throws IOException {
        Boolean verifyFlag =true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertFalse(failMsg, obj.exists());
        } else {
            if (!obj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg+";";
                } else {
                    resultStr += "Element [" + obj + "] is expected Not present, but actually present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }
//
//    public void evaluate() throws Throwable {
//        List<Throwable> errors = new ArrayList<Throwable>();
//        try {
//            next.evaluate();
//        } catch (Throwable e) {
//            errors.add(e);
//        }
//    /**
//     * 解析XML文件
//     *
//     */
//    public String parserXml() throws DocumentException {
//        String fileXpath = "//app/testCases.xml";
//        File inputXml = new File(fileXpath);
//        SAXReader saxReader = new SAXReader();
//        String testKind = "";
//        try {
//            Document document = saxReader.read(fileXpath);
//            Element testCases = document.getRootElement();
//            for (Iterator i = testCases.elementIterator(); i.hasNext();) {
//                Element testCase = (Element) i.next();
//                for (Iterator j = testCase.elementIterator(); j.hasNext();) { // 遍例节点
//                    Element node = (Element) j.next();
//                    System.out.println(node.getName() + ":" + node.getText());
//                    testKind=node.getText();
//                }
//
//            }
//        } catch (DocumentException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("dom4j parserXml");
//        return testKind;
//    }

}