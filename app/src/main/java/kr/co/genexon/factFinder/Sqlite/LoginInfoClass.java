package kr.co.genexon.factFinder.Sqlite;

/**
 * Created by topgu on 2018-04-16.
 */

public class LoginInfoClass {

    public int _id;
    public String mb_id;
    public String cust_id;
    public String password;

    public LoginInfoClass() {

    }

    public LoginInfoClass(int _id, String mbId, String custId, String passWd) {
        this._id = _id;
        this.mb_id = mbId;
        this.cust_id = custId;
        this.password = passWd;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMb_id() {
        return mb_id;
    }

    public void setMb_id(String mbId) {
        this.cust_id = mbId;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String custId) {
        this.cust_id = custId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWd) {
        this.password = passWd;
    }
}
