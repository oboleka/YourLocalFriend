package ru.alexander.yourlocalfriend.packageDTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ekaterina on 12/09/2017.
 */

public class YourLocalFriendDTO implements Parcelable {
    private String yourLocalFriendName;
    private String yourLocalFriendAge;
    private String yourLocalFriendHobbies;

    public YourLocalFriendDTO(String yourLocalFriendName) {
        this.yourLocalFriendName = yourLocalFriendName;
    }

    public YourLocalFriendDTO(String yourLocalFriendName, String yourLocalFriendAge, String yourLocalFriendHobbies) {
        this.yourLocalFriendName = yourLocalFriendName;
        this.yourLocalFriendAge = yourLocalFriendAge;
        this.yourLocalFriendHobbies = yourLocalFriendHobbies;
    }

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

    }
}
