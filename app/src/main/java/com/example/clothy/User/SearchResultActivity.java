package com.example.clothy.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.adapter.UserProductAdapter;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    ArrayList<Product> search_result;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        TextView lolk =findViewById(R.id.searchrsult);
        list = findViewById(R.id.recycler_search_result);
        search_result = (ArrayList<Product>) getIntent().getSerializableExtra("search_result");
        if (search_result.isEmpty()){
            lolk.setVisibility(View.VISIBLE);
        }else {
            UserProductAdapter adapter = new UserProductAdapter(this,search_result,false);
            list.setAdapter(adapter);
            GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
            list.setLayoutManager(gridLayoutManager);
        }

    }
}