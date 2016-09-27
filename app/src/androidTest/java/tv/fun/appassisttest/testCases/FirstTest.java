package tv.fun.appassisttest.testCases;

import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tv.fun.appassisttest.common.Funcs;
import tv.fun.appassisttest.common.PhoneCommon;
import tv.fun.appassisttest.common.UiIds;


@RunWith(AndroidJUnit4.class)
public class FirstTest{
	int iSwipe = 3; // the times of swiping to find the app
	int iDelay = 500;
	int iTabs = 4;
	int USER_CENTER_LIST_LEN = 5; // 个人中心的功能数目
	String sTVName = "风行电视去球器勿忘我";
	boolean bFirstStartFlag = false;
	String[] lsPhoneNum = {"15926274299", "15802778317"};
	String[] lsPassword = {"333333", "111111"};
	String sBuildMethod = "RELEASE";
	static PhoneCommon com = null;
	static UiObject tabsObj = null;
	static UiObject[] uiMainTabObjs = null;

    @Before
	public void setUp(){
		Funcs.WriteLogs("==========[风行电视助手]自动化测试初始化==========");
		com = new PhoneCommon();
		String sFolderName = "/data/local/tmp/log/";
		Funcs.MakeDir(sFolderName);
		
//		com.WakeUp();
//		enterApp();
//		waitingStart();
//		guidePage(); // guide page, first starting of app
		
		Funcs.WriteLogs("-----初始化主页的Tabs对象-----");
		tabsObj = com.getUiObjByResId(debugMethod(UiIds.SMAINTAB_STRING, sBuildMethod));
		Funcs.WriteLogs("-----初始化主页的Tabs对象列表-----");
		uiMainTabObjs = clickMainPageTabs(false);
        iTabs = uiMainTabObjs.length;
        
		Funcs.WriteLogs("==========[风行电视助手]自动化测试初始化成功！==========");
		Funcs.WriteLogs("==========[风行电视助手]自动化测试开始==========");
	}
	
	// main test case
    @Test
	public void testAMainStart(){
		Funcs.WriteLogs("==============main case=================");
		
//        long iCurSec = Funcs.CurSecond();
//        long iWaitSec = 10;
//        Funcs.WriteLogs("-----判断是否是DEBUG版-----");
//        while(!com.isUiObjExists(UiIds.SMAINTAB_STRING, "main tab is not exists")){
//        	if(Funcs.CurSecond() >= iCurSec + iWaitSec){
//        		sBuildMethod = "DEBUG";
//        		break;
//        	}
//        	else if(Funcs.CurSecond() >= iCurSec + (int)(iWaitSec/2)){
//        		com.Back();
//        	}
//        }
//        Funcs.WriteLogs(String.format("-----当前为[%s]版-----", sBuildMethod));

        Funcs.WriteLogs("-----点击主页上的Tabs-----");
        uiMainTabObjs = clickMainPageTabs(true);
        
        Funcs.WriteLogs("-----获取版本号-----");
        String verString = getVersion();
        Funcs.WriteLogs(String.format("-----获取版本号成功：[%s]-----", verString));
        
        enterAboutPage();
		enterAppPage();
		enterFeedback();
		enterForgotPwdPage();
		enterLoginPage();
		enterPersonalPage();
		enterPersonalPage(false);
		enterPersonalPage(true);
		login();
		enterPersonalPage(true);
		enterRegisterPage();
		enterToolsPage();
		enterProgrammeSearchPage();
		connectTV();
		enterControllerPage();
	}
	
	@Test
    public void checkHotSearch(){
        Funcs.Print("==============case: check hotsearch=================");
        caseCheckHotSearch(true);
    }

    @Test
	public void checkSearchResult(){
		Funcs.Print("==============case: check search result=================");
		caseCheckSearchResult();
	}

