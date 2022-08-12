package dream.guys.hotdeskandroid.model.language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagePOJO {

    @SerializedName("Global")
    @Expose
    private Global global;

    @SerializedName("WellBeing")
    @Expose
    private WellBeing wellBeing;

    public WellBeing getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(WellBeing wellBeing) {
        this.wellBeing = wellBeing;
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public class Global {

        @SerializedName("Incoming")
        @Expose
        private String incoming;
        @SerializedName("Outgoing")
        @Expose
        private String outgoing;
        @SerializedName("Report")
        @Expose
        private String report;
        @SerializedName("Add")
        @Expose
        private String add;
        @SerializedName("Cancel")
        @Expose
        private String cancel;
        @SerializedName("Submit")
        @Expose
        private String submit;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Desk")
        @Expose
        private String desk;
        @SerializedName("Rating")
        @Expose
        private String rating;
        @SerializedName("Select")
        @Expose
        private String select;
        @SerializedName("Checkin")
        @Expose
        private String checkin;
        @SerializedName("Checkout")
        @Expose
        private String checkout;
        @SerializedName("Edit")
        @Expose
        private String edit;
        @SerializedName("NotAvailable")
        @Expose
        private String notAvailable;
        @SerializedName("Approved")
        @Expose
        private String approved;
        @SerializedName("Rejected")
        @Expose
        private String rejected;
        @SerializedName("Approve")
        @Expose
        private String approve;
        @SerializedName("Reject")
        @Expose
        private String reject;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("From")
        @Expose
        private String from;
        @SerializedName("StartHour")
        @Expose
        private String startHour;
        @SerializedName("To")
        @Expose
        private String to;
        @SerializedName("FinishHour")
        @Expose
        private String finishHour;
        @SerializedName("SelectData")
        @Expose
        private String selectData;
        @SerializedName("Survey")
        @Expose
        private String survey;
        @SerializedName("Checklist")
        @Expose
        private String checklist;
        @SerializedName("Anonymous")
        @Expose
        private String anonymous;
        @SerializedName("Yes")
        @Expose
        private String yes;
        @SerializedName("No")
        @Expose
        private String no;
        @SerializedName("Team")
        @Expose
        private String team;
        @SerializedName("Delete")
        @Expose
        private String delete;
        @SerializedName("StartTime")
        @Expose
        private String startTime;
        @SerializedName("FinishTime")
        @Expose
        private String finishTime;
        @SerializedName("Id")
        @Expose
        private String id;
        @SerializedName("Save")
        @Expose
        private String save;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("Optional")
        @Expose
        private String optional;
        @SerializedName("AddNew")
        @Expose
        private String addNew;
        @SerializedName("UnderDevelopment")
        @Expose
        private String underDevelopment;
        @SerializedName("Project")
        @Expose
        private String project;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Continue")
        @Expose
        private String _continue;
        @SerializedName("AddBooking")
        @Expose
        private String addBooking;
        @SerializedName("SaveAll")
        @Expose
        private String saveAll;
        @SerializedName("BookingNotSpecified")
        @Expose
        private String bookingNotSpecified;
        @SerializedName("CarPark")
        @Expose
        private String carPark;
        @SerializedName("MeetingRoom")
        @Expose
        private String meetingRoom;
        @SerializedName("Requested")
        @Expose
        private String requested;
        @SerializedName("Available")
        @Expose
        private String available;
        @SerializedName("OnRequest")
        @Expose
        private String onRequest;
        @SerializedName("Unavailable")
        @Expose
        private String unavailable;
        @SerializedName("BookedByMe")
        @Expose
        private String bookedByMe;
        @SerializedName("BookedByUser")
        @Expose
        private String bookedByUser;
        @SerializedName("BookedByElse")
        @Expose
        private String bookedByElse;
        @SerializedName("More")
        @Expose
        private String more;
        @SerializedName("Success")
        @Expose
        private String success;
        @SerializedName("Error")
        @Expose
        private String error;
        @SerializedName("shortMonthNames")
        @Expose
        private String shortMonthNames;
        @SerializedName("shortDayNames")
        @Expose
        private String shortDayNames;
        @SerializedName("NotificationType")
        @Expose
        private String notificationType;
        @SerializedName("NoNotifications")
        @Expose
        private String noNotifications;
        @SerializedName("EmailOnly")
        @Expose
        private String emailOnly;
        @SerializedName("PushOnly")
        @Expose
        private String pushOnly;
        @SerializedName("Both")
        @Expose
        private String both;
        @SerializedName("ProfilePicture")
        @Expose
        private String profilePicture;
        @SerializedName("Logout")
        @Expose
        private String logout;
        @SerializedName("Car")
        @Expose
        private String car;
        @SerializedName("PublicTransport")
        @Expose
        private String publicTransport;
        @SerializedName("TaxiUber")
        @Expose
        private String taxiUber;
        @SerializedName("Bike")
        @Expose
        private String bike;
        @SerializedName("Walk")
        @Expose
        private String walk;
        @SerializedName("User")
        @Expose
        private String user;
        @SerializedName("WorkFromHome")
        @Expose
        private String workFromHome;
        @SerializedName("CovidCertificationTransportMode")
        @Expose
        private String covidCertificationTransportMode;
        @SerializedName("Questions")
        @Expose
        private String questions;
        @SerializedName("Answer")
        @Expose
        private String answer;
        @SerializedName("Covid19SelfCertification")
        @Expose
        private String covid19SelfCertification;
        @SerializedName("TemplateModeOfTransport")
        @Expose
        private String templateModeOfTransport;
        @SerializedName("TermsOfUse")
        @Expose
        private String termsOfUse;
        @SerializedName("TC1")
        @Expose
        private String tc1;
        @SerializedName("CollectionNotice")
        @Expose
        private String collectionNotice;
        @SerializedName("And")
        @Expose
        private String and;
        @SerializedName("PrivacyPolicy")
        @Expose
        private String privacyPolicy;
        @SerializedName("TC2")
        @Expose
        private String tc2;
        @SerializedName("HealthData")
        @Expose
        private String healthData;
        @SerializedName("CheckBox")
        @Expose
        private String checkBox;
        @SerializedName("Disclaimer")
        @Expose
        private String disclaimer;
        @SerializedName("Accept")
        @Expose
        private String accept;
        @SerializedName("Decline")
        @Expose
        private String decline;
        @SerializedName("CovidTermsAccepted")
        @Expose
        private String covidTermsAccepted;
        @SerializedName("CovidTermsNotAccepted")
        @Expose
        private String covidTermsNotAccepted;
        @SerializedName("CovidSCSubmitted")
        @Expose
        private String covidSCSubmitted;
        @SerializedName("CovidSubmitError")
        @Expose
        private String covidSubmitError;
        @SerializedName("ApproveModalText")
        @Expose
        private String approveModalText;
        @SerializedName("NoRequests")
        @Expose
        private String noRequests;
        @SerializedName("TakePhoto")
        @Expose
        private String takePhoto;
        @SerializedName("EditPhoto")
        @Expose
        private String editPhoto;
        @SerializedName("DeletePhoto")
        @Expose
        private String deletePhoto;
        @SerializedName("ChoosePhoto")
        @Expose
        private String choosePhoto;
        @SerializedName("somethingWrong")
        @Expose
        private String somethingWrong;
        @SerializedName("somethingWrongSavings")
        @Expose
        private String somethingWrongSavings;
        @SerializedName("IssueBooking")
        @Expose
        private String issueBooking;
        @SerializedName("NotificationSaved")
        @Expose
        private String notificationSaved;
        @SerializedName("Back")
        @Expose
        private String back;
        @SerializedName("RejectedReason")
        @Expose
        private String rejectedReason;
        @SerializedName("TheSystem")
        @Expose
        private String theSystem;
        @SerializedName("CameraWentWrong")
        @Expose
        private String cameraWentWrong;
        @SerializedName("PhotoUploadError")
        @Expose
        private String photoUploadError;
        @SerializedName("InvaidTimePeriod")
        @Expose
        private String invaidTimePeriod;
        @SerializedName("TimePassed")
        @Expose
        private String timePassed;
        @SerializedName("InvalidStFiTime")
        @Expose
        private String invalidStFiTime;
        @SerializedName("BookingTypes")
        @Expose
        private String bookingTypes;
        @SerializedName("NonBusinessDay")
        @Expose
        private String nonBusinessDay;
        @SerializedName("NotAssignedToTeam")
        @Expose
        private String notAssignedToTeam;
        @SerializedName("SickLeave")
        @Expose
        private String sickLeave;
        @SerializedName("Today")
        @Expose
        private String today;
        @SerializedName("MySettings")
        @Expose
        private String mySettings;

        public String getIncoming() {
            return incoming;
        }

        public void setIncoming(String incoming) {
            this.incoming = incoming;
        }

        public String getOutgoing() {
            return outgoing;
        }

        public void setOutgoing(String outgoing) {
            this.outgoing = outgoing;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getCancel() {
            return cancel;
        }

        public void setCancel(String cancel) {
            this.cancel = cancel;
        }

        public String getSubmit() {
            return submit;
        }

        public void setSubmit(String submit) {
            this.submit = submit;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDesk() {
            return desk;
        }

        public void setDesk(String desk) {
            this.desk = desk;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getCheckin() {
            return checkin;
        }

        public void setCheckin(String checkin) {
            this.checkin = checkin;
        }

        public String getCheckout() {
            return checkout;
        }

        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }

        public String getEdit() {
            return edit;
        }

        public void setEdit(String edit) {
            this.edit = edit;
        }

        public String getNotAvailable() {
            return notAvailable;
        }

        public void setNotAvailable(String notAvailable) {
            this.notAvailable = notAvailable;
        }

        public String getApproved() {
            return approved;
        }

        public void setApproved(String approved) {
            this.approved = approved;
        }

        public String getRejected() {
            return rejected;
        }

        public void setRejected(String rejected) {
            this.rejected = rejected;
        }

        public String getApprove() {
            return approve;
        }

        public void setApprove(String approve) {
            this.approve = approve;
        }

        public String getReject() {
            return reject;
        }

        public void setReject(String reject) {
            this.reject = reject;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getStartHour() {
            return startHour;
        }

        public void setStartHour(String startHour) {
            this.startHour = startHour;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getFinishHour() {
            return finishHour;
        }

        public void setFinishHour(String finishHour) {
            this.finishHour = finishHour;
        }

        public String getSelectData() {
            return selectData;
        }

        public void setSelectData(String selectData) {
            this.selectData = selectData;
        }

        public String getSurvey() {
            return survey;
        }

        public void setSurvey(String survey) {
            this.survey = survey;
        }

        public String getChecklist() {
            return checklist;
        }

        public void setChecklist(String checklist) {
            this.checklist = checklist;
        }

        public String getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(String anonymous) {
            this.anonymous = anonymous;
        }

        public String getYes() {
            return yes;
        }

        public void setYes(String yes) {
            this.yes = yes;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSave() {
            return save;
        }

        public void setSave(String save) {
            this.save = save;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getOptional() {
            return optional;
        }

        public void setOptional(String optional) {
            this.optional = optional;
        }

        public String getAddNew() {
            return addNew;
        }

        public void setAddNew(String addNew) {
            this.addNew = addNew;
        }

        public String getUnderDevelopment() {
            return underDevelopment;
        }

        public void setUnderDevelopment(String underDevelopment) {
            this.underDevelopment = underDevelopment;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContinue() {
            return _continue;
        }

        public void setContinue(String _continue) {
            this._continue = _continue;
        }

        public String getAddBooking() {
            return addBooking;
        }

        public void setAddBooking(String addBooking) {
            this.addBooking = addBooking;
        }

        public String getSaveAll() {
            return saveAll;
        }

        public void setSaveAll(String saveAll) {
            this.saveAll = saveAll;
        }

        public String getBookingNotSpecified() {
            return bookingNotSpecified;
        }

        public void setBookingNotSpecified(String bookingNotSpecified) {
            this.bookingNotSpecified = bookingNotSpecified;
        }

        public String getCarPark() {
            return carPark;
        }

        public void setCarPark(String carPark) {
            this.carPark = carPark;
        }

        public String getMeetingRoom() {
            return meetingRoom;
        }

        public void setMeetingRoom(String meetingRoom) {
            this.meetingRoom = meetingRoom;
        }

        public String getRequested() {
            return requested;
        }

        public void setRequested(String requested) {
            this.requested = requested;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getOnRequest() {
            return onRequest;
        }

        public void setOnRequest(String onRequest) {
            this.onRequest = onRequest;
        }

        public String getUnavailable() {
            return unavailable;
        }

        public void setUnavailable(String unavailable) {
            this.unavailable = unavailable;
        }

        public String getBookedByMe() {
            return bookedByMe;
        }

        public void setBookedByMe(String bookedByMe) {
            this.bookedByMe = bookedByMe;
        }

        public String getBookedByUser() {
            return bookedByUser;
        }

        public void setBookedByUser(String bookedByUser) {
            this.bookedByUser = bookedByUser;
        }

        public String getBookedByElse() {
            return bookedByElse;
        }

        public void setBookedByElse(String bookedByElse) {
            this.bookedByElse = bookedByElse;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getShortMonthNames() {
            return shortMonthNames;
        }

        public void setShortMonthNames(String shortMonthNames) {
            this.shortMonthNames = shortMonthNames;
        }

        public String getShortDayNames() {
            return shortDayNames;
        }

        public void setShortDayNames(String shortDayNames) {
            this.shortDayNames = shortDayNames;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getNoNotifications() {
            return noNotifications;
        }

        public void setNoNotifications(String noNotifications) {
            this.noNotifications = noNotifications;
        }

        public String getEmailOnly() {
            return emailOnly;
        }

        public void setEmailOnly(String emailOnly) {
            this.emailOnly = emailOnly;
        }

        public String getPushOnly() {
            return pushOnly;
        }

        public void setPushOnly(String pushOnly) {
            this.pushOnly = pushOnly;
        }

        public String getBoth() {
            return both;
        }

        public void setBoth(String both) {
            this.both = both;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getLogout() {
            return logout;
        }

        public void setLogout(String logout) {
            this.logout = logout;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getPublicTransport() {
            return publicTransport;
        }

        public void setPublicTransport(String publicTransport) {
            this.publicTransport = publicTransport;
        }

        public String getTaxiUber() {
            return taxiUber;
        }

        public void setTaxiUber(String taxiUber) {
            this.taxiUber = taxiUber;
        }

        public String getBike() {
            return bike;
        }

        public void setBike(String bike) {
            this.bike = bike;
        }

        public String getWalk() {
            return walk;
        }

        public void setWalk(String walk) {
            this.walk = walk;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getWorkFromHome() {
            return workFromHome;
        }

        public void setWorkFromHome(String workFromHome) {
            this.workFromHome = workFromHome;
        }

        public String getCovidCertificationTransportMode() {
            return covidCertificationTransportMode;
        }

        public void setCovidCertificationTransportMode(String covidCertificationTransportMode) {
            this.covidCertificationTransportMode = covidCertificationTransportMode;
        }

        public String getQuestions() {
            return questions;
        }

        public void setQuestions(String questions) {
            this.questions = questions;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getCovid19SelfCertification() {
            return covid19SelfCertification;
        }

        public void setCovid19SelfCertification(String covid19SelfCertification) {
            this.covid19SelfCertification = covid19SelfCertification;
        }

        public String getTemplateModeOfTransport() {
            return templateModeOfTransport;
        }

        public void setTemplateModeOfTransport(String templateModeOfTransport) {
            this.templateModeOfTransport = templateModeOfTransport;
        }

        public String getTermsOfUse() {
            return termsOfUse;
        }

        public void setTermsOfUse(String termsOfUse) {
            this.termsOfUse = termsOfUse;
        }

        public String getTc1() {
            return tc1;
        }

        public void setTc1(String tc1) {
            this.tc1 = tc1;
        }

        public String getCollectionNotice() {
            return collectionNotice;
        }

        public void setCollectionNotice(String collectionNotice) {
            this.collectionNotice = collectionNotice;
        }

        public String getAnd() {
            return and;
        }

        public void setAnd(String and) {
            this.and = and;
        }

        public String getPrivacyPolicy() {
            return privacyPolicy;
        }

        public void setPrivacyPolicy(String privacyPolicy) {
            this.privacyPolicy = privacyPolicy;
        }

        public String getTc2() {
            return tc2;
        }

        public void setTc2(String tc2) {
            this.tc2 = tc2;
        }

        public String getHealthData() {
            return healthData;
        }

        public void setHealthData(String healthData) {
            this.healthData = healthData;
        }

        public String getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(String checkBox) {
            this.checkBox = checkBox;
        }

        public String getDisclaimer() {
            return disclaimer;
        }

        public void setDisclaimer(String disclaimer) {
            this.disclaimer = disclaimer;
        }

        public String getAccept() {
            return accept;
        }

        public void setAccept(String accept) {
            this.accept = accept;
        }

        public String getDecline() {
            return decline;
        }

        public void setDecline(String decline) {
            this.decline = decline;
        }

        public String getCovidTermsAccepted() {
            return covidTermsAccepted;
        }

        public void setCovidTermsAccepted(String covidTermsAccepted) {
            this.covidTermsAccepted = covidTermsAccepted;
        }

        public String getCovidTermsNotAccepted() {
            return covidTermsNotAccepted;
        }

        public void setCovidTermsNotAccepted(String covidTermsNotAccepted) {
            this.covidTermsNotAccepted = covidTermsNotAccepted;
        }

        public String getCovidSCSubmitted() {
            return covidSCSubmitted;
        }

        public void setCovidSCSubmitted(String covidSCSubmitted) {
            this.covidSCSubmitted = covidSCSubmitted;
        }

        public String getCovidSubmitError() {
            return covidSubmitError;
        }

        public void setCovidSubmitError(String covidSubmitError) {
            this.covidSubmitError = covidSubmitError;
        }

        public String getApproveModalText() {
            return approveModalText;
        }

        public void setApproveModalText(String approveModalText) {
            this.approveModalText = approveModalText;
        }

        public String getNoRequests() {
            return noRequests;
        }

        public void setNoRequests(String noRequests) {
            this.noRequests = noRequests;
        }

        public String getTakePhoto() {
            return takePhoto;
        }

        public void setTakePhoto(String takePhoto) {
            this.takePhoto = takePhoto;
        }

        public String getEditPhoto() {
            return editPhoto;
        }

        public void setEditPhoto(String editPhoto) {
            this.editPhoto = editPhoto;
        }

        public String getDeletePhoto() {
            return deletePhoto;
        }

        public void setDeletePhoto(String deletePhoto) {
            this.deletePhoto = deletePhoto;
        }

        public String getChoosePhoto() {
            return choosePhoto;
        }

        public void setChoosePhoto(String choosePhoto) {
            this.choosePhoto = choosePhoto;
        }

        public String getSomethingWrong() {
            return somethingWrong;
        }

        public void setSomethingWrong(String somethingWrong) {
            this.somethingWrong = somethingWrong;
        }

        public String getSomethingWrongSavings() {
            return somethingWrongSavings;
        }

        public void setSomethingWrongSavings(String somethingWrongSavings) {
            this.somethingWrongSavings = somethingWrongSavings;
        }

        public String getIssueBooking() {
            return issueBooking;
        }

        public void setIssueBooking(String issueBooking) {
            this.issueBooking = issueBooking;
        }

        public String getNotificationSaved() {
            return notificationSaved;
        }

        public void setNotificationSaved(String notificationSaved) {
            this.notificationSaved = notificationSaved;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getRejectedReason() {
            return rejectedReason;
        }

        public void setRejectedReason(String rejectedReason) {
            this.rejectedReason = rejectedReason;
        }

        public String getTheSystem() {
            return theSystem;
        }

        public void setTheSystem(String theSystem) {
            this.theSystem = theSystem;
        }

        public String getCameraWentWrong() {
            return cameraWentWrong;
        }

        public void setCameraWentWrong(String cameraWentWrong) {
            this.cameraWentWrong = cameraWentWrong;
        }

        public String getPhotoUploadError() {
            return photoUploadError;
        }

        public void setPhotoUploadError(String photoUploadError) {
            this.photoUploadError = photoUploadError;
        }

        public String getInvaidTimePeriod() {
            return invaidTimePeriod;
        }

        public void setInvaidTimePeriod(String invaidTimePeriod) {
            this.invaidTimePeriod = invaidTimePeriod;
        }

        public String getTimePassed() {
            return timePassed;
        }

        public void setTimePassed(String timePassed) {
            this.timePassed = timePassed;
        }

        public String getInvalidStFiTime() {
            return invalidStFiTime;
        }

        public void setInvalidStFiTime(String invalidStFiTime) {
            this.invalidStFiTime = invalidStFiTime;
        }

        public String getBookingTypes() {
            return bookingTypes;
        }

        public void setBookingTypes(String bookingTypes) {
            this.bookingTypes = bookingTypes;
        }

        public String getNonBusinessDay() {
            return nonBusinessDay;
        }

        public void setNonBusinessDay(String nonBusinessDay) {
            this.nonBusinessDay = nonBusinessDay;
        }

        public String getNotAssignedToTeam() {
            return notAssignedToTeam;
        }

        public void setNotAssignedToTeam(String notAssignedToTeam) {
            this.notAssignedToTeam = notAssignedToTeam;
        }

        public String getSickLeave() {
            return sickLeave;
        }

        public void setSickLeave(String sickLeave) {
            this.sickLeave = sickLeave;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getMySettings() {
            return mySettings;
        }

        public void setMySettings(String mySettings) {
            this.mySettings = mySettings;
        }

    }

    public class WellBeing {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("DeskReport")
        @Expose
        private String deskReport;
        @SerializedName("DeskSurvey")
        @Expose
        private String deskSurvey;
        @SerializedName("Buildinh")
        @Expose
        private String buildinh;
        @SerializedName("Air")
        @Expose
        private String air;
        @SerializedName("Temperature")
        @Expose
        private String temperature;
        @SerializedName("Noise")
        @Expose
        private String noise;
        @SerializedName("Light")
        @Expose
        private String light;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("KeyBoard")
        @Expose
        private String keyBoard;
        @SerializedName("Chair")
        @Expose
        private String chair;
        @SerializedName("Screen")
        @Expose
        private String screen;
        @SerializedName("Cleanliness")
        @Expose
        private String cleanliness;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("EnterComments")
        @Expose
        private String enterComments;
        @SerializedName("Downloads")
        @Expose
        private String downloads;
        @SerializedName("DownloadPDF")
        @Expose
        private String downloadPDF;
        @SerializedName("SurveyReports")
        @Expose
        private String surveyReports;
        @SerializedName("FillChecklist")
        @Expose
        private String fillChecklist;
        @SerializedName("FillSurvey")
        @Expose
        private String fillSurvey;
        @SerializedName("FillReport")
        @Expose
        private String fillReport;
        @SerializedName("FillReportText")
        @Expose
        private String fillReportText;
        @SerializedName("WorkPlaceAssessment")
        @Expose
        private String workPlaceAssessment;
        @SerializedName("CovidCertification")
        @Expose
        private String covidCertification;
        @SerializedName("AssessmentDate")
        @Expose
        private String assessmentDate;
        @SerializedName("HealthyEating")
        @Expose
        private String healthyEating;
        @SerializedName("ExcerciseTips")
        @Expose
        private String excerciseTips;
        @SerializedName("FireWardens")
        @Expose
        private String fireWardens;
        @SerializedName("FirstAid")
        @Expose
        private String firstAid;
        @SerializedName("Benefits")
        @Expose
        private String benefits;
        @SerializedName("Menu")
        @Expose
        private String menu;
        @SerializedName("Events")
        @Expose
        private String events;
        @SerializedName("Notices")
        @Expose
        private String notices;
        @SerializedName("Rewards")
        @Expose
        private String rewards;
        @SerializedName("Accesibility")
        @Expose
        private String accesibility;
        @SerializedName("Desk")
        @Expose
        private String desk;
        @SerializedName("Display")
        @Expose
        private String display;
        @SerializedName("Keyboard")
        @Expose
        private String keyboard;
        @SerializedName("Mouse")
        @Expose
        private String mouse;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Wifi")
        @Expose
        private String wifi;
        @SerializedName("Lan")
        @Expose
        private String lan;
        @SerializedName("WorkSchedule")
        @Expose
        private String workSchedule;
        @SerializedName("WorkHours")
        @Expose
        private String workHours;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Desks")
        @Expose
        private String desks;
        @SerializedName("Preference")
        @Expose
        private String preference;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("Notifications")
        @Expose
        private String notifications;
        @SerializedName("App")
        @Expose
        private String app;
        @SerializedName("SetUpPin")
        @Expose
        private String setUpPin;
        @SerializedName("SetUpBiometric")
        @Expose
        private String setUpBiometric;
        @SerializedName("ResetPassword")
        @Expose
        private String resetPassword;
        @SerializedName("ReportAnIssue")
        @Expose
        private String reportAnIssue;
        @SerializedName("Help")
        @Expose
        private String help;
        @SerializedName("LogOut")
        @Expose
        private String logOut;
        @SerializedName("Default")
        @Expose
        private String _default;
        @SerializedName("ViewProfile")
        @Expose
        private String viewProfile;

        @SerializedName("DarkMode")
        @Expose
        private String darkMode;
        @SerializedName("ChangeOrganization")
        @Expose
        private String changeOrganization;
        @SerializedName("SetUpEditPin")
        @Expose
        private String setUpEditPin;
        @SerializedName("HelpTroubleShoot")
        @Expose
        private String helpTroubleShoot;
        @SerializedName("WhatsNew")
        @Expose
        private String whatsNew;
        @SerializedName("Feedback")
        @Expose
        private String feedback;
        @SerializedName("About")
        @Expose
        private String about;
        @SerializedName("Security")
        @Expose
        private String security;

        public String getSecurity() {
            return security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public String getDarkMode() {
            return darkMode;
        }

        public void setDarkMode(String darkMode) {
            this.darkMode = darkMode;
        }

        public String getChangeOrganization() {
            return changeOrganization;
        }

        public void setChangeOrganization(String changeOrganization) {
            this.changeOrganization = changeOrganization;
        }

        public String getSetUpEditPin() {
            return setUpEditPin;
        }

        public void setSetUpEditPin(String setUpEditPin) {
            this.setUpEditPin = setUpEditPin;
        }

        public String getHelpTroubleShoot() {
            return helpTroubleShoot;
        }

        public void setHelpTroubleShoot(String helpTroubleShoot) {
            this.helpTroubleShoot = helpTroubleShoot;
        }

        public String getWhatsNew() {
            return whatsNew;
        }

        public void setWhatsNew(String whatsNew) {
            this.whatsNew = whatsNew;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getDeskReport() {
            return deskReport;
        }

        public void setDeskReport(String deskReport) {
            this.deskReport = deskReport;
        }

        public String getDeskSurvey() {
            return deskSurvey;
        }

        public void setDeskSurvey(String deskSurvey) {
            this.deskSurvey = deskSurvey;
        }

        public String getBuildinh() {
            return buildinh;
        }

        public void setBuildinh(String buildinh) {
            this.buildinh = buildinh;
        }

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getNoise() {
            return noise;
        }

        public void setNoise(String noise) {
            this.noise = noise;
        }

        public String getLight() {
            return light;
        }

        public void setLight(String light) {
            this.light = light;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getKeyBoard() {
            return keyBoard;
        }

        public void setKeyBoard(String keyBoard) {
            this.keyBoard = keyBoard;
        }

        public String getChair() {
            return chair;
        }

        public void setChair(String chair) {
            this.chair = chair;
        }

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public String getCleanliness() {
            return cleanliness;
        }

        public void setCleanliness(String cleanliness) {
            this.cleanliness = cleanliness;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getEnterComments() {
            return enterComments;
        }

        public void setEnterComments(String enterComments) {
            this.enterComments = enterComments;
        }

        public String getDownloads() {
            return downloads;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getDownloadPDF() {
            return downloadPDF;
        }

        public void setDownloadPDF(String downloadPDF) {
            this.downloadPDF = downloadPDF;
        }

        public String getSurveyReports() {
            return surveyReports;
        }

        public void setSurveyReports(String surveyReports) {
            this.surveyReports = surveyReports;
        }

        public String getFillChecklist() {
            return fillChecklist;
        }

        public void setFillChecklist(String fillChecklist) {
            this.fillChecklist = fillChecklist;
        }

        public String getFillSurvey() {
            return fillSurvey;
        }

        public void setFillSurvey(String fillSurvey) {
            this.fillSurvey = fillSurvey;
        }

        public String getFillReport() {
            return fillReport;
        }

        public void setFillReport(String fillReport) {
            this.fillReport = fillReport;
        }

        public String getFillReportText() {
            return fillReportText;
        }

        public void setFillReportText(String fillReportText) {
            this.fillReportText = fillReportText;
        }

        public String getWorkPlaceAssessment() {
            return workPlaceAssessment;
        }

        public void setWorkPlaceAssessment(String workPlaceAssessment) {
            this.workPlaceAssessment = workPlaceAssessment;
        }

        public String getCovidCertification() {
            return covidCertification;
        }

        public void setCovidCertification(String covidCertification) {
            this.covidCertification = covidCertification;
        }

        public String getAssessmentDate() {
            return assessmentDate;
        }

        public void setAssessmentDate(String assessmentDate) {
            this.assessmentDate = assessmentDate;
        }

        public String getHealthyEating() {
            return healthyEating;
        }

        public void setHealthyEating(String healthyEating) {
            this.healthyEating = healthyEating;
        }

        public String getExcerciseTips() {
            return excerciseTips;
        }

        public void setExcerciseTips(String excerciseTips) {
            this.excerciseTips = excerciseTips;
        }

        public String getFireWardens() {
            return fireWardens;
        }

        public void setFireWardens(String fireWardens) {
            this.fireWardens = fireWardens;
        }

        public String getFirstAid() {
            return firstAid;
        }

        public void setFirstAid(String firstAid) {
            this.firstAid = firstAid;
        }

        public String getBenefits() {
            return benefits;
        }

        public void setBenefits(String benefits) {
            this.benefits = benefits;
        }

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public String getEvents() {
            return events;
        }

        public void setEvents(String events) {
            this.events = events;
        }

        public String getNotices() {
            return notices;
        }

        public void setNotices(String notices) {
            this.notices = notices;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public String getAccesibility() {
            return accesibility;
        }

        public void setAccesibility(String accesibility) {
            this.accesibility = accesibility;
        }

        public String getDesk() {
            return desk;
        }

        public void setDesk(String desk) {
            this.desk = desk;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getKeyboard() {
            return keyboard;
        }

        public void setKeyboard(String keyboard) {
            this.keyboard = keyboard;
        }

        public String getMouse() {
            return mouse;
        }

        public void setMouse(String mouse) {
            this.mouse = mouse;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWifi() {
            return wifi;
        }

        public void setWifi(String wifi) {
            this.wifi = wifi;
        }

        public String getLan() {
            return lan;
        }

        public void setLan(String lan) {
            this.lan = lan;
        }

        public String getWorkSchedule() {
            return workSchedule;
        }

        public void setWorkSchedule(String workSchedule) {
            this.workSchedule = workSchedule;
        }

        public String getWorkHours() {
            return workHours;
        }

        public void setWorkHours(String workHours) {
            this.workHours = workHours;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDesks() {
            return desks;
        }

        public void setDesks(String desks) {
            this.desks = desks;
        }

        public String getPreference() {
            return preference;
        }

        public void setPreference(String preference) {
            this.preference = preference;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getNotifications() {
            return notifications;
        }

        public void setNotifications(String notifications) {
            this.notifications = notifications;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getSetUpPin() {
            return setUpPin;
        }

        public void setSetUpPin(String setUpPin) {
            this.setUpPin = setUpPin;
        }

        public String getSetUpBiometric() {
            return setUpBiometric;
        }

        public void setSetUpBiometric(String setUpBiometric) {
            this.setUpBiometric = setUpBiometric;
        }

        public String getResetPassword() {
            return resetPassword;
        }

        public void setResetPassword(String resetPassword) {
            this.resetPassword = resetPassword;
        }

        public String getReportAnIssue() {
            return reportAnIssue;
        }

        public void setReportAnIssue(String reportAnIssue) {
            this.reportAnIssue = reportAnIssue;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public String getLogOut() {
            return logOut;
        }

        public void setLogOut(String logOut) {
            this.logOut = logOut;
        }

        public String getDefault() {
            return _default;
        }

        public void setDefault(String _default) {
            this._default = _default;
        }

        public String getViewProfile() {
            return viewProfile;
        }

        public void setViewProfile(String viewProfile) {
            this.viewProfile = viewProfile;
        }



    }

}
