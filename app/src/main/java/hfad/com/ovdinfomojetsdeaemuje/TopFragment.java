package hfad.com.ovdinfomojetsdeaemuje;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class TopFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapterREALM customAdapterREALM;
    private GridLayoutManager gridLayoutManager;
    private Realm Realm;
    public List<MyDataRealm> data_listREALM;
    private SwipeRefreshLayout mSwipeRefreshLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler_View);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.top_fragment_swipe_refresh_layout);

        Realm.init(view.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("RemindMe.DB").build();
        Realm.setDefaultConfiguration(config);

        customAdapterREALM = new CustomAdapterREALM(view.getContext(), data_listREALM);
        data_listREALM = new ArrayList<>();
        new ParseTask().execute();
        getAllNewsList();

        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        customAdapterREALM = new CustomAdapterREALM(view.getContext(), data_listREALM);
        mRecyclerView.setAdapter(customAdapterREALM);

        //mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new ParseTask().execute();
                        getAllNewsList();
                    }
                }, 500);
            }
        });

        return mRecyclerView;
    }

    @Override
    public void onRefresh() {
        new ParseTask().execute();
        getAllNewsList();
    }


    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";


        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("https://ovdinfo.org/mobile/json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            JSONObject dataJsonObj = null;

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray news = dataJsonObj.getJSONArray("news");

                for (int i = 0; i < news.length(); i++) {
                    JSONObject newsObject = news.getJSONObject(i);

                    //MyData data = new MyData(newsObject.getInt("nid"), newsObject.getString("title"),
                      //      newsObject.getString("created"), "https://ovdinfo.org/" + newsObject.getString("path"),
                        //    newsObject.getString("text"));
                    //data_list.add(data);


                    MyDataRealm dataRealm = new MyDataRealm(i, newsObject.getString("title"),
                            newsObject.getString("created"), newsObject.getString("path"),
                            newsObject.getString("text"));


                        setChatsList(dataRealm);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            customAdapterREALM.notifyDataSetChanged();
        }

    }



    public void setChatsList(final MyDataRealm myDataRealm) {
        Realm realm2 = Realm;


        try {
            realm2 = Realm.getDefaultInstance();

            realm2.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(myDataRealm);
                }
            });

        } finally {
            if (realm2 != null) {
                realm2.close();
            }
        }
    }

    MyDataRealm getAllNewsList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MyDataRealm> chatsModelResult = realm.where(MyDataRealm.class).findAll();
        MyDataRealm myDataRealm = null;
        for (int i = 1; i < chatsModelResult.size(); i++) {
            myDataRealm = chatsModelResult.get(i);
            data_listREALM.add(myDataRealm);
        }
        return myDataRealm;
    }

}
