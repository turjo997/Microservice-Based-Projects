-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 23, 2023 at 04:49 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tss_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `candidate_marks`
--

CREATE TABLE `candidate_marks` (
  `candidate_id` bigint(20) NOT NULL,
  `full_mark` float DEFAULT NULL,
  `aptitude_test_id` bigint(20) DEFAULT NULL,
  `examinee_id` bigint(20) DEFAULT NULL,
  `hr_viva_id` bigint(20) DEFAULT NULL,
  `technical_viva_id` bigint(20) DEFAULT NULL,
  `written_mark_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `candidate_marks`
--

INSERT INTO `candidate_marks` (`candidate_id`, `full_mark`, `aptitude_test_id`, `examinee_id`, `hr_viva_id`, `technical_viva_id`, `written_mark_id`) VALUES
(2, NULL, 4, 8, 5, 6, 2),
(3, NULL, 7, 11, 8, 9, 3),
(4, NULL, 10, 12, 11, 12, 4);

-- --------------------------------------------------------

--
-- Table structure for table `course_info`
--

CREATE TABLE `course_info` (
  `course_id` bigint(20) NOT NULL,
  `applicant_dashboard_message` varchar(255) DEFAULT NULL,
  `aptitude_test_passed_dashboard_message` varchar(255) DEFAULT NULL,
  `technical_viva_passed_dashboard_message` varchar(255) DEFAULT NULL,
  `trainee_dashboard_message` varchar(255) DEFAULT NULL,
  `written_passed_dashboard_message` varchar(255) DEFAULT NULL,
  `written_shortlisted_dashboard_message` varchar(255) DEFAULT NULL,
  `application_deadline` datetime DEFAULT NULL,
  `batch_code` varchar(255) DEFAULT NULL,
  `course_description` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `written_exam_time` datetime DEFAULT NULL,
  `hr_viva_passed_dashboard_message` varchar(255) DEFAULT NULL,
  `vacancy` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_info`
--

INSERT INTO `course_info` (`course_id`, `applicant_dashboard_message`, `aptitude_test_passed_dashboard_message`, `technical_viva_passed_dashboard_message`, `trainee_dashboard_message`, `written_passed_dashboard_message`, `written_shortlisted_dashboard_message`, `application_deadline`, `batch_code`, `course_description`, `course_name`, `end_date`, `is_available`, `start_date`, `written_exam_time`, `hr_viva_passed_dashboard_message`, `vacancy`) VALUES
(6, 'You have successfully applied for this course J2EE', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.3', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_J2EE', 'This course covers the fundamentals of computer science.', 'J2EE', '2023-12-15 06:00:00', b'1', '2023-09-15 06:00:00', '2023-09-11 00:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(7, 'You have successfully applied for this course J2EE', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_DevOps', 'This course covers the fundamentals of computer science.', 'Dev Ops', '2023-12-15 06:00:00', b'1', '2023-09-15 06:00:00', '2023-09-10 12:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(8, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_SQA', 'This course covers the fundamentals of computer science.', 'SQA', '2023-12-15 06:00:00', b'1', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(9, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_ANDROID', 'This course covers the fundamentals of computer science.', 'Android', '2023-12-15 06:00:00', b'0', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(10, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_ML', 'This course covers the fundamentals of computer science.', 'ML', '2023-12-15 06:00:00', b'0', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(12, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_DL', 'This course covers the fundamentals of computer science.', 'DL', '2023-12-15 06:00:00', b'0', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(13, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_DL2', 'This course covers the fundamentals of computer science.', 'DL', '2023-12-15 06:00:00', b'0', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', NULL),
(14, 'You have successfully applied for this course J2EE', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.3', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_DSA', 'This course covers the fundamentals of computer science.', 'DSA', '2023-12-15 06:00:00', b'1', '2023-09-15 06:00:00', '2023-09-10 12:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', NULL),
(15, 'You have successfully applied for this course', 'Congratulations. You have passed the aptitude test. Your technical viva will held soon.', 'Congratulations. You have passed the technical viva. Your HR viva will held soon', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the written exam. Your aptitude test will held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will held soon', '2023-07-26 06:00:00', 'YSD_03_DataArc', 'Intermediate', 'Data Architecture', '2023-07-19 06:00:00', b'1', '2023-07-05 06:00:00', '2023-07-13 17:09:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 19),
(16, 'You have successfully applied for this course J2EE', 'Congratulations. You have passed aptitude test. Your technical viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have passed technical viva. Your HR viva will be held at 02.00 pm 5th July 2023', 'Congratulations. You have been selected as Trainee.', 'Congratulations. You have passed written exam. Your aptitude test will be held at 02.00 pm 5th July 2023', 'Congratulations. You have shortlisted for written exam. Your written exam will be held at 02.00 pm 1st July 2023', '2023-08-31 06:00:00', 'YSD_02_DB', 'This course covers the fundamentals of computer science.', 'Database', '2023-12-15 06:00:00', b'1', '2023-09-15 06:00:00', '2023-09-10 06:00:00', 'Congratulations. You have passed HR viva . Please wait for final selection', 20),
(17, 'You have successfully applied for this course', 'Congratulations. You have passed the aptitude test. Your technical viva will held soon.', 'Congratulations. You have passed the technical viva. Your HR viva will held soon', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the written exam. Your aptitude test will held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will held soon', '2023-07-26 06:00:00', 'fdfdf', 'fdfd', 'New', '2023-07-26 06:00:00', b'1', '2023-07-19 06:00:00', '2023-07-22 17:45:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 22),
(18, 'You have successfully applied for this course', 'Congratulations. You have passed the aptitude test. Your technical viva will held soon.', 'Congratulations. You have passed the technical viva. Your HR viva will held soon', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the written exam. Your aptitude test will held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will held soon', '2023-07-11 06:00:00', 'dsdsds', 'fd', 'Deep Learning', '2023-07-10 06:00:00', b'1', '2023-07-24 06:00:00', '2023-07-18 17:56:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 23),
(19, 'You have successfully applied for this course', 'Congratulations. You have passed the aptitude test. Your technical viva will held soon.', 'Congratulations. You have passed the technical viva. Your HR viva will held soon', 'dsds', 'Congratulations. You have passed the written exam. Your aptitude test will held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will held soon', '2023-07-17 06:00:00', 'dfsddsf', 'fsdf', 'dfd', '2023-06-26 06:00:00', b'1', '2023-07-12 06:00:00', '2023-07-11 17:59:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 23),
(20, 'You have successfully applied for this course', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the technical viva. Your HR viva will held soon', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the written exam. Your aptitude test will held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will held soon', '2023-07-10 06:00:00', '43', 'fdf', 'dsd5', '2023-07-17 06:00:00', b'1', '2023-07-17 06:00:00', '2023-07-18 12:05:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 43),
(21, 'You have successfully applied for this course', 'Congratulations. You have passed the aptitude test. Your technical viva will be held soon.', 'Congratulations. You have passed the technical viva. Your HR viva will be held soon', 'Congratulations. You have been selected as a Trainee.', 'Congratulations. You have passed the written exam. Your aptitude test will be held soon', 'Congratulations. You have shortlisted for the written exam. Your written exam will be held soon', '2023-07-28 06:00:00', 'fd', 'fdf', '43', '2023-07-18 06:00:00', b'0', '2023-07-09 06:00:00', '2023-07-25 18:05:00', 'Congratulations. You have passed the HR viva. Please wait for the final selection', 43);

-- --------------------------------------------------------

--
-- Table structure for table `data_storage`
--

CREATE TABLE `data_storage` (
  `data_id` bigint(20) NOT NULL,
  `data_key` varchar(255) DEFAULT NULL,
  `data_value` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `data_storage`
--

INSERT INTO `data_storage` (`data_id`, `data_key`, `data_value`) VALUES
(1, 'TotalMarkWritten', '10'),
(2, 'WrittenQuestionNumber', '5'),
(3, 'PassingMarkWritten', '5'),
(4, 'AptitudeQuestionNumber', '5'),
(5, 'TotalMarkAptitude', '10'),
(6, 'PassingMarkAptitude', '5'),
(7, 'TechnicalQuestionNumber', '5'),
(8, 'TotalMarkTechnical', '10'),
(9, 'PassingMarkTechnical', '5'),
(10, 'HrVivaQuestionNumber', '3'),
(11, 'TotalMarkHrViva', '10'),
(12, 'PassingMarkHrViva', '5');

-- --------------------------------------------------------

--
-- Table structure for table `email_info`
--

CREATE TABLE `email_info` (
  `email_id` bigint(20) NOT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `email_body` varchar(1000) DEFAULT NULL,
  `email_time` datetime DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `evaluator_info`
--

CREATE TABLE `evaluator_info` (
  `evaluator_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `evaluator_info`
--

INSERT INTO `evaluator_info` (`evaluator_id`, `email`, `name`) VALUES
(1, 'rahatibnahossain@gmail.com', 'Rahat'),
(2, 'rrr@gmail.com', 'RRR'),
(3, 'sss@gmail.com', 'SSS'),
(4, 'ttt@gmail.com', 'TTT'),
(5, 'AAA@gm.co', 'rrr'),
(6, 'YYY@gm.co', 'yyy'),
(7, 'AAA@gm.com', 'AAA'),
(8, 'ra@gm.co', 'ra');

-- --------------------------------------------------------

--
-- Table structure for table `examinee_info`
--

CREATE TABLE `examinee_info` (
  `examinee_id` bigint(20) NOT NULL,
  `application_time` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `examinee_info`
--

INSERT INTO `examinee_info` (`examinee_id`, `application_time`, `role`, `course_id`, `user_id`) VALUES
(8, '2023-07-21 12:15:03', 'CANDIDATE', 6, 10),
(9, '2023-07-21 14:14:54', 'APPLICANT', 7, 10),
(10, '2023-07-21 15:06:53', 'APPLICANT', 8, 10),
(11, '2023-07-21 16:03:06', 'CANDIDATE', 14, 10),
(12, '2023-07-23 14:04:37', 'CANDIDATE', 6, 11),
(13, '2023-07-23 14:04:45', 'APPLICANT', 7, 11),
(14, '2023-07-23 14:04:52', 'APPLICANT', 8, 11),
(15, '2023-07-23 14:05:03', 'APPLICANT', 18, 11),
(16, '2023-07-23 14:15:43', 'APPLICANT', 14, 11),
(17, '2023-07-23 14:41:16', 'APPLICANT', 17, 11);

-- --------------------------------------------------------

--
-- Table structure for table `hidden_code_info`
--

CREATE TABLE `hidden_code_info` (
  `hidden_code` bigint(20) NOT NULL,
  `candidate_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hidden_code_info`
--

INSERT INTO `hidden_code_info` (`hidden_code`, `candidate_id`) VALUES
(17333, 4);

-- --------------------------------------------------------

--
-- Table structure for table `login_info`
--

CREATE TABLE `login_info` (
  `login_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `evaluator_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_info`
--

INSERT INTO `login_info` (`login_id`, `email`, `password`, `role`, `evaluator_id`, `user_id`) VALUES
(1, 'rahatibnhossain@gmail.com', '$2a$10$aXOzi2Db8nFdr4BwbNc9d.YlyfJM0iUasAhZ/Cm3jYOdYXAAKL76i', 'ADMIN', NULL, NULL),
(3, 'rahatibnahossain@gmail.com', '$2a$10$cO/JRW/MRq3Nq8PmYyuK8.X3RCf57ntwtlhxabtg0jYIBnSoCCNIy', 'EVALUATOR', 1, NULL),
(9, 'rahat.hossain@bjitacademy.com', '$2a$10$7zbSmAeCDpGt1MX8AEA.N.FVoxsbz75MIJx23a8092KP6vZs3HV/K', 'USER', NULL, 7),
(10, 'mdrahatibnahofdfssain@gmail.com', '$2a$10$Mgz6ovZLuu9UFPhDznF9mut9u8lYKUcJPhHkeSC160W4nXIizlk3m', 'APPLICANT', NULL, 8),
(12, 'taslim.cse06@gmail.com', '$2a$10$8oM7mduG661JyhUxuSYuiOPJ4TBmfkIObkPdNTzI2fmEJU4Ryv2im', 'APPLICANT', NULL, 10),
(13, 'mdrahatibnahossain@gmail.com', '$2a$10$7GjbLVN3o/3LHFlNTwXOUeaX4huEkGZUL6vY1Lg5eq8dlIDz0QKNa', 'ADMIN', NULL, NULL),
(14, 'rrr@gmail.com', '$2a$10$jivp8DxqBv472Bu/meP5t.mL2uPdvuwFcJFXc2WsWAFkmsPzxx8YS', 'EVALUATOR', 2, NULL),
(15, 'sss@gmail.com', '$2a$10$DzUz5xd7PZYU4dQRVudOPecLUKjIqBGVRIDVsSluR5k4GeCIwZ0Ne', 'EVALUATOR', 3, NULL),
(16, 'ttt@gmail.com', '$2a$10$DJwGEaQcpgMiKD3SGwGDAeFBYJSsXvNGqcsCeIHEMFOb21Jsqg1ly', 'EVALUATOR', 4, NULL),
(17, 'AAA@gm.co', '$2a$10$PvFTq6icWpD6FwzZEtTyt.VUUvd3xKyYgBbiFbAo1Wh6pLCweBgTa', 'EVALUATOR', 5, NULL),
(18, 'YYY@gm.co', '$2a$10$eL14Em/iuluAoQAo6A80d.6YoX./qFvBK9fmH7GURC6vSQc9F7a82', 'EVALUATOR', 6, NULL),
(19, 'AAA@gm.com', '$2a$10$dL6z3NTYYpJmcR1i11NOLObuUv.L4jzSDIuWaOWSATcbZ6YRke/96', 'EVALUATOR', 7, NULL),
(20, 'rahatibnhossain@gmail.comfdfdf', '$2a$10$cod5SnbfNIR6erXkOD6yEeACqJqGehztJDpzVcvXqxhw4IYavI0ze', 'APPLICANT', NULL, 11),
(21, 'ra@gm.co', '$2a$10$Ed1qpO0IhwISMhCxZwxapuhhedry4e0Yio4Y5P7CTWmDTWrcjSVpy', 'EVALUATOR', 8, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `my_sequence`
--

CREATE TABLE `my_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `my_sequence`
--

INSERT INTO `my_sequence` (`next_val`) VALUES
(17432);

-- --------------------------------------------------------

--
-- Table structure for table `question_marks`
--

CREATE TABLE `question_marks` (
  `question_id` bigint(20) NOT NULL,
  `question_no` int(11) DEFAULT NULL,
  `written_question_mark` float DEFAULT NULL,
  `round_id` bigint(20) DEFAULT NULL,
  `question_mark` float DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `round_marks`
--

CREATE TABLE `round_marks` (
  `round_id` bigint(20) NOT NULL,
  `passed` bit(1) DEFAULT NULL,
  `round_mark` float DEFAULT NULL,
  `round_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `round_marks`
--

INSERT INTO `round_marks` (`round_id`, `passed`, `round_mark`, `round_name`) VALUES
(7, NULL, NULL, NULL),
(6, NULL, NULL, NULL),
(5, NULL, NULL, NULL),
(4, NULL, NULL, NULL),
(8, NULL, NULL, NULL),
(9, NULL, NULL, NULL),
(10, NULL, NULL, NULL),
(11, NULL, NULL, NULL),
(12, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `user_id` bigint(20) NOT NULL,
  `cgpa` float DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `degree_name` varchar(255) DEFAULT NULL,
  `educational_institute` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `passing_year` int(11) DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `present_address` varchar(255) DEFAULT NULL,
  `resume_url` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_id`, `cgpa`, `contact_number`, `date_of_birth`, `degree_name`, `educational_institute`, `email`, `first_name`, `gender`, `last_name`, `passing_year`, `photo_url`, `present_address`, `resume_url`) VALUES
(11, 3.33, '1323', '2023-07-07 06:00:00', 'BSc', 'Jashore Universty of Science and Technology', 'rahatibnhossain@gmail.comfdfdf', 'Raaht', 'male', 'fdfd', 2016, NULL, 'ds', 'C:\\Users\\Test\\Documents\\finalproject\\BJIT-TSS\\back-end\\tss\\src\\main\\resources\\static\\resume\\11.pdf'),
(10, 3.61, '01580661685', NULL, 'BSc', 'Jashore Universty of Science and Technology', 'taslim.cse06@gmail.com', 'Taslim', 'male', 'Shanto', 2023, NULL, 'Dhaka', 'C:\\Users\\Test\\Documents\\finalproject\\BJIT-TSS\\back-end\\tss\\src\\main\\resources\\static\\resume\\10.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `validation_codes`
--

CREATE TABLE `validation_codes` (
  `validation_id` bigint(20) NOT NULL,
  `validation_code` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `validation_codes`
--

INSERT INTO `validation_codes` (`validation_id`, `validation_code`, `user_id`) VALUES
(1, '77323', 1),
(7, '88194', 7),
(10, '41557', 10),
(11, '01574', 11);

-- --------------------------------------------------------

--
-- Table structure for table `written_marks`
--

CREATE TABLE `written_marks` (
  `written_mark_id` bigint(20) NOT NULL,
  `passed` bit(1) DEFAULT NULL,
  `written_mark` float DEFAULT NULL,
  `evaluator_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `written_marks`
--

INSERT INTO `written_marks` (`written_mark_id`, `passed`, `written_mark`, `evaluator_id`) VALUES
(2, NULL, NULL, NULL),
(3, NULL, NULL, NULL),
(4, b'1', 9, 5);

-- --------------------------------------------------------

--
-- Table structure for table `written_question_marks`
--

CREATE TABLE `written_question_marks` (
  `written_question_id` bigint(20) NOT NULL,
  `question_no` int(11) DEFAULT NULL,
  `written_question_mark` float DEFAULT NULL,
  `written_mark_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `written_question_marks`
--

INSERT INTO `written_question_marks` (`written_question_id`, `question_no`, `written_question_mark`, `written_mark_id`) VALUES
(6, 1, 2.4, NULL),
(7, 2, 1, NULL),
(8, 3, 2, NULL),
(9, 4, 1, NULL),
(10, 5, 2, NULL),
(11, 1, 1, 4),
(12, 2, 2, 4),
(13, 3, 6, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `candidate_marks`
--
ALTER TABLE `candidate_marks`
  ADD PRIMARY KEY (`candidate_id`),
  ADD KEY `FKi9y2ydv46etp31n1bpjvjg9xm` (`aptitude_test_id`),
  ADD KEY `FKrymp7387qxo4njcyakcsywe1b` (`examinee_id`),
  ADD KEY `FKao0vvsp4u27b5fco6hft1tu2e` (`hr_viva_id`),
  ADD KEY `FKhqvpgsqv8979hgjkwkw2onk3g` (`technical_viva_id`),
  ADD KEY `FKau3j9h9nrncdnkpoaoa3i90rg` (`written_mark_id`);

--
-- Indexes for table `course_info`
--
ALTER TABLE `course_info`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `data_storage`
--
ALTER TABLE `data_storage`
  ADD PRIMARY KEY (`data_id`);

--
-- Indexes for table `email_info`
--
ALTER TABLE `email_info`
  ADD PRIMARY KEY (`email_id`);

--
-- Indexes for table `evaluator_info`
--
ALTER TABLE `evaluator_info`
  ADD PRIMARY KEY (`evaluator_id`);

--
-- Indexes for table `examinee_info`
--
ALTER TABLE `examinee_info`
  ADD PRIMARY KEY (`examinee_id`),
  ADD KEY `FK5tjioowuk22nqpc48i9er25ej` (`course_id`),
  ADD KEY `FKmgcnn67w9rsnyx7rglb60vrrd` (`user_id`);

--
-- Indexes for table `hidden_code_info`
--
ALTER TABLE `hidden_code_info`
  ADD PRIMARY KEY (`hidden_code`),
  ADD KEY `FKj5vo58mmin376tjpks8wa16i4` (`candidate_id`);

--
-- Indexes for table `login_info`
--
ALTER TABLE `login_info`
  ADD PRIMARY KEY (`login_id`),
  ADD KEY `FKqhrk58711eh4hkh8thqpjxmsh` (`evaluator_id`),
  ADD KEY `FK4g50iu9l6qjo5irsq5wgf7kue` (`user_id`);

--
-- Indexes for table `question_marks`
--
ALTER TABLE `question_marks`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `FK849smvkg7wmhev5rfic7ukgmq` (`round_id`);

--
-- Indexes for table `round_marks`
--
ALTER TABLE `round_marks`
  ADD PRIMARY KEY (`round_id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `validation_codes`
--
ALTER TABLE `validation_codes`
  ADD PRIMARY KEY (`validation_id`),
  ADD KEY `FKg04aua1364wag4jp77dj41u9m` (`user_id`);

--
-- Indexes for table `written_marks`
--
ALTER TABLE `written_marks`
  ADD PRIMARY KEY (`written_mark_id`),
  ADD KEY `FKtfnjysiei6vtgqeptrwrog3w` (`evaluator_id`);

--
-- Indexes for table `written_question_marks`
--
ALTER TABLE `written_question_marks`
  ADD PRIMARY KEY (`written_question_id`),
  ADD KEY `FKb1qhe6onqsx8k29phesh93gkb` (`written_mark_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `candidate_marks`
--
ALTER TABLE `candidate_marks`
  MODIFY `candidate_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `course_info`
--
ALTER TABLE `course_info`
  MODIFY `course_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `data_storage`
--
ALTER TABLE `data_storage`
  MODIFY `data_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `email_info`
--
ALTER TABLE `email_info`
  MODIFY `email_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `evaluator_info`
--
ALTER TABLE `evaluator_info`
  MODIFY `evaluator_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `examinee_info`
--
ALTER TABLE `examinee_info`
  MODIFY `examinee_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `login_info`
--
ALTER TABLE `login_info`
  MODIFY `login_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `question_marks`
--
ALTER TABLE `question_marks`
  MODIFY `question_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `round_marks`
--
ALTER TABLE `round_marks`
  MODIFY `round_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user_info`
--
ALTER TABLE `user_info`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `validation_codes`
--
ALTER TABLE `validation_codes`
  MODIFY `validation_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `written_marks`
--
ALTER TABLE `written_marks`
  MODIFY `written_mark_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `written_question_marks`
--
ALTER TABLE `written_question_marks`
  MODIFY `written_question_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
