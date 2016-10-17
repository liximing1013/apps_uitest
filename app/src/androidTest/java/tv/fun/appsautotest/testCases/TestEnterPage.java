package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

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

    String m_sExpect = "";
    String m_sActual = "";
    String m_sObjId = "";
    UiObject m_uiObj = null;

    @Test
    public void enterHomePage(){
        Utils.Print("case 进入主页 START");
        //int iSeconds = 1;
        //boolean bEnterHome = com.waitTillOccur(com.BY_TEXT, "播放记录", 0, iWaitSec);
        com.Home(2);
        m_sExpect = "播放记录";
        m_sActual = com.getUiObjText(com.getUiObjByText(m_sExpect));
        Utils.funAssert("进入主页失败", m_sActual.equalsIgnoreCase(m_sExpect));
        Utils.Print("case 进入主页 END");
    }

    @Test
    // 焦点选择体育Tab页
    public void enterTvTabPage(){
        com.Navigation("h93");
        com.Sleep(iWaitSec);
    }

    @Test
    // 焦点选择体育Tab页
    public void enterSportsTabPage(){
        com.Navigation("h901");
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterFilmPage(){
        Utils.Print("==============case: enter film page=================");
        com.Navigation("h24");
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterTVPlayPage(){
        Utils.Print("case 进入电视剧页面 START");
        com.Navigation("hh99214"); //new int[]{-1, 2, 1, 4});
        com.isUiObjExists(com.BY_TEXT, "香港TVB", "进入电视剧页面失败！");
        com.Sleep(iWaitSec);
        Utils.Print("case 进入电视剧页面 END");
    }

    @Test
    public void enterHistoryPage(){
        Utils.Print("case 进入观看记录 START");
        com.Navigation("hh9914"); //new int[]{-1, -1, 1, 4});
        com.isUiObjExists(com.BY_TEXT, "猜你喜欢", "进入观看记录页面失败！");
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
