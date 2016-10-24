package tv.fun.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by xuzx on 2016/10/14.
 * 通用函数，手机或者电视上都可以使用的函数就写在这里
 */
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
        // device.pressEnter();
        device.pressDPadCenter();
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

    private static final String COMMAND_SU = "su";
    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_EXIT = "exit\n";
    private static final String COMMAND_LINE_END = "\n";

    public static CommandResult execCommand(
            String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    public static CommandResult execCommand(
            String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (isEmpty(command)) {
                    continue;
                }
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String tmpStr;
                while ((tmpStr = successResult.readLine()) != null) {
                    successMsg.append(tmpStr);
                }
                while ((tmpStr = errorResult.readLine()) != null) {
                    errorMsg.append(tmpStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }

        return new CommandResult(result, (successMsg != null ? successMsg.toString() : null),
                (errorMsg != null ? errorMsg.toString() : null));
    }

    public static class CommandResult {
        public int mResult;
        public String mSuccessMsg;
        public String mErrorMsg;

        CommandResult(int results, String successMsg, String errorMsg) {
            mResult = results;
            mSuccessMsg = successMsg;
            mErrorMsg = errorMsg;
        }
    }

    public static int stopProcess(String packageName) {
        String cmdStopProcess = String.format("am force-stop %s", packageName);

        CommandResult result = execCommand(cmdStopProcess, false, false);
        return result.mResult;
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static void waitForLoadingComplete(UiDevice device) {
        UiObject2 loading = device.findObject(By.res(Infos.S_PROCESS_BAR_ID));

        int tryTimes = 3;
        for (int i = 0, waitTimes = 20; i < waitTimes; i++) {
            SystemClock.sleep(1000);
            if (tryTimes == 0) {
                return;
            }
            if (loading == null) {
                tryTimes--;
            }
        }
    }

}