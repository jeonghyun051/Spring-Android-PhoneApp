package com.cos.phoneapp.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.phoneapp.domain.Phone;
import com.cos.phoneapp.service.PhoneService;
import com.cos.phoneapp.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PhoneController {
	
	private final PhoneService phoneService;

	@GetMapping("/phone") // 전체보기
	public CMRespDto<?> findAll() { // 물음표는 정해지지 않았다, 리턴할때 정하겠다.
		System.out.println("find 함수 실행");
		return new CMRespDto<>(1,phoneService.전체보기()); 
		
	}
	
	@GetMapping("/phone/{id}") // 한건찾기
	public CMRespDto<?> findById(@PathVariable Long id) { // 물음표는 정해지지 않았다, 리턴할때 정하겠다.
		
		return new CMRespDto<>(1,phoneService.상세보기(id)); 
		
	}
	
	@DeleteMapping("/phone/{id}") // 삭제
	public CMRespDto<?> delete(@PathVariable Long id) { // 물음표는 정해지지 않았다, 리턴할때 정하겠다.
		System.out.println("delete 삭제 함수 실행");
	
		phoneService.삭제하기(id);

		return new CMRespDto<>(1,null); 
		
	}

	@PutMapping("/phone/{id}") // 수정
	public CMRespDto<?> update(@PathVariable Long id, @RequestBody Phone phone) { // 물음표는 정해지지 않았다, 리턴할때 정하겠다.
		System.out.println("put 수정 함수 실행");
		System.out.println("수정에서 받은값 : " + id + phone);
		return new CMRespDto<>(1,phoneService.수정하기(id, phone)); 
		
	}
	
	@PostMapping("/phone") // 저장
	public CMRespDto<?> save(@RequestBody Phone phone) { // 물음표는 정해지지 않았다, 리턴할때 정하겠다.
		System.out.println("save 저장 함수 실행");
		return new CMRespDto<>(1,phoneService.저장하기(phone)); 
		
	}
}