package ru.alexander.yourlocalfriend.packageDTO;

import java.util.Date;

/**
 * Created by ekaterina on 09/03/2018.
 */

public class Chat {
    private boolean seen;
    private Date timestamp;

    public Chat() {}

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
