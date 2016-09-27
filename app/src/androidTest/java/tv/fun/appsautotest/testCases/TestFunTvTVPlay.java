package tv.fun.appsautotest.testCases;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tv.fun.appassisttest.common.Funcs;
import tv.fun.appsautotest.common.Common;

/**
 * Created by xuzx on 2016/8/10.
 * Include the test cases for plays.
 */

@RunWith(AndroidJUnit4.class)
public class TestFunTvTVPlay {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    Common com = new Common();
    TestEnterPage enterPage = new TestEnterPage();
    String sPauseBtnID = "com.bestv.ott:id/control_panel_pause_layout_btn";
    String sPlayedTimeID = "com.bestv.ott:id/time_current";
    String sPlayerClass = "com.funshion.player.play.funshionplayer.VideoViewPlayer";
    String sDetailTitleID = "com.bestv.ott:id/detail_title";
    String sEpisodeID = "com.bestv.ott:id/tv_cell";
    //String sEpisodeTipID = "com.bestv.ott:id/detail_tip_button";

    @Before
    public void setUp() {
        com.setRefreshFailWatcher();
    }

    @Test
    public void collectOnePlay(){
        Funcs.Print("==============case: collect one play=================");
        enterPage.enterTVPlayPage();
        com.Navigation(new int[]{2, 1, 1, 4});
        com.waitTillOccur(com.BY_TEXT, "收藏", 0, 10);
        if(!com.isUiObjExists(com.BY_TEXT, "已收藏", "")){
            com.Right(2);
            com.Enter();
        }
    }

    public boolean isPlayerWork(){
        return com.isUiObjExists(com.BY_CLASS, sPlayerClass, "");
    }

    public boolean isPausing(){
        if(com.isUiObjExists(sPauseBtnID)){
            Funcs.Print("Player is pausing...");
            return true;
        }
        return false;
    }

    public String getPlayedTime(){
        String sPlayTime = "00:00";
        if(isPausing()) {
            sPlayTime = com.getUiObjText(com.getUiObject(com.BY_RESID, sPlayedTimeID, 0));
        }
        return sPlayTime;
    }

    // 将xx:xx:xx格式的时间转化为xxx秒
    public int timeTrans(String sTime){
        int iTime;
        int iHour = 0;
        int iMins = 0;
        int iSecs;
        String[] lsTime = sTime.split(":");

        if(3 == lsTime.length){
            iHour = Integer.parseInt(lsTime[0]);
            iMins = Integer.parseInt(lsTime[1]);
            iSecs = Integer.parseInt(lsTime[2]);
        }
        else if(2 == lsTime.length) {
            iMins = Integer.parseInt(lsTime[0]);
            iSecs = Integer.parseInt(lsTime[1]);
        }
        else{
            iSecs = Integer.parseInt(lsTime[0]);
        }

        iTime = 3600 * iHour + 60 * iMins + iSecs;
        return iTime;
    }

    @Test
    public void playOnePlay(){
        Funcs.Print("==============case: play one play=================");
        enterPage.enterTVPlayPage();
        com.Left();
        com.Sleep(iOneSecond * 5);
        com.Down();
        com.Navigation(new int[]{2, 1, 1, 1, 4});

        com.waitTillOccur(com.BY_TEXT, "收藏", 0, 10);
        String sTitle = com.getUiObjText(com.getUiObject(com.BY_RESID, sDetailTitleID, 0));
        Funcs.WriteLogs(String.format("播放电视剧[%s]", sTitle));
        com.Enter(); // 播放电视剧
    }

    // 0: not finished; 1: finished; -1: error
    public int isPlayFinished(){
        // String sEpisodesListID = "com.bestv.ott:id/detail_grid_layout"; // 2.2
        String sEpisodesListID = "com.bestv.ott:id/funtv_control";  // 2.3
        int iChilds = 0;
        String s1;
        String s2 = "0";
        try {
            iChilds = com.getUiObject(com.BY_RESID, sEpisodesListID, 0).getChildCount();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        if(iChilds > 0){
            s1 = com.getUiObjText(com.getUiObject(com.BY_RESID, sEpisodeID, 0));
        }
        else{
            Funcs.WriteLogs("剧集信息获取失败！", com.ERR_CODE);
            return -1;
        }
        if(iChilds > 1){
            s2 = com.getUiObjText(com.getUiObject(com.BY_RESID, sEpisodeID, 1));
        }
        if(Integer.parseInt(s1) - Integer.parseInt(s2) < 0){ // 已完结
            return 1;
        }
        else{
            return 0;
        }
    }

    // only use in detail page
    public void chooseNextEpisode(int iNum){
        for (int i = 0; i < iNum; ++i){
            if(1 == isPlayFinished()){
                com.Right();
            }
            else {
                com.Left();
            }
        }
    }

    // only use in detail page
    public void choosePrevEpisode(int iNum){
        for (int i = 0; i < iNum; ++i){
            if(1 == isPlayFinished()){
                com.Left();
            }
            else {
                com.Right();
            }
        }
    }

    @Test
    public void playNextEpisode(){
        Funcs.Print("==============case: play next episode=================");
        String selectEpisodeID = "com.bestv.ott:id/detail_select_button";
        if(isPlayerWork()) {
            com.Sleep(iOneSecond * 2);
            com.Back(2);
        }
        if(!com.isUiObjExists(sDetailTitleID)){
            Funcs.Print("not in detail page, play one play!");
            playOnePlay();
            com.Back(2);
        }
        com.uiObjClick(com.getUiObjByResId(selectEpisodeID));
        com.Down();
        Funcs.Print("即将播放下一集！");
        chooseNextEpisode(1);
        com.Enter(); // 播放电视剧
    }

    public void exitPlaying(String playTime){
        if(com.waitTillOccur(com.BY_CLASS, sPlayerClass, 0, 10)) {
            if(!isPausing()) {
                com.Enter(); // 暂停播放
            }
        }
        else{
            com.Right(2);
        }

        int iDeltaTime = timeTrans(playTime) - timeTrans(getPlayedTime());
        if(iDeltaTime < 0){
            iDeltaTime = 1;
        }
        com.Enter();
        Funcs.Print("[%s]秒后即将退出播放！", iDeltaTime);
        com.Sleep(iDeltaTime * 1000);
        com.Enter();
        com.Back(2);
    }

    @Test
    public void playOnePlayMinutes(){
        Funcs.Print("==============case: play one play some minutes=================");
        playOnePlay();
        com.Sleep(iWaitSec);
        exitPlaying("5:00");
    }

    @Test
    public void playUntilFail(){
        playOnePlayMinutes();
        com.Sleep(iOneSecond * 2);
        com.Right();
        com.Down();
        while(true){
            chooseNextEpisode(1);
            com.Enter();
            com.Sleep(iOneSecond * 5);
            exitPlaying("1:00");
        }
    }

    @Test
    public void test(){
//        String[] cmd = {"/system/bin/sh", "-c", "/data/local/tmp/sh/right_long_press.sh 1"};
//        com.executeCommand(cmd);
//        Funcs.Print("T: " + com.isUiObjExists(com.BY_TEXT, "缓冲失败，请检查网络设置后重新播放", ""));
        exitPlaying("1:00");
    }
}
