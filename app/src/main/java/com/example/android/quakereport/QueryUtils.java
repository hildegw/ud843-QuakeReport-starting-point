package com.example.android.quakereport;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String USGS_REQUEST_URL =
            //"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-06-01&endtime=2017-06-03&minmagnitude=4";
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=4&limit=20000";

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthQuakeEntry} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthQuakeEntry> extractEarthquakes(Activity context, String earthquakeJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuakeEntry> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonEQRoot = new JSONObject(earthquakeJSON);
            JSONArray jsonEQFeatureArray = jsonEQRoot.getJSONArray("features");
            // looping through all Feature entries
            for (int i = 0; i < jsonEQFeatureArray.length(); i++) {
                JSONObject jsonEQfeature = jsonEQFeatureArray.getJSONObject(i);
                JSONObject jsonEQproperties = jsonEQfeature.getJSONObject("properties");
                Double mag = jsonEQproperties.getDouble("mag");
                String place = jsonEQproperties.getString("place");
                String time = jsonEQproperties.getString("time");
                String urlForEarthquake = jsonEQproperties.getString("url");
                //convert epoch time into date format
                Date dateEpoch = new Date(Long.parseLong(time));
                //add each earthquake event to data set
                earthquakes.add(new EarthQuakeEntry(context, mag, place, dateEpoch, urlForEarthquake));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }


    //added code to read JSON data from USGS site, see "did you feel it" app
    /**
     * Query the USGS dataset and return an {@link EarthQuakeEntry} object to represent a single earthquake.
     */
    public static ArrayList<EarthQuakeEntry> fetchEarthquakeData(Activity context) {
        // Create URL object
        URL url = createUrl(USGS_REQUEST_URL);
        if (url == null) {
            Log.e("url == null", "error!!!");
            return null;
        }
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an EarhQuakeEntry Array object
        ArrayList<EarthQuakeEntry> events = extractEarthquakes(context, jsonResponse);

        // Return the {@link Event}
        return events;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
            return null;        //how to handle the error????
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
