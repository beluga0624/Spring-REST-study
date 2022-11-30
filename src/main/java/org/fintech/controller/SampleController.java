package org.fintech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.fintech.domain.SampleVO;
import org.fintech.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController
//http://localhost:8080/sample
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	//http://localhost:8080/sample/getText
	@GetMapping(value = "/getText", 
				produces = "text/plain; charset=UTF-8")
	//서버 입장에서 반환하는 데이터 타입을 지정
	// produces(서버에서 처리 후 지정된 마임으로 응답) <-> consumes(클라이언트에서 지정된 마임으로 오는것을 받아들임) 
	
	public String getTest() {
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getSample", 
				produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, 
							MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	}
	
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(113, "로켓", "라군");
	}
	
	@GetMapping(value = "/getList")
	public List<SampleVO> getList() {
		//Stream(선언부) + 중간연산(sort, filter etc...) + 최종연산(count, sum, avg 등의 통계함수)
		return IntStream.range(1, 10)
				.mapToObj(i -> new SampleVO(i, i + "First", i + "Last"))
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		
		return map;
	}
	
	@GetMapping(value = "/check", params = {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		//응답처리시 클라이언트에게 데이터와 HTTP 상태코드값을 함께 전송
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			//BAD_GATEWAY(502) : 서버로부터 유효하지 않은 응답을 받았다는 상태코드
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		return result;
	}
	
	@GetMapping("/product/{cat}/{pid}")
	//PathVariable : REST 방식에서 url에 있는 매개변수 값을 가져오는 어노테이션 
	public String[] getPath(@PathVariable("cat") String cat, 
							@PathVariable("pid") Integer pid) {
		return new String[] {"category: " + cat, "productid: " + pid};
	}
	
	@PostMapping("/ticket")
	//@RequestBody : Controller로 전송된 JSON 정보가 자동적으로 MAP 형태로 변환되어 해당 변수에 대입된다.
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert..........ticket " + ticket);
		return ticket;
	}
}
