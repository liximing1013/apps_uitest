package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for data report.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDataReport {
    int m_iOneSecond = 1000;
    int m_iWaitSec = 8 * m_iOneSecond;
    TvCommon m_com = new TvCommon();
    TestFunTvTVPlay m_tfttp = new TestFunTvTVPlay();
    TestEnterPage m_enterPage = new TestEnterPage();

    String m_sExpect = "";
    String m_sActual = "";
    String m_sObjId = "";
    UiObject m_uiObj = null;

    @After
    public void clearUp() {
        m_com.Home(2);
    }

    @Test
    public void case01_playCarousel(){
        Utils.Print("play case1 播放轮播视频 START");
        m_sExpect = "轮播";
        m_com.Navigation("hh93999"); // 进入电视页
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        // 如果没有"轮播"就表示不在电视页
        Utils.funAssert("进入电视页失败", m_sActual.equalsIgnoreCase(m_sExpect));

        m_com.Enter(); // 打开轮播视频
        m_com.Sleep(m_iWaitSec);
        m_com.Enter(); // 调出轮播菜单
        m_sObjId = Infos.S_CAROUSEL_CHANNLE; // 轮播菜单里的控件
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId, 0); // 第一个节目: 24小时热播
        Utils.funAssert("轮播视频打开失败", m_uiObj.exists());
        m_com.Back();
        m_com.Sleep(m_iWaitSec);
        Utils.Print("play case1 播放轮播视频 END");
    }

    @Test
    public void case02_playVIPPageVideo(){
        Utils.Print("play case2 金卡会员的小窗口播放 START");
        m_enterPage.enterVIPPage(); // 进入金卡会员主页，金卡会员页小窗口自动播放
        //TODO 需要程序提供小窗口播放状态获取的接口
        m_com.Sleep(m_iWaitSec);
        Utils.Print("play case2 金卡会员的小窗口播放 END");
    }

    @Test
    public void case03_playFreeVideo(){
        Utils.Print("play case3 播放视频分类-最新的视频 START");
        m_com.Navigation("hh91224499"); // 进入视频分类-最新
        // 顶部Tabs列表，可能会变化
        String [] aExpectTabs = new String []{"全部", "电影", "电视剧", "少儿", "综艺", "纪实"};
        m_sObjId = Infos.S_TAB_TITLE_ID; // 视频分类-最新页面的Tab id
        for (int i = 0; i < aExpectTabs.length; ++i){
            String sTabText = m_com.getUiObjText(m_com.getUiObject(m_com.BY_RESID, m_sObjId, i));
//            Utils.Print(sTabText);
            if(!aExpectTabs[i].equalsIgnoreCase(sTabText)){
                Utils.funAssert("视频分类-最新页的Tabs排序不正确（请查证正确排序）", false);
                return;
            }
        }
        m_com.Sleep(m_iWaitSec);
        m_com.Navigation("2499994"); // 播放全部里的第1个免费视频，位置需要调整
        m_com.Sleep(m_iWaitSec);
        m_com.Enter(); // 暂停
        m_sObjId = Infos.S_CONTROL_PAUSE_BTN; // 暂停按钮的id
        m_uiObj = m_com.getUiObjByResId(m_sObjId);
        Utils.funAssert("播放[视频分类-最新]里的视频失败！", m_uiObj.exists());
        Utils.Print("play case3 播放视频分类-最新的视频 END");
    }

    @Test
    public void case04_playSpecialVideo(){
        Utils.Print("play case4 播放视频-电影-推荐的专题 START");
        m_com.Navigation(new int[]{-1, -1, 2, 4}); // 视频-电影
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Down();
        m_com.Right(7); // 推荐-专题（专题的位置编号：7 可能会发生变化）
        m_com.Enter();
        m_sObjId = Infos.S_SPECIAL_PLAYER; // 专题播放器
        m_uiObj = m_com.getUiObjByResId(m_sObjId);
        Utils.funAssert("进入[视频-电影-推荐]7号位的专题失败", m_uiObj.exists());
        m_com.Sleep(m_iWaitSec);
        Utils.Print("play case4 播放视频-电影-推荐的专题 END");
    }

    @Test
    public void case05_06_playHistoryPlay(){
        Utils.Print("play case5 6 播放视频历史记录");
        m_tfttp.playOnePlay(); // case5
        m_com.Sleep(m_iWaitSec);
        m_com.Back(2);
        m_enterPage.enterHistoryPage(); // case6
        // com.Down(); 2.3版本开始不需要按下方向键
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 2);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
        m_com.Enter();
        m_com.isUiObjExists(m_com.BY_RESID, Infos.S_CONTROL_PAUSE_BTN, "没有开始播放观看记录！");
    }

    @Test
    public void case07_playRecommendFilm(){
        Utils.Print("play case7 播放电影-推荐里的大图视频");
        m_enterPage.enterFilmPage();
        m_com.Down();
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter();
        m_com.isUiObjExists(m_com.BY_RESID, Infos.S_CONTROL_PAUSE_BTN,
                "没有播放电影-推荐里的大图视频");
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case08_playLiveVideo(){
        Utils.Print("play case8 播放直播内容——首页看看新闻");
        m_com.Navigation(new int[]{-1, 1, 2, 2, 1, 4});
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case09_playSportsSpecialVideo(){
        Utils.Print("play case9 播放体育-专题小视频");
        m_enterPage.enterSportsTabPage();
        m_com.Down();
        m_com.Right(2);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case10_playChildSpecialVideo(){
        Utils.Print("play case10");
        m_enterPage.enterTVPlayPage(); // 点击电视剧栏目
        m_enterPage.enterChildPage();  // 点击少儿栏目
        m_com.Right(); // 进入专题分类
        m_com.Down();
        m_com.Right();
        m_com.Enter(); // 点击一个专题（非小视频播放专题）
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Right(4);
        m_com.Enter(); // 进入一个专题视频详情页
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter(); // 播放专题视频
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case11_playBestvLive(){
        Utils.Print("play case11 播放Bestv直播大厅的视频");
        m_enterPage.enterBestvLivePage();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case12_playFreeVideo(){ // 选择[视频]首页的免费视频进行播放
        Utils.Print("play case12 选择[视频]首页的免费视频进行播放");
        m_com.Home();
        m_com.Right(5); // 免费视频的位置 5 可能会变
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case13_playSpecialVideo(){ // 选择[视频]首页的专题视频进行播放
        Utils.Print("play case13 选择[视频]首页的专题视频进行播放");
        m_com.Home();
        m_com.Right(4); // 专题视频的位置 4 可能会变
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Right(3);
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case14_playPayVideo(){
        Utils.Print("play case14 选择[视频]首页的付费视频进行播放");
        m_enterPage.enterVIPPage(); // 首页没有付费视频，就去金卡会员页播放
        m_com.Enter(3);
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case15_16_playNewsVideo(){
        Utils.Print("play case15 16");
        m_com.Home(); // case 15 播放视频首页的新闻
        m_com.Right(2);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
        m_com.Down(); // case 16 播放新闻分类里的一个Tab
        m_com.Right();
        m_com.Down(3);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case17_playNewsInClassification(){
        Utils.Print("play case17 播放“视频分类”里的新闻");
        m_enterPage.enterVideoClassificationPage();
        m_com.Right(3); // 新闻的位置会变化
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case18_playFilmNewSpecial(){
        Utils.Print("play case18 选择“电影-新片前瞻-专题”里的视频进行播放");
        m_enterPage.enterFilmPage();
        m_com.Right();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Down();
        m_com.Right(2);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case19_playLookBack(){
        Utils.Print("play case19 回看节目");
        m_enterPage.enterBestvLivePage();
        m_com.Right(2);
        m_com.Down();
        m_com.Enter();
        m_com.Down(2);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void case20_playHistoryAndSpecial(){
        Utils.Print("play case20 ");
        case18_playFilmNewSpecial(); // 点击播放记录卡片，播放记录中有小窗口播放的专题A
        m_com.Back(2);
        m_enterPage.enterHistoryPage();
        case18_playFilmNewSpecial(); // 点击电影-新片前瞻，点击专题A开始播放
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void clickDataReport(){
        Utils.Print("case1 点击视频首页的三幅图中的1个");
        m_com.Navigation("h49999"); // case1 点击视频首页的三幅图中的1个

        Utils.Print("case2 点击视频首页的电影栏目");
        m_com.Navigation("5249999"); // case2 点击视频首页的电影栏目

        Utils.Print("case3 点击视频首页的电视剧栏目");
        m_com.Navigation("5149999"); // case3 点击视频首页的电视剧栏目

        Utils.Print("case4 点击电影-推荐里的大图视频");
        m_com.Navigation("5349999"); // case4 点击电影-推荐里的大图视频
        m_com.Navigation("249999");

        Utils.Print("case5 点击直播");
        m_com.Navigation("h1221499999"); // case5 点击直播
        m_com.Back(2);

        Utils.Print("case6 点击视频分类");
        m_com.Navigation("349999"); // case6 点击视频分类

        Utils.Print("case7 点击[最新]卡片");
        m_com.Enter(); // case7 点击[最新]卡片

        Utils.Print("case8 点击全部里的1个免费视频");
        m_com.Navigation("9999249999"); // case8 点击全部里的1个免费视频

        Utils.Print("case9 点击金卡会员卡片");
        m_com.Navigation("h1249999"); // case9 点击金卡会员卡片

        Utils.Print("case10 点击视频首页的专题");
        m_com.Navigation("h111149999"); //case10 点击视频首页的专题，位置会变

        Utils.Print("case11 点击视频首页的专题里的一个节目");
        m_com.Navigation("1149999"); // case11 点击视频首页的专题里的一个节目

        Utils.Print("case12 点击视频首页的付费节目");
        m_com.Navigation("h12499991149999"); // case12 点击视频首页的付费节目，位置会变

        Utils.Print("case13 点击视频-电影-最新前瞻的专题");
        m_enterPage.enterFilmPage(); // case13 点击视频-电影-最新前瞻的专题
        m_com.Navigation("1999921149999");

        Utils.Print("case14 点击视频-电影-全部里的正片");
        m_enterPage.enterFilmPage(); // case14 点击视频-电影-全部里的正片
        m_com.Navigation("39999211149999");

        Utils.Print("case15、16 点击视频-电影-推荐-专题");
        m_enterPage.enterFilmPage(); // case15、16 点击视频-电影-推荐-专题
        m_com.Navigation("2111049999");
        m_com.Navigation("249999"); // 点击专题里的影片

        Utils.Print("case17 点击体育-专题-小视频");
        m_enterPage.enterSportsTabPage();
        m_com.Navigation("21149999"); // case17 点击体育-专题-小视频

        Utils.Print("case18 1、点击电视剧栏目");
        m_com.Navigation("h2149999"); // case18 1、点击电视剧栏目

        Utils.Print("case19 2、点击少儿栏目");
        m_com.Navigation("5149999"); // case19 2、点击少儿栏目

        Utils.Print("case20 3、点击专题分类中的一个专题");
        m_com.Navigation("11999921249999"); // case20 3、点击专题分类中的一个专题

        Utils.Print("case21 4、点击专题里的一个节目");
        m_com.Navigation("1149999"); // case21 4、点击专题里的一个节目

        Utils.Print("case22 点击轮播视频");
        m_com.Navigation("h3499999"); // case22 点击轮播视频

        Utils.Print("case23 进入Bestv直播大厅");
        m_enterPage.enterBestvLivePage(); // case23 进入Bestv直播大厅

        Utils.Print("case24 退出Bestv直播大厅");
        m_com.Back(); // case24 退出Bestv直播大厅
// case25 点击视频首页栏目-蜡笔小新(有时候是其他的节目，位置也需要修改)

        Utils.Print("case26 点击视频首页免费节目");
        m_com.Navigation("h11249999"); // case26 点击视频首页免费节目

        Utils.Print("case27 1、点击视频首页新闻");
        m_com.Navigation("h1149999"); // case27 1、点击视频首页新闻

        Utils.Print("case28 2、点击新闻分类里的一个Tab");
        m_com.Navigation("2122249999"); // case28 2、点击新闻分类里的一个Tab

        Utils.Print("case29 1、点击视频分类");
        m_com.Navigation("h12249999"); // case29 1、点击视频分类

        Utils.Print("case30 2、点击新闻");
        m_com.Navigation("1149999"); // case30 2、点击新闻，位置会变化

        Utils.Print("case31 3、点击新闻分类里的一个Tab");
        m_com.Navigation("212249999"); // case31 3、点击新闻分类里的一个Tab
        m_com.Sleep(m_iWaitSec);
    }

    @Test
    public void testUntilFail(){
        m_com.Navigation("hh99991249999");
        m_com.Navigation("11111499991249904");
//        while(true) {
//            clickDataReport();
//        }
    }
}