    @Test
	public void login(){
		enterLoginPage();
		UiObject uiUsrInput = com.getUiObjByResId(debugMethod(UiIds.SPHONEUSR_STRING, sBuildMethod));
        UiObject uiPwdInput = com.getUiObjByResId(debugMethod(UiIds.SPHONEPWD_STRING, sBuildMethod));
        try {
        	String sPhone = uiUsrInput.getText();
        	if(!sPhone.equalsIgnoreCase("手机号")){
        		uiUsrInput.setText("");
        		//com.Back();
        	}
            uiUsrInput.setText(lsPhoneNum[0]);
            Funcs.WriteLogs("-----输入[手机号]-----");
            uiPwdInput.setText(lsPassword[0]);
            Funcs.WriteLogs("-----输入[密码]-----");
            com.Sleep(2000);
            //com.Click(108, 1235, 1); // sogou input
            //com.Back(); // google input
            com.uiObjClickById(debugMethod(UiIds.SLOGIN_STRING, sBuildMethod));
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("login error:" + e, com.ERR_CODE);
		}
	}

    @Test
	public void enterLoginPage(){
		returnMainPage();
        com.uiObjClick(uiMainTabObjs[iTabs - 1]);
        
		clickLogout();
		clickLogin();
		Funcs.WriteLogs("-----进入[登录]页面-----");
	}

    public void clickLogout(){
        String sText = "退出登录";
        if(com.isUiObjExists(com.BY_TEXT, sText, "")){
            com.uiObjClickByType(com.BY_TEXT, sText, 2);
            Funcs.WriteLogs("-----点击[退出登录]按钮-----");
        }
    }

    public void clickLogin(){
        String sText = "登录";
        if(com.isUiObjExists(com.BY_TEXT, sText, "")){
            com.uiObjClickByType(com.BY_TEXT, sText, 2);
            Funcs.WriteLogs("-----点击[登录]按钮-----");
        }
    }

    @Test
	public void enterForgotPwdPage(){
		enterLoginPage();
		com.uiObjClickByText("忘记密码");
		Funcs.WriteLogs("-----点击[忘记密码]-----");
	}

    @Test
	public void enterRegisterPage() {
		enterLoginPage();
		com.uiObjClickByText("注册");
		Funcs.WriteLogs("-----点击[注册]-----");
	}

	public boolean checkProgrammePage(){
		boolean bResult = true;
		String sSearchId = debugMethod(UiIds.SPRO_SEARCH_BAR_STRING, sBuildMethod);
		String sBannerId = debugMethod(UiIds.SPRO_BANNER_STRING, sBuildMethod);
		String sCategoryId = debugMethod(UiIds.SPRO_CATEGORY_STRING, sBuildMethod);
		String sHotPlayId = debugMethod(UiIds.SPRO_HOT_PLAY_STRING, sBuildMethod);
//		UiObject searchObject = com.com.getUiObjByResId(sSearchId);
//		UiObject bannerObject = com.com.getUiObjByResId(sBannerId);
//		UiObject categoryObject = com.com.getUiObjByResId(sCategoryId);
//		UiObject hotplayObject = com.com.getUiObjByResId(sHotPlayId);
		bResult &= com.isUiObjExists(sSearchId, "programme search bar is missing");
		bResult &= com.isUiObjExists(sBannerId, "programme banner is missing");
		bResult &= com.isUiObjExists(sCategoryId, "programme category bar is missing");
		bResult &= com.isUiObjExists(sHotPlayId, "programme hot play list is missing");
		if(bResult){
			// TODO check the secondary page
		}
		return bResult;
	}

	public void clickFloatBtn(){
		com.uiObjClickByType(com.BY_DESC, "float button", 1); // 点击1次
	}
	
	// press back button when obj_id occur
	public void needPressBack(String obj_id){
		if(com.isUiObjExists(debugMethod(obj_id, sBuildMethod))){
			Funcs.Print("Press back: " + obj_id);
			com.Back();
		}
	}
	
	public String debugMethod(String sString, String sMethod){
		if(sMethod.equalsIgnoreCase("DEBUG")){
			sString = sString.replace(":", ".debug:");
		}
		return sString;
	}

