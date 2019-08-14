package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private NumberPicker numberPickerPriority;
    private ImageView imageViewPhoto;
    private Button buttonDeleteImage;
    byte[] noteImageByteArray;

    private static final int galleryPic = 1;
    public static final String EXTRA_ID =
            "com.example.notesapp.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.notesapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.notesapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.notesapp.EXTRA_PRIORITY";
    public static final String EXTRA_IMAGE =
            "com.example.notesapp.EXTRA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        imageViewPhoto = findViewById(R.id.image_view_photo);
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPic);
            }
        });
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(1000);
        buttonDeleteImage = findViewById(R.id.button_delete_image);
        buttonDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_black_48dp));
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            imageViewPhoto.setImageBitmap(NoteAdapter
                    .convertByteArrayToBitmap(intent.getByteArrayExtra(EXTRA_IMAGE)));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Error: All Fields are Required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_IMAGE, noteImageByteArray);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPic && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (resultUri != null){
                    imageViewPhoto.setImageURI(resultUri);
                    Bitmap bitmap = ((BitmapDrawable)imageViewPhoto.getDrawable()).getBitmap();
                    noteImageByteArray = convertBitmapToByte(bitmap);
                } else {
                    Toast.makeText(this, "Please try a different Image", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] convertBitmapToByte(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
