package ru.alexander.yourlocalfriend.packageDTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ekaterina on 12/09/2017.
 */

public class YourLocalFriendDTO implements Parcelable {
    private String yourLocalFriendName;
    private String yourLocalFriendAge;
    private String yourLocalFriendHobbies;
    private long friendId;

    public YourLocalFriendDTO(){}

    public YourLocalFriendDTO(String yourLocalFriendName) {
        this.yourLocalFriendName = yourLocalFriendName;
    }

    public YourLocalFriendDTO(String yourLocalFriendName, String yourLocalFriendAge, String yourLocalFriendHobbies, long Id) {
        this.yourLocalFriendName = yourLocalFriendName;
        this.yourLocalFriendAge = yourLocalFriendAge;
        this.yourLocalFriendHobbies = yourLocalFriendHobbies;
        this.friendId=Id;
    }

    protected YourLocalFriendDTO(Parcel in) {
        yourLocalFriendName = in.readString();
        yourLocalFriendAge = in.readString();
        yourLocalFriendHobbies = in.readString();
        friendId = in.readLong();
    }

    public static final Creator<YourLocalFriendDTO> CREATOR = new Creator<YourLocalFriendDTO>() {
        @Override
        public YourLocalFriendDTO createFromParcel(Parcel in) {
            return new YourLocalFriendDTO(in);
        }

        @Override
        public YourLocalFriendDTO[] newArray(int size) {
            return new YourLocalFriendDTO[size];
        }
    };

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setYourLocalFriendName(String yourLocalFriendName) {
        this.yourLocalFriendName = yourLocalFriendName;
    }

    public void setYourLocalFriendAge(String yourLocalFriendAge) {
        this.yourLocalFriendAge = yourLocalFriendAge;
    }

    public void setYourLocalFriendHobbies(String yourLocalFriendHobbies) {
        this.yourLocalFriendHobbies = yourLocalFriendHobbies;
    }

    public String getYourLocalFriendName() {

        return yourLocalFriendName;
    }

    public String getYourLocalFriendAge() {
        return yourLocalFriendAge;
    }

    public String getYourLocalFriendHobbies() {
        return yourLocalFriendHobbies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(yourLocalFriendName);
        dest.writeString(yourLocalFriendAge);
        dest.writeString(yourLocalFriendHobbies);
        dest.writeLong(friendId);
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId=friendId;
    }
}
