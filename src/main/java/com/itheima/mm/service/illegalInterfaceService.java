package com.itheima.mm.service;



import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class illegalInterfaceService {
    public static void main(String[] args) throws Exception {
        illegalInfo();

    }
    public static void illegalInfo() throws Exception{

        String url="http://localhost:18081/album/search/10/10";
        URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        //conn.setRequestMethod("POST");
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        // conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        // conn.setAllowUserInteraction(false);
        //  PrintStream ps = new PrintStream(conn.getOutputStream());
        // ps.close();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line,resultStr="";
        while(null != (line=bReader.readLine()))
        {
            resultStr +=line;
        }

        System.out.println("违法数据---"+resultStr);
        bReader.close();
        //解析嵌套JSON
        JSONObject jsonobject = JSONObject.fromObject(new String(resultStr.toString()));
        jsonobject.get("illegalinfo");
    }
}
