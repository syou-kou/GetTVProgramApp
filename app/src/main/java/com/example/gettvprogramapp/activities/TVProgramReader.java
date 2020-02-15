package com.example.gettvprogramapp.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class TVProgramReader extends AsyncTask<URL, Void, String> {

    private Activity activity;          // MainActivity

    public TVProgramReader(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    @Override
    protected void onCancelled(String s) {
    }

    @Override
    protected void onCancelled() {
    }

    @Override
    protected String doInBackground(URL... urls) {

        final URL url = urls[0];
        HttpURLConnection con = null;

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(true);
            con.connect();

            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                Toast.makeText(this.activity, "接続に失敗しました statusCode:" + statusCode, Toast.LENGTH_LONG).show();
                return null;
            }

            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            StringBuilder response = new StringBuilder();
            String line = null;

            while ((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            Toast.makeText(this.activity, response.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(this.activity, "例外が発生しました:" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return "テスト";
    }
}
