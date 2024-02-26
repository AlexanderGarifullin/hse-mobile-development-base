package org.hse.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;


public class ScheduleActivity extends AppCompatActivity {

    public static String ARG_TYPE = "Type";
    public static String ARG_MODE = "Mode";
    public static String ARG_ID = "Id";
    public static int DEFAULT_ID = 0;
    private ScheduleMode mode;
    private ScheduleType type;
    private String name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        type = (ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);

        //TextView title = findViewById(R.id.title);

    }
}