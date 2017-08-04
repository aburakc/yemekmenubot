package aburakc.yemekmenubot.data.menu;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class LaunchMenu {

    @Id
    private String id;
    private String uid;
    private Date date;
    private Long formattedDate;
    private String menu;
    private String summary;
    private String imageUrl;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(Long formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
