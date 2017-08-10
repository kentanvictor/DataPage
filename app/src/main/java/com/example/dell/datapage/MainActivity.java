package com.example.dell.datapage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnScrollChangeListener(new ScrollListener());
        data.addAll(DataService.getData(0, 20));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.textView, data);
        listView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private final class ScrollListener implements View.OnScrollChangeListener {

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.i("MainActivity", "OnScroll(firstVisibleItem=" + firstVisibleItem + ",visibleItemCount=" +
                    visibleItemCount + ",totalItemCount=" + totalItemCount + ")");
        }

        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        }
    }
}
