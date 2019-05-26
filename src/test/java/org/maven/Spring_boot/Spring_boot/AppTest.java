package org.maven.Spring_boot.Spring_boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.maven.Spring.console.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	
	@Autowired
	Person person;
	
	@Test
    public void testOne(){
        System.out.println(person);
    }
}
