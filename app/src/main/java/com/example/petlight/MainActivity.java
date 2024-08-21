package com.example.petlight;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText editTextName, editTextBreed, editTextLocation, editTextPhone;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button buttonUpload = findViewById(R.id.button_upload);
        Button buttonConfirm = findViewById(R.id.button_confirm);
        Button buttonCancel = findViewById(R.id.button_cancel);
        editTextName = findViewById(R.id.editText_name);
        editTextBreed = findViewById(R.id.editText_breed);
        editTextLocation = findViewById(R.id.editText_location);
        editTextPhone = findViewById(R.id.editText_phone);

        buttonUpload.setOnClickListener(v -> openImageChooser());

        buttonConfirm.setOnClickListener(v -> showConfirmationDialog());

        buttonCancel.setOnClickListener(v -> finish()); // 返回並結束活動
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "選擇圖片"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("確認儲存")
                .setMessage("您確認要儲存這些資訊嗎？")
                .setPositiveButton("確認", (dialog, which) -> savePetInfo())
                .setNegativeButton("取消", null)
                .show();
    }

    private void savePetInfo() {
        String name = editTextName.getText().toString();
        String breed = editTextBreed.getText().toString();
        String location = editTextLocation.getText().toString();
        String phone = editTextPhone.getText().toString();

        Intent intent = new Intent(MainActivity.this, DisplayPetActivity.class);
        intent.putExtra("pet_name", name);
        intent.putExtra("pet_breed", breed);
        intent.putExtra("pet_location", location);
        intent.putExtra("pet_phone", phone);

        if (selectedImageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("pet_image", byteArray);
        }

        startActivity(intent);
    }
}
