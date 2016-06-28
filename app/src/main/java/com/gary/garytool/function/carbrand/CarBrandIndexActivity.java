package com.gary.garytool.function.carbrand;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gary.garytool.lib.fancyindexer.adapter.CarBrandAdapter;
import com.gary.garytool.lib.fancyindexer.domain.CarBrand;
import com.gary.garytool.lib.fancyindexer.domain.Cheeses;
import com.gary.garytool.lib.fancyindexer.domain.GoodMan;
import com.gary.garytool.lib.fancyindexer.ui.FancyIndexer;
import com.gary.garytool.R;

/**
 * Created by Gary on 2016/6/15/015.
 */
public class CarBrandIndexActivity extends Activity {
    private TextView tv_index_center;
    private final static int BRAND_COLUMN_DEFAULT=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_gridview_index);

    tv_index_center = (TextView) findViewById(R.id.tv_index_center);
    final ListView lv_content = (ListView) findViewById(R.id.lv_content);


    final ArrayList<GoodMan> persons = new ArrayList<GoodMan>();
    // 填充数据, 并排序
    fillAndSortData(persons);


    //构造业务集合数据
    final ArrayList<CarBrand> carBrands = fillData(persons);

    lv_content.setAdapter(new CarBrandAdapter(carBrands, this));

    FancyIndexer mFancyIndexer = (FancyIndexer) findViewById(R.id.bar);
    mFancyIndexer.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {

        @Override
        public void onTouchLetterChanged(String letter) {
            System.out.println("letter: " + letter);

			//showLetter(letter);

            // 从集合中查找第一个拼音首字母为letter的索引, 进行跳转
            for (int i = 0; i < carBrands.size(); i++) {
                CarBrand carBrand = carBrands.get(i);
                String s = carBrand.getPinyin().charAt(0) + "";
                if(TextUtils.equals(s, letter)){
                    // 匹配成功, 中断循环, 跳转到i位置
                    lv_content.setSelection(i);
                    break;
                }
            }
        }
    });
    }

    private ArrayList<CarBrand> fillData(ArrayList<GoodMan> persons) {
        ArrayList<CarBrand> carBrands=new ArrayList<>();
        CarBrand carBrand;
        List<GoodMan> goodMen=null;
        String preLetter="";
        int j=0;
        for (int i = 0; i < persons.size(); i++) {
            GoodMan goodMan = persons.get(i);
            if(i==0)
            {
                preLetter=goodMan.getPinyin().charAt(0) + "";
                goodMen=new ArrayList<>();
                goodMen.add(goodMan);
                carBrand=new CarBrand(goodMan.getName(),goodMen);
                carBrands.add(carBrand);
                j++;
            }
            else
            {
                String currentLetter = persons.get(i).getPinyin().charAt(0) + "";
                if(TextUtils.equals(currentLetter, preLetter)){
                    if(goodMen.size()>=BRAND_COLUMN_DEFAULT)
                    {
                        goodMen=new ArrayList<>();
                        carBrand=new CarBrand(goodMan.getName(),goodMen);
                        carBrands.add(carBrand);
                    }

                    goodMen.add(goodMan);

                    j++;
                }
                else
                {
                    goodMen=new ArrayList<>();
                    goodMen.add(goodMan);
                    carBrand=new CarBrand(goodMan.getName(),goodMen);
                    carBrands.add(carBrand);
                    preLetter=goodMan.getPinyin().charAt(0) + "";
                    j=0;
                }
            }

        }

        return carBrands;
    }


    private Handler mHandler = new Handler();

    /**
     * 显示字母提示
     * @param letter
     */
    protected void showLetter(String letter) {
        tv_index_center.setVisibility(View.VISIBLE);
        tv_index_center.setText(letter);

        // 取消掉刚刚所有的演示操作
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 隐藏
                tv_index_center.setVisibility(View.GONE);
            }
        }, 1000);

    }

    /**
     * 填充,排序
     * @param persons
     */
    private void fillAndSortData(ArrayList<GoodMan> persons) {
        String[] datas = null;
        boolean china = getResources().getConfiguration().locale.getCountry().equals("CN");
        datas = china ? Cheeses.NAMES : Cheeses.sCheeseStrings;
        for (int i = 0; i < datas.length; i++) {
            persons.add(new GoodMan(datas[i]));
        }
        // 排序
        Collections.sort(persons);
    }
}
