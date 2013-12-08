package com.coinhark.litecoinbalance;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import db.Address;
import db.AddressDBHandler;

public class AddressesActivity extends ListActivity {
	
	private static final int DELETE_ID = Menu.FIRST + 1; 

	private List<Address> addressList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresses);
		
		AddressDBHandler datasource = new AddressDBHandler(this);
	    datasource.open();
	    
	    addressList = datasource.getAllAddresses();
	    ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this,
	        android.R.layout.simple_list_item_1, addressList);
	    setListAdapter(adapter);
	    datasource.close();
	    
	    registerForContextMenu(getListView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listmenu, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.address_remove);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case DELETE_ID:
	    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    		AddressDBHandler db = new AddressDBHandler(this);
	    		db.open();
	    		db.deleteAddressById(addressList.get((int) info.id).getId());
	    		db.close();
	    		Intent nextScreen = new Intent(getApplicationContext(), AddressesActivity.class);
	    		startActivity(nextScreen);
	    	return true;
	    }
	    return super.onContextItemSelected(item);
	 }
	  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.create:
				getAddView();
			break;
			case R.id.menu_home:
				getMainView();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getAddView() {
		Intent nextScreen = new Intent(getApplicationContext(), NewAddressActivity.class);
		startActivity(nextScreen);
	}
	
	public void getMainView() {
		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(nextScreen);
	}

}
