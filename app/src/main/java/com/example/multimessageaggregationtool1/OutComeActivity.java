package com.example.multimessageaggregationtool1;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class OutComeActivity extends Activity {


    RecyclerView messageTimeConsumingRecycler;
    RecyclerView.Adapter adapter;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_recycler_view);

    messageTimeConsumingRecycler = findViewById(R.id.my_recycler);
    messageTimeConsumingRecycler();
    initView();
}

    private void messageTimeConsumingRecycler() {


        messageTimeConsumingRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adapter = new TimeConsumingMonitorRecyclerAdapter(messageTimeConsumingRecycler);
        messageTimeConsumingRecycler.setAdapter(adapter);

    }
    private void initView() {
    //将ArrayList中的所有数据传输进来？？？？
        RecyclerView recyclerView = findViewById(R.id.my_recycler);
        recyclerView.setAdapter(adapter);

    }

}
