package dream.guys.hotdeskandroid.example;

public class ValuesPOJO {

    public String values;
    boolean isChecked;

    public ValuesPOJO() {
    }

    public ValuesPOJO(String values) {
        this.values = values;
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
}
