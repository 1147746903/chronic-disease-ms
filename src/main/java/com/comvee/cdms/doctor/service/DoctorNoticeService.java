package com.comvee.cdms.doctor.service;

import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import org.springframework.scheduling.annotation.Async;

public interface DoctorNoticeService {

    @Async
    void memberHealthWarningNotice(AddMemberHealthWarningNoticeDTO param);
}
