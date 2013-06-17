package alex.trotsenko.doggy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import alex.trotsenko.doggy.dto.venues.in.VenueInfo;
import alex.trotsenko.doggy.dto.venues.in.VenuesNearbyResponse;
import alex.trotsenko.doggy.dto.venues.out.CurrentUserLocation;
import alex.trotsenko.doggy.translators.LocationTranslator;
import alex.trotsenko.doggy.utils.LocationAccuracy;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class VenuesActivity extends Activity
{

   static final int LOGIN_REQUEST = 1; // The request code
   private List<VenueInfo> venues = new ArrayList<VenueInfo>();
   private VenuesAdapter adapter;
   private Location lastLocation;
   private LocationListener locationUpdatesListener;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_venues);

      final ListView venuesListview = (ListView) findViewById(R.id.venuesListview);
      adapter = new VenuesAdapter(this, venues);
      venuesListview.setAdapter(adapter);
      venuesListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {

         @Override
         public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
         {
            final VenueInfo venue = (VenueInfo) parent.getItemAtPosition(position);
            Toast.makeText(venuesListview.getContext(), "Curent vanue is " + venue.getName(),
                  Toast.LENGTH_LONG).show();
         }

      });
   }
   
   @Override
   protected void onStart()
   {
      super.onStart();
      initilizeVenuesList();
   }
   
   @Override
   protected void onStop()
   {
      super.onStop();
      LocationManager locator = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      locator.removeUpdates(locationUpdatesListener);
   }

   private void initilizeVenuesList()
   {
      LocationManager locator = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      boolean gpsLocationAccessible = locator.isProviderEnabled(LocationManager.GPS_PROVIDER);
      boolean networkLocationAccessible = locator
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      validateLocationAccessibility(gpsLocationAccessible, networkLocationAccessible);

      // Define a listener that responds to location updates
      locationUpdatesListener = new LocationUpdatesListener();


      Location netLocation = null;
      Location gpsLocation = null;
      if (networkLocationAccessible)
      {
         locator.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
               LocationAccuracy.UPDATE_PERIOD, LocationAccuracy.UPDATE_DISTANCE,
               locationUpdatesListener);
         netLocation = locator.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      }

      if (gpsLocationAccessible)
      {
         locator.requestLocationUpdates(LocationManager.GPS_PROVIDER,
               LocationAccuracy.SIGNIFICANT_PERIOD, LocationAccuracy.SIGNIFICANT_DISTANCE,
               locationUpdatesListener);
         gpsLocation = locator.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      }

      if (!isVenuesWereInilizedBefore())
      {
         boolean isGpsNewerAndMoreAccurate = LocationAccuracy.isBetterLocation(gpsLocation,
            netLocation);
         updateVenuesUponLocation(isGpsNewerAndMoreAccurate ? gpsLocation : netLocation);
      }
   }

   private void validateLocationAccessibility(boolean gpsLocationAccessible,
         boolean networkLocationAccessible)
   {
      if (!gpsLocationAccessible && !networkLocationAccessible)
      {

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Required Location data");
         builder.setMessage("Application have to access either GPS or network location data");

         builder.setPositiveButton("Make location setting accessible",
               new DialogInterface.OnClickListener()
               {

                  public void onClick(DialogInterface dialog, int which)
                  {

                     Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                     startActivity(settingsIntent);
                     dialog.dismiss();
                  }
               });

         builder.setNegativeButton("Close application", new DialogInterface.OnClickListener()
         {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               finish();
               dialog.dismiss();
            }
         });
         AlertDialog alert = builder.create();
         alert.show();

      }
   }

   /** This methods is call back for for addVenue button.
    * 
    * @param view fake according to android api
    */
   public void addVenue(View view)
   {
      //TODO check where user have been already logged on.
      Intent loginIntent = new Intent(this, LoginActivity.class);
      startActivityForResult(loginIntent, LOGIN_REQUEST);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      // Check which request we're responding to
      if (requestCode == LOGIN_REQUEST)
      {
         // Make sure the request was successful
         if (resultCode == RESULT_OK)
         {
            Toast.makeText(this, "User come from login page ", Toast.LENGTH_LONG).show();

            //TODO react to login action
         }
      }
   }

   private void updateVenuesUponLocation(Location newLocation)
   {
      if (null != newLocation)
      {
         boolean canBeTreatedAsNewerAndMoreAccurate = LocationAccuracy.isBetterLocation(
               newLocation, lastLocation);
         if (canBeTreatedAsNewerAndMoreAccurate)
         {
            lastLocation = newLocation;
            CurrentUserLocation locationInfo = LocationTranslator.translate(newLocation);
            new VenuesListUpdater().execute(locationInfo);
         }
      }
   }

   private final class LocationUpdatesListener implements LocationListener
   {
      public void onLocationChanged(Location newLocation)
      {
         // Called when a new location is found by the network location provider.
         updateVenuesUponLocation(newLocation);
      }

      public void onStatusChanged(String provider, int status, Bundle extras)
      {
      }

      public void onProviderEnabled(String provider)
      {
      }

      public void onProviderDisabled(String provider)
      {
      }
   }

   public class VenuesListUpdater extends
         AsyncTask<CurrentUserLocation, Void, VenuesNearbyResponse>
   {

      @Override
      protected VenuesNearbyResponse doInBackground(CurrentUserLocation... currenLocation)
      {
         HttpParams httpParams = new BasicHttpParams();
         httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
         HttpClient client = new DefaultHttpClient(httpParams);
         HttpPost httpPost = new HttpPost("http://api.dev.friendbuy.de/venue/location");

         VenuesNearbyResponse venuesNearby = new VenuesNearbyResponse();
         Gson gson = new Gson();
         String currenLocationJson = gson.toJson(currenLocation[0]);

         String venuesNearbyJson = null;
         try
         {
            StringEntity entityRequest = new StringEntity(currenLocationJson);
            entityRequest.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(entityRequest);

            HttpResponse response = client.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();

            final int sucessfullResponseCode = 200;
            if (status == sucessfullResponseCode)
            {
               HttpEntity entityResponse = response.getEntity();
               venuesNearbyJson = EntityUtils.toString(entityResponse);
               //Log.i("REST_GET_VENUES", venuesNearbyJson);
            }

         }
         catch (IOException e)
         {
            Log.e("REST_GET_VENUES", e.toString());
         }
         venuesNearby = gson.fromJson(venuesNearbyJson, VenuesNearbyResponse.class);
         return venuesNearby;
      }

      protected void onPostExecute(VenuesNearbyResponse venuesNearby)
      {
         venues.clear();
         venues.addAll(venuesNearby.getVenues().getVenue());
         adapter.notifyDataSetChanged();
      }

   }

   private boolean isVenuesWereInilizedBefore()
   {
      return !venues.isEmpty();
   }
}
