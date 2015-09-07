package com.gary.garytool.adapter;

import android.content.Context;

import com.gary.garytool.R;
import com.gary.garytool.info.MessageBean;
import com.gary.garytool.view.AdapterM;
import com.gary.garytool.view.ViewHolderM;

/**
 * Created by Administrator on 2015/9/7.
 */
public class ListAdapter extends AdapterM<MessageBean>{
    public ListAdapter(Context context, int layoutId, MessageBean[] dataArray) {
        super(context, layoutId, dataArray);
    }

    @Override
    public void convert(ViewHolderM holder, MessageBean model) {
        // 为个控件绑定内容
        holder.setText(R.id.tv_title, model.getTitle());
        holder.setText(R.id.tv_content, model.getContent());
        holder.setText(R.id.tv_comment, model.getComment() + "条评论");
        holder.setText(R.id.tv_read, model.getRead() + "阅读");
    }
}
