package com.shopping.pbl_last;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SearchActivity extends AppCompatActivity
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String search_name;
    private int category =1;
    private ImageView imageView;

    private EditText test;
    private EditText test2;

    private String img_url;

    private String price;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        test = (EditText) findViewById(R.id.test);
        test2 = (EditText) findViewById(R.id.test);
        imageView = (ImageView) findViewById(R.id.imgView);
        Intent intent = getIntent();
        if (intent != null) {
            search_name = intent.getStringExtra("search_name");
            category = intent.getIntExtra("category", 1);
        }

        CollectionReference nameRef = db.collection("datas");

        nameRef
                .whereEqualTo("name", search_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                if(document == null)
                                {
                                    test.setText("fail");
                                }

                                img_url = document.get("img").toString();
                                price = document.get("price").toString();

                                upload();

                                test.setText(search_name + "/" + price + "원");
                            }
                        }

                        else{
                            test.setText("fail");
                        }

                    }
                });
    }

    public void upload() {  // ImageView에 사진 업로드
        Glide.with(this)
                .load(img_url)
                .into(imageView);
    }


}