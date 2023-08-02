package com.bjit.tss.service;

import com.bjit.tss.model.NoticeModel;
import org.springframework.http.ResponseEntity;

public interface NoticeService {
    ResponseEntity<Object> createNotice(NoticeModel noticeModel);

    ResponseEntity<Object> updateNotice(Long noticeId, NoticeModel noticeModel);

    ResponseEntity<Object> deleteNotice(Long noticeId);

    ResponseEntity<Object> getAllNotices();

    ResponseEntity<Object> getNoticeById(Long noticeId);
}
