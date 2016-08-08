package com.ogettoweb.dealers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dealer implements Parcelable {
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

    public Dealer(String company) {
        this.company = company;
    }

    protected Dealer(Parcel in) {
        company = in.readString();
        country = in.readString();
        city = in.readString();
        address = in.readString();
        goods = in.createStringArrayList();
        phones = in.createStringArrayList();
        emails = in.createStringArrayList();
        website = in.readString();
    }

    public static final Creator<Dealer> CREATOR = new Creator<Dealer>() {
        @Override
        public Dealer createFromParcel(Parcel in) {
            return new Dealer(in);
        }

        @Override
        public Dealer[] newArray(int size) {
            return new Dealer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(address);
        dest.writeStringList(goods);
        dest.writeStringList(phones);
        dest.writeStringList(emails);
        dest.writeString(website);
    }

    public String getGoodsAsString() {
        StringBuilder goodsString = new StringBuilder();
        if (goods == null) {
            return null;
        }
        for (String good : goods) {
            goodsString.append(good + ", ");
        }
        if (goodsString.length() > 0) {
            goodsString.deleteCharAt(goodsString.length() - 2);
        }
        return goodsString.toString();
    }

    public String getContactsAsString() {
        StringBuilder phoneString = new StringBuilder();
        if (goods == null) {
            return null;
        }
        for (String phone : phones) {
            phoneString.append(phone + ", ");
        }
        if (phoneString.length() > 0) {
            phoneString.deleteCharAt(phoneString.length() - 2);
        }
        return phoneString.toString();
    }
}
