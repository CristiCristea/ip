package com.fiiLicence.fiiLicence.models.requests;

public class ModifyDateRequest {
    private int idCommitte;
    private String date;

    public int getIdCommitte() {
        return idCommitte;
    }

    public void setIdCommitte(int idCommitte) {
        this.idCommitte = idCommitte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
