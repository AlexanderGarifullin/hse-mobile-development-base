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

public class StudentActivity extends AppCompatActivity {

    private TextView time, status, subject, cabinet, corp, teacher;
    private Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

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

        time = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        initData();
    }

    private void initGroupList(List<Group> groups){
        int id = 1;
        String[] program = {"БИ","ПИ"};
        int[] year = {20,21};
        int[] groupNumber = {1,2};
        for (int i = 0; i < program.length; ++i) {
            for (int j = 0; j < year.length; ++j) {
                for (int k = 0; k < groupNumber.length; ++k) {
                    groups.add(new Group(id++, unite(program[i],year[j], groupNumber[k])));
                }
            }
        }
    }
    private String unite(String program, int year, int groupNumber) {
        return program + "-" + year + "-" + groupNumber;
    }

    private void initTime() {
        currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE",
                Locale.forLanguageTag("RU"));
        String[] dateSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = getString(R.string.label_time) + dateSplit[0] + " " +
                dateSplit[1].substring(0,1).toUpperCase() +
                dateSplit[1].substring(1);
        time.setText(timeText);
    }

    private void initData() {
        status.setText(R.string.label_status_base);
        subject.setText(R.string.label_subject_base);
        cabinet.setText(R.string.label_cabinet_base);
        corp.setText(R.string.label_corp_base);
        teacher.setText(R.string.label_teacher_base);
    }
}