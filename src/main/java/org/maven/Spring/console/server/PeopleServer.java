package org.maven.Spring.console.server;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.maven.Spring.console.entity.Person;
import org.maven.Spring.console.mapper.MapperPerson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class PeopleServer {
	
	@Autowired
	MapperPerson mapperPerson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final String STOCK_KEY = "product:stock:";

	public List<Person> selectByPrimaryKey() {


        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        );

        // 找出年龄 > 25 的人，按名字排序，提取名字列表
        List<String> result = people.stream()
                .filter(p -> p.getAge() > 25)
                .sorted(Comparator.comparing(Person::getName))
                .map(Person::getName)
                .collect(Collectors.toList());


        // 并行流处理（自动多线程）
        long count = people.parallelStream()
                .filter(p -> p.getAge() > 25)
                .count();

//        redisService.set("test:key1", "Hello Redis Cluster");
//        String value = (String) redisService.get("test:key1");
//        System.out.println("Get value: " + value);

		return people;
	}

    public void deductStock(String productId){
        // 1. 创建一个锁对象，锁的key建议带上业务标识
        String lockKey = "lock:product:stock:" + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 2. 尝试加锁
            // 参数含义：最大等待时间，锁自动释放时间，时间单位
            // 这里设置等待100秒，锁10秒后自动释放（如果没被看门狗续期的话）
            // 如果使用 lock.lock()，则会依赖看门狗机制自动续期
            boolean isLocked = lock.tryLock(100, TimeUnit.SECONDS);
            if (isLocked) {
                // 3. 加锁成功，执行核心业务逻辑
                System.out.println(Thread.currentThread().getName() + " 获取锁成功，扣减库存...");

                // 模拟业务处理，如查询库存、扣减库存
                int stock = getStock(productId);
                System.out.println(Thread.currentThread().getName() + " 执行扣减库存操作，当前库存：" + stock);
                if (stock > 0) { deductStock(productId, 1); }

            } else {
                // 获取锁失败的处理，比如重试或返回错误
                System.out.println(Thread.currentThread().getName() + " 获取锁失败，请稍后重试");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 4. 释放锁
            // 一定要在 finally 块中释放锁，避免死锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " 释放锁");
            }
        }
    }

    /**
     * 获取当前库存
     */
    public Integer getStock(String productId) {
        String value = stringRedisTemplate.opsForValue().get(STOCK_KEY + productId);
        return value == null ? 0 : Integer.parseInt(value);
    }

    /**
     * 扣减库存（仅做数据操作，不加锁）
     */
    public void deductStock(String productId, int amount) {
        String key = STOCK_KEY + productId;
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new RuntimeException("库存不存在");
        }
        int currentStock = Integer.parseInt(value);
        if (currentStock < amount) {
            throw new RuntimeException("库存不足");
        }
        stringRedisTemplate.opsForValue().set(key, String.valueOf(currentStock - amount));
    }


}
