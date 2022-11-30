package org.fintech.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter, @Setter, @ToString, 
//@RequiredArgsConstructor, @EqualsAndHashCode
//기능들이 @Data 하나에 들어있음
@Data
//모든 필드값을 매개변수로하는 생성자를 자동 생성
@AllArgsConstructor
//기본 생성자를 자동 생성
@NoArgsConstructor
public class SampleVO {
	private Integer mno;
	private String firstName;
	private String lastName;
}
