package com.example.submission3fix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_ID = "key_id";
    public static final String ARG_RESOURCES = "resources";
    private ImageView imgPoster;
    private ImageView backdrop;
    private TextView datePublish,tvPopularity,descriptioin, title, labelOverview;
    private ProgressBar progressBar;
    private View viewOpacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar                 = findViewById(R.id.progress_detail);
        imgPoster                   = findViewById(R.id.iv_poster_detail);
        backdrop                    = findViewById(R.id.iv_backdrop);
        title                       = findViewById(R.id.tv_title_detail);
        datePublish                 = findViewById(R.id.tv_date_detail);
        tvPopularity                = findViewById(R.id.tv_popularity);
        descriptioin                = findViewById(R.id.tv_desc_detail);
        viewOpacity                 = findViewById(R.id.view_opacity);
        labelOverview               = findViewById(R.id.tv_label_overeview);

        progressBar.setVisibility(View.VISIBLE);

        int id = getIntent().getIntExtra(KEY_ID, -1);
        String resources = getIntent().getStringExtra(ARG_RESOURCES);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        String currentLanguage = Locale.getDefault().getLanguage();

        if (id != -1 && resources != null){
            if (currentLanguage.equals("en")){
                mainViewModel.setDataDetail(resources,id,"en-US");
            }else if (currentLanguage.equals("in")){
                mainViewModel.setDataDetail(resources,id,"id-ID");
            }
        }

        mainViewModel.getDataDetail().observe(this, new Observer<ItemsData>() {
            @Override
            public void onChanged(ItemsData itemsData) {
                if (itemsData != null){
                    showAllComponent();

                    loadImage(itemsData.getProfile(),imgPoster);
                    loadImage(itemsData.getBackdroph(), backdrop);
                    title.setText(itemsData.getTitle());
                    //untuk menghandle pergantian bahasa
                    descriptioin.setText(itemsData.getDesc().isEmpty() ? getResources().getString(R.string.not_supported) : itemsData.getDesc());

                    String date = "<b>" + getResources().getString(R.string.label_date)  + "</b>" + itemsData.getDatePublish();
                    String popularity = "<b>" + getResources().getString(R.string.label_popularity) + "</b>" + itemsData.getPopularity();

                    datePublish.setText(Html.fromHtml(date));
                    tvPopularity.setText(Html.fromHtml(popularity));

                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void showAllComponent() {
        viewOpacity.setVisibility(View.VISIBLE);
        imgPoster.setVisibility(View.VISIBLE);
        backdrop.setVisibility(View.VISIBLE);
        labelOverview.setVisibility(View.VISIBLE);
    }

    private void loadImage(String url,ImageView view){
        Glide.with(getApplicationContext())
                .load(url)
                .apply(new RequestOptions())
                .into(view);
    }
}
