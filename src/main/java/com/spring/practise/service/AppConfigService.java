package com.spring.practise.service;

public interface AppConfigService {

    /**
     * Maps Named Configurations from AWS AppConfig to an object and returns it.
     * @param appName Application name in AWS AppConfig.
     * @param clazz Object type that the data is to be mapped to.
     * @return Data mapped to the object type.
     * @param <T>
     */
    <T> T getConfig(String appName, Class<T> clazz);

    /**
     * Maps Common Configuration from AWS AppConfig to an object and returns it.
     * @param clazz Object type that the data is to be mapped to.
     * @return Data mapped to the object type.
     * @param <T>
     */
    <T> T getConfig(Class<T> clazz);

    /**
     * Retrieve Named Configuration from AWS AppConfig as text.
     * @param appName Application name in AWS AppConfig.
     * @return String of data retrieved.
     */
    String getConfigText(String appName);

    /**
     * Retrieve Common Configuration from AWS AppConfig as text.
     * @return String of data retrieved.
     */
    String getConfigText();
}