package com.gary.garytool.lib.fancyindexer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gary.garytool.lib.fancyindexer.domain.CarBrand;
import com.gary.garytool.lib.fancyindexer.domain.GoodMan;
import com.gary.garytool.R;

public class CarBrandAdapter extends BaseAdapter {

	private final ArrayList<CarBrand> carBrands;
	private final Context context;

	public CarBrandAdapter(ArrayList<CarBrand> carBrands, Context context) {
		this.carBrands = carBrands;
		this.context = context;
	}

	@Override
	public int getCount() {
		return carBrands.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if(convertView == null){
			view = View.inflate(context, R.layout.function_car_brand_item, null);
		}else {
			view = convertView;
		}
		
		ViewHolder holder = ViewHolder.getHolder(view);
		CarBrand carBrand = carBrands.get(position);

		// 进行分组, 比较上一个拼音的首字母和自己是否一致, 如果不一致, 就显示tv_index
		
		String currentLetter = carBrand.getPinyin().charAt(0) + "";
		String indexStr = null;
		if(position == 0){
			// 1. 如果是第一位
			indexStr = currentLetter;
		}else {
			// 获取上一个拼音
			String preLetter = carBrands.get(position - 1).getPinyin().charAt(0) + "";
			if(!TextUtils.equals(currentLetter, preLetter)){
				// 2. 当跟上一个不同时, 赋值, 显示
				indexStr = currentLetter;
			}
		}
		
		holder.tv_index.setVisibility(indexStr == null ? View.GONE : View.VISIBLE);
		holder.tv_index.setText(indexStr);

		holder.layout_one.setVisibility(View.INVISIBLE);
		holder.layout_two.setVisibility(View.INVISIBLE);


		List<GoodMan> goodMen=carBrand.getBrands();

		for(int i=0;i<goodMen.size();i++)
		{
			GoodMan goodMan= goodMen.get(i);
			if(i==0)
			{
				holder.layout_one.setVisibility(View.VISIBLE);
				holder.tv_one.setText(goodMan.getName());
				holder.iv_one.setImageDrawable(context.getResources().getDrawable(R.drawable.car_brand_0));
			}
			else if(i==1)
			{
				holder.layout_two.setVisibility(View.VISIBLE);
				holder.tv_two.setText(goodMan.getName());
				holder.iv_two.setImageDrawable(context.getResources().getDrawable(R.drawable.car_brand_0));
			}

		}

		

		return view;
	}
	
	static class ViewHolder {
		public TextView tv_index;
		public LinearLayout layout_one;
		public TextView tv_one;
		public ImageView iv_one;
		public LinearLayout layout_two;
		public TextView tv_two;
		public ImageView iv_two;


		public static ViewHolder getHolder(View view) {
			ViewHolder holder = (ViewHolder) view.getTag();
			
			if(holder == null){
				holder = new ViewHolder();
				holder.tv_index = (TextView) view.findViewById(R.id.tv_index);
				holder.layout_one= (LinearLayout) view.findViewById(R.id.layout_one);
				holder.tv_one = (TextView) view.findViewById(R.id.tv_one);
				holder.iv_one= (ImageView) view.findViewById(R.id.iv_one);
				holder.layout_two= (LinearLayout) view.findViewById(R.id.layout_two);
				holder.tv_two = (TextView) view.findViewById(R.id.tv_two);
				holder.iv_two= (ImageView) view.findViewById(R.id.iv_two);
				view.setTag(holder);
			}
			return holder;
		}
	}

}
