package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarParkingDeleteRequest {

    int parkingSlotId;
    @SerializedName("changesets")
    List<Changesets> changesetsList;
    @SerializedName("deletedIds")
    List<Integer> deIntegerList;

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public List<Changesets> getChangesetsList() {
        return changesetsList;
    }

    public void setChangesetsList(List<Changesets> changesetsList) {
        this.changesetsList = changesetsList;
    }

    public List<Integer> getDeIntegerList() {
        return deIntegerList;
    }

    public void setDeIntegerList(List<Integer> deIntegerList) {
        this.deIntegerList = deIntegerList;
    }

    public class Changesets{
        int id;
        String date;
        @SerializedName("changes")
        Changes changes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }

        public class Changes{
            String from;
            String to;
            String comments;
            int bookedForUser;
            String vehicleRegNumber;

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

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public int getBookedForUser() {
                return bookedForUser;
            }

            public void setBookedForUser(int bookedForUser) {
                this.bookedForUser = bookedForUser;
            }

            public String getVehicleRegNumber() {
                return vehicleRegNumber;
            }

            public void setVehicleRegNumber(String vehicleRegNumber) {
                this.vehicleRegNumber = vehicleRegNumber;
            }
        }

    }

}
