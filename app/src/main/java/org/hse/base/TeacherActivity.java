package org.hse.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class TeacherActivity extends BaseActivity {

    private TextView timeLabel, timeValue, status, subject, cabinet, corp, teacher;
    private Date currentTime;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        spinner = findViewById(R.id.groupList);

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
        initTime(getString(R.string.teacherType));

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        initData();

        View scheduleDay = findViewById(R.id.btn_day);
        scheduleDay.setOnClickListener(v -> showSchedule(ScheduleType.DAY));

        View scheduleWeek = findViewById(R.id.btn_week);
        scheduleWeek.setOnClickListener(v -> showSchedule(ScheduleType.WEEK));
    }

    private void showSchedule(ScheduleType type) {
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Group)) {
            return;
        }
        showScheduleImpl(ScheduleMode.TEACHER, type, (Group) selectedItem);
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, Group group) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, group.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_NAME, group.getName());
        intent.putExtra(ScheduleActivity.ARG_DATE, getResponseTime());
        startActivity(intent);
    }

    private void initGroupList(List<Group> groups){
        groups.add(new Group(1, "Преподаватель 1"));
        groups.add(new Group(2, "Преподаватель 2"));
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