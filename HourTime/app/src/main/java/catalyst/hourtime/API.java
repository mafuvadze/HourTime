package catalyst.hourtime;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anesu on 3/4/17.
 */

public class API implements Response.ErrorListener{

    private static String site = "http://localhost:8080";
    private static String get_hours = "/api/get_class?";

    private static RequestQueue queue;


    public interface OnDataReceived {
        public void onDataReceived(Object data);
    }

    public API(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getHours(String courseId, final OnDataReceived listener) throws JSONException {
        final String url = site + get_hours + "course_id=" + courseId;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Event> data = new ArrayList<>();

                try {
                    data.addAll(parseHours(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onDataReceived(data);

            }
        },this);

        queue.add(request);
    }

    private List<Event> parseHours(JSONObject json) throws JSONException {
        String courseName = json.getString("course_name");

        if(!json.has("hours")){
            return new ArrayList<>();
        }

        List<Event> events = new ArrayList<>();

        JSONObject hours = json.getJSONObject("office_hours");
        Iterator<String> keys = hours.keys();
        while(keys.hasNext()){
            JSONObject eventJson = hours.getJSONObject(keys.next());
            Event singleEvent = new Event();
            singleEvent.setCourseName(courseName);
            singleEvent.setTaName(eventJson.getString("ta_name"));

            Long time = eventJson.getLong("time");
            String dateStr = getStringDate(time);
            String timeStr = getStringTime(time, true);

            singleEvent.setTime(timeStr);
            singleEvent.setDay(dateStr);
            singleEvent.setRawTime(time);

            events.add(singleEvent);
        }

        return events;
    }

    public String getStringDate(long time){
        String date = "";
        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(time * 1000);

        SimpleDateFormat format1 = new SimpleDateFormat("MMM d");
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");

        if(start.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && start.get(Calendar.MONTH) == now.get(Calendar.MONTH)){
            int diff = start.get(Calendar.DAY_OF_MONTH) - now.get(Calendar.DAY_OF_MONTH);
            if(diff == 0){
                date = "Today";
            }else if(diff == 1){
                date = "Tomorrow";
            }else if(diff < 7){
                date = format2.format(start.getTime());
            }
        }else{
            date = format1.format(start.getTime());
        }

        return date;
    }

    public String getStringTime(long time, boolean tag){ //tag used to determine if AM/PM is included
        SimpleDateFormat format1 = new SimpleDateFormat("h:mm aaa");
        SimpleDateFormat format2 = new SimpleDateFormat("h:mm");
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(time * 1000);

        String date = "";
        if(tag){
            date = format1.format(now.getTime());
        }else{
            date = format2.format(now.getTime());
        }

        return date;

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("api error", error.toString());
    }

}
