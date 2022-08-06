package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditCommentRequest {

    @SerializedName("teamId")
    int teamId;
    @SerializedName("teamMembershipId")
    int teamMembershipId;
    @SerializedName("changesets")
    List<Changesets> changesets;
    @SerializedName("deletedIds")
    List<Delete> deleteIds;

    public class Changesets{
        @SerializedName("id")
        int id;
        @SerializedName("date")
        String date;
        @SerializedName("changes")
        Changes changes;

        public class Changes{
            @SerializedName("comments")
            String comments;
        }
    }

    public class Delete{

    }

}
