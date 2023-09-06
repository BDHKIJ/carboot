package com.packt.cardatebase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.packt.cardatebase.domain.Car;
import com.packt.cardatebase.domain.CarRepository;
import com.packt.cardatebase.domain.Owner;
import com.packt.cardatebase.domain.OwnerRepository;
import com.packt.cardatebase.domain.User;
import com.packt.cardatebase.domain.UserRepository;


@SpringBootApplication
//@EnaleAutoConfiguration 스프링 부트 자동 구성을 활성화 
//@ComponentScan 스프링 부트 컴포넌트 검색으로 애플리케이션의 모든 컴포넌트를 찾는다
//@Conficuration 빈정의의 원본으로 쓸수 있는 클래스를 정의한다. 
//합친게 @SpringBootApplication
public class CardatebaseApplication implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(CardatebaseApplication.class);
	
	@Autowired
	private CarRepository repository;
	
	//소유자 리포지터리 의존주입
	@Autowired
	private OwnerRepository orepository;
	
	@Autowired
	private UserRepository urepoistory;
	
	private BCryptPasswordEncoder pwencoder = 
			new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		//이 주석을 추가하면 애플리케이션이 재시작됨
		SpringApplication.run(CardatebaseApplication.class, args);
		logger.info("Application started");
	}
	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner("그린","김");
		Owner owner2 = new Owner("길동","홍");
		Owner owner3 = new Owner("지아","박");
		//insert saveAll : 여러데이터 저장
		orepository.saveAll(Arrays.asList(owner1, owner2,owner3));
		
		repository.save(new Car("Ford","Mustang","red","ADF-1212", 2021, 59000, owner1));
		repository.save(new Car("Nissan","Leaf","white","FAA-2012", 2020, 19000, owner2));
		repository.save(new Car("Toyota","Prius","black","DFA-1229", 2022, 39000, owner3));
		repository.save(new Car("현대","소나타","black","DFA-1229", 2022, 39000, owner3));
		repository.save(new Car("현대","그랜져","black","DFA-1229", 2022, 39000, owner3));
		
		for(Car car: repository.findByBrand("현대")) {
			logger.info(car.getBrand() + " " + car.getModel());
		}
		//사용자이름 user 암호 user
		urepoistory.save(new User("user", pwencoder.encode("user"),"USER"));
		urepoistory.save(new User("admin", pwencoder.encode("admin"),"ADMIN"));
	}
	
}
