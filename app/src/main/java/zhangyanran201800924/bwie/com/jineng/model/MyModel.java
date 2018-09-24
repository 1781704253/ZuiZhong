package zhangyanran201800924.bwie.com.jineng.model;

import zhangyanran201800924.bwie.com.jineng.bean.NewBean;

/**
 * Created by 匹诺曹 on 2018/9/24.
 */

public interface MyModel {
    void onSuccess(NewBean newBean);
    void onError(int code);
}
