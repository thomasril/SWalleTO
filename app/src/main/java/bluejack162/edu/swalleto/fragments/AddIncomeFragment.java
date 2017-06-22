package bluejack162.edu.swalleto.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import bluejack162.edu.swalleto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncomeFragment extends Fragment implements View.OnClickListener{

    EditText txtAmount, txtDate, txtTime, txtDescription;
    Spinner spinnerCategory, spinnerPaymentType;
    Button btnAddIncome;
    Calendar dateTime = Calendar.getInstance();
    DateFormat dateFormat = DateFormat.getDateInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_income, container, false);
        txtAmount = (EditText) v.findViewById(R.id.txtAmount);
        txtDate = (EditText) v.findViewById(R.id.txtDate);
        txtTime = (EditText) v.findViewById(R.id.txtTime);
        txtDescription = (EditText) v.findViewById(R.id.txtDesc);

        spinnerCategory = (Spinner) v.findViewById(R.id.spinnerCategory);
        spinnerPaymentType = (Spinner) v.findViewById(R.id.spinnerType);

        btnAddIncome = (Button) v.findViewById(R.id.btnAddIncome);

        txtDate.setHint("Set date");
        txtTime.setHint("Set time");

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);

        btnAddIncome.setOnClickListener(this);
        return v;
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            txtDate.setText(day + "/" + month + "/" + year);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            txtTime.setText(hour + ":" + minute);
        }
    };

    @Override
    public void onClick(View view) {
        if (view == txtDate) {
            new DatePickerDialog(getContext(),d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == txtTime) {
            new TimePickerDialog(getContext(), t, dateTime.get(Calendar.HOUR), dateTime.get(Calendar.MINUTE), true).show();
        } else if (view == btnAddIncome) {
            // kodingan add Incomes
        }
    }
}
