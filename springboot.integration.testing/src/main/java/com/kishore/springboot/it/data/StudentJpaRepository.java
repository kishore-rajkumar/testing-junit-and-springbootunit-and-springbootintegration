package com.kishore.springboot.it.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kishore.springboot.it.entity.StudentEntity;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {

}
