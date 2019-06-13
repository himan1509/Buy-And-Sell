package ems.erp.mmdu.com.forfirebasestorage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import ems.erp.mmdu.com.forfirebasestorage.Buyer;
import ems.erp.mmdu.com.forfirebasestorage.Seller;


public class PageTree extends Fragment {

    FrameLayout seller, buyer;
    View view1, view2;
    TextView tvseller, tvbuyer;
    Buyer buyerObj;
    Seller sellerObj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_three = inflater.inflate(R.layout.fragment_three, container, false);

        //INIT VIEWS
        init(fragment_three);
        buyerObj  = new Buyer();
        sellerObj = new Seller();

        //SET TABS ONCLICK
        seller.setOnClickListener(clik);
        buyer.setOnClickListener(clik);

        //LOAD PAGE FOR FIRST
        loadPage(sellerObj);
        tvseller.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

        return fragment_three;
    }

    public void init(View v){
        seller = v.findViewById(R.id.seller);
        buyer = v.findViewById(R.id.buyer);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        tvseller = v.findViewById(R.id.tvseller);
        tvbuyer = v.findViewById(R.id.tvbuyer);
    }

    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.seller:
                    //ONSELLER CLICK
                    //LOAD SELLER FRAGMENT CLASS
                    loadPage(sellerObj);

                    //WHEN CLICK TEXT COLOR CHANGED
                    tvseller.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    tvbuyer.setTextColor(getActivity().getResources().getColor(R.color.grey));

                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    break;
                case R.id.buyer:
                    //ONBUYER CLICK
                    //LOAD BUYER FRAGMENT CLASS
                    loadPage(buyerObj);
                    //WHEN CLICK TEXT COLOR CHANGED
                    tvseller.setTextColor(getActivity().getResources().getColor(R.color.grey));
                    tvbuyer.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {

        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
