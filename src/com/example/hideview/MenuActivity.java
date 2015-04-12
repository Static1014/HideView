package com.example.hideview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MenuActivity extends Activity {

	private ListView lv1, lv2, lv3;
	private boolean clickAble = true;
	private boolean show2 = false, show3 = false;
	private boolean link = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		init();
	}

	private void init() {
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		lv3 = (ListView) findViewById(R.id.lv3);

		int w = getScreenWidth(this);
		int h = getScreenHeight(this);

		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(w, h);
		lv1.setLayoutParams(params1);

		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(w * 2 / 3, h);
		params2.setMargins(w / 3, 0, 0, 0);
		lv2.setLayoutParams(params2);

		RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(w / 3, h);
		params3.setMargins(w * 2 / 3, 0, 0, 0);
		lv3.setLayoutParams(params3);

		lv1.setAdapter(new MyAdapter(this, 1));
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (clickAble) {
					showList(lv2);
				}
			}
		});

		lv2.setAdapter(new MyAdapter(this, 2));
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (clickAble && show2) {
					showList(lv3);
				}
			}
		});
		lv3.setAdapter(new MyAdapter(this, 3));
		lv3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (clickAble) {
					Log.e("", "-----------: " + position);
				}
			}
		});

		lv2.setLayoutAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				clickAble = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				clickAble = false;
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				show2 = !show2;
				if (!show2) {
					lv2.setVisibility(View.GONE);
					lv3.setVisibility(View.GONE);
				}
				clickAble = true;
			}
		});
		lv3.setLayoutAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				clickAble = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				clickAble = false;
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				show3 = !show3;
				if (!show3) {
					lv3.setVisibility(View.GONE);
				}
				if (!link) {
					clickAble = true;
				} else {
					link = false;
				}
			}
		});
	}

	private void showList(ListView listView) {
		if (listView == lv2) {
			if (show2) {
				lv2.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_right_out_short));
				lv2.startLayoutAnimation();
				if (show3) {
					link = true;
					lv3.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_right_out_short));
					lv3.startLayoutAnimation();
				}
			} else {
				lv2.setVisibility(View.VISIBLE);
				lv2.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_right_in_short));
				lv2.startLayoutAnimation();
			}
		} else if (listView == lv3) {
			if (show3) {
				lv3.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_right_out_short));
				lv3.startLayoutAnimation();
			} else {
				lv3.setVisibility(View.VISIBLE);
				lv3.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_right_in_short));
				lv3.startLayoutAnimation();
			}
		}
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}
