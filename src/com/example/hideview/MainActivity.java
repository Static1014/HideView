package com.example.hideview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private ListView lv;
	private SimpleAdapter adapter;
	private RelativeLayout rlCondition;
	private View vDetail;

	private List<Map<String, String>> list;

	private final int HIDE = 0, SHOW = 1, ACTION = 2;
	private int state = SHOW;
	private float pre = 0;
	private int conditionHeight, headerHeight, detailHeight;

	private long delay = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.lv);
		rlCondition = (RelativeLayout) findViewById(R.id.rl_condition);
		vDetail = (View) findViewById(R.id.detail);

		conditionHeight = getResources().getDimensionPixelSize(R.dimen.height_condition);
		headerHeight = getResources().getDimensionPixelSize(R.dimen.height_header);
		detailHeight = getResources().getDimensionPixelSize(R.dimen.height_detail);

		initListView();
		initAction();
	}

	private void initListView() {
		list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 12; i++) {
			Map<String, String> item = new HashMap<String, String>();
			item.put("one", i + 1 + "");
			item.put("two", "texttext");
			list.add(item);
		}

		adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[] { "one", "two" }, new int[] { android.R.id.text1, android.R.id.text2 });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.e("", "-----------: " + position);
			}
		});
	}

	@SuppressLint("ClickableViewAccessibility")
	private void initAction() {
		lv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 重置下拉条件VIEW
				vDetail.setVisibility(View.GONE);
				vDetail.setY(headerHeight);

				float cur = event.getRawY();
				delay++;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 重置touch事件起点
					pre = cur;
					delay = -1;

					break;
				case MotionEvent.ACTION_MOVE:
					if (delay % 3 > 0) {
						pre = cur;
					} else if (delay % 3 == 0) {
						delay = delay > 99999 ? 0 : delay;

						switch (state) {
						case ACTION:

							break;
						case SHOW:
							if (cur < pre) {
								// 向上滑动
								animateView(-conditionHeight, 500);
							}
							break;
						case HIDE:
							if (cur > pre) {
								// 向下滑动
								animateView(conditionHeight, 500);
							}

							break;

						default:
							break;
						}
					}
					break;

				default:
					break;
				}
				return false;
			}
		});

		rlCondition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animateDetail(true);
			}
		});

		vDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animateDetail(false);
			}
		});
	}

	private void animateDetail(final boolean show) {
		ObjectAnimator animD = ObjectAnimator.ofFloat(vDetail, "y", show ? headerHeight + conditionHeight : headerHeight + conditionHeight - detailHeight);
		animD.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				vDetail.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (!show) {
					vDetail.setVisibility(View.GONE);
					// 点击条件，刷新LISTVIEW
					adapter.notifyDataSetChanged();
					lv.setSelection(0);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		animD.setDuration(500).start();
	}

	private void animateView(final float dx, final long time) {
		final ObjectAnimator animCV = ObjectAnimator.ofFloat(rlCondition, "y", dx > 0 ? headerHeight : 0).setDuration(500);
		animCV.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				state = dx > 0 ? SHOW : HIDE;
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});

		ObjectAnimator animLV = ObjectAnimator.ofFloat(lv, "y", dx > 0 ? headerHeight + conditionHeight : headerHeight).setDuration(time);
		animLV.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				state = ACTION;

				animCV.start();
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		animLV.start();

	}
}
