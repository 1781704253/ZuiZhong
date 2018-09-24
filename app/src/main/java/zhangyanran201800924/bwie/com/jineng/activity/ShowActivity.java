package zhangyanran201800924.bwie.com.jineng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyales.tagcloud.library.TagBaseAdapter;
import com.fyales.tagcloud.library.TagCloudLayout;

import java.util.ArrayList;
import java.util.List;

import zhangyanran201800924.bwie.com.jineng.R;
import zhangyanran201800924.bwie.com.jineng.view.MainActivity;
import zhangyanran201800924.bwie.com.jineng.weight.CustomView;

public class ShowActivity extends AppCompatActivity {

    private Button but;
    private CustomView afl_cotent;
    private LayoutInflater layoutInflater;
    private TextView tv_attr_tag;
    private List<String> list = new ArrayList<>();
    private TagCloudLayout mContainer;
    private ArrayList<String> mList;
    private TagBaseAdapter mAdapter;
    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        //initView();
        edt = findViewById(R.id.edt);

        mContainer = findViewById(R.id.container);
        mList = new ArrayList<>();
        mAdapter = new TagBaseAdapter(this, mList);
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edt.getText().toString();
                mList.add(s);
                mAdapter.notifyDataSetChanged();
            }
        });
        mContainer.setAdapter(mAdapter);
        mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //初始化控件
        edt = findViewById(R.id.edt);
        but = findViewById(R.id.add_btn);
        //afl_cotent = findViewById(R.id.afl_cotent);
        //加载布局
        layoutInflater = LayoutInflater.from(this);
        //点击事件
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取edit内容
                String s = edt.getText().toString();
                //加载子布局
                View item = layoutInflater.inflate(R.layout.sub_item, null);
                tv_attr_tag = item.findViewById(R.id.tv_attr_tag);
                list.add(s);
                for (int i = 0; i < list.size(); i++) {
                    tv_attr_tag.setText(list.get(i));
                    tv_attr_tag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                afl_cotent.addView(item);
            }
        });
    }
}