	public void enterApp(){
		if(com.isUiObjExists(UiIds.SMAINTAB_STRING)){
			return;
		}
		com.Back();
		Funcs.WriteLogs("-----在桌面寻找并启动App-----");
		// PressHome twice to confirm back to home page
        com.Home(2);
        com.Sleep(iDelay);

        // Get app uiobject and start app
        UiObject uiObjAppIcon = new UiObject(new UiSelector().text(UiIds.SAPPNAME_STRING));
        if(findUiObject(uiObjAppIcon, iSwipe)){
        	com.uiObjClick(uiObjAppIcon);
        }
        else{
        	Funcs.WriteLogs("error: app not found!", com.ERR_CODE);
        }
	}

    @Test
	public void returnMainPage(){
		while(!com.pressBackToFindObj(com.getUiObjByResId(UiIds.SMAINTAB_STRING), 5)){
        	com.Back();
        }
	}

	public UiObject[] clickMainPageTabs(boolean bClickFlag){
		returnMainPage();
		try {
			int iChilds = tabsObj.getChildCount();
	        Funcs.Print("Tabs num: " + iChilds);
	        UiObject[] uiChildObjs = new UiObject[iChilds];
	        for(int i = 0; i < iChilds; i++){
	        	uiChildObjs[i] = tabsObj.getChild(new UiSelector().className("android.widget.ImageView").instance(i));
	        	Funcs.Print("child "+ i + " " + uiChildObjs[i].getBounds());
	        	if(bClickFlag && uiChildObjs[i].exists()){
	        		uiChildObjs[i].click();
	        	}
	        }
	        return uiChildObjs;
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("click mainpage tabs error: " + e, com.ERR_CODE);
			return null;
		}
	}

    @Test
	public void enterAppPage(){
		returnMainPage();
		com.uiObjClickByType(com.BY_TEXT, "应用", 1);
	}

    @Test
	public void enterToolsPage(){
		returnMainPage();
		com.uiObjClick(uiMainTabObjs[iTabs - 2]);
	}

	@Test
	public void enterAboutPage(){
		enterPersonalPage(false);
		com.uiObjClick(uiMainTabObjs[iTabs - 1]);
		Funcs.WriteLogs("-----开始进入[关于]页-----");
		UiObject[] objs = enterPersonalPage(false);
		com.uiObjClick(objs[objs.length - 1]);
		Funcs.WriteLogs("-----成功进入[关于]页-----");
	}

	public String getVersion(){
		enterAboutPage();
		UiObject uiCheckUpdateObj = com.getUiObjByResId(debugMethod(UiIds.SVERSION_STRING, sBuildMethod));
		String sVer = com.getUiObjText(uiCheckUpdateObj);
		String[] lRes = sVer.split("-");
        sBuildMethod = lRes[lRes.length - 1]; // confirm build method
        if(sBuildMethod.equalsIgnoreCase(sVer)){
        	sBuildMethod = "RELEASE";
        }
		com.uiObjClick(uiCheckUpdateObj); // click check update
		if("" != sVer){
			return sVer;
		}
		Funcs.Print("error: cannot get the version!");
		return "0";
	}

    @Test
	public void enterFeedback(){
		Funcs.WriteLogs("-----准备进入[意见反馈]页-----");
		enterAboutPage();
		UiObject uiAboutList = com.getUiObjByResId(debugMethod(UiIds.SABTOULIST_STRING, sBuildMethod));
		Funcs.WriteLogs("-----准备点击[意见反馈]功能项-----");
		UiObject uiFeedbackObj;
		try {
			uiFeedbackObj = uiAboutList.getChild(new UiSelector().className("android.widget.RelativeLayout").instance(0));
			com.uiObjClick(uiFeedbackObj);
			Funcs.WriteLogs("-----成功点击[意见反馈]功能项-----");
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("enter feedback error: " + e, com.ERR_CODE);
		}
	}

