package alex.trotsenko.doggy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Venues extends Activity
{

   static final int LOGIN_REQUEST = 1;  // The request code
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_venues);

      final ListView venuesListview = (ListView) findViewById(R.id.venuesListview);
      String[] vanuesArray = new String[] {"Android2", "iPhone", "WindowsMobile", "Blackberry", "WebOS",
            "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
            "iPhone", "WindowsMobile"};
      final ArrayList<String> vanues = new ArrayList<String>();
      for (int i = 0; i < vanuesArray.length; ++i)
      {
         vanues.add(vanuesArray[i]);
      }

      final VenuesAdapter adapter = new VenuesAdapter(this, vanues);

      venuesListview.setAdapter(adapter);

      venuesListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {

         @Override
         public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
         {
            final String name = (String) parent.getItemAtPosition(position);
            Toast.makeText(venuesListview.getContext(), "Curent vanue is " + name, Toast.LENGTH_LONG)
               .show();
         }

      });

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
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // Check which request we're responding to
       if (requestCode == LOGIN_REQUEST) {
           // Make sure the request was successful
           if (resultCode == RESULT_OK) {
              Toast.makeText(this, "User come from login page ", Toast.LENGTH_LONG)
              .show();
              
              //TODO react to login action
           }
       }
   }
   
}
