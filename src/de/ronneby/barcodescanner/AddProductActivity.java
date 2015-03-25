package de.ronneby.barcodescanner;

import de.ronneby.creditcardscan.R;
import de.ronneby.shopsystem.DatabaseHelper;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Ronneby
 *
 */
public class AddProductActivity extends Activity implements OnClickListener
{
	
	private EditText editName, editPrice;
	private DatabaseHelper dbHelper;
	private String name, tempPrice;
	private double price = 0.0;
	private Button scanBarcode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_add_product);
		setTitle("Product");
		
		scanBarcode = (Button) findViewById(R.id.button_scan_product_barcode);
		editName = (EditText) findViewById(R.id.editText_product_anme);
		editPrice = (EditText) findViewById(R.id.editText_product_price);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		
		scanBarcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.button_scan_product_barcode:
        	name = editName.getText().toString();
        	tempPrice = editPrice.getText().toString();
        	
			if(name.length() == 0 || tempPrice.length() == 0){
				
				Toast.makeText(this, "No productname and or productprice", Toast.LENGTH_LONG).show();
			}
			else
			{
				try
				{
					IntentIntegrator.initiateScan(this, R.layout.capture, R.id.viewfinder_view, R.id.preview_view, true);
				}
				catch(Exception e)
				{
					Toast.makeText(this, "Error: onClick", Toast.LENGTH_LONG).show();
				}	
			}
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode)
		{
        case IntentIntegrator.REQUEST_CODE:
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
            if (scanResult == null) 
            {
                return;
            }
            try
            {
            	final String code = scanResult.getContents();
            	price = Double.parseDouble(tempPrice);

            	if(code != null && !name.isEmpty())
            	{
            		Product p = new Product(name, price, code);
            		dbHelper.createProduct(p);
            		finish();
            	}
            }
			catch(Exception e)
			{
				Toast.makeText(this, "Error: onActivityResult", Toast.LENGTH_LONG).show();
				finish();
			}
         
            break;
        default:
    }
	}
}
