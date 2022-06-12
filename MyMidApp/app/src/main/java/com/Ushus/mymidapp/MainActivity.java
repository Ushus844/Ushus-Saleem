package com.Ushus.mymidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String path, response;
    private OkHttpClient client;
    private News news;
    private httpgetreq req;
    private List<News.Result>data;
    private ListView listView;
    private MyCustomAdapter myCustomAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        req = new httpgetreq();
        data = new ArrayList<>();
        listView = findViewById(R.id.newslist);

        try {
            path = "https://alasartothepoint.alasartechnologies.com/listItem.php?id=1";
            new GetDataFromServer().execute();
        }catch (Exception e)
        {

        }

    }

    private class GetDataFromServer extends AsyncTask<Void ,Void,Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressDialog.setMessage("Loading Please Wait");
                progressDialog.show();
            }catch (Exception e){}
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
                progressDialog.setCancelable(false);
            }

            myCustomAdapter  = new MyCustomAdapter(MainActivity.this,R.layout.news_list_item, data);
            listView.setAdapter(myCustomAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                client = new OkHttpClient();
                response = httpgetreq.GET(client,path);
                Gson gson = new Gson();
                Type type = new TypeToken<Collection<News>>()
                {}.getType();

                news = gson.fromJson(response,News.class);

                if(data.isEmpty())
                {
                    for(int index = 0; index < news.getData().size(); index++ )
                    {
                        data.add(news.getData().get(index));
                    }

                }
            }catch (IOException e){e.printStackTrace();}
            return  null;


        }
    }
}





