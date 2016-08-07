package com.ogettoweb.dealers.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dealer {
    @SerializedName("entity_id")
    private Integer id;
    @SerializedName("company")
    private String company;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("goods")
    private List<String> goods;
    @SerializedName("phones")
    private List<String> phones;
    @SerializedName("emails")
    private List<String> emails;
    @SerializedName("website")
    private String website;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("longitude")
    private Float longitude;

    public Integer getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getGoods() {
        return goods;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public String getWebsite() {
        return website;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
}
