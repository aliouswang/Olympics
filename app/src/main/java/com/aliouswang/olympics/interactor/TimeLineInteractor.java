package com.aliouswang.olympics.interactor;

import com.aliouswang.entities.TimeLineWrap;

/**
 * Created by aliouswang on 16/3/8.
 */
public interface TimeLineInteractor {

    void timeLineFetchSuccess(TimeLineWrap timeLineWrap);

    void timeLineFetchError(String error);

}
