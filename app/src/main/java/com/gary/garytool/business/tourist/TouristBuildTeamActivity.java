package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/3/31.
 */
public class TouristBuildTeamActivity extends Activity{
    private TextView mTvTopBarTitle;
    private TextView mTvBuildTeam;
    private TextView mTvJoinTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_team_build_activity);

        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText("我的团队");

        mTvBuildTeam= (TextView) findViewById(R.id.tv_build_team);
        mTvBuildTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvJoinTeam= (TextView) findViewById(R.id.tv_join_team);
        mTvJoinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onBarBack(View view) {
        finish();
    }
}
