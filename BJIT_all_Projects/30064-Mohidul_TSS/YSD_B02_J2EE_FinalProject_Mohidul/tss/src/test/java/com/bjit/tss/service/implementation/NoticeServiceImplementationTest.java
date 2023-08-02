package com.bjit.tss.service.implementation;

import com.bjit.tss.entity.NoticeEntity;
import com.bjit.tss.model.NoticeModel;
import com.bjit.tss.repository.NoticeRepository;
import com.bjit.tss.service.Implementation.NoticeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoticeServiceImplementationTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeServiceImplementation noticeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNotice() {
        NoticeModel noticeModel = getSampleNoticeModel();
        NoticeEntity noticeEntity = getSampleNoticeEntity();

        when(noticeRepository.save(any())).thenReturn(noticeEntity);

        ResponseEntity<Object> response = noticeService.createNotice(noticeModel);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(noticeEntity, response.getBody());

        verify(noticeRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateNoticeWhenNoticeExists() {
        Long noticeId = 1L;
        NoticeModel updatedNoticeModel = getSampleNoticeModel();

        NoticeEntity existingNotice = getSampleNoticeEntity();
        existingNotice.setNoticeId(noticeId);

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(existingNotice));
        when(noticeRepository.save(any())).thenReturn(existingNotice);

        ResponseEntity<Object> response = noticeService.updateNotice(noticeId, updatedNoticeModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingNotice, response.getBody());

        verify(noticeRepository, times(1)).findById(noticeId);
        verify(noticeRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateNoticeWhenNoticeDoesNotExist() {
        Long noticeId = 1L;
        NoticeModel updatedNoticeModel = getSampleNoticeModel();

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = noticeService.updateNotice(noticeId, updatedNoticeModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(noticeRepository, times(1)).findById(noticeId);
        verify(noticeRepository, never()).save(any());
    }

    @Test
    public void testDeleteNoticeWhenNoticeExists() {
        Long noticeId = 1L;

        NoticeEntity existingNotice = getSampleNoticeEntity();
        existingNotice.setNoticeId(noticeId);

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(existingNotice));

        ResponseEntity<Object> response = noticeService.deleteNotice(noticeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingNotice, response.getBody());

        verify(noticeRepository, times(1)).findById(noticeId);
        verify(noticeRepository, times(1)).delete(existingNotice);
    }

    @Test
    public void testDeleteNoticeWhenNoticeDoesNotExist() {
        Long noticeId = 1L;

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = noticeService.deleteNotice(noticeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(noticeRepository, times(1)).findById(noticeId);
        verify(noticeRepository, never()).delete(any());
    }

    @Test
    public void testGetAllNotices() {
        List<NoticeEntity> notices = Arrays.asList(getSampleNoticeEntity(), getSampleNoticeEntity());

        when(noticeRepository.findAll()).thenReturn(notices);

        ResponseEntity<Object> response = noticeService.getAllNotices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notices, response.getBody());

        verify(noticeRepository, times(1)).findAll();
    }

    @Test
    public void testGetNoticeByIdWhenNoticeExists() {
        Long noticeId = 1L;
        NoticeEntity existingNotice = getSampleNoticeEntity();
        existingNotice.setNoticeId(noticeId);

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(existingNotice));

        ResponseEntity<Object> response = noticeService.getNoticeById(noticeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingNotice, response.getBody());

        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    public void testGetNoticeByIdWhenNoticeDoesNotExist() {
        Long noticeId = 1L;

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = noticeService.getNoticeById(noticeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(noticeRepository, times(1)).findById(noticeId);
    }

    private NoticeModel getSampleNoticeModel() {
        return new NoticeModel(
                null,
                "Sample Notice",
                "This is a sample notice body."
        );
    }

    private NoticeEntity getSampleNoticeEntity() {
        return new NoticeEntity(
                null,
                "Sample Notice",
                "This is a sample notice body."
        );
    }
}

