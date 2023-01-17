package com.brickendon.hdplus.model.language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagePOJO {

    @SerializedName("Global")
    @Expose
    private Global global;

    @SerializedName("WellBeing")
    @Expose
    private WellBeing wellBeing;

    @SerializedName("Login")
    @Expose
    private Login login;
    @SerializedName("Me")
    @Expose
    private Me me;
    @SerializedName("Booking")
    @Expose
    private Booking booking;

    @SerializedName("CovidSelfCertification")
    @Expose
    private CovidSelfCertification covidSelfCertification;
    @SerializedName("PersonalHelp")
    @Expose
    private PersonalHelp personalHelp;
    @SerializedName("Approvals")
    @Expose
    private Approvals approvals;
    @SerializedName("Requests")
    @Expose
    private Requests requests;
    @SerializedName("MeetingRooms")
    @Expose
    private MeetingRooms meetingRooms;
    @SerializedName("CarPark")
    @Expose
    private CarPark carPark;

    @SerializedName("ResetPassword")
    @Expose
    private ResetPassword resetPassword;
    @SerializedName("AccountSettings")
    @Expose
    private AccountSettings accountSettings;
    @SerializedName("CommonTitles")
    @Expose
    private CommonTitles commonTitles;
    @SerializedName("Search")
    @Expose
    private Search search;
    @SerializedName("AppFeedback")
    @Expose
    private AppFeedback appFeedback;
    @SerializedName("ActionOverLays")
    @Expose
    private ActionOverLays actionOverLays;
    @SerializedName("Settings")
    @Expose
    private Settings settings;
    @SerializedName("AppKeys")
    @Expose
    private AppKeys appKeys;


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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Me getMe() {
        return me;
    }

    public void setMe(Me me) {
        this.me = me;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public CovidSelfCertification getCovidSelfCertification() {
        return covidSelfCertification;
    }

    public void setCovidSelfCertification(CovidSelfCertification covidSelfCertification) {
        this.covidSelfCertification = covidSelfCertification;
    }

    public PersonalHelp getPersonalHelp() {
        return personalHelp;
    }

    public void setPersonalHelp(PersonalHelp personalHelp) {
        this.personalHelp = personalHelp;
    }

    public Approvals getApprovals() {
        return approvals;
    }

    public void setApprovals(Approvals approvals) {
        this.approvals = approvals;
    }

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    public MeetingRooms getMeetingRooms() {
        return meetingRooms;
    }

    public void setMeetingRooms(MeetingRooms meetingRooms) {
        this.meetingRooms = meetingRooms;
    }

    public CarPark getCarPark() {
        return carPark;
    }

    public void setCarPark(CarPark carPark) {
        this.carPark = carPark;
    }


    public ResetPassword getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(ResetPassword resetPassword) {
        this.resetPassword = resetPassword;
    }

    public AccountSettings getAccountSettings() {
        return accountSettings;
    }

    public void setAccountSettings(AccountSettings accountSettings) {
        this.accountSettings = accountSettings;
    }

    public CommonTitles getCommonTitles() {
        return commonTitles;
    }

    public void setCommonTitles(CommonTitles commonTitles) {
        this.commonTitles = commonTitles;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public AppFeedback getAppFeedback() {
        return appFeedback;
    }

    public void setAppFeedback(AppFeedback appFeedback) {
        this.appFeedback = appFeedback;
    }

    public ActionOverLays getActionOverLays() {
        return actionOverLays;
    }

    public void setActionOverLays(ActionOverLays actionOverLays) {
        this.actionOverLays = actionOverLays;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public AppKeys getAppKeys() {
        return appKeys;
    }

    public void setAppKeys(AppKeys appKeys) {
        this.appKeys = appKeys;
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

    public class Login {

        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("Tenant")
        @Expose
        private String tenant;
        @SerializedName("InvalidCredentialsTitle")
        @Expose
        private String invalidCredentialsTitle;
        @SerializedName("InvalidCredentials")
        @Expose
        private String invalidCredentials;
        @SerializedName("SecondAuthentication")
        @Expose
        private String secondAuthentication;
        @SerializedName("EnterYourCode")
        @Expose
        private String enterYourCode;
        @SerializedName("Authenticate")
        @Expose
        private String authenticate;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getInvalidCredentialsTitle() {
            return invalidCredentialsTitle;
        }

        public void setInvalidCredentialsTitle(String invalidCredentialsTitle) {
            this.invalidCredentialsTitle = invalidCredentialsTitle;
        }

        public String getInvalidCredentials() {
            return invalidCredentials;
        }

        public void setInvalidCredentials(String invalidCredentials) {
            this.invalidCredentials = invalidCredentials;
        }

        public String getSecondAuthentication() {
            return secondAuthentication;
        }

        public void setSecondAuthentication(String secondAuthentication) {
            this.secondAuthentication = secondAuthentication;
        }

        public String getEnterYourCode() {
            return enterYourCode;
        }

        public void setEnterYourCode(String enterYourCode) {
            this.enterYourCode = enterYourCode;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

    }

    public class Me {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("Home")
        @Expose
        private String home;
        @SerializedName("MyWeek")
        @Expose
        private String myWeek;
        @SerializedName("MyTeam")
        @Expose
        private String myTeam;
        @SerializedName("MyVirtualTeam")
        @Expose
        private String myVirtualTeam;
        @SerializedName("EmptyList")
        @Expose
        private String emptyList;
        @SerializedName("EmptyTeam")
        @Expose
        private String emptyTeam;
        @SerializedName("UnableToCheckin")
        @Expose
        private String unableToCheckin;
        @SerializedName("UnableToCheckout")
        @Expose
        private String unableToCheckout;
        @SerializedName("CheckinTooEarly")
        @Expose
        private String checkinTooEarly;
        @SerializedName("CheckinTooLate")
        @Expose
        private String checkinTooLate;
        @SerializedName("NoDeskMatching")
        @Expose
        private String noDeskMatching;
        @SerializedName("AutoCheckedin")
        @Expose
        private String autoCheckedin;
        @SerializedName("Checkedin")
        @Expose
        private String checkedin;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMyWeek() {
            return myWeek;
        }

        public void setMyWeek(String myWeek) {
            this.myWeek = myWeek;
        }

        public String getMyTeam() {
            return myTeam;
        }

        public void setMyTeam(String myTeam) {
            this.myTeam = myTeam;
        }

        public String getMyVirtualTeam() {
            return myVirtualTeam;
        }

        public void setMyVirtualTeam(String myVirtualTeam) {
            this.myVirtualTeam = myVirtualTeam;
        }

        public String getEmptyList() {
            return emptyList;
        }

        public void setEmptyList(String emptyList) {
            this.emptyList = emptyList;
        }

        public String getEmptyTeam() {
            return emptyTeam;
        }

        public void setEmptyTeam(String emptyTeam) {
            this.emptyTeam = emptyTeam;
        }

        public String getUnableToCheckin() {
            return unableToCheckin;
        }

        public void setUnableToCheckin(String unableToCheckin) {
            this.unableToCheckin = unableToCheckin;
        }

        public String getUnableToCheckout() {
            return unableToCheckout;
        }

        public void setUnableToCheckout(String unableToCheckout) {
            this.unableToCheckout = unableToCheckout;
        }

        public String getCheckinTooEarly() {
            return checkinTooEarly;
        }

        public void setCheckinTooEarly(String checkinTooEarly) {
            this.checkinTooEarly = checkinTooEarly;
        }

        public String getCheckinTooLate() {
            return checkinTooLate;
        }

        public void setCheckinTooLate(String checkinTooLate) {
            this.checkinTooLate = checkinTooLate;
        }

        public String getNoDeskMatching() {
            return noDeskMatching;
        }

        public void setNoDeskMatching(String noDeskMatching) {
            this.noDeskMatching = noDeskMatching;
        }

        public String getAutoCheckedin() {
            return autoCheckedin;
        }

        public void setAutoCheckedin(String autoCheckedin) {
            this.autoCheckedin = autoCheckedin;
        }

        public String getCheckedin() {
            return checkedin;
        }

        public void setCheckedin(String checkedin) {
            this.checkedin = checkedin;
        }

    }

    public class Booking {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("AlternateOffice")
        @Expose
        private String alternateOffice;
        @SerializedName("Day")
        @Expose
        private String day;
        @SerializedName("Week")
        @Expose
        private String week;
        @SerializedName("InOffice")
        @Expose
        private String inOffice;
        @SerializedName("WorkingFromHome")
        @Expose
        private String workingFromHome;
        @SerializedName("OutOfOffice")
        @Expose
        private String outOfOffice;
        @SerializedName("Training")
        @Expose
        private String training;
        @SerializedName("RequestForDesk")
        @Expose
        private String requestForDesk;
        @SerializedName("TypeSelectTitle")
        @Expose
        private String typeSelectTitle;
        @SerializedName("DeskSelectTitle")
        @Expose
        private String deskSelectTitle;
        @SerializedName("ProjectSelectTitle")
        @Expose
        private String projectSelectTitle;
        @SerializedName("TimeFromPlaceHolder")
        @Expose
        private String timeFromPlaceHolder;
        @SerializedName("TimeToPlaceHolder")
        @Expose
        private String timeToPlaceHolder;
        @SerializedName("DeskPlaceHolder")
        @Expose
        private String deskPlaceHolder;
        @SerializedName("ProjectPlaceHolder")
        @Expose
        private String projectPlaceHolder;
        @SerializedName("Desks")
        @Expose
        private String desks;
        @SerializedName("NoBooking")
        @Expose
        private String noBooking;
        @SerializedName("Types")
        @Expose
        private String types;
        @SerializedName("InvalidTimeZoneId")
        @Expose
        private String invalidTimeZoneId;
        @SerializedName("InvalidTimePeriod")
        @Expose
        private String invalidTimePeriod;
        @SerializedName("TimeOverlap")
        @Expose
        private String TimeOverlap;
        @SerializedName("COVID_SYMPTOMS")
        @Expose
        private String COVID_SYMPTOMS;
        @SerializedName("InvalidDeskBooking")
        @Expose
        private String invalidDeskBooking;
        @SerializedName("InvalidRequest")
        @Expose
        private String invalidRequest;
        @SerializedName("UserTimeOverlap")
        @Expose
        private String userTimeOverlap;
        @SerializedName("InvalidUsageType")
        @Expose
        private String invalidUsageType;
        @SerializedName("InvalidFrom")
        @Expose
        private String invalidFrom;
        @SerializedName("InvalidTo")
        @Expose
        private String invalidTo;
        @SerializedName("InvalidTeamDeskId")
        @Expose
        private String invalidTeamDeskId;
        @SerializedName("TimePassed")
        @Expose
        private String timePassed;
        @SerializedName("DeskUnavailable")
        @Expose
        private String deskUnavailable;
        @SerializedName("BookingSaved")
        @Expose
        private String bookingSaved;
        @SerializedName("DefaultError")
        @Expose
        private String defaultError;
        @SerializedName("DeskZoneMaxUtilization")
        @Expose
        private String deskZoneMaxUtilization;
        @SerializedName("FloorMaxUtilization")
        @Expose
        private String floorMaxUtilization;
        @SerializedName("NotAvailableForRequest")
        @Expose
        private String NotAvailableForRequest;
        @SerializedName("AvailableForRequest")
        @Expose
        private String AvailableForRequest;
        @SerializedName("PastDate")
        @Expose
        private String PastDate;

        public String getPastDate() {
            return PastDate;
        }

        public void setPastDate(String pastDate) {
            PastDate = pastDate;
        }

        public String getNotAvailableForRequest() {
            return NotAvailableForRequest;
        }

        public void setNotAvailableForRequest(String notAvailableForRequest) {
            NotAvailableForRequest = notAvailableForRequest;
        }

        public String getAvailableForRequest() {
            return AvailableForRequest;
        }

        public void setAvailableForRequest(String availableForRequest) {
            AvailableForRequest = availableForRequest;
        }

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getAlternateOffice() {
            return alternateOffice;
        }

        public void setAlternateOffice(String alternateOffice) {
            this.alternateOffice = alternateOffice;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getInOffice() {
            return inOffice;
        }

        public void setInOffice(String inOffice) {
            this.inOffice = inOffice;
        }

        public String getWorkingFromHome() {
            return workingFromHome;
        }

        public void setWorkingFromHome(String workingFromHome) {
            this.workingFromHome = workingFromHome;
        }

        public String getOutOfOffice() {
            return outOfOffice;
        }

        public void setOutOfOffice(String outOfOffice) {
            this.outOfOffice = outOfOffice;
        }

        public String getTraining() {
            return training;
        }

        public void setTraining(String training) {
            this.training = training;
        }

        public String getRequestForDesk() {
            return requestForDesk;
        }

        public void setRequestForDesk(String requestForDesk) {
            this.requestForDesk = requestForDesk;
        }

        public String getTypeSelectTitle() {
            return typeSelectTitle;
        }

        public void setTypeSelectTitle(String typeSelectTitle) {
            this.typeSelectTitle = typeSelectTitle;
        }

        public String getDeskSelectTitle() {
            return deskSelectTitle;
        }

        public void setDeskSelectTitle(String deskSelectTitle) {
            this.deskSelectTitle = deskSelectTitle;
        }

        public String getProjectSelectTitle() {
            return projectSelectTitle;
        }

        public void setProjectSelectTitle(String projectSelectTitle) {
            this.projectSelectTitle = projectSelectTitle;
        }

        public String getTimeFromPlaceHolder() {
            return timeFromPlaceHolder;
        }

        public void setTimeFromPlaceHolder(String timeFromPlaceHolder) {
            this.timeFromPlaceHolder = timeFromPlaceHolder;
        }

        public String getTimeToPlaceHolder() {
            return timeToPlaceHolder;
        }

        public void setTimeToPlaceHolder(String timeToPlaceHolder) {
            this.timeToPlaceHolder = timeToPlaceHolder;
        }

        public String getDeskPlaceHolder() {
            return deskPlaceHolder;
        }

        public void setDeskPlaceHolder(String deskPlaceHolder) {
            this.deskPlaceHolder = deskPlaceHolder;
        }

        public String getProjectPlaceHolder() {
            return projectPlaceHolder;
        }

        public void setProjectPlaceHolder(String projectPlaceHolder) {
            this.projectPlaceHolder = projectPlaceHolder;
        }

        public String getDesks() {
            return desks;
        }

        public void setDesks(String desks) {
            this.desks = desks;
        }

        public String getNoBooking() {
            return noBooking;
        }

        public void setNoBooking(String noBooking) {
            this.noBooking = noBooking;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getInvalidTimeZoneId() {
            return invalidTimeZoneId;
        }

        public void setInvalidTimeZoneId(String invalidTimeZoneId) {
            this.invalidTimeZoneId = invalidTimeZoneId;
        }

        public String getCOVID_SYMPTOMS() {
            return COVID_SYMPTOMS;
        }

        public void setCOVID_SYMPTOMS(String COVID_SYMPTOMS) {
            this.COVID_SYMPTOMS = COVID_SYMPTOMS;
        }

        public String getTimeOverlap() {
            return TimeOverlap;
        }

        public void setTimeOverlap(String timeOverlap) {
            TimeOverlap = timeOverlap;
        }

        public String getInvalidTimePeriod() {
            return invalidTimePeriod;
        }

        public void setInvalidTimePeriod(String invalidTimePeriod) {
            this.invalidTimePeriod = invalidTimePeriod;
        }

        public String getInvalidDeskBooking() {
            return invalidDeskBooking;
        }

        public void setInvalidDeskBooking(String invalidDeskBooking) {
            this.invalidDeskBooking = invalidDeskBooking;
        }

        public String getInvalidRequest() {
            return invalidRequest;
        }

        public void setInvalidRequest(String invalidRequest) {
            this.invalidRequest = invalidRequest;
        }

        public String getUserTimeOverlap() {
            return userTimeOverlap;
        }

        public void setUserTimeOverlap(String userTimeOverlap) {
            this.userTimeOverlap = userTimeOverlap;
        }

        public String getInvalidUsageType() {
            return invalidUsageType;
        }

        public void setInvalidUsageType(String invalidUsageType) {
            this.invalidUsageType = invalidUsageType;
        }

        public String getInvalidFrom() {
            return invalidFrom;
        }

        public void setInvalidFrom(String invalidFrom) {
            this.invalidFrom = invalidFrom;
        }

        public String getInvalidTo() {
            return invalidTo;
        }

        public void setInvalidTo(String invalidTo) {
            this.invalidTo = invalidTo;
        }

        public String getInvalidTeamDeskId() {
            return invalidTeamDeskId;
        }

        public void setInvalidTeamDeskId(String invalidTeamDeskId) {
            this.invalidTeamDeskId = invalidTeamDeskId;
        }

        public String getTimePassed() {
            return timePassed;
        }

        public void setTimePassed(String timePassed) {
            this.timePassed = timePassed;
        }

        public String getDeskUnavailable() {
            return deskUnavailable;
        }

        public void setDeskUnavailable(String deskUnavailable) {
            this.deskUnavailable = deskUnavailable;
        }

        public String getBookingSaved() {
            return bookingSaved;
        }

        public void setBookingSaved(String bookingSaved) {
            this.bookingSaved = bookingSaved;
        }

        public String getDefaultError() {
            return defaultError;
        }

        public void setDefaultError(String defaultError) {
            this.defaultError = defaultError;
        }

        public String getDeskZoneMaxUtilization() {
            return deskZoneMaxUtilization;
        }

        public void setDeskZoneMaxUtilization(String deskZoneMaxUtilization) {
            this.deskZoneMaxUtilization = deskZoneMaxUtilization;
        }

        public String getFloorMaxUtilization() {
            return floorMaxUtilization;
        }

        public void setFloorMaxUtilization(String floorMaxUtilization) {
            this.floorMaxUtilization = floorMaxUtilization;
        }

    }

    public class CovidSelfCertification {


    }

    public class PersonalHelp {

        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Introduction")
        @Expose
        private String introduction;
        @SerializedName("DescribeInfo")
        @Expose
        private String describeInfo;
        @SerializedName("PlaceHolder")
        @Expose
        private String placeHolder;
        @SerializedName("SubmitSuccess")
        @Expose
        private String submitSuccess;
        @SerializedName("SubmitFailed")
        @Expose
        private String submitFailed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getDescribeInfo() {
            return describeInfo;
        }

        public void setDescribeInfo(String describeInfo) {
            this.describeInfo = describeInfo;
        }

        public String getPlaceHolder() {
            return placeHolder;
        }

        public void setPlaceHolder(String placeHolder) {
            this.placeHolder = placeHolder;
        }

        public String getSubmitSuccess() {
            return submitSuccess;
        }

        public void setSubmitSuccess(String submitSuccess) {
            this.submitSuccess = submitSuccess;
        }

        public String getSubmitFailed() {
            return submitFailed;
        }

        public void setSubmitFailed(String submitFailed) {
            this.submitFailed = submitFailed;
        }

    }

    public class Approvals {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("PastApprovals")
        @Expose
        private String pastApprovals;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getPastApprovals() {
            return pastApprovals;
        }

        public void setPastApprovals(String pastApprovals) {
            this.pastApprovals = pastApprovals;
        }

    }

    public class Requests {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("PastRequests")
        @Expose
        private String pastRequests;
        @SerializedName("RequestedBy")
        @Expose
        private String requestedBy;
        @SerializedName("DesksAvailable")
        @Expose
        private String desksAvailable;
        @SerializedName("RequestData")
        @Expose
        private String requestData;
        @SerializedName("ApprovedBy")
        @Expose
        private String approvedBy;
        @SerializedName("RejectedBy")
        @Expose
        private String rejectedBy;
        @SerializedName("RequestApprovedText")
        @Expose
        private String requestApprovedText;
        @SerializedName("RequestRejectedText")
        @Expose
        private String requestRejectedText;
        @SerializedName("RequestedOn")
        @Expose
        private String requestedOn;
        @SerializedName("RejectComments")
        @Expose
        private String rejectComments;
        @SerializedName("Reject")
        @Expose
        private String reject;
        @SerializedName("Cancel")
        @Expose
        private String cancel;
        @SerializedName("YourComment")
        @Expose
        private String yourComment;
        @SerializedName("Comment")
        @Expose
        private String comment;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getPastRequests() {
            return pastRequests;
        }

        public void setPastRequests(String pastRequests) {
            this.pastRequests = pastRequests;
        }

        public String getRequestedBy() {
            return requestedBy;
        }

        public void setRequestedBy(String requestedBy) {
            this.requestedBy = requestedBy;
        }

        public String getDesksAvailable() {
            return desksAvailable;
        }

        public void setDesksAvailable(String desksAvailable) {
            this.desksAvailable = desksAvailable;
        }

        public String getRequestData() {
            return requestData;
        }

        public void setRequestData(String requestData) {
            this.requestData = requestData;
        }

        public String getApprovedBy() {
            return approvedBy;
        }

        public void setApprovedBy(String approvedBy) {
            this.approvedBy = approvedBy;
        }

        public String getRejectedBy() {
            return rejectedBy;
        }

        public void setRejectedBy(String rejectedBy) {
            this.rejectedBy = rejectedBy;
        }

        public String getRequestApprovedText() {
            return requestApprovedText;
        }

        public void setRequestApprovedText(String requestApprovedText) {
            this.requestApprovedText = requestApprovedText;
        }

        public String getRequestRejectedText() {
            return requestRejectedText;
        }

        public void setRequestRejectedText(String requestRejectedText) {
            this.requestRejectedText = requestRejectedText;
        }

        public String getRequestedOn() {
            return requestedOn;
        }

        public void setRequestedOn(String requestedOn) {
            this.requestedOn = requestedOn;
        }

        public String getRejectComments() {
            return rejectComments;
        }

        public void setRejectComments(String rejectComments) {
            this.rejectComments = rejectComments;
        }

        public String getReject() {
            return reject;
        }

        public void setReject(String reject) {
            this.reject = reject;
        }

        public String getCancel() {
            return cancel;
        }

        public void setCancel(String cancel) {
            this.cancel = cancel;
        }

        public String getYourComment() {
            return yourComment;
        }

        public void setYourComment(String yourComment) {
            this.yourComment = yourComment;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }

    public class MeetingRooms {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("Room")
        @Expose
        private String room;
        @SerializedName("Invite")
        @Expose
        private String invite;
        @SerializedName("Participants")
        @Expose
        private String participants;
        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("RoomsAvailable")
        @Expose
        private String roomsAvailable;
        @SerializedName("RoomsRequirements")
        @Expose
        private String roomsRequirements;
        @SerializedName("ChooseNumberOfPeople")
        @Expose
        private String chooseNumberOfPeople;
        @SerializedName("NoBooking")
        @Expose
        private String noBooking;
        @SerializedName("ChooseLocationPlaceholder")
        @Expose
        private String chooseLocationPlaceholder;
        @SerializedName("ChooseRoomPlaceholder")
        @Expose
        private String chooseRoomPlaceholder;
        @SerializedName("ExternalAttendees")
        @Expose
        private String externalAttendees;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }

        public String getParticipants() {
            return participants;
        }

        public void setParticipants(String participants) {
            this.participants = participants;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getRoomsAvailable() {
            return roomsAvailable;
        }

        public void setRoomsAvailable(String roomsAvailable) {
            this.roomsAvailable = roomsAvailable;
        }

        public String getRoomsRequirements() {
            return roomsRequirements;
        }

        public void setRoomsRequirements(String roomsRequirements) {
            this.roomsRequirements = roomsRequirements;
        }

        public String getChooseNumberOfPeople() {
            return chooseNumberOfPeople;
        }

        public void setChooseNumberOfPeople(String chooseNumberOfPeople) {
            this.chooseNumberOfPeople = chooseNumberOfPeople;
        }

        public String getNoBooking() {
            return noBooking;
        }

        public void setNoBooking(String noBooking) {
            this.noBooking = noBooking;
        }

        public String getChooseLocationPlaceholder() {
            return chooseLocationPlaceholder;
        }

        public void setChooseLocationPlaceholder(String chooseLocationPlaceholder) {
            this.chooseLocationPlaceholder = chooseLocationPlaceholder;
        }

        public String getChooseRoomPlaceholder() {
            return chooseRoomPlaceholder;
        }

        public void setChooseRoomPlaceholder(String chooseRoomPlaceholder) {
            this.chooseRoomPlaceholder = chooseRoomPlaceholder;
        }

        public String getExternalAttendees() {
            return externalAttendees;
        }

        public void setExternalAttendees(String externalAttendees) {
            this.externalAttendees = externalAttendees;
        }

    }


    public class CarPark {

        @SerializedName("TabTitle")
        @Expose
        private String tabTitle;
        @SerializedName("ParkingSlot")
        @Expose
        private String parkingSlot;
        @SerializedName("RegistrationNumber")
        @Expose
        private String registrationNumber;
        @SerializedName("Nobookings")
        @Expose
        private String nobookings;
        @SerializedName("Parking")
        @Expose
        private String parking;
        @SerializedName("ParkingRequest")
        @Expose
        private String parkingRequest;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }

        public String getParkingSlot() {
            return parkingSlot;
        }

        public void setParkingSlot(String parkingSlot) {
            this.parkingSlot = parkingSlot;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public String getNobookings() {
            return nobookings;
        }

        public void setNobookings(String nobookings) {
            this.nobookings = nobookings;
        }

        public String getParking() {
            return parking;
        }

        public void setParking(String parking) {
            this.parking = parking;
        }

        public String getParkingRequest() {
            return parkingRequest;
        }

        public void setParkingRequest(String parkingRequest) {
            this.parkingRequest = parkingRequest;
        }

    }


    public class ResetPassword {

        @SerializedName("Company")
        @Expose
        private String company;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("SubmittedText")
        @Expose
        private String submittedText;
        @SerializedName("SubmitErrorText")
        @Expose
        private String submitErrorText;
        @SerializedName("TitleText")
        @Expose
        private String titleText;
        @SerializedName("BackToLogin")
        @Expose
        private String backToLogin;
        @SerializedName("UnAuthorized")
        @Expose
        private String unAuthorized;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSubmittedText() {
            return submittedText;
        }

        public void setSubmittedText(String submittedText) {
            this.submittedText = submittedText;
        }

        public String getSubmitErrorText() {
            return submitErrorText;
        }

        public void setSubmitErrorText(String submitErrorText) {
            this.submitErrorText = submitErrorText;
        }

        public String getTitleText() {
            return titleText;
        }

        public void setTitleText(String titleText) {
            this.titleText = titleText;
        }

        public String getBackToLogin() {
            return backToLogin;
        }

        public void setBackToLogin(String backToLogin) {
            this.backToLogin = backToLogin;
        }

        public String getUnAuthorized() {
            return unAuthorized;
        }

        public void setUnAuthorized(String unAuthorized) {
            this.unAuthorized = unAuthorized;
        }

    }


    public class AccountSettings {

        @SerializedName("TitleShow")
        @Expose
        private String titleShow;
        @SerializedName("TitleEdit")
        @Expose
        private String titleEdit;
        @SerializedName("DeskPhone")
        @Expose
        private String deskPhone;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Team")
        @Expose
        private String team;
        @SerializedName("PreferredDesk")
        @Expose
        private String preferredDesk;
        @SerializedName("DefaultZone")
        @Expose
        private String defaultZone;
        @SerializedName("WorkHours")
        @Expose
        private String workHours;
        @SerializedName("WorkHoursStart")
        @Expose
        private String workHoursStart;
        @SerializedName("WorkHoursFinish")
        @Expose
        private String workHoursFinish;
        @SerializedName("SettingsUpdated")
        @Expose
        private String settingsUpdated;
        @SerializedName("SettingUpdateFail")
        @Expose
        private String settingUpdateFail;
        @SerializedName("ProfilePhoto")
        @Expose
        private String profilePhoto;
        @SerializedName("NotificationsSetting")
        @Expose
        private String notificationsSetting;
        @SerializedName("Country")
        @Expose
        private String country;

        public String getTitleShow() {
            return titleShow;
        }

        public void setTitleShow(String titleShow) {
            this.titleShow = titleShow;
        }

        public String getTitleEdit() {
            return titleEdit;
        }

        public void setTitleEdit(String titleEdit) {
            this.titleEdit = titleEdit;
        }

        public String getDeskPhone() {
            return deskPhone;
        }

        public void setDeskPhone(String deskPhone) {
            this.deskPhone = deskPhone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getPreferredDesk() {
            return preferredDesk;
        }

        public void setPreferredDesk(String preferredDesk) {
            this.preferredDesk = preferredDesk;
        }

        public String getDefaultZone() {
            return defaultZone;
        }

        public void setDefaultZone(String defaultZone) {
            this.defaultZone = defaultZone;
        }

        public String getWorkHours() {
            return workHours;
        }

        public void setWorkHours(String workHours) {
            this.workHours = workHours;
        }

        public String getWorkHoursStart() {
            return workHoursStart;
        }

        public void setWorkHoursStart(String workHoursStart) {
            this.workHoursStart = workHoursStart;
        }

        public String getWorkHoursFinish() {
            return workHoursFinish;
        }

        public void setWorkHoursFinish(String workHoursFinish) {
            this.workHoursFinish = workHoursFinish;
        }

        public String getSettingsUpdated() {
            return settingsUpdated;
        }

        public void setSettingsUpdated(String settingsUpdated) {
            this.settingsUpdated = settingsUpdated;
        }

        public String getSettingUpdateFail() {
            return settingUpdateFail;
        }

        public void setSettingUpdateFail(String settingUpdateFail) {
            this.settingUpdateFail = settingUpdateFail;
        }

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getNotificationsSetting() {
            return notificationsSetting;
        }

        public void setNotificationsSetting(String notificationsSetting) {
            this.notificationsSetting = notificationsSetting;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public class CommonTitles {

        @SerializedName("LocateTabTitle")
        @Expose
        private String locateTabTitle;
        @SerializedName("HolidayTabTitle")
        @Expose
        private String holidayTabTitle;
        @SerializedName("AppraisalTabTitle")
        @Expose
        private String appraisalTabTitle;
        @SerializedName("DocsTabTitle")
        @Expose
        private String docsTabTitle;
        @SerializedName("KudosTabTitle")
        @Expose
        private String kudosTabTitle;

        public String getLocateTabTitle() {
            return locateTabTitle;
        }

        public void setLocateTabTitle(String locateTabTitle) {
            this.locateTabTitle = locateTabTitle;
        }

        public String getHolidayTabTitle() {
            return holidayTabTitle;
        }

        public void setHolidayTabTitle(String holidayTabTitle) {
            this.holidayTabTitle = holidayTabTitle;
        }

        public String getAppraisalTabTitle() {
            return appraisalTabTitle;
        }

        public void setAppraisalTabTitle(String appraisalTabTitle) {
            this.appraisalTabTitle = appraisalTabTitle;
        }

        public String getDocsTabTitle() {
            return docsTabTitle;
        }

        public void setDocsTabTitle(String docsTabTitle) {
            this.docsTabTitle = docsTabTitle;
        }

        public String getKudosTabTitle() {
            return kudosTabTitle;
        }

        public void setKudosTabTitle(String kudosTabTitle) {
            this.kudosTabTitle = kudosTabTitle;
        }

    }


    public class Search {

        @SerializedName("BarPlaceHolder")
        @Expose
        private String barPlaceHolder;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Team")
        @Expose
        private String team;
        @SerializedName("DeskPhone")
        @Expose
        private String deskPhone;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Desk")
        @Expose
        private String desk;

        public String getBarPlaceHolder() {
            return barPlaceHolder;
        }

        public void setBarPlaceHolder(String barPlaceHolder) {
            this.barPlaceHolder = barPlaceHolder;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getDeskPhone() {
            return deskPhone;
        }

        public void setDeskPhone(String deskPhone) {
            this.deskPhone = deskPhone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    }

    public class AppFeedback {

        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Introduction")
        @Expose
        private String introduction;
        @SerializedName("Placeholder")
        @Expose
        private String placeholder;
        @SerializedName("SubmitSuccess")
        @Expose
        private String submitSuccess;
        @SerializedName("SubmitFailed")
        @Expose
        private String submitFailed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getSubmitSuccess() {
            return submitSuccess;
        }

        public void setSubmitSuccess(String submitSuccess) {
            this.submitSuccess = submitSuccess;
        }

        public String getSubmitFailed() {
            return submitFailed;
        }

        public void setSubmitFailed(String submitFailed) {
            this.submitFailed = submitFailed;
        }

    }


    public class ActionOverLays {

        @SerializedName("SuccessfullyEdited")
        @Expose
        private String successfullyEdited;
        @SerializedName("SuccessfullyBooked")
        @Expose
        private String successfullyBooked;
        @SerializedName("SuccessfullyDeleted")
        @Expose
        private String successfullyDeleted;
        @SerializedName("CheckedIn")
        @Expose
        private String checkedIn;
        @SerializedName("CheckedOut")
        @Expose
        private String checkedOut;

        public String getSuccessfullyEdited() {
            return successfullyEdited;
        }

        public void setSuccessfullyEdited(String successfullyEdited) {
            this.successfullyEdited = successfullyEdited;
        }

        public String getSuccessfullyBooked() {
            return successfullyBooked;
        }

        public void setSuccessfullyBooked(String successfullyBooked) {
            this.successfullyBooked = successfullyBooked;
        }

        public String getSuccessfullyDeleted() {
            return successfullyDeleted;
        }

        public void setSuccessfullyDeleted(String successfullyDeleted) {
            this.successfullyDeleted = successfullyDeleted;
        }

        public String getCheckedIn() {
            return checkedIn;
        }

        public void setCheckedIn(String checkedIn) {
            this.checkedIn = checkedIn;
        }

        public String getCheckedOut() {
            return checkedOut;
        }

        public void setCheckedOut(String checkedOut) {
            this.checkedOut = checkedOut;
        }

    }


    public class Settings {

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
        @SerializedName("SetUpBiometric")
        @Expose
        private String setUpBiometric;
        @SerializedName("ResetPassword")
        @Expose
        private String resetPassword;
        @SerializedName("ReportAnIssue")
        @Expose
        private String reportAnIssue;
        @SerializedName("LogOut")
        @Expose
        private String logOut;
        @SerializedName("Default")
        @Expose
        private String _default;
        @SerializedName("ViewProfile")
        @Expose
        private String viewProfile;
        @SerializedName("Security")
        @Expose
        private String security;
        @SerializedName("ChangeOrganisation")
        @Expose
        private String changeOrganisation;
        @SerializedName("SetUpPin")
        @Expose
        private String setUpPin;
        @SerializedName("Feedback")
        @Expose
        private String feedback;
        @SerializedName("Whatsnew")
        @Expose
        private String whatsnew;
        @SerializedName("Help")
        @Expose
        private String help;
        @SerializedName("About")
        @Expose
        private String about;
        @SerializedName("DarkMode")
        @Expose
        private String darkMode;
        @SerializedName("SetUpPinEditPin")
        @Expose
        private String SetUpPinEditPin;

        public String getSetUpPinEditPin() {
            return SetUpPinEditPin;
        }

        public void setSetUpPinEditPin(String setUpPinEditPin) {
            SetUpPinEditPin = setUpPinEditPin;
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

        public String getSecurity() {
            return security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public String getChangeOrganisation() {
            return changeOrganisation;
        }

        public void setChangeOrganisation(String changeOrganisation) {
            this.changeOrganisation = changeOrganisation;
        }

        public String getSetUpPin() {
            return setUpPin;
        }

        public void setSetUpPin(String setUpPin) {
            this.setUpPin = setUpPin;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getWhatsnew() {
            return whatsnew;
        }

        public void setWhatsnew(String whatsnew) {
            this.whatsnew = whatsnew;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getDarkMode() {
            return darkMode;
        }

        public void setDarkMode(String darkMode) {
            this.darkMode = darkMode;
        }

    }


    public class AppKeys {

        @SerializedName("SignIn")
        @Expose
        private String signIn;
        @SerializedName("SignInWithPin")
        @Expose
        private String signInWithPin;
        @SerializedName("SignInWithSso")
        @Expose
        private String signInWithSso;
        @SerializedName("Tenant")
        @Expose
        private String tenant;
        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("ForgotPassword")
        @Expose
        private String forgotPassword;
        @SerializedName("OrSignInWith")
        @Expose
        private String orSignInWith;
        @SerializedName("TenantName")
        @Expose
        private String tenantName;
        @SerializedName("PastEvents")
        @Expose
        private String PastEvents;
        @SerializedName("EmailAddress")
        @Expose
        private String emailAddress;
        @SerializedName("SubmitPasswordReset")
        @Expose
        private String submitPasswordReset;
        @SerializedName("GoBackToSignIn")
        @Expose
        private String goBackToSignIn;
        @SerializedName("EnterYourPin")
        @Expose
        private String enterYourPin;
        @SerializedName("UseEmailPassword")
        @Expose
        private String useEmailPassword;
        @SerializedName("Locate")
        @Expose
        private String locate;
        @SerializedName("Home")
        @Expose
        private String home;
        @SerializedName("WellBeing")
        @Expose
        private String wellBeing;
        @SerializedName("Search")
        @Expose
        private String search;
        @SerializedName("Cancel")
        @Expose
        private String cancel;
        @SerializedName("Apply")
        @Expose
        private String apply;
        @SerializedName("ChooseLocation")
        @Expose
        private String chooseLocation;
        @SerializedName("Start")
        @Expose
        private String start;
        @SerializedName("End")
        @Expose
        private String end;
        @SerializedName("Available")
        @Expose
        private String available;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Repeat")
        @Expose
        private String repeat;
        @SerializedName("Book")
        @Expose
        private String book;
        @SerializedName("SelectDate")
        @Expose
        private String selectDate;
        @SerializedName("Back")
        @Expose
        private String back;
        @SerializedName("Continue")
        @Expose
        private String _continue;
        @SerializedName("None")
        @Expose
        private String none;
        @SerializedName("Daily")
        @Expose
        private String daily;
        @SerializedName("Weekly")
        @Expose
        private String weekly;
        @SerializedName("Monthly")
        @Expose
        private String monthly;
        @SerializedName("Yearly")
        @Expose
        private String yearly;
        @SerializedName("Interval")
        @Expose
        private String interval;
        @SerializedName("Until")
        @Expose
        private String until;
        @SerializedName("OnDays")
        @Expose
        private String onDays;
        @SerializedName("Forever")
        @Expose
        private String forever;
        @SerializedName("AllDays")
        @Expose
        private String allDays;
        @SerializedName("MYTeam")
        @Expose
        private String mYTeam;
        @SerializedName("AllTeams")
        @Expose
        private String allTeams;
        @SerializedName("Skip")
        @Expose
        private String skip;
        @SerializedName("Filters")
        @Expose
        private String filters;
        @SerializedName("WorkSpaces")
        @Expose
        private String workSpaces;
        @SerializedName("Rooms")
        @Expose
        private String rooms;
        @SerializedName("Parkings")
        @Expose
        private String parkings;
        @SerializedName("Monitor")
        @Expose
        private String monitor;
        @SerializedName("AdjustableHeight")
        @Expose
        private String adjustableHeight;
        @SerializedName("LaptopStand")
        @Expose
        private String laptopStand;
        @SerializedName("USBCDock")
        @Expose
        private String uSBCDock;
        @SerializedName("ChargerPoint")
        @Expose
        private String chargerPoint;
        @SerializedName("StandingDesk")
        @Expose
        private String standingDesk;
        @SerializedName("ByRequest")
        @Expose
        private String byRequest;
        @SerializedName("Booked")
        @Expose
        private String booked;
        @SerializedName("BookedByMe")
        @Expose
        private String bookedByMe;
        @SerializedName("UnAvilable")
        @Expose
        private String unAvilable;
        @SerializedName("Teams")
        @Expose
        private String teams;
        @SerializedName("EndTime")
        @Expose
        private String endTime;
        @SerializedName("EndOfDay")
        @Expose
        private String endOfDay;
        @SerializedName("Now")
        @Expose
        private String now;
        @SerializedName("Tomorrow")
        @Expose
        private String tomorrow;
        @SerializedName("NextWeek")
        @Expose
        private String nextWeek;
        @SerializedName("Yesterday")
        @Expose
        private String yesterday;
        @SerializedName("Today")
        @Expose
        private String today;
        @SerializedName("DaysAgo")
        @Expose
        private String daysAgo;
        @SerializedName("Days")
        @Expose
        private String days;
        @SerializedName("In")
        @Expose
        private String in;
        @SerializedName("WorkSpace")
        @Expose
        private String workSpace;
        @SerializedName("Room")
        @Expose
        private String room;
        @SerializedName("Parking")
        @Expose
        private String parking;
        @SerializedName("Remote")
        @Expose
        private String Remote;
        @SerializedName("Sick")
        @Expose
        private String Sick;
        @SerializedName("Holiday")
        @Expose
        private String Holiday;
        @SerializedName("TrainingStr")
        @Expose
        private String TrainingStr;
        @SerializedName("More")
        @Expose
        private String more;
        @SerializedName("BookWorkSpace")
        @Expose
        private String bookWorkSpace;
        @SerializedName("workingRemotely")
        @Expose
        private String workingRemotely;
        @SerializedName("logSickness")
        @Expose
        private String logSickness;
        @SerializedName("BookHoliday")
        @Expose
        private String BookHoliday;
        @SerializedName("BookTraining")
        @Expose
        private String BookTraining;
        @SerializedName("BookRoom")
        @Expose
        private String bookRoom;
        @SerializedName("BookParking")
        @Expose
        private String bookParking;
        @SerializedName("TypeBook")
        @Expose
        private String typeBook;
        @SerializedName("EditYourBooking")
        @Expose
        private String editYourBooking;
        @SerializedName("Select")
        @Expose
        private String select;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("AvailableDesk")
        @Expose
        private String availableDesk;
        @SerializedName("ExpandAll")
        @Expose
        private String expandAll;
        @SerializedName("DesksAvailable")
        @Expose
        private String desksAvailable;
        @SerializedName("Profile")
        @Expose
        private String profile;
        @SerializedName("Schedule")
        @Expose
        private String schedule;
        @SerializedName("UpcomingBookings")
        @Expose
        private String upcomingBookings;
        @SerializedName("ViewAll")
        @Expose
        private String viewAll;
        @SerializedName("Contact")
        @Expose
        private String contact;
        @SerializedName("ViewOlder")
        @Expose
        private String viewOlder;
        @SerializedName("HealthAndSafety")
        @Expose
        private String healthAndSafety;
        @SerializedName("HealthTips")
        @Expose
        private String healthTips;
        @SerializedName("Menu")
        @Expose
        private String menu;
        @SerializedName("FireWardens")
        @Expose
        private String fireWardens;
        @SerializedName("FirstAid")
        @Expose
        private String firstAid;
        @SerializedName("MentalHealth")
        @Expose
        private String mentalHealth;
        @SerializedName("HumanResources")
        @Expose
        private String humanResources;
        @SerializedName("Leave")
        @Expose
        private String leave;
        @SerializedName("Places")
        @Expose
        private String places;
        @SerializedName("ReportAnIssue")
        @Expose
        private String reportAnIssue;
        @SerializedName("Benefits")
        @Expose
        private String benefits;
        @SerializedName("Training")
        @Expose
        private String training;
        @SerializedName("Notices")
        @Expose
        private String notices;
        @SerializedName("WorkSpaceSurvey")
        @Expose
        private String workSpaceSurvey;
        @SerializedName("WorkSpaceAssessment")
        @Expose
        private String workSpaceAssessment;
        @SerializedName("Covid")
        @Expose
        private String covid;
        @SerializedName("PersonalHelp")
        @Expose
        private String personalHelp;
        @SerializedName("Events")
        @Expose
        private String events;
        @SerializedName("ReportHazard")
        @Expose
        private String reportHazard;
        @SerializedName("EvacuationPlan")
        @Expose
        private String evacuationPlan;
        @SerializedName("YourFireWardens")
        @Expose
        private String yourFireWardens;
        @SerializedName("YourFirstAiders")
        @Expose
        private String yourFirstAiders;
        @SerializedName("YourMentalHealthAdvocates")
        @Expose
        private String yourMentalHealthAdvocates;
        @SerializedName("InOffice")
        @Expose
        private String inOffice;
        @SerializedName("Links")
        @Expose
        private String links;
        @SerializedName("HolidayRemaining")
        @Expose
        private String holidayRemaining;
        @SerializedName("UpcomingLeave")
        @Expose
        private String upcomingLeave;
        @SerializedName("History")
        @Expose
        private String history;
        @SerializedName("Duration")
        @Expose
        private String duration;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("LeaveType")
        @Expose
        private String leaveType;
        @SerializedName("Upcoming")
        @Expose
        private String upcoming;
        @SerializedName("Past")
        @Expose
        private String past;
        @SerializedName("RequestTimeOff")
        @Expose
        private String requestTimeOff;
        @SerializedName("ReportIssueTitle")
        @Expose
        private String reportIssueTitle;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("PastBooking")
        @Expose
        private String pastBooking;
        @SerializedName("DateApplicable")
        @Expose
        private String dateApplicable;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Add")
        @Expose
        private String add;
        @SerializedName("From")
        @Expose
        private String from;
        @SerializedName("To")
        @Expose
        private String to;
        @SerializedName("Topic")
        @Expose
        private String topic;
        @SerializedName("Submit")
        @Expose
        private String submit;
        @SerializedName("Unavailable")
        @Expose
        private String unavailable;
        @SerializedName("UnavailableDeskTitle")
        @Expose
        private String unavailableDeskTitle;
        @SerializedName("GlobalLocation")
        @Expose
        private String globalLocation;
        @SerializedName("Team")
        @Expose
        private String team;
        @SerializedName("Car")
        @Expose
        private String car;
        @SerializedName("Transport")
        @Expose
        private String transport;
        @SerializedName("TaxiUber")
        @Expose
        private String taxiUber;
        @SerializedName("Bike")
        @Expose
        private String bike;
        @SerializedName("Walk")
        @Expose
        private String walk;
        @SerializedName("WorkFromHome")
        @Expose
        private String workFromHome;
        @SerializedName("Yes")
        @Expose
        private String yes;
        @SerializedName("No")
        @Expose
        private String no;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("ViewTeam")
        @Expose
        private String viewTeam;
        @SerializedName("CovidTransportMode")
        @Expose
        private String covidTransportMode;
        @SerializedName("Questions")
        @Expose
        private String questions;
        @SerializedName("BookedBySomeone")
        @Expose
        private String bookedBySomeone;
        @SerializedName("Request")
        @Expose
        private String request;
        @SerializedName("StartTime")
        @Expose
        private String startTime;
        @SerializedName("AddNew")
        @Expose
        private String addNew;
        @SerializedName("ActiveBookings")
        @Expose
        private String activeBookings;
        @SerializedName("NoActiveBookings")
        @Expose
        private String noActiveBookings;
        @SerializedName("Notifications")
        @Expose
        private String notifications;
        @SerializedName("New")
        @Expose
        private String _new;
        @SerializedName("Week")
        @Expose
        private String week;
        @SerializedName("Weeks")
        @Expose
        private String weeks;
        @SerializedName("Month")
        @Expose
        private String month;
        @SerializedName("Months")
        @Expose
        private String months;
        @SerializedName("Year")
        @Expose
        private String year;
        @SerializedName("Years")
        @Expose
        private String years;
        @SerializedName("Day")
        @Expose
        private String day;
        @SerializedName("WeekDays")
        @Expose
        private String weekDays;
        @SerializedName("ConfigureMailAlert")
        @Expose
        private String configureMailAlert;
        @SerializedName("Monday")
        @Expose
        private String monday;
        @SerializedName("Tuesday")
        @Expose
        private String tuesday;
        @SerializedName("Wednesday")
        @Expose
        private String wednesday;
        @SerializedName("Thursday")
        @Expose
        private String thursday;
        @SerializedName("Friday")
        @Expose
        private String friday;
        @SerializedName("Saturday")
        @Expose
        private String saturday;
        @SerializedName("Sunday")
        @Expose
        private String sunday;
        @SerializedName("CheckedIn")
        @Expose
        private String checkedIn;
        @SerializedName("CheckedOut")
        @Expose
        private String checkedOut;
        @SerializedName("CheckInSuccessful")
        @Expose
        private String checkInSuccessful;
        @SerializedName("CheckOutSuccessful")
        @Expose
        private String checkOutSuccessful;
        @SerializedName("BookingCreated")
        @Expose
        private String bookingCreated;
        @SerializedName("BookingUpdated")
        @Expose
        private String bookingUpdated;
        @SerializedName("BookingDeleted")
        @Expose
        private String bookingDeleted;
        @SerializedName("SomethingWentWrong")
        @Expose
        private String somethingWentWrong;
        @SerializedName("Close")
        @Expose
        private String close;
        @SerializedName("InsertActionTitle")
        @Expose
        private String insertActionTitle;
        @SerializedName("DeskPhone")
        @Expose
        private String deskPhone;
        @SerializedName("Country")
        @Expose
        private String country;
        @SerializedName("VehicleNumber")
        @Expose
        private String vehicleNumber;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("DisplayName")
        @Expose
        private String displayName;
        @SerializedName("DefaultWorkingHours")
        @Expose
        private String defaultWorkingHours;
        @SerializedName("DefaultLocation")
        @Expose
        private String defaultLocation;
        @SerializedName("DefaultAssets")
        @Expose
        private String defaultAssets;
        @SerializedName("Edit")
        @Expose
        private String edit;
        @SerializedName("Delete")
        @Expose
        private String delete;
        @SerializedName("Reject")
        @Expose
        private String reject;
        @SerializedName("InsertYourComment")
        @Expose
        private String insertYourComment;
        @SerializedName("Change")
        @Expose
        private String change;
        @SerializedName("UploadPhoto")
        @Expose
        private String uploadPhoto;
        @SerializedName("HalfDay")
        @Expose
        private String halfDay;
        @SerializedName("Note")
        @Expose
        private String note;
        @SerializedName("SendRequest")
        @Expose
        private String sendRequest;
        @SerializedName("TimeOffType")
        @Expose
        private String timeOffType;
        @SerializedName("AnnualLeave")
        @Expose
        private String annualLeave;
        @SerializedName("SickLeave")
        @Expose
        private String sickLeave;
        @SerializedName("FamilyCompassionateLeave")
        @Expose
        private String familyCompassionateLeave;
        @SerializedName("MedicalAppointment")
        @Expose
        private String medicalAppointment;
        @SerializedName("Other")
        @Expose
        private String other;
        @SerializedName("Welcome")
        @Expose
        private String welcome;
        @SerializedName("CompanyNameNotEmptyAlert")
        @Expose
        private String companyNameNotEmptyAlert;
        @SerializedName("EmailNotEmptyAlert")
        @Expose
        private String emailNotEmptyAlert;
        @SerializedName("InvalidEmail")
        @Expose
        private String invalidEmail;
        @SerializedName("PasswordNotEmptyAlert")
        @Expose
        private String passwordNotEmptyAlert;
        @SerializedName("IncorrectPassword")
        @Expose
        private String incorrectPassword;
        @SerializedName("InvalidLogInDetails")
        @Expose
        private String invalidLogInDetails;
        @SerializedName("PleaseAcceptGDPR")
        @Expose
        private String pleaseAcceptGDPR;
        @SerializedName("TenantNameNotEmptyAlert")
        @Expose
        private String tenantNameNotEmptyAlert;
        @SerializedName("EmailOrCompanyInvalid")
        @Expose
        private String emailOrCompanyInvalid;
        @SerializedName("ResetPasswordAlert")
        @Expose
        private String resetPasswordAlert;
        @SerializedName("IncompletePinEntry")
        @Expose
        private String incompletePinEntry;
        @SerializedName("InvalidPIN")
        @Expose
        private String invalidPIN;
        @SerializedName("EnterYourOldPin")
        @Expose
        private String enterYourOldPin;
        @SerializedName("EnterYourNewPin")
        @Expose
        private String enterYourNewPin;
        @SerializedName("ReEnterNewPin")
        @Expose
        private String reEnterNewPin;
        @SerializedName("PinNotMatched")
        @Expose
        private String pinNotMatched;
        @SerializedName("TenantNameAlert")
        @Expose
        private String tenantNameAlert;
        @SerializedName("PasswordUpdatedAlert")
        @Expose
        private String passwordUpdatedAlert;
        @SerializedName("DurationFrom")
        @Expose
        private String durationFrom;
        @SerializedName("OtherComments")
        @Expose
        private String otherComments;
        @SerializedName("Accessibility")
        @Expose
        private String accessibility;
        @SerializedName("Air")
        @Expose
        private String air;
        @SerializedName("Cleanliness")
        @Expose
        private String cleanliness;
        @SerializedName("Light")
        @Expose
        private String light;
        @SerializedName("Noise")
        @Expose
        private String noise;
        @SerializedName("Temperature")
        @Expose
        private String temperature;
        @SerializedName("Chair")
        @Expose
        private String chair;
        @SerializedName("Desk")
        @Expose
        private String desk;
        @SerializedName("Display")
        @Expose
        private String display;
        @SerializedName("KeyBoard")
        @Expose
        private String keyBoard;
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
        @SerializedName("FromDateAlert")
        @Expose
        private String fromDateAlert;
        @SerializedName("ToDateAlert")
        @Expose
        private String toDateAlert;
        @SerializedName("CommentEmptyAlert")
        @Expose
        private String commentEmptyAlert;
        @SerializedName("ThankYouFeedbackAlert")
        @Expose
        private String thankYouFeedbackAlert;
        @SerializedName("DescriptionEmptyAlert")
        @Expose
        private String descriptionEmptyAlert;
        @SerializedName("CheckMailbox")
        @Expose
        private String checkMailbox;
        @SerializedName("SelectMeetingRoomAlert")
        @Expose
        private String selectMeetingRoomAlert;
        @SerializedName("InvalidQRCode")
        @Expose
        private String invalidQRCode;
        @SerializedName("RequestToRejectAlert")
        @Expose
        private String requestToRejectAlert;
        @SerializedName("RequestToAcceptAlert")
        @Expose
        private String requestToAcceptAlert;
        @SerializedName("RejectRequest")
        @Expose
        private String rejectRequest;
        @SerializedName("AcceptRequest")
        @Expose
        private String acceptRequest;
        @SerializedName("CertificateUpdated")
        @Expose
        private String certificateUpdated;
        @SerializedName("CertificateNotUpdated")
        @Expose
        private String certificateNotUpdated;
        @SerializedName("BookingNotUpdated")
        @Expose
        private String bookingNotUpdated;
        @SerializedName("RegistraionNumberNotEmptyAlert")
        @Expose
        private String registraionNumberNotEmptyAlert;
        @SerializedName("SelectDeskAlert")
        @Expose
        private String selectDeskAlert;
        @SerializedName("WrongDeskAlert")
        @Expose
        private String wrongDeskAlert;
        @SerializedName("BookingNotDeleted")
        @Expose
        private String bookingNotDeleted;
        @SerializedName("FromTimeNotEmptyAlert")
        @Expose
        private String fromTimeNotEmptyAlert;
        @SerializedName("ToTimeNotEmptyAlert")
        @Expose
        private String toTimeNotEmptyAlert;
        @SerializedName("VehicleNoNotEmptyAlert")
        @Expose
        private String vehicleNoNotEmptyAlert;
        @SerializedName("DateNotEemptyAlert")
        @Expose
        private String dateNotEemptyAlert;
        @SerializedName("SubjectNotEmptyAlert")
        @Expose
        private String subjectNotEmptyAlert;
        @SerializedName("ProfileUpdated")
        @Expose
        private String profileUpdated;
        @SerializedName("IssueReported")
        @Expose
        private String issueReported;
        @SerializedName("PersonalHelpTeamAlert")
        @Expose
        private String personalHelpTeamAlert;
        @SerializedName("NotificationUpdated")
        @Expose
        private String notificationUpdated;
        @SerializedName("BookedForYou")
        @Expose
        private String bookedForYou;
        @SerializedName("Ok")
        @Expose
        private String ok;
        @SerializedName("SelectImageTitle")
        @Expose
        private String selectImageTitle;
        @SerializedName("TakePhoto")
        @Expose
        private String takePhoto;
        @SerializedName("CameraRoll")
        @Expose
        private String cameraRoll;
        @SerializedName("PhotoLibrary")
        @Expose
        private String photoLibrary;
        @SerializedName("RemovePhoto")
        @Expose
        private String removePhoto;
        @SerializedName("TermsOfService")
        @Expose
        private String termsOfService;
        @SerializedName("Accept")
        @Expose
        private String accept;
        @SerializedName("Decline")
        @Expose
        private String decline;
        @SerializedName("FireWarden")
        @Expose
        private String fireWarden;
        @SerializedName("RejectionCommentAlert")
        @Expose
        private String rejectionCommentAlert;
        @SerializedName("ImpactMobileNotificationsTitle")
        @Expose
        private String impactMobileNotificationsTitle;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("Building")
        @Expose
        private String building;
        @SerializedName("Floor")
        @Expose
        private String floor;
        @SerializedName("Old")
        @Expose
        private String old;
        @SerializedName("Pending")
        @Expose
        private String pending;
        @SerializedName("PinSuccessfullAlert")
        @Expose
        private String pinSuccessfullAlert;
        @SerializedName("PinUpdatedSuccessfullAlert")
        @Expose
        private String pinUpdatedSuccessfullAlert;
        @SerializedName("CheckInAndCheckOut")
        @Expose
        private String checkInAndCheckOut;
        @SerializedName("BookingChangeAlerts")
        @Expose
        private String bookingChangeAlerts;
        @SerializedName("CovidReminderTitle")
        @Expose
        private String covidReminderTitle;
        @SerializedName("CheckInNotificationtitle")
        @Expose
        private String checkInNotificationtitle;
        @SerializedName("AcceptedOrRejectedTitle")
        @Expose
        private String acceptedOrRejectedTitle;
        @SerializedName("Push")
        @Expose
        private String push;
        @SerializedName("Set")
        @Expose
        private String set;
        @SerializedName("Help")
        @Expose
        private String help;
        @SerializedName("Registration")
        @Expose
        private String registration;
        @SerializedName("CurrentPassword")
        @Expose
        private String currentPassword;
        @SerializedName("NewPassword")
        @Expose
        private String newPassword;
        @SerializedName("ConfirmPassword")
        @Expose
        private String confirmPassword;
        @SerializedName("ChangePassword")
        @Expose
        private String changePassword;

        @SerializedName("SpecificDate")
        @Expose
        private String specificDate;
        @SerializedName("Recipients")
        @Expose
        private String recipients;
        @SerializedName("AvailableFrom")
        @Expose
        private String availableFrom;
        @SerializedName("AvailableTo")
        @Expose
        private String availableTo;
        @SerializedName("EditNotice")
        @Expose
        private String editNotice;
        @SerializedName("CreateNotice")
        @Expose
        private String createNotice;
        @SerializedName("Videos")
        @Expose
        private String videos;
        @SerializedName("Guides")
        @Expose
        private String guides;
        @SerializedName("RequestTraining")
        @Expose
        private String requestTraining;
        @SerializedName("ActiveNotices")
        @Expose
        private String activeNotices;
        @SerializedName("IssueDate")
        @Expose
        private String issueDate;
        @SerializedName("Expired")
        @Expose
        private String expired;
        @SerializedName("PendingRequests")
        @Expose
        private String pendingRequests;
        @SerializedName("PendingApproval")
        @Expose
        private String pendingApproval;
        @SerializedName("WorkFromOffice")
        @Expose
        private String workFromOffice;
        @SerializedName("WorkingRemotedly")
        @Expose
        private String workingRemotedly;
        @SerializedName("NoBookingsAvailable")
        @Expose
        private String noBookingsAvailable;
        @SerializedName("AvailableParkings")
        @Expose
        private String availableParkings;
        @SerializedName("EnterVehicleNum")
        @Expose
        private String enterVehicleNum;
        @SerializedName("RepeatUntill")
        @Expose
        private String repeatUntill;
        @SerializedName("AvailableRooms")
        @Expose
        private String availableRooms;
        @SerializedName("HybridHero")
        @Expose
        private String hybridHero;

        //New...
        @SerializedName("HealthEating")
        @Expose
        private String HealthEating;
        @SerializedName("Send")
        @Expose
        private String Send;

        @SerializedName("Repeatsdaily")
        private String repeatsDaily;
        @SerializedName("endOfWeek")
        private String endOfWeek;
        @SerializedName("participants(optional)")
        private String participantsOptional;
        @SerializedName("Checkin")
        private String checkIn;
        @SerializedName("Checkout")
        private String Checkout;
        @SerializedName("Booknearby")
        private String bookNearBy;
        @SerializedName("SaveChanges")
        private String saveChanges;
        @SerializedName("BookedByMeL")
        private String bookedByme;
        @SerializedName("Capacity")
        private String Capacity;


        @SerializedName("Internalparticipantsoptional")
        String internalParticipant;
        @SerializedName("Externalparticipantsoptional")
        String externalParticipants;
        @SerializedName("Commentsoptional")
        String Commentsoptional;
        @SerializedName("Signinwithpin")
        String Signinwithpin;

        @SerializedName("EditWorkingRemotely")
        String EditWorkingRemotely;
        @SerializedName("EditSickness")
        String EditSickness;
        @SerializedName("EditHoliday")
        String EditHoliday;
        @SerializedName("EditTraining")
        String EditTraining; @SerializedName("SettingsTitle")
        String SettingsTitle;

        public String getPastEvents() {
            return PastEvents;
        }

        public void setPastEvents(String pastEvents) {
            PastEvents = pastEvents;
        }

        public String getSettingsTitle() {
            return SettingsTitle;
        }

        public void setSettingsTitle(String settingsTitle) {
            SettingsTitle = settingsTitle;
        }

        public String getEditWorkingRemotely() {
            return EditWorkingRemotely;
        }

        public void setEditWorkingRemotely(String editWorkingRemotely) {
            EditWorkingRemotely = editWorkingRemotely;
        }

        public String getEditSickness() {
            return EditSickness;
        }

        public void setEditSickness(String editSickness) {
            EditSickness = editSickness;
        }

        public String getEditHoliday() {
            return EditHoliday;
        }

        public void setEditHoliday(String editHoliday) {
            EditHoliday = editHoliday;
        }

        public String getEditTraining() {
            return EditTraining;
        }

        public void setEditTraining(String editTraining) {
            EditTraining = editTraining;
        }

        public String getRemote() {
            return Remote;
        }

        public void setRemote(String remote) {
            Remote = remote;
        }

        public String getSick() {
            return Sick;
        }

        public void setSick(String sick) {
            Sick = sick;
        }

        public String getHoliday() {
            return Holiday;
        }

        public void setHoliday(String holiday) {
            Holiday = holiday;
        }

        public String getTrainingStr() {
            return TrainingStr;
        }

        public void setTrainingStr(String trainingStr) {
            TrainingStr = trainingStr;
        }

        public String getWorkingRemotely() {
            return workingRemotely;
        }

        public void setWorkingRemotely(String workingRemotely) {
            this.workingRemotely = workingRemotely;
        }

        public String getLogSickness() {
            return logSickness;
        }

        public void setLogSickness(String logSickness) {
            this.logSickness = logSickness;
        }

        public String getBookHoliday() {
            return BookHoliday;
        }

        public void setBookHoliday(String bookHoliday) {
            BookHoliday = bookHoliday;
        }

        public String getBookTraining() {
            return BookTraining;
        }

        public void setBookTraining(String bookTraining) {
            BookTraining = bookTraining;
        }

        public String getSigninwithpin() {
            return Signinwithpin;
        }

        public void setSigninwithpin(String signinwithpin) {
            Signinwithpin = signinwithpin;
        }

        public String getInternalParticipant() {
            return internalParticipant;
        }

        public void setInternalParticipant(String internalParticipant) {
            this.internalParticipant = internalParticipant;
        }

        public String getExternalParticipants() {
            return externalParticipants;
        }

        public void setExternalParticipants(String externalParticipants) {
            this.externalParticipants = externalParticipants;
        }

        public String getCommentsoptional() {
            return Commentsoptional;
        }

        public void setCommentsoptional(String commentsoptional) {
            Commentsoptional = commentsoptional;
        }

        public String getCapacity() {
            return Capacity;
        }

        public void setCapacity(String capacity) {
            Capacity = capacity;
        }

        public String getBookedByme() {
            return bookedByme;
        }

        public void setBookedByme(String bookedByme) {
            this.bookedByme = bookedByme;
        }

        public String getSaveChanges() {
            return saveChanges;
        }

        public void setSaveChanges(String saveChanges) {
            this.saveChanges = saveChanges;
        }

        public String get_continue() {
            return _continue;
        }

        public void set_continue(String _continue) {
            this._continue = _continue;
        }

        public String getmYTeam() {
            return mYTeam;
        }

        public void setmYTeam(String mYTeam) {
            this.mYTeam = mYTeam;
        }

        public String getuSBCDock() {
            return uSBCDock;
        }

        public void setuSBCDock(String uSBCDock) {
            this.uSBCDock = uSBCDock;
        }

        public String get_new() {
            return _new;
        }

        public void set_new(String _new) {
            this._new = _new;
        }

        public String getRepeatsDaily() {
            return repeatsDaily;
        }

        public void setRepeatsDaily(String repeatsDaily) {
            this.repeatsDaily = repeatsDaily;
        }

        public String getEndOfWeek() {
            return endOfWeek;
        }

        public void setEndOfWeek(String endOfWeek) {
            this.endOfWeek = endOfWeek;
        }

        public String getParticipantsOptional() {
            return participantsOptional;
        }

        public void setParticipantsOptional(String participantsOptional) {
            this.participantsOptional = participantsOptional;
        }

        public String getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(String checkIn) {
            this.checkIn = checkIn;
        }

        public String getCheckout() {
            return Checkout;
        }

        public void setCheckout(String checkout) {
            Checkout = checkout;
        }

        public String getBookNearBy() {
            return bookNearBy;
        }

        public void setBookNearBy(String bookNearBy) {
            this.bookNearBy = bookNearBy;
        }

        public String getSignInWithSso() {
            return signInWithSso;
        }

        public void setSignInWithSso(String signInWithSso) {
            this.signInWithSso = signInWithSso;
        }

        public String getSend() {
            return Send;
        }

        public void setSend(String send) {
            Send = send;
        }

        public String getHealthEating() {
            return HealthEating;
        }

        public void setHealthEating(String healthEating) {
            HealthEating = healthEating;
        }

        public String getSpecificDate() {
            return specificDate;
        }

        public void setSpecificDate(String specificDate) {
            this.specificDate = specificDate;
        }

        public String getRecipients() {
            return recipients;
        }

        public void setRecipients(String recipients) {
            this.recipients = recipients;
        }

        public String getAvailableFrom() {
            return availableFrom;
        }

        public void setAvailableFrom(String availableFrom) {
            this.availableFrom = availableFrom;
        }

        public String getAvailableTo() {
            return availableTo;
        }

        public void setAvailableTo(String availableTo) {
            this.availableTo = availableTo;
        }

        public String getEditNotice() {
            return editNotice;
        }

        public void setEditNotice(String editNotice) {
            this.editNotice = editNotice;
        }

        public String getCreateNotice() {
            return createNotice;
        }

        public void setCreateNotice(String createNotice) {
            this.createNotice = createNotice;
        }

        public String getVideos() {
            return videos;
        }

        public void setVideos(String videos) {
            this.videos = videos;
        }

        public String getGuides() {
            return guides;
        }

        public void setGuides(String guides) {
            this.guides = guides;
        }

        public String getRequestTraining() {
            return requestTraining;
        }

        public void setRequestTraining(String requestTraining) {
            this.requestTraining = requestTraining;
        }

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public String getActiveNotices() {
            return activeNotices;
        }

        public void setActiveNotices(String activeNotices) {
            this.activeNotices = activeNotices;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public String getPendingRequests() {
            return pendingRequests;
        }

        public void setPendingRequests(String pendingRequests) {
            this.pendingRequests = pendingRequests;
        }

        public String getPendingApproval() {
            return pendingApproval;
        }

        public void setPendingApproval(String pendingApproval) {
            this.pendingApproval = pendingApproval;
        }

        public String getWorkFromOffice() {
            return workFromOffice;
        }

        public void setWorkFromOffice(String workFromOffice) {
            this.workFromOffice = workFromOffice;
        }

        public String getWorkingRemotedly() {
            return workingRemotedly;
        }

        public void setWorkingRemotedly(String workingRemotedly) {
            this.workingRemotedly = workingRemotedly;
        }

        public String getNoBookingsAvailable() {
            return noBookingsAvailable;
        }

        public void setNoBookingsAvailable(String noBookingsAvailable) {
            this.noBookingsAvailable = noBookingsAvailable;
        }

        public String getAvailableParkings() {
            return availableParkings;
        }

        public void setAvailableParkings(String availableParkings) {
            this.availableParkings = availableParkings;
        }

        public String getEnterVehicleNum() {
            return enterVehicleNum;
        }

        public void setEnterVehicleNum(String enterVehicleNum) {
            this.enterVehicleNum = enterVehicleNum;
        }

        public String getRepeatUntill() {
            return repeatUntill;
        }

        public void setRepeatUntill(String repeatUntill) {
            this.repeatUntill = repeatUntill;
        }

        public String getAvailableRooms() {
            return availableRooms;
        }

        public void setAvailableRooms(String availableRooms) {
            this.availableRooms = availableRooms;
        }

        public String getHybridHero() {
            return hybridHero;
        }

        public void setHybridHero(String hybridHero) {
            this.hybridHero = hybridHero;
        }

        public String getSignIn() {
            return signIn;
        }

        public void setSignIn(String signIn) {
            this.signIn = signIn;
        }

        public String getSignInWithPin() {
            return signInWithPin;
        }

        public void setSignInWithPin(String signInWithPin) {
            this.signInWithPin = signInWithPin;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getForgotPassword() {
            return forgotPassword;
        }

        public void setForgotPassword(String forgotPassword) {
            this.forgotPassword = forgotPassword;
        }

        public String getOrSignInWith() {
            return orSignInWith;
        }

        public void setOrSignInWith(String orSignInWith) {
            this.orSignInWith = orSignInWith;
        }

        public String getTenantName() {
            return tenantName;
        }

        public void setTenantName(String tenantName) {
            this.tenantName = tenantName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getSubmitPasswordReset() {
            return submitPasswordReset;
        }

        public void setSubmitPasswordReset(String submitPasswordReset) {
            this.submitPasswordReset = submitPasswordReset;
        }

        public String getGoBackToSignIn() {
            return goBackToSignIn;
        }

        public void setGoBackToSignIn(String goBackToSignIn) {
            this.goBackToSignIn = goBackToSignIn;
        }

        public String getEnterYourPin() {
            return enterYourPin;
        }

        public void setEnterYourPin(String enterYourPin) {
            this.enterYourPin = enterYourPin;
        }

        public String getUseEmailPassword() {
            return useEmailPassword;
        }

        public void setUseEmailPassword(String useEmailPassword) {
            this.useEmailPassword = useEmailPassword;
        }

        public String getLocate() {
            return locate;
        }

        public void setLocate(String locate) {
            this.locate = locate;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getWellBeing() {
            return wellBeing;
        }

        public void setWellBeing(String wellBeing) {
            this.wellBeing = wellBeing;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public String getCancel() {
            return cancel;
        }

        public void setCancel(String cancel) {
            this.cancel = cancel;
        }

        public String getApply() {
            return apply;
        }

        public void setApply(String apply) {
            this.apply = apply;
        }

        public String getChooseLocation() {
            return chooseLocation;
        }

        public void setChooseLocation(String chooseLocation) {
            this.chooseLocation = chooseLocation;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRepeat() {
            return repeat;
        }

        public void setRepeat(String repeat) {
            this.repeat = repeat;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getSelectDate() {
            return selectDate;
        }

        public void setSelectDate(String selectDate) {
            this.selectDate = selectDate;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getContinue() {
            return _continue;
        }

        public void setContinue(String _continue) {
            this._continue = _continue;
        }

        public String getNone() {
            return none;
        }

        public void setNone(String none) {
            this.none = none;
        }

        public String getDaily() {
            return daily;
        }

        public void setDaily(String daily) {
            this.daily = daily;
        }

        public String getWeekly() {
            return weekly;
        }

        public void setWeekly(String weekly) {
            this.weekly = weekly;
        }

        public String getMonthly() {
            return monthly;
        }

        public void setMonthly(String monthly) {
            this.monthly = monthly;
        }

        public String getYearly() {
            return yearly;
        }

        public void setYearly(String yearly) {
            this.yearly = yearly;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getUntil() {
            return until;
        }

        public void setUntil(String until) {
            this.until = until;
        }

        public String getOnDays() {
            return onDays;
        }

        public void setOnDays(String onDays) {
            this.onDays = onDays;
        }

        public String getForever() {
            return forever;
        }

        public void setForever(String forever) {
            this.forever = forever;
        }

        public String getAllDays() {
            return allDays;
        }

        public void setAllDays(String allDays) {
            this.allDays = allDays;
        }

        public String getMYTeam() {
            return mYTeam;
        }

        public void setMYTeam(String mYTeam) {
            this.mYTeam = mYTeam;
        }

        public String getAllTeams() {
            return allTeams;
        }

        public void setAllTeams(String allTeams) {
            this.allTeams = allTeams;
        }

        public String getSkip() {
            return skip;
        }

        public void setSkip(String skip) {
            this.skip = skip;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }

        public String getWorkSpaces() {
            return workSpaces;
        }

        public void setWorkSpaces(String workSpaces) {
            this.workSpaces = workSpaces;
        }

        public String getRooms() {
            return rooms;
        }

        public void setRooms(String rooms) {
            this.rooms = rooms;
        }

        public String getParkings() {
            return parkings;
        }

        public void setParkings(String parkings) {
            this.parkings = parkings;
        }

        public String getMonitor() {
            return monitor;
        }

        public void setMonitor(String monitor) {
            this.monitor = monitor;
        }

        public String getAdjustableHeight() {
            return adjustableHeight;
        }

        public void setAdjustableHeight(String adjustableHeight) {
            this.adjustableHeight = adjustableHeight;
        }

        public String getLaptopStand() {
            return laptopStand;
        }

        public void setLaptopStand(String laptopStand) {
            this.laptopStand = laptopStand;
        }

        public String getUSBCDock() {
            return uSBCDock;
        }

        public void setUSBCDock(String uSBCDock) {
            this.uSBCDock = uSBCDock;
        }

        public String getChargerPoint() {
            return chargerPoint;
        }

        public void setChargerPoint(String chargerPoint) {
            this.chargerPoint = chargerPoint;
        }

        public String getStandingDesk() {
            return standingDesk;
        }

        public void setStandingDesk(String standingDesk) {
            this.standingDesk = standingDesk;
        }

        public String getByRequest() {
            return byRequest;
        }

        public void setByRequest(String byRequest) {
            this.byRequest = byRequest;
        }

        public String getBooked() {
            return booked;
        }

        public void setBooked(String booked) {
            this.booked = booked;
        }

        public String getBookedByMe() {
            return bookedByMe;
        }

        public void setBookedByMe(String bookedByMe) {
            this.bookedByMe = bookedByMe;
        }

        public String getUnAvilable() {
            return unAvilable;
        }

        public void setUnAvilable(String unAvilable) {
            this.unAvilable = unAvilable;
        }

        public String getTeams() {
            return teams;
        }

        public void setTeams(String teams) {
            this.teams = teams;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getEndOfDay() {
            return endOfDay;
        }

        public void setEndOfDay(String endOfDay) {
            this.endOfDay = endOfDay;
        }

        public String getNow() {
            return now;
        }

        public void setNow(String now) {
            this.now = now;
        }

        public String getTomorrow() {
            return tomorrow;
        }

        public void setTomorrow(String tomorrow) {
            this.tomorrow = tomorrow;
        }

        public String getNextWeek() {
            return nextWeek;
        }

        public void setNextWeek(String nextWeek) {
            this.nextWeek = nextWeek;
        }

        public String getYesterday() {
            return yesterday;
        }

        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getDaysAgo() {
            return daysAgo;
        }

        public void setDaysAgo(String daysAgo) {
            this.daysAgo = daysAgo;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public String getWorkSpace() {
            return workSpace;
        }

        public void setWorkSpace(String workSpace) {
            this.workSpace = workSpace;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getParking() {
            return parking;
        }

        public void setParking(String parking) {
            this.parking = parking;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getBookWorkSpace() {
            return bookWorkSpace;
        }

        public void setBookWorkSpace(String bookWorkSpace) {
            this.bookWorkSpace = bookWorkSpace;
        }

        public String getBookRoom() {
            return bookRoom;
        }

        public void setBookRoom(String bookRoom) {
            this.bookRoom = bookRoom;
        }

        public String getBookParking() {
            return bookParking;
        }

        public void setBookParking(String bookParking) {
            this.bookParking = bookParking;
        }

        public String getTypeBook() {
            return typeBook;
        }

        public void setTypeBook(String typeBook) {
            this.typeBook = typeBook;
        }

        public String getEditYourBooking() {
            return editYourBooking;
        }

        public void setEditYourBooking(String editYourBooking) {
            this.editYourBooking = editYourBooking;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getAvailableDesk() {
            return availableDesk;
        }

        public void setAvailableDesk(String availableDesk) {
            this.availableDesk = availableDesk;
        }

        public String getExpandAll() {
            return expandAll;
        }

        public void setExpandAll(String expandAll) {
            this.expandAll = expandAll;
        }

        public String getDesksAvailable() {
            return desksAvailable;
        }

        public void setDesksAvailable(String desksAvailable) {
            this.desksAvailable = desksAvailable;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getUpcomingBookings() {
            return upcomingBookings;
        }

        public void setUpcomingBookings(String upcomingBookings) {
            this.upcomingBookings = upcomingBookings;
        }

        public String getViewAll() {
            return viewAll;
        }

        public void setViewAll(String viewAll) {
            this.viewAll = viewAll;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getViewOlder() {
            return viewOlder;
        }

        public void setViewOlder(String viewOlder) {
            this.viewOlder = viewOlder;
        }

        public String getHealthAndSafety() {
            return healthAndSafety;
        }

        public void setHealthAndSafety(String healthAndSafety) {
            this.healthAndSafety = healthAndSafety;
        }

        public String getHealthTips() {
            return healthTips;
        }

        public void setHealthTips(String healthTips) {
            this.healthTips = healthTips;
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

        public String getMentalHealth() {
            return mentalHealth;
        }

        public void setMentalHealth(String mentalHealth) {
            this.mentalHealth = mentalHealth;
        }

        public String getHumanResources() {
            return humanResources;
        }

        public void setHumanResources(String humanResources) {
            this.humanResources = humanResources;
        }

        public String getLeave() {
            return leave;
        }

        public void setLeave(String leave) {
            this.leave = leave;
        }

        public String getPlaces() {
            return places;
        }

        public void setPlaces(String places) {
            this.places = places;
        }

        public String getReportAnIssue() {
            return reportAnIssue;
        }

        public void setReportAnIssue(String reportAnIssue) {
            this.reportAnIssue = reportAnIssue;
        }

        public String getBenefits() {
            return benefits;
        }

        public void setBenefits(String benefits) {
            this.benefits = benefits;
        }

        public String getTraining() {
            return training;
        }

        public void setTraining(String training) {
            this.training = training;
        }

        public String getNotices() {
            return notices;
        }

        public void setNotices(String notices) {
            this.notices = notices;
        }

        public String getWorkSpaceSurvey() {
            return workSpaceSurvey;
        }

        public void setWorkSpaceSurvey(String workSpaceSurvey) {
            this.workSpaceSurvey = workSpaceSurvey;
        }

        public String getWorkSpaceAssessment() {
            return workSpaceAssessment;
        }

        public void setWorkSpaceAssessment(String workSpaceAssessment) {
            this.workSpaceAssessment = workSpaceAssessment;
        }

        public String getCovid() {
            return covid;
        }

        public void setCovid(String covid) {
            this.covid = covid;
        }

        public String getPersonalHelp() {
            return personalHelp;
        }

        public void setPersonalHelp(String personalHelp) {
            this.personalHelp = personalHelp;
        }

        public String getEvents() {
            return events;
        }

        public void setEvents(String events) {
            this.events = events;
        }

        public String getReportHazard() {
            return reportHazard;
        }

        public void setReportHazard(String reportHazard) {
            this.reportHazard = reportHazard;
        }

        public String getEvacuationPlan() {
            return evacuationPlan;
        }

        public void setEvacuationPlan(String evacuationPlan) {
            this.evacuationPlan = evacuationPlan;
        }

        public String getYourFireWardens() {
            return yourFireWardens;
        }

        public void setYourFireWardens(String yourFireWardens) {
            this.yourFireWardens = yourFireWardens;
        }

        public String getYourFirstAiders() {
            return yourFirstAiders;
        }

        public void setYourFirstAiders(String yourFirstAiders) {
            this.yourFirstAiders = yourFirstAiders;
        }

        public String getYourMentalHealthAdvocates() {
            return yourMentalHealthAdvocates;
        }

        public void setYourMentalHealthAdvocates(String yourMentalHealthAdvocates) {
            this.yourMentalHealthAdvocates = yourMentalHealthAdvocates;
        }

        public String getInOffice() {
            return inOffice;
        }

        public void setInOffice(String inOffice) {
            this.inOffice = inOffice;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public String getHolidayRemaining() {
            return holidayRemaining;
        }

        public void setHolidayRemaining(String holidayRemaining) {
            this.holidayRemaining = holidayRemaining;
        }

        public String getUpcomingLeave() {
            return upcomingLeave;
        }

        public void setUpcomingLeave(String upcomingLeave) {
            this.upcomingLeave = upcomingLeave;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getUpcoming() {
            return upcoming;
        }

        public void setUpcoming(String upcoming) {
            this.upcoming = upcoming;
        }

        public String getPast() {
            return past;
        }

        public void setPast(String past) {
            this.past = past;
        }

        public String getRequestTimeOff() {
            return requestTimeOff;
        }

        public void setRequestTimeOff(String requestTimeOff) {
            this.requestTimeOff = requestTimeOff;
        }

        public String getReportIssueTitle() {
            return reportIssueTitle;
        }

        public void setReportIssueTitle(String reportIssueTitle) {
            this.reportIssueTitle = reportIssueTitle;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPastBooking() {
            return pastBooking;
        }

        public void setPastBooking(String pastBooking) {
            this.pastBooking = pastBooking;
        }

        public String getDateApplicable() {
            return dateApplicable;
        }

        public void setDateApplicable(String dateApplicable) {
            this.dateApplicable = dateApplicable;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
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

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getSubmit() {
            return submit;
        }

        public void setSubmit(String submit) {
            this.submit = submit;
        }

        public String getUnavailable() {
            return unavailable;
        }

        public void setUnavailable(String unavailable) {
            this.unavailable = unavailable;
        }

        public String getUnavailableDeskTitle() {
            return unavailableDeskTitle;
        }

        public void setUnavailableDeskTitle(String unavailableDeskTitle) {
            this.unavailableDeskTitle = unavailableDeskTitle;
        }

        public String getGlobalLocation() {
            return globalLocation;
        }

        public void setGlobalLocation(String globalLocation) {
            this.globalLocation = globalLocation;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getTransport() {
            return transport;
        }

        public void setTransport(String transport) {
            this.transport = transport;
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

        public String getWorkFromHome() {
            return workFromHome;
        }

        public void setWorkFromHome(String workFromHome) {
            this.workFromHome = workFromHome;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getViewTeam() {
            return viewTeam;
        }

        public void setViewTeam(String viewTeam) {
            this.viewTeam = viewTeam;
        }

        public String getCovidTransportMode() {
            return covidTransportMode;
        }

        public void setCovidTransportMode(String covidTransportMode) {
            this.covidTransportMode = covidTransportMode;
        }

        public String getQuestions() {
            return questions;
        }

        public void setQuestions(String questions) {
            this.questions = questions;
        }

        public String getBookedBySomeone() {
            return bookedBySomeone;
        }

        public void setBookedBySomeone(String bookedBySomeone) {
            this.bookedBySomeone = bookedBySomeone;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getAddNew() {
            return addNew;
        }

        public void setAddNew(String addNew) {
            this.addNew = addNew;
        }

        public String getActiveBookings() {
            return activeBookings;
        }

        public void setActiveBookings(String activeBookings) {
            this.activeBookings = activeBookings;
        }

        public String getNoActiveBookings() {
            return noActiveBookings;
        }

        public void setNoActiveBookings(String noActiveBookings) {
            this.noActiveBookings = noActiveBookings;
        }

        public String getNotifications() {
            return notifications;
        }

        public void setNotifications(String notifications) {
            this.notifications = notifications;
        }

        public String getNew() {
            return _new;
        }

        public void setNew(String _new) {
            this._new = _new;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getYears() {
            return years;
        }

        public void setYears(String years) {
            this.years = years;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeekDays() {
            return weekDays;
        }

        public void setWeekDays(String weekDays) {
            this.weekDays = weekDays;
        }

        public String getConfigureMailAlert() {
            return configureMailAlert;
        }

        public void setConfigureMailAlert(String configureMailAlert) {
            this.configureMailAlert = configureMailAlert;
        }

        public String getMonday() {
            return monday;
        }

        public void setMonday(String monday) {
            this.monday = monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        public String getFriday() {
            return friday;
        }

        public void setFriday(String friday) {
            this.friday = friday;
        }

        public String getSaturday() {
            return saturday;
        }

        public void setSaturday(String saturday) {
            this.saturday = saturday;
        }

        public String getSunday() {
            return sunday;
        }

        public void setSunday(String sunday) {
            this.sunday = sunday;
        }

        public String getCheckedIn() {
            return checkedIn;
        }

        public void setCheckedIn(String checkedIn) {
            this.checkedIn = checkedIn;
        }

        public String getCheckedOut() {
            return checkedOut;
        }

        public void setCheckedOut(String checkedOut) {
            this.checkedOut = checkedOut;
        }

        public String getCheckInSuccessful() {
            return checkInSuccessful;
        }

        public void setCheckInSuccessful(String checkInSuccessful) {
            this.checkInSuccessful = checkInSuccessful;
        }

        public String getCheckOutSuccessful() {
            return checkOutSuccessful;
        }

        public void setCheckOutSuccessful(String checkOutSuccessful) {
            this.checkOutSuccessful = checkOutSuccessful;
        }

        public String getBookingCreated() {
            return bookingCreated;
        }

        public void setBookingCreated(String bookingCreated) {
            this.bookingCreated = bookingCreated;
        }

        public String getBookingUpdated() {
            return bookingUpdated;
        }

        public void setBookingUpdated(String bookingUpdated) {
            this.bookingUpdated = bookingUpdated;
        }

        public String getBookingDeleted() {
            return bookingDeleted;
        }

        public void setBookingDeleted(String bookingDeleted) {
            this.bookingDeleted = bookingDeleted;
        }

        public String getSomethingWentWrong() {
            return somethingWentWrong;
        }

        public void setSomethingWentWrong(String somethingWentWrong) {
            this.somethingWentWrong = somethingWentWrong;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getInsertActionTitle() {
            return insertActionTitle;
        }

        public void setInsertActionTitle(String insertActionTitle) {
            this.insertActionTitle = insertActionTitle;
        }

        public String getDeskPhone() {
            return deskPhone;
        }

        public void setDeskPhone(String deskPhone) {
            this.deskPhone = deskPhone;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDefaultWorkingHours() {
            return defaultWorkingHours;
        }

        public void setDefaultWorkingHours(String defaultWorkingHours) {
            this.defaultWorkingHours = defaultWorkingHours;
        }

        public String getDefaultLocation() {
            return defaultLocation;
        }

        public void setDefaultLocation(String defaultLocation) {
            this.defaultLocation = defaultLocation;
        }

        public String getDefaultAssets() {
            return defaultAssets;
        }

        public void setDefaultAssets(String defaultAssets) {
            this.defaultAssets = defaultAssets;
        }

        public String getEdit() {
            return edit;
        }

        public void setEdit(String edit) {
            this.edit = edit;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public String getReject() {
            return reject;
        }

        public void setReject(String reject) {
            this.reject = reject;
        }

        public String getInsertYourComment() {
            return insertYourComment;
        }

        public void setInsertYourComment(String insertYourComment) {
            this.insertYourComment = insertYourComment;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getUploadPhoto() {
            return uploadPhoto;
        }

        public void setUploadPhoto(String uploadPhoto) {
            this.uploadPhoto = uploadPhoto;
        }

        public String getHalfDay() {
            return halfDay;
        }

        public void setHalfDay(String halfDay) {
            this.halfDay = halfDay;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getSendRequest() {
            return sendRequest;
        }

        public void setSendRequest(String sendRequest) {
            this.sendRequest = sendRequest;
        }

        public String getTimeOffType() {
            return timeOffType;
        }

        public void setTimeOffType(String timeOffType) {
            this.timeOffType = timeOffType;
        }

        public String getAnnualLeave() {
            return annualLeave;
        }

        public void setAnnualLeave(String annualLeave) {
            this.annualLeave = annualLeave;
        }

        public String getSickLeave() {
            return sickLeave;
        }

        public void setSickLeave(String sickLeave) {
            this.sickLeave = sickLeave;
        }

        public String getFamilyCompassionateLeave() {
            return familyCompassionateLeave;
        }

        public void setFamilyCompassionateLeave(String familyCompassionateLeave) {
            this.familyCompassionateLeave = familyCompassionateLeave;
        }

        public String getMedicalAppointment() {
            return medicalAppointment;
        }

        public void setMedicalAppointment(String medicalAppointment) {
            this.medicalAppointment = medicalAppointment;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getWelcome() {
            return welcome;
        }

        public void setWelcome(String welcome) {
            this.welcome = welcome;
        }

        public String getCompanyNameNotEmptyAlert() {
            return companyNameNotEmptyAlert;
        }

        public void setCompanyNameNotEmptyAlert(String companyNameNotEmptyAlert) {
            this.companyNameNotEmptyAlert = companyNameNotEmptyAlert;
        }

        public String getEmailNotEmptyAlert() {
            return emailNotEmptyAlert;
        }

        public void setEmailNotEmptyAlert(String emailNotEmptyAlert) {
            this.emailNotEmptyAlert = emailNotEmptyAlert;
        }

        public String getInvalidEmail() {
            return invalidEmail;
        }

        public void setInvalidEmail(String invalidEmail) {
            this.invalidEmail = invalidEmail;
        }

        public String getPasswordNotEmptyAlert() {
            return passwordNotEmptyAlert;
        }

        public void setPasswordNotEmptyAlert(String passwordNotEmptyAlert) {
            this.passwordNotEmptyAlert = passwordNotEmptyAlert;
        }

        public String getIncorrectPassword() {
            return incorrectPassword;
        }

        public void setIncorrectPassword(String incorrectPassword) {
            this.incorrectPassword = incorrectPassword;
        }

        public String getInvalidLogInDetails() {
            return invalidLogInDetails;
        }

        public void setInvalidLogInDetails(String invalidLogInDetails) {
            this.invalidLogInDetails = invalidLogInDetails;
        }

        public String getPleaseAcceptGDPR() {
            return pleaseAcceptGDPR;
        }

        public void setPleaseAcceptGDPR(String pleaseAcceptGDPR) {
            this.pleaseAcceptGDPR = pleaseAcceptGDPR;
        }

        public String getTenantNameNotEmptyAlert() {
            return tenantNameNotEmptyAlert;
        }

        public void setTenantNameNotEmptyAlert(String tenantNameNotEmptyAlert) {
            this.tenantNameNotEmptyAlert = tenantNameNotEmptyAlert;
        }

        public String getEmailOrCompanyInvalid() {
            return emailOrCompanyInvalid;
        }

        public void setEmailOrCompanyInvalid(String emailOrCompanyInvalid) {
            this.emailOrCompanyInvalid = emailOrCompanyInvalid;
        }

        public String getResetPasswordAlert() {
            return resetPasswordAlert;
        }

        public void setResetPasswordAlert(String resetPasswordAlert) {
            this.resetPasswordAlert = resetPasswordAlert;
        }

        public String getIncompletePinEntry() {
            return incompletePinEntry;
        }

        public void setIncompletePinEntry(String incompletePinEntry) {
            this.incompletePinEntry = incompletePinEntry;
        }

        public String getInvalidPIN() {
            return invalidPIN;
        }

        public void setInvalidPIN(String invalidPIN) {
            this.invalidPIN = invalidPIN;
        }

        public String getEnterYourOldPin() {
            return enterYourOldPin;
        }

        public void setEnterYourOldPin(String enterYourOldPin) {
            this.enterYourOldPin = enterYourOldPin;
        }

        public String getEnterYourNewPin() {
            return enterYourNewPin;
        }

        public void setEnterYourNewPin(String enterYourNewPin) {
            this.enterYourNewPin = enterYourNewPin;
        }

        public String getReEnterNewPin() {
            return reEnterNewPin;
        }

        public void setReEnterNewPin(String reEnterNewPin) {
            this.reEnterNewPin = reEnterNewPin;
        }

        public String getPinNotMatched() {
            return pinNotMatched;
        }

        public void setPinNotMatched(String pinNotMatched) {
            this.pinNotMatched = pinNotMatched;
        }

        public String getTenantNameAlert() {
            return tenantNameAlert;
        }

        public void setTenantNameAlert(String tenantNameAlert) {
            this.tenantNameAlert = tenantNameAlert;
        }

        public String getPasswordUpdatedAlert() {
            return passwordUpdatedAlert;
        }

        public void setPasswordUpdatedAlert(String passwordUpdatedAlert) {
            this.passwordUpdatedAlert = passwordUpdatedAlert;
        }

        public String getDurationFrom() {
            return durationFrom;
        }

        public void setDurationFrom(String durationFrom) {
            this.durationFrom = durationFrom;
        }

        public String getOtherComments() {
            return otherComments;
        }

        public void setOtherComments(String otherComments) {
            this.otherComments = otherComments;
        }

        public String getAccessibility() {
            return accessibility;
        }

        public void setAccessibility(String accessibility) {
            this.accessibility = accessibility;
        }

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getCleanliness() {
            return cleanliness;
        }

        public void setCleanliness(String cleanliness) {
            this.cleanliness = cleanliness;
        }

        public String getLight() {
            return light;
        }

        public void setLight(String light) {
            this.light = light;
        }

        public String getNoise() {
            return noise;
        }

        public void setNoise(String noise) {
            this.noise = noise;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getChair() {
            return chair;
        }

        public void setChair(String chair) {
            this.chair = chair;
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

        public String getKeyBoard() {
            return keyBoard;
        }

        public void setKeyBoard(String keyBoard) {
            this.keyBoard = keyBoard;
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

        public String getFromDateAlert() {
            return fromDateAlert;
        }

        public void setFromDateAlert(String fromDateAlert) {
            this.fromDateAlert = fromDateAlert;
        }

        public String getToDateAlert() {
            return toDateAlert;
        }

        public void setToDateAlert(String toDateAlert) {
            this.toDateAlert = toDateAlert;
        }

        public String getCommentEmptyAlert() {
            return commentEmptyAlert;
        }

        public void setCommentEmptyAlert(String commentEmptyAlert) {
            this.commentEmptyAlert = commentEmptyAlert;
        }

        public String getThankYouFeedbackAlert() {
            return thankYouFeedbackAlert;
        }

        public void setThankYouFeedbackAlert(String thankYouFeedbackAlert) {
            this.thankYouFeedbackAlert = thankYouFeedbackAlert;
        }

        public String getDescriptionEmptyAlert() {
            return descriptionEmptyAlert;
        }

        public void setDescriptionEmptyAlert(String descriptionEmptyAlert) {
            this.descriptionEmptyAlert = descriptionEmptyAlert;
        }

        public String getCheckMailbox() {
            return checkMailbox;
        }

        public void setCheckMailbox(String checkMailbox) {
            this.checkMailbox = checkMailbox;
        }

        public String getSelectMeetingRoomAlert() {
            return selectMeetingRoomAlert;
        }

        public void setSelectMeetingRoomAlert(String selectMeetingRoomAlert) {
            this.selectMeetingRoomAlert = selectMeetingRoomAlert;
        }

        public String getInvalidQRCode() {
            return invalidQRCode;
        }

        public void setInvalidQRCode(String invalidQRCode) {
            this.invalidQRCode = invalidQRCode;
        }

        public String getRequestToRejectAlert() {
            return requestToRejectAlert;
        }

        public void setRequestToRejectAlert(String requestToRejectAlert) {
            this.requestToRejectAlert = requestToRejectAlert;
        }

        public String getRequestToAcceptAlert() {
            return requestToAcceptAlert;
        }

        public void setRequestToAcceptAlert(String requestToAcceptAlert) {
            this.requestToAcceptAlert = requestToAcceptAlert;
        }

        public String getRejectRequest() {
            return rejectRequest;
        }

        public void setRejectRequest(String rejectRequest) {
            this.rejectRequest = rejectRequest;
        }

        public String getAcceptRequest() {
            return acceptRequest;
        }

        public void setAcceptRequest(String acceptRequest) {
            this.acceptRequest = acceptRequest;
        }

        public String getCertificateUpdated() {
            return certificateUpdated;
        }

        public void setCertificateUpdated(String certificateUpdated) {
            this.certificateUpdated = certificateUpdated;
        }

        public String getCertificateNotUpdated() {
            return certificateNotUpdated;
        }

        public void setCertificateNotUpdated(String certificateNotUpdated) {
            this.certificateNotUpdated = certificateNotUpdated;
        }

        public String getBookingNotUpdated() {
            return bookingNotUpdated;
        }

        public void setBookingNotUpdated(String bookingNotUpdated) {
            this.bookingNotUpdated = bookingNotUpdated;
        }

        public String getRegistraionNumberNotEmptyAlert() {
            return registraionNumberNotEmptyAlert;
        }

        public void setRegistraionNumberNotEmptyAlert(String registraionNumberNotEmptyAlert) {
            this.registraionNumberNotEmptyAlert = registraionNumberNotEmptyAlert;
        }

        public String getSelectDeskAlert() {
            return selectDeskAlert;
        }

        public void setSelectDeskAlert(String selectDeskAlert) {
            this.selectDeskAlert = selectDeskAlert;
        }

        public String getWrongDeskAlert() {
            return wrongDeskAlert;
        }

        public void setWrongDeskAlert(String wrongDeskAlert) {
            this.wrongDeskAlert = wrongDeskAlert;
        }

        public String getBookingNotDeleted() {
            return bookingNotDeleted;
        }

        public void setBookingNotDeleted(String bookingNotDeleted) {
            this.bookingNotDeleted = bookingNotDeleted;
        }

        public String getFromTimeNotEmptyAlert() {
            return fromTimeNotEmptyAlert;
        }

        public void setFromTimeNotEmptyAlert(String fromTimeNotEmptyAlert) {
            this.fromTimeNotEmptyAlert = fromTimeNotEmptyAlert;
        }

        public String getToTimeNotEmptyAlert() {
            return toTimeNotEmptyAlert;
        }

        public void setToTimeNotEmptyAlert(String toTimeNotEmptyAlert) {
            this.toTimeNotEmptyAlert = toTimeNotEmptyAlert;
        }

        public String getVehicleNoNotEmptyAlert() {
            return vehicleNoNotEmptyAlert;
        }

        public void setVehicleNoNotEmptyAlert(String vehicleNoNotEmptyAlert) {
            this.vehicleNoNotEmptyAlert = vehicleNoNotEmptyAlert;
        }

        public String getDateNotEemptyAlert() {
            return dateNotEemptyAlert;
        }

        public void setDateNotEemptyAlert(String dateNotEemptyAlert) {
            this.dateNotEemptyAlert = dateNotEemptyAlert;
        }

        public String getSubjectNotEmptyAlert() {
            return subjectNotEmptyAlert;
        }

        public void setSubjectNotEmptyAlert(String subjectNotEmptyAlert) {
            this.subjectNotEmptyAlert = subjectNotEmptyAlert;
        }

        public String getProfileUpdated() {
            return profileUpdated;
        }

        public void setProfileUpdated(String profileUpdated) {
            this.profileUpdated = profileUpdated;
        }

        public String getIssueReported() {
            return issueReported;
        }

        public void setIssueReported(String issueReported) {
            this.issueReported = issueReported;
        }

        public String getPersonalHelpTeamAlert() {
            return personalHelpTeamAlert;
        }

        public void setPersonalHelpTeamAlert(String personalHelpTeamAlert) {
            this.personalHelpTeamAlert = personalHelpTeamAlert;
        }

        public String getNotificationUpdated() {
            return notificationUpdated;
        }

        public void setNotificationUpdated(String notificationUpdated) {
            this.notificationUpdated = notificationUpdated;
        }

        public String getBookedForYou() {
            return bookedForYou;
        }

        public void setBookedForYou(String bookedForYou) {
            this.bookedForYou = bookedForYou;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }

        public String getSelectImageTitle() {
            return selectImageTitle;
        }

        public void setSelectImageTitle(String selectImageTitle) {
            this.selectImageTitle = selectImageTitle;
        }

        public String getTakePhoto() {
            return takePhoto;
        }

        public void setTakePhoto(String takePhoto) {
            this.takePhoto = takePhoto;
        }

        public String getCameraRoll() {
            return cameraRoll;
        }

        public void setCameraRoll(String cameraRoll) {
            this.cameraRoll = cameraRoll;
        }

        public String getPhotoLibrary() {
            return photoLibrary;
        }

        public void setPhotoLibrary(String photoLibrary) {
            this.photoLibrary = photoLibrary;
        }

        public String getRemovePhoto() {
            return removePhoto;
        }

        public void setRemovePhoto(String removePhoto) {
            this.removePhoto = removePhoto;
        }

        public String getTermsOfService() {
            return termsOfService;
        }

        public void setTermsOfService(String termsOfService) {
            this.termsOfService = termsOfService;
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

        public String getFireWarden() {
            return fireWarden;
        }

        public void setFireWarden(String fireWarden) {
            this.fireWarden = fireWarden;
        }

        public String getRejectionCommentAlert() {
            return rejectionCommentAlert;
        }

        public void setRejectionCommentAlert(String rejectionCommentAlert) {
            this.rejectionCommentAlert = rejectionCommentAlert;
        }

        public String getImpactMobileNotificationsTitle() {
            return impactMobileNotificationsTitle;
        }

        public void setImpactMobileNotificationsTitle(String impactMobileNotificationsTitle) {
            this.impactMobileNotificationsTitle = impactMobileNotificationsTitle;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getOld() {
            return old;
        }

        public void setOld(String old) {
            this.old = old;
        }

        public String getPending() {
            return pending;
        }

        public void setPending(String pending) {
            this.pending = pending;
        }

        public String getPinSuccessfullAlert() {
            return pinSuccessfullAlert;
        }

        public void setPinSuccessfullAlert(String pinSuccessfullAlert) {
            this.pinSuccessfullAlert = pinSuccessfullAlert;
        }

        public String getPinUpdatedSuccessfullAlert() {
            return pinUpdatedSuccessfullAlert;
        }

        public void setPinUpdatedSuccessfullAlert(String pinUpdatedSuccessfullAlert) {
            this.pinUpdatedSuccessfullAlert = pinUpdatedSuccessfullAlert;
        }

        public String getCheckInAndCheckOut() {
            return checkInAndCheckOut;
        }

        public void setCheckInAndCheckOut(String checkInAndCheckOut) {
            this.checkInAndCheckOut = checkInAndCheckOut;
        }

        public String getBookingChangeAlerts() {
            return bookingChangeAlerts;
        }

        public void setBookingChangeAlerts(String bookingChangeAlerts) {
            this.bookingChangeAlerts = bookingChangeAlerts;
        }

        public String getCovidReminderTitle() {
            return covidReminderTitle;
        }

        public void setCovidReminderTitle(String covidReminderTitle) {
            this.covidReminderTitle = covidReminderTitle;
        }

        public String getCheckInNotificationtitle() {
            return checkInNotificationtitle;
        }

        public void setCheckInNotificationtitle(String checkInNotificationtitle) {
            this.checkInNotificationtitle = checkInNotificationtitle;
        }

        public String getAcceptedOrRejectedTitle() {
            return acceptedOrRejectedTitle;
        }

        public void setAcceptedOrRejectedTitle(String acceptedOrRejectedTitle) {
            this.acceptedOrRejectedTitle = acceptedOrRejectedTitle;
        }

        public String getPush() {
            return push;
        }

        public void setPush(String push) {
            this.push = push;
        }

        public String getSet() {
            return set;
        }

        public void setSet(String set) {
            this.set = set;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public String getRegistration() {
            return registration;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getChangePassword() {
            return changePassword;
        }

        public void setChangePassword(String changePassword) {
            this.changePassword = changePassword;
        }

    }


}
