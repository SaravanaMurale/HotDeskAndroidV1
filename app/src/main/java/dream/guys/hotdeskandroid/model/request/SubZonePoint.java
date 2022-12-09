package dream.guys.hotdeskandroid.model.request;

import java.util.List;

public class SubZonePoint {

    int supportZoneId;
    String title;
    List<SubPoint> subPoint;


    public SubZonePoint() {
    }

    public SubZonePoint(int supportZoneId, String title, List<SubPoint> subPoint) {
        this.supportZoneId = supportZoneId;
        this.title = title;
        this.subPoint = subPoint;
    }

    public SubZonePoint(List<SubPoint> subPoint) {
        this.subPoint = subPoint;
    }

    public int getSupportZoneId() {
        return supportZoneId;
    }

    public void setSupportZoneId(int supportZoneId) {
        this.supportZoneId = supportZoneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubPoint> getSubPoint() {
        return subPoint;
    }

    public void setSubPoint(List<SubPoint> subPoint) {
        this.subPoint = subPoint;
    }

    public class SubPoint{

        int x;
        int y;

        public SubPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
