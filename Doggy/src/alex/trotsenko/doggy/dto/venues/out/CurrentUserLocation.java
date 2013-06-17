package alex.trotsenko.doggy.dto.venues.out;


public class CurrentUserLocation
{
   private final int range = 25000000;
   private double latitude;
   private double longitude;

   
   
   public CurrentUserLocation()
   {
   }
   
   public CurrentUserLocation(double latitude, double longitude)
   {
      this.latitude = latitude;
      this.longitude = longitude;
   }

   public void setLatitude(double latitude)
   {
      this.latitude = latitude;
   }

   public void setLongitude(double longitude)
   {
      this.longitude = longitude;
   }

}
