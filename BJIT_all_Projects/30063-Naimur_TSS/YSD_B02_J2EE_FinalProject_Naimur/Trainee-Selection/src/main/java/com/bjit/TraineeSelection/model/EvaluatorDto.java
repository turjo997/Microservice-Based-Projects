package com.bjit.TraineeSelection.model;

import com.bjit.TraineeSelection.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorDto {

    private Long evaluatorId;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String password;
    private String contactNumber;
    private String specialization;
    private Role role;

}
