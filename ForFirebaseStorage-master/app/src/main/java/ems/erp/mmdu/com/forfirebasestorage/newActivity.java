package ems.erp.mmdu.com.forfirebasestorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class newActivity extends AppCompatActivity {

    FirebaseFirestore mfirebase;
    DocumentReference documentReference;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Post obj = (Post) getIntent().getSerializableExtra("data");
        String str = getIntent().getExtras().getString("documentId");

        mfirebase = FirebaseFirestore.getInstance();
        documentReference = mfirebase.collection("NewPost").document(str);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        Map bids = obj.getBids();
        bids.put(mUser.getUid(),500);
        documentReference.update("bids",bids);



    }
}