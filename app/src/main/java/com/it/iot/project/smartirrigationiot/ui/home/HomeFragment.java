package com.it.iot.project.smartirrigationiot.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.it.iot.project.smartirrigationiot.MyApplication;
import com.it.iot.project.smartirrigationiot.R;
import com.it.iot.project.smartirrigationiot.data.AppHelper;
import com.it.iot.project.smartirrigationiot.data.Model;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private boolean light = true;
    private float progress = 50F;
    private long durationAnim = 1000L;
    Calendar calendar = Calendar.getInstance();
    private static DecimalFormat df = new DecimalFormat("0.00");


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        CircularProgressBar circularProgressBar = root.findViewById(R.id.circularProgressBar);
        TextView moistureTxt = root.findViewById(R.id.moisture_percentage);
        TextView tempTxt = root.findViewById(R.id.temp);
        TextView humidityTxt = root.findViewById(R.id.humidity);
        SwitchMaterial solenoidValveSwitch = root.findViewById(R.id.solenoid_valve);
        ;


        MyApplication.setQuicksand_Regular(requireContext(), tempTxt, humidityTxt);
        MyApplication.setBodoni_Moda(requireContext(), moistureTxt);


// Set Progress
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(0f);// or with animation

// Set Progress Max
        circularProgressBar.setProgressMax(100f);


        solenoidValveSwitch.setTextSize(16f);
        calendar.getTime();
        calendar.setTime(calendar.getTime());
        String hour_of_the_day = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dm = new SimpleDateFormat("yyyy-M-d");
        String dateToday = dm.format(new Date().getTime());


        AppHelper.getDaysData("2021", "February", dateToday, hour_of_the_day + "_hours", minute + "_minute")
                .addOnCompleteListener(task -> Log.d(TAG, "OnComplete"))
                .addOnFailureListener(e -> Log.e(TAG, Objects.requireNonNull(e.getMessage())))
                .addOnSuccessListener(dataSnapshot -> {
                    Log.d(TAG, "dataSnapshot: values " + String.valueOf(dataSnapshot.getValue()));
                    Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                    for (DataSnapshot data : iterator) {
                        System.out.printf("Key: %s%n Data:  %s%n", data.getKey(), data.getValue());
                        Gson g = new Gson();
                        Log.e(TAG, data.getValue().toString());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH");
                        String str = sdf.format(new Date());
                        if (dataSnapshot.getValue() != null) {
                            Model m = g.fromJson(Objects.requireNonNull(dataSnapshot.getValue()).toString(), Model.class);

                            moistureTxt.setText(String.valueOf(df.format(Float.valueOf(m.toMap().get("moisture").toString()))) + "%");
                            tempTxt.setText(m.toMap().get("temperature") + "");
                            humidityTxt.setText(m.toMap().get("humidity") + "");

                            Float moisturePer = Float.valueOf(m.toMap().get("moisture").toString());
                            circularProgressBar.setProgressWithAnimation(moisturePer, durationAnim); // =1s
                            if (m.toMap().get("solenoid_valve") != null) {
                                if (m.toMap().get("solenoid_valve").equals("ON")) {
                                    solenoidValveSwitch.setChecked(true);
                                    solenoidValveSwitch.setText(getText(R.string.label_solenoid_on));

                                } else {
                                    solenoidValveSwitch.setChecked(false);
                                    solenoidValveSwitch.setText(getText(R.string.label_solenoid_off));

                                }
                            }

                            System.out.printf(" Model %s date %s%n", m.toMap().get("temperature"), str);
                        }


                        //Read more: https://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html#ixzz6j479udok
                        Iterable<DataSnapshot> iterable = data.getChildren();
                        iterable.forEach(dataSnapshot1 -> {
                            System.out.printf("dataSnapshot1 Key: %s %s%n", dataSnapshot1.getKey(), dataSnapshot1.getValue());
                            if (dataSnapshot1.getKey().equals(0)) {
                                Log.e(TAG, "0 " + dataSnapshot1.getValue());
                            }
                        });
                    }


                });
        ValueEventListener dataChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                Model model = dataSnapshot.getValue(Model.class);
                System.out.printf("Data changed %s", dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        AppHelper.getDatabaseReference().addValueEventListener(dataChangeListener);

// or with animation
//    private fun TypedValue.getAttr(@AttrRes attrRes: Int): Int {
//        theme.resolveAttribute(attrRes, this, true)
//        return data
//    }
        //https://ssaurel.medium.com/learn-to-create-a-thermometer-application-for-android-295d6611b4f9
        //https://github.com/supersnek/Humidity-App-Design
        //https://codelabs.developers.google.com/codelabs/material-motion-android#8
        //https://docs.telerik.com/devtools/android/controls/gauges/gauges-getting-started
        //https://github.com/AnyChart/AnyChart-Android
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        @SuppressLint("SimpleDateFormat") SimpleDateFormat month = new SimpleDateFormat("MMMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String strDate = dm.format(new Date());
        String strMonth = month.format(new Date());
        String strYear = year.format(new Date());
        System.out.printf("date %s month %s year %s", strDate, strMonth, strYear);
        Log.e("date", "" + calendar.get(Calendar.DAY_OF_MONTH));

        Log.e("month", "" + calendar.get(Calendar.MONTH));

        Log.e("year", "" + calendar.get(Calendar.YEAR));

        Log.e("hour", "" + calendar.get(Calendar.HOUR_OF_DAY));

        Log.e("minutes", "" + calendar.get(Calendar.MINUTE));

        Log.e("seconds", "" + calendar.get(Calendar.SECOND));

        Log.e("Time", "" + dm.format(new Date().getTime()));

        return root;
    }
}