package com.example.hideview;

/**
 * Copyright 2013 Square, Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

	}

	public void click(View v) {
		String str = "http://c.hiphotos.baidu.com/image/w%3D2048/sign=94cfe3de9c510fb378197097ed0bc895/b21bb051f8198618447842e348ed2e738bd4e69e.jpg";
		String gif = "http://b21.photo.store.qq.com/psb?/V12YVail1ax9PD/3HFWiEfQ69SFu.lvZUyF3K0mz8QVVJPXXI7j3zx4OHI!/b/dBUAAAAAAAAA&bo=kAHhAJAB4QACCCw!";
		String video = Environment.getExternalStorageDirectory().getPath() + "/Movies/you belong with me.mp4";

		ImageView iv = (ImageView) findViewById(R.id.iv);
		ImageView iv2 = (ImageView) findViewById(R.id.iv2);
		ImageView iv3 = (ImageView) findViewById(R.id.iv3);

		// Picasso.with(this).load(str).resize(80,
		// 100).centerInside().placeholder(R.drawable.ic_launcher).error(R.drawable.psb).into(iv);
		Glide.with(this).load(str).placeholder(R.drawable.ic_launcher).error(R.drawable.psb).into(iv);
		Glide.with(this).load(gif).placeholder(R.drawable.ic_launcher).error(R.drawable.psb).into(iv2);
		Glide.with(this).load(video).placeholder(R.drawable.ic_launcher).error(R.drawable.psb).into(iv3);
	}

	public void clickView(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

}
