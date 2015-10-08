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


public class XUtilsActivity extends Activity {
    TextView tv;
    Button bt_add;
    Button bt_delete;
    DbUtils db;
    Button bt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);
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

        bt_add= (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student stu = new Student();
                int base= RandomUtil.getRandomInt();
                stu.setAge(base);
                stu.setName("gary" + base);
                try {
                    db.save(stu);
                } catch (DbException e) {

                }


            }
        });

//        2）自定义查询（常用到的链接查询可以这样写）
//        SqlInfo sqlInfo = new SqlInfo();
//        sqlInfo.setSql("select * from tbl_Student a,tbl_class b where a.stu_class_id = b.id and a.id > ?");
//        sqlInfo.addBindArg(2);
//        List<DbModel> StudentByInfo = db.findDbModelAll(sqlInfo);
        bt_search= (Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> list = db.findAll(Selector.from(Student.class));
                    String result = "";
                    for (Student stu : list) {
                        result = result + stu.getName() + "," + stu.getAge() + ";";
                    }
                    tv.setText(result);
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        });

//        3）删除，包含三种方法：删除list集合，根据Id删除，删除一个对象
//        List<Grade> stus = db.findAll(Selector.from(Grade.class).where("class_name", "=", ""));
//        db.deleteAll(stus);
//        db.deleteById(Grade.class, 2);
//        db.delete(stus.get(3));
        bt_delete= (Button) findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Student> list = db.findAll(Selector.from(Student.class));
                    if(list.size()>0)
                    db.delete(list.get(0));
                } catch (DbException e) {

                }
            }
        });
    }

}
