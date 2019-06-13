package ems.erp.mmdu.com.forfirebasestorage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ems.erp.mmdu.com.forfirebasestorage.helper.Space;

public class Seller extends Fragment {

    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("NewPost");
    List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View seller = inflater.inflate(R.layout.tab_seller, container, false);
        recyclerView = seller.findViewById(R.id.recyclerView_seller);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new Space(1, 50, true, 0));
        postList = new ArrayList<>();
        loadNotes();
        return seller;
    }

    public void loadNotes() {
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Map<String, Object> allData = documentSnapshot.getData();
                            Post obj = documentSnapshot.toObject(Post.class);
                            postList.add(obj);
                        }
                        //creating recyclerview adapter
                        PostAdapter adapter = new PostAdapter(getActivity(), postList);
                        //setting adapter to recyclerview
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}

