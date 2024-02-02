package org.hse.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeacherActivity extends AppCompatActivity {

    private TextView timeLabel, timeValue, status, subject, cabinet, corp, teacher;
    private Date currentTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        final Spinner spinner = findViewById(R.id.groupList);

        List<Group> groups = new ArrayList<>();
        initGroupList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = adapter.getItem(position);
                Log.d("select", "onItemSelected: " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeLabel = findViewById(R.id.timeLabelStudent);
        timeValue = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        initData();
    }

    private void initGroupList(List<Group> groups){
        groups.add(new Group(1, "Преподаватель 1"));
        groups.add(new Group(2, "Преподаватель 2"));
    }

    private void initTime() {
        currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE",
                Locale.forLanguageTag("RU"));
        String[] dateSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = dateSplit[0] + " " +
                dateSplit[1].substring(0,1).toUpperCase() +
                dateSplit[1].substring(1);
        timeValue.setText(timeText);
    }

    private void initData() {
        timeLabel.setText(R.string.label_time);
        status.setText(R.string.label_status_base);
        subject.setText(R.string.label_subject_base);
        cabinet.setText(R.string.label_cabinet_base);
        corp.setText(R.string.label_corp_base);
        teacher.setText(R.string.label_teacher_base);
    }
}