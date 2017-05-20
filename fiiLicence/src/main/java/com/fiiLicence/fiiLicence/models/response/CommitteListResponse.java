package com.fiiLicence.fiiLicence.models.response;

public class CommitteListResponse {
    private int id;
    private String numeComisie;
    private String beginDate;
    private String endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeComisie() {
        return numeComisie;
    }

    public void setNumeComisie(String numeComisie) {
        this.numeComisie = numeComisie;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
