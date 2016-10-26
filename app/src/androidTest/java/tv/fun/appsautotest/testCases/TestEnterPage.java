package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for enter page.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEnterPage {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    TvCommon m_com = new TvCommon();

    boolean m_bPass = false;
    long m_lConsumeTime = -1;
    String m_sExpect = "";
    String m_sActual = "";
    String m_sObjId = "";
    String m_sModuleName = "";
    String m_sCaseName = "";
    UiObject m_uiObj = null;

    @Before
    public void setUp(){
        m_lConsumeTime = Utils.getCurSecond();
    }

    @After
    public void clearUp() {
        m_com.Sleep(iOneSecond * 4);
        m_com.Home(2);
    }

    @Test
    public void LC_TB_01_enterHomePage(){
        m_com.Home(2);

        m_sExpect = "播放记录";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        Utils.writeCaseResult("进入[视频]主页失败", m_bPass, m_lConsumeTime);
    }

    public void enterTvTabPage(){
        m_com.Navigation("hh9903");
    }
    @Test
    // 焦点选择电视Tab页
    public void LC_TB_02_enterTvTabPage(){
        enterTvTabPage();

        m_sExpect = "轮播";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        Utils.writeCaseResult("进入[轮播]页失败", m_bPass, m_lConsumeTime);
    }

    public void enterSportsTabPage(){
        m_com.Navigation("hh9901");
    }

    @Test
    // 焦点选择体育Tab页
    public void LC_TB_03_enterSportsTabPage(){
        enterSportsTabPage();

        m_sExpect = "英超";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        Utils.writeCaseResult("进入[体育]页失败", m_bPass, m_lConsumeTime);
    }

    public void enterFilmPage(){
        m_com.Navigation("hh9999249999");
    }
    @Test
    public void LC_TB_04_enterFilmPage(){
//        Utils.Print("case 进入电影页 START");
        enterFilmPage();

        String sTitle1 = "好莱坞";
        String sTitle2 = "最新上线";
        m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

        Utils.writeCaseResult("进入电影页失败", m_bPass, m_lConsumeTime);

//        m_sCaseName = Utils.getMethodName(3); // 第3层函数名
//        Utils.writeCaseResult("LC", m_sCaseName, "Success", "进入[电影]页失败！", m_bPass,
//                Utils.getCurDate() + " "+ Utils.getCurTime(),
//                Utils.getCurSecond() - m_lConsumeTime, "");
////        Utils.Print("case 进入电影页 END");
    }

    public void enterTVPlayPage(){
        m_com.Navigation("hh99214");
    }
    @Test
    public void LC_TB_05_enterTVPlayPage(){
//        Utils.Print("case 进入电视剧页面 START");
        enterTVPlayPage();

        String sTitle1 = "香港TVB";
        String sTitle2 = "都市喜剧";
        m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

        Utils.writeCaseResult("进入电视剧页失败", m_bPass, m_lConsumeTime);
//        Utils.Print("case 进入电视剧页面 END");
    }

    public void enterHistoryPage(){
        m_com.Navigation("hh9914"); //new int[]{-1, -1, 1, 4});
    }
    @Test
    public void LC_TB_06_enterHistoryPage(){
//        Utils.Print("case 进入观看记录 START");
        enterHistoryPage();

        String sTitle1 = "播放记录";
        String sTitle2 = "猜你喜欢";
        m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

        Utils.writeCaseResult("进入播放记录页失败", m_bPass, m_lConsumeTime);
//        Utils.Print("case 进入播放记录 END");
    }

    public void enterChildPage(){
        m_com.Navigation("hh992114");
    }
    @Test
    public void LC_TB_07_enterChildPage(){
        enterChildPage();
        // TODO 可以用服务端的数据来做校验
        String sTitle1 = "启蒙";
        String sTitle2 = "乐园";
        m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

        Utils.writeCaseResult("进入少儿页失败", m_bPass, m_lConsumeTime);
    }

    public void enterVIPPage(){
        m_com.Navigation("hh99124"); // 视频-金卡会员页小窗口自动播放
    }
    @Test
    public void LC_TB_08_enterVIPPage(){
//        Utils.Print("case 进入金卡会员页面 START");
        enterVIPPage();

        m_sExpect = Infos.S_TEXT_VIP_TITLE;
        m_sObjId = Infos.S_VIP_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("进入金卡会员主页失败", m_bPass, m_lConsumeTime);
//        Utils.Print("case 进入金卡会员页面 END");
    }

    public void enterVideoClassificationPage(){
        m_com.Navigation("hh991224");
    }
    @Test
    public void LC_TB_09_enterVideoClassificationPage(){
        enterVideoClassificationPage();

        String sTitle1 = "最新";
        String sTitle2 = "排行榜";
        m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

        Utils.writeCaseResult("进入视频分类页失败", m_bPass, m_lConsumeTime);
    }

    public void enterBestvLivePage(){
        m_com.Navigation("hh990122224");
    }
    @Test
    public void LC_TB_10_enterBestvLivePage(){ // 百视通直播大厅
        enterBestvLivePage();

        m_sExpect = "看看新闻";
        m_sObjId = Infos.S_BEST_LIVE_HALL_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("进入百视通直播大厅失败", m_bPass, m_lConsumeTime);
    }

//    @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}