    @Test
	public void  enterPersonalPage() {
		returnMainPage();
		com.uiObjClick(uiMainTabObjs[uiMainTabObjs.length - 1]);
		Funcs.WriteLogs("-----进入[个人中心]页-----");
	}
	
	public UiObject[] enterPersonalPage(boolean bClickFlag){
		enterPersonalPage();
		UiObject personnalObj = com.getUiObjByResId(debugMethod(UiIds.SUSERCENTERLIST_STRING, sBuildMethod));
		int iChilds = USER_CENTER_LIST_LEN;
        UiObject[] uiChildObjs = new UiObject[iChilds];
        try {
        	for(int i = 0; i < iChilds; i++){
            	uiChildObjs[i] = personnalObj.getChild(new UiSelector().resourceId("com.funshion.remotecontrol:id/tv_item_name").instance(i));
            	Funcs.Print("child "+ uiChildObjs[i].getText() + " " + uiChildObjs[i].getBounds());
            	if(bClickFlag && uiChildObjs[i].exists()){
            		uiChildObjs[i].click();
            		returnMainPage();
            	}
            }
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("enter Personal page error: " + e, com.ERR_CODE);
		}
		return uiChildObjs;
	}
	
	// click controller operation guiding images
	public void guideImageClick(){
		String sOperateTips = "com.funshion.remotecontrol:id/guide_imageview"; // when first connected, guiding image tips
		UiObject uiOperateTipsObj = com.getUiObjByResId(sOperateTips);
        // when first connected, guiding image tips show up
		while(uiOperateTipsObj.exists()){
        	Funcs.Print("info: the guide tips of operation is up");
			com.uiObjClick(uiOperateTipsObj);
		}
	}

	public void operationController(){
		int steps = 5;
		int x, y;
		Rect rect;
		guideImageClick();
		UiObject bodyObj = com.getUiObjByResId(debugMethod(UiIds.SCONTROLCONTENT_STRING, sBuildMethod));
		try {
			rect = bodyObj.getBounds();
			x = rect.centerX();
			y = rect.centerY();
			Funcs.Print("rect: " + rect + "x: " + x + "y: " + y);
			// enter video classification on TV
			com.uiObjClickById(debugMethod(UiIds.SHOME_BTN_STRING, sBuildMethod));
			com.SwipeL2R(x, y, 100, steps);
			com.SwipeT2B(x, y, 100, steps);
			com.SwipeT2B(x, y, 100, steps);
			com.Click(x, y, 1);
			com.Sleep(6 * iDelay);
			
			// direction operation
			com.SwipeL2R(x, y, 100, steps);
			com.Sleep(iDelay);
			com.SwipeR2L(x, y, 100, steps);
			com.Sleep(iDelay);
			com.SwipeT2B(x, y, 100, steps);
			com.Sleep(iDelay);
			com.SwipeB2T(x, y, 100, steps);
			com.Sleep(iDelay);

			// press menu button
			com.uiObjClickById(debugMethod(UiIds.SMENU_BTN_STRING, sBuildMethod));
			com.Sleep(4 * iDelay);
			com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
					
			com.Click(x, y, 1);
			com.Sleep(8 * iDelay);
			com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
			com.uiObjClickById(debugMethod(UiIds.SOPTIMIZE_BTN_STRING, sBuildMethod));
			com.Sleep(10 * iDelay);
			com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
			com.uiObjClickById(debugMethod(UiIds.SVOICE_BTN_STRING, sBuildMethod));
			com.Sleep(20 * iDelay);
			com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("operate error: " + e, com.ERR_CODE);
		}
	}

