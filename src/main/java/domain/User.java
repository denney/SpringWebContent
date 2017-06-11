package domain;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/12.
 */

public class User implements Serializable{

    private Long id;
    private String userName;
    private String userpass;
    private String imgPath;

    public User() {
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public User(Long id, String userName, String userpass, String imgPath) {
        this.id = id;
        this.userName = userName;
        this.userpass = userpass;
        this.imgPath = imgPath;
    }
}
