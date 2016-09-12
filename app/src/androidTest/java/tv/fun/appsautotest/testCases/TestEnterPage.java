package tv.fun.appsautotest.testCases;

import org.junit.Test;

import tv.fun.appsautotest.common.Common;
import tv.fun.appassisttest.common.Funcs;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for enter page.
 */
public class TestEnterPage {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    Common com = new Common();

    @Test
    public void enterSportsPage(){
        com.Navigation(new int[]{-1, 0, 1});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterFilmPage(){
        Funcs.Print("==============case: enter tv play page=================");
        com.Navigation(new int[]{-1, 2, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterTVPlayPage(){
        Funcs.Print("==============case: enter tv play page=================");
        com.Navigation(new int[]{-1, 2, 1, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterHistoryPage(){
        com.Navigation(new int[]{-1, 1, 4});
        com.Sleep(iOneSecond * 4);
    }

    @Test
    public void enterChildPage(){
        Funcs.Print("==============case: enter tv play page=================");
        com.Navigation(new int[]{-1, 2, 1, 1, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterVIPPage(){
        com.Navigation(new int[]{-1, 1, 2, 4}); // 视频-金卡会员页小窗口自动播放
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterVideoClassificationPage(){
        com.Navigation(new int[]{-1, 1, 2, 2, 4});
        com.Sleep(iWaitSec);
    }

    @Test
    public void enterBestvLivePage(){ // 百视通直播大厅
        enterSportsPage();
        com.Down(4);
        com.Enter();
        com.Sleep(iWaitSec);
    }
}
