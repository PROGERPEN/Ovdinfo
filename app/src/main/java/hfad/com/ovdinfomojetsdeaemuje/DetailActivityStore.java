package hfad.com.ovdinfomojetsdeaemuje;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivityStore extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_TEXT = "text";
    public static final String EXTRA_IMAGE = "image";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
         CollapsingToolbarLayout collapsingToolbar =
              (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String text = getIntent().getStringExtra(EXTRA_TEXT);
        String imageLink = getIntent().getStringExtra(EXTRA_IMAGE);
        collapsingToolbar.setTitle(title);

        TextView placeDetail = (TextView) findViewById(R.id.Title);
        placeDetail.setText(title);

        TextView placeLocation = (TextView) findViewById(R.id.place_location);
        placeLocation.setText(Html.fromHtml(text));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(imageLink).into(placePicutre);
    }
}