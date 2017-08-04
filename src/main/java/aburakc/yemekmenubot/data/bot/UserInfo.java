package aburakc.yemekmenubot.data.bot;

import org.springframework.data.annotation.Id;

/**
 * Created by Burak on 29/07/2017.
 */
public class UserInfo {

    @Id
    private String id;

    private long chatId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
