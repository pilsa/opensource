package com.pilsa.invest.framework.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * The enum Environment code.
 * 시스템 환경 구분
 *
 * @author pilsa_home1
 */
@Getter
@RequiredArgsConstructor
public enum EnvironmentCode {
	LOCAL("local", "로컬", "L"),
    DEVELOP("dev", "개발", "D"),
    STAGE("stg", "스테이지(테스트)" ,"S"),
    PRODUCTION("prd", "운영", "P"),
	;

    private final String code;
    private final String name;
    private final String prefix;

    private static String activeProfile = SystemProperties.ACTIVE_PROFILE;

    public static EnvironmentCode getCurrentEnvironmentCode() {
        String env = getSystemEnvironmentVariable();
        if (StringUtils.isEmpty(env)) return LOCAL;
        for (EnvironmentCode environmentCode: EnvironmentCode.values()) {
            if (environmentCode.getCode().equals(env)) return environmentCode;
        }
        return LOCAL;
    }

    public static boolean isProduction() {
        String env = getSystemEnvironmentVariable();
        if (StringUtils.isEmpty(env)) env = LOCAL.getCode();
        return PRODUCTION.getCode().equals(env);
    }

    public static boolean isDevelop() {
        String env = getSystemEnvironmentVariable();
        if (StringUtils.isEmpty(env)) env = LOCAL.getCode();
        return DEVELOP.getCode().equals(env);
    }

    public static boolean isStage() {
        String env = getSystemEnvironmentVariable();
        if (StringUtils.isEmpty(env)) env = LOCAL.getCode();
        return STAGE.getCode().equals(env);
    }

    public static boolean isLocal() {
        String env = getSystemEnvironmentVariable();
        if (StringUtils.isEmpty(env)) env = LOCAL.getCode();
        return LOCAL.getCode().equals(env);
    }

    private static String getSystemEnvironmentVariable() {
        return activeProfile;
    }
}
