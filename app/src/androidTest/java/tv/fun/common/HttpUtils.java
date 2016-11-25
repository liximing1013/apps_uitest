package tv.fun.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class HttpUtils {
    public static String m_sJoUrl = "http://jo.funtv.bestv.com.cn";
    public static String m_sFilmMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=109&nav_id=8";
    public static String m_sPlayMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=110&nav_id=8";
    public static String m_sChildMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=119&nav_id=8";
    public static String m_sVarietyMainPageUrl = m_sJoUrl + "/config/mretrievetabs/v2?" +
            "block_id=112&nav_id=8";
	/**
     * 向指定URL发送GET方法的请求
     */
    public static String sendGet(String sRequestUrl) {
        String sResponseData = "";
        BufferedReader br = null;

        try {
            URL uUrl = new URL(sRequestUrl);
            // 打开和URL之间的连接
            URLConnection connection = uUrl.openConnection();
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);//设置连接超时5s
            connection.setReadTimeout(5000);//设置读取超时5s
            // 建立实际的连接
            connection.connect();

            //获取响应头中的ResponseCode, ResponseMsg
//            Map<String, List<String>> mapHttpHeaders = connection.getHeaderFields();
//            List<String> lsStatus = mapHttpHeaders.get(null);
//            String sStatus = lsStatus.get(0);
//            String sResponseCode = sStatus.split(" ")[1];
//            String sResponseMsg = sStatus.split(" ")[2];

            // 定义 BufferedReader输入流来读取URL的响应
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String sLine;
            while ((sLine = br.readLine()) != null) {
            	sResponseData += sLine;
            }
        } catch (Exception e) {
        	sResponseData = "fail";//e.getMessage();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sResponseData;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(5000);//设置连接超时5s
            conn.setReadTimeout(5000);//设置读取超时5s
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
