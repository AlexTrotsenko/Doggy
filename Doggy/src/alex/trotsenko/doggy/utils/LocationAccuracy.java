package alex.trotsenko.doggy.utils;

import android.location.Location;
import android.text.TextUtils;

public final class LocationAccuracy
{
   public static final int UPDATE_PERIOD = 1000 * 60 * 3;

   public static final int SIGNIFICANT_PERIOD = 1000 * 60 * 6;

   public static final int UPDATE_DISTANCE = 100;

   public static final int SIGNIFICANT_DISTANCE = 200;

   private LocationAccuracy()
   {
   }

   /** Determines whether one Location reading is better than the current Location fix.
    * @param newLocation  The new Location that you want to evaluate
    * @param currentBestLocation  The current Location fix, to which you want to compare the new one
    */
   public static boolean isBetterLocation(Location newLocation, Location currentBestLocation)
   {
      if (currentBestLocation == null)
      {
         // A new location is always better than no location
         return true;
      }
      
      if (newLocation == null)
      {
         // Any location is always better than no location
         return false;
      }

      // Check whether the new location fix is newer or older
      long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
      boolean isSignificantlyNewer = timeDelta > SIGNIFICANT_PERIOD;
      boolean isSignificantlyOlder = timeDelta < -SIGNIFICANT_PERIOD;
      boolean isNewer = timeDelta > 0;

      // If it's been more than two minutes since the current location, use the new location
      // because the user has likely moved
      if (isSignificantlyNewer)
      {
         return true;
         // If the new location is more than two minutes older, it must be worse
      }
      else if (isSignificantlyOlder)
      {
         return false;
      }

      // Check whether the new location fix is more or less accurate
      int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
      boolean isLessAccurate = accuracyDelta > 0;
      boolean isMoreAccurate = accuracyDelta < 0;
      boolean isSignificantlyLessAccurate = accuracyDelta > SIGNIFICANT_DISTANCE;

      // Check if the old and new location are from the same provider
      boolean isFromSameProvider = TextUtils.equals(newLocation.getProvider(),
            currentBestLocation.getProvider());

      // Determine location quality using a combination of timeliness and accuracy
      if (isMoreAccurate)
      {
         return true;
      }
      else if (isNewer && !isLessAccurate)
      {
         return true;
      }
      else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
      {
         return true;
      }
      return false;
   }
}
