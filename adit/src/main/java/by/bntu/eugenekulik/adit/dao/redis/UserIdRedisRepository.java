package by.bntu.eugenekulik.adit.dao.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@Slf4j
public class UserIdRedisRepository {

  private final RedisTemplate<String,Object> redisTemplate;

  public UserIdRedisRepository(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public String save(Long userId, String token){
    redisTemplate.opsForValue().set(token,userId.toString());
    redisTemplate.expire(token, Duration.ofMinutes(30));
    return token;
  }

  public void deleteToken(String token) {
    redisTemplate.delete(token);
  }


  public Long getUserIdByToken(String token){
    Long value = null;
    try {
      value = Long.parseLong((String) redisTemplate.opsForValue().get(token));
    } catch (NumberFormatException e){
      log.info("value in redis not found");
    }
    return value;
  }


}
