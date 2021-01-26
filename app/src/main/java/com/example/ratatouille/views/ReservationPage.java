package com.example.ratatouille.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ratatouille.R;
import com.example.ratatouille.models.ReservationRequest;
import com.example.ratatouille.utils.Utils;
import com.example.ratatouille.vars.VariablesUsed;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class ReservationPage extends AppCompatActivity {

    private TextView numOfPerson, datePick, descriptionReservation, timePick;
    private LinearLayout backButton, submitReservation;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);

        numOfPerson = findViewById(R.id.numOfPerson);
        datePick = findViewById(R.id.datePickerButton);
        timePick = findViewById(R.id.timePicker);
        descriptionReservation = findViewById(R.id.descriptionReservation);
        backButton = findViewById(R.id.reservationpage_backButton);
        submitReservation = findViewById(R.id.submitReservation);
        context = this;

        final Calendar myCalendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);

                String myFormat = "hh:mm aa"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                timePick.setText(sdf.format(myCalendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datePick.setText(sdf.format(myCalendar.getTime()));
            }

        };

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }
        });
        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tpd = new TimePickerDialog(context, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = MediaPlayer.create(ReservationPage.this, R.raw.personleave);
                player.start();
                finish();
            }
        });
        numOfPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        submitReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datePick.getText().toString().equals("Pick a Date") ||
                timePick.getText().toString().equals("Pick Time")) return;
                if(Integer.parseInt(numOfPerson.getText().toString()) <= 0) return;
                if(descriptionReservation.getText().toString().equals("") ||
                descriptionReservation.getText().toString().equals("Write description")) return;
                Timestamp reservation = new Timestamp(myCalendar.getTimeInMillis());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                ReservationRequest rr = new ReservationRequest(UUID.randomUUID().toString(), VariablesUsed.loggedUser.getUid(), VariablesUsed.currentRestoDetail.getResto_id(),
                        now.toString(), reservation.toString(), Integer.parseInt(numOfPerson.getText().toString()), descriptionReservation.getText().toString(), null);
                rr.save();
                Utils.showDialogMessage(R.drawable.verified_logo, context, "Booking Success!", "Please wait till the Restaurant respond.");
            }
        });
    }

}