package android.mainactivity;

/**
 * Created by dengdeng on 2020/11/11.
 */

public class diary {
    private String content;
    private String time;

    public diary(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void save() {
    }
}
