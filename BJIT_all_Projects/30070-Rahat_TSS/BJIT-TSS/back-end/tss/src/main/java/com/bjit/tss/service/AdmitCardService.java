package com.bjit.tss.service;

import java.io.ByteArrayInputStream;

public interface AdmitCardService {

    ByteArrayInputStream generateAdmit(String examineeId);
}