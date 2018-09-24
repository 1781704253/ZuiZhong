package zhangyanran201800924.bwie.com.jineng.presenter;

import zhangyanran201800924.bwie.com.jineng.bean.NewBean;
import zhangyanran201800924.bwie.com.jineng.model.MainModel;
import zhangyanran201800924.bwie.com.jineng.model.MyModel;
import zhangyanran201800924.bwie.com.jineng.view.MyView;

/**
 * Created by 匹诺曹 on 2018/9/24.
 */

public class MainPresenter implements MyPresenter{

    private MyView myView;
    private MainModel mainModel;

    public MainPresenter(MyView myView) {
        this.myView = myView;
        mainModel = new MainModel();
    }

    public void getData(){
        mainModel.getData(new MyModel() {
            @Override
            public void onSuccess(NewBean newBean) {
                myView.onSuccess(newBean);
            }

            @Override
            public void onError(int code) {
                myView.onError(1);
            }
        });
    }

    @Override
    public void desc() {

    }
}