    @Test
	public void enterControllerPage(){
        returnMainPage();
        com.uiObjClick(uiMainTabObjs[0]);
		boolean bConnect = false;
		clickFloatBtn();
		
		// connected
		if(com.isUiObjExists(debugMethod(UiIds.SCONNECT_STATU_STRING, sBuildMethod), "controller is not connected!")){
			checkControllerBody();
			com.uiObjClickById(debugMethod(UiIds.SCONTROLCLEAR_BTN_STRING, sBuildMethod));
			clickFloatBtn();
			com.uiObjClickById(debugMethod(UiIds.SCONNECT_STATU_STRING, sBuildMethod));
			bConnect = getTVList(sTVName, false);
		}
		else{
			int i = 0;
			while(!bConnect && i < 3){
				Funcs.Print("i: " + i);
				bConnect = getTVList(sTVName, true);
				++i;
			}
		}
		if(bConnect){
			operationController();
		}
	}

    @Test
	public void connectTV(){
		returnMainPage();
        com.uiObjClick(uiMainTabObjs[0]);
		clickFloatBtn();
		if (com.isUiObjExists(UiIds.SWIFI_LOGO_STRING)) { // not connect tv yet
			getTVList(sTVName,true);
		}
	}

    public void connectTV(String sName){
        clickFloatBtn();
        if (com.isUiObjExists(UiIds.SWIFI_LOGO_STRING)) { // not connect tv yet
            getTVList(sName, true);
        }
    }
	
