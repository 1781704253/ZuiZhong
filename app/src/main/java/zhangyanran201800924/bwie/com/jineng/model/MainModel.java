package zhangyanran201800924.bwie.com.jineng.model;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zhangyanran201800924.bwie.com.jineng.bean.NewBean;
import zhangyanran201800924.bwie.com.jineng.okhttputils.OkHttp3Utils;

/**
 * Created by 匹诺曹 on 2018/9/24.
 */

public class MainModel {
    private String api = "http://www.zhaoapi.cn/product/getCarts?uid=71";

    public void getData(final MyModel myModel){
        OkHttp3Utils.doget(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myModel.onError(1);
                Log.i("aaa","-----------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("aaa",string+"-----------------");
                NewBean json = new Gson().fromJson(string, NewBean.class);
                myModel.onSuccess(json);
            }
        });
    }
}
