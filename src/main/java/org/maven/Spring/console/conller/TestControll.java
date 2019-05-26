package org.maven.Spring.console.conller;

import java.util.List;

import org.maven.Spring.console.entity.Person;
import org.maven.Spring.console.server.PeopleServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testBoot")
public class TestControll {

	@Autowired
    private PeopleServer peopleServer;
	
	@ResponseBody
	@RequestMapping(value= "/hello",method = RequestMethod.GET,produces="application/json;charset=utf-8")
	public List<Person> hello() {
		List<Person> person = peopleServer.selectByPrimaryKey();
		return person;
	}
	 
	@RequestMapping("/testBeforeService.do")
    public String testBeforeService(String key ,String value){
        return "key : "+key+ ", value : "+value;
        /*
        url:  http://localhost:8080/aop/testBeforeService.do?key=zdj&value=123
        我是前置通知
        代理的方法是 ： testBeforeService
        AOP 代理的名字 ： com.zdj.springboot_aop.Controller.AopTestController
        请求 ： org.apache.catalina.connector.RequestFacade@4f8cf74e ,  HttpSession : org.apache.catalina.session.StandardSessionFacade@7b37a638
        请求参数信息为 ： {"value":"123","key":"zdj"}
         */
    }

    @RequestMapping("/testAfterReturning1.do")
    public String testAfterReturning1(String key){
        return "key = "+key;
        /*
            url :  http://localhost:8080/aop/testAfterReturning1.do?key=zdj&value=123
            后置通知执行了！！
            第一个后置返回通知的返回值是 ：key = zdj
            第二个后置返回通知的返回值是 ：key = zdj
         */
    }

    @RequestMapping("/testAfterReturning2.do")
    public Integer testAfterReturning2(Integer key){
        return key;
        /*
            url :  http://localhost:8080/aop/testAfterReturning2.do?key=111222&value=123

            后置通知执行了！！
            第一个后置返回通知的返回值是 ：111222

            注 ： 因第二个后置通知首参不是JoinPoint,并且相应参数类型是String，而该目标方法的返回值类型是Integer，所以第二个后置通知方法不执行
         */
    }

    @RequestMapping("/testAfterThrowing.do")
    public  String testAfterThrowing(String key){
        throw new NullPointerException();
        /*
        url ： http://localhost:8080/aop/testAfterThrowing.do?key=zdk&value=123
        我是前置通知
        代理的方法是 ： testAfterThrowing
        AOP 代理的名字 ： com.zdj.springboot_aop.Controller.AopTestController
        请求 ： org.apache.catalina.connector.RequestFacade@41b8dcce ,  HttpSession : org.apache.catalina.session.StandardSessionFacade@33c33c37
        请求参数信息为 ： {"value":"123","key":"zdk"}
        testAfterThrowing
        发生了空指针异常
        */
    }

    @RequestMapping("/testAfter1.do")
    public String testAfter1(String key){
        throw new NullPointerException();
        /*
        url: http://localhost:8080/aop/testAfter1.do?key=zdj&value=123
        后置最终通知执行了！
         */
    }

    @RequestMapping("/testAfter2.do")
    public String testAfter2(String key){
        return key;
        /*
        url: http://localhost:8080/aop/testAfter2.do?key=zdj&value=123
        后置最终通知执行了！
         */
    }

    @RequestMapping("/testAroundService.do")
    public String testAroundService(String key){
        return "环绕通知 ： " + key;
        /*
        url : http://localhost:8080/aop/testAroundService.do?key=1122
        环绕通知的目标方法名为 ： testAroundService

        当访问 http://localhost:8080/aop/testAfter2.do?key=1122&value=sjshhjdh，不符合环绕通知的切入规则，所以环绕通知不会执行；
         */
    }

}
