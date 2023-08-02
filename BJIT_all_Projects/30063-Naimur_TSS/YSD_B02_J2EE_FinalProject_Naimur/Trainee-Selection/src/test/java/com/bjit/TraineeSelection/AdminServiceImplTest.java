package com.bjit.TraineeSelection;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.model.CircularDto;
import com.bjit.TraineeSelection.repository.CircularRepository;
import com.bjit.TraineeSelection.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class AdminServiceImplTest {

    @Mock
    private CircularRepository circularRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//getCirculars
    @Test
    public void testGetCirculars_Success() {
        List<Circular> circulars = new ArrayList<>();
        circulars.add(new Circular(1L, "Circular 1", "Details 1", LocalDate.now(), "active"));
        circulars.add(new Circular(2L, "Circular 2", "Details 2", LocalDate.now(), "expired"));

        when(circularRepository.findAll()).thenReturn(circulars);

        ResponseEntity<List<Circular>> response = adminService.getCirculars();

        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == 2;
    }

    //getCircularByIds
    @Test
    public void testGetCircularByIds_Success() {
        Circular circular = new Circular(1L, "Circular 1", "Details 1", LocalDate.now(), "active");
        when(circularRepository.findById(1L)).thenReturn(Optional.of(circular));

        ResponseEntity<Object> response = adminService.getCircularByIds(1L);

        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
    }

}

