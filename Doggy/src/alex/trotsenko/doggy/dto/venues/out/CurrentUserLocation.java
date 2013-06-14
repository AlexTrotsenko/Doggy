package alex.trotsenko.doggy.dto.venues.out;

public class CurrentUserLocation
{
   private final int range = 25000000;
   private double latitude;
   private double longitude;

   public void setLatitude(double latitude)
   {
      this.latitude = latitude;
   }

   public void setLongitude(double longitude)
   {
      this.longitude = longitude;
   }
   
   public boolean isInitialized()
   {
      return latitude != 0.0 && longitude != 0.0;
   }
}
