package org.maven.Spring.console.server;

import java.util.List;

import org.maven.Spring.console.entity.Person;
import org.maven.Spring.console.mapper.MapperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PeopleServer {
	
	@Autowired
	MapperPerson mapperPerson;
	
	public List<Person> selectByPrimaryKey() {
		return mapperPerson.selectByPrimaryKey();
	}
}
