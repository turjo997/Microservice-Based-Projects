package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "email_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long emailId;
    private String receiverName;
    private String emailAddress;

    @Column(length = 1000)
    private String emailBody;
    private Date emailTime;
}