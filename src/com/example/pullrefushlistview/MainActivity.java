package com.example.pullrefushlistview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pullrefushlistview.MainActivity.PullToRefreshListViewSampleAdapter.ViewHolder;
import com.example.pullrefushlistview.PullToRefreshListView.OnRefreshListener;

public class MainActivity extends Activity {
	private PullToRefreshListView listView;
	private PullToRefreshListViewSampleAdapter adapter;

	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);

		// OPTIONAL: Dsssisable scrolling when list is refreshing
		// listView.setLockScrollWhileRefreshing(false);

		// OPTIONAL: Uncomment this if you want the Pull to Refresh header to show the 'last updated' time
		 listView.setShowLastUpdatedText(true);

		// OPTIONAL: Uncomment this if you want to override the date/time format of the 'last updated' field
		// listView.setLastUpdatedDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

		// OPTIONAL: Uncomment this if you want to override the default strings
		// listView.setTextPullToRefresh("Pull to Refresh");
		// listView.setTextReleaseToRefresh("Release to Refresh");
		// listView.setTextRefreshing("Refreshing");

		// MANDATORY: Set the onRefreshListener on the list. You could also use
		// listView.setOnRefreshListener(this); and let this Activity
		// implement OnRefreshListener.
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents goes here
				// for example:
				// If this is a webservice call, it might be asynchronous so
				// you would have to call listView.onRefreshComplete(); when
				// the webservice returns the data
				adapter.loadData();
				// Make sure you call listView.onRefreshComplete()
				// when the loading is done. This can be done from here or any
				// other place, like on a broadcast receive from your loading
				// service or the onPostExecute of your AsyncTask.
				// For the sake of this sample, the code will pause here to
				// force a delay when invoking the refresh
				listView.postDelayed(new Runnable() {
					@Override
					public void run() {
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});
		adapter = new PullToRefreshListViewSampleAdapter() {};
		listView.setAdapter(adapter);
		// Request the adapter to load the data
		adapter.loadData();
		// click listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ViewHolder viewHolder = (ViewHolder) arg1.getTag();
				if (viewHolder.name != null){
					Toast.makeText(MainActivity.this, viewHolder.name.getText(), Toast.LENGTH_SHORT).show();
				}					
			}
		});
	
	}


	


	/**
	 * The adapter used to display the results in the list
	 * 
	 */
	public abstract class PullToRefreshListViewSampleAdapter extends android.widget.BaseAdapter {

		
		private ArrayList<String> items = new ArrayList<String>();;
		
		public class ViewHolder {
			public String id;
			public TextView name;
		}
		
		
		
		/**
		 * Loads the data. 
		 */
		public void loadData() {
			items.add("Ajax Amsterdam");
			items.add("Barcelona");
			items.add("Manchester United");
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;

			String record = (String) getItem(position);

			LayoutInflater inflater = MainActivity.this.getLayoutInflater();

			ViewHolder viewHolder = new ViewHolder();

			if (convertView == null){
				rowView = inflater.inflate(R.layout.list_item,null);

				viewHolder.name = (TextView) rowView.findViewById(R.id.textView1);

				rowView.setTag(viewHolder);
			}

			final ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.name.setText(record); 

			return rowView;
		}
	}

	
	

}
