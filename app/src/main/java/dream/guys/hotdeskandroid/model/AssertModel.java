package dream.guys.hotdeskandroid.model;

import android.graphics.drawable.Drawable;

public class AssertModel {
    private Integer id;
    private String assertName;
    private Drawable image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssertName() {
        return assertName;
    }

    public void setAssertName(String assertName) {
        this.assertName = assertName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
