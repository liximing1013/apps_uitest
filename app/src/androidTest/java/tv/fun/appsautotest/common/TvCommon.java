package tv.fun.appsautotest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tv.fun.common.Utils;
import tv.fun.common.Common;

public class TvCommon extends Common{
    int m_iDelay = 1000;
//	public UiObject getUiObjByResId(String resId){
//		UiObject uiObj = null;
//		try {
//			uiObj = new UiObject(new UiSelector().resourceId(resId));
//		} catch (Exception e) {
//			Utils.writeLogs(String.format("没有找到resource_id为[%s]的对象\r\n%s", resId, e), ERR_CODE);
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
//			Utils.writeLogs(String.format("没有找到content-desc为[%s]的对象\r\n%s", sDesc, e), ERR_CODE);
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
//			Utils.writeLogs(String.format("没有找到text为[%s]的对象\r\n%s", sText, e), ERR_CODE);
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
//			Utils.writeLogs(String.format("没有找到class为[%s]的对象\r\n%s", sClass, e), ERR_CODE);
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
        if(!uiObj.waitForExists(m_iDelay)){
            exists = false;
            if(!sError.equalsIgnoreCase("")){
                Utils.writeLogs(sError, ERR_CODE);
                Utils.funAssert(sError, exists);
            }
        }
        return exists;
    }

    public UiObject getUiObjByResId(String resId){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().resourceId(resId));
        } catch (Exception e) {
            Utils.writeLogs(String.format("没有找到resource_id为[%s]的对象\r\n%s", resId, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByDesc(String sDesc){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().description(sDesc));
        } catch (Exception e) {
            Utils.writeLogs(String.format("没有找到content-desc为[%s]的对象\r\n%s", sDesc, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByText(String sText){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().text(sText));
        } catch (Exception e) {
            Utils.writeLogs(String.format("没有找到text为[%s]的对象\r\n%s", sText, e), ERR_CODE);
        }
        return uiObj;
    }

    public UiObject getUiObjByClass(String sClass){
        UiObject uiObj = null;
        try {
            uiObj = new UiObject(new UiSelector().className(sClass));
        } catch (Exception e) {
            Utils.writeLogs(String.format("没有找到class为[%s]的对象\r\n%s", sClass, e), ERR_CODE);
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
				Utils.writeLogs("ui click error: " + e, ERR_CODE);
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
			Utils.writeLogs("get uiobject child error: " + e, ERR_CODE);
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
			Utils.writeLogs("get uiobject child error: " + e, ERR_CODE);
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
		long iCurSec = Utils.getCurSecond();
        UiObject uiObj = null;
		while(null == uiObj) {
			if(Utils.getCurSecond() >= iCurSec + 10){
				Utils.Print("waiting time is up, obj not occur!");
                bOccur = false;
				break;
			}
            uiObj = getUiObject(iType, sContent, index);
		}
        Sleep(iWaitSec * m_iDelay);
        return bOccur;
	}
	
	public boolean waitTillGone(int iType, String sContent, int index, int iWaitSec){
        boolean bGone = true;
		long iCurSec = Utils.getCurSecond();
        UiObject uiObj = getUiObject(iType, sContent, index);
		while(uiObj.exists()) {
			if(Utils.getCurSecond() >= iCurSec + 10){
				Utils.Print("waiting time is up, obj not gone!");
                bGone = false;
				break;
			}
            uiObj = getUiObject(iType, sContent, index);
		}
        Sleep(iWaitSec * m_iDelay);
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
                Sleep(m_iDelay);
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
                Sleep(m_iDelay);
            }
            Sleep(m_iDelay);
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
                Sleep(m_iDelay);
            }
            Sleep(iWaiSec);
		}
	}

    public String executeCommand(String[] cmd) {
        Utils.Print("Exec started!");
        StringBuffer sb = new StringBuffer("");
        Process pr = null;
        try{
            pr = Runtime.getRuntime().exec(cmd);
            BufferedInputStream bi = new BufferedInputStream(pr.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bi));
            String line;
            while((line = br.readLine()) != null){
                sb.append(line + "\n");
                Utils.Print(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sRes = sb.toString();
        Utils.Print("Exec finished!");
        return sRes;
    }
}

