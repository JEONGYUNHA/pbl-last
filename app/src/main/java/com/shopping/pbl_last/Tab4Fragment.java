package com.shopping.pbl_last;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab4Fragment";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<MyData> mData = new ArrayList<MyData>();
    private GridView mGrid;
    private MyAdapter mAdapter;
    View view;
    int count=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_fragment, container, false);

        //int position = tabLayout.getSelectedTabPosition();
        // String category = mViewPager.getAdapter().getPageTitle(position).toString();
        String category = "정육/계란";
        db.collection("datas")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // document.getId() -> document 이름, document.getData() -> document의 모든 정보
                                /*test.append(document.getId() + " + " + document.getData().toString());*/
                                if(count==0) {
                                    mData.add(new MyData(document.get("img").toString(), document.get("name").toString(), Integer.parseInt(document.get("price").toString())));
                                }
                            }
                            upload();
                            count++;
                        } else {

                        }
                    }
                });
        return view;
    }

    public void upload() {
        mGrid = (GridView) view.findViewById(R.id.grid);
        mAdapter = new MyAdapter(this.getActivity(), mData);
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position %= mData.size();
                itemView(mData.get(position).name);
                //Toast.makeText(MainActivity.this, mData.get(position).name + " 선택!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void itemView(String search) { // item 정보 불러오기
        db.collection("datas")
                .whereEqualTo("name", search)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String datas[] = new String[5];
                                datas[0] = document.get("img").toString();
                                datas[1] = document.get("name").toString();
                                datas[2] = document.get("price").toString();
                                datas[3] = document.get("category").toString();
                                datas[4] = document.get("country").toString();

                                if(mOnMyListener!=null)
                                    mOnMyListener.onReceivedData(datas);
                            }
                        } else {
                        }
                    }
                });
    }

    public interface OnMyListener{
        void onReceivedData(Object data);
    }
    private Tab1Fragment.OnMyListener mOnMyListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!=null&&getActivity() instanceof Tab1Fragment.OnMyListener){
            mOnMyListener = (Tab1Fragment.OnMyListener) getActivity();
        }
    }
}