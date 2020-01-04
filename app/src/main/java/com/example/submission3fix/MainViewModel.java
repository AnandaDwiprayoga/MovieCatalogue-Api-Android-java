package com.example.submission3fix;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private final String END_POINTS_IMAGE = "http://image.tmdb.org/t/p/w185/";
    private MutableLiveData<ArrayList<ItemsData>> listDatas = new MutableLiveData<>();
    private MutableLiveData<ItemsData> itemData = new MutableLiveData<>();

    void setData(final String resources, String language){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ItemsData> datas = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/" + resources +"?api_key=" + BuildConfig.MOVIE_API_KEY +"&language=" + language;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result  = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray resultArray = responseObject.getJSONArray("results");

                    for (int i = 0; i < 20; i++){
                        if (resultArray.getJSONObject(i) != null){
                            JSONObject listResult = resultArray.getJSONObject(i);

                            ItemsData data = new ItemsData();

                            if (resources.equals("movie")) {
                                data.setDatePublish(listResult.getString("release_date"));
                                data.setTitle(listResult.getString("title"));
                            }else {
                                data.setDatePublish(listResult.getString("first_air_date"));
                                data.setTitle(listResult.getString("original_name"));
                            }

                            data.setDesc(listResult.getString("overview"));
                            data.setId(listResult.getInt("id"));
                            data.setProfile(END_POINTS_IMAGE + listResult.getString("poster_path"));

                            datas.add(data);
                        }
                    }
                    listDatas.postValue(datas);
                }catch (Exception e){
                    Log.d("ERROR", "error becauese : " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("ERROR", "error becauese : " + error.getMessage());
            }
        });
    }

    void setDataDetail(final String resources, int id, String langueage){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/"+ resources +"/" + id +"?api_key="+ BuildConfig.MOVIE_API_KEY +"&language=" + langueage;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody);
                    JSONObject result = new JSONObject(response);

                    ItemsData data = new ItemsData();

                    if (resources.equals("movie")){
                        data.setTitle(result.getString("original_title"));
                        data.setDatePublish(result.getString("release_date"));
                    }else{
                        data.setTitle(result.getString("original_name"));
                        data.setDatePublish(result.getString("first_air_date"));
                    }

                    data.setBackdroph(END_POINTS_IMAGE + result.getString("backdrop_path"));
                    data.setProfile(END_POINTS_IMAGE + result.getString("poster_path"));
                    data.setPopularity(String.valueOf(result.getInt("popularity")));
                    data.setDesc(result.getString("overview"));

                    itemData.postValue(data);
                }catch (Exception e){
                    Log.d("ERROR", "Error because " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("ERROR", "Error because " + error.getMessage());
            }
        });
    }

    LiveData<ArrayList<ItemsData>> getData(){
        return listDatas;
    }

    LiveData<ItemsData> getDataDetail(){
        return itemData;
    }
}
