package com.kishore.springboot.it.dto;


import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class StudentDTO {

	private Long id;

	@NotNull
	private String name;
	@NotNull
	private String rollNo;

}
