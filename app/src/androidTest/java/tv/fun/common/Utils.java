package tv.fun.common;

import android.os.Environment;
import android.util.Log;

import junit.framework.Assert;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by xuzx on 2016/10/14.
 * 通用函数，Java/Android中可以通用的函数就写在这里
 */

public class Utils {
	public static final String sTAG = "Utils";

    public static String getCurDate(){
        return getCurDate("yyyy-MM-dd");
    }
	public static String getCurDate(String sFormat){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(sFormat);
		return df.format(date);
	}

    public static String getCurTime(){
        return getCurTime("HH:mm:ss");
    }
	public static String getCurTime(String sFormat){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(sFormat);
		return df.format(date);
	}
	
	public static long getCurSecond(){
		Date date = new Date();
		return date.getTime() / 1000;
	}
	
	public static void Print(Object text){
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		Log.d(sTAG, sHead + text);
	}

	public static void Print(String sFormat, Object... args){
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		Log.d(sTAG, sHead + String.format(sFormat, args));
	}
	
	/* random int range: [0, iMax)
	if iSeed exists, the random is pseudo random
	everytime you run this code, you will get a same result
	so you should choose which is your purpose
		int iSeed = 10;
		Random ra = new Random(iSeed);
	*/
	public static int randInt(int iMax){
		Random ra = new Random();
		return ra.nextInt(iMax);
	}
	
	public static boolean makeDir(String sFolderName) {
        File folder = new File(sFolderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
	
	public static void writeLine(String sFileName, String sContent, boolean appendFlag){
		FileWriter fw = null;
		try{
			fw = new FileWriter(sFileName, appendFlag);
			fw.write(sContent);  
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void Log(String sFileName, String sLogText) {
        String sPath = Environment.getExternalStorageDirectory().getPath();
        Print(sPath);
        Log(sPath + "/autotest/log/", sFileName, sLogText);
    }
	public static void Log(String sFolderName, String sFileName, String sLogText) {
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		makeDir(sFolderName);
		writeLine(String.format("%s%s_%s", sFolderName, sFileName, getCurDate()), sHead + sLogText + "\r\n", true);
	}

    public static void writeLogs(String sContent){
        writeLogs("runtime_log.log", sContent);
    }
	public static void writeLogs(String sLogName, String sContent){
		Print(sContent);
		Log(sLogName, sContent);
	}
	
	public static void writeLogs(String sContent, int iLevel){
		Print(sContent);
		Log("runtime_log.log", sContent);
		switch (iLevel) {
		case 0:
			Log("runtime_log_error.log", "[ERROR]" + sContent + "  ToT");
			break;
		default:
			break;
		}
	}

    public static String getMethodName(int iLevel) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[iLevel];
        String methodName = e.getMethodName();
        return methodName;
    }

    // 条件判断结果为false时记录脚本的error log
    // sErrorMsg  : error log
    // bCondition : 条件判断的结果
    public static void funAssert(String sErrorMsg, boolean bCondition){
        if(!bCondition){
            writeLogs(sErrorMsg, 0);
            Assert.assertTrue(sErrorMsg, bCondition);
        }
    }

    public static void writeCaseLog(String sCaseName, String sOK, String sError, boolean bCondition){
        if(!bCondition){
            String sErrorText = String.format("[%s] %s", sCaseName, sError);
            Utils.funAssert(sErrorText, bCondition);
        }
        else{
            String sOKText = String.format("[%s] %s", sCaseName, sOK);
            Utils.writeLogs(sOKText);
        }
    }
}