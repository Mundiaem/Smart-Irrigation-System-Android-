//package com.it.iot.project.smartirrigationiot.ui.analytics;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.ContextThemeWrapper;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.chart.common.dataentry.DataEntry;
//import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.charts.Cartesian;
//import com.anychart.core.cartesian.series.Line;
//import com.anychart.data.Mapping;
//import com.anychart.data.Set;
//import com.anychart.enums.Anchor;
//import com.anychart.enums.MarkerType;
//import com.anychart.enums.TooltipPositionMode;
//import com.anychart.graphics.vector.Stroke;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.Description;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.ValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.it.iot.project.smartirrigationiot.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AnalyticsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AnalyticsFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AnalyticsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AnalyticsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AnalyticsFragment newInstance(String param1, String param2) {
//        AnalyticsFragment fragment = new AnalyticsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @SuppressLint("ResourceAsColor")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
//
//        View root = inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.fragment_analytics, container, false);
//        System.out.printf("This is the fragment %s%n %s%n", mParam1, mParam2);
//
//        if (mParam2.equalsIgnoreCase("Moisture")) {
//            chartGraphMoisture(root);
//
//        } else if (mParam2.equalsIgnoreCase("Humidity")) {
//            chartGraphHumidity(root);
//
//        } else if (mParam2.equalsIgnoreCase("Temperature")) {
//            chartGraphTemperature(root);
//
//        }
//
//
//        return root;
//    }
//
//    private class CustomDataEntry extends ValueDataEntry {
//
//        CustomDataEntry(String x, Number value, Number value2, Number value3) {
//            super(x, value);
//            setValue("value2", value2);
//            setValue("value3", value3);
//        }
//
//    }
//
//    private void chartGraphHumidity(View root) {
////        BarChart chart = (BarChart) root.findViewById(R.id.chart);
//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, 30f));
//        entries.add(new BarEntry(1f, 80f));
//        entries.add(new BarEntry(2f, 60f));
//        entries.add(new BarEntry(3f, 50f));
//        // gap of 2f
//        entries.add(new BarEntry(5f, 70f));
//        entries.add(new BarEntry(6f, 60f));
//        BarDataSet set1 = new BarDataSet(entries, "Group 2");
//
//        BarDataSet set2 = new BarDataSet(entries, "Group 2");
//
//
//        BarData data = new BarData(set2, set1);
//
//        chart.setData(getDataSet());
//        Description description=new Description();
//        description.setText("Data");
//
//        chart.setDescription(description);
//        chart.animateXY(2000, 2000);
//        chart.invalidate();
//
//
//    }
//    private final int[] colors = new int[] {
//            Color.rgb(137, 230, 81),
//            Color.rgb(240, 240, 30),
//            Color.rgb(89, 199, 250),
//            Color.rgb(250, 104, 104)
//    };
//
//    private void chartGraphTemperature(View root) {
//
//    }
//
//    private void chartGraphMoisture(View root) {
//
//    }
//    private BarData getDataSet() {
//        ArrayList dataSets = null;
//
//        ArrayList valueSet1 = new ArrayList();
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);
//
//        ArrayList valueSet2 = new ArrayList();
//        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
//        valueSet2.add(v2e2);
//        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
//        valueSet2.add(v2e3);
//        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
//        valueSet2.add(v2e4);
//        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
//        valueSet2.add(v2e5);
//        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
//        valueSet2.add(v2e6);
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
//        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
//        BarData data = new BarData(barDataSet1, barDataSet2);
//
//        dataSets = new ArrayList();
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
//        return data;
//    }
//
//    private ArrayList getXAxisValues() {
//
//
//        ArrayList xAxis = new ArrayList();
//        xAxis.add("JAN");
//        xAxis.add("FEB");
//        xAxis.add("MAR");
//        xAxis.add("APR");
//        xAxis.add("MAY");
//        xAxis.add("JUN");
//        return xAxis;
//    }
//
//}