package com.example.david.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by david on 11/7/15.
 */
public class Event implements Parcelable {

    private static DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    String name;
    Date creation_date;
    String owner;
    List<String> photos;
    List<String> contributors;
    String access_code;

    public Event(String name, Date creation_date, String owner, List<String> photos, List<String> contributors, String access_code) {
        this.name = name;
        this.creation_date = creation_date;
        this.owner = owner;
        this.photos = photos;
        this.contributors = contributors;
        this.access_code = access_code;
    }

    public static Event fromJson(JSONObject json) throws JSONException, ParseException {
        // Date creation_date = (Date)json.get("creation_date");
        Log.e("EVENT JSON", json.getString("creation_date"));
        Date creation_date = Event.getDateFromString(json.getString("creation_date"));
        List<String> photos = Event.convertJsonArrayToList(json.getJSONArray("photos"));
        List<String> contributors = Event.convertJsonArrayToList(json.getJSONArray("contributors"));
        return new Event(json.getString("name"),
                creation_date,
                json.getString("owner"),
                photos,
                contributors,
                json.getString("access_code"));
    }

    private static List<String> convertJsonArrayToList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.get(i).toString());
        }
        return list;
    }

    public static Date getDateFromString(String date_string) throws ParseException {
        return Event.dateFormat.parse(date_string);
    }

    // The Parcelable interface

    protected Event(Parcel in) {
        name = in.readString();
        owner = in.readString();
        photos = in.createStringArrayList();
        contributors = in.createStringArrayList();
        access_code = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(owner);
        dest.writeStringList(photos);
        dest.writeStringList(contributors);
        dest.writeString(access_code);
    }
}
