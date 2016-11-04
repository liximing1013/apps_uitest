package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tv.fun.appsettingstest.common.TaskFileManager;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.FILEMANAGER_HOME_ACTIVITY;
import static tv.fun.common.Constants.FILEMANAGER_PKG_NAME;
import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;

/**
 * Created by zhengjin on 2016/11/4.
 * <p>
 * TestFileManager
 */

public final class TestFileManager {

    private UiDevice mDevice;
    private TaskFileManager mTask;
    private long mExecTime;
    private String mErrorStack = null;

    private static final String TEXT_REMOVE_BUTTON = "删除";
    private static final String TEXT_HIDDEN_BUTTON = "隐藏";
    private static final String TEXT_SHOWALL_BUTTON = "显示全部";

    private static final String TEST_ROOT_DIR_NAME = "TestFile";
    private static final String TEST_ROOT_DIR_PATH;
    private static final String TEST_DIR_NAME = "TestDirectory";
    private static final String TEST_DIR_PATH;
    private static final String TEST1_FILE_NAME = "TestFile1.log";
    private static final String TEST1_FILE_PATH;
    private static final String TEST2_FILE_NAME = "TestFile2.log";
    private static final String TEST2_FILE_PATH;

    static {
        TEST_ROOT_DIR_PATH =
                String.format("%s/%s", Utils.getExternalStoragePath(), TEST_ROOT_DIR_NAME);
        TEST_DIR_PATH = String.format("%s/%s", TEST_ROOT_DIR_PATH, TEST_DIR_NAME);
        TEST1_FILE_PATH = String.format("%s/%s", TEST_ROOT_DIR_PATH, TEST1_FILE_NAME);
        TEST2_FILE_PATH = String.format("%s/%s", TEST_DIR_PATH, TEST2_FILE_NAME);
    }

    private static void prepareData() {
        String message = "Prepare files for file manager test.";
        String cmdCreateHiddenDir = String.format("mkdir -p %s", TEST_DIR_PATH);
        String cmdCreateTxtFile1 = String.format("touch %s", TEST1_FILE_PATH);
        String cmdCreateTxtFile2 = String.format("touch %s", TEST2_FILE_PATH);
        String commands[] = {cmdCreateHiddenDir, cmdCreateTxtFile1, cmdCreateTxtFile2};

        Utils.CommandResult result = Utils.execCommand(commands, false, false);
        Assert.assertTrue(message, (result.mResult == 0));
    }

    private static void removeData() {
        String message = "Clear files for file manager test.";
        String removeAllFiles = String.format("rm -rf %s", TEST_ROOT_DIR_PATH);

        Utils.CommandResult result = Utils.execCommand(removeAllFiles, false, false);
        Assert.assertTrue(message, (result.mResult == 0));
    }

    @BeforeClass
    public static void classSetup() {
        prepareData();
    }

    @AfterClass
    public static void classClearUp() {
        removeData();
        Utils.stopAndClearProcess(FILEMANAGER_PKG_NAME);
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskFileManager.getInstance();
        mExecTime = Utils.getCurSecond();

        Utils.stopAndClearProcess(FILEMANAGER_PKG_NAME);
        mDevice.pressHome();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(FILEMANAGER_PKG_NAME, FILEMANAGER_HOME_ACTIVITY);
        SystemClock.sleep(Constants.WAIT);
    }

    @Test
    public void FileM_Category_11_01_testOpenAllFilesCardFromSdcardTab() {
        try {
            mTask.openLocalFilesCard();

            UiObject2 mainTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_main"));
            Utils.writeCaseResult("Verify the text of main title from sdcard local files.",
                    "本地存储".equals(mainTitle.getText()), mExecTime);

            UiObject2 subTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_sub"));
            Utils.writeCaseResult("Verify the text of sub title from sdcard local files.",
                    "全部文件".equals(subTitle.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void FileM_Category_12_01_testNavigateToSpecifiedPath() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

            UiObject2 subTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_sub"));
            Utils.writeCaseResult("Verify navigate to the specified path.",
                    TEST_ROOT_DIR_NAME.equals(subTitle.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void FileM_Category_18_01_testOpenUnknownTypeFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateAndOpenSpecifiedFile(TEST1_FILE_PATH);
            SystemClock.sleep(Constants.WAIT);

            UiObject2 subTitle = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult(
                    "Verify open unknown type file.", subTitle.isEnabled(), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void FileM_Menu_01_01_testMenuHideBtnExistForDir() {
        try {
            mTask.openLocalFilesCard();
            UiObject2 menuTips =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_menu_tips"));
            Utils.writeCaseResult("Verify the menu tips is displayed.",
                    menuTips.getText().contains("查看更多操作"), mExecTime);

            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.showMenuAndRequestFocus();

            UiObject2 menuHideBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_hide_id"));
            Utils.writeCaseResult("Verify the hide button is focused in the bottom menu.",
                    menuHideBtnContainer.isFocused(), mExecTime);

            UiObject2 menuHideBtn =
                    menuHideBtnContainer.findObject(By.res("android:id/tv_fun_menu_text"));
            Utils.writeCaseResult("Verify the text of hide button in the bottom menu.",
                    TEXT_HIDDEN_BUTTON.equals(menuHideBtn.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }


}
