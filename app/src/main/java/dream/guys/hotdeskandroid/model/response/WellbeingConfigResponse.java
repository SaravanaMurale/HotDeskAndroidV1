package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WellbeingConfigResponse implements Serializable
{
    private int type;

    private List<Integer> components;

    private boolean active;

    private String description;

    @SerializedName("links")
    List<Link> links;

    private List<Persons> persons;

    private List<Emails> emails;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public List<Integer> getComponents()
    {
        return components;
    }

    public void setComponents(List<Integer> components)
    {
        this.components = components;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }

    public List<Persons> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Persons> persons)
    {
        this.persons = persons;
    }

    public List<Emails> getEmails()
    {
        return emails;
    }

    public void setEmails(List<Emails> emails)
    {
        this.emails = emails;
    }

    public class Link implements Serializable{

        private String name;
        private String url;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

    private class Persons
    {
        private int id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private boolean active;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getFullName()
        {
            return fullName;
        }

        public void setFullName(String fullName)
        {
            this.fullName = fullName;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public boolean isActive()
        {
            return active;
        }

        public void setActive(boolean active)
        {
            this.active = active;
        }
    }


    private class Emails
    {
    }
}


