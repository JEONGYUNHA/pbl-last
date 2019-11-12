package com.shopping.pbl_last;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView)findViewById(R.id.img);
        TextView name = (TextView)findViewById(R.id.name);
        TextView price = (TextView)findViewById(R.id.price);
        TextView category = (TextView)findViewById(R.id.category);
        TextView country = (TextView)findViewById(R.id.country);

        Intent intent = getIntent();

        String img = intent.getExtras().getString("img");
        Glide.with(this)
                .load(img)
                .into(imageView);

        String nameV = intent.getExtras().getString("name");
        name.setText(nameV);

        int priceV = Integer.parseInt(intent.getExtras().getString("price"));
        price.setText(String.valueOf(priceV));

        String categoryV = intent.getExtras().getString("category");
        category.setText(categoryV);

        String countryV = intent.getExtras().getString("country");
        country.setText(countryV);

    }

}
