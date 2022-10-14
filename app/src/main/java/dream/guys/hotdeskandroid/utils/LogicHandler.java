package dream.guys.hotdeskandroid.utils;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;

public class LogicHandler {

    public static List<Integer> getNewlyAdded(List<MeetingListToEditResponse.Attendees> attendeesListForEdit, List<ParticipantDetsilResponse> chipList){

        //Contains all original value
        List<Integer> originalList=new ArrayList<>();
        for (int i = 0; i <attendeesListForEdit.size() ; i++) {
            originalList.add(attendeesListForEdit.get(i).getId());
        }
        //Added value
        List<Integer> addedList=new ArrayList<>();
        for (int i = 0; i <chipList.size() ; i++) {
            addedList.add(chipList.get(i).getId());
        }

        addedList.removeAll(originalList);

        return addedList;

    }

    public static List<Integer> getRemoved(List<MeetingListToEditResponse.Attendees> attendeesListForEdit, List<ParticipantDetsilResponse> chipList){

        //Contains all original value
        List<Integer> originalList=new ArrayList<>();
        for (int i = 0; i <attendeesListForEdit.size() ; i++) {
            originalList.add(attendeesListForEdit.get(i).getId());
        }

        //Added value
        List<Integer> addedList=new ArrayList<>();
        for (int i = 0; i <chipList.size() ; i++) {
            addedList.add(chipList.get(i).getId());
        }

        originalList.removeAll(addedList);

        return originalList;

    }

}
