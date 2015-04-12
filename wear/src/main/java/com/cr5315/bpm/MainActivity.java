package com.cr5315.bpm;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;

    private ArrayList<Long> mTaps = new ArrayList<>();
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                mRelativeLayout = (RelativeLayout) watchViewStub.findViewById(R.id.layout);
                mTextView = (TextView) watchViewStub.findViewById(R.id.text);

                mRelativeLayout.setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mTaps.add(System.currentTimeMillis());

        double avg = average(mTaps);
        if (avg != 0) {
            double avgSeconds = avg / 1000;
            double bpm = 60 / avgSeconds;
            mTextView.setText(mDecimalFormat.format(bpm));
        }

    }

    private double average(ArrayList<Long> arrayList) {
        if (arrayList.size() == 0) return 0;

        double sum = 0;

        if (arrayList.size() > 1) {
            for (int i = 1; i < arrayList.size(); i++) {
                long now = arrayList.get(i);
                long then = arrayList.get(i - 1);

                sum += Math.abs(then - now);
            }
        }

        return sum / arrayList.size();
    }
}