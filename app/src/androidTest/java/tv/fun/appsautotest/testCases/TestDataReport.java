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
        com.Navigation(new int[]{2, 4, 4}); // 播放全部里的第1个免费视频
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
        com.Sleep(iWaitSec);
        com.Back(2);
        enterPage.enterHistoryPage(); // case6
        // com.Down(); 2.3版本不需要按下方向键
        com.Enter();
        com.Sleep(iOneSecond * 2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case07_playRecommendFilm(){
        Funcs.Print("play case7 播放电影-推荐里的大图视频");
        enterPage.enterFilmPage();
        com.Down();
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case08_playLiveVideo(){
        Funcs.Print("play case8 播放直播内容——首页看看新闻");
        com.Navigation(new int[]{-1, 1, 2, 2, 1, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void case09_playSportsSpecialVideo(){
        Funcs.Print("play case9 播放体育-专题小视频");
        enterPage.enterSportsPage();
        com.Down();
        com.Right(2);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case10_playChildSpecialVideo(){
        Funcs.Print("play case10");
        enterPage.enterTVPlayPage(); // 点击电视剧栏目
        enterPage.enterChildPage();  // 点击少儿栏目
        com.Right(); // 进入专题分类
        com.Down();
        com.Right();
        com.Enter(); // 点击一个专题（非小视频播放专题）
        com.Sleep(iOneSecond * 4);
        com.Right(4);
        com.Enter(); // 进入一个专题视频详情页
        com.Sleep(iOneSecond * 4);
        com.Enter(); // 播放专题视频
        com.Sleep(iWaitSec);
    }

    @Test
    public void case11_playBestvLive(){
        Funcs.Print("play case11 播放Bestv直播大厅的视频");
        enterPage.enterBestvLivePage();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case12_playFreeVideo(){ // 选择[视频]首页的免费视频进行播放
        Funcs.Print("play case12 选择[视频]首页的免费视频进行播放");
        com.Home();
        com.Right(5); // 免费视频的位置 5 可能会变
        com.Enter();
        com.Sleep(iOneSecond * 4);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case13_playSpecialVideo(){ // 选择[视频]首页的专题视频进行播放
        Funcs.Print("play case13 选择[视频]首页的专题视频进行播放");
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
        Funcs.Print("play case14 选择[视频]首页的付费视频进行播放");
        enterPage.enterVIPPage(); // 首页没有付费视频，就去金卡会员页播放
        com.Enter(3);
        com.Sleep(iWaitSec);
    }

    @Test
    public void case15_16_playNewsVideo(){
        Funcs.Print("play case15 16");
        com.Home(); // case 15 播放视频首页的新闻
        com.Right(2);
        com.Enter();
        com.Sleep(iWaitSec);
        com.Down(); // case 16 播放新闻分类里的一个Tab
        com.Right();
        com.Down(3);
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case17_playNewsInClassification(){
        Funcs.Print("play case17 播放“视频分类”里的新闻");
        enterPage.enterVideoClassificationPage();
        com.Right(3); // 新闻的位置会变化
        com.Enter();
        com.Sleep(iWaitSec);
    }

    @Test
    public void case18_playFilmNewSpecial(){
        Funcs.Print("play case18 选择“电影-新片前瞻-专题”里的视频进行播放");
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
        Funcs.Print("play case19 回看节目");
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
        Funcs.Print("play case20 ");
        case18_playFilmNewSpecial(); // 点击播放记录卡片，播放记录中有小窗口播放的专题A
        com.Back(2);
        enterPage.enterHistoryPage();
        case18_playFilmNewSpecial(); // 点击电影-新片前瞻，点击专题A开始播放
        com.Sleep(iWaitSec);
    }

    @Test
    public void clickDataReport(){
        Funcs.Print("case1 点击视频首页的三幅图中的1个");
        com.Navigation("h49999"); // case1 点击视频首页的三幅图中的1个

        Funcs.Print("case2 点击视频首页的电影栏目");
        com.Navigation("5249999"); // case2 点击视频首页的电影栏目

        Funcs.Print("case3 点击视频首页的电视剧栏目");
        com.Navigation("5149999"); // case3 点击视频首页的电视剧栏目

        Funcs.Print("case4 点击电影-推荐里的大图视频");
        com.Navigation("5349999"); // case4 点击电影-推荐里的大图视频
        com.Navigation("249999");

        Funcs.Print("case5 点击直播");
        com.Navigation("h1221499999"); // case5 点击直播
        com.Back(2);

        Funcs.Print("case6 点击视频分类");
        com.Navigation("349999"); // case6 点击视频分类

        Funcs.Print("case7 点击[最新]卡片");
        com.Enter(); // case7 点击[最新]卡片

        Funcs.Print("case8 点击全部里的1个免费视频");
        com.Navigation("9999249999"); // case8 点击全部里的1个免费视频

        Funcs.Print("case9 点击金卡会员卡片");
        com.Navigation("h1249999"); // case9 点击金卡会员卡片

        Funcs.Print("case10 点击视频首页的专题");
        com.Navigation("h111149999"); //case10 点击视频首页的专题，位置会变

        Funcs.Print("case11 点击视频首页的专题里的一个节目");
        com.Navigation("1149999"); // case11 点击视频首页的专题里的一个节目

        Funcs.Print("case12 点击视频首页的付费节目");
        com.Navigation("h12499991149999"); // case12 点击视频首页的付费节目，位置会变

        Funcs.Print("case13 点击视频-电影-最新前瞻的专题");
        enterPage.enterFilmPage(); // case13 点击视频-电影-最新前瞻的专题
        com.Navigation("1999921149999");

        Funcs.Print("case14 点击视频-电影-全部里的正片");
        enterPage.enterFilmPage(); // case14 点击视频-电影-全部里的正片
        com.Navigation("39999211149999");

        Funcs.Print("case15、16 点击视频-电影-推荐-专题");
        enterPage.enterFilmPage(); // case15、16 点击视频-电影-推荐-专题
        com.Navigation("2111049999");
        com.Navigation("249999"); // 点击专题里的影片

        Funcs.Print("case17 点击体育-专题-小视频");
        enterPage.enterSportsPage();
        com.Navigation("21149999"); // case17 点击体育-专题-小视频

        Funcs.Print("case18 1、点击电视剧栏目");
        com.Navigation("h2149999"); // case18 1、点击电视剧栏目

        Funcs.Print("case19 2、点击少儿栏目");
        com.Navigation("5149999"); // case19 2、点击少儿栏目

        Funcs.Print("case20 3、点击专题分类中的一个专题");
        com.Navigation("11999921249999"); // case20 3、点击专题分类中的一个专题

        Funcs.Print("case21 4、点击专题里的一个节目");
        com.Navigation("1149999"); // case21 4、点击专题里的一个节目

        Funcs.Print("case22 点击轮播视频");
        com.Navigation("h3499999"); // case22 点击轮播视频

        Funcs.Print("case23 进入Bestv直播大厅");
        enterPage.enterBestvLivePage(); // case23 进入Bestv直播大厅

        Funcs.Print("case24 退出Bestv直播大厅");
        com.Back(); // case24 退出Bestv直播大厅
// case25 点击视频首页栏目-蜡笔小新(有时候是其他的节目，位置也需要修改)

        Funcs.Print("case26 点击视频首页免费节目");
        com.Navigation("h11249999"); // case26 点击视频首页免费节目

        Funcs.Print("case27 1、点击视频首页新闻");
        com.Navigation("h1149999"); // case27 1、点击视频首页新闻

        Funcs.Print("case28 2、点击新闻分类里的一个Tab");
        com.Navigation("2122249999"); // case28 2、点击新闻分类里的一个Tab

        Funcs.Print("case29 1、点击视频分类");
        com.Navigation("h12249999"); // case29 1、点击视频分类

        Funcs.Print("case30 2、点击新闻");
        com.Navigation("11149999"); // case30 2、点击新闻，位置会变化

        Funcs.Print("case31 3、点击新闻分类里的一个Tab");
        com.Navigation("212249999"); // case31 3、点击新闻分类里的一个Tab
        com.Sleep(iWaitSec);
    }

    //@Test
    public void testUntilFail(){
        while(true) {
            clickDataReport();
        }
    }
}
