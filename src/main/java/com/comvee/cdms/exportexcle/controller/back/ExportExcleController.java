package com.comvee.cdms.exportexcle.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.exportexcle.model.ExportDataDTO;
import com.comvee.cdms.exportexcle.service.ExportExcleService;
import com.comvee.cdms.exportexcle.tool.ExcelUtil;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 导出数据到excle
 * @author wyc
 * @date 2019/4/18 15:04
 */
@RestController
@RequestMapping("/back/exportExcle")
public class ExportExcleController {

    private final Logger logger = LoggerFactory.getLogger(ExportExcleController.class);

    @Autowired
    private ExportExcleService exportExcleService;

}
