package ems.erp.mmdu.com.forfirebasestorage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.firebase.ui.auth.AuthUI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ems.erp.mmdu.com.forfirebasestorage.helper.Space;


public class AmmarFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private NavigationView nvDrawer;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("NewPost");
    private DocumentReference notebookRef1;
    private FirebaseUser user;

    ImageView userProfilePicture;
    TextView name;

    //a list to store all the products
    List<Post> postList;
    List<String> documentIDsForProduct;
    RecyclerView recyclerView;

    private FloatingActionButton newAdd;

    Users userObj = null;
    String imageLink = null;

    public AmmarFragment() {
        // Required empty public constructor
    }

    public static AmmarFragment newInstance(String param1, String param2) {
        AmmarFragment fragment = new AmmarFragment();
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
        View view = inflater.inflate(R.layout.fragment_ammar2, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_menu);

        // Find our drawer view
        mDrawer = view.findViewById(R.id.drawer_layout);
        nvDrawer = view.findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);


        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        newAdd = view.findViewById(R.id.floatingActionButton);
        newAdd.setOnClickListener(this);

        View headerView = nvDrawer.getHeaderView(0);

        //for header in navigation bar, display user details
        name= headerView.findViewById(R.id.name1);
        userProfilePicture= headerView.findViewById(R.id.user_profile1);
        user = FirebaseAuth.getInstance().getCurrentUser();
        notebookRef1 = db.collection("Users").document(user.getUid());
        notebookRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        userObj = documentSnapshot.toObject(Users.class);
                                                        if (userObj != null) {
                                                            name.setText(userObj.getName());
                                                            imageLink = userObj.getProfilePicture();
                                                            Picasso.with(getContext()).load(userObj.getProfilePicture()).into(userProfilePicture);
                                                        }
                                                    }
                                                });

        postList = new ArrayList<>();
        documentIDsForProduct = new ArrayList<>();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(),newActivity.class);
                        intent.putExtra("data", postList.get(position));
                        intent.putExtra("documentId",documentIDsForProduct.get(position));
                        startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        loadNotes();
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void loadNotes() {
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Map<String, Object> allData = documentSnapshot.getData();
                            Post obj = documentSnapshot.toObject(Post.class);
                            documentIDsForProduct.add(documentSnapshot.getId());
                            postList.add(obj);
                        }
                        PostAdapter adapter = new PostAdapter(getActivity(), postList);
                        recyclerView.addItemDecoration(new Space(2, 10, true, 0));
                        recyclerView.setAdapter(adapter);
                    }
                });
    }


    @Override
    public void onClick(View view) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new tusharFragment())
                .addToBackStack(null)
                .commit();
    }

    public void selectDrawerItem(MenuItem menuItem) {

        int id= menuItem.getItemId();
        switch (id) {
            case R.id.profile:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new DevaFragment());
                transaction.commit();
                break;

            case R.id.sign_out:
                    AuthUI.getInstance().signOut(getActivity());
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                break;
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
