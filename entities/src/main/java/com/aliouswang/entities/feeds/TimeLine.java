package com.aliouswang.entities.feeds;

import com.aliouswang.entities.BaseEntity;
import com.aliouswang.entities.images.TimeLineImage;
import com.aliouswang.entities.user.User;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/3/8.
 */
public class TimeLine extends BaseEntity{

    public String created_at;
    public long id;
    public String idStr;
    public String text;
    public int textLength;
    public int source_allowclick;
    public int source_type;
    public String source;
    public boolean favorited;
    public boolean truncated;
    public User user;
    public String thumbnail_pic;
    public String bmiddle_pic;
    public String original_pic;
    public ArrayList<TimeLineImage> pic_urls;

    public String getSourceDesc() {
        if (source != null) {
            int preTag = source.lastIndexOf("\">");
            int tailTag = source.lastIndexOf("</a>");
            if (preTag > 0 && tailTag > 0 && preTag < tailTag) {
                return source.substring(preTag + 2, tailTag);
            }
        }
        return "";
    }

}
