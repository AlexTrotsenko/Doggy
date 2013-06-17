package alex.trotsenko.doggy.translators;

import alex.trotsenko.doggy.dto.venues.out.CurrentUserLocation;
import android.location.Location;

public final class LocationTranslator
{
   private LocationTranslator()
   {
   }

   public static CurrentUserLocation translate(Location androidLocation)
   {
      CurrentUserLocation dtoLocation = new CurrentUserLocation();
      dtoLocation.setLatitude(androidLocation.getLatitude());
      dtoLocation.setLongitude(androidLocation.getLongitude());
      return dtoLocation;
   }
}
