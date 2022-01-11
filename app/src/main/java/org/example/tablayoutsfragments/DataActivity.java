package org.example.tablayoutsfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    ListView simpleListView;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        simpleListView = (ListView) findViewById(R.id.simpleListView);

        pieChart = findViewById(R.id.pieChart_view);

        DatabaseHandler db = new DatabaseHandler(this);

        Log.d("Reading: ", "Reading all categories..");
        List<Category> categories = db.getAllCategories();
        List<String> finalCategories = new ArrayList<>();

        initPieChart();
        showPieChart(categories);

        for (Category ct : categories) {
            String toBeAdded = ct.getDate() + "\t\t\t\t" + ct.getTitle() + "\n";
            toBeAdded += ct.getCategory() + "\t\t";
//            Log.e("work", ct.getCategory().toString());
            if(ct.getWant_need()==2)
                toBeAdded+="\t";
            else if(ct.getWant_need()==1)
                toBeAdded+="Want" + "\t";
            else
            {
                toBeAdded+="Need" + "\t";
            }
//            toBeAdded += (ct.getWant_need()==1) ? "Want" : "Need";
            toBeAdded+="\n";
            toBeAdded += (ct.getEI()==1) ? "+" : "-";
            toBeAdded += ct.getValue() + "\t";

            // Writing Contacts to log
            finalCategories.add(toBeAdded);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.item_view, R.id.itemTextView, finalCategories);
        simpleListView.setAdapter(arrayAdapter);
    }

    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"));

    }

    private void showPieChart(List<Category> categories){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();


        typeAmountMap.put("Grocery",0);
        typeAmountMap.put("Dairy",0);
        typeAmountMap.put("Meals",0);
        typeAmountMap.put("Travel",0);
        typeAmountMap.put("Subscriptions",0);
        typeAmountMap.put("Bills",0);
        typeAmountMap.put("Fuel",0);
        typeAmountMap.put("Rent",0);
        typeAmountMap.put("Shopping",0);

        for(Category ct : categories)
        {
//            typeAmountMap.computeIfPresent("hello", (k, v) -> v + 1);
            if(ct.getEI()==0)
                typeAmountMap.put(ct.getCategory(), typeAmountMap.get(ct.getCategory()) + ct.getValue());
//            typeAmountMap.put(ct.getCategory(), typeAmountMap.get(ct.getCategory())+ct.getValue());
        }

        Iterator iter = typeAmountMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String,Integer> entry = (Map.Entry) iter.next();
            if(entry.getValue()==0){
                iter.remove();
            }
        }

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));
        colors.add(Color.parseColor("#9c2752"));
        colors.add(Color.parseColor("#1e5234"));


        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}