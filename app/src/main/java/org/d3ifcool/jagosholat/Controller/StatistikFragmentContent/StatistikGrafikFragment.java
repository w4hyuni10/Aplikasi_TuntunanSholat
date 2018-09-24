package org.d3ifcool.jagosholat.Controller.StatistikFragmentContent;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.d3ifcool.jagosholat.Controller.Helper.MethodHelper;
import org.d3ifcool.jagosholat.Model.DataOperation;
import org.d3ifcool.jagosholat.R;
import org.d3ifcool.jagosholat.Controller.MainContent.StatistikFragment;
import org.d3ifcool.jagosholat.Model.DataContract.DataEntry;
import java.util.ArrayList;

public class StatistikGrafikFragment extends Fragment {

    // ---------------------------------------------------------------------------------------------
    private LineChart mChart;
    // ---------------------------------------------------------------------------------------------
    private MethodHelper methodHelper = new MethodHelper();
    private DataOperation crud = new DataOperation();
    private Cursor cursorTanggal, cursorCount;
    private String [] days;
    // ---------------------------------------------------------------------------------------------

    public StatistikGrafikFragment() {
        // Required empty public constructor
    }

    public boolean isEmptyTanggal(){
        try {
            cursorTanggal = crud.getSemuaTanggal(getContext());
            int cek = cursorTanggal.getCount();
            return cek == 0;
        } finally {
            cursorTanggal.close();
        }

    }


    public void CreateGrafik(){
        // -----------------------------------------------------------------------------------------
        mChart.setBorderColor(Color.GREEN);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.canScrollHorizontally(10);
        mChart.setDescription(null);
        mChart.setMinimumHeight(20);
        // -----------------------------------------------------------------------------------------
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        // -----------------------------------------------------------------------------------------
        if (!isEmptyTanggal()) {
            try {
                cursorTanggal = crud.getSemuaTanggal(getContext());
                days = new String[cursorTanggal.getCount()];
                float xData = 0;
                int i = 0;
                // ---------------------------------------------------------------------------------
                while (cursorTanggal.moveToNext()){
                    // -----------------------------------------------------------------------------
                    int tanggalColumnIndex = cursorTanggal.getColumnIndex(DataEntry.COLUMN_TANGGAL); // Mencari index dalam database
                    String tanggal = cursorTanggal.getString(tanggalColumnIndex); // Mendapat data dari database berdasarkan index
                    // -----------------------------------------------------------------------------
                    try {
                        cursorCount = crud.getDataTanggal(getContext(), tanggal);
                        int jumlahData = cursorCount.getCount();
                        float yData  = (float) jumlahData * 2;
                        // -------------------------------------------------------------------------
                        yValues.add(new Entry(xData, yData)); // Posisi di grafik
                    } finally {
                        cursorCount.close();
                    }
                    // -----------------------------------------------------------------------------
                    String noYears = tanggal.substring(0,tanggal.length()-5);
                    days[i] = noYears;
                    // -----------------------------------------------------------------------------
                    xData++;
                    i++;
                }
            } finally {
                cursorTanggal.close();
            }
        } else {
            yValues.add(new Entry(0, 0f));
            yValues.add(new Entry(1, 0f));
            yValues.add(new Entry(2, 0f));
            yValues.add(new Entry(3, 0f));
            yValues.add(new Entry(4, 0f));
            yValues.add(new Entry(5, 0f));
            yValues.add(new Entry(6, 0f));
            days = new String[7];
            for (int i = 0; i<days.length ; i++){
                days[i] = "0";
            }
        }

        LineDataSet mLineDataSet = new LineDataSet(yValues, "Grafik PerHari Tahun " + methodHelper.getSystemYear());
        // -----------------------------------------------------------------------------------------
        mLineDataSet.setFillAlpha(10);
        mLineDataSet.setColor(Color.BLACK);
        mLineDataSet.setLineWidth(3f);
        mLineDataSet.setValueTextSize(10f);
        mLineDataSet.setValueTextColor(Color.BLACK);
        mLineDataSet.setLabel("ASASAS");
        // -----------------------------------------------------------------------------------------
        dataSets.add(mLineDataSet);
        LineData data = new LineData(dataSets);
        // -----------------------------------------------------------------------------------------
        mChart.setData(data);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter( new StatistikFragment.MyXAxisValueFormatter(days));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_statistik_grafik, container, false);

        // -----------------------------------------------------------------------------------------
        mChart = (LineChart) rootView.findViewById(R.id.stat_chart);
        // -----------------------------------------------------------------------------------------
        CreateGrafik();
        // -----------------------------------------------------------------------------------------

        return rootView;
    }

}