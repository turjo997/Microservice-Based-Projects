package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.CircularEntity;
import com.bjit.tss.model.CircularModel;
import com.bjit.tss.repository.CircularRepository;
import com.bjit.tss.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CircularServiceImplementation implements CircularService {

    private final CircularRepository circularRepository;

    @Override
    public ResponseEntity<Object> createCircular(CircularModel circularModel) {
        CircularEntity circularEntity = new CircularEntity();
        circularEntity.setTitle(circularModel.getTitle());
        circularEntity.setDescription(circularModel.getDescription());

        CircularEntity savedCircular = circularRepository.save(circularEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCircular);
    }

    @Override
    public ResponseEntity<Object> updateCircular(Long circularId, CircularModel circularModel) {
        Optional<CircularEntity> optionalCircular = circularRepository.findById(circularId);
        if (optionalCircular.isPresent()) {
            CircularEntity existingCircular = optionalCircular.get();
            existingCircular.setTitle(circularModel.getTitle());
            existingCircular.setDescription(circularModel.getDescription());

            CircularEntity updatedCircular = circularRepository.save(existingCircular);
            return ResponseEntity.ok(updatedCircular);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteCircular(Long circularId) {
        Optional<CircularEntity> optionalCircular = circularRepository.findById(circularId);
        if (optionalCircular.isPresent()) {
            CircularEntity existingCircular = optionalCircular.get();
            circularRepository.delete(existingCircular);
            return ResponseEntity.ok(existingCircular);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllCirculars() {
        List<CircularEntity> circulars = circularRepository.findAll();
        return ResponseEntity.ok(circulars);
    }

    @Override
    public ResponseEntity<Object> getDetailById(Long circularId) {
        Optional<CircularEntity> optionalCircular = circularRepository.findById(circularId);
        if (optionalCircular.isPresent()) {
            CircularEntity circular = optionalCircular.get();
            return ResponseEntity.ok(circular);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
