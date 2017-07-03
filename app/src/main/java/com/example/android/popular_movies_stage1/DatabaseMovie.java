package com.example.android.popular_movies_stage1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Admin on 6/28/2017.
 */

public class DatabaseMovie {
    final private static String API_KEY = ""; // API KEY HERE
    final private static String API_KEY_PARAM_NAME = "api_key";

    final private static String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    final public static String TOP_RATED = "top_rated";
    final public static String POPULAR = "popular";

    final private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    final private static String IMAGE_SIZE = "w185/";

    public static String getMovies(String type) {
        URL url = buildURL(type);

        String response = null;
        try {
            response = getResponseFromHttpUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String buildImageUrl(String imagePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE + imagePath;
    }

    private static URL buildURL(String sort) {
        Uri uri = Uri.parse(API_BASE_URL + sort)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM_NAME, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
