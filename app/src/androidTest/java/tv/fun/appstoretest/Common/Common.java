package tv.fun.appstoretest.Common;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;


/**
 * Created by liuqing on 2016/8/15.
 */
public class Common {
    public UiDevice device;
    public String appStoreIconName = "应用市场";
    public String tvMasterIconName = "电视助手";
    public String myAppIconName = "我的应用";
    public String myAppCountUnit = "个)";
    public String[] tabs = {"推荐", "游戏", "娱乐", "生活", "教育"};

    @Before
    public void setup() throws RemoteException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
       if(!device.isScreenOn()){
           device.wakeUp();
       }
//        device.pressHome();
    }

    @After
    public void tearDown() {
//        device.pressHome();
        executeADBCommond("am force-stop tv.fun.appstore");
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

    public void executeADBCommond(String commondStr)  {
        try {
            device.executeShellCommand(commondStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
