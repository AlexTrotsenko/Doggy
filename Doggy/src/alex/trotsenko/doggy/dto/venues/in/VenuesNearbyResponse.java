package alex.trotsenko.doggy.dto.venues.in;

public class VenuesNearbyResponse
{
   private int status;
   private String procName;
   private VenueContainer Venues;
   
   public int getStatus()
   {
      return status;
   }
   public String getProcName()
   {
      return procName;
   }
   public VenueContainer getVenues()
   {
      return Venues;
   }
   
   
}
