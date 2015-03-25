package de.ronneby.shopsystem;


import de.ronneby.creditcardscan.R;
import de.ronneby.barcodescanner.AddProductActivity;
import de.ronneby.barcodescanner.ShowProductsActivity;
import de.ronneby.creditcardscan.MyCreditCard;
import de.ronneby.creditcardscan.ShowCardsActivity;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 *  @author Ronneby
 *  @version 8.2.0
 *
 */
public class MainActivity extends Activity implements OnClickListener 
{

	private Button btn1, btn2, btn3, btn4;				
	private int MY_SCAN_REQUEST_CODE = 100;
	private DatabaseHelper dbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_main);
		setTitle("Shopsystem");
		
		btn1 = (Button) findViewById(R.id.btn_add_product);
		btn2 = (Button) findViewById(R.id.btn_show_product);
		btn3 = (Button) findViewById(R.id.btn_show_credit_card);
		btn4 = (Button) findViewById(R.id.btn_add_credit_card);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
		{
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			String number = scanResult.getFormattedCardNumber();
			int month = 0;
			int year = 0;
			if (scanResult.isExpiryValid()) 
			{
				month = scanResult.expiryMonth;
				year = scanResult.expiryYear;
			}

			MyCreditCard card = new MyCreditCard(number, month, year, "", "");
			dbHelper.createCard(card);
		}
	}
	

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_add_product:
			startActivity(new Intent(this, AddProductActivity.class));
			break;
			
		case R.id.btn_show_product:
			startActivity(new Intent(this, ShowProductsActivity.class));
			break;
			
		case R.id.btn_add_credit_card:
			Intent scanIntent = new Intent(this, CardIOActivity.class);
			scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
			scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false);
			scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
			scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false);
			startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
			break;
			
		case R.id.btn_show_credit_card:
			startActivity(new Intent(this, ShowCardsActivity.class));
			break;
		}
	}
}
