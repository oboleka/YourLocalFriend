package ru.alexander.yourlocalfriend.packageDTO;

/**
 * Created by ekaterina on 18/04/2018.
 */

public class Message {
    private String message, type;
    private boolean seen;
    private long time;
    private String from;

    public Message() {
    }

    public Message(String message, String type, boolean seen, long time) {
        this.message = message;
        this.from = from;
        this.type = type;
        this.seen = seen;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
