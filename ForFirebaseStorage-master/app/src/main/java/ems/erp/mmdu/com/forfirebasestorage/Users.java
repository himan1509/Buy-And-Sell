package ems.erp.mmdu.com.forfirebasestorage;

public class Users {

    private String name;
    private String gender;
    private  String email;
    private String phone;
    private  String language;
    private String location;
    private String additional1;
    private String additional2;
    private int itemSold;
    private int itemBought;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String userToken;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    private String profilePicture;

    public Users() {
    }

    public int getItemSold() {

        return itemSold;
    }

    public void setItemSold(int itemSold) {
        this.itemSold = itemSold;
    }

    public int getItemBought() {
        return itemBought;
    }

    public void setItemBought(int itemBought) {
        this.itemBought = itemBought;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdditional1() {
        return additional1;
    }

    public void setAdditional1(String additional1) {
        this.additional1 = additional1;
    }

    public String getAdditional2() {
        return additional2;
    }

    public void setAdditional2(String additional2) {
        this.additional2 = additional2;
    }

    public Users(String name, String gender, String email, String phone, String language, String location, String additional1, String additional2,String profilePicture) {

        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.location = location;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.profilePicture = profilePicture;
    }
}
