package io.github.educontessi.core.config.redis;

import java.time.Duration;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	@Primary
	public CompositeCacheManager cacheManager() {

		CompositeCacheManager cacheManager = new CompositeCacheManager();

		ArrayList<CacheManager> cacheManagers = new ArrayList<>();
		// cacheManagers.add(simpleCacheManager());
		cacheManagers.add(redisCacheManager(new RedisObjectMapper(), redisConnectionFactory));
		cacheManager.setCacheManagers(cacheManagers);
		cacheManager.setFallbackToNoOpCache(true);
		return cacheManager;
	}

	@Bean
	public RedisCacheManager redisCacheManager(ObjectMapper objectMapper,
			RedisConnectionFactory redisConnectionFactory) {

		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1))

				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))

		;

		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(cacheConfiguration).build();
		cacheManager.setTransactionAware(true);
		return cacheManager;
	}

	public class RedisObjectMapper extends ObjectMapper {

		private static final long serialVersionUID = 1L;

		public RedisObjectMapper() {

			super();

			this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

			this.registerModule(new Hibernate5Module().disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION));

			this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		}

	}

}
