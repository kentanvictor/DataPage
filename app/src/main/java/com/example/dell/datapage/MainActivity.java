package com.example.dell.datapage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private int number = 20;//每次获取多少数据
    private int maxPage = 5;//总共有多少页
    private boolean loadFinish = true;//默认是否加载完成下一页
    View footer;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        footer = getLayoutInflater().inflate(R.layout.footer, null);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnScrollChangeListener(new ScrollListener());
        data.addAll(DataService.getData(0, 20));
        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.textView, data);
        listView.addFooterView(footer);//添加页脚
        listView.setAdapter(adapter);
        listView.removeFooterView(footer);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private final class ScrollListener implements View.OnScrollChangeListener {

        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, final int totalItemCount) {
            Log.i("MainActivity", "OnScroll(firstVisibleItem=" + firstVisibleItem + ",visibleItemCount=" +
                    visibleItemCount + ",totalItemCount=" + totalItemCount + ")");
            int lastItemid = listView.getLastVisiblePosition();//获取当前屏幕最后的Item的ID
            if ((lastItemid + 1) == totalItemCount)//达到数据的最后一条记录
            {
                if (totalItemCount > 0) {
                    int currentPage = totalItemCount % number == 0 ? totalItemCount / number : totalItemCount / number + 1;
                    int nextPage = currentPage + 1;//获取下一页
                    if (nextPage <= maxPage && loadFinish) {
                        loadFinish = false;
                        listView.addFooterView(footer);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                List<String> result = DataService.getData(totalItemCount, number);
                                handler.sendMessage(handler.obtainMessage(100, result));
                            }
                        }).start();
                    }
                }
            }
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                data.addAll((List<String>) msg.obj);
                adapter.notifyDataSetChanged();//告诉ListView数据已经发生改变,要求ListVIew更新界面显示
                if (listView.getFooterViewsCount() > 0) {
                    listView.removeFooterView(footer);
                }
                loadFinish = true;
            }
        };
    }
}

