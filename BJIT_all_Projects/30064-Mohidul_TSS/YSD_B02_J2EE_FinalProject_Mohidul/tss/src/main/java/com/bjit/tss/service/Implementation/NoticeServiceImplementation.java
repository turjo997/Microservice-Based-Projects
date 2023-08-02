package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.NoticeEntity;
import com.bjit.tss.model.NoticeModel;
import com.bjit.tss.repository.NoticeRepository;
import com.bjit.tss.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImplementation implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<Object> createNotice(NoticeModel noticeModel) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .noticeTitle(noticeModel.getNoticeTitle())
                .noticeBody(noticeModel.getNoticeBody())
                .build();

        NoticeEntity savedNotice = noticeRepository.save(noticeEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotice);
    }

    @Override
    public ResponseEntity<Object> updateNotice(Long noticeId, NoticeModel noticeModel) {
        Optional<NoticeEntity> optionalNotice = noticeRepository.findById(noticeId);
        if (optionalNotice.isPresent()) {
            NoticeEntity existingNotice = optionalNotice.get();
            existingNotice.setNoticeTitle(noticeModel.getNoticeTitle());
            existingNotice.setNoticeBody(noticeModel.getNoticeBody());

            noticeRepository.save(existingNotice);
            return ResponseEntity.ok(existingNotice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteNotice(Long noticeId) {
        Optional<NoticeEntity> optionalNotice = noticeRepository.findById(noticeId);
        if (optionalNotice.isPresent()) {
            NoticeEntity existingNotice = optionalNotice.get();
            noticeRepository.delete(existingNotice);
            return ResponseEntity.ok(existingNotice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllNotices() {
        List<NoticeEntity> notices = noticeRepository.findAll();
        return ResponseEntity.ok(notices);
    }

    @Override
    public ResponseEntity<Object> getNoticeById(Long noticeId) {
        Optional<NoticeEntity> optionalNotice = noticeRepository.findById(noticeId);
        if (optionalNotice.isPresent()) {
            NoticeEntity notice = optionalNotice.get();
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
