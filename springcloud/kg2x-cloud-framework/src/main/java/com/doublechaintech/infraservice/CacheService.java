package com.doublechaintech.infraservice;

public interface CacheService {
  void cache(String tokenId, Object token, long ttl);

  <T> T get(String tokenId, Class<T> clazz);
}
