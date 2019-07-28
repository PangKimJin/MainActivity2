package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.System.in;

public class ITTR extends AppCompatActivity {
    Database db;
    EditText mResultEt;
    ImageView mPreviewIv;
    Toolbar toolbar_ittr;
    Button toolbar_ittr_back;
    Button ittr_next;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ittr);
//        ActionBar actionBar = getSupportActionBar();                      //redundant after actionbar is removed
//        actionBar.setSubtitle("Click + button to insert image");          //redundant after actionbar is removed
        db = new Database(this);
        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.imageIv);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //toolbar stuff
        toolbar_ittr = findViewById(R.id.toolbar_ittr);
        toolbar_ittr.setTitle("ITTR");
        setSupportActionBar(toolbar_ittr);

        toolbar_ittr_back = findViewById(R.id.toolbar_ittr_back);
        ittr_next = findViewById(R.id.menu_main_next);

        toolbar_ittr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomePage();
            }
        });

//        ittr_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!mResultEt.getText().toString().equals("")) {
//                    processString(mResultEt.getText().toString());
//                    String expenditureListID = "" + db.getLatestEntryIDExpenditureList();
//                    goToEditExpenditureList(expenditureListID);
//                } else {
//                    Toast.makeText(ITTR.this, "Please add a valid entry", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
    }

    public void goToEditExpenditureList(String expenditureListID) {
        Intent intent = new Intent(this, EditExpenditureList.class);
        String id = expenditureListID;
        intent.putExtra("ExpenditureListID", id);
        startActivity(intent);
    }

    public void processString(String text) {
        //Creating a default expenditureList
        try {
            db.insertData3("New Expenditure List", "Category", getCurrentDate());

            //Processing text
            String trimmedText = text.trim().replaceAll(",", ".");
            String[] stringArray = trimmedText.split("\\r?\\n");
            ArrayList<Double> prices = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (String string : stringArray) {
                if (isDouble(string)) {
                    Double price = Double.parseDouble(string);
                    prices.add(price);
                } else {
                    names.add(string);
                }
            }


            //Creating item objects
            int ExpenditureListID = db.getLatestEntryIDExpenditureList();
            ArrayList<Item> items = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                Item item = new Item(0, names.get(i), 1, prices.get(i), ExpenditureListID);
                items.add(item);
            }

            //Inserting items into database
            for (Item item : items) {
                String name = item.getName();
                int quantity = item.getQuantity();
                double price = item.getPrice();
                int ListID = item.getListID();
                db.insertData4(name, quantity, price, ListID);
            }
        } catch (Exception ex) {
            Toast.makeText(ITTR.this, "Please take a clear image and crop properly", Toast.LENGTH_LONG).show();
        }

    }



    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / YYYY ");
        String strDate = "" + mdformat.format(calendar.getTime());
        return strDate;
    }

    public boolean isDouble(String text) {
        boolean result;
        try {
            Double.parseDouble(text);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }



    public void backToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);                              //the menu_main provides the add entry button

        return true;
    }
    //handle actionbar item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage) {
            showImageImportDialog();
        }
        if (id == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_main_next) {
            if (!mResultEt.getText().toString().equals("")) {
                processString(mResultEt.getText().toString());
                String expenditureListID = "" + db.getLatestEntryIDExpenditureList();
                goToEditExpenditureList(expenditureListID);
            } else {
                Toast.makeText(ITTR.this, "Please add a valid entry", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        //items to display in dialogue
        String[] items = {" Camera", " Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //camera option clicked
                    /*for OS Marshmallow and above we need to ask for runtime permission for
                    camera and storage
                     */
                    if (!checkCameraPermission()) {
                        //camera permissions not allowed, request for it
                        requestCameraPermission();
                    }
                    else {
                        //permission allowed, take photo
                        pickCamera();
                    }
                }
                if (which == 1) {
                    //gallery option clicked
                    if (!checkStoragePermission()) {
                        //camera permissions not allowed, request for it
                        requestStoragePermission();
                    }
                    else {
                        //permission allowed, take photo
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show(); //show dialog
    }

    private void pickGallery() {
        //to pick image from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        //to take an image from the camera and then save it to storage
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic"); //title of picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To text"); //description
        image_uri =  getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if ( writeStorageAccepted) {
                        pickGallery();
                    }
                    else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery to crop
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera to crop
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
                        .start(this);
            }
        }
        //get cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri(); // get image uri
                //set image to image view
                mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {                                                                                      //this should be the block that handles the text
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text from sb until there is no text
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    //set text to edit text
                    mResultEt.setText(sb.toString());                                                       //sb.toString() is the text from the ITTR
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

