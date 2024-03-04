package org.hse.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ScheduleActivity extends AppCompatActivity {

    public static String ARG_TYPE = "Type";
    public static String ARG_MODE = "Mode";
    public static String ARG_ID = "Id";
    public static String ARG_NAME = "Name";
    public static String ARG_DATE = "Time";
    public static int DEFAULT_ID = 0;
    private ScheduleMode mode;
    private ScheduleType type;
    private String name, time;
    private int id;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private TextView scheduleTitle, serverTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        type = (ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);
        name = getIntent().getStringExtra(ARG_NAME);
        time = getIntent().getStringExtra(ARG_DATE);

        recyclerView = findViewById(R.id.timetable_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        OnItemClick onItemClick = new OnItemClick() {
            @Override
            public void onClick(ScheduleItem data) {

            }
        };
        adapter = new ItemAdapter(onItemClick);
        recyclerView.setAdapter(adapter);
        initData();

        scheduleTitle = findViewById(R.id.scheduleTitle);
        setTitle();
        serverTime = findViewById(R.id.serverTime);
        setServerTime();
    }

    private void setTitle() {
        scheduleTitle.setText(name);
    }

    private void setServerTime() {
        serverTime.setText(time);
    }

    private void initData() {
        List<ScheduleItem> list = new ArrayList<>();

        ScheduleItemHeader header = new ScheduleItemHeader();
        header.setTitle("Понедельник, 28 января");
        list.add(header);

        ScheduleItem item = new ScheduleItem();
        item.setStart("10:30");
        item.setEnd("11:50");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("12:10");
        item.setEnd("13:20");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 504Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("13:40");
        item.setEnd("15:50");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 506Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("16:00");
        item.setEnd("17:20");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 505Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("17:40");
        item.setEnd("19:00");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 507Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new ScheduleItem();
        item.setStart("19:10");
        item.setEnd("20:20");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 501Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);


        item = new ScheduleItem();
        item.setStart("20:30");
        item.setEnd("21:20");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 506Б Кочновский пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        adapter.setDataList(list);
    }
}