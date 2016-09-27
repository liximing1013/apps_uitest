package tv.fun.appsautotest.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tv.fun.appassisttest.common.Funcs;

public class Common {
	UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
	int iHeight = Height();
	int iWidth = Width();
	int iDelay = 1000;
	public final int ERR_CODE = 0;
	public final int BY_CLASS = 0;
	public final int BY_TEXT = 1;
	public final int BY_DESC = 2;
	public final int BY_RESID = 3;

	public UiDevice getDevice(){
		return device;
	}

	public void Sleep(int imSeconds){
		SystemClock.sleep(imSeconds);
	}
	
	public void Up(){
		device.pressDPadUp();
	}

    public void Up(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Up();
        }
    }
	
	public void Down(){
		device.pressDPadDown();
	}

    public void Down(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Down();
        }
    }

	public void Left(){
		device.pressDPadLeft();
	}

    public void Left(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Left();
        }
    }

	public void Right(){
		device.pressDPadRight();
	}

    public void Right(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Right();
        }
    }
	
	public void Delete(){
		device.pressDelete();
	}
	
	public void Enter(){
		device.pressEnter();
        Sleep(iDelay * 4);
	}

    public void Enter(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Enter();
        }
    }

	public void Search(){
		device.pressSearch();
	}

	public void PressKey(int keyCode){
		device.pressKeyCode(keyCode);
	}

	public void Home(){
		device.pressHome();	
	}

	public void Home(int iTimes){
		for(int i = 0; i < iTimes; ++i){
			Home();
		}
	}
	public void Menu(){
		device.pressMenu();
	}

	public void Back(){
		device.pressBack();
	}

    public void Back(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Back();
        }
    }

	public void Click(int x, int y){
		device.click(x, y);
	}

	public int Height(){
		return device.getDisplayHeight();
	}
	
	public int Width(){
		return device.getDisplayWidth();
	}
	
//	public UiObject getUiObjByResId(String resId){
//		UiObject uiObj = null;
//		try {
//			uiObj = new UiObject(new UiSelector().resourceId(resId));
//		} catch (Exception e) {
//			Funcs.WriteLogs(String.format("没有找到resource_id为[%s]的对象\r\n%s", resId, e), ERR_CODE);
//		}
//		return uiObj;
//	}
//
//	public UiObject2 getUiObjByDesc(String sDesc){
//		UiObject2 uiObj = null;
//		try {
//			//uiObj = new UiObject(new UiSelector().description(sDesc));
//            uiObj = device.findObject(By.desc(sDesc));
//		} catch (Exception e) {
//			Funcs.WriteLogs(String.format("没有找到content-desc为[%s]的对象\r\n%s", sDesc, e), ERR_CODE);
//		}
//		return uiObj;
//	}
//
//	public UiObject2 getUiObjByText(String sText){
//		UiObject2 uiObj = null;
//		try {
//			//uiObj = new UiObject(new UiSelector().text(sText));
//            uiObj = device.findObject(By.text(sText));
//		} catch (Exception e) {
//			Funcs.WriteLogs(String.format("没有找到text为[%s]的对象\r\n%s", sText, e), ERR_CODE);
//		}
//		return uiObj;
//	}
//
//	public UiObject2 getUiObjByClass(String sClass){
//		UiObject2 uiObj = null;
//		try {
//			//uiObj = new UiObject(new UiSelector().className(sClass));
//            uiObj = device.findObject(By.clazz(sClass));
//		} catch (Exception e) {
//			Funcs.WriteLogs(String.format("没有找到class为[%s]的对象\r\n%s", sClass, e), ERR_CODE);
//		}
//		return uiObj;
//	}

