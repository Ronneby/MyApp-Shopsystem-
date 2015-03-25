package de.ronneby.barcodescanner;


import java.util.ArrayList;
import de.ronneby.creditcardscan.R;
import de.ronneby.creditcardscan.MyArrayAdapter;
import de.ronneby.creditcardscan.MyCreditCard;
import de.ronneby.shopsystem.DatabaseHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Ronneby
 *
 */
public class BuyProductActivity extends Activity
{
	private ListView list;
	private ArrayList<MyCreditCard> cards;	
	private DatabaseHelper dbHelper; 		 
	private MyArrayAdapter adapter; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_card);
		
		list = (ListView) findViewById(R.id.listView_choose_card);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());

		cards = new ArrayList<MyCreditCard>(dbHelper.getAllCreditCards());
		
		adapter = new MyArrayAdapter(getApplicationContext(), R.layout.item,cards);
		
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) 
			{				
				new AlertDialog.Builder(BuyProductActivity.this)
			    .setMessage("Do you want to use following credit card for the payment? Card Nr: " + cards.get(arg2).cardNumber)
			    .setPositiveButton(R.string.AD_choose_card_yes, new DialogInterface.OnClickListener() 
			    {
			        public void onClick(DialogInterface dialog, int which) 
			        { 
			        	Intent intent = new Intent(getApplicationContext(), ReceiptActivity.class);
			        	intent.putExtra("ID", cards.get(arg2).getId());
			        	startActivity(intent);
			        }
			     })
			    .setNegativeButton(R.string.AD_choose_card_no, new DialogInterface.OnClickListener() 
			    {
			        public void onClick(DialogInterface dialog, int which) 
			        { 
			           
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert).show();
			}
		});
	}
}
