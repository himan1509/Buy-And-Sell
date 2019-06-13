package ems.erp.mmdu.com.forfirebasestorage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class tusharFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public tusharFragment() {
        // Required empty public constructor
    }


    public static tusharFragment newInstance(String param1, String param2) {
        tusharFragment fragment = new tusharFragment();
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
        View view = inflater.inflate(R.layout.fragment_tushar, null);
        expListView = view.findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();


        listAdapter = new ems.erp.mmdu.com.forfirebasestorage.ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setDividerHeight(10);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getActivity(), AddNewAd.class);
                intent.putExtra("category", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)+"&&"+listDataHeader.get(groupPosition));
                startActivity(intent);
                // TODO Auto-generated method stub
                /*Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });

        return view;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("> Mobiles, Computers");
        listDataHeader.add("> Electronics, Appliances");
        listDataHeader.add("> Men's Fashion");
        listDataHeader.add("> Women's Fashion");
        listDataHeader.add("> Books");
        listDataHeader.add("> Bags, Luggage");
        listDataHeader.add("> Sports, Fitness");

        // Adding child data
        List<String> MobilesandAccessories = new ArrayList<String>();
        MobilesandAccessories.add("Mobiles");
        MobilesandAccessories.add("Mobile Accessories");
        MobilesandAccessories.add("Power Banks");
        MobilesandAccessories.add("Laptops");
        MobilesandAccessories.add("Tablets");
        MobilesandAccessories.add("Computer and Accessories");
        MobilesandAccessories.add("Wearable Devices");
        MobilesandAccessories.add("Gaming and Accessories");
        MobilesandAccessories.add("Other");

        List<String> ElectronicsAppliances = new ArrayList<String>();
        ElectronicsAppliances.add("Headphones");
        ElectronicsAppliances.add("Earphones");
        ElectronicsAppliances.add("Speakers");
        ElectronicsAppliances.add("Cameras");
        ElectronicsAppliances.add("Heating Appliances");
        ElectronicsAppliances.add("Cooling Appliances");
        ElectronicsAppliances.add("Air Conditioners");
        ElectronicsAppliances.add("Kitchen Appliances");
        ElectronicsAppliances.add("Other");

        List<String> MensFashion = new ArrayList<String>();
        MensFashion.add("T-Shirts");
        MensFashion.add("Shirts");
        MensFashion.add("Footwear");
        MensFashion.add("Trousers");
        MensFashion.add("Kurtas");
        MensFashion.add("Jeans");
        MensFashion.add("Watches");
        MensFashion.add("Sunglasses");
        MensFashion.add("Grooming Items");
        MensFashion.add("Wallets");
        MensFashion.add("Sportswear");
        MensFashion.add("Other");

        List<String> WomensFashion = new ArrayList<String>();
        WomensFashion.add("Footwear");
        WomensFashion.add("Handbags and Clutches");
        WomensFashion.add("Dresses");
        WomensFashion.add("Personal Care Appliances");
        WomensFashion.add("Beauty & Grooming");
        WomensFashion.add("Jeans");
        WomensFashion.add("Watches");
        WomensFashion.add("Leggings");
        WomensFashion.add("Sunglasses");
        WomensFashion.add("Jewellery");
        WomensFashion.add("Other");

        List<String> Books = new ArrayList<String>();
        Books.add("TextBooks");
        Books.add("Fiction");
        Books.add("Non-Fiction");
        Books.add("Used Books");
        Books.add("Biography");
        Books.add("Entrance Exams");
        Books.add("Indian Language Books");
        Books.add("Academic");
        Books.add("Other");

        List<String> BagsLuggage = new ArrayList<String>();
        BagsLuggage.add("Backpacks");
        BagsLuggage.add("Travel Luggage");
        BagsLuggage.add("Travel Accessories");
        BagsLuggage.add("Other");

        List<String> SportsFitness = new ArrayList<String>();
        SportsFitness.add("Cricket");
        SportsFitness.add("Badminton");
        SportsFitness.add("Football");
        SportsFitness.add("Skating");
        SportsFitness.add("Dumbbells");
        SportsFitness.add("Yoga Mats");
        SportsFitness.add("Other");

        listDataChild.put(listDataHeader.get(0), MobilesandAccessories); // Header, Child data
        listDataChild.put(listDataHeader.get(1), ElectronicsAppliances);
        listDataChild.put(listDataHeader.get(2), MensFashion);
        listDataChild.put(listDataHeader.get(3), WomensFashion);
        listDataChild.put(listDataHeader.get(4), Books);
        listDataChild.put(listDataHeader.get(5), BagsLuggage);
        listDataChild.put(listDataHeader.get(6), SportsFitness);
    }
}

