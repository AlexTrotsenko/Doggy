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
   
   
   public List<VenuePicture> getPicture()
   {
      return Picture;
   }
   public void setPicture(List<VenuePicture> picture)
   {
      Picture = picture;
   }
   public int getDistance()
   {
      return distance;
   }
   public void setDistance(int distance)
   {
      this.distance = distance;
   }
   public String getMaxDogSize()
   {
      return maxDogSize;
   }
   public void setMaxDogSize(String maxDogSize)
   {
      this.maxDogSize = maxDogSize;
   }
   public String getCity()
   {
      return city;
   }
   public void setCity(String city)
   {
      this.city = city;
   }
   public int getId()
   {
      return id;
   }
   public void setId(int id)
   {
      this.id = id;
   }
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   public String getLine1()
   {
      return line1;
   }
   public void setLine1(String line1)
   {
      this.line1 = line1;
   }
}
