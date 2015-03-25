package de.ronneby.barcodescanner;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.ronneby.creditcardscan.R;

/**
 * @author Ronneby
 *
 */
public class MyArrayAdapterProducts extends ArrayAdapter<Product> {
	
	private ArrayList<Product> products;
	private Context context;

	public MyArrayAdapterProducts(Context context, int layout, ArrayList<Product> items) {
		super(context, layout, items);
		this.context = context;
		products = items;
	}

	private class ViewHolder {
		TextView name, price, code;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		Product p = products.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_product_list, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.textV_show_productName);
			holder.price = (TextView) convertView
					.findViewById(R.id.textV_productPrice);
			holder.code = (TextView) convertView
					.findViewById(R.id.textV_productBarcode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(p.getName());
		holder.price.setText("" + p.getPrice());
		holder.code.setText("" + p.getCode());
		return convertView;
	}
}
