package stockcomp.client.backend.config

import redis.clients.jedis.JedisPooled


fun getRedisClient() = JedisPooled(
    System.getenv("REDIS_HOST"),
    System.getenv("REDIS_PORT").toInt(),
    System.getenv("REDIS_USER"),
    System.getenv("REDIS_PASSWORD")
)