package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.entity.Application;
import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.exception.ApplicationAlreadyExistsException;
import com.bjit.TraineeSelection.model.ApplicantDto;
import com.bjit.TraineeSelection.model.ApplicationDto;
import com.bjit.TraineeSelection.model.CircularDto;
import com.bjit.TraineeSelection.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;


    @PostMapping("/apply")
    public ResponseEntity<Object>applyCircular(@RequestBody ApplicationDto applicationDto){
        return applicantService.applyCircular(applicationDto);
    }

    @GetMapping("/view/{circularId}")
    public ResponseEntity<Object> viewCirculars(@PathVariable Long circularId) {
        return applicantService.viewCircular(circularId);
    }

    @GetMapping("/circular/all")
    public ResponseEntity<List<Circular>>getAllCirculars()
    {
        return this.applicantService.getAllCirculars();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getApplicantId(@PathVariable String email)
    {
        return this.applicantService.getApplicantId(email);
    }

    @GetMapping("/applications/{applicantId}")
    public ResponseEntity<List<Application>> ApplicantApplications(@PathVariable Long applicantId)
    {
        List<Application> application=this.applicantService.ApplicantApplications(applicantId);

        return new ResponseEntity<>(application,HttpStatus.OK);
    }

    @GetMapping("/admit-card/{applicantId}")
    public ResponseEntity<byte[]> getAdmitCard(@PathVariable Long applicantId) {
        return applicantService.getAdmitCard(applicantId);
    }

    @PostMapping("/upload/{applicantId}")
    public String handleFileUpload(@PathVariable Long applicantId,@RequestParam("image") MultipartFile image,
                                   @RequestParam("cv") MultipartFile cv) {
        try {
            ApplicantService.findById(applicantId);
            applicantService.uploadImageAndCv(applicantId, image, cv);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error"; // Redirect to an error page if there's an issue with file upload.
        }

        return "redirect:/success"; // Redirect to a success page after successful upload.
    }




}
