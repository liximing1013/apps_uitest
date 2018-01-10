package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.appstoretest.common.GameLobbyPage;
import tv.fun.common.Utils;

/**
 * Created by liuqing on 2018/1/1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGameLobby extends GameLobbyPage{

    /*
     *Game_Tab_01:Launcher下游戏tab首页默认UI显示
     */
    @Test
    public void Game_Tab_01_testGameTabPageUI(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            UiObject pageEles = findElementByID(gameTabPageGridLocatorID);
            UiObject childEleObj = pageEles.getChild(new UiSelector().className(nodeNormalClassName));
            //assert
            int eleCount = pageEles.getChildCount();
            verifyNumber("", eleCount, cardNumUnderGameTab);
            UiObject myGameObj = pageEles.getChild(new UiSelector().className(nodeNormalClassName).index(orderOfMyGameCardInPage-1));
            UiObject myGameEle = myGameObj.getChild(new UiSelector().resourceId(gameTabPageEleTitleLocator));
            verifyString("", myGameEle.getText(), myGameCardName);
            UiObject gameLobbyObj = pageEles.getChild(new UiSelector().className(nodeNormalClassName).index(orderOfGameLobbyCardInPage-1));
//            UiObject gameLobbyEle = gameLobbyObj.getChild(new UiSelector().resourceId("com.bestv.ott:id/title"));
//            String test = gameLobbyObj.getText();
//            verifyString("", gameLobbyObj.getText(), gameLobbyCardName);
            UiObject gameLobbyEle = findElementByText(gameLobbyCardName, gameTabPageEleTitleLocator);
            verifyElementPresent("", gameLobbyEle);
            UiObject gamePHBObj = pageEles.getChild(new UiSelector().className(nodeNormalClassName).index(eleCount-1));
            UiObject gameRankListObj = gamePHBObj.getChild(new UiSelector().resourceId(gameTabPHBRankListLocatorID));
            verifyElementPresent("", gameRankListObj);
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
     * Game_Tab_24:排行榜UI显示正确
     */
    @Test
    public void Game_Tab_24_testPHBUIDisplay(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            UiObject phbObj = findElementByID(gameTabPHBRankListLocatorID);
            //assert
            UiObject listTitleObj = phbObj.getChild(new UiSelector().resourceId(gameTabPageEleTitleLocator));
            verifyString("", listTitleObj.getText(), gameTabPhbListTitle);
            UiObject phbListObj = findElementByID(gameTabPHBScrollListLocatorID);
            UiObject phbListChildObj = phbListObj.getChild(new UiSelector().className(childNodeNormalClassName));
            int appCount = phbListChildObj.getChildCount();
            verifyNumberLarger("", appCount, 1);
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
     * Game_Tab_28:点击排行榜应用可以正常进入详情页
     */
    @Test
    public void Game_Tab_28_testGotoDetailPageFromPHB(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            UiObject phbListObj = findElementByID(gameTabPHBScrollListLocatorID);
            UiObject phbListChildsObj = phbListObj.getChild(new UiSelector().className(childNodeNormalClassName));
            UiObject phbListChildEle = phbListChildsObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            phbListChildEle.click();
            phbListChildEle.click();
            enter();
            //assert
            String publicText = findElementByID(appDetailPagePublicTimeLocatorID).getText();
            verifyIncludeString("", publicText, appDetailPagePublicText);
            UiObject haiBaoObj = findElementByID(appDetailPagePosterLocatorID);
            UiObject detailInfo = findElementByID(appDetailPageBriefLocatorID);
            UiObject downloadCountObj = findElementByID(appDetailPageDownloadCountLocatorID);
            verifyElementPresent("", haiBaoObj);
            verifyElementPresent("", detailInfo);
            verifyElementPresent("", downloadCountObj);
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
    * Game_Tab_29: 可以正常安装排行榜中的应用
    */
    @Test
    public void Game_Tab_29_testInstallAppInGamePagePHB() {
        int installedAppCount = 0;
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入我的游戏页
            gotoMyGamePage();
            //卸载已安装的应用
            if(!findElementByID(myGameNoAppPageLocatorID).exists()){
                gotoAppCleanPageFromAppListByMenu();
                uninstallAllAppFromAppCleanPage();
            }
            if(findElementByID(myGameNoAppPageLocatorID).exists()) {
                installedAppCount = 0;
            }else {
                UiObject subTitleObj = findElementByID(myGamePageSubTitleLocatorID);
                String subTitle = subTitleObj.getText();
                String countStr = subTitle.replace(myGamePageSubTitleChildStrs[1], "").replace(myGamePageSubTitleChildStrs[0], "");
                installedAppCount = Integer.valueOf(countStr);
            }
            back();
            //点击游戏tab页下载排行榜区域的app, 进入其详情页
            gotoAppDetailPageFromPHBUnderGameTab();
            //安装应用
            String appName = findElementByID(appDetailPageAppNameLocatorID).getText();
            if (!device.findObject(new UiSelector().resourceId(appDetailPageButtonLocatorID).text(appDetailPageInstallBtnText)).exists()) {
                back();
                gotoMyGameAppPage();
                gotoAppCleanPageFromAppListByMenu();
                uninstallAppFromAppCleanPage(appName);
                back();
                gotoAppDetailPageFromPHBUnderGameTab();
            }
            installAppInDetailPage();
            UiObject openBtn = findElementByID(getAppDetailPageButtonParentLocatorID).getChild(new UiSelector().resourceId(appDetailPageButtonLocatorID));
            //断言
            verifyTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase(appDetailPageOpenBtnText));
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
     *  Game_Tab_33:可以正常进入我的游戏页面且UI显示正确
     */
    @Test
    public void Game_Tab_33_testGotoMyGameFromGameTabPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入我的游戏页面
            gotoMyGamePage();
            UiObject listObj = findElementByID(myGamePageListLocatorID);
            UiObject titleObj = findElementByText(myGameCardName, appDetailPageAppNameLocatorID);

            //Assert
            verifyElementPresent("", listObj);
            verifyElementPresent("", titleObj);
            if(findElementByID(myGameNoAppPageLocatorID).exists()){
                UiObject msgObj = findElementByID(myGameNoAppPageLocatorID);
                String msgText = msgObj.getText();
                verifyString("", msgText, myGameNoAppPageMsg);
            }else{
               UiObject appNameObj = findElementByID(myGamePageAppNameLocator);
                verifyElementPresent("", appNameObj);
               UiObject subTitleObj =findElementByID(myGamePageSubTitleLocatorID);
                String subTitleStr = subTitleObj.getText();
                verifyIncludeString("", subTitleStr, myGamePageSubTitleChildStrs[1]);
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
     * Game_Tab_34:可以正常进入游戏大厅页面且UI显示正确
     */
    @Test
    public void Game_Tab_34_testGotoGameLobbyPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            UiObject gameSearchObj = findElementByID(gameSearchIconLocatorID);
            UiObject firstTabObj = findElementByText(tabsOfGameLobby[0], gameLobbyTabColLocator);
            UiObject secTabObj = findElementByText(tabsOfGameLobby[1], gameLobbyTabColLocator);
            UiObject thirTabObj = findElementByText(tabsOfGameLobby[2], gameLobbyTabColLocator);
            UiObject fifTabObj = findElementByText(tabsOfGameLobby[3], gameLobbyTabColLocator);
            verifyElementPresent("", gameSearchObj);
            verifyElementPresent("", firstTabObj);
            verifyElementPresent("", secTabObj);
            verifyElementPresent("", thirTabObj);
            verifyElementPresent("", fifTabObj);
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
     * Game_Home_1: 游戏大厅首页UI显示正确
     */
    @Test
    public void Game_Home_01_testGameLobbyPageUIDisplay(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //Assert
            UiObject searchIconObj = findElementByID(gameSearchIconLocatorID);
            verifyElementPresent("", searchIconObj);
            UiObject titleColObj = findElementByID(gameLobbyTabTitleLocator);
            int tabCount = titleColObj.getChildCount();
            verifyNumber("", tabCount, tabsOfGameLobby.length);
            for(int i=0; i<tabCount; i++){
                UiObject childColObj = titleColObj.getChild(new UiSelector().className(nodeNormalClassName).index(i));
                UiObject eachTabObj = childColObj.getChild(new UiSelector().resourceId(gameLobbyTabColLocator));
                String tabStr = eachTabObj.getText();
                verifyString("", tabStr, tabsOfGameLobby[i]);
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
     * Game_Home_5:游戏大厅首页不会响应Menu键操作
     */
    @Test
    public void Game_Home_05_testNoMenuActionInGameLobbyPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //向下移动焦点到推荐位
            moveDownForMultiple(2);
            //按Menu键
            menu();
            Thread.sleep(500);
            //assert
            verifyElementNotPresent("", menuPopupLocatorID);
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
     * Game_Home_10:可以正常响应Back键操作
     */
    @Test
    public void Game_Home_10_testPressBackInGameLobbyPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            UiObject eleInGameLobby = findElementByText(tabsOfGameLobby[2], gameLobbyTabColLocator);
            verifyElementPresent("", eleInGameLobby);
            //按遥控器Back键
            back();
            //assert
            verifyElementNotPresent("", findElementByText(tabsOfGameLobby[2], gameLobbyTabColLocator));
            UiObject gameLobbyCardObj = findElementByText(gameLobbyCardName, gameTabPageEleTitleLocator);
            verifyElementPresent("", gameLobbyCardObj);
            verifyTrue("", gameLobbyCardObj.isSelected());
            UiObject phbObj = findElementByText(gameTabPhbListTitle, gameTabPageEleTitleLocator);
            verifyElementPresent("", phbObj);
            verifyElementPresent("", gameTabPHBScrollListLocatorID);
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
     * Game_Home_11: 可以正常响应Home键操作
     */
    @Test
    public void Game_Home_11_testPressHomeInGameLobbyPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            UiObject eleInGameLobby = findElementByText(tabsOfGameLobby[2], gameLobbyTabColLocator);
            verifyElementPresent("", eleInGameLobby);
            //按遥控器home键
            home();
            //assert
            UiObject tvCard = findElementByText(launcherDSJCardEleText, gameTabPageEleTitleLocator);
            UiObject videoTabObj = findElementByText(videoTab, launcherTabID);
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
     *  Game_Home_16_推荐tab页面UI显示正确
     */
    @Test
    public void Game_Home_16_testJingPinTabUIDisplay(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //展开精品tab页面
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[0]);
            //assert
            UiObject firstColObj = findElementByID(firstColLocatorUnderJingpin);
            int suggestCardNum = firstColObj.getChildCount();
            UiObject secondColObj = findElementByID(secColLocatorUnderJingpin);
            int secSuggestAppNum = secondColObj.getChildCount();
            verifyNumber("", suggestCardNum, suggestAppCountInFirstColUnderJingpin);
            verifyNumber("", secSuggestAppNum,suggestAppCountInSecColUnderJingpin);
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
     * Game_Home_25: 点击推荐应用可以正常进入详情页
     */
    @Test
    public void Game_Home_25_testGotoAppDetailFromAppUnderJingpinTab(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //展开精品tab页面
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[0]);
            //在精品tab下点击任一推荐应用
            moveDownForMultiple(2);
            enter();
            waitForAppDetailPageDisplay();
            //assert
            String publicText = findElementByID(appDetailPagePublicTimeLocatorID).getText();
            verifyIncludeString("", publicText, appDetailPagePublicText);
            UiObject haiBaoObj = findElementByID(appDetailPagePosterLocatorID);
            UiObject detailInfo = findElementByID(appDetailPageBriefLocatorID);
            UiObject downloadCountObj = findElementByID(appDetailPageDownloadCountLocatorID);
            verifyElementPresent("", haiBaoObj);
            verifyElementPresent("", detailInfo);
            verifyElementPresent("", downloadCountObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    @Test
    public void Game_Home_26_testInstallAppUnderJingpinTab(){
        int installedAppCount = 0;
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入我的游戏，查看已有游戏数量
            gotoMyGamePage();
            //卸载已安装的应用
            if(!findElementByID(myGameNoAppPageLocatorID).exists()){
                gotoAppCleanPageFromAppListByMenu();
                uninstallAllAppFromAppCleanPage();
            }
            if(findElementByID(myGameNoAppPageLocatorID).exists()) {
                installedAppCount = 0;
            }else {
                UiObject subTitleObj = findElementByID(myGamePageSubTitleLocatorID);
                String subTitle = subTitleObj.getText();
                String countStr = subTitle.replace(myGamePageSubTitleChildStrs[1], "").replace(myGamePageSubTitleChildStrs[0], "");
                installedAppCount = Integer.valueOf(countStr);
            }
            back();
            //进入游戏大厅
            gotoGameLobbyPage();
            //展开精品tab页面
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[0]);
            //在精品tab下点击任一推荐应用
            moveDownForMultiple(2);
            enter();
            waitForAppDetailPageDisplay();
            installAppInDetailPage();
            UiObject openBtn = device.findObject(new UiSelector().resourceId(getAppDetailPageButtonParentLocatorID)).getChild(new UiSelector().resourceId(appDetailPageButtonLocatorID));
            //断言
            verifyTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase(appDetailPageOpenBtnText));
            backForMultiple(2);
            gotoMyGamePage();
            UiObject subTitleEle = findElementByID(myGamePageSubTitleLocatorID);
            String subTitleStr = subTitleEle.getText();
            String actualCountStr = subTitleStr.replace(myGamePageSubTitleChildStrs[1], "").replace(myGamePageSubTitleChildStrs[0], "");
            verifyNumber("", Integer.valueOf(actualCountStr), installedAppCount+1);
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
     *  Game_Home_38: 排行榜tab页UI显示正确
     */
    @Test
    public void Game_Home_38_testPHBTabPageUI(){
        Boolean flag = false;
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到排行榜tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[1]);
            //Assert
            //下载排行榜
            UiObject downloadRankObj = findElementByID(gameLobbyDPHBColLocatorID);
            UiObject downloadRankCardObj = downloadRankObj.getChild(new UiSelector().resourceId(gameLobbyDPHBIconLocatorID));
            UiObject downloadRankListObj = findElementByID(gameLobbyDPHBListLocatorID);
            int downloadRankAppNum = downloadRankListObj.getChildCount();
            UiObject firstDownloadRankApp = downloadRankListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            UiObject iconOfAppInDRank = firstDownloadRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppIconLocatorID));
            UiObject appNameInDRank = firstDownloadRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            UiObject appRanItemInDRank = firstDownloadRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppRankLocatorID));
            flag = verifyElementPresent("", downloadRankCardObj);
            flag = verifyNumberLarger("", downloadRankAppNum, 1);
            flag = verifyElementPresent("", iconOfAppInDRank);
            flag = verifyElementPresent("", appNameInDRank);
            flag = verifyElementPresent("", appRanItemInDRank);
            //热门排行榜
            UiObject hotRankObj = findElementByID(gameLobbyHPHBColLocatorID);
            UiObject hotRankCardObj = hotRankObj.getChild(new UiSelector().resourceId(gameLobbyHPHBIconLocatorID));
            UiObject hotRankListObj = findElementByID(gameLobbyHPHBListLocatorID);
            int hotRankAppNum = hotRankListObj.getChildCount();
            UiObject firstHotRankApp = hotRankListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            UiObject iconOfAppInHRank = firstHotRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppIconLocatorID));
            UiObject appNameInHRank = firstHotRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            UiObject appRanItemInHRank = firstHotRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppRankLocatorID));
            flag = verifyElementPresent("", hotRankCardObj);
            flag = verifyNumberLarger("", hotRankAppNum, 1);
            flag = verifyElementPresent("", iconOfAppInHRank);
            flag = verifyElementPresent("", appNameInHRank);
            flag = verifyElementPresent("", appRanItemInHRank);
            //新秀排行榜
            UiObject newRankObj = findElementByID(gameLobbyNPHBColLocatorID);
            UiObject newRankCardObj = newRankObj.getChild(new UiSelector().resourceId(gameLobbyNPHBIconLocatorID));
            UiObject newRankListObj = findElementByID(gameLobbyNPHBListLocatorID);
            int newRankAppNum = newRankListObj.getChildCount();
            UiObject firstNewRankApp = newRankListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            UiObject iconOfAppInNRank = firstNewRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppIconLocatorID));
            UiObject appNameInNRank = firstNewRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            UiObject appRanItemInNRank = firstNewRankApp.getChild(new UiSelector().resourceId(gameLobbyPHBAppRankLocatorID));
            flag = verifyElementPresent("", newRankCardObj);
            flag = verifyNumberLarger("", newRankAppNum, 1);
            flag = verifyElementPresent("", iconOfAppInNRank);
            flag = verifyElementPresent("", appNameInNRank);
            flag = verifyElementPresent("", appRanItemInNRank);
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
     * Game_Home_44:点击排行榜中任一应用可以正常进入详情页
     */
    @Test
    public void Game_Home_44_testGotoAppDetailFromEachPHB(){
        try {
           //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到排行榜tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[1]);
            //点击下载排行榜下任一应用
            UiObject dPHBAppObj = findElementByID(gameLobbyDPHBListLocatorID).getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            String appNameOfDPHB = dPHBAppObj.getText();
            dPHBAppObj.click();
            //Assert
            String publicText = findElementByID(appDetailPagePublicTimeLocatorID).getText();
            verifyIncludeString("", publicText, appDetailPagePublicText);
            UiObject haiBaoObj = findElementByID(appDetailPagePosterLocatorID);
            UiObject detailInfo = findElementByID(appDetailPageBriefLocatorID);
            UiObject downloadCountObj = findElementByID(appDetailPageDownloadCountLocatorID);
            UiObject appNameObj = findElementByID(appDetailPageAppNameLocatorID);
            verifyElementPresent("", haiBaoObj);
            verifyElementPresent("", detailInfo);
            verifyElementPresent("", downloadCountObj);
            verifyString("", appNameObj.getText(), appNameOfDPHB);

            //回到游戏大厅，点击上升排行榜下任一应用
            back();
            UiObject hPHBAppObj = findElementByID(gameLobbyHPHBListLocatorID).getChild(new UiSelector().className(nodeNormalClassName).index(0));
            UiObject hPHBAppNameObj = hPHBAppObj.getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            String appNameOfHPHB = hPHBAppNameObj.getText();
            hPHBAppObj.click();
            //Assert
            UiObject detailInfoOfHPHB = findElementByID(appDetailPageBriefLocatorID);
            verifyElementPresent("", detailInfoOfHPHB);
            UiObject appNameObjOfHPHB = findElementByID(appDetailPageAppNameLocatorID);
            verifyString("", appNameObjOfHPHB.getText(), appNameOfHPHB);

            //回到游戏大厅，点击新秀榜下任一应用
            back();
            UiObject nPHBAppObj = findElementByID(gameLobbyNPHBListLocatorID).getChild(new UiSelector().resourceId(gameLobbyPHBAppNameLocatorID));
            String appNameOfNPHB = nPHBAppObj.getText();
            nPHBAppObj.click();
            //Assert
            UiObject detailInfoOfNPHB = findElementByID(appDetailPageBriefLocatorID);
            verifyElementPresent("", detailInfoOfNPHB);
            UiObject appNameObjOfNPHB = findElementByID(appDetailPageAppNameLocatorID);
            verifyString("", appNameObjOfNPHB.getText(), appNameOfNPHB);
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
     * Game_Home_45:可以正常安装排行榜应用
     */
    @Test
    public void Game_Home_45_testInstallAppFromPHBInGameLobby(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到排行榜tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[1]);
            //点击上升排行榜下任一应用
            String appNameOfHPHB = gotoAppDetailPageByClickingAppUnderGameLobbyPHB(gameLobbyHPHBListLocatorID);
            //安装应用
            String appName = findElementByID(appDetailPageAppNameLocatorID).getText();
            if (!device.findObject(new UiSelector().resourceId(appDetailPageButtonLocatorID).text(appDetailPageInstallBtnText)).exists()) {
                back();
                gotoMyGameAppPage();
                gotoAppCleanPageFromAppListByMenu();
                uninstallAppFromAppCleanPage(appName);
                back();
                //进入游戏大厅
                gotoGameLobbyPage();
                //移动焦点到排行榜tab
                moveFromCurrentTabToTargetTab(tabsOfGameLobby[1]);
                gotoAppDetailPageByClickingAppUnderGameLobbyPHB(gameLobbyHPHBListLocatorID);
            }
            installAppInDetailPage();
            UiObject openBtn = findElementByID(getAppDetailPageButtonParentLocatorID).getChild(new UiSelector().resourceId(appDetailPageButtonLocatorID));
            //断言
            verifyTrue("Failed to install app from GameLobby PHB tab page", openBtn.getText().equalsIgnoreCase(appDetailPageOpenBtnText));
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
     * Game_Home_48: 遥控器tab页面UI显示正确
     */
    @Test
    public void Game_Home_48_testTelecontrollerPageUI(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //Assert
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            int childEnterCount = childEnterListObj.getChildCount();
            verifyNumber("", childEnterCount, childEnterNumUnderYKQTab);
            UiObject firstEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            UiObject firstEnterTextObj = firstEnterObj.getChild(new UiSelector().resourceId(appDetailPageAppNameLocatorID));
            verifyString("", firstEnterTextObj.getText(), childEntersNameUnderYKQ[0]);
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
     * Game_Home_54: 点击遥控器tab页面任一推荐应用可以正常进入详情页
     */
    @Test
    public void Game_Home_54_testGotoAppDetailFromYKQTab(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到推荐应用，并点击进入详情页
            moveDown();
            moveRight();
            enter();
            waitForAppDetailPageDisplay();
            //Assert
            String publicText = findElementByID(appDetailPagePublicTimeLocatorID).getText();
            verifyIncludeString("", publicText, appDetailPagePublicText);
            UiObject haiBaoObj = findElementByID(appDetailPagePosterLocatorID);
            UiObject detailInfo = findElementByID(appDetailPageBriefLocatorID);
            UiObject downloadCountObj = findElementByID(appDetailPageDownloadCountLocatorID);
            verifyElementPresent("", haiBaoObj);
            verifyElementPresent("", detailInfo);
            verifyElementPresent("", downloadCountObj);
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
     * Game_Home_55:可以正常安装应用
     */
    @Test
    public void Game_Home_55_testInstallAppUnderYKQTab(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到推荐应用，并点击进入详情页
            moveDown();
            moveRight();
            enter();
            waitForAppDetailPageDisplay();
            String appName = findElementByID(appDetailPageAppNameLocatorID).getText();
            if (!device.findObject(new UiSelector().resourceId(appDetailPageButtonLocatorID).text(appDetailPageInstallBtnText)).exists()) {
                back();
                gotoMyGameAppPage();
                gotoAppCleanPageFromAppListByMenu();
                uninstallAppFromAppCleanPage(appName);
                back();
                //进入游戏大厅
                gotoGameLobbyPage();
                //移动焦点到遥控器tab
                moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
                //移动焦点到推荐应用，并点击进入详情页
                moveDown();
                moveRight();
                enter();
                waitForAppDetailPageDisplay();
            }
            installAppInDetailPage();
            UiObject openBtn = findElementByID(getAppDetailPageButtonParentLocatorID).getChild(new UiSelector().resourceId(appDetailPageButtonLocatorID));
            //断言
            verifyTrue("Failed to install app from Game Lobby YKQ tab page", openBtn.getText().equalsIgnoreCase(appDetailPageOpenBtnText));
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
     * Game_Home_64:点击任一子入口可以正常进入列表页
     */
    @Test
    public void Game_Home_64_testGotoChildListPageFromYKQTab(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            //Assert列表页显示
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            UiObject listAppPageTitleObj = findElementByID(listAppPageTitleLocatorID);
            String listPageTitle = listAppPageTitleObj.getText();
            verifyElementPresent("", listAppPageListObj);
            verifyString("", listPageTitle, firstChildEnterName);
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
     * Game_List_1:列表页UI显示正确
     */
    @Test
    public void Game_List_01_testGameListPageUIDisplay(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            //Assert
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            int appCountInList =listAppPageListObj.getChildCount();
            UiObject listAppPageTitleObj = findElementByID(listAppPageTitleLocatorID);
            String listPageTitle = listAppPageTitleObj.getText();
            verifyNumberLarger("", appCountInList, 1);
            verifyString("", listPageTitle, firstChildEnterName);
            if(appCountInList>12){
               verifyElementPresent("", scrollBarInListAppPageLocatorID);
            }
            //向下，保证有app被选中
            moveDown();
            moveRight();
            UiObject selectedAppObj = device.findObject(new UiSelector().resourceId(appNameObjInListPageLocator).selected(true));
            String selectedAppName = selectedAppObj.getText();

            UiObject appStatusInfoObj = findElementByID(appShortInfoObjLocatorID);
            UiObject appPrefixInShortInfoObj = findElementByID(appPrefixInShortInfoLocatorID);
            String appPrefixStr = appPrefixInShortInfoObj.getText().replace(" ", "");
            String appNameInShortInfo = appPrefixStr.split("　|　")[0];
            String downloadStrInShortInfo = appPrefixStr.split("　|　")[2];
            UiObject appRateInfoObj = findElementByID(appRateInShortInfoLocatorID);
            UiObject appSuffixInfoObj = findElementByID(appSuffixInShortInfoLocatorID);//|　冰雪世界的爽快竞速项目
            verifyElementPresent("", appStatusInfoObj);
            verifyElementPresent("", appRateInfoObj);
            verifyElementPresent("", appSuffixInfoObj);
            verifyString("", appNameInShortInfo, selectedAppName);

            //点击当前的应用，进入详情页核对信息
            enter();
            UiObject appNameInAppDetailObj = findElementByID(appDetailPageAppNameLocatorID);
            String appNameInAppDetail = appNameInAppDetailObj.getText();
            String appDownloadCountStr = findElementByID(appDetailPageDownloadCountLocatorID).getText();//下载：462万+
            verifyString("", appNameInAppDetail, appNameInShortInfo);
            verifyString("", appDownloadCountStr.split("：")[1] + "下载", downloadStrInShortInfo);
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
     * Game_List_2:遥控器列表页中显示的应用都是有遥控器操作方式的游戏应用
     */
    //@Test
    public void Game_List_02_testOnlyHaveYKQControlAppInGameListPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            //Assert
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            int appCountInList =listAppPageListObj.getChildCount();
            UiObject listAppPageTitleObj = findElementByID(listAppPageTitleLocatorID);
            String listPageTitle = listAppPageTitleObj.getText();
            verifyNumberLarger("", appCountInList, 1);
            verifyString("", listPageTitle, firstChildEnterName);

            //TODO

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
     * Game_List_7:列表页可以正常响应Menu键操作
     */
    @Test
    public void Game_List_07_testPressMenuInGameListPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上， 并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
           //按遥控器Menu键
            menu();
            //Assert
            UiObject menuPopup = findElementByID(menuPopupLocatorID);
            int btnCountInMenuPopup = menuPopup.getChildCount();
            UiObject btnObjInMenuPopup = findElementByID(btnTextInMenuPopupLocatorID);
            String btnName = btnObjInMenuPopup.getText();
            verifyElementPresent("", menuPopup);
            verifyNumber("", btnCountInMenuPopup, 1);
            verifyString("", btnName, btnNameInListPageMenuPopup);

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
     * Game_List_8:在列表页，通过Menu键可以进入搜索页面
     */
    @Test
    public void Game_List_08_testGotoSearchPageFromGameListPageByMenu(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上， 并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            //按遥控器Menu键,并点击弹框上搜索按钮
            menu();
            UiObject btnObjInMenuPopup = findElementByID(btnTextInMenuPopupLocatorID);
            btnObjInMenuPopup.clickAndWaitForNewWindow();

            //Assert
            UiObject keyboardObjInSearchPage = findElementByID(searchKeyboardObjLocatorID);
            UiObject defaultSearchResultObj = findElementByID(searchResultAreaTitleLocatorID);
            verifyElementPresent("", keyboardObjInSearchPage);
            verifyElementPresent("", defaultSearchResultObj);
            verifyString("", defaultSearchResultObj.getText(), searchResultDefaultTitleText);
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
     * Game_List_9:可以通过Back键取消Menu弹框
     */
    @Test
    public void Game_List_09_testPressBackToCancelMenuPopup(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上， 并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            //按遥控器Menu键
            menu();
            UiObject menuPopup = findElementByID(menuPopupLocatorID);
            verifyElementPresent("", menuPopup);

            //按遥控器back键取消menu弹框
            back();
            //Assert
            verifyElementNotPresent("", menuPopupLocatorID);
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
     * Game_List_10:列表页可以正常响应Back键操作
     */
    @Test
    public void Game_List_10_testPressBackInGameListPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上,并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            verifyElementPresent("", listAppPageListObj);
            //按遥控器back键
            back();
            //Assert
            verifyElementNotPresent("", listAppViewObjLocatorID);
            UiObject phbTabObj = findElementByText(tabsOfGameLobby[1], gameLobbyTabColLocator);
            UiObject gameSearchObj = findElementByID(gameSearchIconLocatorID);
            verifyElementPresent("", gameSearchObj);
            verifyElementPresent("", phbTabObj);
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
     * Game_List_12:列表页可以正常响应Home键操作
     */
    @Test
    public void Game_List_12_testPressHomeInGameListPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上,并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            verifyElementPresent("", listAppPageListObj);
            //按遥控器Home键
            home();
            //Assert
            UiObject tvCard = findElementByText(launcherDSJCardEleText, gameTabPageEleTitleLocator);
            UiObject videoTabObj = findElementByText(videoTab, launcherTabID);
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
     * Game_List_13:可以正常安装应用
     */
    @Test
    public void Game_List_13_testInstallAppInGameListPage(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上,并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            String firstChildEnterName = childEnterObj.getChild(new UiSelector().resourceId(telControlChildEnterNameLocatorID)).getText();
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            verifyElementPresent("", listAppPageListObj);

            //向下，保证有app被选中
            moveDown();
            moveRight();
//            UiObject selectedAppObj = device.findObject(new UiSelector().resourceId(appNameObjInListPageLocator).selected(true));
//            String selectedAppName = selectedAppObj.getText();
            //进入此应用详情页
            enter();
            waitForAppDetailPageDisplay();

            String appName = findElementByID(appDetailPageAppNameLocatorID).getText();
            Boolean flag = false;
            if (!device.findObject(new UiSelector().resourceId(appDetailPageButtonLocatorID).text(appDetailPageInstallBtnText)).exists()) {
                back();
                for(int i=0; i<6; i++){
                    moveRight();
                    enter();
                    if (device.findObject(new UiSelector().resourceId(appDetailPageButtonLocatorID).text(appDetailPageInstallBtnText)).exists()){
                        flag = true;
                        break;
                    }else{
                        back();
                    }
                }
                if(!flag){
                    backForMultiple(3);
                    gotoMyGameAppPage();
                    gotoAppCleanPageFromAppListByMenu();
                    uninstallAppFromAppCleanPage(appName);
                    backForMultiple(2);
                    //进入游戏大厅
                    gotoGameLobbyPage();
                    //移动焦点到遥控器tab
                    moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
                    //移动焦点到子入口卡片上,并点击进入列表页
                    moveDown();
                    childEnterObj.clickAndWaitForNewWindow();
                    //选择应用，点击进入详情页
                    UiObject targetAppObj = findElementByText(appName, appNameObjInListPageLocator);
                }
            }
            installAppInDetailPage();
            UiObject openBtn = findElementByID(getAppDetailPageButtonParentLocatorID).getChild(new UiSelector().resourceId(appDetailPageButtonLocatorID));
            //断言
            verifyTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase(appDetailPageOpenBtnText));
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
     * Game_Install_1:每次只允许同时下载两个应用，其他等待下载
     */
    //@Test
    public void Game_Install_01_testOnlyAllowDownloadTwoAppsAtSameTime(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入我的游戏
            gotoMyGamePage();
            //卸载所有个游戏应用
            gotoAppCleanPageFromAppListByMenu();
            uninstallAllAppFromAppCleanPage();
            backForMultiple(2);
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上,并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            verifyElementPresent("", listAppPageListObj);

            //同时只允许下载两个应用，其他等待下载
            moveDown();
            moveRight();
            UiObject selectedAppOneObj = device.findObject(new UiSelector().resourceId(appNameObjInListPageLocator).selected(true));
            String selectedAppOneName = selectedAppOneObj.getText();
            enter();
            UiObject appNameOfAppOneObj = findElementByID(appDetailPageAppNameLocatorID);
            String appNameOfAppOne = appNameOfAppOneObj.getText();
            pressStartToInstallInAppDetailPage();
            back();


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
     * Game_Install_2:下载中的应用可以暂停下载
     */
    @Test
    public void Game_Install_02_testPauseTheDownloadingApp(){
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入我的游戏
            gotoMyGamePage();
            //卸载所有个游戏应用
            if(!findElementByID(myGameNoAppPageLocatorID).exists()) {
                gotoAppCleanPageFromAppListByMenu();
                uninstallAllAppFromAppCleanPage();
            }
            backForMultiple(2);
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到遥控器tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[2]);
            //移动焦点到子入口卡片上,并点击进入列表页
            moveDown();
            UiObject childEnterListObj = findElementByID(telControlChildEnterListLocatorID);
            UiObject childEnterObj = childEnterListObj.getChild(new UiSelector().className(nodeNormalClassName).index(0));
            childEnterObj.clickAndWaitForNewWindow();
            waitForElementPresentByID(listAppViewObjLocatorID);
            UiObject listAppPageListObj = findElementByID(listAppViewObjLocatorID);
            verifyElementPresent("", listAppPageListObj);

            //暂停下载应用
            moveDown();
            moveRight();
            for(int i=0; i<10; i++) {
                if (!device.findObject(new UiSelector().resourceId(appNameObjInListPageLocator).selected(true)).exists()) {
                    moveRight();
                }else{
                    break;
                }
            }
            UiObject selectedAppOneObj = device.findObject(new UiSelector().resourceId(appNameObjInListPageLocator).selected(true));
            String selectedAppOneName = selectedAppOneObj.getText();
            enter();
            UiObject appNameOfAppOneObj = findElementByID(appDetailPageAppNameLocatorID);
            String appNameOfAppOne = appNameOfAppOneObj.getText();
            pressStartToInstallInAppDetailPage();
            //点击下载中按钮，暂停下
            enter();
            //Assert
            UiObject progressStatusObj = findElementByID(appDetailPageDownloadProgressLocator);
            UiObject downloadProgressObj = findElementByID(appDetailPageDownProgressObjLocator);
            String downloadProgressStr = downloadProgressObj.getText().replace("%", "");
            int downloadProgressNum = Integer.valueOf(downloadProgressStr);
            verifyString("", progressStatusObj.getText(), appDetailPageAppStopInstallStatus);
            verifyElementPresent("", downloadProgressObj);
            Thread.sleep(5000);
            UiObject latestDownloadProgressObj = findElementByID(appDetailPageDownProgressObjLocator);
            int latestDownloadProgressNum =  Integer.valueOf(latestDownloadProgressObj.getText().replace("%", ""));
            verifyNumber("", latestDownloadProgressNum, downloadProgressNum);
            //再次点击以完成此应用的安装
            enter();
            backForMultiple(2);
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
     * Game_Search_1:游戏搜索UI显示正确，且搜索默认推荐的是游戏应用
     */
    @Test
    public void Game_Search_01_testDefaultUIInGameSearchPage() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            //Assert
            UiObject inputBox = findElementByID(searchInputObjLocatorID);
            UiObject delBtn = findElementByID(searchDelBtnObjLocatorID);
            verifyElementPresent("搜索页面中输入框未显示", inputBox);
            verifyElementPresent("搜索页面中删除按钮未显示", delBtn);
            verifyString("", inputBox.getText(), defaultMsgInSearchInput);
            UiObject keyboardInSearch = findElementByID(searchKeyboardObjLocatorID);
            verifyElementPresent("搜索页面键盘区域没有显示", keyboardInSearch);
            int eleCount = keyboardInSearch.getChildCount();
            for (int i = 2; i < eleCount; i++) {
                if (i == eleCount - 1) {
                    String msgText = keyboardInSearch.getChild(new UiSelector().className("android.widget.TextView").index(eleCount - 1)).getText();
                    verifyTrue("The indicate message behind search keyboard is incorrect", msgText.equalsIgnoreCase(searchMsgInSearchPageBotm));
                } else {
                    UiObject key = keyboardInSearch.getChild(new UiSelector().resourceId(searchKeyInputObjLocaotorID).index(i));
                    String keyWord = key.getText();
                    verifyTrue("The key displayed in search keyboard is incorrect", keyWord.equalsIgnoreCase(textInSearchWindow[i - 2]));
                }
            }
            String resultInitTitle = findElementByID(searchResultAreaTitleLocatorID).getText();
            verifyTrue("The title of suggest area in search page is displayed incorrectly", resultInitTitle.equalsIgnoreCase(searchResultDefaultTitleText));
            UiObject suggestApp = findElementByID(gameSearchResultAppListLocatorID).getChild(new UiSelector().resourceId(singalAppLocatorInSearchResult).index(0));
            verifyElementPresent("The suggest app in search page is not displayed", suggestApp);
            UiObject firstAppIconObj = suggestApp.getChild(new UiSelector().resourceId(gameSearchResultAppIconLocatorID));
            UiObject firstAppNameObj = suggestApp.getChild(new UiSelector().resourceId(gameSearchResultAppTitleLocatorID));
            UiObject firstAppDownCntObj = suggestApp.getChild(new UiSelector().resourceId(gameSearchResultAppDownCntLocatorID));
            verifyElementPresent("", firstAppIconObj);
            verifyElementPresent("", firstAppNameObj);
            verifyElementPresent("", firstAppDownCntObj);
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
     *Game_Search_2:可以正常进行搜索，且搜索出来的都是游戏应用
     */
    @Test
    public void Game_Search_02_testGameSearchResultOnlyDisplayGameApp() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            //Assert
            for(int i=0; i< keywordForResult.length; i++){
                UiObject targetKeyObj = findElementByText(keywordForResult[i], searchKeyInputObjLocaotorID);
                targetKeyObj.click();
                Thread.sleep(500);
            }
            waitForElementPresentByIDAndText(searchResultAreaTitleLocatorID, gameSearchPageSearchResultTitle);
            //Assert
            String appNumInSearchResultStr = findElementByID(gameSearchPageSearchResultCountLocator).getText();
            int appNumInSearchResult = Integer.valueOf(appNumInSearchResultStr);
            UiObject searchResultListObj = findElementByID(gameSearchPageSearchResultListLocator);
            for(int i=0; i<appNumInSearchResult; i++){
                UiObject eachAppObj = searchResultListObj.getChild(new UiSelector().resourceId(gameSearchResultAppListLocatorID).index(i));
                UiObject eachAppNameObj = eachAppObj.getChild(new UiSelector().resourceId(gameSearchResultAppTitleLocatorID));
                String eachAppName = eachAppNameObj.getText();
                verifyNotString("", eachAppName, notGameApp);
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
     *Game_Search_3:无结果搜索页面显示正确
     */
    @Test
    public void Game_Search_03_testGameSearchNoResultPageDisplay() {
        String[] keywordForNoResult = {"H", "I"};
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            for(int i=0; i< keywordForNoResult.length; i++){
                UiObject targetKeyObj = findElementByText(keywordForNoResult[i], searchKeyInputObjLocaotorID);
                targetKeyObj.click();
                Thread.sleep(500);
            }
            waitForElementPresentByID(gameSearchNotResultLocatorID);
            //Assert
            UiObject noResultObj = findElementByID(gameSearchNotResultLocatorID);
            String noReusltStr = noResultObj.getText();
            verifyElementPresent("", noResultObj);
            verifyString("", noReusltStr, gameSearchNoResultMsg);
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
     *Game_Search_6:搜索页可以正常响应BACK键操作
     */
    @Test
    public void Game_Search_06_testPressBackInGameSearchPage() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            UiObject inputBox = findElementByID(searchInputObjLocatorID);
            verifyElementPresent("", inputBox);
            //按遥控器back键
            back();
            //Assert
            verifyElementNotPresent("", searchInputObjLocatorID);
            verifyElementPresent("", findElementByID(gameSearchIconLocatorID));
            UiObject firstTabObj = findElementByText(tabsOfGameLobby[0], gameLobbyTabColLocator);
            verifyElementPresent("", firstTabObj);
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
     *Game_Search_8:搜索页面可以正常响应Home键操作
     */
    @Test
    public void Game_Search_08_testPressHomeInGameSearchPage() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            UiObject inputBox = findElementByID(searchInputObjLocatorID);
            verifyElementPresent("", inputBox);
            //按遥控器home键
            home();
            //Assert
            verifyElementNotPresent("", searchInputObjLocatorID);
            UiObject tvCard = findElementByText(launcherDSJCardEleText, gameTabPageEleTitleLocator);
            UiObject videoTabObj = findElementByText(videoTab, launcherTabID);
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
     *Game_Search_9:搜索页面不会响应Menu键操作
     */
    @Test
    public void Game_Search_09_testPressMenuInGameSearchPage() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到搜索icon上
            moveFromCurrentTabToGameSearch();
            //进入搜索页面
            gotoGameSearchPageByPressSearchIcon();
            UiObject inputBox = findElementByID(searchInputObjLocatorID);
            verifyElementPresent("", inputBox);
            //按遥控器menu键
            menu();
            Thread.sleep(sleepInterval);
            //Assert
            verifyElementNotPresent("", btnTextInMenuPopupLocatorID);
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
     *Game_Setting_1:游戏大厅设置UI显示正确
     */
    @Test
    public void Game_Setting_01_testGameLobbySettingPageUIDisplay() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //点击进入游戏大厅页
            gotoGameLobbyPage();
            //移动焦点到设置tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[3]);
            //Assert
            UiObject gameLobbySettingsPageObj = findElementByID(gameLobbyPageLocatorID).getChild(new UiSelector().className(childNodeNormalClassName));
            int cardNumUnderGameSettingsTab = gameLobbySettingsPageObj.getChildCount();
            UiObject sdkLoginObj = findElementByID(gameLobbySDKLoginCardLocatorID);
            UiObject myGameObj = findElementByID(gameLobbyMyGameCardLocatorID);
            UiObject handleSetObj = findElementByID(gameLobbyHandleSettingCardLocatorID);
            UiObject handleBuyObj = findElementByID(gameLobbyHandleBuyCardLocatorID);
            verifyNumber("", cardNumUnderGameSettingsTab, 4);
            verifyElementPresent("", sdkLoginObj);
            verifyElementPresent("", myGameObj);
            verifyElementPresent("", handleSetObj);
            verifyElementPresent("", handleBuyObj);
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
     *Game_Setting_5:可以正常进入sdk登录页面
     */
    @Test
    public void Game_Setting_05_testGotoSDKLoginPageFromGameLobby() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //点击进入游戏大厅页
            gotoGameLobbyPage();
            //移动焦点到设置tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[3]);
            //点击设置tab下sdk登录卡片
            gotoSDKLoginPageFromGameLobby();
            //Assert
            UiObject sdkLoginPageTitleObj = findElementByID(sdkLoginPageTitleLocatorID);
            String sdkLoginPageTitleStr = sdkLoginPageTitleObj.getText();
            verifyElementPresent("", sdkLoginPageTitleObj);
            verifyString("", sdkLoginPageTitleStr, sdkLoginPageTitleText);
            UiObject sdkLoginPageSubtitleObj = findElementByID(sdkLoginPageSubtitleLocatorID);
            String subTitleStr = sdkLoginPageSubtitleObj.getText();
            verifyElementPresent("", sdkLoginPageSubtitleObj);
            verifyString("", subTitleStr, sdkLoginPageSubtitleText);
            UiObject sdkLoginPageAccountInputObj = findElementByID(sdkLoginPageAccountInputLocatorID);
            String accountInputMsg = sdkLoginPageAccountInputObj.getText();
            verifyElementPresent("", sdkLoginPageAccountInputObj);
            verifyString("", accountInputMsg, sdkLoginPageAccountInputMsg);
            UiObject sdkLoginPagePsdInputObj = findElementByID(sdkLoginPagePsdInputLocatorID);
            verifyElementPresent("", sdkLoginPagePsdInputObj);
            UiObject sdkLoginPageLoginBtnObj = findElementByID(sdkLoginPageLoginBtnLocatorID);
            String loginBtnText = sdkLoginPageLoginBtnObj.getText();
            verifyElementPresent("", sdkLoginPageLoginBtnObj);
            verifyString("", loginBtnText, sdkLoginPagLoginBtnText);
            UiObject sdkLoginPageRegBtnObj = findElementByID(sdkLoginPageRegisterBtnLocatorID);
            String registerBtnText = sdkLoginPageRegBtnObj.getText();
            verifyElementPresent("", sdkLoginPageRegBtnObj);
            verifyString("", registerBtnText, sdkLoginPagRegisterBtnText);
            UiObject sdkLoginPagePsdForgetBtnObj = findElementByID(sdkLoginPagePsdForgetBtnLocatorID);
            String psdForgetBtnText = sdkLoginPagePsdForgetBtnObj.getText();
            verifyElementPresent("", sdkLoginPagePsdForgetBtnObj);
            verifyString("", psdForgetBtnText, sdkLoginPagLPwdForgetBtnText);
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
     *Game_Setting_9:可以正常进入我的游戏页面
     */
    @Test
    public void Game_Setting_09_testGotoMyGamePageFromGameLobby() {
        try {
            //移动焦点到Launcher游戏tab
            navigateToLauncherGameTab();
            //进入游戏大厅
            gotoGameLobbyPage();
            //移动焦点到设置tab
            moveFromCurrentTabToTargetTab(tabsOfGameLobby[3]);
            //点击我的游戏卡片
            UiObject myGameObj = findElementByID(gameLobbyMyGameCardLocatorID);
            myGameObj.clickAndWaitForNewWindow();
            //Assert
            UiObject listObj = findElementByID(myGamePageListLocatorID);
            UiObject titleObj = findElementByText(myGameCardName, appDetailPageAppNameLocatorID);
            verifyElementPresent("", listObj);
            verifyElementPresent("", titleObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

//    @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}
