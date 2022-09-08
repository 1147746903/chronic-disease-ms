package com.comvee.cdms.actiontrack.mapper;

import com.comvee.cdms.actiontrack.model.ActionTrackConfigPO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ActionTrackConfigMapper {

    @Select("select t.action_name,t.request_uri from t_action_track_config t where t.valid = 1")
    @Results(value = {
            @Result(property = "actionName" ,column = "action_name"),
            @Result(property = "requestUri" ,column = "request_uri")
    })
    List<ActionTrackConfigPO> listActionTrackConfig();
}
