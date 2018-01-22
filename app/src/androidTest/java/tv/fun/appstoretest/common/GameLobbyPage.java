package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

/**
 * Created by liuqing on 2018/1/5.
 */

public class GameLobbyPage extends AppStorePage{
    public String launcherDSJCardEleText = "电视剧";
    //Game tab element name or locator id
    public String gameTabName = "游戏";
    public String myGameCardName = "我的游戏";
    public String gameLobbyCardName = "游戏大厅";
    public String gameTabPhbListTitle = "下载排行榜";
    public String gameTabPageGridLocatorID = "com.bestv.ott:id/grid";
    public String gameTabPHBRankListLocatorID = "com.bestv.ott:id/game_ranklistContainer";
    public String gameTabPHBScrollListLocatorID = "com.bestv.ott:id/rank_scroll_list";
    public int cardNumUnderGameTab = 8;
    //Game Lobby page element name or locator id
    public String[] tabsOfGameLobby = {"精品", "排行榜", "遥控器", "设置"};
    public String[] tabsOfGameLobbyWithSearch = {"搜索","精品", "排行榜", "遥控器", "设置"};
    public String gameLobbyTabColLocator = "tv.fun.appstore:id/column_title";
    public String gameLobbyTabTitleLocator = "tv.fun.appstore:id/game_title";
    public String gameTabPageEleTitleLocator = "com.bestv.ott:id/title";
    public String gameSearchIconLocatorID = "tv.fun.appstore:id/game_search";
    public String nodeNormalClassName = "android.widget.RelativeLayout";
    public String childNodeNormalClassName = "android.widget.LinearLayout";
    public String childEleNormalClassName = "android.widget.FrameLayout";
    public String menuPopupLocatorID = "android:id/tv_fun_menu";
    public String btnTextInMenuPopupLocatorID = "android:id/tv_fun_menu_text";
    public int orderOfMyGameCardInPage = 4;
    public int orderOfGameLobbyCardInPage = orderOfMyGameCardInPage+1;
    //Game Lobby page Jingpin Tab element name or locator id
    public String firstColLocatorUnderJingpin = "tv.fun.appstore:id/game_page_choice_gallery";
    public int suggestAppCountInFirstColUnderJingpin = 3;
    public String secColLocatorUnderJingpin = "tv.fun.appstore:id/game_page_choice_items";
    public int suggestAppCountInSecColUnderJingpin = 5;
    //YKQ tab element or locator id
    public int childEnterNumUnderYKQTab = 6;
    public String[] childEntersNameUnderYKQ = {"动作","角色", "棋牌", "射击", "体育", "休闲"};
    public String childEnterOfEdu = "儿歌";

    //App detail page element locator info
    public String appDetailPageAppNameLocatorID = "tv.fun.appstore:id/title";
    public String appDetailPageButtonLocatorID = "tv.fun.appstore:id/titleContainer";
    public String getAppDetailPageButtonParentLocatorID = "tv.fun.appstore:id/emptyButton";
    public String appDetailPageInstallBtnText = "安装";
    public String appDetailPageOpenBtnText = "打开";//安装后显示“打开”按钮
    public String appDetailPageDownloadProgressLocator = "tv.fun.appstore:id/progressState";
    public String appDetailPagePublicTimeLocatorID = "tv.fun.appstore:id/publicTime";
    public String appDetailPagePublicText = "更新：";
    public String appDetailPagePosterLocatorID = "tv.fun.appstore:id/app_detail_poster_image";
    public String appDetailPageDownloadCountLocatorID = "tv.fun.appstore:id/downloadCount";
    public String appDetailPageBriefLocatorID = "tv.fun.appstore:id/brief";
    public String appDetailPageDownProgressObjLocator = "tv.fun.appstore:id/progressNum";
    public String appDetailPageAppStopInstallStatus = "已暂停";
    //My game page element name or locator id
    public String myGamePageTitleLocatorID = "tv.fun.appstore:id/title";
    public String myGamePageSubTitleLocatorID = "tv.fun.appstore:id/subTitle";
    public String[] myGamePageSubTitleChildStrs = {"有", "个游戏"};
    public String myGameNoAppPageLocatorID = "tv.fun.appstore:id/emptyView";
    public String myGamePageListLocatorID = "tv.fun.appstore:id/listview";
    public String myGameNoAppPageMsg = "未安装任何游戏，快去安装吧";
    public String myGamePageAppNameLocator = "tv.fun.appstore:id/appName";
    public String myGamePageMenuIconLocator = "android:id/tv_fun_menu_icon";
    public String myGamePageMenuPopupBtnLocator = "android:id/tv_fun_menu_text";
    public String[] btnsNameInMenuPopup = {"详情", "卸载", "清理数据"};

