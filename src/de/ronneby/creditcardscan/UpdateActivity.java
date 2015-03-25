package de.ronneby.creditcardscan;

import de.ronneby.creditcardscan.R;
import de.ronneby.shopsystem.DatabaseHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class UpdateActivity extends Activity implements OnClickListener
{

	private MyCreditCard cc;
	private EditText number, date;
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
		Intent intent = getIntent();
		
		number = (EditText) findViewById(R.id.number);
		date = (EditText) findViewById(R.id.date);
		Button update = (Button) findViewById(R.id.save);
		
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		
		cc = dbHelper.getAllCreditCards().get(intent.getIntExtra("POSITION", 0));
		number.setText(cc.cardNumber);
		date.setText(cc.expiryMonth + "/" + cc.expiryYear);
		
		update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.save:
			try {
				String checkNumber = number.getText().toString();
				int[] tempArray = convert(checkNumber);
				
				if(check(tempArray)){
					MyCreditCard oldCard = cc;
					cc.cardNumber = checkNumber;
					cc.cardNumber = cc.getFormattedCardNumber();
					String[] dat = date.getText().toString().split("/");
					cc.expiryMonth = Integer.parseInt(dat[0]);
					cc.expiryYear = Integer.parseInt(dat[1]);
					dbHelper.updateCard(oldCard, cc);
					finish();
				}
				else
				{
					errorAD();
				}
				
			} catch (Exception e) {
				Toast.makeText(this, "Error: onClick()", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public boolean check(int[] digits) {
		int sum = 0;
		int length = digits.length;
		for (int i = 0; i < length; i++) {

			int digit = digits[length - i - 1];

			if (i % 2 == 1) {
				digit *= 2;
			}
			sum += digit > 9 ? digit - 9 : digit;
		}
		return sum % 10 == 0;
	}


	public int[] convert(String number) {

		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = number.charAt(i) - '0';
		}
		return array;
	}

	
	public void errorAD(){
		new AlertDialog.Builder(this)
		.setTitle("ERROR")
		.setMessage("Incorrect entry")
		.setPositiveButton(R.string.AD_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
					}
				}).show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "Delete the spaces between the numbers!!", Toast.LENGTH_LONG).show();
	}
}
