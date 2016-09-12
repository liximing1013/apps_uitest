package tv.fun.appsautotest.testCases;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appassisttest.common.Funcs;
import tv.fun.appsautotest.common.Common;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for data report.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDataReport {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    Common com = new Common();
    TestFunTvTVPlay tfttp = new TestFunTvTVPlay();
    TestEnterPage enterPage = new TestEnterPage();

    @Test
    public void case01_playCyclePlay(){
        Funcs.Print("play case1 播放轮播视频");
        com.Navigation(new int[]{-1, -1, 3, 4}); // 播放轮播视频
        com.Sleep(iWaitSec);
    }

    @Test
    public void case02_playVIPPageVideo(){
        Funcs.Print("play case2 金卡会员的小窗口播放");
        enterPage.enterVIPPage(); // 视频-金卡会员页小窗口自动播放
        com.Sleep(iWaitSec);
    }

    @Test
    public void case03_playFreeVideo(){
        Funcs.Print("play case3");
        com.Navigation(new int[]{-1, 1, 2, 2, 4, 4}); // 进入视频分类-最新
        com.Sleep(iWaitSec);
        com.Navigation(new int[]{2, 4, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void case04_playSpecialVideo(){
        Funcs.Print("play case4");
        com.Navigation(new int[]{-1, -1, 2, 4}); // 视频-电影
        com.Sleep(iOneSecond * 4);
        com.Down();
        for(int i = 0; i < 7; ++i){ // 推荐-专题（位置 7 一般都会发生变化）
            com.Right();
        }
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case05_06_playHistoryPlay(){
        Funcs.Print("play case5 6");
        tfttp.playOnePlay(); // case5
        com.Back(2);
        enterPage.enterHistoryPage(); // case6
        com.Down();
        com.Enter();
        com.Sleep(iOneSecond * 2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case07_playRecommendFilm(){
        Funcs.Print("play case7");
        enterPage.enterFilmPage();
        com.Down();
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case08_playLiveVideo(){
        Funcs.Print("play case8");
        com.Navigation(new int[]{-1, 1, 2, 2, 1, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void case09_playSportsSpecialVideo(){
        Funcs.Print("play case9");
        enterPage.enterSportsPage();
        com.Down();
        com.Right();
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case10_playChildSpecialVideo(){
        Funcs.Print("play case10");
        enterPage.enterTVPlayPage();
        enterPage.enterChildPage();
        com.Right(2);
        com.Down();
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Right(4);
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case11_playBestvLive(){
        Funcs.Print("play case11");
        enterPage.enterBestvLivePage();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case12_playFreeVideo(){ // 选择[视频]首页的免费视频进行播放
        Funcs.Print("play case12");
        com.Home();
        com.Right(5); // 免费视频的位置 5 可能会变
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case13_playSpecialVideo(){ // 选择[视频]首页的专题视频进行播放
        Funcs.Print("play case13");
        com.Home();
        com.Right(4); // 专题视频的位置 4 可能会变
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Right(3);
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case14_playPayVideo(){
        Funcs.Print("play case14");
        enterPage.enterVIPPage(); // 首页没有付费视频，就去金卡会员页播放
        com.Enter(3);
        com.Sleep(iWaitSec);
    }

    @Test
    public void case15_16_playNewsVideo(){
        Funcs.Print("play case15 16");
        com.Home(); // case 15
        com.Right(2);
        com.Enter();
        com.Sleep(iWaitSec);
        com.Down(); // case 16
        com.Right();
        com.Down(3);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case17_playNewsInClassification(){
        Funcs.Print("play case17");
        enterPage.enterVideoClassificationPage();
        com.Right(2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case18_playFilmNewSpecial(){
        Funcs.Print("play case18");
        enterPage.enterFilmPage();
        com.Right();
        com.Sleep(iOneSecond * 4);
        com.Down();
        com.Right(2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case19_playLookBack(){
        Funcs.Print("play case19");
        enterPage.enterBestvLivePage();
        com.Right(2);
        com.Down();
        com.Enter();
        com.Down(2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case20_playHistoryAndSpecial(){
        Funcs.Print("play case20");
        case18_playFilmNewSpecial();
        com.Back(2);
        enterPage.enterHistoryPage();
        case18_playFilmNewSpecial();
        com.Sleep(iWaitSec);
    }

    @Test
    public void clickDataReport(){
        com.Navigation("h4"); // case1 点击视频首页的三幅图中的1个
        com.Navigation("524"); // case2 点击视频首页的电影栏目
        com.Navigation("514"); // case3 点击视频首页的电视剧栏目
        com.Navigation("534"); // case4 点击电影-推荐里的大图视频
        com.Sleep(iOneSecond * 4);
        com.Navigation("24");
        com.Navigation("h1221499999"); // case5 点击直播
        com.Back(2);
        com.Navigation("34"); // case6 点击视频分类
        com.Enter(); // case7 点击[最新]卡片
        com.Navigation("24"); // case8 点击全部里的1个免费视频
        com.Navigation("h124"); // case9 点击金卡会员卡片
        com.Navigation("h11114"); //case10 点击视频首页的专题
        com.Navigation("114"); // case11 点击视频首页的专题里的一个节目
        com.Navigation("h124114"); // case12 点击视频首页的付费节目
        enterPage.enterFilmPage(); // case13 点击视频-电影-最新前瞻的专题
        com.Navigation("1992114");
        enterPage.enterFilmPage(); // case14 点击视频-电影-全部里的正片
        com.Navigation("39921114");
        enterPage.enterFilmPage(); // case15、16 点击视频-电影-推荐-专题
        com.Navigation("211104");
        com.Navigation("24"); // 点击专题里的影片
        enterPage.enterSportsPage();
        com.Navigation("214"); // case17 点击体育-奥运会-小视频
        com.Navigation("h214"); // case18 1、点击电视剧栏目
        com.Navigation("514"); // case19 2、点击少儿栏目
        com.Navigation("11992124"); // case20 3、点击专题分类中的一个专题
        com.Navigation("114"); // case21 4、点击专题里的一个节目
        com.Navigation("h3499999"); // case22 点击轮播视频
        enterPage.enterBestvLivePage(); // case23 进入Bestv直播大厅
        com.Back(); // case24 退出Bestv直播大厅
// case25 点击视频首页栏目-蜡笔小新(有时候是其他的节目，位置也需要修改)
        com.Navigation("h1124"); // case26 点击视频首页免费节目
        com.Navigation("h114"); // case27 1、点击视频首页新闻
        com.Navigation("212224"); // case28 2、点击新闻分类里的一个Tab
        com.Navigation("h1224"); // case29 1、点击视频分类
        com.Navigation("114"); // case30 2、点击新闻
        com.Navigation("21224"); // case31 3、点击新闻分类里的一个Tab
        com.Sleep(iWaitSec);
    }

    //@Test
    public void testUntilFail(){
        while(true) {
            clickDataReport();
        }
    }
}
