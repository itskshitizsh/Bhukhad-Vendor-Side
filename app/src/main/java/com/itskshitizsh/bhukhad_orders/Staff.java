package com.itskshitizsh.bhukhad_orders;

class Staff {
    private String Name;
    private String Password = "";
    private String PhoneNo;

    public Staff(String name, String password, String phoneNo) {
        this.Name = name;
        this.Password = password;
        this.PhoneNo = phoneNo;
    }

    public Staff() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.PhoneNo = phoneNo;
    }

}
