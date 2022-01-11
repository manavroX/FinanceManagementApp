package org.example.tablayoutsfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class FragmentEarning extends Fragment {

    Button doneBtn;

    EditText titleText;
    EditText valueText;
    Spinner categoryDropdown;
    EditText dateText;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earning, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("work", "works till here fragment earning");
        doneBtn = getView().findViewById(R.id.btnInsertEarning);
        titleText = getView().findViewById(R.id.earntitle);
        valueText = getView().findViewById(R.id.earnvalue);
        categoryDropdown = getView().findViewById(R.id.earncategory);
        dateText = getView().findViewById(R.id.earndate);

        String[] categories = new String[]{"Salary","Rent", "Bonus", "Stocks"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        categoryDropdown.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                DatabaseHandler db = new DatabaseHandler(getActivity());
                Log.d("Insert: ", "Inserting ..");
                db.addCategory(
                        new Category(
                                1,
                                2,
                                titleText.getText().toString(),
                                Integer.parseInt(valueText.getText().toString()),
                                categoryDropdown.getSelectedItem().toString(),
                                dateText.getText().toString()
                            )
                    );
                Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }
}