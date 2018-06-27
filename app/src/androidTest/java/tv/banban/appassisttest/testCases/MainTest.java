package tv.banban.appassisttest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.banban.appassisttest.common.Funcs;
import tv.banban.appassisttest.common.PhoneCommon;
import tv.banban.appassisttest.common.UiIds;
import tv.banban.common.Utils;

/**
 * Created by xuzx on 2016/12/22.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest{
    FirstTest mFirst = new FirstTest();
    static PhoneCommon m_com = null;

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;
    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    @Before
    public void setUp(){
        m_sResult = "自动化脚本运行失败！";
        m_lConsumeTime = Utils.getCurSecond();
    }
    @Test
    public void test_01_connectTvByScanning(){
        try {
            mFirst.setUp();
            m_com = mFirst.m_com;
            int iTimes = 10;
            for (int i = 0; i < iTimes; ++i) {
                mFirst.connectTV();
                m_sObjId = UiIds.SCONNECT_STATU_STRING;
                m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
                m_bPass = m_uiObj.exists();
                if(!m_bPass){
                    m_sResult = "连接电视后，右上角没有【已连接】状态！";
                }

                if (m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING, "TV not connected!")) {
                    m_com.uiObjClickById(UiIds.SCONNECT_STATU_STRING);
                    mFirst.disconnectTV();
                    m_bPass = !m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING); // 不应该存在
                    if(!m_bPass){
                        m_sResult = "断开连接后，右上角仍然是【已连接】状态！";
                    }
                    m_com.Back();
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void test_02_connectTvByInputIP(){
        try {
            mFirst.setUp();
            m_com = mFirst.m_com;
            int iTimes = 10;
            String sIP = "172.17.5.125";
            for (int i = 0; i < iTimes; ++i) {
                Funcs.Print("第" + i + "次输入IP！");
                mFirst.connectTVByIP(sIP);
                // 判断是否已经连接
                m_sObjId = UiIds.SCONNECT_STATU_STRING;
                m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
                m_bPass = m_uiObj.exists();
                if(!m_bPass){
                    m_sResult = "连接电视后，右上角没有【已连接】状态！";
                    Funcs.Print(m_sResult);
                    continue;
                }
                Funcs.Print("连接成功！");

                if (m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING, "TV not connected!")) {
                    m_com.uiObjClickById(UiIds.SCONNECT_STATU_STRING);
                    mFirst.disconnectTV();
                    Funcs.Print("断开连接！");
                    m_bPass = !m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING); // 不应该存在
                    if(!m_bPass){
                        m_sResult = "断开连接后，右上角仍然是【已连接】状态！";
                        Funcs.Print(m_sResult);
                    }
                    m_com.Back();
                    Funcs.Print("断开成功！返回前一页");
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
}