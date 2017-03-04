package com.infzm.slidingmenu.demo;

import com.infzm.slidingmenu.demo.fragment.LeftFragment;
import com.infzm.slidingmenu.demo.fragment.TodayFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 涓荤晫闈�
 */
public class MainActivity extends SlidingFragmentActivity implements OnClickListener {
	WebView webView;
	EditText edit1;

	ImageButton ib1;

	Button button1;
	private ImageView topButton;
	private Fragment mContent;
	private TextView topTextView;
	
	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
				long contentLength) {
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 鏃犳爣棰�
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu(savedInstanceState);
		webView = (WebView) findViewById(R.id.webView1);
		topButton = (ImageView) findViewById(R.id.topButton);
		topButton.setOnClickListener(this);
		topTextView = (TextView) findViewById(R.id.topTv);
		 
		WebSettings webSettings = webView.getSettings(); // 允许访问文件
		webView.getSettings().setSupportZoom(true);// 实现缩放
		webView.getSettings().setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webView.getSettings().setSupportZoom(true);// 实现缩放
		webView.getSettings().setJavaScriptEnabled(true);
	
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		// 开启 DOM storage API 功能
		webView.getSettings().setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webView.getSettings().setDatabaseEnabled(true);
		String cacheDirPath = getFilesDir().getAbsolutePath() + ACCESSIBILITY_SERVICE;

		// 设置数据库缓存路径

		// 设置 Application Caches 缓存目录
		webView.getSettings().setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.loadUrl("https://m.baidu.com/?from=1013843a&pu=sz%401321_480&wpo=btmfast");
		
		webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;

			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				webView.loadUrl("file:///android_asset/error.html");
			}

		});

		webView.setWebViewClient(new WebViewClient());
		// 给webview加入监听

		webView.setDownloadListener(new MyWebViewDownLoadListener());

		WebSettings webSetting = webView.getSettings();

		webSetting.setJavaScriptEnabled(true);

		webSetting.setAllowFileAccess(true);

		webSetting.setDomStorageEnabled(true);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
	
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();// 返回上一页面
				return true;
			} else {
				System.exit(0);// 退出程序
			}
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {
		case R.id.menu_about:
			Toast.makeText(MainActivity.this, "" + "关于", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_settings:

			Toast.makeText(MainActivity.this, "" + "设置", Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_refresh:

			Toast.makeText(MainActivity.this, "" + "刷新", Toast.LENGTH_SHORT).show();
			webView.reload();
			break;
		case R.id.menu_quit:

			Toast.makeText(MainActivity.this, "" + "退出", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}

	/**
	 * 鍒濆鍖栦晶杈规爮
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 濡傛灉淇濆瓨鐨勭姸鎬佷笉涓虹┖鍒欏緱鍒颁箣鍓嶄繚瀛樼殑Fragment锛屽惁鍒欏疄渚嬪寲MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new TodayFragment();
		}

		// 璁剧疆宸︿晶婊戝姩鑿滃崟
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new LeftFragment()).commit();

		// 瀹炰緥鍖栨粦鍔ㄨ彍鍗曞璞�
		SlidingMenu sm = getSlidingMenu();
		// 璁剧疆鍙互宸﹀彸婊戝姩鐨勮彍鍗�
		sm.setMode(SlidingMenu.LEFT);
		// 璁剧疆婊戝姩闃村奖鐨勫搴�
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 璁剧疆婊戝姩鑿滃崟闃村奖鐨勫浘鍍忚祫婧�
		sm.setShadowDrawable(null);
		// 璁剧疆婊戝姩鑿滃崟瑙嗗浘鐨勫搴�
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 璁剧疆娓愬叆娓愬嚭鏁堟灉鐨勫��
		sm.setFadeDegree(0.35f);
		// 璁剧疆瑙︽懜灞忓箷鐨勬ā寮�,杩欓噷璁剧疆涓哄叏灞�
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 璁剧疆涓嬫柟瑙嗗浘鐨勫湪婊氬姩鏃剁殑缂╂斁姣斾緥
		sm.setBehindScrollScale(0.0f);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	/**
	 * 鍒囨崲Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		topTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}

}
