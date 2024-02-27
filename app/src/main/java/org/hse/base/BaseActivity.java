package org.hse.base;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";
    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    protected TextView time;
    protected Date currentTime;
    private OkHttpClient client = new OkHttpClient();
    protected void getTime() {
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "getTime", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseResponse(response);
            }
        });
    }

    protected void initTime(String type){
        if (Objects.equals(type, String.valueOf(R.string.studentType))) {
            time = findViewById(R.id.timeStudent);
        } else if (Objects.equals(type, String.valueOf(R.string.teacherType))){
            time = findViewById(R.id.time);
        }
        getTime();
    }

    private void showTime(Date dateTime) {
        if (dateTime == null) {
            return;
        }
        currentTime = dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE",
                Locale.forLanguageTag("RU"));
        String[] dateSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = dateSplit[0] + " " +
                dateSplit[1].substring(0,1).toUpperCase() +
                dateSplit[1].substring(1);
        time.setText(timeText);
    }

    private void parseResponse(Response response) {
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try {
            if (body == null) {
                return;
            }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeRespone = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeRespone.getTimeZone().getCurrentTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.getDefault());
            Date dateTime = simpleDateFormat.parse(currentTimeVal);
            runOnUiThread(()->showTime(dateTime));
        } catch (Exception e) {
            Log.e(TAG,"",e);
        }
    }
}
