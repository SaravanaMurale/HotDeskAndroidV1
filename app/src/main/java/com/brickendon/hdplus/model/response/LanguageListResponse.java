package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageListResponse {

    @SerializedName("multilanguages")
     Multilanguages multilanguages;

    public Multilanguages getMultilanguages() {
        return multilanguages;
    }

    public void setMultilanguages(Multilanguages multilanguages) {
        this.multilanguages = multilanguages;
    }

    public class Multilanguages{
        @SerializedName("existingLanguages")
        List<ExistingLanguages> existingLanguagesList;

        public List<ExistingLanguages> getExistingLanguagesList() {
            return existingLanguagesList;
        }

        public void setExistingLanguagesList(List<ExistingLanguages> existingLanguagesList) {
            this.existingLanguagesList = existingLanguagesList;
        }

        public class ExistingLanguages{
            String name;
            String Text;
            String imagesrc;

            public ExistingLanguages() {
            }

            public ExistingLanguages(String name, String text, String imagesrc) {
                this.name = name;
                Text = text;
                this.imagesrc = imagesrc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getText() {
                return Text;
            }

            public void setText(String text) {
                Text = text;
            }

            public String getImagesrc() {
                return imagesrc;
            }

            public void setImagesrc(String imagesrc) {
                this.imagesrc = imagesrc;
            }
        }
    }

}
