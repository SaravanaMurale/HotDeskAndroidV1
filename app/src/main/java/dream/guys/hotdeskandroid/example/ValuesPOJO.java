package dream.guys.hotdeskandroid.example;

public class ValuesPOJO {

    private int id;
    public String values;
    public boolean isChecked;

    public ValuesPOJO() {
    }

    public ValuesPOJO(String values) {
        this.values = values;
    }

    public ValuesPOJO(int id, String values, boolean isChecked) {
        this.id = id;
        this.values = values;
        this.isChecked = isChecked;
    }

    public ValuesPOJO(String values, boolean isChecked) {
        this.values = values;
        this.isChecked = isChecked;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
