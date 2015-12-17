package com.antyzero.smoksmog.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultYAxisValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.History;
import pl.malopolska.smoksmog.model.Particulate;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ParticulateHistoryViewHolder> {

    private List<Particulate> particulates;

    public HistoryAdapter(List<Particulate> particulates) {
        this.particulates = particulates;
    }

    @Override
    public ParticulateHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ParticulateHistoryViewHolder(inflater.inflate(R.layout.item_chart, parent, false));
    }

    @Override
    public int getItemCount() {
        return particulates.size();
    }

    @Override
    public void onBindViewHolder(ParticulateHistoryViewHolder holder, int position) {
        holder.bind(particulates.get(position));
    }

    static class ParticulateHistoryViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.lineChart)
        LineChart chart;

        public ParticulateHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Particulate particulate) {
            final List<History> historyList = particulate.getValues();
            Collections.sort(historyList, (history1, history2) -> history1.getDate().isAfter(history2.getDate()) ? 1 : -1);

            final int lineColor = itemView.getContext().getResources().getColor(R.color.primary);
            final int fillColor = itemView.getContext().getResources().getColor(R.color.accent);
            final LineData lineData = getLineData(historyList, lineColor, fillColor);

            chart.setData(lineData);
            chart.setDescription(particulate.getName() + " (" + particulate.getShortName() + ")");
            chart.setDescriptionTextSize(12);

            LimitLine normLimitLine = createLimitLine(particulate);
            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().removeAllLimitLines();
            chart.getAxisLeft().addLimitLine(normLimitLine);
            chart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            chart.getAxisLeft().setAxisMaxValue(getYAxisMac(particulate, lineData));

            chart.getLegend().setEnabled(false);

            chart.invalidate();
        }

        private float getYAxisMac(Particulate particulate, LineData lineData) {
            float baseMax = particulate.getNorm() > lineData.getYMax() ? particulate.getNorm() : lineData.getYMax();
            return baseMax + (baseMax / 100 * 10);
        }

        @NonNull
        private LineData getLineData(List<History> historyList, int lineColor, int fillColor) {
            final LineData lineData = new LineData();
            final List<Entry> entries = new ArrayList<>();

            for (int i = 0; i < historyList.size(); i++) {
                final History history = historyList.get(i);
                final Entry entry = new Entry(history.getValue(), i);
                entries.add(entry);

                //add short day of week (eg. "Tue ")
                lineData.addXValue(history.getDate().toString("EEE"));
            }
            final LineDataSet lineDataSet = new LineDataSet(entries, null);
            lineDataSet.setCircleColorHole(lineColor);
            lineDataSet.setCircleColor(lineColor);
            lineDataSet.setColor(lineColor);
            lineDataSet.setValueTextSize(10);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillAlpha(50);
            lineDataSet.setFillColor(fillColor);
            lineDataSet.setDrawCubic(true);

            lineData.addDataSet(lineDataSet);
            lineData.setHighlightEnabled(false);
            lineData.setDrawValues(false);
            return lineData;
        }

        @NonNull
        private LimitLine createLimitLine(Particulate particulate) {
            LimitLine normLimitLine = new LimitLine(particulate.getNorm(), itemView.getContext().getString(R.string.label_norm));
            normLimitLine.setLineWidth(1f);
            normLimitLine.setLineColor(Color.DKGRAY);
            normLimitLine.setTextColor(Color.DKGRAY);
            normLimitLine.setTextSize(10);
            normLimitLine.enableDashedLine(10f, 10f, 0f);
            normLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            normLimitLine.setTextSize(10f);
            return normLimitLine;
        }
    }
}