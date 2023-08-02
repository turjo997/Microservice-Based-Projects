package com.bjit.tss.controllers;

import com.bjit.tss.model.NoticeModel;
import com.bjit.tss.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> createNotice(@RequestBody NoticeModel noticeModel) {
        return noticeService.createNotice(noticeModel);
    }

    @PutMapping("/{noticeId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeModel noticeModel) {
        return noticeService.updateNotice(noticeId, noticeModel);
    }

    @DeleteMapping("/{noticeId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> deleteNotice(@PathVariable Long noticeId) {
        return noticeService.deleteNotice(noticeId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{noticeId}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getNoticeById(@PathVariable Long noticeId) {
        return noticeService.getNoticeById(noticeId);
    }
}
