package org.example.tablayoutsfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class FragmentExpense extends Fragment {

    Button doneBtn;
    SwitchCompat simpleToggleButton;
    EditText titleText;
    EditText valueText;
    Spinner categoryDropdown;
    EditText dateText;

    Boolean toggleButtonState;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("work", "works till here");
//        Toast.makeText(FragmentExpense.this, "works till here", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_expense, container, false);
//        Toast.makeText(getApplicationContext(), "hello world", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("work", "works till here fragment earning");
        doneBtn = getView().findViewById(R.id.btnInsertExpense);
        simpleToggleButton = (SwitchCompat) getView().findViewById(R.id.needwantswitch);

        titleText = getView().findViewById(R.id.exptitle);
        valueText = getView().findViewById(R.id.expvalue);
        categoryDropdown = getView().findViewById(R.id.expcategory);
        dateText = getView().findViewById(R.id.expdate);

        String[] categories = new String[]{"Grocery","Dairy", "Meals", "Travel", "Subscriptions", "Bills", "Fuel", "Rent", "Shopping"};
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

                DatabaseHandler db = new DatabaseHandler(getActivity());
                Log.d("Insert: ", "Inserting ..");
                toggleButtonState = simpleToggleButton.isChecked();
                int toggleVal = (toggleButtonState) ? 1 : 0;
//                Toast.makeText(getActivity(), String.valueOf(toggleVal), Toast.LENGTH_SHORT).show();
                db.addCategory(
                        new Category(
                                0,
                                toggleVal,
                                titleText.getText().toString(),
                                Integer.parseInt(valueText.getText().toString()),
                                categoryDropdown.getSelectedItem().toString(),
                                dateText.getText().toString()
                        )
                );
                Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),categoryDropdown.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }

}