package com.ullash.springjpa.repository;

import com.ullash.springjpa.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course , Long> {
}
