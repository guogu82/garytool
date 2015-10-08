package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.info.Student;
import com.gary.garytool.util.RandomUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;


/**
 * 1:PullToRefresh https://github.com/chrisbanes/Android-PullToRefresh
 * 2:Volley 用于网络请求.缺点-它不适合数据的上传和下载。大文件上传。 https://github.com/Tim9Liu9/volley_demo
 * 3:XUtils 用于数据库也支持大文件上传 https://github.com/wyouflf/xUtils
 */
public class BaseFrameworkActivity extends Activity {
   TextView tv;
   Button bt;
   DbUtils db;
    Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initView();
    }

    private void initView() {
        DbUtils.DaoConfig config=new DbUtils.DaoConfig(this);
        config.setDbName("gary-util-db");
        config.setDbVersion(1);
        db=DbUtils.create(config);
        try {
            db.createTableIfNotExist(Student.class);
        } catch (DbException e) {

        }


        tv= (TextView) findViewById(R.id.tv);
        tv.setText("gary is going");

        bt= (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student stu = new Student();
                int base=RandomUtil.getRandomInt();
                stu.setAge(base);
                stu.setName("gary" + base);
                try {
                    db.save(stu);
                } catch (DbException e) {

                }


            }
        });

        bt_search= (Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                  List<Student> list= db.findAll(Selector.from(Student.class));
                    String result="";
                    for(Student stu:list)
                    {
                        result=result+stu.getName()+","+stu.getAge()+";";
                    }
                    tv.setText(result);
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        });
    }



}
