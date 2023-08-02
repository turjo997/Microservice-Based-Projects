package com.bjit.trainingmanagementsystem.service.classroom;

import com.bjit.trainingmanagementsystem.models.classroom.NoticeCreateRequest;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeModel;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeUpdateModel;

import java.util.List;

public interface NoticeService {
    NoticeModel createNotice(NoticeCreateRequest noticeCreateRequest);

    List<NoticeModel> getNoticesByNoticeBoardId(Long noticeBoardId);

    NoticeModel updateNotice(Long noticeId, NoticeUpdateModel noticeUpdateModel);
}
