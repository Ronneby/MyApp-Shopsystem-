package de.ronneby.shopsystem;


import java.util.ArrayList;
import java.util.List;
import de.ronneby.barcodescanner.Product;
import de.ronneby.creditcardscan.MyCreditCard;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Ronneby
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	private static DatabaseHelper mInstance = null;

	private static final String DATABASE_NAME = "shopsystem.db";
	private static final int DATABASE_VERSION = 4;
	
	private static final String CARDSCAN_TABLE = "creditcard_scan";
	private static final String PRODUCTSCAN_TABLE = "product_scan";

	private static final String CARDSCAN_ID = "id"; 
	private static final String CARDSCAN_CC_NUMBER = "cc_number"; 
	private static final String CARDSCAN_EXPIRATION_MONTH = "expiration_month";
	private static final String CARDSCAN_EXPIRATION_YEAR = "expiration_year"; 

	private static final String PRODUCTSCAN_ID = "id"; 
	private static final String PRODUCTSCAN_NAME = "name"; 
	private static final String PRODUCTSCAN_PRICE = "price"; 
	private static final String PRODUCTSCAN_CODE = "code"; 

	private Context mCxt;

	public static DatabaseHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}

	private DatabaseHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.mCxt = ctx;
		Log.d("Singleton-Context", "" + mCxt);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String createDB = "CREATE TABLE " + CARDSCAN_TABLE + "(" + CARDSCAN_ID
				+ " INTEGER PRIMARY KEY, " + CARDSCAN_CC_NUMBER + " TEXT, "
				+ CARDSCAN_EXPIRATION_MONTH + " INTENGER, "
				+ CARDSCAN_EXPIRATION_YEAR + " INTEGER" + ")"; 

		String createDBCode = "CREATE TABLE " + PRODUCTSCAN_TABLE + "("
				+ PRODUCTSCAN_ID + " INTEGER PRIMARY KEY, " + PRODUCTSCAN_NAME
				+ " TEXT, " + PRODUCTSCAN_PRICE + " DOUBLE, "
				+ PRODUCTSCAN_CODE + " TEXT" + ")"; 

		db.execSQL(createDBCode); 
		db.execSQL(createDB);     
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CARDSCAN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + PRODUCTSCAN_TABLE);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}


	public long createCard(MyCreditCard cc) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CARDSCAN_CC_NUMBER, cc.cardNumber);
		values.put(CARDSCAN_EXPIRATION_MONTH, cc.expiryMonth);
		values.put(CARDSCAN_EXPIRATION_YEAR, cc.expiryYear);
		return db.insert(CARDSCAN_TABLE, null, values);
	}

	public void deleteCreditCard(MyCreditCard cc) {
		SQLiteDatabase db = getReadableDatabase();
		db.delete(CARDSCAN_TABLE, CARDSCAN_ID + " = ?", new String[] { String.valueOf(cc.getId()) });
	}


	public void clearCard() {
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("DELETE FROM " + CARDSCAN_TABLE);
	}


	public void deleteProduct(Product p) {
		SQLiteDatabase db = getReadableDatabase();
		db.delete(PRODUCTSCAN_TABLE, PRODUCTSCAN_ID + " = ?", new String[] { String.valueOf(p.getId()) });
	}


	public void clearProduct() {
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("DELETE FROM " + PRODUCTSCAN_TABLE);
	}


	public void updateCard(MyCreditCard oldCard, MyCreditCard newCard) {
		SQLiteDatabase db = getReadableDatabase();
		String selectQuery = "SELECT * FROM " + CARDSCAN_TABLE + " WHERE " + CARDSCAN_ID + " = " + oldCard.getId();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null && c.moveToFirst()) {
			ContentValues values = new ContentValues();
			values.put(CARDSCAN_CC_NUMBER, newCard.cardNumber);
			values.put(CARDSCAN_EXPIRATION_MONTH, newCard.expiryMonth);
			values.put(CARDSCAN_EXPIRATION_YEAR, newCard.expiryYear);
			db.update(CARDSCAN_TABLE, values, CARDSCAN_ID + " = " + oldCard.getId(), null);
		}
	}


	public List<MyCreditCard> getAllCreditCards() {
		List<MyCreditCard> cardList = new ArrayList<MyCreditCard>();

		String selectQuery = "SELECT  * FROM " + CARDSCAN_TABLE;

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				String number = cursor.getString(cursor.getColumnIndex(CARDSCAN_CC_NUMBER));
				int month = cursor.getInt(cursor.getColumnIndex(CARDSCAN_EXPIRATION_MONTH));
				int year = cursor.getInt(cursor.getColumnIndex(CARDSCAN_EXPIRATION_YEAR));

				MyCreditCard cc = new MyCreditCard(number, month, year, "", "");
				cc.setId(cursor.getLong(cursor.getColumnIndex(CARDSCAN_ID)));
				cardList.add(cc);
			} while (cursor.moveToNext());
		}
		return cardList;
	}

	public long createProduct(Product p) {
		SQLiteDatabase db = getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(PRODUCTSCAN_NAME, p.getName());
		values.put(PRODUCTSCAN_PRICE, p.getPrice());
		values.put(PRODUCTSCAN_CODE, p.getCode());
		return db.insert(PRODUCTSCAN_TABLE, null, values);
	}

	public List<Product> getAllScanCodes() {
		List<Product> cardList = new ArrayList<Product>();
		String selectQuery = "SELECT  * FROM " + PRODUCTSCAN_TABLE;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex(PRODUCTSCAN_NAME));
				double price = cursor.getDouble(cursor.getColumnIndex(PRODUCTSCAN_PRICE));
				String code = cursor.getString(cursor.getColumnIndex(PRODUCTSCAN_CODE));
				Product qr = new Product(name, price, code);
				qr.setId(cursor.getLong(cursor.getColumnIndex(PRODUCTSCAN_ID)));
				cardList.add(qr);
			} while (cursor.moveToNext());
		}
		return cardList;
	}
}
