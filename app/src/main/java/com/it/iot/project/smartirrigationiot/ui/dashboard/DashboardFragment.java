package com.it.iot.project.smartirrigationiot.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.it.iot.project.smartirrigationiot.MainActivity;
import com.it.iot.project.smartirrigationiot.MyApplication;
import com.it.iot.project.smartirrigationiot.R;
import com.it.iot.project.smartirrigationiot.adapters.MicrocontrollerDataAdapter;
import com.it.iot.project.smartirrigationiot.data.AppHelper;
import com.it.iot.project.smartirrigationiot.data.Model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView mRecyclerViewMicrocontroller;
    private static final String TAG = DashboardFragment.class.getSimpleName();
    private List<Model> modelList = new ArrayList<>();
    TextView tbl_hd_time,tbl_hd_moisture,tbl_hd_humidity,tbl_hd_temperature,tbl_hd_solenoid_valve;

    private MicrocontrollerDataAdapter microcontrollerDataAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        loadMicrocontrollerDetails();
        mRecyclerViewMicrocontroller = root.findViewById(R.id.rv_microcontroller_data);
        tbl_hd_temperature=root.findViewById(R.id.tbl_hd_temperature);
        tbl_hd_time=root.findViewById(R.id.tbl_hd_time);
        tbl_hd_moisture=root.findViewById(R.id.tbl_hd_moisture);
        tbl_hd_humidity=root.findViewById(R.id.tbl_hd_humidity);
        tbl_hd_solenoid_valve=root.findViewById(R.id.tbl_hd_solenoid_valve);
        MyApplication.setQuicksand_Regular(getContext(),tbl_hd_time,tbl_hd_moisture,tbl_hd_humidity,tbl_hd_temperature,tbl_hd_solenoid_valve);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
//        viewPager.setAdapter(new SampleFragmentPagerAdapter(requireActivity().getSupportFragmentManager(),
//                getContext()));
//
//        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        microcontrollerDataAdapter = new MicrocontrollerDataAdapter(getContext(), modelList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewMicrocontroller.setLayoutManager(layoutManager);
        mRecyclerViewMicrocontroller.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewMicrocontroller.setAdapter(microcontrollerDataAdapter);

//        mRecyclerViewMicrocontroller.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerViewMicrocontroller, (view, position) -> {
//            ((MainActivity) getActivity()).navigate(LoanDetailsFragment.newInstance("loan_by_id", loanModelList.get(position).getDocId()));
//        }));
    }

    private void loadMicrocontrollerDetails() {
        AppHelper.geLoanCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot doc : documentSnapshots) {
                    Log.e(TAG, "microcontroller data " + doc.getData());
                    Model model
                            = new Model();
                    model.setTime(String.valueOf(doc.getData().get("time")));
                    model.setFahrenheit(String.valueOf(doc.getData().get("Fahrenheit")));
                    model.setHumidity(String.valueOf(doc.getData().get("humidity")));
                    model.setMoisture(String.valueOf(doc.getData().get("moisture")));
                    model.setSolenoid_valve(String.valueOf(doc.getData().get("solenoid_valve")));
                    model.setTemperature(String.valueOf(doc.getData().get("temperature")));
                    modelList.add(model);
                    microcontrollerDataAdapter.notifyDataSetChanged();


                }

            }
        });
    }
    //    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
//        final int PAGE_COUNT = 3;
//        private String tabTitles[] = new String[]{"Moisture", "Humidity", "Temperature"};
//        private Context context;
//
//        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
//            super(fm);
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            return PAGE_COUNT;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//            return AnalyticsFragment.newInstance(tabTitles[position], String.valueOf(position + 1));
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Generate title based on item position
//            return tabTitles[position];
//        }
//    }


}