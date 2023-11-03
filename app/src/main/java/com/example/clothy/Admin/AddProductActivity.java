package com.example.clothy.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothy.Models.Product;
import com.example.clothy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    ImageView selectProduct;
    EditText ProductName,ProductDesc,ProductPrice;
    TextView select_cat;
    Button SendProduct;
    Uri Image_file;
    StorageReference productImageRef;
    DatabaseReference productRef;
    String imageUrl;
    ProgressDialog loading;
    String[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        elem();

        ActivityResultLauncher<Intent> openGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            Image_file = data.getData();
                            selectProduct.setImageURI(Image_file);
                        }
                    }
                });

        selectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryResult.launch(OpenGallery());
            }

        });

        select_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder =new AlertDialog.Builder(AddProductActivity.this);
                mbuilder.setTitle("Select category");
                mbuilder.setSingleChoiceItems(categories, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        select_cat.setText(categories[i]);
                        dialogInterface.dismiss();
                    }
                });
                mbuilder.show();
            }
        });

        SendProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckFields()){
                    UploadProduct();
                }
            }
        });
    }




    private void elem(){
        selectProduct = findViewById(R.id.select_img);
        ProductName=findViewById(R.id.product_name);
        ProductDesc=findViewById(R.id.product_desc);
        ProductPrice=findViewById(R.id.product_price);
        select_cat = findViewById(R.id.select_cat);
        SendProduct = findViewById(R.id.add_product);
        productImageRef = FirebaseStorage.getInstance().getReference().child("products images");
        loading = new ProgressDialog(this);
        loading.setTitle("Uploading the product");
        loading.setMessage("Please wait uploading the product");
        categories = new String[]{"Man","Woman","Child","Pets"};
    }
    private Intent OpenGallery(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        return i;
    }

    private String Generate(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyyMMdd");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        return saveCurrentDate+saveCurrentTime;
    }

    private  void saveProductInDB(Product product){
        productRef.setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddProductActivity.this, "Product added", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    finish();
                }else{
                    loading.dismiss();
                    Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean CheckFields(){
        if (Image_file==null){
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;
        }else if (ProductName.getText().toString().isEmpty()) {
            ProductName.setError("Please fill this field");
            return false;
        }else if (ProductDesc.getText().toString().isEmpty()) {
                ProductDesc.setError("Please fill this field");
                return false;
        }else if (select_cat.getText().toString().isEmpty()){
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }else if (ProductPrice.getText().toString().isEmpty()){
            ProductPrice.setError("Please fill this field");
            return false;
        }else{
            return true;
        }

    }

    private void UploadProduct(){
        loading.show();
        String Id = Generate();
        productImageRef.child(Id+".jpeg").putFile(Image_file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    productImageRef.child(Id+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            String id;
                            productRef = FirebaseDatabase.getInstance(getString(R.string.DB)).getReference().child("products");
                            productRef = productRef.push();
                            id = productRef.getKey();
                            Product product = new Product(imageUrl,ProductName.getText().toString(),ProductDesc.getText().toString(),select_cat.getText().toString(),id,Integer.valueOf(ProductPrice.getText().toString()));
                            saveProductInDB(product);
                        }
                    });
                }else {
                    if(task.getException().toString().equals("com.google.firebase.storage.StorageException: User does not have permission to access this object.")){
                        loading.dismiss();
                        Toast.makeText(AddProductActivity.this, "you don't have permission", Toast.LENGTH_SHORT).show();
                    }else {
                        loading.dismiss();
                        Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

}