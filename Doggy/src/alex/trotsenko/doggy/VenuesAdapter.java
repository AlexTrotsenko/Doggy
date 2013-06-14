package alex.trotsenko.doggy;

import java.util.List;

import alex.trotsenko.doggy.dto.venues.in.VenueInfo;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VenuesAdapter extends ArrayAdapter<VenueInfo>
{
   private final Activity contextActivity;
   private final List<VenueInfo> venues;

   public VenuesAdapter(Activity context, List<VenueInfo> venues)
   {
      super(context, R.layout.rowlayout, venues);
      this.contextActivity = context;
      this.venues = venues;
   }

   static class ViewHolder
   {
      private TextView venueName;
      private ImageView icon;
      public TextView distance;
      public TextView streetAndCity;
      public TextView sizeAllowed;
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
         viewHolder.venueName = (TextView) rowView.findViewById(R.id.venue_name);
         viewHolder.distance = (TextView) rowView.findViewById(R.id.distance);
         viewHolder.streetAndCity = (TextView) rowView.findViewById(R.id.street_and_city);
         viewHolder.sizeAllowed = (TextView) rowView.findViewById(R.id.size_allowed);
         viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
         rowView.setTag(viewHolder);
      }

      ViewHolder holder = (ViewHolder) rowView.getTag();
      VenueInfo venue = venues.get(position);
      String name = venue.getName();
      holder.venueName.setText(name);
      holder.distance.setText(" " + Integer.toString(venue.getDistance()) + " m");
      holder.streetAndCity.setText(venue.getLine1() + ", " + venue.getCity());
      holder.sizeAllowed.setText(venue.getMaxDogSize() + " size allowed");
      //FAKE icon animation. Sould be loaded from somewhere by value of Picture
      if (name.contains("a"))
      {
         holder.icon.setImageResource(R.drawable.restaurant_logo);
      }
      else
      {
         holder.icon.setImageResource(R.drawable.cafe_logo);
      }

      return rowView;
   }
}