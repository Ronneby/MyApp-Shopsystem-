package de.ronneby.barcodescanner;

import java.util.ArrayList;
import de.ronneby.creditcardscan.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Ronneby
 *
 */
public class MyArrayAdapterReceipt extends ArrayAdapter<Product>
{
	private ArrayList<Product> products;
    private Context context;

    public MyArrayAdapterReceipt(Context context, int layout, ArrayList<Product> items){
        super(context, layout, items);
        this.context=context;
        products=items;
    }
    
    private class ViewHolder{
    	TextView name;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        
        Product p = products.get(position);
        
        if(convertView==null){
        	
            convertView=LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
            holder = new ViewHolder();
            holder.name=(TextView) convertView.findViewById(R.id.textView_receipt_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.name.setText(p.getName());
        return convertView;
    }
}
