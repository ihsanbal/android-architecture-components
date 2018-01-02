package com.example.arc.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ihsan on 12/3/17.
 */
@Entity
public class Source extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "news_id")
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    @Ignore
    private boolean isSelected;

    public Source() {
        //Default constructor
    }

    public Source(int uid, String id, String name, String description, String url, String category,
                  String language, String country, boolean isSelected) {
        this.uid = uid;
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.category);
        dest.writeString(this.language);
        dest.writeString(this.country);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected Source(Parcel in) {
        this.uid = in.readInt();
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.category = in.readString();
        this.language = in.readString();
        this.country = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}
