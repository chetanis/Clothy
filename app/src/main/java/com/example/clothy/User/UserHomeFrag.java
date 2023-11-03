package com.example.clothy.User;

import android.annotation.SuppressLint;
import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.example.clothy.adapter.UserProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserHomeFrag extends Fragment {
    View mview;
    CardView man,woman,kids,pets;
    RecyclerView user_productlist;
    TextView show_all;
    EditText Search_bar;
    DatabaseReference productref;
    ArrayList<Product> products;
    LinearLayout focus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_user_home, container, false);
        elem();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        user_productlist.setLayoutManager(manager);
        initadApter();


        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ViewAllProductsActivity.class);
                i.putExtra("cat","Man");
                startActivity(i);
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ViewAllProductsActivity.class);
                i.putExtra("cat","Woman");
                startActivity(i);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ViewAllProductsActivity.class);
                i.putExtra("cat","Child");
                startActivity(i);
            }
        });
        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ViewAllProductsActivity.class);
                i.putExtra("cat","Pets");
                startActivity(i);
            }
        });
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ViewAllProductsActivity.class);
                i.putExtra("cat","Just Arrived");
                startActivity(i);
            }
        });

        Search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    if (Search_bar.getText().toString().isEmpty()){}
                    else{ performSearch(Search_bar.getText().toString());}
                    return true;
                }
                return false;
            }
        });
        focus.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Search_bar.clearFocus();
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(Search_bar.getWindowToken(),0);
                return false;
            }
        });



        return mview;
    }

    @Override
    public void onResume() {
        super.onResume();
        Search_bar.setText("");
        Search_bar.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(Search_bar.getWindowToken(),0);

    }


    private void elem(){
        user_productlist = mview.findViewById(R.id.user_productlist);
        productref = FirebaseDatabase.getInstance(getContext().getString(R.string.DB))
                .getReference().child("products");
        man =mview.findViewById(R.id.man);
        woman =mview.findViewById(R.id.woman);
        kids =mview.findViewById(R.id.kids);
        pets =mview.findViewById(R.id.pets);
        show_all = mview.findViewById(R.id.show_all);
        Search_bar = mview.findViewById(R.id.SearchView);
        focus = mview.findViewById(R.id.focus);
    }

    private void initadApter(){
        products = new ArrayList<>();
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    Product product = oneSnapshot.getValue(Product.class);
                    products.add(product);
                }
                UserProductAdapter adapter = new UserProductAdapter(getContext(),products,true);
                user_productlist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void performSearch(String string){
        ArrayList<Product> searched = new ArrayList<>();
        Search_bar.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(Search_bar.getWindowToken(),0);
        for (Product product : products){
            if (product.getProductName().toLowerCase().contains(string.toLowerCase())){
                searched.add(product);
            }
        }
        Intent i = new Intent(getContext(), SearchResultActivity.class);
        i.putExtra("search_result",searched);
        startActivity(i);

    }
}