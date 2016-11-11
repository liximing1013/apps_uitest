package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.HttpUtils;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/11/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSearch {
    private int m_iOneSecond = 1000;
    private int m_iWaitSec = 8 * m_iOneSecond;
    private TvCommon m_com = new TvCommon();

    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    private TestEnterPage m_enterPage = new TestEnterPage();

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;

    private String m_sLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String m_sClearId = "com.bestv.voiceAssist:id/clear_icon"; // 清空按钮
    private String m_sDeletId = "com.bestv.voiceAssist:id/delete_icon"; // 删除按钮
    private String m_sSchList = "com.bestv.voiceAssist:id/hot_search_list"; // 右侧搜索
    private String m_sSchCell = "com.bestv.voiceAssist:id/hot_search_cell"; // 搜索结果
    private UiObject m_clearObj;
    private UiObject m_deletObj;

    @Before
    public void setUp(){
        m_sResult = "自动化脚本运行失败！";
        m_lConsumeTime = Utils.getCurSecond();
    }
//    @After
    public void clearUp() {
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Home(2);
    }

    public boolean isInSearchPage(){
        m_sExpect = "大家都在搜："; // 如果没有"大家都在搜"的文字就表示不在搜索页
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));

        m_sObjId = Infos.S_VOICE_SCH_LETTER;
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        return m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();
    }
    @Test
    public void Voice_SCH_01_enterSearchPage(){
        try {
            m_sResult = "从主页搜索按钮进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Navigation("hh990111114"); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    // iNums 菜单的按钮数量
    public boolean isMenuExists(int iNums){
        m_sExpect = "搜索";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        if(iNums > 1) {
            m_sExpect = "视频分类";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            return m_bPass && m_sActual.equalsIgnoreCase(m_sExpect);
        }
        return m_bPass;
    }

    // sPath 进入要点击menu按键的页面的路径
    // iNums 菜单的按钮数量
    public void openMenuTest(String sPath, int iNums){
        if(iNums > 1){
            m_sResult = "打开Menu菜单失败！页面中没有“视频分类”按钮";
        }
        else if(iNums == 1){
            m_sResult = "打开Menu菜单失败！页面中没有“搜索”按钮";
        }
        try {
            m_com.Navigation(sPath); // 进入sPath的指定页
            m_com.Menu();
            m_bPass = isMenuExists(iNums);
        } catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }
    }

    @Test
    public void Voice_SCH_02_enterSearchByMenu(){
        openMenuTest("hh99", 2); // 进入主页，按下menu键
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "主页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_03_enterSearchByMenuFromSports(){
        openMenuTest("hh9901", 2); // 进入体育页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "体育页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_04_enterSearchByMenuFromChild(){
        openMenuTest("hh99011", 2); // 进入少儿页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "少儿页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_05_enterSearchByMenuFromApp(){
        openMenuTest("hh990111", 1); // 进入应用页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "应用页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_06_enterSearchByMenuFromTvTab(){
        openMenuTest("hh9903", 1); // 进入电视页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "电视页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_07_cannotOpenMenuAtSetting(){
        try {
            m_sResult = "此时不应该打开Menu菜单！页面中出现了“搜索”按钮";
            m_com.Navigation("hh9901111"); // 进入设置页
            m_com.Menu();
            m_bPass = !isMenuExists(0);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
    }

    private String enterSearchPage(){
        String sResult = "从主页搜索按钮进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
        try{
            m_enterPage.enterSearchPage();
            if(isInSearchPage()){
                m_sObjId = "com.bestv.voiceAssist:id/bubble_hint"; // 第一次进入搜索页
                if(m_com.isUiObjExists(m_sObjId)){
                    m_com.Enter();
                    Utils.Log("special_log", "刷机后第一次进入搜索页才会出现这个Log！");
                }
                return "OK";
            }
        }catch (Throwable e){
            return sResult + e.toString();
        }
        return sResult;
    }
    @Test
    public void Voice_SCH_08_focusOnLetterA(){
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if(!m_sResult.equalsIgnoreCase("OK")){
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "进入搜索页后焦点不在字母A上！Focus状态为false";
            m_uiObj = m_com.getUiObjByText("A");
            m_bPass = m_uiObj.isFocused();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_10_checkLeftTopLayout() {
        UiObject uiObj;
        // case 10
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case10运行失败！搜索页左侧上方没有输入框！";
            m_sExpect = "输入影片首字母或全拼";
            m_uiObj = m_com.getUiObjByText(m_sExpect);
            m_bPass = m_uiObj.exists();
        } catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        } finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

     @Test
     public void Voice_SCH_11_checkLeftLetterLayout() {
         // case 11
         UiObject uiObj;
         try {
             // 进入搜索页的功能函数
             m_sResult = enterSearchPage();
             if (!m_sResult.equalsIgnoreCase("OK")) {
                 Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
             }

             m_sResult = "case11运行失败！";
             m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_LETTER);
             int iBtns = 29; // 29个按键
             int iChilds = m_uiObj.getChildCount();
             if (iBtns != iChilds) {
                 m_sResult += "搜索页左侧中部按键数量不对！";
                 m_bPass = false;
             } else {
                 m_sResult += "左侧按键阵列出现错误！";
                 for (int i = 0; i < iBtns - 3; ++i) {
                     uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                             Infos.S_TXTVIEW_CLASS, i);
                     m_sActual = m_com.checkExpectResult(uiObj,
                             String.valueOf(m_sLetters.charAt(i)));
                     if (!m_sActual.equalsIgnoreCase("OK")) {
                         m_sResult += m_sActual;
                         m_bPass = false;
                         break;
                     }
                 }

                 uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                         Infos.S_TXTVIEW_CLASS, iBtns - 3); // 123按键
                 m_sActual = m_com.checkExpectResult(uiObj, "123");
                 if (!m_sActual.equalsIgnoreCase("OK")) {
                     m_sResult += m_sActual;
                     m_bPass = false;
                 }

                 m_clearObj = m_com.getUiObjByResId(m_sClearId);
                 m_deletObj = m_com.getUiObjByResId(m_sDeletId);
                 if (!m_clearObj.exists()) {
                     m_sResult += " 没有清空按钮！";
                     m_bPass = false;
                 }
                 if (!m_deletObj.exists()) {
                     m_sResult += " 没有删除按钮！";
                     m_bPass = false;
                 }
             }
         } catch (Throwable e) {
             e.printStackTrace();
             m_bPass = false;
             m_sResult += e.toString();
         } finally {
             Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
         }
     }
    @Test
    public void Voice_SCH_12_checkLeftBottomLayout() {
        UiObject uiObj;
        // case 12
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case12运行失败！";
            m_sResult += "搜索页左侧下方没有【用手机搜片】的按钮！";
            m_sExpect = "用手机搜片";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

            m_sObjId = "com.bestv.voiceAssist:id/voice_search_hint";
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

        try{
            m_sResult = "case12运行失败！";
            m_sObjId = "com.bestv.voiceAssist:id/voice_search_hint";
            m_uiObj = m_com.getUiObjByResId(m_sObjId);
            if(m_uiObj.getChildCount() != 3){
                m_sResult += "使用语音搜索的提示控件数量不是3个！";
                m_bPass = false;
            }
            else{
                m_sResult += "使用语音搜索的提示文字不正确：";
                uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                        Infos.S_TXTVIEW_CLASS, 0);
                m_sExpect = "按住遥控器";
                m_sActual = m_com.checkExpectResult(uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }

                m_sResult += "使用语音搜索的提示文字不正确：";
                uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                        Infos.S_TXTVIEW_CLASS, 1);
                m_sExpect = "启动语音搜索";
                m_sActual = m_com.checkExpectResult(uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }
            }
        }catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_13_selectOneHotSearchVideo() {
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case13运行失败！";
            m_com.Sleep(m_iOneSecond * 4);
            m_com.Right(5);
            m_uiObj = m_com.getUiObjByResId(m_sSchCell);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult += "热门搜索数据为空！";
            }

            String sUrl = "http://es.fun.tv/js/media/hot"; // 热门搜索链接
            String sHotSearch = HttpUtils.sendGet(sUrl);
            JSONObject jsonObject = new JSONObject(sHotSearch);

            int iMax = jsonObject.getInt("num");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            String [] aHotNames = new String[iMax];
            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObj = (JSONObject)jsonArray.get(i);
                aHotNames[i] = jsonObj.getString("name");
                Utils.Print(i + " " + aHotNames[i]);
            }

            int iNum = Utils.randInt(iMax);
            m_sExpect = aHotNames[iNum];
            Utils.Print(iNum + " " +m_sExpect);
            for(int i = 0; i < iNum; ++i){
                m_com.Down();
                m_com.Sleep(500);
            }

            m_com.Enter(); // 进入节目详情页
            m_com.waitTillOccur(m_com.BY_RESID, Infos.S_LC_DETAIL_TITLE_ID, 0, 15);
            m_uiObj = m_com.getUiObjByResId(Infos.S_LC_DETAIL_TITLE_ID);
            m_sActual = m_com.checkExpectResult(m_uiObj, m_sExpect);
            if(!m_sActual.equalsIgnoreCase("OK")){
                m_sResult += m_sActual;
                m_bPass = false;
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_14_inputLetter(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            int iIndex = Utils.randInt(m_sLetters.length()); // 随机输入一个字母
            m_uiObj = m_com.getUiObjByText("A");
            if(m_uiObj.isFocused()){ // 焦点在A字母上
                m_sResult = "输入的字母错误！";
                m_sExpect = enterLetter(iIndex);
                // 检查输入框中是否就是输入的这个字母
                m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
                m_sActual = m_com.checkExpectResult(m_uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }

                // 热门搜索列表应该消失
                m_uiObj = m_com.getUiObjByResId(m_sSchCell);
                if(m_uiObj.exists()){
                    m_bPass = false;
                    m_sResult = "输入字母后热门搜索列表没有消失！";
                }
            }else{
                m_bPass = false;
                m_sResult = "进入搜索页时焦点不在字母A上！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    private String enterLetter(String sLetter){
        sLetter = sLetter.toUpperCase();
        int iIndex = m_sLetters.indexOf(sLetter);
        return enterLetter(iIndex);
    }
    private String enterLetter(int iIndex){
        try {
            String sLetter = String.valueOf(m_sLetters.charAt(iIndex));
            int iNumsPerLine = 5;
            int iDown, iRight;
            iDown = iIndex / iNumsPerLine;
            iRight = iIndex % iNumsPerLine;
            m_com.Down(iDown);
            m_com.Right(iRight);
            m_uiObj = m_com.getUiObjByText(sLetter);
            if (!m_uiObj.isFocused()) {
                m_bPass = false;
                m_sResult = "字母按键没有焦点框！";
            } else {
                m_com.Enter();
                m_com.Up(iDown);
                m_com.Left(iRight);
                return sLetter;
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult = e.toString();
        }
        return "";
    }

    @Test
    public void Voice_SCH_15_focusAtLeftBottom(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            enterLetter("C");
            m_com.Down(10);
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_PHONE_BTN);
            m_bPass = m_uiObj.isFocused();
            if(!m_bPass){
                m_sResult = "左侧焦点没有一直处于手机搜片按钮上！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    //@Test TODO 自动化脚本会导致程序代码的字符串判断有误，需要修改后再运行该脚本
    public void Voice_SCH_16_enter123Button(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_com.Navigation("222221"); // 【123】按钮
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_NUM_LIST);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "点击【123】按钮，没有出现数字输入界面！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_17_18_clearAndDelete(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            m_com.Navigation("22222114"); // clear btn
            m_com.Navigation("14"); // delete btn
            if(!m_uiObj.getText().equalsIgnoreCase("输入影片首字母或全拼")){
                m_bPass = false;
                m_sResult = "输入框的内容发生了变化！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_19_clearInput(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            enterLetter("D");
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            m_com.Navigation("22222114"); // clear btn
            if(!m_uiObj.getText().equalsIgnoreCase("输入影片首字母或全拼")){
                m_bPass = false;
                m_sResult = "输入框的内容没有被清空！";
            }

            m_com.Sleep(m_iOneSecond * 2);
            // 热门搜索列表应该出现
            m_uiObj = m_com.getUiObjByResId(m_sSchCell);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "清空输入框之后，没有出现热门搜索列表！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    //@Test
    public void test() throws UiObjectNotFoundException {
//        TvCommon.printAllMethods(this.getClass().getName());
        // 进入搜索页的功能函数
        m_sResult = enterSearchPage();
        if (!m_sResult.equalsIgnoreCase("OK")) {
            Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
        }

        for(int i = 0; i < 26; i += 2) {

        }
    }
}
