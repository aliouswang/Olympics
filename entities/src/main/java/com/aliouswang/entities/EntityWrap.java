package com.aliouswang.entities;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/3/8.
 */
public class EntityWrap<T> {

    public ArrayList<T> statuses;

    public boolean isEmpty() {
        return statuses == null || statuses.isEmpty();
    }

}
