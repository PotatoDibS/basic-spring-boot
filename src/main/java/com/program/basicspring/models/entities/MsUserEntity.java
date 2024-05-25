package com.program.basicspring.models.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ms_user", schema = "sch_system")
public class MsUserEntity {
    
    @Id
    private String userId;
    private String userName;
    private String userPassword;
    private String aktif;
    private String inputBy;
    private Date inputDt;
    private String updateBy;
    private Date updateDt;


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAktif() {
        return this.aktif;
    }

    public void setAktif(String aktif) {
        this.aktif = aktif;
    }

    public String getInputBy() {
        return this.inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy;
    }

    public Date getInputDt() {
        return this.inputDt;
    }

    public void setInputDt(Date inputDt) {
        this.inputDt = inputDt;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

}
