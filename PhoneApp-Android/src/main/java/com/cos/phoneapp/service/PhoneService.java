package com.cos.phoneapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.phoneapp.domain.Phone;
import com.cos.phoneapp.domain.PhoneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PhoneService {

	private final PhoneRepository phoneRepository;
	
	public List<Phone> 전체보기() {
		
		return phoneRepository.findAll();

	}

	public Phone 상세보기(Long id) {

		return phoneRepository.findById(id).get();
	}

	@Transactional // 스프링 꺼
	public void 삭제하기(Long id) {

		phoneRepository.deleteById(id);
	}

	@Transactional // 스프링 꺼
	public Phone 수정하기(Long id, Phone phone) {

		// 1. 영속화 - 영속화 컨텍스트에 넣고, 
		Phone phoneEntity = phoneRepository.findById(id).get();
		
		// 2. 영속화 된 애들 수정하기
		phoneEntity.setName(phone.getName());
		phoneEntity.setTel(phone.getTel());
		
		return phoneEntity;
		
		
	} // 서비스 종료시에 영속성 컨텍스트가 변경을 감지해서 flush()로 db로 반영함

	@Transactional // 스프링 꺼
	public Phone 저장하기(Phone phone) {

		return phoneRepository.save(phone);
	}
}
