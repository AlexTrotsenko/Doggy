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
   public void setStatus(int status)
   {
      this.status = status;
   }
   public String getProcName()
   {
      return procName;
   }
   public void setProcName(String procName)
   {
      this.procName = procName;
   }
   public VenueContainer getVenues()
   {
      return Venues;
   }
   public void setVenues(VenueContainer venues)
   {
      Venues = venues;
   }
   
   
}
