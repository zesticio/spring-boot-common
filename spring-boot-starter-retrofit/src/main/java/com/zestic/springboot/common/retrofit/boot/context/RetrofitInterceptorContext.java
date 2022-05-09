package com.zestic.springboot.common.retrofit.boot.context;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

import java.util.Optional;

/**
 * The k v store for retrofit instance, because the retrofit instance is immutable,
 * and we couldn't get some useful identify from it's public method.
 * <p>
 * In order to support multiply base url endpoint instance, we must create and store them separately.
 */
/**
 * @author deebendukumar
 */
public interface RetrofitInterceptorContext {

    /**
     * Register the given interceptor to specified identity,if the context already hold the given identity,
     * we would return the old interceptor instance
     */
    Interceptor register(String identity, Interceptor interceptor);

    /**
     * remove the given retrofit from context
     *
     * @return true for succeed in remove, false for the given retrofit identity doesn't existed
     */
    boolean unregister(String identity);

    Optional<Interceptor> getRetrofit(String identity);

    boolean hasRetrofit(String identity);

    boolean empty();
}
