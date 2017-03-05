package catalyst.hourtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anesu on 3/4/17.
 */

public class FeedAdapter extends ArrayAdapter<Event> {
    private int resource;
    private List<Event> events;
    public FeedAdapter(Context context, int resource, List<Event> events) {
        super(context, resource);
        this.resource = resource;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView courseName = (TextView) convertView.findViewById(R.id.course_name);
        TextView taName = (TextView) convertView.findViewById(R.id.ta);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView day = (TextView) convertView.findViewById(R.id.day);

        Event event  = getItem(position);

        courseName.setText(event.getCourseName());
        taName.setText(event.getTaName());
        time.setText(event.getTime());
        day.setText(event.getDay());

        return convertView;
    }
}
