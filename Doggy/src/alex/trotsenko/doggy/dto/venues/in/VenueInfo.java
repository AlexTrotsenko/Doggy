package alex.trotsenko.doggy.dto.venues.in;

import java.util.List;

public class VenueInfo
{
   private int id;
   private String name;
   private String line1;
   private String city;
   private int distance;
   private String maxDogSize;
   List<VenuePicture> Picture;
   public int getId()
   {
      return id;
   }
   public String getName()
   {
      return name;
   }
   public String getLine1()
   {
      return line1;
   }
   public String getCity()
   {
      return city;
   }
   public int getDistance()
   {
      return distance;
   }
   public String getMaxDogSize()
   {
      return maxDogSize;
   }
   public List<VenuePicture> getPicture()
   {
      return Picture;
   }
   
   
}
