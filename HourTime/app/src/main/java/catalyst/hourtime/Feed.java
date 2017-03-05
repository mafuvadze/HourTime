package catalyst.hourtime;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        API api = new API(this);
        try {
            api.getHours("-KeQNBcQEiN7KDRw5hCX", new API.OnDataReceived() {
                @Override
                public void onDataReceived(Object data) {
                    List<Event> events = (List<Event>) data;
                    initUi(events);
                }
            });
        } catch (JSONException e) {
            Log.d("json error", e.toString());
            e.printStackTrace();
        }

        List<Event> events = new ArrayList<>();
        events.add(new Event("CS250-spring", "Billy bob", "10:00 pm", "saturday", (long) 93394423));
        events.add(new Event("CS250-spring", "Billy bob", "11:30 pm", "saturday", (long) 93394442));
        events.add(new Event("CS250-spring", "Billy bob", "4:00 pm", "saturday", (long) 933944451));
        initUi(events);

    }

    private void initUi(List<Event> events){
        Log.d("json data", events.toString());
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                return Long.compare(e1.getRawTime(), e2.getRawTime());
            }
        });

        ListView hoursLv = (ListView) findViewById(R.id.hours);
        FeedAdapter feedAdapter = new FeedAdapter(this, R.layout.single_event, events);
        hoursLv.setAdapter(feedAdapter);

    }

    private void addOfficeHours() {
    }

}
