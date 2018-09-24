package zhangyanran201800924.bwie.com.jineng.view;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import zhangyanran201800924.bwie.com.jineng.R;
import zhangyanran201800924.bwie.com.jineng.adapter.ShopCartAdapter;
import zhangyanran201800924.bwie.com.jineng.bean.NewBean;
import zhangyanran201800924.bwie.com.jineng.event.OnResfreshListener;
import zhangyanran201800924.bwie.com.jineng.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MyView{
    private TextView tvShopCartSubmit, tvShopCartSelect, tvShopCartTotalNum,tvShopCartTotalPrice;
    private RecyclerView rlvShopCart;
    private LinearLayout llPay;
    private List<NewBean.DataBean.ListBean> mAllOrderList = new ArrayList<>();
    private ShopCartAdapter mShopCartAdapter;
    private MainPresenter presenter;
    private boolean mSelect;
    private float mTotalPrice1;
    private ArrayList<NewBean.DataBean.ListBean> mGoPayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShopCartSelect = findViewById(R.id.tv_shopcart_addselect);
        tvShopCartTotalPrice = findViewById(R.id.tv_shopcart_totalprice);
        tvShopCartTotalNum = findViewById(R.id.tv_shopcart_totalnum);
        rlvShopCart = findViewById(R.id.rlv_shopcart);
        llPay = findViewById(R.id.ll_pay);
        tvShopCartSubmit = findViewById(R.id.tv_shopcart_submit);
        //引入P层
        presenter = new MainPresenter(this);
        presenter.getData();
        //布局管理
        rlvShopCart.setLayoutManager(new LinearLayoutManager(this));
        //加载适配器
        mShopCartAdapter = new ShopCartAdapter(this, mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);

        //实时监控全选按钮----计算总价
        mShopCartAdapter.setOnResfreshListener(new OnResfreshListener() {
            @Override
            public void onResfresh(boolean isSelect) {
                mSelect = isSelect;
                if (isSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }
                // 计算总价
                float mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                // 遍历所有商品 计算总价
                for (int i = 0; i < mAllOrderList.size(); i++)
                    if (mAllOrderList.get(i).getSelected() == 0) {
                        mTotalPrice += mAllOrderList.get(i).getPrice() * mAllOrderList.get(i).getNum();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                mTotalPrice1 = mTotalPrice;
                tvShopCartTotalPrice.setText("总价：" + mTotalPrice);
                tvShopCartTotalNum.setText("共" + mTotalNum + "件商品");
            }
        });

        tvShopCartSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 对全选状态进行去反
                mSelect = !mSelect;
                if (mSelect) {
                    // 全部选中
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (NewBean.DataBean.ListBean listBean : mAllOrderList) {
                        listBean.setSelected(0);
                        listBean.setShopSelect(true);
                    }
                } else {
                    // 全部取消
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (NewBean.DataBean.ListBean listBean : mAllOrderList) {
                        listBean.setSelected(1);
                        listBean.setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();
            }
        });

    }

    //判断是否是第一个条目
    public static void isSelectFirst(List<NewBean.DataBean.ListBean> list) {
        // 1. 判断是否有商品 有商品 根据商品是否是第一个显示商铺
        if (list.size() > 0) {
            //头个商品一定属于它所在商铺的第一个位置，isFirst标记为1.
            list.get(0).setFirst(true);
            for (int i = 1; i < list.size(); i++) {
                //每个商品跟它前一个商品比较，如果Shopid相同isFirst则标记为2，
                //如果Shopid不同，isFirst标记为1.
                if (list.get(i).getSellerid() == list.get(i - 1).getSellerid()) {
                    list.get(i).setFirst(false);
                } else {
                    list.get(i).setFirst(true);
                }
            }
        }
    }

    @Override
    public void onSuccess(final NewBean newBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (NewBean.DataBean dataBean : newBean.getData()) {
                    for (NewBean.DataBean.ListBean listBean : dataBean.getList()) {
                        Log.e("tag", "onCreate: "+listBean.getTitle() );

                        listBean.setShopName(dataBean.getSellerName());
                        mAllOrderList.add(listBean);
                    }
                }
                isSelectFirst(mAllOrderList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mShopCartAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onError(int code) {

    }
}
