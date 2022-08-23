package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteMeetingRoomRequest {

    @SerializedName("changesets")
    List<DelChangesets> changesetsList;

    @SerializedName("deletedIds")
    List<Integer> deletedIdsList;

    public List<DelChangesets> getChangesetsList() {
        return changesetsList;
    }

    public void setChangesetsList(List<DelChangesets> changesetsList) {
        this.changesetsList = changesetsList;
    }

    public List<Integer> getDeletedIdsList() {
        return deletedIdsList;
    }

    public void setDeletedIdsList(List<Integer> deletedIdsList) {
        this.deletedIdsList = deletedIdsList;
    }

    public class DeletedIds{

    }

    public class DelChangesets{

    }

}
