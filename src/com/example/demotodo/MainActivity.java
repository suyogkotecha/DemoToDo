package com.example.demotodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.LauncherActivity.ListItem;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity {

	ListView listView;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
/*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id., new PlaceholderFragment()).commit();
		}*/

		items = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.lvToDoItems);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		listView.setAdapter(itemsAdapter);
		setItemActionListener();
	}

	private void setItemActionListener() {
		// TODO Auto-generated method stub
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				items.remove(position);
				saveItems();
				itemsAdapter.notifyDataSetChanged();
				return true;
			}
		});
	}

	public void addTodoItem(View v){
		EditText et = (EditText) findViewById(R.id.etNewItem);
		if(et.getText().toString()!=null && et.getText().toString().length()>0){
			itemsAdapter.add(et.getText().toString());
			saveItems();
		}
		et.setText("");
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	public void saveItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
