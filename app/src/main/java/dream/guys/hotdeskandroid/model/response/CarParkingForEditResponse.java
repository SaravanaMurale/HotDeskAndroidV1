package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CarParkingForEditResponse {

    private List<CarParkBooking> carParkBookings;
    private List<Integer> workDays;

    public List<CarParkBooking> getCarParkBookings() {
        return carParkBookings;
    }

    public void setCarParkBookings(List<CarParkBooking> carParkBookings) {
        this.carParkBookings = carParkBookings;
    }

    public List<Integer> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<Integer> workDays) {
        this.workDays = workDays;
    }

    public class CarParkBooking{
        public int id;
        public int parkingSlotId;
        public String parkingSlotName;
        public Date date;
        public String from;
        @SerializedName("to")
        public String myto;
        public String fromUtc;
        public String toUtc;
        public String timeZoneId;
        public String comments;
        public BookedForUser bookedForUser;
        public int bookingCreatedUserId;
        public String bookedForUserName;
        public String vehicleRegNumber;
        public Status status;
        public boolean managedBooking;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParkingSlotId() {
            return parkingSlotId;
        }

        public void setParkingSlotId(int parkingSlotId) {
            this.parkingSlotId = parkingSlotId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public String getParkingSlotName() {
            return parkingSlotName;
        }

        public void setParkingSlotName(String parkingSlotName) {
            this.parkingSlotName = parkingSlotName;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public BookedForUser getBookedForUser() {
            return bookedForUser;
        }

        public void setBookedForUser(BookedForUser bookedForUser) {
            this.bookedForUser = bookedForUser;
        }

        public int getBookingCreatedUserId() {
            return bookingCreatedUserId;
        }

        public void setBookingCreatedUserId(int bookingCreatedUserId) {
            this.bookingCreatedUserId = bookingCreatedUserId;
        }

        public String getBookedForUserName() {
            return bookedForUserName;
        }

        public void setBookedForUserName(String bookedForUserName) {
            this.bookedForUserName = bookedForUserName;
        }

        public String getVehicleRegNumber() {
            return vehicleRegNumber;
        }

        public void setVehicleRegNumber(String vehicleRegNumber) {
            this.vehicleRegNumber = vehicleRegNumber;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public boolean isManagedBooking() {
            return managedBooking;
        }

        public void setManagedBooking(boolean managedBooking) {
            this.managedBooking = managedBooking;
        }

        public class BookedForUser{
            public int id;
            public String firstName;
            public String lastName;
            public String fullName;
            public Object email;
            public boolean active;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }
        }

        public class Status{
            public String timeStatus;
            public String bookingStatus;
            public String bookingType;
            public boolean isToday;

            public String getTimeStatus() {
                return timeStatus;
            }

            public void setTimeStatus(String timeStatus) {
                this.timeStatus = timeStatus;
            }

            public String getBookingStatus() {
                return bookingStatus;
            }

            public void setBookingStatus(String bookingStatus) {
                this.bookingStatus = bookingStatus;
            }

            public String getBookingType() {
                return bookingType;
            }

            public void setBookingType(String bookingType) {
                this.bookingType = bookingType;
            }

            public boolean isToday() {
                return isToday;
            }

            public void setToday(boolean today) {
                isToday = today;
            }
        }

    }


}
