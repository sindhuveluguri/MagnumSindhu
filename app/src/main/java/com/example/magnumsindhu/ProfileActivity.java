package com.example.magnumsindhu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.magnumsindhu.MainActivity.EXTRA_ID;
import static com.example.magnumsindhu.MainActivity.EXTRA_LOGIN;
import static com.example.magnumsindhu.MainActivity.EXTRA_URL;

public class ProfileActivity extends AppCompatActivity {

    private Button btnprof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnprof = findViewById(R.id.btn_profile_viewProfile);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String LoginName = intent.getStringExtra(EXTRA_LOGIN);
        String LoginId = intent.getStringExtra(EXTRA_ID);

        ImageView imageView = findViewById(R.id.iv_profile_logo);
        TextView tvName = findViewById(R.id.tv_profile_loginName);
        TextView tvId = findViewById(R.id.tv_profile_LoginId);

        Picasso.get().load(imageUrl).into(imageView);
        tvName.setText(LoginName);
        tvId.setText(LoginId);

        btnprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/saransh2405"));
                startActivity(intent);
            }
        });
    }
}
