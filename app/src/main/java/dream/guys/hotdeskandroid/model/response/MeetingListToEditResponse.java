package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingListToEditResponse {

        @SerializedName("id")
        int id;
        @SerializedName("meetingRoomId")
        int meetingRoomId;
        @SerializedName("meetingRoomName")
        String meetingRoomName;
        @SerializedName("date")
        String date;
        @SerializedName("from")
        String from;
        @SerializedName("to")
        String to;
        @SerializedName("fromUtc")
        String fromUtc;
        @SerializedName("toUtc")
        String toUtc;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMeetingRoomId() {
            return meetingRoomId;
        }

        public void setMeetingRoomId(int meetingRoomId) {
            this.meetingRoomId = meetingRoomId;
        }

        public String getMeetingRoomName() {
            return meetingRoomName;
        }

        public void setMeetingRoomName(String meetingRoomName) {
            this.meetingRoomName = meetingRoomName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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


}
