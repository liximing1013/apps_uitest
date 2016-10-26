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
 * Include the test cases for data report.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDataReport {
    private int m_iOneSecond = 1000;
    private int m_iWaitSec = 8 * m_iOneSecond;
    private TvCommon m_com = new TvCommon();
    private TestFunTvTVPlay m_tfttp = new TestFunTvTVPlay();
    private TestEnterPage m_enterPage = new TestEnterPage();

    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;

    @Before
    public void setUp(){
        m_lConsumeTime = Utils.getCurSecond();
    }
    @After
    public void clearUp() {
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Home(2);
    }

    @Test
    public void Data_Play_01_Carousel(){
//        Utils.Print("play case1 播放轮播视频 START");
        m_com.Navigation("hh93999"); // 进入电视页

        // test point 1
        m_sExpect = "轮播"; // 如果没有"轮播"就表示不在电视页
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        Utils.writeCaseResult("进入电视页失败", m_bPass, m_lConsumeTime);

        // test point 2
        m_com.Enter(); // 打开轮播视频
        m_com.Sleep(m_iWaitSec);
        m_com.Enter(); // 调出轮播菜单

        m_sObjId = Infos.S_CAROUSEL_CHANNLE; // 轮播菜单里的控件
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId, 0); // 第一个节目: 24小时热播

        Utils.writeCaseResult("轮播视频打开失败", m_uiObj.exists(), m_lConsumeTime);
//        Utils.Print("play case1 播放轮播视频 END");
    }

    @Test
    public void Data_Play_02_VIPPageVideo(){
//        Utils.Print("play case2 金卡会员的小窗口播放 START");
        m_enterPage.enterVIPPage(); // 进入金卡会员主页，金卡会员页小窗口自动播放

        //TODO 需要程序提供小窗口播放状态获取的接口
        m_sExpect = "续费金卡会员";
        m_sObjId = Infos.S_VIP_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("播放金卡小窗口视频失败", m_bPass, m_lConsumeTime);
//        Utils.Print("play case2 金卡会员的小窗口播放 END");
    }

    @Test
    public void Data_Play_03_FreeVideo(){
//        Utils.Print("play case3 播放视频分类-最新的视频 START");
        m_com.Navigation("hh91224499"); // 进入视频分类-最新
        // 顶部Tabs列表，可能会变化
        String [] aExpectTabs = new String []{"全部", "电影", "电视剧", "少儿", "综艺", "纪实"};

        m_sObjId = Infos.S_TAB_TITLE_ID; // 视频分类-最新页面的Tab id
        for (int i = 0; i < aExpectTabs.length; ++i){
            m_sActual = m_com.getUiObjText(m_com.getUiObject(m_com.BY_RESID, m_sObjId, i));
//            Utils.Print(sTabText);
            if(!aExpectTabs[i].equalsIgnoreCase(m_sActual)){
                Utils.writeCaseResult("视频分类-最新页的Tabs排序不正确（请查证正确排序）",
                        false, m_lConsumeTime);
                return;
            }
        }
        m_com.Sleep(m_iWaitSec);
        m_com.Navigation("24994"); // 播放全部里的第1个免费视频，位置需要调整
        m_com.Sleep(m_iWaitSec);
        m_com.Enter(); // 暂停

        m_sObjId = Infos.S_CONTROL_PAUSE_BTN; // 暂停按钮的id
        m_uiObj = m_com.getUiObjByResId(m_sObjId);
        m_bPass = m_uiObj.exists();
        Utils.writeCaseResult("播放[视频分类-最新]里的视频失败", m_bPass, m_lConsumeTime);
//        Utils.Print("play case3 播放视频分类-最新的视频 END");
    }

    @Test
    public void Data_Play_04_SpecialVideo(){
//        Utils.Print("play case4 播放视频-电影-推荐的专题 START");
        m_enterPage.enterFilmPage(); // 视频-电影
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Down();
        m_com.Right(7); // 推荐-专题（专题的位置编号：7 可能会发生变化）
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_sObjId = Infos.S_SPECIAL_PLAYER; // 专题播放器
        m_uiObj = m_com.getUiObjByResId(m_sObjId);
        m_bPass = m_uiObj.exists();
        Utils.writeCaseResult("进入[视频-电影-推荐]7号位的专题失败", m_bPass, m_lConsumeTime);
//        Utils.Print("play case4 播放视频-电影-推荐的专题 END");
    }

    @Test
    public void Data_Play_05_PlayOnePlay(){
//        Utils.Print("play case5 6 播放视频历史记录 START");
        m_tfttp.playOnePlay(); // case5
        m_com.Sleep(m_iWaitSec);
        m_com.Back(2);
        Utils.writeCaseResult("播放电视剧失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_06_History(){
        m_enterPage.enterHistoryPage(); // case6
        // com.Down(); 2.3版本开始不需要按下方向键
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 8);
        m_com.Enter();
        m_com.Sleep(m_iWaitSec);
        m_com.Enter();
        m_bPass = m_com.isUiObjExists(m_com.BY_RESID, Infos.S_CONTROL_PAUSE_BTN, "");
        Utils.writeCaseResult("没有开始播放观看记录", m_bPass, m_lConsumeTime);
//        Utils.Print("play case6 播放视频历史记录 END");
    }

    @Test
    public void Data_Play_07_RecommendFilm(){
//        Utils.Print("play case7 播放电影-推荐里的大图视频");
        m_enterPage.enterFilmPage();
        m_com.Down();
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 8);
        m_com.Enter();
        m_bPass = m_com.isUiObjExists(m_com.BY_RESID, Infos.S_CONTROL_PAUSE_BTN, "");
        Utils.writeCaseResult("没有播放电影-推荐里的大图视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_08_LiveVideo(){
//        Utils.Print("play case8 播放直播内容——看看新闻");
        m_enterPage.enterVideoClassificationPage();
        m_com.Navigation("991149999"); // 点击视频分类 - 看看新闻

        m_sExpect = "看看新闻";
        m_sObjId = Infos.S_KANKAN_NEWS_LOG_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("没有进入看看新闻直播", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_09_SportsSpecialVideo(){
//        Utils.Print("play case9 播放体育-专题小视频");
        m_enterPage.enterSportsTabPage();
        m_com.Down();
        m_com.Right(2);
        m_com.Enter();
        Utils.writeCaseResult("没有进入体育专题小视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_10_ChildSpecialVideo(){
//        Utils.Print("play case10");
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
        Utils.writeCaseResult("没有播放儿童专题视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_11_BestvLive(){
//        Utils.Print("play case11 播放Bestv直播大厅的视频");
        m_enterPage.enterBestvLivePage();

        m_sExpect = "看看新闻";
        m_sObjId = Infos.S_BEST_LIVE_HALL_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("进入百视通直播大厅失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_12_FreeVideo(){ // 选择[视频]首页的免费视频进行播放
//        Utils.Print("play case12 选择[视频]首页的免费视频进行播放");
        m_com.Home();
        m_com.Right(5); // 免费视频的位置 5 可能会变
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter();
        Utils.writeCaseResult("没有播放首页免费视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_13_SpecialVideo(){ // 选择[视频]首页的专题视频进行播放
//        Utils.Print("play case13 选择[视频]首页的专题视频进行播放");
        m_com.Home();
        m_com.Right(4); // 专题视频的位置 4 可能会变
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Right(3);
        m_com.Enter();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Enter();
        Utils.writeCaseResult("没有播放首页专题视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_14_PayVideo(){
//        Utils.Print("play case14 选择[视频]首页的付费视频进行播放");
        m_enterPage.enterVIPPage(); // 首页没有付费视频，就去金卡会员页播放
        m_com.Enter(3);
        Utils.writeCaseResult("没有播放付费视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_15_News() {
//        Utils.Print("play case15 16");
        m_com.Home(); // case 15 播放视频首页的新闻
        m_com.Right(2);
        m_com.Enter();
        Utils.writeCaseResult("没有播放首页新闻", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_16_NewsTab(){
        m_com.Down(); // case 16 播放新闻分类里的一个Tab
        m_com.Right();
        m_com.Down(3);
        m_com.Enter();
        Utils.writeCaseResult("没有播放首页新闻的左侧的Tab", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_17_NewsInClassification(){
//        Utils.Print("play case17 播放“视频分类”里的新闻");
        m_enterPage.enterVideoClassificationPage();
        m_com.Right(2); // 新闻的位置会变化
        m_com.Enter();
        Utils.writeCaseResult("没有播放视频分类里的新闻", m_bPass, m_lConsumeTime);
    }

    public void play_FilmNewSpecial(){
        m_enterPage.enterFilmPage();
        m_com.Right();
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Down();
        m_com.Right(2);
        m_com.Enter();
    }
    @Test
    public void Data_Play_18_FilmNewSpecial(){
//        Utils.Print("play case18 选择“电影-新片前瞻-专题”里的视频进行播放");
        play_FilmNewSpecial();
        Utils.writeCaseResult("没有播放电影-新片前瞻-专题里的视频", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_19_LookBack(){
//        Utils.Print("play case19 回看节目");
        m_enterPage.enterBestvLivePage();
        m_com.Right(2);
        m_com.Down();
        m_com.Enter();
        m_com.Down(2);
        m_com.Enter();
        Utils.writeCaseResult("没有回看节目", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Play_20_HistoryAndSpecial(){
//        Utils.Print("play case20 ");
        play_FilmNewSpecial(); // 点击播放记录卡片，播放记录中有小窗口播放的专题A
        m_com.Back(2);
        m_enterPage.enterHistoryPage();
        play_FilmNewSpecial(); // 点击电影-新片前瞻，点击专题A开始播放
        Utils.writeCaseResult("专题播放失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_01_OneOfThreeMain() {
        Utils.Print("case1 点击视频首页的三幅图中的1个");
        m_com.Navigation("h4"); // case1 点击视频首页的三幅图中的1个
        Utils.writeCaseResult("点击首页三幅图中的1个失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_02_FilmCard() {
        Utils.Print("case2 点击视频首页的电影栏目");
        m_com.Navigation("h24"); // case2 点击视频首页的电影栏目
        Utils.writeCaseResult("点击视频首页的电影栏目失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_03_SerialCard() {
        Utils.Print("case3 点击视频首页的电视剧栏目");
        m_com.Navigation("h14"); // case3 点击视频首页的电视剧栏目
        Utils.writeCaseResult("点击视频首页的电视剧栏目失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_04_FilmBigVideo() {
        Utils.Print("case4 点击电影-推荐里的大图视频");
        m_com.Navigation("h349924"); // case4 点击电影-推荐里的大图视频
        Utils.writeCaseResult("点击电影-推荐里的大图视频失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_05_Live() {
        Utils.Print("case5 点击直播");
        m_com.Navigation("h111111499"); // case5 点击直播
        m_com.Back(2);
        Utils.writeCaseResult("点击直播失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_06_08_VcNewestVideo() {
        Utils.Print("case6 点击视频分类");
        m_com.Navigation("h1224"); // case6 点击视频分类
        Utils.Print("case7 点击[最新]卡片");
        m_com.Enter(); // case7 点击[最新]卡片
        Utils.Print("case8 点击全部里的1个免费视频");
        m_com.Navigation("992499"); // case8 点击全部里的1个免费视频
        Utils.writeCaseResult("点击视频分类最新里的免费视频失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_09_VIPCard() {
        Utils.Print("case9 点击金卡会员卡片");
        m_com.Navigation("h12499"); // case9 点击金卡会员卡片

        m_sExpect = Infos.S_TEXT_VIP_TITLE;
        m_sObjId = Infos.S_VIP_ID;
        m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

        Utils.writeCaseResult("点击金卡会员卡片失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_10_11_SpVideoInMain() {
        Utils.Print("case10 点击视频首页的专题");
        m_com.Navigation("h1111499"); //case10 点击视频首页的专题，位置会变
        Utils.Print("case11 点击视频首页的专题里的一个节目");
        m_com.Navigation("11499"); // case11 点击视频首页的专题里的一个节目
        Utils.writeCaseResult("点击视频首页的专题失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_12_PayVideoInMain() {
        Utils.Print("case12 点击视频首页的付费节目");
        m_com.Navigation("h1249911499"); // case12 点击视频首页的付费节目，位置会变
        Utils.writeCaseResult("点击视频首页的付费节目失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_13_SpVideoInNewestFilm() {
        Utils.Print("case13 点击视频-电影-最新前瞻的专题");
        m_enterPage.enterFilmPage(); // case13 点击视频-电影-最新前瞻的专题
        m_com.Navigation("199211499");
        Utils.writeCaseResult("点击视频-电影-最新前瞻的专题失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_14_VideoInAllFilm() {
        Utils.Print("case14 点击视频-电影-全部里的正片");
        m_enterPage.enterFilmPage(); // case14 点击视频-电影-全部里的正片
        m_com.Navigation("3992111499");
        Utils.writeCaseResult("点击视频-电影-全部里的正片失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_15_SpLanMuInRecommendFilm() {
        Utils.Print("case15 点击视频-电影-推荐-好莱坞");
        m_enterPage.enterFilmPage();
        m_com.Navigation("211100499");
        Utils.writeCaseResult("点击视频-电影-推荐-好莱坞失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_16_SpVideoInRecommendFilm() {
        Utils.Print("case16 点击视频-电影-推荐-专题");
        m_enterPage.enterFilmPage(); // case15、16 点击视频-电影-推荐-专题
        m_com.Navigation("21110499");
        m_com.Navigation("2499"); // 点击专题里的影片
        Utils.writeCaseResult("点击视频-电影-推荐-专题失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_17_SpVideoInSport() {
        Utils.Print("case17 点击体育-专题(小视频)");
        m_enterPage.enterSportsTabPage();
        m_com.Navigation("211499"); // case17 点击体育-专题(小视频)
        Utils.writeCaseResult("点击体育-专题(小视频)失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_18_21_SpVideoInChild() {
//        Utils.Print("case18 1、点击电视剧栏目");
        m_enterPage.enterTVPlayPage();
//        Utils.Print("case19 2、点击少儿栏目");
        m_enterPage.enterChildPage();
        m_com.Navigation("51499"); // case19 2、点击少儿栏目
//        Utils.Print("case20 3、点击专题分类中的一个专题");
        m_com.Navigation("1199212499"); // case20 3、点击专题分类中的一个专题
//        Utils.Print("case21 4、点击专题里的一个节目");
        m_com.Navigation("11499"); // case21 4、点击专题里的一个节目
        Utils.writeCaseResult("点击少儿栏目专题视频失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_22_Carousel(){
        Utils.Print("case22 点击轮播视频");
        m_com.Navigation("h34999"); // case22 点击轮播视频
        Utils.writeCaseResult("点击轮播视频失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_23_24_EnterAndQuitLiveHall(){
//        Utils.Print("case23 进入Bestv直播大厅");
        m_enterPage.enterBestvLivePage(); // case23 进入Bestv直播大厅
        Utils.writeCaseResult("进入直播大厅失败", m_bPass, m_lConsumeTime);
//        Utils.Print("case24 退出Bestv直播大厅");
        m_com.Back(); // case24 退出Bestv直播大厅
        Utils.writeCaseResult("退出直播大厅失败", m_bPass, m_lConsumeTime);
    }

//    @Test
//    public void Data_Click_25_LanMu(){
//     case25 点击视频首页栏目-蜡笔小新(有时候是其他的节目，位置也需要修改)
//    }

    @Test
    public void Data_Click_26_FreeVideoInMain(){
        Utils.Print("case26 点击视频首页免费节目");
        m_com.Navigation("h112499"); // case26 点击视频首页免费节目
        Utils.writeCaseResult("首页免费节目点击失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_27_28_NewsInMain(){
        Utils.Print("case27 1、点击视频首页新闻");
        m_com.Navigation("h11499"); // case27 1、点击视频首页新闻
        Utils.Print("case28 2、点击新闻分类里的一个Tab");
        m_com.Navigation("21222499"); // case28 2、点击新闻分类里的一个Tab
        Utils.writeCaseResult("首页新闻点击Tab失败", m_bPass, m_lConsumeTime);
    }

    @Test
    public void Data_Click_29_31_NewsInVc(){
        Utils.Print("case29 1、点击视频分类");
        m_com.Navigation("h122499"); // case29 1、点击视频分类
        Utils.Print("case30 2、点击新闻");
        m_com.Navigation("11499"); // case30 2、点击新闻，位置会变化
        Utils.Print("case31 3、点击新闻分类里的一个Tab");
        m_com.Navigation("2122499"); // case31 3、点击新闻分类里的一个Tab
        Utils.writeCaseResult("视频分类的新闻点击Tab失败", m_bPass, m_lConsumeTime);
    }
    @Test
    public void Data_Click_32_34_FirstVideoInVIPPage(){
        m_enterPage.enterVIPPage();
        m_com.Sleep(m_iWaitSec);
        m_com.Enter(); // case 32 1、金卡会员页，点击小窗口播放
        m_com.Sleep(m_iOneSecond * 2);
        m_com.Enter(); // case 33 2、点击【观看完整影片】按钮，进入详情页
        m_com.Sleep(m_iOneSecond * 10);
        Utils.writeCaseResult("金卡小窗口视频全屏后到详情页失败", m_bPass, m_lConsumeTime);
    }

//    @Test
    public void testUntilFail(){
//        m_com.Navigation("111114991249904");
//        while(true) {
        m_com.Navigation("hh99091929191900");
//            clickDataReport();
//        }
    }

//    @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}