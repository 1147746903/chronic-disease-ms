package com.comvee.cdms.defender.wechat.job;

import com.comvee.cdms.defender.service.CourseServiceI;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @Author linr
 * @Date 2021/11/26
 * 重置患教推荐课程(每天0点)
 */
@Component
public class RecommendCourseJob{

    @Autowired
    @Qualifier("courseService")
    private CourseServiceI courseService;

    private final static Logger log = LoggerFactory.getLogger(RecommendCourseJob.class);

    @XxlJob("RecommendCourseJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("开始执行重置患教推荐课程定时器......");
        courseService.reSetRecommendCourseList();
        return null;
    }
}
