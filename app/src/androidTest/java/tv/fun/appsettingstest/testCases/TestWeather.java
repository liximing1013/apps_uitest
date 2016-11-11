package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import tv.fun.appsettingstest.common.TaskWeather;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;
import static tv.fun.common.Constants.WEATHER_HOME_ACTIVITY;
import static tv.fun.common.Constants.WEATHER_PKG_NAME;

/**
 * Created by zhengjin on 2016/11/11.
 * <p>
 * Test cases for Weather app.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestWeather {

    private UiDevice mDevice;
    private TaskWeather mTask;
    private long mExecTime;
    private String mMessage;
    private String mErrorStack = null;

    private final String INIT_PROVINCE = "湖北";
    private final String INIT_CITY = "武汉";
    private final String ADD_PROVINCE_1 = "山东";
    private final String ADD_CITY_1 = "青岛";
    private final String ADD_DEFAULT_PROVINCE_2 = "海南";
    private final String ADD_DEFAULT_CITY_2 = "三亚";

    @BeforeClass
    public static void classSetUp() {
        Utils.stopAndClearProcess(Constants.WEATHER_PKG_NAME);
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskWeather.getInstance();
        mExecTime = Utils.getCurSecond();

        mDevice.pressHome();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(WEATHER_PKG_NAME, WEATHER_HOME_ACTIVITY);
        SystemClock.sleep(Constants.LONG_WAIT);
    }

    @After
    public void clearUp() {
        Utils.stopProcess(WEATHER_PKG_NAME);
    }

    @Test
    public void test11DefaultLocatedCity() {
        try {
            mMessage = "Verify the default location on weather home.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertTrue(location.isEnabled());
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test12BottomTipOnWeatherHome() {
        try {
            mMessage = "Verify the tip at the bottom of weather home.";
            UiObject2 tip = mDevice.findObject(By.res("tv.fun.weather:id/tv_tip"));
            Assert.assertTrue(mMessage, tip.getText().contains("菜单键管理城市"));
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
    public void test13WeatherForecastDates() {
        try {
            List<UiObject2> dates =
                    mDevice.findObjects(By.res("tv.fun.weather:id/tv_weather_date"));
            final int size = dates.size();
            List<String> actualDates = new ArrayList<>(size);
            for (UiObject2 date : dates) {
                actualDates.add(mTask.getWeatherForecastDateFromUiText(date.getText()));
            }
            Collections.sort(actualDates, new dateComparator());

            List<String> expectedDates = mTask.getWeatherForecastDates();
            Collections.sort(expectedDates, new dateComparator());

            mMessage = "Verify the length of weather forecast dates is 5.";
            Assert.assertEquals(mMessage, actualDates.size(), expectedDates.size());

            for (int i = 0; i < size; i++) {
                mMessage = String.format(Locale.getDefault(),
                        "Verify the forecast date at position %d", i);
                Assert.assertEquals(mMessage, expectedDates.get(i), actualDates.get(i));
            }
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
    public void test14FunctionButtonsInMenu() {
        try {
            mTask.openBottomMenu();

            mMessage = "Verify update button exist.";
            UiObject2 btnUpdate = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_UPDATE)).getParent();
            Assert.assertTrue(mMessage, btnUpdate.isClickable());
            mMessage = "Verify update button is default focused.";
            Assert.assertTrue(mMessage, btnUpdate.isFocused());

            mMessage = "Verify modify default city button exist.";
            UiObject2 btnModifyDefault = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT)).getParent();
            Assert.assertTrue(mMessage, btnModifyDefault.isClickable());

            mMessage = "Verify add city button exist.";
            UiObject2 btnAddCity = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY)).getParent();
            Assert.assertTrue(mMessage, btnAddCity.isClickable());

            mMessage = "Verify the delete city button NOT exist.";
            UiObject2 textDeleteCity =
                    mDevice.findObject(By.text(mTask.WEATHER_MENU_BUTTON_TEXT_DELETE_CITY));
            Assert.assertNull(mMessage, textDeleteCity);
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
    public void test15UpdateAndRefreshWeatherData() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_UPDATE);

            mMessage = "Verify the refresh time is updated after click Update menu button.";
            UiObject2 refreshTime =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_refresh_time"));
            Assert.assertEquals(mMessage, "刚刚更新", refreshTime.getText());
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
    public void test21AddCityAndCancel() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY);

            // verification 1
            mMessage = "Verify the title of add city page.";
            UiObject2 title = mDevice.findObject(By.res("tv.fun.weather:id/setting_title"));
            Assert.assertEquals(mMessage, "添加城市", title.getText());
            mMessage = "Verify the default located province.";
            Assert.assertEquals(mMessage, INIT_PROVINCE, mTask.getSelectedLocationProvince());
            mMessage = "Verify the default located city.";
            Assert.assertEquals(mMessage, INIT_CITY, mTask.getSelectedLocationCity());

            // select 山东 青岛
            mTask.selectSpecifiedLocationProvince(ADD_PROVINCE_1, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_CITY_1, false);
            mDevice.pressBack();
            SystemClock.sleep(Constants.WAIT);

            // verification 2
            mMessage = "Verify the shown city is not changed after cancel add city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertNotNull(location);
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test22AddNewCityWeather() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY);

            // select 山东 青岛
            mTask.selectSpecifiedLocationProvince(ADD_PROVINCE_1, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_CITY_1, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            mMessage = "Verify the shown city after add new city weather.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, ADD_CITY_1, location.getText());
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
    public void test23AddDefaultCityAndCancel() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // verification 1
            mMessage = "Verify the title of modify default city page.";
            UiObject2 title = mDevice.findObject(By.res("tv.fun.weather:id/setting_title"));
            Assert.assertEquals(mMessage, "修改默认城市", title.getText());
            mMessage = "Verify the default located province.";
            Assert.assertEquals(mMessage, INIT_PROVINCE, mTask.getSelectedLocationProvince());
            mMessage = "Verify the default located city.";
            Assert.assertEquals(mMessage, INIT_CITY, mTask.getSelectedLocationCity());

            // select 海南 三亚
            mTask.selectSpecifiedLocationProvince(ADD_DEFAULT_PROVINCE_2, false);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_DEFAULT_CITY_2, false);
            mDevice.pressBack();
            SystemClock.sleep(Constants.WAIT);

            // verification 2
            mMessage = "Verify the shown city is not changed after cancel add new default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test24AddDefaultCityWeather() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // select 海南 三亚
            mTask.selectSpecifiedLocationProvince(ADD_DEFAULT_PROVINCE_2, false);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_DEFAULT_CITY_2, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            mMessage = "Verify the shown city after add new default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage,
                    String.format("%s(默认)", ADD_DEFAULT_CITY_2), location.getText());
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
    public void test25FirstCityAfterAddDefaultCity() {
        try {
            mMessage = "Verify the 1st shown city after add new default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage,
                    String.format("%s(默认)", ADD_DEFAULT_CITY_2), location.getText());
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
    public void test26ModifyDefaultCity() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // select 湖北 武汉
            mTask.selectSpecifiedLocationProvince(INIT_PROVINCE, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(INIT_CITY, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            mMessage = "Verify the shown city after change default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test27FirstCityAfterModifyDefaultCity() {
        try {
            mMessage = "Verify the 1st shown city after change default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test28ReAddDefaultCity() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // select 湖北 武汉
            mTask.selectSpecifiedLocationProvince(INIT_PROVINCE, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(INIT_CITY, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            mMessage = "Verify the shown city after re-add same default city.";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());
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
    public void test29SequenceAfterAddedCities() {
        try {
            mMessage = "Verify the 1st default city";
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, String.format("%s(默认)", INIT_CITY), location.getText());

            mMessage = "Verify the 2nd added city.";
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);
            location = mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, ADD_CITY_1, location.getText());

            mMessage = "Verify the 3rd added city.";
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);
            location = mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Assert.assertEquals(mMessage, ADD_DEFAULT_CITY_2, location.getText());
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
    public void test31DeleteMenuButton() {
        try {
            mTask.openBottomMenu();

            mMessage = "Verify delete button exist.";
            UiObject2 btnDeleteCity = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_DELETE_CITY)).getParent();
            Assert.assertTrue(mMessage, btnDeleteCity.isClickable());
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    private class dateComparator implements Comparator<String> {
        @Override
        public int compare(String arg1, String arg2) {
            int tmpInt1 =
                    Integer.parseInt(arg1.substring((arg1.indexOf("月") + 1), (arg1.length() - 1)));
            int tmpInt2 =
                    Integer.parseInt(arg2.substring((arg2.indexOf("月") + 1), (arg2.length() - 1)));
            return tmpInt1 - tmpInt2;
        }
    }


}
