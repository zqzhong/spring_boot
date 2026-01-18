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

    @Autowired
    RedisService redisService;
	
	public List<Person> selectByPrimaryKey() {

        redisService.set("test:key1", "Hello Redis Cluster");
        String value = (String) redisService.get("test:key1");
        System.out.println("Get value: " + value);

		return mapperPerson.selectByPrimaryKey();
	}
}
