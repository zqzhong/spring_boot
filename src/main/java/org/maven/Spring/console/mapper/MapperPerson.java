package org.maven.Spring.console.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.maven.Spring.console.entity.Person;
import org.springframework.stereotype.Repository;


@Mapper
public interface MapperPerson {
	
	 List<Person> selectByPrimaryKey();
			 
}
