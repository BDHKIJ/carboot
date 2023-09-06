package com.packt.cardatebase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Car {
	//기본키는 @Id어노테이션으로 지정 
	//Identity
	@Id
	//기본키 생성 전략 id값을 자동으로 생성되도록 지정 GeneratedValue
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String brand, model, color, registerNumber;
	@Column(name="`year`")
	private int year;
	private int price;
	//FetcyType은 데이터베이스에서 데이터를 검색하는전략을 정의함
	//즉시검색 : EAGER, 지연검색 : LAZY --> 지연검색은
	//데이터 베이스에서 해당 컬럼을 검색하면 필요할 때에 해당 컬럼과
	//연결된 값을 검색함.
	//대다관계어서는 LAZY가 기본값임 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn()
	private Owner owner;
	//열의 이름 , 열의 길이, null허용 여부 
	//@Column(name="exdesc" , nullable=false, length=512)
	//private String desc;
	//생성자
	public Car() {}
	public Car(String brand, String model, String color, 
			String registerNumber, int year, int price, Owner owner) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.year = year;
		this.price = price;
		this.owner = owner;
	}
	
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	//getter, setter
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
