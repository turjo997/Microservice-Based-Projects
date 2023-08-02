package com.bjit.tss.service;


import com.bjit.tss.model.CircularModel;
import org.springframework.http.ResponseEntity;

public interface CircularService {

    ResponseEntity<Object> createCircular(CircularModel circularModel);

    ResponseEntity<Object> updateCircular(Long circularId, CircularModel circularModel);

    ResponseEntity<Object> deleteCircular(Long circularId);

    ResponseEntity<Object> getAllCirculars();

    ResponseEntity<Object> getDetailById(Long circularId);
}
