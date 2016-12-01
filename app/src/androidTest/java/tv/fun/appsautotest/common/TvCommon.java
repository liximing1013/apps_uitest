package tv.fun.appsautotest.common;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import tv.fun.common.Common;
import tv.fun.common.Utils;

public class TvCommon extends Common{
    private int m_iDelay = 1000;
    private UiDevice device = getDevice();
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
        UiObject uiObj;
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
                Utils.funAssert(sError, false);
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
			if(Utils.getCurSecond() >= iCurSec + iWaitSec){
				Utils.Print("waiting time is up, obj not occur!");
                bOccur = false;
				break;
			}
            uiObj = getUiObject(iType, sContent, index);
		}
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

    // 根据字符串来进行对应的遥控操作，eg：Navigation("h901234995");
    // 上面的例子表示：按home，延时1秒后，按上、右、下、左方向键，按确定键，延时2秒后按返回键
    // 0 1 2 3 - up right down left
    // h 4 5 - home enter back
    // 9 - 表示延时1秒
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

    // 操作完成后，Sleep iWaitSec秒
    public void Navigation(String sPath, int iWaiSec){
        Navigation(sPath);
        Sleep(iWaiSec);
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

    public void longPressUp(int iSecond){
        longPress(0, iSecond);
    }

    public void longPressRight(int iSecond){
        longPress(1, iSecond);
    }

    public void longPressDown(int iSecond){
        longPress(2, iSecond);
    }

    public void longPressLeft(int iSecond){
        longPress(3, iSecond);
    }

    // 需要将/dev/input/event3的权限设置为666才能运行长按操作
    public void longPress(int iKey, int iSecond){
        String [] asCmd = new String[7];

        if(0 == iKey){ // Up
            asCmd[0] = "sendevent /dev/input/event3 4 4 458834";
            asCmd[1] = "sendevent /dev/input/event3 1 103 1";
            asCmd[2] = "sendevent /dev/input/event3 0 0 0";
            asCmd[3] = String.format("/system/bin/sleep %s", iSecond);
            asCmd[4] = asCmd[0];
            asCmd[5] = "sendevent /dev/input/event3 1 103 0";
            asCmd[6] = asCmd[2];
        }
        else if(1 == iKey){ // Right
            asCmd[0] = "sendevent /dev/input/event3 4 4 458831";
            asCmd[1] = "sendevent /dev/input/event3 1 106 1";
            asCmd[2] = "sendevent /dev/input/event3 0 0 0";
            asCmd[3] = String.format("/system/bin/sleep %s", iSecond);
            asCmd[4] = asCmd[0];
            asCmd[5] = "sendevent /dev/input/event3 1 106 0";
            asCmd[6] = asCmd[2];
        }
        else if(2 == iKey){ // Down
            asCmd[0] = "sendevent /dev/input/event3 4 4 458833";
            asCmd[1] = "sendevent /dev/input/event3 1 108 1";
            asCmd[2] = "sendevent /dev/input/event3 0 0 0";
            asCmd[3] = String.format("/system/bin/sleep %s", iSecond);
            asCmd[4] = asCmd[0];
            asCmd[5] = "sendevent /dev/input/event3 1 108 0";
            asCmd[6] = asCmd[2];
        }
        else if(3 == iKey){ // Left
            asCmd[0] = "sendevent /dev/input/event3 4 4 458832";
            asCmd[1] = "sendevent /dev/input/event3 1 105 1";
            asCmd[2] = "sendevent /dev/input/event3 0 0 0";
            asCmd[3] = String.format("/system/bin/sleep %s", iSecond);
            asCmd[4] = asCmd[0];
            asCmd[5] = "sendevent /dev/input/event3 1 105 0";
            asCmd[6] = asCmd[2];
        }
        Utils.execCommand(asCmd, false, true);
    }

    public String checkExpectResult(UiObject uiObj, String sExpect){
        String sResult;
        String sActual = getUiObjText(uiObj);
        if(!sActual.equalsIgnoreCase(sExpect)) {
            sResult = String.format(" 实际值[%s]与期望值[%s]不符！", sActual, sExpect);
            return sResult;
        }
        return "OK";
    }

    public void setRefreshFailWatcher(){
        device.registerWatcher("BufferRefreshFailWatcher", new BufferRefreshFailWatcher());
    }

    private class BufferRefreshFailWatcher implements UiWatcher {
        @Override
        public boolean checkForCondition() {
            Utils.Print("Invoke BufferRefreshFailedWatcher.checkForCondition().");

            UiObject2 errorText = device.findObject(By.textContains("缓冲失败"));
            if (errorText != null) {
                // if buffer refresh error occur, stop testing process
                Utils.Print("Found error(Buffer Refresh Failed), force exit testing process.");
                int existCode = 0;
                System.exit(existCode);
                return true;
            }
            return false;
        }
    }
}

