package com.testgartner.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        final String fileNameCapterra = "capterra.yaml";
        final String fileNameSoftwareadvice = "softwareadvice.json";

        Constructor constructor = new Constructor(YamlConfig.class);
        Yaml yaml = new Yaml( constructor );

        InputStream inputYaml = null;
        InputStream  inputJson = null;

        try {

            //asset folder is the feed-products

            //get capterra data from assets folder
            inputYaml = getResources().getAssets().open(fileNameCapterra);
            //read yaml file
            YamlConfig data = yaml.loadAs( inputYaml, YamlConfig.class );
            //print data
            //System.out.println( yaml.dump( data ).toString());


            // get TextView from layout
            TextView yamlText = (TextView)findViewById(R.id.yamlText);
            //put text from yaml file to view
            yamlText.setText(yaml.dump( data ));

            //get softwareadvice data from assets folder
            inputJson = getResources().getAssets().open(fileNameSoftwareadvice);

            //read Json file
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArry = obj.getJSONArray("products");
            //print data
            System.out.println( jsonArry );

            // get TextView from layout
            TextView softwareadviceText = (TextView)findViewById(R.id.softwareadvice);
            //put text from yaml file to view
            softwareadviceText .setText(jsonArry.toString());


            //parser Json and  put in arraylist formList
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            // parser Json file.
            // this is a format that I have used in my previous implementations

            for (int i = 0; i < jsonArry.length(); i++) {
                JSONObject json = jsonArry.getJSONObject(i);

                //Add the values in your `ArrayList`
                m_li = new HashMap<String, String>();

                if (json.has("categories")) {
                String categories_value = json.getString("categories");
                    m_li.put("categories", categories_value);
                }
                if (json.has("twitter")) {
                    String twitter_value = json.getString("twitter");
                    m_li.put("twitter", twitter_value);
                }
                if (json.has("title")) {
                    String title_value = json.getString("title");
                    m_li.put("title", title_value);
                }
                // add into the array
                formList.add(m_li);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("softwareadvice.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
