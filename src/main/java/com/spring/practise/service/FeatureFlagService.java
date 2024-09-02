package com.spring.practise.service;

import com.spring.practise.model.FeatureFlag;
import java.util.Optional;

/**
 * This includes checking if a feature flag is enabled and retrieving feature flag details.
 */
public interface FeatureFlagService {

    /**
     * Checks if the feature flag associated with the given key is enabled.
     *
     * @param key The key of the feature flag to check.
     * @return true if the feature flag is enabled, false otherwise.
     */
    boolean isFeatureFlagEnabled(String key);

    /**
     * Retrieves the feature flag associated with the given key.
     *
     * @param key The key of the feature flag to retrieve.
     * @return An {@link Optional} containing the {@link FeatureFlag} if found, or empty if not.
     */
    Optional<FeatureFlag> getFeatureFlag(String key);

    /**
     * Checks if the feature flag associated with the given application context and key is enabled.
     *
     * @param app The application context of the feature flag to check.
     * @param key The key of the feature flag to check.
     * @return true if the feature flag is enabled, false otherwise.
     */
    boolean isFeatureFlagEnabled(String app, String key);

    /**
     * Retrieves the feature flag associated with the given application context and key.
     *
     * @param app The application context of the feature flag to retrieve.
     * @param key The key of the feature flag to retrieve.
     * @return An {@link Optional} containing the {@link FeatureFlag} if found, or empty if not.
     */
    Optional<FeatureFlag> getFeatureFlag(String app, String key);

}
