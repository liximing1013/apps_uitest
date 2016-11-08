package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
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

    @Test
    public void Voice_SCH_08_enterSearchByMenuFromSetting(){
        try {
            m_sResult = "从主页搜索按钮进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Navigation("hh99");
            m_com.Menu();
            m_com.Enter();
            m_bPass = isInSearchPage();
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

        try {
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
}
