package RESTAssuredClient.RESTAssuredClient;

import com.microsoft.bot.schema.models.Activity;

import java.util.ArrayList;
import java.util.List;

public class ResponseParserActivities {
    private List<Activity> activities;
    private String watermark;

    public ResponseParserActivities() {
    }

    public ResponseParserActivities(String watermark, List<Activity> activities) {
        this.watermark = watermark;
        this.activities = activities;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
