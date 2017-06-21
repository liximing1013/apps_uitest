package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appstoretest.common.ChildrenPage;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/12/1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVChildrenHome extends ChildrenPage {

    /**
     * Navigate to TV Children page via Children card under launcher video tab
     */
    @Test
    public void Child_Home_02_testNavigateToChildrenFromChildUnderVideoTab() {
        try {
            //在launcher视频tab页面点击“少儿”卡片
            navigateToChildrenByChildrenCardUnderVideo();
            waitForElementPresentByID("tv.fun.children:id/tab_title_setting");
            //断言：少儿页面正确显示
            UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
            UiObject searchBtnObj = findElementByID("tv.fun.children:id/tab_title_search");
            verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
            verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
            verifyElementPresent("", searchBtnObj);
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
     * Navigate to TV Children page via Children card in video category page
     */
    @Test
    public void Child_Home_03_testNavigateToChildrenFromVideoCategory() {
        try {
            //在launcher页面点击“视频分类”卡片
            home();
            UiObject videoCategoryObj = findElementByText("视频分类", "com.bestv.ott:id/title");
            if(!videoCategoryObj.isSelected()) {
                videoCategoryObj.click();
            }
            videoCategoryObj.clickAndWaitForNewWindow();
            //点击“少儿”卡片
            UiObject childCardObj = findElementByText("少儿", "com.bestv.ott:id/maintitle");
            childCardObj.click();
            childCardObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/tab_title");
            //断言：少儿页面正确显示
            UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
            verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
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
     *Test that can navigate to children page from 七巧板
     */
    // @Test
    public void Child_Home_04_testNavigateToChildrenFromSevenPiece(){
        try {
            //长按home键
//            home();
            //长按home键
//            longPressHome(KeyEvent.KEYCODE_HOME);
//            device.pressKeyCode( KeyEvent.KEYCODE_HOME, 5000);
//            device.pressHome();
//            Utils.stopProcess(CHILDREN_PKG_NAME);
//            longPressKeyCode(KeyEvent.KEYCODE_HOME, 5000);
//            longPressHome();


            //点击七巧板上少儿频道卡片
            UiObject childrenIconObj = findElementByText("少儿频道", "com.bestv.ott:id/maintitle");
            if(!childrenIconObj.isSelected()) {
                childrenIconObj.click();
            }
            childrenIconObj.clickAndWaitForNewWindow();
            //断言：少儿页面正确显示
            waitForElementPresentByID("tv.fun.children:id/tab_title");
            UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
            verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that can navigate to children page after clicking 智能桌面 in launcher pop-up
     */
    @Test
    public void Child_Home_05_testNavigateToChildrenFromLauncherPopUp(){
        try {
            //点击launcher悬浮框上智能桌面卡片
            home();
            moveUpForMultiple(2);
            if(!findElementByID(networkIconIDInPopup).exists()){
                moveUpForMultiple(2);
            }
            UiObject desktopIconObj = findElementByText("智能桌面", "com.bestv.ott:id/launcher_title");
            if(!desktopIconObj.isSelected()) {
                desktopIconObj.click();
            }
            desktopIconObj.click();
            //点击智能桌面弹框上儿童桌面
            UiObject childDesktopObj = findElementByText("儿童桌面", "tv.fun.settings:id/title");
            moveRight();
            if(!childDesktopObj.isSelected()) {
                moveUp();
                moveRight();
            }
            enter();
            //断言：少儿页面正确显示
            waitForElementPresentByID("tv.fun.children:id/tab_title");
            UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
            verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Children Desktop UI displays correctly
     */
    @Test
    public void Child_Home_07_testChildrenDesktopUIDisplay(){
        try{
            navigateToChildrenDesktopPage();
            //断言
            UiObject suggestTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
            UiObject childTabObj = findElementByID("tv.fun.children:id/tab_title_children");
            UiObject brandTabObj = findElementByID("tv.fun.children:id/tab_title_brand");
            UiObject searchObj = findElementByID("tv.fun.children:id/tab_title_search");
            UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("", suggestTabObj);
            verifyElementPresent("", childTabObj);
            verifyElementPresent("", brandTabObj);
            verifyElementPresent("", searchObj);
            verifyElementPresent("", settingObj);
            verifyTrue("", suggestTabObj.isFocused());
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
     * Children tab page UI displays correctly
     */
    @Test
    public void Child_Home_08_testChildrenTabUIDisplay(){
        try{
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //断言
            UiObject childHistoryObj = findElementByID("tv.fun.children:id/children_history");
            UiObject myListObj = findElementByID("tv.fun.children:id/children_menu");
            UiObject latestHistoryObj = findElementByID("tv.fun.children:id/children_history_icon");
            verifyElementPresent("", childHistoryObj);
            verifyElementPresent("", myListObj);
            verifyElementPresent("", latestHistoryObj);
            verifyString("", childHistoryObj.getText(), "播放记录");
            verifyString("", myListObj.getText(), "我的收藏");
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
     * Check the suggested video in children tab page when no playing record exists
     */
    @Test
    public void Child_Home_09_testSuggestVideoWhenNoPlayRecord(){
        try{
            //进入少儿桌面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击播放记录卡片
            navigateToPlayRecordPage();
            UiObject recordList = findElementByID("tv.fun.children:id/listView");
            if(!findElementByID("tv.fun.children:id/empty").exists()){
                moveDown();
                menu();
                UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                clearObj.click();
                waitForElementPresentByID("tv.fun.children:id/empty");
            }
            back();
            //断言
            UiObject firstCardObj = findElementByID("tv.fun.children:id/children_view_history");
            int firstCardEleCount = firstCardObj.getChildCount();
            UiObject firstSuggestCardTitle = firstCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/children_history_title"));
            UiObject firstSuggestCardSubTitle = firstCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/children_history_sub_title"));
            verifyNumber("", firstCardEleCount, 3);
            verifyElementPresent("", firstSuggestCardTitle);
            verifyElementPresent("", firstSuggestCardSubTitle);
            verifyFalse("", firstSuggestCardSubTitle.getText().contains("观看第"));
            verifyFalse("", firstSuggestCardSubTitle.getText().contains("集至"));
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
     * Test the playing record page displaying when no playing record
     */
    @Test
    public void Child_Home_11_testPlayRecordPageWhenNoRecord(){
        try{
            //进入少儿桌面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击播放记录卡片
            navigateToPlayRecordPage();
            UiObject recordList = findElementByID("tv.fun.children:id/listView");
            if(!findElementByID("tv.fun.children:id/empty").exists()){
                menu();
                UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                clearObj.click();
                waitForElementPresentByID("tv.fun.children:id/empty");
                back();
                navigateToPlayRecordPage();
            }
            //断言
            UiObject historyObj = findElementByText("播放记录", "tv.fun.children:id/history");
            verifyTrue("", historyObj.isSelected()&&historyObj.isFocused());
            verifyString("", findElementByID("tv.fun.children:id/empty").getText(), "您还没有看过任何影片");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test playing record page displaying when have playing records
     */
    @Test
    public void Child_Home_12_testPlayRecordPageWhenHaveRecord(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            waitForElementPresentByID("tv.fun.children:id/tab_title_children");
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击播放记录卡片
            navigateToPlayRecordPage();
            //准备测试条件：有播放记录
            if(findElementByID("tv.fun.children:id/empty").exists()){
                back();
                UiObject historyObj = findElementByID("tv.fun.children:id/children_history_icon");
                historyObj.clickAndWaitForNewWindow();
                Thread.sleep(2000);
                back();
                back();
                if(findElementByID("com.bestv.ott:id/playerflipper").exists()){
                    back();
                    back();
                }
                navigateToPlayRecordPage();
            }
            UiObject recordList = findElementByID("tv.fun.children:id/listView");
            int recordCount = recordList.getChildCount();
            verifyNumberEqualORLarger("", recordCount, 1);
            UiObject timeStrObj = findElementByID("tv.fun.children:id/timestamp");
            verifyTrue("", timeStrObj.getText().contains("观看第"));
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test my favorite page displaying when no restored record
     */
    @Test
    public void Child_Home_13_testMyFavoritePageWhenNoRecord(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            waitForElementPresentByID("tv.fun.children:id/tab_title_children");
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击“我的收藏”
            navigateToMyCollectionPage();
            //准备测试条件：无收藏记录
            if(!findElementByID("tv.fun.children:id/empty").exists()){
                moveDown();
                if(!findElementByText("我的收藏", "tv.fun.children:id/favorite").isSelected()){
                    moveRightForMultiple(2);
                    moveDown();
                }
                menu();
                UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                clearObj.click();
                moveRightForMultiple(2);
                if(!findElementByID("tv.fun.children:id/empty").exists()){
                    moveDown();
                    menu();
                    clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                    clearObj.click();
                }
                waitForElementPresentByID("tv.fun.children:id/empty");
            }
            back();
            if(!findElementByID("tv.fun.children:id/tab_title_children").isFocused()){
                moveLeftForMultiple(2);
                moveRight();
            }
            navigateToMyCollectionPage();
            //断言
            UiObject historyObj = findElementByText("我的收藏", "tv.fun.children:id/favorite");
            verifyTrue("", historyObj.isSelected()&&historyObj.isFocused());
            verifyString("", findElementByID("tv.fun.children:id/empty").getText(), "您还没收藏任何影片");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test my favorite page displaying when have restored record
     */
    @Test
    public void Child_Home_14_testMyFavoritePageWhenHaveRecord(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击“我的收藏”
            UiObject myFavoriteObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            myFavoriteObj.clickAndWaitForNewWindow();
            //准备测试条件：有收藏记录
            if(findElementByID("tv.fun.children:id/empty").exists()) {
                back();
                //点击任一列表卡片：例如动画大全
                UiObject allVideoObj = findElementByID("tv.fun.children:id/children_view_item2");
                allVideoObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveDown();
                moveRightForMultiple(3);
                if(device.findObject(new UiSelector().className("android.widget.Button").selected(true)).exists()){
                    moveUpForMultiple(3);
                    moveRightForMultiple(3);
                    Thread.sleep(3000);
                }
                moveDown();
                menu();
                UiObject collObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collObj.click();
                waitForElementNotPresent("tv.fun.children:id/menu_text");
                back();
                //点击我的收藏卡片
                myFavoriteObj.clickAndWaitForNewWindow();
            }
            UiObject recordList = findElementByID("tv.fun.children:id/listView");
            int recordCount = recordList.getChildCount();
            verifyNumberEqualORLarger("", recordCount, 1);
            UiObject timeStrObj = findElementByID("tv.fun.children:id/timestamp");
            verifyTrue("", timeStrObj.getText().equalsIgnoreCase("已收藏"));
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test go to search page via clicking search icon in Children Home page
     */
    @Test
    public void Child_Home_15_testSearchPageViaSearchIconInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //点击少儿首页“搜索”icon
            UiObject searchObj = findElementByID("tv.fun.children:id/tab_title_search");
            searchObj.clickAndWaitForNewWindow();
            //断言
            UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
            UiObject searchTitleObj = findElementByID("tv.fun.children:id/resultTitle");//大家都在搜：
            UiObject searchInputObj = findElementByID("tv.fun.children:id/search_input");//输入首字母或全拼
            verifyElementPresent("", searchResultObj);
            verifyString("", searchTitleObj.getText(), "大家都在搜：");
            verifyString("", searchInputObj.getText(), "输入首字母或全拼");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test go to parental setting page via setting icon in Children home page
     */
    @Test
    public void Child_Home_16_testParentalSettingPageViaSettingIconInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //点击少儿首页“家长设置”icon
            UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
            settingObj.clickAndWaitForNewWindow();
            inputChildrenKeyPasswordIfPopupDisplay();
            //断言
            UiObject settingTitleObj = findElementByID("tv.fun.children:id/settings_title");//家长设置
            UiObject sexObj = findElementByID("tv.fun.children:id/settings_item_sex_icon");//宝贝性别
            UiObject birthObj = findElementByID("tv.fun.children:id/settings_birth");//宝贝生日
            UiObject birthLableObj = birthObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            UiObject playObj = findElementByID("tv.fun.children:id/settings_play_time");//播放时长控制
            UiObject playLableObj = playObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            UiObject lockObj = findElementByID("tv.fun.children:id/settings_lock");//儿童锁
            UiObject lockLableObj = lockObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            UiObject eyeModeObj = findElementByID("tv.fun.children:id/settings_eye_mode");//护眼模式
            UiObject eyeModeLabelObj = eyeModeObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            verifyElementPresent("", settingTitleObj);
            verifyString("", settingTitleObj.getText(), "家长设置");
            verifyElementPresent("", sexObj);
            verifyString("", sexObj.getText(), "宝贝性别");
            verifyIncludeString("", birthLableObj.getText(), "宝贝生日");
            verifyIncludeString("", playLableObj.getText(), "播放时长控制");
            verifyIncludeString("", lockLableObj.getText(), "儿童锁");
            verifyIncludeString("", eyeModeLabelObj.getText(), "护眼模式");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test navigate to Bilingual Area page via bilingual Area card in Children home page
     */
    @Test
    public void Child_Home_17_testBilingualAreaPageViaCardInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到品牌专区tab
            moveToTargetTabInChildren(childTabs[2]);
            //点击少儿首页“双语专区”卡片
            UiObject bilingualObj = findElementByID("tv.fun.children:id/children_brand_item9");
            bilingualObj.clickAndWaitForNewWindow();
            //断言
            UiObject listObj = findElementByID("tv.fun.children:id/listView");
            int videoCount = listObj.getChildCount();
            verifyNumberLarger("", videoCount, 1);
            moveRight();
            UiObject videoCardObj = listObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
            int videoCardCount = videoCardObj.getChildCount();
            verifyNumber("", videoCardCount,3);
            UiObject cardEle = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/card"));
            UiObject chineseBtn = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/chinese"));
            UiObject englishBtn = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/english"));
            verifyElementPresent("", cardEle);
            verifyElementPresent("", chineseBtn);
            verifyElementPresent("", englishBtn);
            verifyString("", chineseBtn.getText(), "中文版");
            verifyString("", englishBtn.getText(), "英文版");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test age list page via clicking age list card in Children home page
     */
    @Test
    public void Child_Home_19_testAgeListPageViaAgeCardInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击少儿首页“按年龄”卡片
            UiObject ageListObj = findElementByID("tv.fun.children:id/children_view_item6");
            ageListObj.clickAndWaitForNewWindow();
            moveRightForMultiple(2);
            Thread.sleep(1000);
            //断言
            UiObject tabSectionObj = findElementByID("tv.fun.children:id/tabTitleSection");
            UiObject searchObj = tabSectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/search"));
            UiObject firstAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("0-3岁"));
            UiObject secondAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("4-6岁"));
            UiObject thirdAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("7岁以上"));
            UiObject listObj = findElementByID("tv.fun.children:id/listView");
            int videoCount = listObj.getChildCount();
            verifyNumberLarger("", videoCount, 1);
            verifyTrue("", firstAgeTabObj.exists()&&secondAgeTabObj.exists()&&thirdAgeTabObj.exists());
            UiObject videoCardObj = listObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject imageObj = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/image"));
            UiObject videoTitleObj = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            verifyElementPresent("", imageObj);
            verifyElementPresent("", videoTitleObj);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test navigate to cartoon star page via clicking cartoon star card in Children home page
     */
    @Test
    public void Child_Home_20_testCartoonStarPageViaStarCardInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击少儿首页“卡通明星”卡片
            UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
            cartoonStarObj.clickAndWaitForNewWindow();
            moveRightForMultiple(1);
            //断言
            UiObject cartoonStarTitleObj = findElementByID("tv.fun.children:id/listTitleImage");//卡通明星
            UiObject listObj = findElementByID("tv.fun.children:id/listView");
            int starCount = listObj.getChildCount();
            verifyNumberLarger("", starCount, 1);
            verifyString("", cartoonStarTitleObj.getText(), "卡通明星");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test navigate to 动画大全 page via clicking 动画大全 card in Children home page
     */
    @Test
    public void Child_Home_21_testAllChildrenVideoPageViaCardInHome(){
        String[] tabs = {"全部", "推荐", "专题", "金卡VIP", "卡通明星", "安全教育", "爆笑乐园", "冒险世界", "动画电影"};
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击少儿首页“动画大全”卡片
            UiObject allVideoObj = findElementByID("tv.fun.children:id/children_view_item2");
            allVideoObj.clickAndWaitForNewWindow();
            moveRightForMultiple(3);
            Thread.sleep(1000);
            //断言
            UiObject suggestObj = device.findObject(new UiSelector().className("android.widget.Button").text("推荐"));
            UiObject listObj = findElementByID("tv.fun.children:id/listView");
            int videoCount = listObj.getChildCount();
            verifyNumberLarger("", videoCount, 1);
            UiObject sectionObj = findElementByID("tv.fun.children:id/tabTitleSection");
            UiObject firstLabelObj = sectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/search"));
            UiObject secLabelObj = sectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/filter"));
            verifyElementPresent("", firstLabelObj);
            verifyElementPresent("", secLabelObj);
            UiObject tabListObj = findElementByID("tv.fun.children:id/tabContainer");
            int tabCount = tabListObj.getChildCount();
            for (int i=0; i<tabCount; i++){
                if(tabCount>9){
                    tabCount=9;
                }
                UiObject eachTabObj = tabListObj.getChild(new UiSelector().className("android.widget.Button").index(i));
                String eachTabName = eachTabObj.getText();
                verifyString("", eachTabObj.getText(), tabs[i]);
            }
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test navigate to topic page via clicking topic card in Children home page
     */
    @Test
    public void Child_Home_22_testGotoTopicPageViaCardInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击少儿首页任一专题卡片
            UiObject earlyTeachObj = findElementByID("tv.fun.children:id/children_view_item5");
            earlyTeachObj.clickAndWaitForNewWindow();
            //断言
            UiObject listObj = findElementByID("tv.fun.children:id/container");
            UiObject languageObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/language_class"));
            UiObject afterObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/after_class"));
            UiObject earlyObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/early_class"));
            UiObject artObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/art_class"));
            verifyElementPresent("", languageObj);
            verifyElementPresent("", afterObj);
            verifyElementPresent("", earlyObj);
            verifyElementPresent("", artObj);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test navigate to single tab list page via clicking card in Children home page
     */
    @Test
    public void Child_Home_23_testGotoSingleTabListPageViaCardInHome(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //移动到少儿tab
            moveToTargetTabInChildren(childrenTabName);
            //点击少儿首页任一单tab列表页卡片: 例如讲故事
            UiObject singleTabListObj = findElementByID("tv.fun.children:id/children_view_item7");
            singleTabListObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.children:id/listTitleImage");
            moveRight();
            //断言
            verifyElementPresent("", findElementByID("tv.fun.children:id/listTitleImage"));
            UiObject listObj = findElementByID("tv.fun.children:id/listView");
            int starCount = listObj.getChildCount();
            verifyNumberLarger("", starCount, 1);
            verifyElementPresent("", findElementByID("tv.fun.children:id/briefContainer"));
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that no response when pressing menu in Children home page
     */
    @Test
    public void Child_Home_33_testNoResponseWhenPressingMenuInHome() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //按遥控器Menu键
            menu();
            verifyElementNotPresent("", findElementByID("tv.fun.children:id/menu_text"));
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
     * Test that can back to correct page from children home page when pressing back in Children home page
     */
    @Test
    public void Child_Home_34_testBackFromChildrenPageWhenPressingBackInHome() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //按遥控器Back键
            back();
            inputChildrenKeyPasswordIfPopupDisplay();
            //断言
            UiObject launcherTabObj = findElementByText("电视", "com.bestv.ott:id/tab_title");
            UiObject launcherStatusBar = findElementByID("com.bestv.ott:id/status_bar");
            verifyElementPresent("", launcherTabObj);
            verifyElementPresent("", launcherStatusBar);
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
     * Test that can back to launcher home page from children home page when pressing home in Children home page
     */
    @Test
    public void Child_Home_35_testBackToLauncherHomeWhenPressingHomeInHome() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //按遥控器Home键
            home();
            //断言
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
     * Test that the people like tab page UI displaying correctly
     */
    @Test
    public void Child_Home_39_testPeopleLikeTabPageUI(){
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            UiObject likeTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
            UiObject likePageObj = findElementByID("tv.fun.children:id/tab_content").getChild(new UiSelector().resourceId("tv.fun.children:id/children_recommand_view"));
            int likePageCardCount = likePageObj.getChildCount();
            verifyNumberLarger("", likePageCardCount, 10);
            UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
            int rankListCount = rankListObj.getChildCount();
            verifyNumberLarger("", rankListCount, 4);
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
     * Play correctly after clicking any video in rank lisy under 推荐 tab
     */
    @Test
    public void Child_Home_44_testVideoPlayOfRank() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击排行榜list中任一单片
            UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
            UiObject firstVideoObj = rankListObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            firstVideoObj.clickAndWaitForNewWindow();
            Thread.sleep(5000);
            //断言
            moveLeft();
            waitForElementPresentByID("com.bestv.ott:id/media_progress");
            UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
            UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
            String tipText = tipObj.getText();
            verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
            verifyElementPresent("", videoPage);
            verifyString("", tipText, "按\uE693菜单键切换清晰度");
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
     * Check the rank list page displays correctly
     */
    @Test
    public void Child_Home_45_testRankListPageUIDisplay() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击排行榜list中任一单片
            UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
            UiObject firstVideoObj = rankListObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
            UiObject rankListUnderTab = findElementByID("tv.fun.children:id/children_view_rank_list");
            int videosCountInRank = rankListUnderTab.getChildCount();
            String[] videosNameInRank = new String[videosCountInRank];
            if(videosCountInRank>4){
                videosCountInRank=4;
            }
            for(int i=0; i<videosCountInRank; i++){
                UiObject eachVideoInRank = rankListUnderTab.getChild(new UiSelector().className("android.widget.TextView").index(i));
                videosNameInRank[i] = eachVideoInRank.getText();
            }
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //断言
            UiObject videoList = findElementByID("tv.fun.children:id/listView");
            int videoCount = videoList.getChildCount();
            verifyNumberEqualORLarger("", videoCount, videosCountInRank);
            //向下选中第一个卡片
            moveDown();
            for(int j=0; j<videosCountInRank; j++){
                UiObject singleVideoObj = videoList.getChild(new UiSelector().className("android.widget.FrameLayout").index(j));
                UiObject singleVideo = singleVideoObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String singleVideoName = singleVideo.getText();
                verifyString("", singleVideoName, videosNameInRank[j]);
                if(j<3){
                    UiObject markObj = singleVideoObj.getChild(new UiSelector().resourceId("tv.fun.children:id/badge"));
                    verifyElementPresent("", markObj);
                }
                UiObject briefObj = findElementByID("tv.fun.children:id/briefContainer");
                UiObject briefTopObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefTop"));
                UiObject briefTitleObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefTitle"));
                UiObject briefMetaObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefMeta"));
                String videoNameInBrief = briefTitleObj.getText();
                String topInBrief = briefTopObj.getText();
                verifyString("", briefTopObj.getText(), "TOP"+(j+1));
                verifyString("",videoNameInBrief, singleVideoName);
                verifyElementPresent("", briefMetaObj);
                moveRight();
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

    /**
     * test that rank page can response the MENU
     */
    @Test
    public void Child_Home_48_testMenuResponseInRankPage(){
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //选中任一卡片，按遥控器Menu键
            moveDown();
            menu();
            //断言
            UiObject menuPopupObj = findElementByID("tv.fun.children:id/menu_container");
            UiObject storeObj = menuPopupObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0)).getChild(new UiSelector().resourceId("tv.fun.children:id/menu_text"));
            UiObject searchObj = menuPopupObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).getChild(new UiSelector().resourceId("tv.fun.children:id/menu_text"));
            verifyString("", storeObj.getText(), "收藏");
            verifyString("", searchObj.getText(), "搜索");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that can jump to search page after clicking search icon in menu pop-up
     */
    @Test
    public void Child_Home_49_testJumpToSearchPageAfterClickingSearchIconInMenu(){
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //选中任一卡片，按遥控器Menu键
            moveDown();
            menu();
            UiObject searchIconObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
            searchIconObj.clickAndWaitForNewWindow();
            //断言
            UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
            UiObject searchTitleObj = findElementByID("tv.fun.children:id/resultTitle");//大家都在搜：
            UiObject searchInputObj = findElementByID("tv.fun.children:id/search_input");//输入首字母或全拼
            verifyElementPresent("", searchResultObj);
            verifyString("", searchTitleObj.getText(), "大家都在搜：");
            verifyString("", searchInputObj.getText(), "输入首字母或全拼");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can store video after clicking store icon in menu pop-up
     */
    @Test
    public void Child_Home_50_testStoreVideoViaStoreIcon(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //选中任一卡片，按遥控器Menu键
            moveDown();
            UiObject nameInBriefObj = findElementByID("tv.fun.children:id/briefTitle");
            String nameInBrief = nameInBriefObj.getText();
            //收藏当前选中的影片
            menu();
            if(findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()){
                UiObject cancelStoreIconObj = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelStoreIconObj.clickAndWaitForNewWindow();
                moveRight();
                moveLeft();
                menu();
            }
            UiObject storeIconObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
            storeIconObj.clickAndWaitForNewWindow();
            back();
            //移动焦点到少儿tab
            moveUp();
            moveRight();
            //点击 我的收藏，进入此页面查看收藏记录
            UiObject myFavoriteObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
            myFavoriteObj.clickAndWaitForNewWindow();
            UiObject myFavoriteListObj = findElementByID("tv.fun.children:id/listView");
            UiObject firstRecordObj = myFavoriteListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
            UiObject firstRecordNameObj = firstRecordObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
            UiObject firstRecordStatusObj = firstRecordObj.getChild(new UiSelector().resourceId("tv.fun.children:id/timestamp"));
            String firstRecordName = firstRecordNameObj.getText();
            String firstRecordStatus = firstRecordStatusObj.getText();
            verifyString("", firstRecordName, nameInBrief);
            verifyString("", firstRecordStatus, "已收藏");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can back the last page after clicking Back in rank list page
     */
    @Test
    public void Child_Home_51_testBackFromRankListPage(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //按遥控器back键
            back();
            //断言
            UiObject likeTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
            UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
            verifyElementPresent("", likeTabObj);
            verifyElementPresent("", settingObj);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can back the launcher home page after pressing Home in rank list page
     */
    @Test
    public void Child_Home_52_testPressHomeInRankListPage(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", launcherVideoTab.isSelected());
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the video in ranking list page can play correctly
     */
    @Test
    public void Child_Home_54_testVideoPlayInRankListPage(){
        try{
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击热播榜卡片进入排行榜页面
            UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
            reboObj.clickAndWaitForNewWindow();
            //点击任一影片
            moveRight();
            enter();
            Thread.sleep(5000);
            //断言
            moveLeft();
            waitForElementPresentByID("com.bestv.ott:id/media_progress");
            UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
            UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
            String tipText = tipObj.getText();
            verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
            verifyElementPresent("", videoPage);
            verifyString("", tipText, "按\uE693菜单键切换清晰度");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the suggested video under like tab can be played correctly
     */
    @Test
    public void Child_Home_63_testSuggestVideoPlayUnderLikeTab() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children suggest tab page
            moveToTargetTabInChildren(childTabs[0]);
            waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
            //点击大家都在看tab页面推荐影片
            UiObject suggestVideoObj = findElementByID("tv.fun.children:id/children_recommand_item3");//选中任一影片，点击播放
            suggestVideoObj.clickAndWaitForNewWindow();
            Thread.sleep(5000);
            if(findElementByID("tv.fun.children:id/addFav").exists()){
                moveDownForMultiple(2);
                enter();
                Thread.sleep(6000);
            }
            //断言
            moveLeft();
            waitForElementPresentByID("com.bestv.ott:id/media_progress");
            UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
            moveLeft();
            UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
            String tipText = tipObj.getText();
            verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
            verifyElementPresent("", videoPage);
            verifyString("", tipText, "按\uE693菜单键切换清晰度");
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
     * Test that the brand tab page UI displays correctly
     */
    @Test
    public void Child_Home_69_testBrandTabPageUIDisplay() {
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children Brand tab page
            moveToTargetTabInChildren(childTabs[2]);
            UiObject brandTab = findElementByID("tv.fun.children:id/tab_title_brand");
            verifyTrue("", brandTab.isFocused());
            //断言
            UiObject brandListObj = findElementByID("tv.fun.children:id/children_brand_view");
            int cardCount = brandListObj.getChildCount();
            verifyNumber("", cardCount, 9);
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
     * Test that can enter into topic page under brand tab
     */
    @Test
    public void Child_Home_74_testEnterTopicPageUnderBrandTab(){
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children brand tab page
            moveToTargetTabInChildren(childTabs[2]);
            UiObject topicObj = findElementByID("tv.fun.children:id/children_brand_item4");//第四个专题卡片
            topicObj.clickAndWaitForNewWindow();
            //断言
            UiObject storeBtnObj = findElementByID("tv.fun.children:id/addFav");
            UiObject videoList = findElementByID("tv.fun.children:id/listView");
            int videoCount = videoList.getChildCount();
            moveDownForMultiple(2);
            UiObject videoShortInfo = findElementByID("tv.fun.children:id/briefContainer");
            verifyElementPresent("", storeBtnObj);
            verifyElementPresent("", videoList);
            verifyNumberLarger("", videoCount, 1);
            verifyElementPresent("", videoShortInfo);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that no response when pressing menu under Brand tab
     *
     * 品牌专区页面不响应Menu键操作
     */
    @Test
    public void Child_Home_77_testNoMenuResponseWhenPressMenuUnderBrandTab(){
        try {
            //进入少儿页面
            navigateToChildrenDesktopPage();
            //Goto Children brand tab page
            moveUp();
            moveRight();
            //Move down to select one brand topic card
            moveDown();
            //Press menu
            menu();
            //断言
            verifyElementNotPresent("", "tv.fun.children:id/menu_text");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

}