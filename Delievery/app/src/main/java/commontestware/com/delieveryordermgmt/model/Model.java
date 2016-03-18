package commontestware.com.delieveryordermgmt.model;

/**
 * Created by BLT0037 on 9/22/2015.
 */
public class Model {

    private String name;
    private String mail;
    private String password;
    private String confirmpassword;
    private String profile;
    private String gender;

    public Model() {
    }


    public Model(String name, String mail, String password, String confirmpassword, String profile, String gender) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.profile = profile;
        this.gender = gender;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
