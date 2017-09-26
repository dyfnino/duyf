package tcp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import msg.TSPGpsBasicInfoVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-7
 * 说明:
 *测试netty关于http请求
 */
public class Test {

    public static void main(String[] args) {
        try {
            String s = "{\"on_off\":\"on\",\"vincode\":\"hhhhhhh\"}";
            //将JsonObject数据转换为Json
           // JSONObject object = JSON.parseObject(s);
            //http://127.0.0.1:6789/netty?name=%7B%22name%22:%22duyf%22%7D
            getURLByPost("http://192.168.0.91:6789/netty",s);
            System.out.println(getURLByPost("http://192.168.0.91:6789/netty",s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /*
   * *
    * post方式 http请求服务
    * @param urlStr
    * @param params   name=yxd&age=25
            * @return
            * @throws Exception
    */
    public static String getURLByPost(String urlStr,String params)throws Exception{
        URL url=new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.flush();
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try{
            in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {
            throw ex;
        } finally{
            try{
                conn.disconnect();
                if(in!=null){
                    in.close();
                }
                if(printWriter!=null){
                    printWriter.close();
                }
            }catch(IOException ex) {
                throw ex;
            }
        }
        System.out.println("客户端返回"+sb.toString());
        return sb.toString();
    }
}
