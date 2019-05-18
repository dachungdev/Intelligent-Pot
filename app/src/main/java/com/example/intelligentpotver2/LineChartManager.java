package com.example.intelligentpotver2;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class LineChartManager{

    private LineChart lineChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<String> timeList = new ArrayList<>();
    private int valueId;

    public LineChartManager(LineChart mLineChart,String name,int color,int valueId){
        this.lineChart = mLineChart;
        this.valueId = valueId;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        xAxis = lineChart.getXAxis();
        initLineChart();
        initLineDataSet(name,color);
    }

    public void initLineChart(){
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(8f);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(8);
        xAxis.setTextSize(5);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return timeList.get((int) value%timeList.size());
            }
        });

        leftAxis.setAxisMaximum(0.0f);
        rightAxis.setAxisMaximum(0.0f);

        switch (valueId){
            case 0:
                setEtY();
                break;
            case 1:
                setEhY();
                break;
            case 2:
                setThY();
                break;
            case 3:
                setPhY();
                break;
            case 4:
                setCo2Y();
                break;
            default:
                break;
        }
    }

    public void initLineDataSet(String name,int color){
        lineDataSet = new LineDataSet(null,name);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleRadius(1.5f);
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setHighLightColor(color);

        switch (valueId){
            case 0:
                setEtData();
                break;
            case 1:
                setEhData();
                break;
            case 2:
                setThData();
                break;
            case 3:
                setPhData();
                break;
            case 4:
                setCo2Data();
                break;
            default:
                break;
        }
        lineDataSet.setDrawFilled(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueTextSize(10.0f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    public void addEntry(float number,String time){
        if (lineDataSet.getEntryCount() == 0){
            lineData.addDataSet(lineDataSet);
        }

        lineChart.setData(lineData);
        if (timeList.size()>11){
            timeList.clear();
        }

        timeList.add(time);

        Entry entry = new Entry(lineDataSet.getEntryCount(),number);
        lineData.addEntry(entry,0);

        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(8);
        lineChart.moveViewToX(lineData.getEntryCount()-4);
    }

    public void setYAxis(float max,float min,int labelCount){
        if (max<min){
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount,false);
    }

    public void setHightLimitLine(float high,String name,int color){
        if (name==null){
            name = "高限制线";
        }

        LimitLine hightLimit = new LimitLine(high,name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    public void setLowLimitLine(int low,String name){
        if (name == null){
            name = "低限制线";
        }

        LimitLine hightLimit = new LimitLine(low,name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    public void setDescription(String str){
        Description description = new Description();
        description.setText(str);
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    public void setEtY(){
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (value +"°");
            }
        });
    }

    public void setEtData(){
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (value +"°");
            }
        });
    }

    public void setEhY(){
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (value +"°");
            }
        });
    }

    public void setEhData(){
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (value +"°");
            }
        });
    }

    public void setThY(){
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ( value+"%");
            }
        });
    }

    public void setThData(){
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (value+"%");
            }
        });
    }

    public void setPhY(){
    }

    public void setPhData(){
        lineDataSet.setValueFormatter(new DefaultValueFormatter(1));
    }

    public void setCo2Y(){
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(1));
    }

    public void setCo2Data(){
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (value+"%");
            }
        });
    }
}
