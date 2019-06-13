package ems.erp.mmdu.com.forfirebasestorage;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public class DevaFragment extends Fragment {

    EditText name,gender,email,phone,languages,location,additional1,additional2;
    ImageView editButton,submitButton;
    LinearLayout emailLayout,phoneLayout,languageLayout,locationLayout,additional1Layout,additional2Layout;
    RelativeLayout upperField;
    ProgressDialog dialog;
    private FirebaseUser user;
    FirebaseFirestore mFirebase;
    private DocumentReference notebookRef;
    private Uri ImageUri;
    private ProgressDialog loadingBar;
    ImageView userProfilePicture;
    Users userObj = null;
    String imageLink = null;

    Uri filepath;
    StorageReference storageReference;


    public DevaFragment() {
        // Required empty public constructor
    }


    public static DevaFragment newInstance(String param1, String param2) {
        DevaFragment fragment = new DevaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_deva, null);
        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        languages = view.findViewById(R.id.language);
        location = view.findViewById(R.id.location);
        additional1 = view.findViewById(R.id.additional1);
        additional2 = view.findViewById(R.id.additional2);
        editButton = view.findViewById(R.id.editButton);
        submitButton = view.findViewById(R.id.submitButton);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebase = FirebaseFirestore.getInstance();
        notebookRef = mFirebase.collection("Users").document(user.getUid());
        userProfilePicture = view.findViewById(R.id.user_profile);
        loadingBar = new ProgressDialog(getActivity());
        storageReference = FirebaseStorage.getInstance().getReference();
        String toke = FirebaseInstanceId.getInstance().getToken();


        notebookRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userObj = documentSnapshot.toObject(Users.class);
                    if(userObj != null){
                        name.setText(userObj.getName());
                        gender.setText(userObj.getGender());
                        email.setText(userObj.getEmail());
                        phone.setText(userObj.getPhone());
                        languages.setText(userObj.getLanguage());
                        location.setText(userObj.getLocation());
                        additional1.setText(userObj.getAdditional1());
                        additional2.setText(userObj.getAdditional2());
                        imageLink = userObj.getProfilePicture();
                        Picasso.with(getContext()).load(userObj.getProfilePicture()).into(userProfilePicture);
                    }
            }
        });

        emailLayout = view.findViewById(R.id.email_layout);
        phoneLayout = view.findViewById(R.id.phone_layout);
        languageLayout = view.findViewById(R.id.language_layout);
        locationLayout = view.findViewById(R.id.location_layout);
        additional2Layout = view.findViewById(R.id.additional1_layout);
        additional1Layout = view.findViewById(R.id.additional2_layout);
        upperField = view.findViewById(R.id.upper_field);

        submitButton.setVisibility(View.INVISIBLE);
        name.setEnabled(false);
        gender.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        languages.setEnabled(false);
        location.setEnabled(false);
        additional1.setEnabled(false);
        additional2.setEnabled(false);

        userProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption();
            }
        });
        userProfilePicture.setClickable(false);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
                name.setEnabled(false);
                gender.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                languages.setEnabled(false);
                location.setEnabled(false);
                additional1.setEnabled(false);
                additional2.setEnabled(false);

                emailLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                phoneLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                languageLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                locationLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                additional1Layout.setBackgroundColor(Color.parseColor("#ffffff"));
                additional2Layout.setBackgroundColor(Color.parseColor("#ffffff"));
                upperField.setBackgroundResource(R.drawable.grad);

                String newName = name.getText().toString();
                String newGender = gender.getText().toString();
                String newEmail = email.getText().toString();
                String newPhone = phone.getText().toString();
                String newLanguages = languages.getText().toString();
                String newLocation= location.getText().toString();
                String newAdditional1= additional1.getText().toString();
                String newAdditional2= additional2.getText().toString();

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wait");
                dialog.show();

                userObj = new Users(newName,newGender,newEmail,newPhone,newLanguages,newLocation,newAdditional1,newAdditional2,"");
                userObj.setUserToken(FirebaseInstanceId.getInstance().getToken());
                userObj.setProfilePicture(imageLink);
                mFirebase.collection("Users").document(user.getUid()).set(userObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Updated Profile", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "There Was some Problem" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfilePicture.setClickable(true);
                name.setEnabled(true);
                gender.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                languages.setEnabled(true);
                location.setEnabled(true);
                additional1.setEnabled(true);
                additional2.setEnabled(true);
                submitButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);

                emailLayout.setBackgroundColor(Color.parseColor("#c5ede7"));
                phoneLayout.setBackgroundColor(Color.parseColor("#c5ede7"));
                languageLayout.setBackgroundColor(Color.parseColor("#c5ede7"));
                locationLayout.setBackgroundColor(Color.parseColor("#c5ede7"));
                additional1Layout.setBackgroundColor(Color.parseColor("#c5ede7"));
                additional2Layout.setBackgroundColor(Color.parseColor("#c5ede7"));
                upperField.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        return view;
    }


    private void selectOption()
    {
        Intent pickPhoto = new Intent();
        pickPhoto.setType("image/*");
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        filepath = imageReturnedIntent.getData();
        try {
            filepath = getImageUri(getContext(),MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch(requestCode) {
            case 1:      // GALLERY
                if(resultCode == getActivity().RESULT_OK && imageReturnedIntent != null){
                    ImageUri = imageReturnedIntent.getData();
                    userProfilePicture.setImageURI(ImageUri);
                }
                uploadImage();
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage()
    {
        loadingBar.setTitle("Add new Profile Pic");
        loadingBar.setMessage("Please wait while we are uploading image..");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String imageName = user.getUid();
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
                                imageLink = uri.toString();
                            }
                        });
                        loadingBar.dismiss();
                        Toast.makeText(getActivity(), "Uploaded !!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        loadingBar.dismiss();
                        Toast.makeText(getActivity(), "Uploaded !!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(getActivity(), "Failed !!"+e.getMessage(), Toast.LENGTH_SHORT).show();
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

