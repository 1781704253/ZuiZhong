package zhangyanran201800924.bwie.com.jineng.okhttputils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 匹诺曹 on 2018/9/24.
 */

public class OkHttp3Utils {
    private static OkHttpClient client = null;
    public OkHttp3Utils(){
    }
    public static OkHttpClient getInstance(){
        if (client==null){
            synchronized (OkHttp3Utils.class){
                if (client==null){
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }
    public static void doget(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
