package alex.trotsenko.doggy.dto.venues.out;

public class CurrentUserLocation
{
   private final int range = 25000000;
   private float latitude;
   private float longitude;
   
   public void setLatitude(float latitude)
   {
      this.latitude = latitude;
   }
   public void setLongitude(float longitude)
   {
      this.longitude = longitude;
   }
}
