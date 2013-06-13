package alex.trotsenko.doggy;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VenuesAdapter extends ArrayAdapter<String>
{
   private final Activity contextActivity;
   private final List<String> venuesNames;

   public VenuesAdapter(Activity context, List<String> venuesNames)
   {
      super(context, R.layout.rowlayout, venuesNames);
      this.contextActivity = context;
      this.venuesNames = venuesNames;
   }

   static class ViewHolder
   {
      private TextView text;
      private ImageView image;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent)
   {
      View rowView = convertView;
      if (rowView == null)
      {
         LayoutInflater inflater = contextActivity.getLayoutInflater();
         rowView = inflater.inflate(R.layout.rowlayout, null);
         ViewHolder viewHolder = new ViewHolder();
         viewHolder.text = (TextView) rowView.findViewById(R.id.label);
         viewHolder.image = (ImageView) rowView.findViewById(R.id.icon);
         rowView.setTag(viewHolder);
      }

      ViewHolder holder = (ViewHolder) rowView.getTag();
      String s = venuesNames.get(position);
      holder.text.setText(s);
      if (s.startsWith("Windows7") || s.startsWith("iPhone") || s.startsWith("Solaris"))
      {
         holder.image.setImageResource(R.drawable.restaurant_logo);
      }
      else
      {
         holder.image.setImageResource(R.drawable.cafe_logo);
      }

      return rowView;
   }
}