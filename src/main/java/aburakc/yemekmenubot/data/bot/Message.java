package aburakc.yemekmenubot.data.bot;

import org.springframework.data.annotation.Id;

/**
 * Created by Burak on 30/07/2017.
 */
public class Message {

    @Id
    private String id;
    private String message;
    private long chatId;
    private long userId;
    private String to;
    private String from;
    private Integer messageTime;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Integer messageTime) {
        this.messageTime = messageTime;
    }
}
