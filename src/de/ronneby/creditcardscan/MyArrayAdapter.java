package de.ronneby.creditcardscan;

import java.util.ArrayList;
import de.ronneby.creditcardscan.R;
import io.card.payment.CreditCard;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 *  @author Ronneby
 *
 */
public class MyArrayAdapter extends ArrayAdapter<MyCreditCard>
{

    private ArrayList<MyCreditCard> items;
    private Context context;
	

    public MyArrayAdapter(Context context, int layout, ArrayList<MyCreditCard> items){
        super(context, layout, items);
        this.items=items;
        this.context=context;
    }
     

    private class ViewHolder{
    	TextView number, date;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        CreditCard card=items.get(position);
        
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.number=(TextView) convertView.findViewById(R.id.textView1);
            holder.date=(TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.number.setText(card.cardNumber);
        holder.date.setText(card.expiryMonth + "/" + card.expiryYear);
        return convertView;
    }
}