	public boolean getTVList(String sTvName, boolean bClickFlag){
		String tvListString = debugMethod(UiIds.STVLIST_STRING, sBuildMethod);
		String tvLoadingBar = debugMethod(UiIds.SLOADINGBAR_STRING, sBuildMethod);
		Funcs.Print("---正在刷新TV列表---");
		while(com.isUiObjExists(tvLoadingBar)){
		}
		if(!com.isUiObjExists(tvListString)){
			Funcs.WriteLogs(String.format("TV[%s]和手机不在同一个WiFi下！", sTvName));
			com.uiObjClickById(debugMethod(UiIds.SREFRESH_STRING, sBuildMethod));
			return false;
		}
		UiObject uiTVObject = com.getUiObjByResId(tvListString);
		try {
			int iCnt = uiTVObject.getChildCount();
			UiObject[] uiChildObjs = new UiObject[iCnt];
			for(int i = 0; i < iCnt; i++){
				uiChildObjs[i] = uiTVObject.getChild(new UiSelector().resourceId(UiIds.STVLIST_ITEM_LAYOUT_STRING).instance(i));
				if(uiChildObjs[i].waitForExists(6 * iDelay)){
					String sDeviceNameString = uiChildObjs[i].getChild(new UiSelector().resourceId(UiIds.STVNAME_STRING).instance(0)).getText();
					Funcs.Print("tvlist "+ i + " " + uiChildObjs[i].getBounds() + "text: " + sDeviceNameString);
					if(bClickFlag && sDeviceNameString.equalsIgnoreCase(sTvName)){
						uiTVObject = uiChildObjs[i];
						uiChildObjs[i].click();
						Funcs.Print("connect to " + sDeviceNameString);
						break;
					}
				}
			}
			if(!bClickFlag){
				Funcs.WriteLogs("不连接遥控，直接返回");
				com.uiObjClickById(debugMethod(UiIds.SLISTBACK_BTN_STRING, sBuildMethod)); // back button in add devices page
			}
			Funcs.Print("尝试连接电视！");
			for(int i = 0; i < 5 && !com.isUiObjExists(UiIds.SCONNECT_STATU_STRING); ++i){
				uiTVObject.click();
				if(com.isUiObjExists(UiIds.SCONNECT_STATU_STRING)){
					Funcs.Print("tv is connected!");
					return true;
				}
				else{
					Funcs.Print("tv connection failed!");
				}
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get tv list error: " + e, com.ERR_CODE);
			return false;
		}
		return false;
	}

    @Test
	public void enterProgrammeSearchPage(){
		returnMainPage();
		com.uiObjClick(uiMainTabObjs[0]);
		Funcs.WriteLogs("点击节目首页的搜索栏");
		com.uiObjClickById(debugMethod(UiIds.SPRO_SEARCH_BAR_STRING, sBuildMethod));
	}
	
	// case：检查搜索jk后的结果
	public void caseCheckSearchResult(){
		searchProgramme("jk");	// 搜索jk
		UiObject uiObj = null;
				
		// 获取第2个搜索结果对象，点击后返回
		uiObj = com.getUiObject(com.BY_RESID, UiIds.SSEARCH_ITEM_STRING, 1); // 1是节目序号
		
		com.waitTillOccur(uiObj, 10);
		
		com.uiObjClick(uiObj);
		com.Back();

        // 连接电视后返回节目列表
		connectTV(sTVName);
        com.Back();

		// 获取第2个搜索结果的电视播放按钮对象，然后点击
		uiObj = com.getUiObject(com.BY_RESID, UiIds.STVPLAY_BTN_STRING, 1);
		com.uiObjClick(uiObj);
		
		// 向上滑动，直到有明星列表项出现
		while(!com.isUiObjExists(UiIds.SSEARCH_STAR_STRING)){
			com.SwipeB2T(0, 0, 100, 10);
		}
		
		// 点击第1个明星列表项
		uiObj = com.getUiObject(com.BY_RESID, UiIds.SSEARCH_STAR_STRING, 0);
		String sName = "";
		try {
			sName = com.getUiObjChild(uiObj, com.BY_CLASS, "android.widget.TextView", 0).getText();
		} catch (UiObjectNotFoundException e) {
			sName = "获取名字出错！";
		}
		com.uiObjClick(uiObj);
		
		com.Sleep(5 * iDelay);
		// 没有内容显示时，就点击下一个明星名字
		if(!com.isUiObjExists(com.BY_RESID, UiIds.SSEARCH_ITEM_STRING, String.format("[%s]没有搜索到结果！", sName))){
			com.Back();
			com.SwipeB2T(0, 0, 100, 20);
			uiObj = com.getUiObject(com.BY_RESID, UiIds.SSEARCH_STAR_STRING, 1);
			com.uiObjClick(uiObj);
		}
	}
	
	// case：检查热门搜索名称
	public void caseCheckHotSearch(boolean bClick){
		enterProgrammeSearchPage();
		String sHotSearch = "com.funshion.remotecontrol:id/tvprogram_fragment_flowlayout";
		UiObject uiHotSearch = com.getUiObjByResId(debugMethod(sHotSearch, sBuildMethod));
		try {
			int iCnt = uiHotSearch.getChildCount();
			for(int i = 0; i < iCnt; ++i){
				UiObject uiLink = uiHotSearch.getChild(
						new UiSelector().className("android.widget.TextView").instance(i));
				Funcs.Print("Name: " + uiLink.getText());
				if(bClick){
					uiLink.click();
					com.Sleep(2000);
					com.Back();
				}
			}
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("check hotsearch error:" + e, com.ERR_CODE);
		}
	}
	
	public void searchProgramme(String sSearchText){
		enterProgrammeSearchPage();
		String sSearchEdit = "com.funshion.remotecontrol:id/tvprogram_search_edit";
		UiObject uiSearchEdit = com.getUiObjByResId(debugMethod(sSearchEdit, sBuildMethod));
		try {
			uiSearchEdit.clearTextField();
            com.uiObjClick(uiSearchEdit);
			uiSearchEdit.setText(sSearchText);
			com.clickEnter(0);
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("search error: " + e, com.ERR_CODE);
		}
	}
	
	public boolean checkControllerBody(){
		boolean bResult = true;
		String sSwitchBtn = debugMethod(UiIds.SPOWER_BTN_STRING, sBuildMethod); // power button
		String sOptimizeBtn = debugMethod(UiIds.SOPTIMIZE_BTN_STRING, sBuildMethod); // optimize button
		String sVoiceBtn = debugMethod(UiIds.SVOICE_BTN_STRING, sBuildMethod); // voice button
		String sHomeBtn = debugMethod(UiIds.SHOME_BTN_STRING, sBuildMethod); // home button
		String sBackBtn = debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod); // back button
		String sMenuBtn = debugMethod(UiIds.SMENU_BTN_STRING, sBuildMethod); // menu button
//		String sLine1 = debugMethod(UiIds.STEXTLINE1_STRING, sBuildMethod);
//		String sLine2 = debugMethod(UiIds.STEXTLINE2_STRING, sBuildMethod);
//		String sLine3 = debugMethod(UiIds.STEXTLINE3_STRING, sBuildMethod);

		
		bResult &= com.isUiObjExists(sSwitchBtn, "error: the power button is missing");
		bResult &= com.isUiObjExists(sOptimizeBtn, "error: the optimize button is missing");
		bResult &= com.isUiObjExists(sHomeBtn, "error: the home button is missing");
		bResult &= com.isUiObjExists(sBackBtn, "error: the back button is missing");
		bResult &= com.isUiObjExists(sMenuBtn, "error: the menu button is missing");
		bResult &= com.isUiObjExists(sVoiceBtn, "error: the voice button is missing");
//		bResult &= com.isUiObjExists(sLine1, "error: the introduction text1 is missing");
//		bResult &= com.isUiObjExists(sLine2, "error: the introduction text2 is missing");
//		bResult &= com.isUiObjExists(sLine3, "error: the introduction text3 is missing");
		Funcs.Print("controller body checking result: " + bResult);
		return bResult;
	}
	
//	public boolean checkConnectPage(){
//		if(checkBody()){
//			return true;
//		}
//		return false;
//	}
	
//	public boolean checkTitleBar(){
//		boolean bResult = true;
//		bResult &= com.isUiObjExists("com.funshion.remotecontrol:id/btnMenu", 
//				"error: the personal center button is missing");
//		bResult &= com.isUiObjExists("com.funshion.remotecontrol:id/btnShaoMa", 
//				"error: the scanner button is missing");
//		return bResult;
//	}
	
//	public boolean checkBody(){
//		boolean bResult = true;
//		bResult &= com.isUiObjExists("com.funshion.remotecontrol:id/device_server", 
//				"error: the pic of device in the body is missing");
//		bResult &= com.isUiObjExists("com.funshion.remotecontrol:id/device_connect_tip_textview", 
//				"error: the text of connection tips in the body is missing");
//		return bResult;
//	}
	
