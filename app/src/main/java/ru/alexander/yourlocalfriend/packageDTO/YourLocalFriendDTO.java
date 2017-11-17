package ru.alexander.yourlocalfriend.packageDTO;

/**
 * Created by ekaterina on 12/09/2017.
 */

public class YourLocalFriendDTO {
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
}