    //Game Lobby page PHB tab element name or locator id
    //Download PHB
    public String gameLobbyDPHBColLocatorID = "tv.fun.appstore:id/game_download_rank_container";
    public String gameLobbyDPHBIconLocatorID = "tv.fun.appstore:id/game_download_rank_icon";
    public String gameLobbyDPHBListLocatorID = "tv.fun.appstore:id/game_download_rank_layout";
    //hot PHN
    public String gameLobbyHPHBColLocatorID = "tv.fun.appstore:id/game_hot_rank_container";
    public String gameLobbyHPHBIconLocatorID = "tv.fun.appstore:id/game_hot_rank_icon";
    public String gameLobbyHPHBListLocatorID = "tv.fun.appstore:id/game_hot_rank_layout";
    //New PHB
    public String gameLobbyNPHBColLocatorID = "tv.fun.appstore:id/game_new_rank_container";
    public String gameLobbyNPHBIconLocatorID = "tv.fun.appstore:id/game_new_rank_icon";
    public String gameLobbyNPHBListLocatorID = "tv.fun.appstore:id/game_new_rank_layout";
    public String gameLobbyPHBAppIconLocatorID = "tv.fun.appstore:id/game_rank_item_icon";
    public String gameLobbyPHBAppNameLocatorID = "tv.fun.appstore:id/game_rank_item_text";
    public String gameLobbyPHBAppRankLocatorID = "tv.fun.appstore:id/game_rank_item_rank";

    //遥控器tab
    public String telControlChildEnterListLocatorID = "tv.fun.appstore:id/list";
    public String telControlChildEnterNameLocatorID = "tv.fun.appstore:id/title";

    //列表页
    public String listAppViewObjLocatorID = "tv.fun.appstore:id/all_apps_view";
    public String listAppPageTitleLocatorID = "tv.fun.appstore:id/all_apps_title";
    public String appShortInfoObjLocatorID = "tv.fun.appstore:id/all_apps_status";
    public String appPrefixInShortInfoLocatorID = "tv.fun.appstore:id/all_apps_status_prefix";
    public String appRateInShortInfoLocatorID = "tv.fun.appstore:id/all_apps_status_divider_ratingbar";
    public String appSuffixInShortInfoLocatorID = "tv.fun.appstore:id/all_apps_status_suffix";
    public String scrollBarInListAppPageLocatorID = "android:id/tv_fun_scrollbar";
    public String btnNameInListPageMenuPopup = "搜索";
    public String appNameObjInListPageLocator = "tv.fun.appstore:id/all_app_title";
    //游戏搜索页element, locator id
    public String searchKeyInputObjLocaotorID = "tv.fun.appstore:id/search_single_key";
    public String searchInputObjLocatorID = "tv.fun.appstore:id/search_input";
    public String defaultMsgInSearchInput = "请输入应用拼音首字母";
    public String searchDelBtnObjLocatorID = "tv.fun.appstore:id/search_del_key";
    public String searchKeyboardObjLocatorID ="tv.fun.appstore:id/keyboard";
    public String searchMsgInSearchPageBotm = "首字母搜索，例如\"有乐斗地主\"输入YLDDZ";
    public String searchResultAreaTitleLocatorID = "tv.fun.appstore:id/search_result_title";
    public String searchResultDefaultTitleText = "热门搜索：";
    public String singalAppLocatorInSearchResult = "tv.fun.appstore:id/container";
    public String gameSearchResultAppListLocatorID = "tv.fun.appstore:id/app_search_reulst_cell";
    public String gameSearchResultAppIconLocatorID = "tv.fun.appstore:id/app_image";
    public String gameSearchResultAppTitleLocatorID = "tv.fun.appstore:id/app_title";
    public String gameSearchResultAppDownCntLocatorID = "tv.fun.appstore:id/search_downcnt";
    public String gameSearchNotResultLocatorID = "tv.fun.appstore:id/search_no_result";
    public String gameSearchNoResultMsg = "暂无匹配结果！";
    public String gameSearchPageSearchResultTitle = "搜索结果：";
    public String gameSearchPageSearchResultCountLocator = "tv.fun.appstore:id/search_result_count";
    public String gameSearchPageSearchResultListLocator = "tv.fun.appstore:id/search_result_noraml";
    public String[] keywordForResult = {"Q", "Q"};
    public String notGameApp = "QQ音乐";

