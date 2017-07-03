package com.example.android.popular_movies_stage1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 6/28/2017.
 */

public class JsonParser {

    public static List<EntityMovie> parseJson(String json) {
        List<EntityMovie> movieEntities = new ArrayList<EntityMovie>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                movieEntities.add(new EntityMovie(
                        result.getInt("id"),
                        result.getString("original_title"),
                        DatabaseMovie.buildImageUrl(result.getString("poster_path")),
                        result.getString("overview"),
                        result.getDouble("vote_average"),
                        result.getString("release_date")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieEntities;
    }
}
