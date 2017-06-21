package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appstoretest.common.ChildrenPage;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2017/5/4.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVChildrenList extends ChildrenPage {

    /**
     * Test that the UI of multiple tabs page displays correctly
     * <p>
     * 多tab列表页UI显示正确
     */
    @Test
    public void Child_List_01_testMultipleTabPageUIDisplay() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            //断言
            UiObject searchIconObj = findElementByID("tv.fun.children:id/search");
            UiObject filterIconObj = findElementByID("tv.fun.children:id/filter");
            UiObject tabsObj = findElementByID("tv.fun.children:id/tabContainer");
            UiObject firstTabObj = tabsObj.getChild(new UiSelector().className("android.widget.Button").index(0));
            int tabCount = tabsObj.getChildCount();
            verifyElementPresent("", searchIconObj);
            verifyElementPresent("", filterIconObj);
            verifyElementPresent("", tabsObj);
            verifyNumberLarger("", tabCount, 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can clicking any tab without changing
     * <p>
     * 点击tab无异常
     */
    @Test
    public void Child_List_06_testMultipleTabPageUIDisplay() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(3);
            UiObject tabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("全部"));
            tabObj.click();
            //断言
            UiObject tabListObj = findElementByID("tv.fun.children:id/listView");
            UiObject scrollBarObj = findElementByID("android:id/tv_fun_scrollbar");
            int videoCountInList = tabListObj.getChildCount();
            verifyElementPresent("", tabListObj);
            verifyElementPresent("", scrollBarObj);
            verifyNumberLarger("", videoCountInList, 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the video info displays correctly
     * <p>
     * 影片简介信息显示正确
     */
    @Test
    public void Child_List_20_testVideoInfo() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(3);
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String infoStr = videoInfoObj.getText();
            do {
                moveRight();
                videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                infoStr = videoInfoObj.getText();
            } while (!infoStr.contains("集)"));
            //断言
            UiObject selectedVideo = device.findObject(new UiSelector().resourceId("tv.fun.children:id/title").selected(true));
            videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String currentVideoName = selectedVideo.getText();//宝宝巴士之神奇简笔画
            String videoInfo = videoInfoObj.getText();//宝宝巴士之神奇简笔画 (全30集)　8.0分　2016/内地/早教　"做一个天才的小画家吧"
            String videoNameInInfo = videoInfo.split(" ")[0];
            String otherInfo = videoInfo.split(" ")[1];
            String jujiInfo = otherInfo.split("　")[0];
            String gradeInfo = otherInfo.split("　")[1];
            String timeInfo = otherInfo.split("　")[2];
            String shortInfo = otherInfo.split("　")[3];
            int shortInfoSize = shortInfo.length();
            verifyString("", videoNameInInfo, currentVideoName);
            verifyIncludeString("", jujiInfo, "(");
            verifyIncludeString("", jujiInfo, ")");
            verifyIncludeString("", gradeInfo, "分");
            verifyIncludeString("", timeInfo, "/");
            verifyIncludeString("", shortInfo, "\"");
            verifyNumberLarger("", shortInfoSize, 3);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
    * Test that the topic info displays correctly
    *
    * 专题的简介信息显示正确
    */
    @Test
    public void Child_List_21_testTopicInfo() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(3);
            UiObject topicTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            do {
                moveRight();
            } while (!topicTabObj.isSelected());
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //断言
            UiObject selectedTopic = device.findObject(new UiSelector().resourceId("tv.fun.children:id/title").selected(true));
            UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String currentTopicName = selectedTopic.getText();
            String topicInfo = topicInfoObj.getText();//好习惯只需3分钟　"时间虽短 剧情上佳"
            String topicNameInInfo = topicInfo.split("　")[0];
            String shortInfo = topicInfo.split("　")[1];
            int infoLength = shortInfo.length();
            verifyString("", topicNameInInfo, currentTopicName);
            verifyIncludeString("", shortInfo, "\"");
            verifyNumberLarger("", infoLength, 3);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
   * Test that the list page can response to Back button
   *
   * 列表页可以正常响应Back键操作
   */
    @Test
    public void Child_List_28_testPressBackInListPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            //按遥控器Back键
            back();
            //断言
            verifyElementNotPresent("", "tv.fun.children:id/filter");
            verifyElementPresent("", "tv.fun.children:id/tab_title_setting");
            verifyElementPresent("", "tv.fun.children:id/tab_title_brand");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
 * Test that the list page can response to Home key
 *
 * 列表页可以正常响应Home键操作
 */
    @Test
    public void Child_List_29_testPressHomeInListPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            //按遥控器Home键
            home();
            //断言
            waitForElementPresentByID("com.bestv.ott:id/title");
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", launcherVideoTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
  * Test that the list page can response to Menu key
  *
  * 列表页可以正常响应Menu键操作
  */
    @Test
    public void Child_List_30_testPressMenuInListPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            waitForElementPresentByID("tv.fun.children:id/tab_title_children");
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            //按遥控器Menu键
            menu();
            //断言
            UiObject iconInMenuPop = findElementByID("tv.fun.children:id/menu_icon");
            UiObject textInMenuPop = findElementByID("tv.fun.children:id/menu_text");
            String btnNameInMenu = textInMenuPop.getText();
            verifyElementPresent("", iconInMenuPop);
            verifyElementPresent("", textInMenuPop);
            verifyString("", btnNameInMenu, "搜索");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
 * Test that can goto search page by pressing menu button in list page
 *
 * 列表页可以通过Menu键进入搜索页面
 */
    @Test
    public void Child_List_31_testGotoSearchPageByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            //按遥控器Menu键
            menu();
            UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
            searchBtnInMenu.clickAndWaitForNewWindow();
            //断言
            UiObject inputObj = findElementByID("tv.fun.children:id/search_input");
            String textInInput = inputObj.getText();//
            UiObject searchResultTitleObj = findElementByID("tv.fun.children:id/resultTitle");
            String suggestSearchTitle = searchResultTitleObj.getText();
            UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
            verifyElementPresent("", inputObj);
            verifyString("", textInInput, "输入首字母或全拼");
            verifyElementPresent("", searchResultTitleObj);
            verifyString("", suggestSearchTitle, "大家都在搜：");
            verifyElementPresent("", searchResultObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
     * Test that can cancel collecting video or topic by Menu
     *
     * 在列表页中，可以取消收藏影片/专题
     */
    @Test
    public void Child_List_32_testCollectVideoByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(3);
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //获取当前选中影片的名称
            UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String videoInfo = videoInfoObj.getText();
            String currentVideoName = videoInfo.split(" ")[0];
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                menu();
            }
            UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnInMenu.click();
            back();
            //进入我的收藏页面
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            //断言
            UiObject collectTabObj = findElementByID("tv.fun.children:id/favorite");
            UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
            UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            UiObject firstCollectStatusObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/timestamp"));
            String firstCollectName = firstCollectNameObj.getText();
            String firstCollectStatus = firstCollectStatusObj.getText();
            verifyTrue("", collectTabObj.isSelected());
            verifyString("", firstCollectName, currentVideoName);
            verifyString("", firstCollectStatus, "已收藏");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
* Test that can cancel collecting video or topic by Menu
*
* 在列表页中，可以取消收藏影片/专题
*/
    @Test
    public void Child_List_33_testCancelCollectVideoByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(3);
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //获取当前选中影片的名称
            UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String videoInfo = videoInfoObj.getText();
            String currentVideoName = videoInfo.split(" \\(")[0];
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
//                moveDown();
                moveRight();
                menu();
            }
            UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnInMenu.click();
            back();
            //进入我的收藏页面
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
            UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentVideoName);
            back();
            //再次进入列表页，取消收藏
            multipleTabObj.clickAndWaitForNewWindow();
            moveRightForMultiple(3);
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //获取当前选中影片的名称
            UiObject selectedVideoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String selectedVideoInfo = selectedVideoInfoObj.getText();
            String selectedVideoName = selectedVideoInfo.split(" \\(")[0];
            menu();
            if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                firstCollectName = selectedVideoName;
                collectBtnTwoInMenu.click();
                moveDown();
                menu();
            }
            UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
            cancelCollectBtnInMenu.click();
            back();
            //断言
            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
            if (findElementByID("tv.fun.children:id/empty").exists()) {
                verifyElementNotPresent("", "tv.fun.children:id/listView");
            } else {
                UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstActualCollectName = firstCollectNameObj.getText();
                verifyNotString("", firstActualCollectName, selectedVideoName);
                verifyNotString("", firstActualCollectName, firstCollectName);
                verifyString("", selectedVideoName, firstCollectName);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
   * Test that can collect topic by Menu
   *
   * 可以正常收藏大背景专题
   */
    @Test
    public void Child_List_42_testCollectTopicByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            //向下选中任一专题
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //获取当前选中专题的名称
            UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String topicInfo = topicInfoObj.getText();
            String currentTopicName = topicInfo.split("　")[0];
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                moveRight();
                menu();
            }
            UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnInMenu.click();
            back();
            //进入我的收藏页面
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentTopicName);
            back();
            //再次进入列表页，取消收藏
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            //向下选中任一影片
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(3);
                moveDown();
            }
            //获取当前选中影片的名称
            UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String selectedTopicInfo = selectedTopicInfoObj.getText();
            String selectedTopicName = selectedTopicInfo.split(" ")[0];
            menu();
            if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnTwoInMenu.click();
                moveDown();
                menu();
            }
            UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
            cancelCollectBtnInMenu.click();
            back();
            //断言
            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
            UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstActualCollectName = firstCollectNameObj.getText();
            verifyNotString("", firstActualCollectName, selectedTopicName);
            verifyNotString("", firstActualCollectName, firstCollectName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /*
* Test that can cancel collecting topic by Menu
*
* 可以取消收藏大背景专题
*/
    @Test
    public void Child_List_43_testCancelCollectTopicByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(5);
                moveDown();
            }
            //获取当前选中专题的名称
            UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String topicInfo = topicInfoObj.getText();
            String currentTopicName = topicInfo.split(" ")[0];
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                moveRight();
                menu();
            }
            UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnInMenu.click();
            back();
            //进入我的收藏页面
            UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentTopicName);
            back();
            //再次进入列表页，取消收藏
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            //向下选中任一专题
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(5);
                moveDown();
            }
            //获取当前选中专题的名称
            UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String selectedTopicInfo = selectedTopicInfoObj.getText();
            String selectedTopicName = selectedTopicInfo.split("　")[0];
            menu();
            if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnTwoInMenu.click();
                moveDown();
                menu();
            }
            UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
            cancelCollectBtnInMenu.click();
            back();
            //断言
            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
            UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstActualCollectName = firstCollectNameObj.getText();
            verifyNotString("", firstActualCollectName, selectedTopicName);
            verifyNotString("", firstActualCollectName, firstCollectName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 大背景专题页可以正确响应Menu键操作
     */
    @Test
    public void Child_List_44_testPressMenuInTopicPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            enter();
            UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
            verifyElementPresent("", collectBtnInTopic);
            moveRight();
            //按遥控器Menu键
            menu();
            //断言
            UiObject menuPopupObj = findElementByID("tv.fun.children:id/menu_container");
            UiObject collectBtnObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
            UiObject searchBtnObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
            verifyElementPresent("", menuPopupObj);
            verifyElementPresent("", searchBtnObj);
            verifyElementPresent("", menuPopupObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 大背景专题页可以正确响应Back键操作
     */
    @Test
    public void Child_List_45_testPressBackInTopicPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            Boolean f = verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            enter();
            waitForElementPresentByID("com.bestv.ott:id/special_player_progress");
            if (findElementByID("com.bestv.ott:id/special_player_progress").exists()) {
                UiObject videoListObj = findElementByID("com.bestv.ott:id/special_play_list");
                boolean f11 = verifyElementPresent("", videoListObj);
            } else {
                UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                verifyElementPresent("", collectBtnInTopic);
            }
            //按遥控器Back键
            back();
            //断言
            UiObject filterObj = findElementByID("tv.fun.children:id/filter");
            UiObject suggestTabObj = device.findObject(new UiSelector().className("android.widget.Button").text("推荐"));
            verifyElementPresent("", suggestTabObj);
            verifyElementPresent("", filterObj);
            verifyElementPresent("", "tv.fun.children:id/listView");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 大背景专题页可以正确响应Home键操作
     */
    @Test
    public void Child_List_46_testPressHomeInTopicPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            enter();
            UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
            verifyElementPresent("", collectBtnInTopic);
            //按遥控器Home键
            home();
            //断言
            waitForElementPresentByID("com.bestv.ott:id/title");
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", launcherVideoTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在大背景专题页可以通过menu键进入搜索页面
     */
    @Test
    public void Child_List_47_testGotoSearchPageFromTopicPageByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            enter();
            UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
            verifyElementPresent("", collectBtnInTopic);
            moveRight();
            //按遥控器Menu键
            menu();
            UiObject searchBtnObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
            searchBtnObj.clickAndWaitForNewWindow();
            //断言
            UiObject inputObj = findElementByID("tv.fun.children:id/search_input");
            String textInInput = inputObj.getText();
            UiObject searchResultTitleObj = findElementByID("tv.fun.children:id/resultTitle");
            String suggestSearchTitle = searchResultTitleObj.getText();
            UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
            verifyElementPresent("", inputObj);
            verifyString("", textInInput, "输入首字母或全拼");
            verifyElementPresent("", searchResultTitleObj);
            verifyString("", suggestSearchTitle, "大家都在搜：");
            verifyElementPresent("", searchResultObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在大背景专题页可以通过menu键收藏影片
     */
    @Test
    public void Child_List_48_1_testCollectVideoInTopicPageByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren(childrenTabName);
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            enter();
            UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
            verifyElementPresent("", collectBtnInTopic);
            moveDown();
            UiObject briefInfo = findElementByID("tv.fun.children:id/briefContainer");
            String briefInfoStr = briefInfo.getText();
            String videoNameInBrief = briefInfoStr.split(" \\(")[0];//小蜜蜂 第一季 (全40集)　8.1分　2016/英国/早教　"萌萌哒的小蜜蜂们"
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                back();
                moveRightForMultiple(5);
                moveDown();
                enter();
                menu();
            }
            UiObject collectBtnObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnObj.clickAndWaitForNewWindow();
            back();
            back();
            //断言
            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
            UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstActualCollectNameObj = firstActualCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstActualCollectName = firstActualCollectNameObj.getText();
            verifyString("", firstActualCollectName, videoNameInBrief);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在大背景专题页可以通过menu键取消收藏影片
     */
    @Test
    public void Child_List_48_2_testCancelCollectVideoInTopicPageByMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
            verifyTrue("", currentSelectedTab.isSelected());
            //向下选中任一专题
            moveDown();
            //获取当前选中专题的名称
            UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String topicInfo = topicInfoObj.getText();
            String currentTopicName = topicInfo.split("　")[0];//奇趣大自然　"好多毛蓬蓬的小动物啊"
            //按遥控器Menu键
            menu();
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                moveRight();
                menu();
            }
            UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            collectBtnInMenu.click();
            back();
            //进入我的收藏页面
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
            UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentTopicName);
            //退出我的收藏页面，回到少儿首页
            back();
            //再次进入列表页，取消收藏
            multipleTabObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/filter");
            moveRightForMultiple(5);
            //向下选中任一专题
            moveDown();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                moveUpForMultiple(5);
                moveDown();
            }
            //获取当前选中专题的名称
            UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
            String selectedTopicInfo = selectedTopicInfoObj.getText();
            String selectedTopicName = selectedTopicInfo.split("　")[0];
            menu();
            if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnTwoInMenu.click();
                moveDown();
                menu();
            }
            UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
            cancelCollectBtnInMenu.click();
            back();
            //断言
            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
            UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstActualCollectName = firstCollectNameObj.getText();
            verifyNotString("", firstActualCollectName, selectedTopicName);
            verifyNotString("", firstActualCollectName, firstCollectName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 无tab列表页UI显示正确
     */
    @Test
    public void Child_List_58_testSingleTabPageUI() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject singleTabObj = findElementByID("tv.fun.children:id/children_view_item8");
            singleTabObj.clickAndWaitForNewWindow();

            //断言
            UiObject signleTabPageTitleObj = findElementByID("tv.fun.children:id/listTitleImage");
            UiObject videoListObj = findElementByID("tv.fun.children:id/listView");
            verifyElementPresent("", signleTabPageTitleObj);
            verifyElementPresent("", videoListObj);
            verifyElementNotPresent("", "tv.fun.children:id/tabScrollContainer");
            verifyElementNotPresent("", "tv.fun.children:id/tabScrollContainer");
            verifyElementNotPresent("", "tv.fun.children:id/filter");
            verifyElementNotPresent("", "tv.fun.children:id/tabTitleSection");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 早教学堂页面UI显示正确清晰
     */
    @Test
    public void Child_Early_01_testEarlySchoolPageUI() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            //断言
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            UiObject afterClassObj = findElementByID("tv.fun.children:id/after_class");
            UiObject artClassObj = findElementByID("tv.fun.children:id/art_class");
            UiObject earlyClassObj = findElementByID("tv.fun.children:id/early_class");
            verifyElementPresent("", languageClassObj);
            verifyElementPresent("", afterClassObj);
            verifyElementPresent("", artClassObj);
            verifyElementPresent("", earlyClassObj);

        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 早教学堂页面可以正确响应Back键操作
     */
    @Test
    public void Child_Early_03_testPressBackInEarlySchoolPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            verifyElementPresent("", "tv.fun.children:id/language_class");
            //press back
            back();
            //断言
            UiObject childTabObj = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject settingBtnObj = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("", settingBtnObj);
            verifyElementPresent("", childTabObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 早教学堂页面可以正确响应Home键操作
     */
    @Test
    public void Child_Early_04_testPressHomeInEarlySchoolPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            verifyElementPresent("", "tv.fun.children:id/language_class");
            //press home
            home();
            waitForElementNotPresent("tv.fun.children:id/language_class");
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 早教学堂页面不响应Menu键操作
     */
    @Test
    public void Child_Early_05_testNoMenuResponseInEarlySchoolPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            verifyElementPresent("", "tv.fun.children:id/language_class");
            //press menu
            menu();
            //断言
            Thread.sleep(500);
            verifyElementNotPresent("", "android:id/tv_fun_menu");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 学堂页面UI显示正确
     */
    @Test
    public void Child_Early_09_testEarlyClassPageUI() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            //断言
            Thread.sleep(500);
            UiObject classMenuObj = findElementByID("tv.fun.children:id/video_menu");
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            UiObject playAreaObj = findElementByID("tv.fun.children:id/video_television");
            UiObject playContainerObj = findElementByID("tv.fun.children:id/video_container");
            verifyElementPresent("", classMenuObj);
            verifyElementPresent("", classMenuTitleObj);
            verifyElementPresent("", playAreaObj);
            verifyElementPresent("", playContainerObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 学堂页面可正确响应Back键操作
     */
    @Test
    public void Child_Early_11_testPressBackInClassPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //press back
            back();
            //断言
            Boolean flag = verifyElementPresent("", "tv.fun.children:id/art_class");
            Boolean flag1 = verifyElementPresent("", "tv.fun.children:id/language_class");
            Boolean flag2 = verifyElementNotPresent("", "tv.fun.children:id/video_television");

        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 学堂页面可正确响应Home键操作
     */
    @Test
    public void Child_Early_12_testPressHomeInClassPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //press home
            home();
            waitForElementNotPresent("tv.fun.children:id/video_television");
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 学堂页面不响应Menu键操作
     */
    @Test
    public void Child_Early_03_testNoMenuResponseInClassPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //press menu
            menu();
            //断言
            Thread.sleep(500);
            verifyElementNotPresent("", "android:id/tv_fun_menu");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在全屏播放页面可以通过向上或向下键呼出所有课程
     */
    @Test
    public void Child_Early_36_testAllClassDisplayInFullScreen() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            //按遥控器向下键
            moveDown();

            //断言
            UiObject classListObj = findElementByID("tv.fun.children:id/video_schedule_list");
            UiObject classTitleObj = findElementByID("tv.fun.children:id/video_schedule_title");
            UiObject scheduleObj = findElementByID("tv.fun.children:id/video_schedule_view");
//            UiObject listTitleObj = scheduleObj.getChild(new UiSelector().className("android.widget.TextView").index(1));
//            String tt = listTitleObj.getText();
//            verifyString("", listTitleObj.getText(), "今日课程表");
            verifyElementPresent("", classListObj);
            verifyElementPresent("", classTitleObj);
            verifyElementPresent("", scheduleObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 影片播放时，全屏播放页面可以通过Back键正确切换到小窗口
     */
    @Test
    public void Child_Early_40_testBackFromFullScreenToSmall() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
//            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            //按遥控器Back键
            back();
            //断言
            UiObject classMenuObj = findElementByID("tv.fun.children:id/video_menu");
            UiObject playAreaObj = findElementByID("tv.fun.children:id/video_television");
            UiObject playContainerObj = findElementByID("tv.fun.children:id/video_container");
            verifyElementPresent("", classMenuObj);
            verifyElementPresent("", playAreaObj);
            verifyElementPresent("", playContainerObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 全屏播放页面可以正确响应Home键操作
     */
    @Test
    public void Child_Early_42_testPressHomeInFullScreen() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            waitForElementNotPresent("tv.fun.children:id/loading_icon");
            //按遥控器home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 全屏播放页面可以正确响应Menu键操作
     */
    @Test
    public void Child_Early_44_testPressMenuInFullScreen() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            waitForElementNotPresent("tv.fun.children:id/loading_icon");
            //按遥控器menu键
            menu();
            //断言
            UiObject menuViewObj = findElementByID("tv.fun.children:id/video_clarity_view");
            UiObject menuListObj = findElementByID("tv.fun.children:id/video_clarity_list");
            verifyElementPresent("", menuViewObj);
            verifyElementPresent("", menuListObj);
            UiObject clarityObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_clarity"));
            UiObject playOrderObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_play_order"));
            UiObject playModeObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_video_mode"));
            verifyString("", clarityObj.getText(), "清晰度");
            verifyTrue("", clarityObj.isFocused());
            verifyString("", playOrderObj.getText(), "播放模式");
            verifyString("", playModeObj.getText(), "画面比例");
            moveUpForMultiple(3);
            UiObject clarityOptionList = findElementByID("tv.fun.children:id/video_clarity_clarity");
            int clarityListCount = clarityOptionList.getChildCount();
            verifyTrue("", clarityOptionList.isEnabled());
            verifyNumberLarger("", clarityListCount, 1);
            moveDown();
            UiObject playOrderOptionList = findElementByID("tv.fun.children:id/video_clarity_play_order");
            int playOrderOptionCount = playOrderOptionList.getChildCount();
            verifyNumber("", playOrderOptionCount, 2);
            UiObject firstOptionObj = playOrderOptionList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            UiObject secOptionObj = playOrderOptionList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
            String firstOptionStr = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
            String secOptionStr = secOptionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
            verifyString("", firstOptionStr, "顺序播放");
            verifyString("", secOptionStr, "单集循环");

            //press menu
            menu();
            moveDownForMultiple(3);
            UiObject modeListObj = findElementByID("tv.fun.children:id/video_clarity_video_mode");
            verifyTrue("", modeListObj.isEnabled());
            int modeListCount = modeListObj.getChildCount();
            verifyNumber("", modeListCount, 3);
            UiObject firstModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            UiObject secModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
            UiObject thirdModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(2));
            String firstModenStr = firstModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
            String secModeStr = secModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
            String thirdOptionStr = thirdModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
            verifyString("", firstModenStr, "自适应");
            verifyString("", secModeStr, "16:9");
            verifyString("", thirdOptionStr, "4:3");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 全屏播放页面, Back键可取消Menu弹框
     */
    @Test
    public void Child_Early_45_testCancelMenuPopupByBack() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            waitForElementPresentByID("tv.fun.children:id/children_view_item5");
            //点击早教学堂
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            Boolean flag1 = verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            Boolean flag2 = verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            waitForElementNotPresent("tv.fun.children:id/loading_icon");
            //按遥控器menu键
            menu();
            //Menu框
            if (!findElementByID("tv.fun.children:id/video_clarity_view").exists()) {
                waitForElementNotPresent("tv.fun.children:id/loading_icon");
                menu();
            }
            Thread.sleep(500);
            UiObject menuViewObj = findElementByID("tv.fun.children:id/video_clarity_view");
            UiObject menuListObj = menuViewObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_clarity_list"));
            Boolean flag3 = verifyElementPresent("3 is not pass", menuViewObj);
//            Boolean flag4 = verifyElementPresent("4 is not pass", menuListObj);//有时有问题
            //按遥控器Back键
            back();
            //断言
            Boolean flag5 = verifyElementNotPresent("5 is not pass", "tv.fun.children:id/video_clarity_view");
            Boolean flag6 = verifyElementNotPresent("6 is not pass", "tv.fun.children:id/video_clarity_list");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Back键可取消今日课程表显示
     */
    @Test
    public void Child_Early_63_testCancelClassScheduleByBack() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //Clicking multiple tab card
            UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlySchoolObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/language_class");
            UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
            languageClassObj.clickAndWaitForNewWindow();
            UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
            verifyElementPresent("", classMenuTitleObj);
            //点击小窗口播放
            UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
            smallPlayObj.clickAndWaitForNewWindow();
            moveUp();
            moveLeft();
            UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
            verifyElementPresent("", processObj);
            waitForElementNotPresent(processObj);
            waitForElementNotPresent("tv.fun.children:id/loading_icon");
            //按遥控器向下键
            moveDown();
            waitForElementPresentByID("tv.fun.children:id/video_schedule_list");
            UiObject classListObj = device.findObject(new UiSelector().className("android.widget.TextView").text("今日课程表"));
            UiObject classTitleObj = findElementByID("tv.fun.children:id/video_schedule_title");
            verifyElementPresent("", classListObj);
            verifyElementPresent("", classTitleObj);
            //按遥控器Back键
            back();
            //断言
            verifyElementNotPresent("", "tv.fun.children:id/video_schedule_list");
//            verifyElementNotPresent("", "tv.fun.children:id/video_schedule_title");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 卡通明星页面UI显示正确
     */
    @Test
    public void Child_Star_01_testCartoonStarPageUI() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            //断言
            UiObject startPageTitleObj = findElementByID("tv.fun.children:id/listTitleImage");
            verifyString("", startPageTitleObj.getText(), "卡通明星");
            UiObject starListObj = findElementByID("tv.fun.children:id/listView");
            int starCount = starListObj.getChildCount();
            verifyElementPresent("", starListObj);
            verifyNumberLarger("", starCount, 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 卡通明星页面可以正确响应Menu键操作
     */
    @Test
    public void Child_Star_17_testPressMenuInCartoonStarPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //选中任一明星，并按遥控器Menu键
            moveUpForMultiple(2);
            menu();
            //断言
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                findElementByText("取消收藏", "tv.fun.children:id/menu_text").click();
                moveUpForMultiple(2);
                menu();
            }
            UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
            verifyElementPresent("", storeBtnInMenu);
            verifyElementPresent("", searchBtnInMenu);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在卡通明星页面可以通过Menu键跳转到搜索页面
     */
    @Test
    public void Child_Star_18_testGotoSearchPageViaMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //选中任一明星，并按遥控器Menu键
            moveUpForMultiple(2);
            menu();
            //点击menu弹框上搜索按钮
            UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
            searchBtnInMenu.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/search_input");
            //断言
            UiObject inputBox = findElementByID("tv.fun.children:id/search_input");
            UiObject phoneSearchBtn = findElementByID("tv.fun.children:id/phoneSearch");
            verifyElementPresent("搜索页面中输入框未显示", inputBox);
            verifyElementPresent("搜索页面中删除按钮未显示", phoneSearchBtn);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在卡通明星页面可以通过Menu键收藏卡通明星
     */
    @Test
    public void Child_Star_19_testStoreCartoonStarViaMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //选中任一明星，并按遥控器Menu键
            moveUpForMultiple(2);
            UiObject currentStarObj = findElementByID("tv.fun.children:id/title");
            String currentStarName = currentStarObj.getText();
            menu();
            //点击menu弹框上收藏按钮
            if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                moveUpForMultiple(2);
                menu();
            }
            UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
            storeBtnInMenu.clickAndWaitForNewWindow();
            //断言
            back();
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentStarName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 在卡通明星页面可以通过Menu键取消收藏卡通明星
     */
    @Test
    public void Child_Star_20_testCancelStoreCartoonStarViaMenu() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //选中任一明星，并按遥控器Menu键
            moveUpForMultiple(2);
            moveLeftForMultiple(5);
            UiObject currentStarObj = findElementByID("tv.fun.children:id/title");
            String currentStarName = currentStarObj.getText();
            menu();
            String currentStarNameInSec = null;
            //点击menu弹框上取消收藏按钮
            if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                storeBtnInMenu.click();
                back();
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject firstStoreObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstStoreNameObj = firstStoreObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstStoreName = firstStoreNameObj.getText();
                back();
                findElementByID("tv.fun.children:id/children_view_item4").clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                moveUpForMultiple(2);
                moveLeftForMultiple(5);
                currentStarNameInSec = findElementByID("tv.fun.children:id/title").getText();
                menu();
            }
            UiObject cancelStoreBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
            cancelStoreBtnInMenu.clickAndWaitForNewWindow();
            //断言
            back();
            UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            collectBtnObj.clickAndWaitForNewWindow();
            UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            String firstCollectName = firstCollectNameObj.getText();
            verifyString("", firstCollectName, currentStarNameInSec);
            verifyString("", currentStarNameInSec, currentStarName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 卡通明星页面可以正确响应Back键操作
     */
    @Test
    public void Child_Star_21_testPressBackInCartoonStarPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //按遥控器back键
            back();
            //断言
            verifyElementNotPresent("", "tv.fun.children:id/filter");
            verifyElementPresent("", "tv.fun.children:id/tab_title_setting");
            verifyElementPresent("", "tv.fun.children:id/tab_title_brand");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 卡通明星页面可以正确响应Home键操作
     */
    @Test
    public void Child_Star_22_testPressHomeInCartoonStarPage() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //按遥控器home键
            home();
            //断言
            waitForElementPresentByID("com.bestv.ott:id/title");
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", launcherVideoTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * 卡通明星页面中点击大背景图专题可以进入大背景图专题页面
     */
    @Test
    public void Child_Star_24_testGotoTopicPageByClickingStar() {
        try {
            //进入少儿页面
            navigateToChildrenByChildrenCardUnderVideo();
            //移动到期望的tab
            moveToTargetTabInChildren("少儿");
            //点击“卡通明星”
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listView");
            //点击任一明星专题
            moveUpForMultiple(2);
            moveLeftForMultiple(5);
            enter();
            //断言
            waitForElementPresentByID("tv.fun.children:id/addFav");
            UiObject storeBtn = findElementByID("tv.fun.children:id/addFav");
            UiObject videoList = findElementByID("tv.fun.children:id/listView");
            int videoCount = videoList.getChildCount();
            UiObject videoInfo = findElementByID("tv.fun.children:id/briefContainer");
            verifyElementPresent("", storeBtn);
            verifyElementPresent("", videoList);
            verifyNumberLarger("", videoCount, 1);
            verifyElementPresent("", videoInfo);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }


//
//    /**
//     * 筛选页面UI显示正确
//     */
//    @Test
//    public void Child_List_67_testFilterPageUI(){
//        try{
//            //进入少儿页面
//            navigateToChildrenByChildrenCardUnderVideo();
//            //移动到期望的tab
//            moveToTargetTabInChildren("少儿");
//            //Clicking multiple tab card
//            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
//            multipleTabObj.clickAndWaitForNewWindow();
//            if(!findElementByID("tv.fun.children:id/filter").exists()){
//                back();
//                multipleTabObj = findElementByID("tv.fun.children:id/children_view_item6");
//                multipleTabObj.clickAndWaitForNewWindow();
//            }
//            waitForElementPresentByID("tv.fun.children:id/filter");
//            UiObject filterBtn = findElementByID("tv.fun.children:id/filter");
//            filterBtn.clickAndWaitForNewWindow();
//            //向下选中任一专题
//
//            //断言
//            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
//            UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
//            UiObject firstActualCollectNameObj = firstActualCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
//            String firstActualCollectName = firstActualCollectNameObj.getText();
//            verifyString("", firstActualCollectName, videoNameInBrief);
//        } catch (Throwable e) {
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr = e.toString();


//        } finally {
//            Utils.writeCaseResult(resultStr,
//                    resultFlag, execTime);
//        }
//    }
}
