package zhangyanran201800924.bwie.com.jineng.view;

import zhangyanran201800924.bwie.com.jineng.bean.NewBean;

/**
 * Created by 匹诺曹 on 2018/9/24.
 */

public interface MyView {
    void onSuccess(NewBean newBean);
    void onError(int code);
}
