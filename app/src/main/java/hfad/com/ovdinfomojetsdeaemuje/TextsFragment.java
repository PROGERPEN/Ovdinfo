package hfad.com.ovdinfomojetsdeaemuje;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class TextsFragment extends Fragment {

    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<MyDataTexts> data_list;
    private CustomAdapterStore customAdapterStore;
    private GridLayoutManager gridLayoutManager;
    private Realm Realm2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_texts, container, false);
        Context context = view.getContext();
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.Recycler_View2);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView2.setLayoutManager(mLayoutManager);

        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder().name("RemindMe.DB2").build();
        Realm2.setDefaultConfiguration(config);

        customAdapterStore = new CustomAdapterStore(view.getContext(), data_list);
        data_list = new ArrayList<>();
        new ParseTask2().execute();
        getAllNewsList2();


        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        mRecyclerView2.setLayoutManager(gridLayoutManager);

        customAdapterStore = new CustomAdapterStore(view.getContext(), data_list);
        mRecyclerView2.setAdapter(customAdapterStore);

        return mRecyclerView2;
    }

    private class ParseTask2 extends AsyncTask<Void, Void, String> {

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
                JSONArray news = dataJsonObj.getJSONArray("texts");

                for (int i = 0; i < news.length(); i++) {
                    JSONObject newsObject = news.getJSONObject(i);

                    MyDataTexts data = new MyDataTexts(i, newsObject.getString("title"),
                            newsObject.getString("created"), "https://ovdinfo.org/" + newsObject.getString("path"),
                            newsObject.getString("text"), "https://ovdinfo.org/" + newsObject.getString("cover"));

                    setChatsList2(data);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            customAdapterStore.notifyDataSetChanged();
        }
    }

    public void setChatsList2(final MyDataTexts myDataRealm) {
        Realm realm = Realm2;


        try {
            realm = Realm2.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(myDataRealm);
                }
            });

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    MyDataTexts getAllNewsList2() {
        Realm realm = Realm2.getDefaultInstance();
        RealmResults<MyDataTexts> chatsModelResult = realm.where(MyDataTexts.class).findAll();
        MyDataTexts myDataTexts = null;
        for (int i = 1; i < chatsModelResult.size(); i++) {
            myDataTexts = chatsModelResult.get(i);
            data_list.add(myDataTexts);
        }
        return myDataTexts;
    }


}
