package com.it.iot.project.smartirrigationiot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.it.iot.project.smartirrigationiot.MyApplication;
import com.it.iot.project.smartirrigationiot.R;
import com.it.iot.project.smartirrigationiot.data.Model;


import java.util.ArrayList;
import java.util.List;

public class MicrocontrollerDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Model> mModelList = new ArrayList<>();


    public MicrocontrollerDataAdapter(Context mContext, List<Model> modelList) {
        this.mContext = mContext;
        this.mModelList = modelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mrocontroller_data_table, parent, false);
        return new AppliedLoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AppliedLoanViewHolder appliedLoanViewHolder = (AppliedLoanViewHolder) holder;
        Model model = mModelList.get(position);

        appliedLoanViewHolder.m_txt_time_data.setText(model.getTime());
        appliedLoanViewHolder.m_txt_humidity_data.setText(model.getHumidity());
        appliedLoanViewHolder.m_txt_moisture_data.setText(model.getMoisture());
        appliedLoanViewHolder.m_txt_temperature_data.setText(model.getTemperature());
        appliedLoanViewHolder.m_txt_solenoid_status.setText(model.getSolenoid_valve());


    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class AppliedLoanViewHolder extends RecyclerView.ViewHolder {
        private TextView m_txt_time_data, m_txt_moisture_data, m_txt_humidity_data, m_txt_temperature_data, m_txt_solenoid_status;


        public AppliedLoanViewHolder(@NonNull View itemView) {
            super(itemView);
            m_txt_time_data = itemView.findViewById(R.id.tb_time_data);
            m_txt_moisture_data = itemView.findViewById(R.id.tb_moisture_data);
            m_txt_moisture_data = itemView.findViewById(R.id.tb_moisture_data);
            m_txt_humidity_data = itemView.findViewById(R.id.tb_humidity_data);
            m_txt_temperature_data = itemView.findViewById(R.id.tbl_temperature_data);
            m_txt_solenoid_status = itemView.findViewById(R.id.tbl_solenoid_data);

//            MyApplication.setQuicksand_Regular(mContext, m_txt_applied_loan_amount, txt_ap_sl_header, txt_ap_sl_total_loan_tle, txt_ap_sl_due_date_tle, txt_ap_sl_interest_rate_tle, txt_ap_sl_principal_amount_tle, txt_ap_sl_total_loan_tle);
            MyApplication.setBodoni_Moda(mContext, m_txt_time_data, m_txt_moisture_data, m_txt_humidity_data, m_txt_temperature_data, m_txt_solenoid_status);
        }
    }
}
