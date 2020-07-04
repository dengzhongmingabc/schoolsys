package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class School extends EntityObj {

    @Column
    private String name;//学校名称

    @Column
    private String address;//地址

    @Column
    private String license;//营业执照

    @Column
    private String mobile;//手机

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
