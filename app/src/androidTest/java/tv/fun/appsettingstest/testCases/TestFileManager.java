package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

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

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void FileM_test01_Category_11_01_OpenAllFilesCardFromSdcardTab() {
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
    public void FileM_test02_Category_12_01_NavigateToSpecifiedPath() {
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
    public void FileM_test03_Category_18_01_OpenUnknownTypeFile() {
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
    public void FileM_test04_Category_12_01_MessageWhenEmptyForAppCard() {
        try {
            mTask.openCategoryAppCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in APP card.",
                    "未发现可安装的应用".equals(tips.getText()), mExecTime);
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
    public void FileM_test05_Category_12_02_MessageWhenEmptyForAppCard() {
        try {
            mTask.openCategoryAppCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in APP card.",
                    "未发现可安装的应用".equals(tips.getText()), mExecTime);
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
    public void FileM_test06_Category_12_03_MessageWhenEmptyForMusicCard() {
        try {
            mTask.openCategoryMusicCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in music card.",
                    "未发现可播放的音乐".equals(tips.getText()), mExecTime);
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
    public void FileM_test07_Category_12_04_MessageWhenEmptyForPictureCard() {
        try {
            mTask.openCategoryPictureCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in picture card.",
                    "未发现可显示的图片".equals(tips.getText()), mExecTime);
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
    public void FileM_test11_Menu_04_01_01_MenuHideBtnExistForDir() {
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

    @Test
    public void FileM_test12_Menu_01_02_MenuRemoveAndHideBtnExistForFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.showMenuAndRequestFocus();

            UiObject2 menuRemoveBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_del_id"));
            Utils.writeCaseResult("Verify the remove button is focused in the bottom menu.",
                    menuRemoveBtnContainer.isFocused(), mExecTime);

            UiObject2 menuRemoveBtn =
                    menuRemoveBtnContainer.findObject(By.res("android:id/tv_fun_menu_text"));
            Utils.writeCaseResult("Verify the text of remove button in the bottom menu.",
                    TEXT_REMOVE_BUTTON.equals(menuRemoveBtn.getText()), mExecTime);

            UiObject2 menuHideBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_hide_id"));
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

    @Test
    public void FileM_test13_Hidden_04_01_HideAndShowDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 fileHidden = mDevice.findObject(By.text(TEST_DIR_NAME));
            Utils.writeCaseResult("Verify the directory is hidden.", fileHidden == null, mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 fileShow = mDevice.findObject(By.text(TEST_DIR_NAME));
            Utils.writeCaseResult("Verify the directory is show.", fileShow != null, mExecTime);
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
    public void FileM_test14_Hidden_01_01_HideAndShowFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 fileHidden = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify the file is hidden.", fileHidden == null, mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 fileShow = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify the file is show.", fileShow != null, mExecTime);
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
    public void FileM_test15_Remove_01_01_RemoveFileAndCancel() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 cancelBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_cancel"));
            Utils.writeCaseResult("Verify the Cancel button of confirm dialog.",
                    cancelBtn != null, mExecTime);

            cancelBtn.click();
            SystemClock.sleep(Constants.WAIT);
            UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify click cancel and do not remove a file.",
                    fileDeleted != null, mExecTime);
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
    public void FileM_test16_Remove_01_02_RemoveFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm"));
            Utils.writeCaseResult("Verify the Yes button of confirm dialog.",
                    confirmBtn != null, mExecTime);

            confirmBtn.click();
            SystemClock.sleep(Constants.WAIT);
            UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify click yes and remove a file.",
                    fileDeleted == null, mExecTime);
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
    public void FileM_test17_Hidden_02_01_HideAndShowOnlyFileOfDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_DIR_PATH);
            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips of empty directory from all files card.",
                    "未发现可显示的文件".equals(tips.getText()), mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 hiddenFile = mDevice.findObject(By.text(TEST2_FILE_NAME));
            Utils.writeCaseResult("Verify the file is show after click Show All.",
                    hiddenFile.isEnabled(), mExecTime);
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
    public void FileM_test18_Remove_03_01_RemoveOnlyFileOfDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_DIR_PATH);
            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm"));
            confirmBtn.click();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips of empty directory from all files card.",
                    "未发现可显示的文件".equals(tips.getText()), mExecTime);
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
