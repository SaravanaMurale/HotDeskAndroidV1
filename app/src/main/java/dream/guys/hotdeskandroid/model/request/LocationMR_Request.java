package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationMR_Request {

    @SerializedName("amenities")
    List<Amenities> amenitiesList;
    @SerializedName("from")
    String from;
    @SerializedName("to")
    String to;
    @SerializedName("date")
    String date;
    @SerializedName("locationId")
    int locationId;
    @SerializedName("timezone")
    Timezone timezone;

    public class Amenities{

    }

    public class Timezone{
        @SerializedName("id")
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public List<Amenities> getAmenitiesList() {
        return amenitiesList;
    }

    public void setAmenitiesList(List<Amenities> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }
}
