package com.bjit.TraineeSelection.repository;

import com.bjit.TraineeSelection.entity.EvaluatorEntity;
import com.bjit.TraineeSelection.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
}