	public void waitingStart(){
		Funcs.Print("---App启动中...---");
		while(com.isUiObjExists(UiIds.SSTART_IMG_STRING)){
			com.Sleep(1000);
		}
		Funcs.Print("---App启动成功！---");
	}
	
	public void guidePage(){
		// first start app, there are three guide pages
        String sStartBtn = "com.funshion.remotecontrol:id/startguide_btn";
        // first guide page
        bFirstStartFlag = com.isUiObjExists(UiIds.SGUIDE_IMG_STRING);
        if(bFirstStartFlag){
        	Funcs.WriteLogs("-----第一次启动App时，出现三张[指引页]-----");
        }
        while(!com.isUiObjExists(sStartBtn) && bFirstStartFlag){
            com.SwipeR2L(0, 0, 100, 10);
        }
        // third guide page
        if(com.isUiObjExists(sStartBtn)){
        	Funcs.WriteLogs("-----点击第三张[指引页]上的【立即体验】按钮-----");
        	com.uiObjClickById(sStartBtn);
        }
	}
		
	public boolean findUiObject(UiObject uiObj, int iFindTimes){
		int iCnt = 0;
		// find object from left
		while(!uiObj.exists() && iCnt != iFindTimes){
			com.SwipeL2R(0, 0, 100, 10);
			com.Sleep(iDelay);
			if(uiObj.exists()){
				Funcs.Print("find obj: found in left of home");
				return true;
			}
			iCnt++;
		}
		// find object from right
		while(!uiObj.exists() && iCnt != 3*iFindTimes){
			com.SwipeR2L(0, 0, 100, 10);
			com.Sleep(iDelay);
			if(uiObj.exists()){
				Funcs.Print("find obj: found in right of home");
				return true;
			}
			iCnt++;
		}
		return uiObj.exists();
	}
}
