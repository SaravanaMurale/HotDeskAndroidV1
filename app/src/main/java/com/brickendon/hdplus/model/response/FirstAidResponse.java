package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FirstAidResponse {

    int type;
    boolean active;
    String description;
    List<Links> linksList;
    @SerializedName("persons")
    List<Persons> personsList;
    List<Emails> emailsList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Links> getLinksList() {
        return linksList;
    }

    public void setLinksList(List<Links> linksList) {
        this.linksList = linksList;
    }

    public List<Persons> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Persons> personsList) {
        this.personsList = personsList;
    }

    public List<Emails> getEmailsList() {
        return emailsList;
    }

    public void setEmailsList(List<Emails> emailsList) {
        this.emailsList = emailsList;
    }

    public class Links{

    }

    public class Persons{

        int id;
        String firstName;
        String lastName;
        String fullName;
        String email;
        boolean active;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public class Emails{

    }

}
