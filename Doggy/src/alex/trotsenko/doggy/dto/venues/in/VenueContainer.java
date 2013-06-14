package alex.trotsenko.doggy.dto.venues.in;

import java.util.List;

public class VenueContainer
{
   private List<VenueInfo> Venue;

   public List<VenueInfo> getVenue()
   {
      return Venue;
   }

   public void setVenue(List<VenueInfo> venueHolder)
   {
      Venue = venueHolder;
   }
   
   
}
