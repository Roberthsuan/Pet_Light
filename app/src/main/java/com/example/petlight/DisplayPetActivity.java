package com.example.petlight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayPetActivity extends AppCompatActivity {

    private ImageView displayImageView;
    private TextView textViewName, textViewBreed, textViewLocation, textViewPhone;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pet); // 確認使用了正確的佈局

        displayImageView = findViewById(R.id.displayImageView);
        textViewName = findViewById(R.id.textView_name);
        textViewBreed = findViewById(R.id.textView_breed);
        textViewLocation = findViewById(R.id.textView_location);
        textViewPhone = findViewById(R.id.textView_phone);
        buttonBack = findViewById(R.id.button_back);

        // 從 Intent 中接收傳遞過來的數據
        String name = getIntent().getStringExtra("pet_name");
        String breed = getIntent().getStringExtra("pet_breed");
        String location = getIntent().getStringExtra("pet_location");
        String phone = getIntent().getStringExtra("pet_phone");
        byte[] imageBytes = getIntent().getByteArrayExtra("pet_image");

        // 設置接收到的數據到對應的視圖
        textViewName.setText("寵物姓名: " + name);
        textViewBreed.setText("寵物品種: " + breed);
        textViewLocation.setText("走失地點: " + location);
        textViewPhone.setText("聯絡電話: " + phone);

        // 將圖片 byte array 轉換成 Bitmap 並顯示
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            displayImageView.setImageBitmap(bitmap);
        }

        // 返回按鈕的功能
        buttonBack.setOnClickListener(v -> finish());
    }
}
