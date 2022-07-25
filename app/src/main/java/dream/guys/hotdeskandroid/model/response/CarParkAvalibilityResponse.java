package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

public class CarParkAvalibilityResponse {

    @SerializedName("parkingSlotId")
    int parkingSlotAvalibilityId;
    @SerializedName("isAvailable")
    boolean isAvailable;
    @SerializedName("isBookedByUser")
    boolean isBookedByUser;
    @SerializedName("isPartiallyAvailable")
    boolean isPartiallyAvailable;
    @SerializedName("isBookedByElse")
    boolean isBookedByElse;

    public int getParkingSlotAvalibilityId() {
        return parkingSlotAvalibilityId;
    }

    public void setParkingSlotAvalibilityId(int parkingSlotAvalibilityId) {
        this.parkingSlotAvalibilityId = parkingSlotAvalibilityId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isBookedByUser() {
        return isBookedByUser;
    }

    public void setBookedByUser(boolean bookedByUser) {
        isBookedByUser = bookedByUser;
    }

    public boolean isPartiallyAvailable() {
        return isPartiallyAvailable;
    }

    public void setPartiallyAvailable(boolean partiallyAvailable) {
        isPartiallyAvailable = partiallyAvailable;
    }

    public boolean isBookedByElse() {
        return isBookedByElse;
    }

    public void setBookedByElse(boolean bookedByElse) {
        isBookedByElse = bookedByElse;
    }
}