    //游戏大厅设置tab页面element, locator id
    public String gameLobbySDKLoginCardLocatorID = "tv.fun.appstore:id/game_settings_login";
    public String gameLobbyMyGameCardLocatorID = "tv.fun.appstore:id/game_settings_mygame";
    public String gameLobbyHandleSettingCardLocatorID = "tv.fun.appstore:id/game_settings_handle";
    public String gameLobbyHandleBuyCardLocatorID = "tv.fun.appstore:id/game_settings_buy";
    public String gameLobbyPageLocatorID = "tv.fun.appstore:id/game_app_pager";
    //sdk登录页element
    public String sdkLoginPageTitleLocatorID = "tv.fun.appstore:id/setting_title";
    public String sdkLoginPageTitleText = "风行账号登录";
    public String sdkLoginPageSubtitleLocatorID = "tv.fun.appstore:id/setting_subtitle";
    public String sdkLoginPageSubtitleText = "有问题请拨打客服电话 400-600-6258";
    public String sdkLoginPageAccountInputLocatorID = "tv.fun.appstore:id/edittext_username";
    public String sdkLoginPageAccountInputMsg = "请输入风行账号(手机号码)";
    public String sdkLoginPagePsdInputLocatorID = "tv.fun.appstore:id/edittext_password";
    public String sdkLoginPageLoginBtnLocatorID = "tv.fun.appstore:id/login";
    public String sdkLoginPagLoginBtnText = "立即登录";
    public String sdkLoginPageRegisterBtnLocatorID = "tv.fun.appstore:id/register";
    public String sdkLoginPagRegisterBtnText = "立即注册";
    public String sdkLoginPagePsdForgetBtnLocatorID = "tv.fun.appstore:id/pwd_forget";
    public String sdkLoginPagLPwdForgetBtnText = "忘记密码";
    public String sdkLoginUsername = "18602766907";
    public String sdkLoginPassword = "123456";

    //专题页element
    public String topicPageAppListLocatorID = "tv.fun.appstore:id/topic_app_list";
    public String topicPageBackgroundObjLocatorID = "tv.fun.appstore:id/topic_poster";
    public String topicPageContentLocatorID = "android:id/content";





    //
    /**
     * Method for navigating to Launcher Game tab page
     */
    public void navigateToLauncherGameTab() throws InterruptedException, UiObjectNotFoundException {
        home();
        moveToLauncherTargetTab(gameTabName);
    }

    /**
     * Goto my game app page
     */
    public void gotoMyGameAppPage() throws UiObjectNotFoundException {
        UiObject myGameCardObj = findElementByText("我的游戏","com.bestv.ott:id/title");
        myGameCardObj.click();
        myGameCardObj.clickAndWaitForNewWindow();
    }

    /**
     * Method for navigating to MyGame App lisy page by clicking myGame card under game tab page
     */
    public void gotoMyGamePage() throws InterruptedException, UiObjectNotFoundException {
        UiObject myGameCardObj = findElementByText("我的游戏","com.bestv.ott:id/title");
        myGameCardObj.click();
        myGameCardObj.clickAndWaitForNewWindow();
    }

    /**
     * Goto app detail page from PHB in Game tab page
     */
    public void gotoAppDetailPageFromPHBUnderGameTab() throws UiObjectNotFoundException, InterruptedException {
        UiObject phbListObj = findElementByID("com.bestv.ott:id/rank_scroll_list");
        UiObject phbListChildsObj = phbListObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
        UiObject phbListChildEle = phbListChildsObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
        phbListChildEle.click();
        phbListChildEle.click();
        enter();
        waitForAppDetailPageDisplay();
    }

    /**
     * Goto App detail page by pressing enter btn on app card
     */
    public void gotoAppDetailPageByKeyEvent() throws InterruptedException {
        device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
        waitForAppDetailPageDisplay();
    }

    /**
     * Goto App detail page by pressing enter btn on app card
     */
    public void gotoAppDetailPageByEnter() throws InterruptedException {
        enter();
        waitForAppDetailPageDisplay();
    }


