package org.apereo.cas.monitor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.HashMap;
import java.util.Map;

/**
 * This is {@link MonitorableTask}.
 *
 * @author Misagh Moayyed
 * @since 7.0.0
 */
@With
@Getter
@RequiredArgsConstructor
public class MonitorableTask {
    private final Map<String, String> boundedValues = new HashMap<>();

    private final Map<String, String> unboundedValues = new HashMap<>();

    private final String name;

    private final Object executableTask;
}
