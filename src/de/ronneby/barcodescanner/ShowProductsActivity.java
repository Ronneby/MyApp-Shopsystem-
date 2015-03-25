package de.ronneby.barcodescanner;

import java.util.ArrayList;
import de.ronneby.creditcardscan.R;
import de.ronneby.shopsystem.DatabaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Ronneby
 *
 */
public class ShowProductsActivity extends Activity implements OnClickListener
{
	
	private ArrayList<Product> products;
	private DatabaseHelper dbHelper;
	private MyArrayAdapterProducts adapter;
	private Button btn_buy;
	private ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		setTitle("Products");
		
		list = (ListView) findViewById(R.id.listView_products);
		btn_buy = (Button) findViewById(R.id.button_buynow);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		products = new ArrayList<Product>(dbHelper.getAllScanCodes());
		adapter = new MyArrayAdapterProducts(getApplicationContext(), R.layout.item_product_list, products);
		list.setAdapter(adapter);
		
		list.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) 
			{
				Log.v("long clicked", "pos: " + pos);
		
				dbHelper.deleteProduct(products.get(pos));

				products.remove(pos);

				adapter.notifyDataSetChanged();
				
				return true;
			}
		});
		
		btn_buy.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if(list.getCount() > 0){
			startActivity(new Intent(this, BuyProductActivity.class));	
		}
		else
		{
			Toast.makeText(this, "No products to buy", Toast.LENGTH_LONG).show();
		}
	}
}