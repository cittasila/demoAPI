package com.langying.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheElement;
import org.springframework.data.redis.cache.RedisCacheKey;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.nullSafeEquals;

/**
 * Created by chenxu on 2015/12/7.
 */
public class RedisCacheExt implements Cache {

    @SuppressWarnings("rawtypes")//
    public final RedisOperations redisOperations;
    public final RedisCacheMetadata cacheMetadata;
    public final CacheValueAccessor cacheValueAccessor;

    /**
     * Constructs a new <code>RedisCache</code> instance.
     *
     * @param name cache name
     * @param prefix
     * @param redisOperations
     * @param expiration
     */
    public RedisCacheExt(String name, byte[] prefix, RedisOperations<? extends Object, ? extends Object> redisOperations,
                         long expiration) {

        hasText(name, "non-empty cache name is required");
        this.cacheMetadata = new RedisCacheMetadata(name, prefix);
        this.cacheMetadata.setDefaultExpiration(expiration);

        this.redisOperations = redisOperations;
        this.cacheValueAccessor = new CacheValueAccessor(redisOperations.getValueSerializer());
    }

    /**
     * Return the value to which this cache maps the specified key, generically specifying a type that return value will
     * be cast to.
     *
     * @param key
     * @param type
     * @return
     *
     */
    public <T> T get(Object key, Class<T> type) {

        Cache.ValueWrapper wrapper = get(key);
        return wrapper == null ? null : (T) wrapper.get();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#get(java.lang.Object)
     */
    @Override
    public Cache.ValueWrapper get(Object key) {

        Cache.ValueWrapper valueWrapper= get(new RedisCacheKey(key).usePrefix(this.cacheMetadata.getKeyPrefix()).withKeySerializer(
                redisOperations.getKeySerializer()));
        if(this.cacheMetadata.defaultExpiration!=0&&valueWrapper!=null){
            get(new RedisCacheKey(key).usePrefix(this.cacheMetadata.getKeyPrefix()).withKeySerializer(
                    redisOperations.getKeySerializer())).expireAfter(this.cacheMetadata.defaultExpiration);
            this.redisOperations.expire(key,this.cacheMetadata.defaultExpiration, TimeUnit.SECONDS);
            this.redisOperations.expire(this.cacheMetadata.cacheName+"~keys",this.cacheMetadata.defaultExpiration, TimeUnit.SECONDS);
        }
        return valueWrapper;
    }

    /**
     * Return the value to which this cache maps the specified key.
     *
     * @param cacheKey the key whose associated value is to be returned via its binary representation.
     * @return the {@link RedisCacheElement} stored at given key or {@literal null} if no value found for key.
     * @since 1.5
     */
    public RedisCacheElement get(final RedisCacheKey cacheKey) {

        notNull(cacheKey, "CacheKey must not be null!");

        byte[] bytes = (byte[]) redisOperations.execute(new AbstractRedisCacheCallback<byte[]>(new BinaryRedisCacheElement(
                new RedisCacheElement(cacheKey, null), cacheValueAccessor), cacheMetadata) {

            @Override
            public byte[] doInRedis(BinaryRedisCacheElement element, RedisConnection connection) throws DataAccessException {
                return connection.get(element.getKeyBytes());
            }
        });

        return (bytes == null ? null : new RedisCacheElement(cacheKey, cacheValueAccessor.deserializeIfNecessary(bytes)));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(final Object key, final Object value) {

        put(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getKeyPrefix()).withKeySerializer(
                redisOperations.getKeySerializer()), value).expireAfter(cacheMetadata.getDefaultExpiration()));
    }

