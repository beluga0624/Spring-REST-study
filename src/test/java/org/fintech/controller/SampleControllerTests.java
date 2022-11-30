package org.fintech.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.fintech.domain.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)

//Controller 및 Web환경에 사용되는 Bean들을 자동으로 생성하여 등록시켜주는 어노테이션
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
						"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class SampleControllerTests {
	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;
	
	//톰갯 서버를 이용하지 않고 프로그램에서 url처리를 하는 가상의 서버 기법
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testConvert() throws Exception{
		Ticket ticket = new Ticket();
		ticket.setTno(123);
		ticket.setOwner("Admin");
		ticket.setGrade("AAA");
		
		//자바 겍체를 json 형태로 변환 처리
		String jsonStr = new Gson().toJson(ticket);
		
		log.info(jsonStr);
		
		mockMvc.perform(post("/sample/ticket")
			//클라이언트로 전달되는 데이터 타입 지정
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
			//예상값을 검증처리하는 메서드
			.andExpect(status().is(200));
	}
 	
	
}
