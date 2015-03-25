package de.ronneby.barcodescanner;


import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import de.ronneby.creditcardscan.R;
import de.ronneby.shopsystem.DatabaseHelper;
import de.ronneby.shopsystem.MainActivity;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Ronneby
 *
 */
public class ReceiptActivity extends Activity implements OnClickListener
{
	private Button btn;
	private TextView tv;
	private ListView list;
	
	private ArrayList<Product> products;
	private DatabaseHelper dbHelper;
	private MyArrayAdapterReceipt adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy);
		
		btn = (Button) findViewById(R.id.button_cont_shop);
		tv = (TextView) findViewById(R.id.textView_receipt);
		list = (ListView) findViewById(R.id.listView_paid_products);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		products = new ArrayList<Product>(dbHelper.getAllScanCodes());
		adapter = new MyArrayAdapterReceipt(getApplicationContext(), R.layout.item_receipt, products);
		
		list.setAdapter(adapter);
		btn.setOnClickListener(this);
		

		double price=0;
		for(Product p : products){
			price+=p.getPrice();
		}
		tv.setText(""+price+Currency.getInstance(Locale.getDefault()).getSymbol());
	}

	public void showNotification(){

		Notification n = new Notification.Builder(this)
		.setContentTitle("Successful Shopping")
		.setContentText("Thank you for shopping in our Shopsystem")
		.setSmallIcon(R.drawable.ic_launchertwo)
		.build();
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(0, n);
	}

	@Override
	public void onClick(View e)
	{
		dbHelper.clearProduct();
		finish();
		startActivity(new Intent(this, MainActivity.class));
		showNotification();

	}
}