    /**
     * Add the element by adding {@link RedisCacheElement#get()} at {@link RedisCacheElement#getKeyBytes()}. If the cache
     * previously contained a mapping for this {@link RedisCacheElement#getKeyBytes()}, the old value is replaced by
     * {@link RedisCacheElement#get()}.
     *
     * @param element must not be {@literal null}.
     * @since 1.5
     */
    public void put(RedisCacheElement element) {

        notNull(element, "Element must not be null!");

        redisOperations.execute(new RedisCachePutCallback(new BinaryRedisCacheElement(element, cacheValueAccessor),
                cacheMetadata));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#putIfAbsent(java.lang.Object, java.lang.Object)
     */
    public Cache.ValueWrapper putIfAbsent(Object key, final Object value) {

        return putIfAbsent(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getKeyPrefix())
                .withKeySerializer(redisOperations.getKeySerializer()), value)
                .expireAfter(cacheMetadata.getDefaultExpiration()));
    }

    /**
     * Add the element as long as no element exists at {@link RedisCacheElement#getKeyBytes()}. If a value is present for
     * {@link RedisCacheElement#getKeyBytes()} this one is returned.
     *
     * @param element must not be {@literal null}.
     * @return
     * @since 1.5
     */
    public Cache.ValueWrapper putIfAbsent(RedisCacheElement element) {

        notNull(element, "Element must not be null!");

        new RedisCachePutIfAbsentCallback(new BinaryRedisCacheElement(element, cacheValueAccessor), cacheMetadata);

        return toWrapper(cacheValueAccessor.deserializeIfNecessary((byte[]) redisOperations
                .execute(new RedisCachePutIfAbsentCallback(new BinaryRedisCacheElement(element, cacheValueAccessor),
                        cacheMetadata))));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#evict(java.lang.Object)
     */
    public void evict(Object key) {
        evict(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getKeyPrefix()).withKeySerializer(
                redisOperations.getKeySerializer()), null));
    }

    /**
     * @param element {@link RedisCacheElement#getKeyBytes()}
     * @since 1.5
     */
    public void evict(final RedisCacheElement element) {

        notNull(element, "Element must not be null!");
        redisOperations.execute(new RedisCacheEvictCallback(new BinaryRedisCacheElement(element, cacheValueAccessor),
                cacheMetadata));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#clear()
     */
    public void clear() {
        redisOperations.execute(cacheMetadata.usesKeyPrefix() ? new RedisCacheCleanByPrefixCallback(cacheMetadata)
                : new RedisCacheCleanByKeysCallback(cacheMetadata));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.cache.Cache#getName()
     */
    public String getName() {
        return cacheMetadata.getCacheName();
    }

    /**
     * {@inheritDoc} This implementation simply returns the RedisTemplate used for configuring the cache, giving access to
     * the underlying Redis store.
     */
    public Object getNativeCache() {
        return redisOperations;
    }

    private Cache.ValueWrapper toWrapper(Object value) {
        return (value != null ? new SimpleValueWrapper(value) : null);
    }

    /**
     * Metadata required to maintain {@link RedisCache}. Keeps track of additional data structures required for processing
     * cache operations.
     *
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCacheMetadata {

        private final String cacheName;
        private final byte[] keyPrefix;
        private final byte[] setOfKnownKeys;
        private final byte[] cacheLockName;
        private long defaultExpiration = 0;

        /**
         * @param cacheName must not be {@literal null} or empty.
         * @param keyPrefix can be {@literal null}.
         */
        public RedisCacheMetadata(String cacheName, byte[] keyPrefix) {

            hasText(cacheName, "CacheName must not be null or empty!");
            this.cacheName = cacheName;
            this.keyPrefix = keyPrefix;

            StringRedisSerializer stringSerializer = new StringRedisSerializer();

            // name of the set holding the keys
            this.setOfKnownKeys = usesKeyPrefix() ? new byte[]{} : stringSerializer.serialize(cacheName + "~keys");
            this.cacheLockName = stringSerializer.serialize(cacheName + "~lock");
        }

        /**
         * @return true if the {@link RedisCache} uses a prefix for key ranges.
         */
        public boolean usesKeyPrefix() {
            return (keyPrefix != null && keyPrefix.length > 0);
        }

        /**
         * Get the binary representation of the key prefix.
         *
         * @return never {@literal null}.
         */
        public byte[] getKeyPrefix() {
            return this.keyPrefix;
        }

        /**
         * Get the binary representation of the key identifying the data structure used to maintain known keys.
         *
         * @return never {@literal null}.
         */
        public byte[] getSetOfKnownKeysKey() {
            return setOfKnownKeys;
        }

        /**
         * Get the binary representation of the key identifying the data structure used to lock the cache.
         *
         * @return never {@literal null}.
         */
        public byte[] getCacheLockKey() {
            return cacheLockName;
        }

        /**
         * Get the name of the cache.
         *
         * @return
         */
        public String getCacheName() {
            return cacheName;
        }

        /**
         * Set the default expiration time in seconds
         *
         * @param seconds
         */
        public void setDefaultExpiration(long seconds) {
            this.defaultExpiration = seconds;
        }

        /**
         * Get the default expiration time in seconds.
         *
         * @return
         */
        public long getDefaultExpiration() {
            return defaultExpiration;
        }

    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class CacheValueAccessor {

        @SuppressWarnings("rawtypes")//
        private final RedisSerializer valueSerializer;

        @SuppressWarnings("rawtypes")
        CacheValueAccessor(RedisSerializer valueRedisSerializer) {
            valueSerializer = valueRedisSerializer;
        }

        byte[] convertToBytesIfNecessary(Object value) {

            if (value == null) {
                return new byte[0];
            }

            if (valueSerializer == null && value instanceof byte[]) {
                return (byte[]) value;
            }

            return valueSerializer.serialize(value);
        }

        Object deserializeIfNecessary(byte[] value) {

            if (valueSerializer != null) {
                return valueSerializer.deserialize(value);
            }

            return value;
        }
    }

    /**
     * @author Christoph Strobl
     * @since 1.6
     */
    static class BinaryRedisCacheElement extends RedisCacheElement {

        private byte[] keyBytes;
        private byte[] valueBytes;
        private RedisCacheElement element;

        public BinaryRedisCacheElement(RedisCacheElement element, CacheValueAccessor accessor) {

            super(element.getKey(), element.get());
            this.element = element;
            this.keyBytes = element.getKeyBytes();
            this.valueBytes = accessor.convertToBytesIfNecessary(element.get());
        }

        @Override
        public byte[] getKeyBytes() {
            return keyBytes;
        }

        public long getTimeToLive() {
            return element.getTimeToLive();
        }

        public boolean hasKeyPrefix() {
            return element.hasKeyPrefix();
        }

        public boolean isEternal() {
            return element.isEternal();
        }

        public RedisCacheElement expireAfter(long seconds) {
            return element.expireAfter(seconds);
        }

        @Override
        public byte[] get() {
            return valueBytes;
        }

    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     * @param <T>
     */
    static abstract class AbstractRedisCacheCallback<T> implements RedisCallback<T> {

        private long WAIT_FOR_LOCK_TIMEOUT = 300;
        private final BinaryRedisCacheElement element;
        private final RedisCacheMetadata cacheMetadata;

        public AbstractRedisCacheCallback(BinaryRedisCacheElement element, RedisCacheMetadata metadata) {
            this.element = element;
            this.cacheMetadata = metadata;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.core.RedisCallback#doInRedis(org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public T doInRedis(RedisConnection connection) throws DataAccessException {
            waitForLock(connection);
            return doInRedis(element, connection);
        }

        public abstract T doInRedis(BinaryRedisCacheElement element, RedisConnection connection) throws DataAccessException;

        protected void processKeyExpiration(RedisCacheElement element, RedisConnection connection) {
            if (!element.isEternal()) {
                connection.expire(element.getKeyBytes(), element.getTimeToLive());
            }
        }

        protected void maintainKnownKeys(RedisCacheElement element, RedisConnection connection) {

            if (!element.hasKeyPrefix()) {

                connection.zAdd(cacheMetadata.getSetOfKnownKeysKey(), 0, element.getKeyBytes());

                if (!element.isEternal()) {
                    connection.expire(cacheMetadata.getSetOfKnownKeysKey(), element.getTimeToLive());
                }
            }
        }

        protected void cleanKnownKeys(RedisCacheElement element, RedisConnection connection) {

            if (!element.hasKeyPrefix()) {
                connection.zRem(cacheMetadata.getSetOfKnownKeysKey(), element.getKeyBytes());
            }
        }

        protected boolean waitForLock(RedisConnection connection) {

            boolean retry=true;
            boolean foundLock = true;
            while (retry) {
                retry = false;
                if (connection.exists(cacheMetadata.getCacheLockKey())) {
                    foundLock = true;
                    try {
                        Thread.sleep(WAIT_FOR_LOCK_TIMEOUT);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    retry = true;
                }
            } ;

            return foundLock;
        }
    }

    /**
     * @author Christoph Strobl
     * @param <T>
     * @since 1.5
     */
    static abstract class LockingRedisCacheCallback<T> implements RedisCallback<T> {

        private final RedisCacheMetadata metadata;

        public LockingRedisCacheCallback(RedisCacheMetadata metadata) {
            this.metadata = metadata;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.core.RedisCallback#doInRedis(org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public T doInRedis(RedisConnection connection) throws DataAccessException {

            if (connection.exists(metadata.getCacheLockKey())) {
                return null;
            }
            try {
                connection.set(metadata.getCacheLockKey(), metadata.getCacheLockKey());
                return doInLock(connection);
            } finally {
                connection.del(metadata.getCacheLockKey());
            }
        }

        public abstract T doInLock(RedisConnection connection);
    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCacheCleanByKeysCallback extends LockingRedisCacheCallback<Void> {

        private static final int PAGE_SIZE = 128;
        private final RedisCacheMetadata metadata;

        RedisCacheCleanByKeysCallback(RedisCacheMetadata metadata) {
            super(metadata);
            this.metadata = metadata;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.cache.RedisCache.LockingRedisCacheCallback#doInLock(org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public Void doInLock(RedisConnection connection) {

            int offset = 0;
            boolean finished = false;

            while (!finished){
                // need to paginate the keys
                Set<byte[]> keys = connection.zRange(metadata.getSetOfKnownKeysKey(), (offset) * PAGE_SIZE, (offset + 1)
                        * PAGE_SIZE - 1);
                finished = keys.size() < PAGE_SIZE;
                offset++;
                if (!keys.isEmpty()) {
                    connection.del(keys.toArray(new byte[keys.size()][]));
                }
            } ;

            connection.del(metadata.getSetOfKnownKeysKey());
            return null;
        }
    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCacheCleanByPrefixCallback extends LockingRedisCacheCallback<Void> {

        private static final byte[] REMOVE_KEYS_BY_PATTERN_LUA = new StringRedisSerializer()
                .serialize("local keys = redis.call('KEYS', ARGV[1]); local keysCount = table.getn(keys); if(keysCount > 0) then for _, key in ipairs(keys) do redis.call('del', key); end; end; return keysCount;");
        private static final byte[] WILD_CARD = new StringRedisSerializer().serialize("*");
        private final RedisCacheMetadata metadata;

        public RedisCacheCleanByPrefixCallback(RedisCacheMetadata metadata) {
            super(metadata);
            this.metadata = metadata;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.cache.RedisCache.LockingRedisCacheCallback#doInLock(org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public Void doInLock(RedisConnection connection) throws DataAccessException {

            byte[] prefixToUse = Arrays.copyOf(metadata.getKeyPrefix(), metadata.getKeyPrefix().length + WILD_CARD.length);
            System.arraycopy(WILD_CARD, 0, prefixToUse, metadata.getKeyPrefix().length, WILD_CARD.length);

            connection.eval(REMOVE_KEYS_BY_PATTERN_LUA, ReturnType.INTEGER, 0, prefixToUse);

            return null;
        }
    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCacheEvictCallback extends AbstractRedisCacheCallback<Void> {

        public RedisCacheEvictCallback(BinaryRedisCacheElement element, RedisCacheMetadata metadata) {
            super(element, metadata);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.cache.RedisCache.AbstractRedisCacheCallback#doInRedis(org.springframework.data.redis.cache.RedisCacheElement, org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public Void doInRedis(BinaryRedisCacheElement element, RedisConnection connection) throws DataAccessException {

            connection.del(element.getKeyBytes());
            cleanKnownKeys(element, connection);
            return null;
        }
    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCachePutCallback extends AbstractRedisCacheCallback<Void> {

        public RedisCachePutCallback(BinaryRedisCacheElement element, RedisCacheMetadata metadata) {

            super(element, metadata);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.cache.RedisCache.AbstractRedisPutCallback#doInRedis(org.springframework.data.redis.cache.RedisCache.RedisCacheElement, org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public Void doInRedis(BinaryRedisCacheElement element, RedisConnection connection) throws DataAccessException {

            connection.multi();

            connection.set(element.getKeyBytes(), element.get());

            processKeyExpiration(element, connection);
            maintainKnownKeys(element, connection);

            connection.exec();
            return null;
        }
    }

    /**
     * @author Christoph Strobl
     * @since 1.5
     */
    static class RedisCachePutIfAbsentCallback extends AbstractRedisCacheCallback<byte[]> {

        public RedisCachePutIfAbsentCallback(BinaryRedisCacheElement element, RedisCacheMetadata metadata) {
            super(element, metadata);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.redis.cache.RedisCache.AbstractRedisPutCallback#doInRedis(org.springframework.data.redis.cache.RedisCache.RedisCacheElement, org.springframework.data.redis.connection.RedisConnection)
         */
        @Override
        public byte[] doInRedis(BinaryRedisCacheElement element, RedisConnection connection) throws DataAccessException {

            waitForLock(connection);
            byte[] resultValue = put(element, connection);

            if (nullSafeEquals(element.get(), resultValue)) {
                processKeyExpiration(element, connection);
                maintainKnownKeys(element, connection);
            }

            return resultValue;
        }

        private byte[] put(BinaryRedisCacheElement element, RedisConnection connection) {

            boolean valueWasSet = connection.setNX(element.getKeyBytes(), element.get());
            return valueWasSet ? null : connection.get(element.getKeyBytes());
        }
    }

}
