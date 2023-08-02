package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.entity.*;
import com.bjit.TraineeSelection.model.*;
import com.bjit.TraineeSelection.repository.AdminRepository;
import com.bjit.TraineeSelection.repository.ApplicantRepository;
import com.bjit.TraineeSelection.repository.UserRepository;
import com.bjit.TraineeSelection.service.AdminService;
import com.bjit.TraineeSelection.service.ApplicantService;
import com.bjit.TraineeSelection.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ApplicantService applicantService;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping("/evaluator/register")
    public ResponseEntity<Object> registerEvaluator(@RequestBody EvaluatorDto applicantDto){
        return adminService.registerEvaluator(applicantDto);
    }

    @GetMapping("/showAllEvaluator")
    public ResponseEntity<Object> showAllEvaluator(){
        return adminService.showAllEvaluators();
    }

    @GetMapping("/showEvaluatorById/{evaluatorId}")
    public ResponseEntity<Object>getEvaluatorByIds(@PathVariable Long evaluatorId)
    {
        return this.adminService.getEvaluatorById(evaluatorId);
    }


    //create circular
    @PostMapping("/circular/create")
    public ResponseEntity<Object> createCircular(@RequestBody CircularDto circularDto){
        return adminService.createCircular(circularDto);
    }

    //update circular
    @PutMapping("/circular/update")
    public ResponseEntity<Object> updateCircular(@RequestBody CircularDto circularDto){
        return adminService.updateCircular(circularDto);
    }

    @GetMapping("/circular/all")
    public ResponseEntity<List<Circular>>getCirculars()
    {
        return this.adminService.getCirculars();
    }

    //done
    @GetMapping("/circular/{circularId}")
    public ResponseEntity<Object>getCircularByIds(@PathVariable Long circularId)
    {
        return this.adminService.getCircularByIds(circularId);
    }


    //all applications
    @GetMapping("application/showAll")
    public ResponseEntity<Object> showAllApplications() {
        return adminService.showAllApplication();
    }

    //approve applications
    @PutMapping("/application/approve/{id}")
    public ResponseEntity<Object> createCircular(@PathVariable("id") Long applicationId){
        ResponseEntity<Object> updateApplicantStatus=adminService.approveApplicant(applicationId);
        return ResponseEntity.ok(updateApplicantStatus);
    }

    //show applicant
    @GetMapping("/applicant/showAll")
    public ResponseEntity<Object> showAll() {
        return adminService.showAll();
    }



    @GetMapping("/finalCandidates")
    public ResponseEntity<List<Marks>> showAllFinalCandidates() {
        return adminService.showAllFinalCandidate();
    }



    @GetMapping("/approvedApplications")
    public ResponseEntity<List<Application>> getApprovedApplications() {
        return adminService.getAllApprovedApplication();
    }

    //marks upload
    @PostMapping("/marks/upload")
    public ResponseEntity<Object> uploadMarks(@RequestBody MarksDto marksDto){
        return adminService.uploadMark(marksDto);
    }

    //marks update
    @PutMapping("/marks/update/{applicantId}")
    public ResponseEntity<Object> updateMarks(@PathVariable Long applicantId,@RequestBody MarksDto marksDto){
        return adminService.updateMark(applicantId,marksDto);
    }

    @GetMapping("/marks/{applicantId}")
    public ResponseEntity<Object> applicantMarksById(@PathVariable Long applicantId){
        return adminService.findApplicant(applicantId);
    }




    //view by circular
   @GetMapping("/application/{circularId}")
   public ResponseEntity<Object> viewAllApplicationsByCircular(@PathVariable Long circularId) {
       return adminService.viewAllApplicationsByCircular(circularId);
}


    @GetMapping("/admit-card/{applicantId}")
    public ResponseEntity<byte[]> getAdmitCard(@PathVariable Long applicantId) {
        return adminService.getAdmitCard(applicantId);
    }


    @PostMapping("/assign/evaluator")
    public ResponseEntity<Object> assignEvaluator(@RequestBody AssignEvaluatorDto assignEvaluatorDto){
        return adminService.assignEvaluator(assignEvaluatorDto);
    }

//    @PostMapping("/send-email")
//    public String sendEmail(@RequestBody Mail mail) {
//
//        return adminService.sendMail(mail);
//
//    }


}