    /**
     * Method for navigating to Game Lobby by clicking gamelobby card under game tab page
     */
    public void gotoGameLobbyPage() throws InterruptedException, UiObjectNotFoundException {
        if(!findElementByText(gameLobbyCardName, "com.bestv.ott:id/title").exists()){
            navigateToLauncherGameTab();
        }
        UiObject gameLobbyEle = findElementByText(gameLobbyCardName, "com.bestv.ott:id/title");
        gameLobbyEle.click();
        gameLobbyEle.clickAndWaitForNewWindow();
    }

    /**
     * Method for clicking search icon in game lobby page
     */
    public void gotoGameSearchPage() throws InterruptedException, UiObjectNotFoundException {
        UiObject gameSearchIconObj = findElementByID(gameSearchIconLocatorID);
        gameSearchIconObj.click();

        waitForElementPresentByID(searchKeyInputObjLocaotorID);
    }

    /**
     * Move to target tab
     */
    public void moveFromCurrentTabToTargetTab(String targetTab) throws UiObjectNotFoundException {
        //移动焦点到目标tab
        moveDown();
        moveRight();
        UiObject targetTabObj = findElementByText(targetTab, gameLobbyTabColLocator);
        if(!targetTabObj.isSelected()){
            moveUpForMultiple(2);
            UiObject currentFocusObj = device.findObject(new UiSelector().resourceId(gameLobbyTabColLocator).selected(true));
            String currentTab = currentFocusObj.getText();
            moveFromCurrentTabToTargetTab(tabsOfGameLobby, currentTab, targetTab);
        }
    }

    /**
     * Move to game search icon from other tab in game lobby page
     */
    public void moveFromCurrentTabToGameSearch() throws UiObjectNotFoundException {
        //移动焦点到目标tab
        moveDown();
        UiObject targetTabObj = findElementByID(gameSearchIconLocatorID);
        if(!targetTabObj.isFocused()){
            moveUpForMultiple(2);
            UiObject currentFocusObj = device.findObject(new UiSelector().resourceId(gameLobbyTabColLocator).selected(true));
            String currentTab = currentFocusObj.getText();
            moveFromCurrentTabToTargetTab(tabsOfGameLobbyWithSearch, currentTab, tabsOfGameLobbyWithSearch[0]);
        }
    }

    /**
     * Goto game search page by pressing search icon in game lobby page
     */
    public void gotoGameSearchPageByPressSearchIcon() throws UiObjectNotFoundException, InterruptedException {
        enter();
        waitForElementPresentByID(searchInputObjLocatorID);
    }

    /**
     * Click one app under one PHB in GameLobby page, and goto app detail page
     */
    public String gotoAppDetailPageByClickingAppUnderGameLobbyPHB(String phbListLocatorID) throws UiObjectNotFoundException, InterruptedException {
        UiObject pHBAppObj = findElementByID(phbListLocatorID).getChild(new UiSelector().className(nodeNormalClassName).index(0));
        UiObject pHBAppNameObj = pHBAppObj.getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
        String appNameOfHPHB = pHBAppNameObj.getText();
        pHBAppObj.click();
        waitForAppDetailPageDisplay();
        return appNameOfHPHB;
    }

    /**
     * Click start btn in app detail page to install app
     */
    public void pressStartToInstallInAppDetailPage() throws InterruptedException {
        //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
        moveDown();
        moveUp();
        if(!findElementByID(appDetailPageOpenBtnText).exists()) {
            enter();
            waitForElementNotPresent(appDetailPageInstallBtnText);
        }
    }

    /**
     * Goto sdk login page from game lobby page
     */
    public void gotoSDKLoginPageFromGameLobby() throws InterruptedException, UiObjectNotFoundException {
        UiObject sdkLoginObj = findElementByID(gameLobbySDKLoginCardLocatorID);
        sdkLoginObj.click();
        waitForElementPresentByID(sdkLoginPageTitleLocatorID);
    }

    /**
     * Get installed game app num from subtitle in my game page
     */
    public int getInstalledGameAppNumFromMyGameSubTitle() throws UiObjectNotFoundException {
        UiObject subTitleObj = findElementByID(myGamePageSubTitleLocatorID);
        String subTitle = subTitleObj.getText();
        String countStr = subTitle.replace(myGamePageSubTitleChildStrs[1], "").replace(myGamePageSubTitleChildStrs[0], "");
        int appNum = Integer.valueOf(countStr);
        return appNum;
    }
}
