package com.comvee.cdms.actiontrack.mapper;

import com.comvee.cdms.actiontrack.model.AddActionTrackRecordPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActionTrackRecordMapper {

    @Insert(value = {
            "<script>"
            ,"INSERT INTO `t_action_track_record` (`uid`, `identify`, `action_name`, `action_dt`, `request_uri`, `ip`, `request_data`, `response_data`) VALUES "
            ,"<foreach collection = 'list' item = 'item' separator = ','>"
            ,"(#{item.uid}, #{item.identify}, #{item.actionName} , #{item.actionDt} , #{item.requestUri} , #{item.ip} , #{item.requestData} , #{item.responseData})"
            ,"</foreach>"
            ,"</script>"
    })
    void batchAddActionTrackRecord(@Param("list") List<AddActionTrackRecordPO> list);
}
