package org.maven.Spring.console.entity;

//@Component
//@ConfigurationProperties(prefix = "person")
public class Person {

	private Integer id;
	private Integer age;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	private String name;
//	@Override
//	public String toString() {
//		return "Person [name=" + name + ", map=" + map + ", lists=" + lists + "]";
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