// iType: 0-class 1-text 2-desc
//public UiObject2 getUiObject(int iType, String sContent){
//    UiObject2 uiObj = null;
//    switch (iType) {
//        case BY_CLASS:
//            uiObj = getUiObjByClass(sContent);  // 根据class查找obj
//            break;
//        case BY_TEXT:
//            uiObj = getUiObjByText(sContent);  // 根据text查找obj
//            break;
//        case BY_DESC:
//            uiObj = getUiObjByDesc(sContent);  // 根据desc查找obj
//            break;
//        default:
//            uiObj = getUiObjByResId(sContent);  // 根据id查找obj
//            break;
//    }
//    return uiObj;
//}

    public boolean isUiObjExists(String sResId){
        UiObject uiObj = null;
        uiObj = getUiObjByResId(sResId);  // 根据id查找obj
        return uiObj.exists();
    }

    public boolean isUiObjExists(int iType, String sContent, String sError){
        UiObject uiObj = getUiObject(iType, sContent);
        boolean exists = true;
        if(!uiObj.waitForExists(iDelay)){
            exists = false;
            if(!sError.equalsIgnoreCase("")){
                Funcs.WriteLogs(sError, ERR_CODE);
            }
        }
        return exists;
    }

    public UiObject getUiObjByResId(String resId){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().resourceId(resId));
        } catch (Exception e) {
            Funcs.WriteLogs(String.format("没有找到resource_id为[%s]的对象\r\n%s", resId, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByDesc(String sDesc){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().description(sDesc));
        } catch (Exception e) {
            Funcs.WriteLogs(String.format("没有找到content-desc为[%s]的对象\r\n%s", sDesc, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByText(String sText){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().text(sText));
        } catch (Exception e) {
            Funcs.WriteLogs(String.format("没有找到text为[%s]的对象\r\n%s", sText, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByClass(String sClass){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().className(sClass));
        } catch (Exception e) {
            Funcs.WriteLogs(String.format("没有找到class为[%s]的对象\r\n%s", sClass, e), ERR_CODE);
        }
        return uiObj;
    }

    // iType: 0-class 1-text 2-desc
    public UiObject getUiObject(int iType, String sContent){
        UiObject uiObj = null;
        switch (iType) {
            case BY_CLASS:
                uiObj = getUiObjByClass(sContent);  // 根据class查找obj
                break;
            case BY_TEXT:
                uiObj = getUiObjByText(sContent);  // 根据text查找obj
                break;
            case BY_DESC:
                uiObj = getUiObjByDesc(sContent);  // 根据desc查找obj
                break;
            default:
                uiObj = getUiObjByResId(sContent);  // 根据id查找obj
                break;
        }
        return uiObj;
    }

    public String getUiObjText(UiObject uiObj){
		try {
			return uiObj.getText();
		} catch (UiObjectNotFoundException e) {
			return "";
		}
	}
	
	public boolean uiObjClick(UiObject uiObj){
		if(uiObj.exists()){
			try {
				return uiObj.click();
			} catch (UiObjectNotFoundException e) {
				Funcs.WriteLogs("ui click error: " + e, ERR_CODE);
			}
		}
		return false;
	}
	
//	public void uiObjClickByType(int iType, String sContent, int iTimes){
//		UiObject uiObj = null;
//		switch (iType) {
//		case BY_CLASS:
//			uiObj = getUiObjByClass(sContent);  // 根据class查找obj
//			break;
//		case BY_TEXT:
//			uiObj = getUiObjByText(sContent);  // 根据text查找obj
//			break;
//		case BY_DESC:
//			uiObj = getUiObjByDesc(sContent);  // 根据desc查找obj
//			break;
//		default:
//			uiObj = getUiObjByResId(sContent);  // 根据id查找obj
//			break;
//		}
//		if(isUiObjExists(iType, sContent, "")){
//			for(int i = 0; i < iTimes; ++i){
//				uiObjClick(uiObj);
//			}
//		}
//	}
	
//	public boolean uiObjClickById(String resId){
//		UiObject uiObj = getUiObjByResId(resId);
//		return uiObjClick(uiObj);
//	}
//
//	public boolean uiObjClickByDesc(String sDesc){
//		UiObject uiObj = getUiObjByDesc(sDesc);
//		return uiObjClick(uiObj);
//	}
//
//	public boolean uiObjClickByText(String sText){
//		UiObject uiObj = getUiObjByText(sText);
//		return uiObjClick(uiObj);
//	}
	
	public UiObject getUiObject(int iType, String sContent, int index){
        UiObject uiObj = null;
		try {
			switch (iType) {
			case BY_CLASS:
				uiObj = new UiObject(new UiSelector().className(sContent).instance(index));
				break;
			case BY_TEXT:
				uiObj = new UiObject(new UiSelector().text(sContent).instance(index));
				break;
			case BY_DESC:
				uiObj = new UiObject(new UiSelector().description(sContent).instance(index));
				break;
			default:
				uiObj = new UiObject(new UiSelector().resourceId(sContent).instance(index));
				break;
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get uiobject child error: " + e, ERR_CODE);
		}
		return uiObj;
	}
	
	public UiObject getUiObjChild(UiObject uiObj, int iChildType, String sChildContent, int index){
		UiObject uiChildObject = null;
		try {
			int iChilds = uiObj.getChildCount();
			if(index >= iChilds){
				index = iChilds - 1;
			}
			switch (iChildType) {
			case BY_CLASS:
				uiChildObject = uiObj.getChild(new UiSelector().className(sChildContent).instance(index));
				break;
			case BY_TEXT:
				uiChildObject = uiObj.getChild(new UiSelector().text(sChildContent).instance(index));
				break;
			case BY_DESC:
				uiChildObject = uiObj.getChild(new UiSelector().description(sChildContent).instance(index));
				break;
			default:
				uiChildObject = uiObj.getChild(new UiSelector().resourceId(sChildContent).instance(index));
				break;
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get uiobject child error: " + e, ERR_CODE);
		}
		return uiChildObject;
	}
	
	public boolean pressBackToFindObj(UiObject obj, int iTimes){
		for(int i = 0; i < iTimes; ++i){
			if(obj.exists()){
				return true;
			}
			Back();
		}
		return false;
	}
	
	public boolean waitTillOccur(int iType, String sContent, int index, int iWaitSec){
        boolean bOccur = true;
		long iCurSec = Funcs.CurSecond();
        UiObject uiObj = null;
		while(null == uiObj) {
			if(Funcs.CurSecond() >= iCurSec + 10){
				Funcs.Print("waiting time is up, obj not occur!");
                bOccur = false;
				break;
			}
            uiObj = getUiObject(iType, sContent, index);
		}
        Sleep(iWaitSec * iDelay);
        return bOccur;
	}
	
	public boolean waitTillGone(int iType, String sContent, int index, int iWaitSec){
        boolean bGone = true;
		long iCurSec = Funcs.CurSecond();
        UiObject uiObj = getUiObject(iType, sContent, index);
		while(uiObj.exists()) {
			if(Funcs.CurSecond() >= iCurSec + 10){
				Funcs.Print("waiting time is up, obj not gone!");
                bGone = false;
				break;
			}
            uiObj = getUiObject(iType, sContent, index);
		}
        Sleep(iWaitSec * iDelay);
        return bGone;
	}

    // h - home
    public void Navigation(String sPath){
        int iLen = sPath.length();
        int[] liPath = new int[iLen];
        for(int i = 0; i < iLen; ++i){
            String sTemp = String.valueOf(sPath.charAt(i));
            if(sTemp.equalsIgnoreCase("h")){
                liPath[i] = -1;
            }
            else {
                int iTemp = Integer.parseInt(sTemp);
                liPath[i] = iTemp;
            }
        }
        Navigation(liPath);
    }

    // 0 1 2 3 - up right down left
    // -1 4 5 9 - home enter back sleep
    public void Navigation(int[] liPath){
        int iDirect;
        for(int i = 0; i < liPath.length; ++i){
            iDirect = liPath[i];
            if(-1 == iDirect){
                Home();
                Sleep(iDelay);
            }
            else if(0 == iDirect){
                Up();
            }
            else if(1 == iDirect){
                Right();
            }
            else if(2 == iDirect){
                Down();
            }
            else if(3 == iDirect){
                Left();
            }
            else if(4 == iDirect){
                Enter();
            }
            else if(5 == iDirect){
                Back();
            }
            else if(9 == iDirect){
                Sleep(iDelay);
            }
            Sleep(iDelay);
        }
    }

	public void Navigation(int[] liPath, int iWaiSec){
		int iDirect;
		for(int i = 0; i < liPath.length; ++i){
			iDirect = liPath[i];
			if(-1 == iDirect){
				Home();
			}
			else if(0 == iDirect){
				Up();
			}
			else if(1 == iDirect){
				Right();
			}
			else if(2 == iDirect){
				Down();
			}
			else if(3 == iDirect){
				Left();
			}
			else if(4 == iDirect){
				Enter();
			}
			else if(5 == iDirect){
				Back();
			}
            else if(9 == iDirect){
                Sleep(iDelay);
            }
            Sleep(iWaiSec);
		}
	}

    public String executeCommand(String[] cmd) {
        Funcs.Print("Exec started!");
        StringBuffer sb = new StringBuffer("");
        Process pr = null;
        try{
            pr = Runtime.getRuntime().exec(cmd);
            BufferedInputStream bi = new BufferedInputStream(pr.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bi));
            String line;
            while((line = br.readLine()) != null){
                sb.append(line + "\n");
                Funcs.Print(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sRes = sb.toString();
        Funcs.Print("Exec finished!");
        return sRes;
    }

    public void setRefreshFailWatcher(){
        device.registerWatcher("BufferRefreshFailWatcher", new BufferRefreshFailWatcher());
    }

    private class BufferRefreshFailWatcher implements UiWatcher {
        @Override
        public boolean checkForCondition() {
            Funcs.Print("Invoke BufferRefreshFailedWatcher.checkForCondition().");

            UiObject2 errorText = device.findObject(By.textContains("缓冲失败"));
            if (errorText != null) {
                // if buffer refresh error occur, stop testing process
                Funcs.Print("Found error(Buffer Refresh Failed), force exit testing process.");
                int existCode = 0;
                System.exit(existCode);
                return true;
            }
            return false;
        }
    }
}

