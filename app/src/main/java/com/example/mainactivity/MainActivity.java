package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    EditText mResultEt;
//    ImageView mPreviewIv;
//
//
//    private static final int CAMERA_REQUEST_CODE = 200;
//    private static final int STORAGE_REQUEST_CODE = 400;
//    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
//    private static final int IMAGE_PICK_CAMERA_CODE = 1001;
    private Button button;
    private Button buttonScan;
    private Button buttonExpenditure;
    private Button buttonDashboard;
//
//    String cameraPermission[];
//    String storagePermission[];
//
//    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setSubtitle("Click + button to insert image");
//
//        mResultEt = findViewById(R.id.resultEt);
//        mPreviewIv = findViewById(R.id.imageIv);
//
//        //camera permission
//        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        //storage permission
//        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //button from homepage
        button = findViewById(R.id.button);
        buttonScan = findViewById(R.id.button_Scan);
        buttonExpenditure = findViewById(R.id.button_Expenditure);
        buttonDashboard = findViewById(R.id.button_dashboard);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserInput();
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageToTextRecognition();
            }
        });
        buttonExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPastExpenditure();
            }
        });
        buttonDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
    }

    public void openImageToTextRecognition() {
        Intent intent = new Intent(this, ITTR.class);
        startActivity(intent);
    }

    public void openUserInput() {
        Intent intent = new Intent(this, CreateLists.class);
        startActivity(intent);
    }

    public void openMyPastExpenditure() {
        Intent intent = new Intent(this, MyPastExpenditure.class);
        startActivity(intent);
    }

    public void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

//    //actionbar menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //inflate menu
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    //handle actionbar item clicks
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.addImage) {
//            showImageImportDialog();
//        }
//        if (id == R.id.settings) {
//            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showImageImportDialog() {
//        //items to display in dialogue
//        String[] items = {" Camera", " Gallery"};
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        //set title
//        dialog.setTitle("Select Image");
//        dialog.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    //camera option clicked
//                    /*for OS Marshmallow and above we need to ask for runtime permission for
//                    camera and storage
//                     */
//                    if (!checkCameraPermission()) {
//                        //camera permissions not allowed, request for it
//                        requestCameraPermission();
//                    }
//                    else {
//                        //permission allowed, take photo
//                        pickCamera();
//                    }
//                }
//                if (which == 1) {
//                    //gallery option clicked
//                    if (!checkStoragePermission()) {
//                        //camera permissions not allowed, request for it
//                        requestStoragePermission();
//                    }
//                    else {
//                        //permission allowed, take photo
//                        pickGallery();
//                    }
//                }
//            }
//        });
//        dialog.create().show(); //show dialog
//    }
//
//    private void pickGallery() {
//        //to pick image from the gallery
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        //set intent type to image
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
//    }
//
//    private void pickCamera() {
//        //to take an image from the camera and then save it to storage
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "NewPic"); //title of picture
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To text"); //description
//        image_uri =  getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
//        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
//    }
//
//    private void requestStoragePermission() {
//        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
//    }
//
//    private boolean checkStoragePermission() {
//        boolean result = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
//        return result;
//    }
//
//    private void requestCameraPermission() {
//        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
//    }
//
//    private boolean checkCameraPermission() {
//        boolean result = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
//        boolean result1 = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
//        return result && result1;
//    }
//
//    //handle permission result
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean cameraAccepted = grantResults[0] ==
//                            PackageManager.PERMISSION_GRANTED;
//                    boolean writeStorageAccepted = grantResults[0] ==
//                            PackageManager.PERMISSION_GRANTED;
//                    if (cameraAccepted && writeStorageAccepted) {
//                        pickCamera();
//                    }
//                    else {
//                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            case STORAGE_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean writeStorageAccepted = grantResults[0] ==
//                            PackageManager.PERMISSION_GRANTED;
//                    if ( writeStorageAccepted) {
//                        pickGallery();
//                    }
//                    else {
//                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }
//
//    //handle image result
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
//                //got image from gallery to crop
//                CropImage.activity(data.getData())
//                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
//                        .start(this);
//            }
//            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
//                //got image from camera to crop
//                CropImage.activity(image_uri)
//                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
//                        .start(this);
//            }
//        }
//        //get cropped image
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri(); // get image uri
//                //set image to image view
//                mPreviewIv.setImageURI(resultUri);
//
//                //get drawable bitmap for text recognition
//                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//
//                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
//
//                if (!recognizer.isOperational()) {
//                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//                    SparseArray<TextBlock> items = recognizer.detect(frame);
//                    StringBuilder sb = new StringBuilder();
//                    //get text from sb until there is no text
//                    for (int i = 0; i < items.size(); i++) {
//                        TextBlock myItem = items.valueAt(i);
//                        sb.append(myItem.getValue());
//                        sb.append("\n");
//                    }
//                    //set text to edit text
//                    mResultEt.setText(sb.toString());
//                }
//            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
