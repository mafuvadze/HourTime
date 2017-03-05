package catalyst.hourtime;

/**
 * Created by anesu on 3/4/17.
 */

public class Event {
    private String courseName, taName, time, day;
    private Long rawTime;

    public Event(String courseName, String taName, String time, String day, Long rawTime) {
        this.courseName = courseName;
        this.taName = taName;
        this.time = time;
        this.day = day;
        this.rawTime = rawTime;
    }

    public Event(){ /*no argument parameter*/ };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getRawTime() {
        return rawTime;
    }

    public void setRawTime(Long rawTime) {
        this.rawTime = rawTime;
    }
}
