package com.cos.phoneapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // javax 꺼임
public class Phone {

	@Id //javax꺼임
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략 3가지 Table, auto_increment, Sequence / 기본 전략을 따르겠다
	private Long id;
	
	private String name;
	private String tel;
	
	
}
