package com.example.quoteapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private static final String PREF_NAME = "daily_spark_favorites";
    private static final String KEY_FAVORITES = "favorites_list";
    private final SharedPreferences prefs;

    public FavoritesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public List<QuoteDatabase.Quote> getFavorites() {
        List<QuoteDatabase.Quote> favorites = new ArrayList<>();
        String json = prefs.getString(KEY_FAVORITES, "[]");
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                favorites.add(new QuoteDatabase.Quote(
                        obj.getString("text"),
                        obj.getString("author"),
                        obj.getString("category")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public void saveFavorites(List<QuoteDatabase.Quote> favorites) {
        JSONArray array = new JSONArray();
        try {
            for (QuoteDatabase.Quote q : favorites) {
                JSONObject obj = new JSONObject();
                obj.put("text", q.getText());
                obj.put("author", q.getAuthor());
                obj.put("category", q.getCategory());
                array.put(obj);
            }
            prefs.edit().putString(KEY_FAVORITES, array.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isFavorited(QuoteDatabase.Quote quote) {
        List<QuoteDatabase.Quote> favorites = getFavorites();
        for (QuoteDatabase.Quote q : favorites) {
            if (q.getText().equals(quote.getText())) {
                return true;
            }
        }
        return false;
    }

    public void toggleFavorite(QuoteDatabase.Quote quote) {
        List<QuoteDatabase.Quote> favorites = getFavorites();
        boolean removed = false;
        
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getText().equals(quote.getText())) {
                favorites.remove(i);
                removed = true;
                break;
            }
        }
        
        if (!removed) {
            favorites.add(quote);
        }
        
        saveFavorites(favorites);
    }
    
    public void removeFavorite(QuoteDatabase.Quote quote) {
        List<QuoteDatabase.Quote> favorites = getFavorites();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getText().equals(quote.getText())) {
                favorites.remove(i);
                break;
            }
        }
        saveFavorites(favorites);
    }
}
