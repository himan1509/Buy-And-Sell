package ems.erp.mmdu.com.forfirebasestorage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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


public class CategoryFragment extends Fragment {

    private static final String DESCRIBABLE_KEY = "describable_key";
    String param1;

    RecyclerView recyclerView;
    List<Post> postList;
    List<String> documentIDsForProduct;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("NewPost");


    public CategoryFragment() {
        // Required empty public constructor
    }


    public static CategoryFragment newInstance(String param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(DESCRIBABLE_KEY, param1);
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

        param1 = (String) getArguments().getSerializable(
                DESCRIBABLE_KEY);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        postList = new ArrayList<>();
        documentIDsForProduct = new ArrayList<>();


        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Map<String, Object> allData = documentSnapshot.getData();
                            Post obj = documentSnapshot.toObject(Post.class);
                            if(obj.getCategories().equals(param1)){
                                postList.add(obj);
                                documentIDsForProduct.add(documentSnapshot.getId());
                            }
                        }
                        PostAdapter adapter = new PostAdapter(getActivity(), postList);
                        recyclerView.addItemDecoration(new Space(2, 10, true, 0));
                        recyclerView.setAdapter(adapter);
                    }
                });

        return view;
    }


}
