package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.Test;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for enter page.
 */
public class TestEnterPage {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    TvCommon com = new TvCommon();

    boolean m_bPass = false;
    String m_sExpect = "";
    String m_sActual = "";
    String m_sObjId = "";
    String m_sCaseName = "";
    UiObject m_uiObj = null;

    @After
    public void clearUp() {
        com.Sleep(iOneSecond * 4);
    }

    @Test
    public void enterHomePage(){
        Utils.Print("case 进入主页 START");
        //int iSeconds = 1;
        //boolean bEnterHome = com.waitTillOccur(com.BY_TEXT, "播放记录", 0, iWaitSec);
        com.Home(2);

        m_sExpect = "播放记录";
        m_sActual = com.getUiObjText(com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        m_sCaseName = Utils.getMethodName(3); // 第3层函数名
        Utils.writeCaseLog(m_sCaseName, "Success", "进入主页失败", m_bPass);
        Utils.Print("case 进入主页 END");
    }

    @Test
    // 焦点选择电视Tab页
    public void enterTvTabPage(){
        com.Navigation("hh9903");
    }

    @Test
    // 焦点选择体育Tab页
    public void enterSportsTabPage(){
        com.Navigation("hh9901");
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterFilmPage(){
        Utils.Print("case 进入电影页 START");
        com.Navigation("hh999924");
        com.Sleep(iWaitSec);
        String sTitle1 = "好莱坞";
        String sTitle2 = "最新上线";
        m_bPass = com.isUiObjExists(com.BY_TEXT, sTitle1, "") ||
                com.isUiObjExists(com.BY_TEXT, sTitle2, "");
        m_sCaseName = Utils.getMethodName(3); // 第3层函数名
        Utils.writeCaseLog(m_sCaseName, "Success", "进入[电影]页失败！", m_bPass);
        Utils.Print("case 进入电影页 END");
    }

    @Test
    public void enterTVPlayPage(){
        Utils.Print("case 进入电视剧页面 START");
        com.Navigation("hh9999214"); //new int[]{-1, 2, 1, 4});
        String sTitle1 = "香港TVB";
        String sTitle2 = "都市喜剧";
        Utils.funAssert("进入[电视剧]页失败！", com.isUiObjExists(com.BY_TEXT, sTitle1, "") ||
                com.isUiObjExists(com.BY_TEXT, sTitle2, ""));
        com.Sleep(iOneSecond * 4);
        Utils.Print("case 进入电视剧页面 END");
    }

    @Test
    public void enterHistoryPage(){
        Utils.Print("case 进入观看记录 START");
        com.Navigation("hh9914"); //new int[]{-1, -1, 1, 4});
        String sTitle1 = "播放记录";
        String sTitle2 = "猜你喜欢";
        Utils.funAssert("进入[电视剧]页失败！", com.isUiObjExists(com.BY_TEXT, sTitle1, "") ||
                com.isUiObjExists(com.BY_TEXT, sTitle2, ""));
        com.Sleep(iOneSecond * 4);
        Utils.Print("case 进入观看记录 END");
    }

    @Test
    public void enterChildPage(){
        Utils.Print("==============case: enter children page=================");
        com.Navigation(new int[]{-1, 2, 1, 1, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterVIPPage(){
        Utils.Print("case 进入金卡会员页面 START");
        com.Navigation("hh99124"); // new int[]{-1, 1, 2, 4}); // 视频-金卡会员页小窗口自动播放
        m_sExpect = "续费金卡会员";
        m_sObjId = Infos.S_VIP_ID;
        m_sActual = com.getUiObjText(com.getUiObjByResId(m_sObjId));
        m_uiObj = com.getUiObject(com.BY_RESID, m_sObjId);
        Utils.funAssert("进入金卡会员主页失败",
                m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists());
        com.Sleep(iWaitSec);
        Utils.Print("case 进入金卡会员页面 END");
    }

    @Test
    public void enterVideoClassificationPage(){
        com.Navigation(new int[]{-1, 1, 2, 2, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterBestvLivePage(){ // 百视通直播大厅
        enterSportsTabPage();
        com.Down(4);
        com.Enter();
        com.Sleep(iWaitSec);
    }
}
