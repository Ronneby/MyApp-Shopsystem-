/**
 * @author Aron
 * @version 6.0
 * 
 * 
 * 
 */

package de.ronneby.creditcardscan;

import java.util.ArrayList;
import de.ronneby.creditcardscan.R;
import de.ronneby.shopsystem.DatabaseHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Ronneby
 *
 */
public class ShowCardsActivity extends Activity 
{

	final String TAG = getClass().getName();
	private ArrayList<MyCreditCard> cards;	 									
	private DatabaseHelper dbHelper; 		 
	private MyArrayAdapter adapter; 
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_list);
		getActionBar().setHomeButtonEnabled(true);
		setTitle("Credit Cards");

		list = (ListView) findViewById(R.id.listView1);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		cards = new ArrayList<MyCreditCard>(dbHelper.getAllCreditCards());
		adapter = new MyArrayAdapter(getApplicationContext(), R.layout.item, cards);
		
		list.setAdapter(adapter);
		registerForContextMenu(list);
			
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.card_selected, menu);
		menu.setHeaderTitle("Options"); 
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId())
		{
		case R.id.card_delete:
			dbHelper.deleteCreditCard(cards.get(info.position));
			cards.remove(info.position);
			adapter.notifyDataSetChanged();
			return true;
			
		case R.id.card_update:
			Intent intent = new Intent(getApplicationContext(),UpdateActivity.class);
			MyCreditCard cc = cards.get(info.position);
			
			intent.putExtra("POSTION", cards.get(info.position));
			intent.putExtra("Number", cc.cardNumber);
			intent.putExtra("Date", cc.expiryMonth + "/" + cc.expiryYear);
			startActivity(intent);		
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	public void onClear(View v) 
	{
		new AlertDialog.Builder(this)
	    .setTitle("Clear all")
	    .setMessage("Delete all entries?")
	    .setPositiveButton(R.string.AD_clear_all_yes, new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int which) 
	        { 
	        		dbHelper.clearCard();
	        		cards.clear();
	        		adapter.notifyDataSetChanged();
	        }
	     })
	    .setNegativeButton(R.string.AD_clear_all_no, new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int which) 
	        { 
	            
	        }
	     }).setIcon(android.R.drawable.ic_dialog_alert).show();
	}
}
