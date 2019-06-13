package ems.erp.mmdu.com.forfirebasestorage;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
            ///////////////////   Code By Deva    ///////////////////////////////////////////////
 */

public class AddNewAd extends AppCompatActivity
{
    private String CategoryDataFromPreviousIntent, Description, Price, PTitle;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private TextView CategorySelected;
    private EditText InputProductTitle, InputProductDescription, InputProductPrice;
    //private static final int GalleryPick = 1;
    private static final int CAMERA_PIC_REQUEST = 0;
    private static final int GALLERY_PIC_REQUEST = 1;
    private Uri ImageUri;
    Bitmap imageBitmap;
    private static final int REQUEST_READ_CODE = 99;
    private String productRandomKey, downloadImageUrl;
    private ProgressDialog loadingBar;
    private FirebaseUser user;
    FirebaseFirestore mFirebase;

    //New From DEVA
    private String CategoryName, Email,saveCurrentDate, saveCurrentTime, mCurrentPhotoPath;
    //private static final int GalleryPick = 1;
    private static final String TAG = "PostFragment";

    String m_curentDateandTime;
    String m_imagePath;
    //    public interface OnPhotoSelectedListener{
//        void getImagePath(Uri imagePath);
//        void getImageBitmap(Bitmap bitmap);
//    }
//    OnPhotoSelectedListener mOnPhotoSelectedListener;
    FirebaseStorage storage;
    Uri filepath;
    byte[] bytes;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ad);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebase = FirebaseFirestore.getInstance();

        CategoryDataFromPreviousIntent = getIntent().getExtras().get("category").toString();
        AddNewProductButton = (Button) findViewById(R.id.btn_post);
        InputProductImage = (ImageView) findViewById(R.id.post_image);
        InputProductTitle = (EditText) findViewById(R.id.input_title);
        CategorySelected = (TextView) findViewById(R.id.category);
        InputProductDescription = (EditText) findViewById(R.id.input_description);
        InputProductPrice = (EditText) findViewById(R.id.input_price);
        loadingBar = new ProgressDialog(this);

        //Firebase Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        final Boolean hasPermission = (ContextCompat.checkSelfPermission(AddNewAd.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);


        CategorySelected.setText(CategoryDataFromPreviousIntent);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(AddNewAd.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            99);
                }else {

                }
                SelectOption();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_READ_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted
                } else
                {
                    Toast.makeText(AddNewAd.this, "You Must Give Access to Storage", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

    }



    private void SelectOption()
    {
        /*Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);*/


        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewAd.this);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        //ImageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//                        //takePicture.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri); // set the image file name
//
////                        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "BuySellPic");
////                        imagesFolder.mkdirs(); // <----
////                        File image = new File(imagesFolder, "BuySellPic.jpg");
////                        filepath = Uri.fromFile(image);
////                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, filepath);
//                        startActivityForResult(takePicture, CAMERA_PIC_REQUEST);//zero can be replaced with any action code
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_PIC_REQUEST);
                    }
                });

        builder1.setNegativeButton(
                "Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent pickPhoto = new Intent();
                        pickPhoto.setType("image/*");
                        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(pickPhoto , GALLERY_PIC_REQUEST);//one can be replaced with any action code
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        Button btnPositive = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alert11.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        /*if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }*/

        /*ImageUri = imageReturnedIntent.getData();

        Bundle newExtras = new Bundle();
        if (ImageUri != null) {
            newExtras.putParcelable(MediaStore.EXTRA_OUTPUT, ImageUri);
        } else {
            newExtras.putBoolean("return-data", true);
        }*/

        filepath = imageReturnedIntent.getData();
        switch(requestCode) {
            case 0:         // CAMERA
                if(resultCode == RESULT_OK && imageReturnedIntent != null){
                    Bitmap bitmap;
                    bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");

                    //send the bitmap to PostFragment and dismiss dialog
                    getImageBitmap(bitmap);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    //Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
                    /*Uri selectedImage = imageReturnedIntent.getData();
                    InputProductImage.setImageURI(selectedImage);*/
                }
                break;
            case 1:      // GALLERY
                if(resultCode == RESULT_OK && imageReturnedIntent != null){
                    ImageUri = imageReturnedIntent.getData();
                    InputProductImage.setImageURI(ImageUri);
                }
                break;
        }
    }



    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        InputProductImage.setImageBitmap(bitmap);
        //assign to a global variable
        ImageUri = null;
        imageBitmap = bitmap;
        bytes = getBytesFromBitmap(imageBitmap, 50);
    }


    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality,stream);
        return stream.toByteArray();
    }


    private void ValidateProductData()
    {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        PTitle = InputProductTitle.getText().toString();


        if (ImageUri == null && imageBitmap == null)
        {
            Toast.makeText(this, "Please Select/Capture Image...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PTitle))
        {
            Toast.makeText(this, "Please Enter Title...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please Enter Description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please Enter Price...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            uploadImage();
        }
    }


    private void uploadImage()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait while we are uploading image..");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String imageName = UUID.randomUUID().toString();
        storageReference = storageReference.child("images/"+imageName);
//        final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();

//        final StorageReference ref = FirebaseStorage.getInstance().getReference()
//                .child("posts/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() +
//                        "/"  + "/post_image");
            storageReference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    try {
                                        String imageLink = uri.toString();
                                        String uid = user.getUid();
                                        String title  = InputProductTitle.getText().toString();
                                        Double price = Double.valueOf(InputProductPrice.getText().toString());
                                        String categories = CategoryDataFromPreviousIntent.split("&&")[0];
                                        String subCategories = CategoryDataFromPreviousIntent.split("&&")[1];
                                        String dateAdded = new Date().toString();
                                        String description = InputProductDescription.getText().toString();
                                        Post newPost = new Post(uid,title, price, categories, subCategories.substring(1), dateAdded, false,description,imageLink);

                                        mFirebase.collection("NewPost").document().set(newPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                            loadingBar.dismiss();
                            Toast.makeText(AddNewAd.this, "Uploaded !!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            loadingBar.dismiss();
                            Toast.makeText(AddNewAd.this, "Uploaded !!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            Toast.makeText(AddNewAd.this, "Failed !!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            loadingBar.setMessage("Uploading "+(int)progress+"%");
                        }
                    });

    }
}