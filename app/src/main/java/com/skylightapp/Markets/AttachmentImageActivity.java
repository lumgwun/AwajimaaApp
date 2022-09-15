package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.R;

public class AttachmentImageActivity extends BaseActivity {
    private static final String EXTRA_URL = "url";

    private ImageView imageView;
    private ProgressBar progressBar;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, AttachmentImageActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attachment_image);
        initUI();
        loadImage();
    }
    @Override
    protected View getSnackbarAnchorView() {
        return _findViewById(R.id.layout_root);
    }

    private void initUI() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        imageView = _findViewById(R.id.image_full_view);
        progressBar = _findViewById(R.id.progress_bar_show_image);
    }

    private void loadImage() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_error_black_24dp);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error_black_24dp)
                .dontTransform()
                .override(ConstsInterface.PREFERRED_IMAGE_SIZE_FULL, ConstsInterface.PREFERRED_IMAGE_SIZE_FULL)
                .into(imageView);
    }
}