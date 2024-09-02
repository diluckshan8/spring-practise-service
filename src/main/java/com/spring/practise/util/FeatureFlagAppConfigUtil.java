package com.spring.practise.util;

import com.spring.practise.service.FeatureFlagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeatureFlagAppConfigUtil {
    private final FeatureFlagService featureFlagService;

    public FeatureFlagAppConfigUtil(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    public boolean isFeatureFlagEnable(String opcoId, String application, String key) {
        log.info("{} flag is for opco number : {}", key,opcoId);
        return featureFlagService.getFeatureFlag(application, key)
                .map(featureFlag -> featureFlag.getSites() != null && featureFlag.getSites().contains(opcoId))
                .orElse(false);
    }
}