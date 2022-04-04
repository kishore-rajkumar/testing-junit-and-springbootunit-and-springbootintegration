package com.kishore.springboot.it.mapper;

import org.mapstruct.Mapper;

import com.kishore.springboot.it.dto.StudentDTO;
import com.kishore.springboot.it.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface MapStructStudentMapper {
	
	public StudentDTO studentEntityToDTO(StudentEntity entity);

	public StudentEntity studentDTOToEntity(StudentDTO dto);

}
