package com.example.kids.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class KiddiesDataHolder {

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public KiddiesDataHolder(Activity activity) {
        this.sharedPreferences = activity.getSharedPreferences("KIDDIES", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public <T> T get(Class<T> clazz) {
        return gson.fromJson(sharedPreferences.getString(clazz.getSimpleName(), null), clazz);
    }

    public <T> void put(T object) {
        sharedPreferences.edit().putString(object.getClass().getSimpleName(), gson.toJson(object))
                .apply();
    }
}
