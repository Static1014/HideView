package com.example.hideview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private int layoutId;

	public MyAdapter(Context context, int type) {
		this.context = context;
		if (type == 1) {
			layoutId = R.layout.item_list1;
		} else if (type == 2) {
			layoutId = R.layout.item_list2;
		} else {
			layoutId = R.layout.item_list3;
		}
	}

	@Override
	public int getCount() {
		return 12;
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	public class ViewHolder {
		TextView tView;
		TextView tView2;
	}

}
