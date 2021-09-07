package com.pilsa.invest.framework.properties;

import lombok.Setter;

/**
 * 다수의 서버 다수의 인스턴스 동작을 위한 SystemProperties
 * 주입방법 : JVM Option 으로 주입 (-Dinstance.id=ppia11 -Dspring.profiles.active=prd)
 * 불변 속성의 시스템 Property를 관리한다.
 *
 * @author pilsa_home1
 */
@Setter
public class SystemProperties {
    /**
     * ======================================================================================
     * instance.id 설명 (was-instans)
     * - ACTIVE_PROFILE : 시스템 환경 코드 (l:로컬,d:개발,s:테스트,p:운영)
     * - APPLICATION_CODE : application 코드약어 (PIA: Product Investment API, 상품투자API)
     * - INSTANCE_NODE : node 번호 (11:1번WAS의 1번째instans 22:2번WAS의 2번째 instans)
     * =======================================================================================
     */
    public final static String INSTANCE_ID = System.getProperty("instance.id", "lpia11");
    public final static String ACTIVE_PROFILE = INSTANCE_ID.substring(0, 1).toUpperCase();
    public final static String APPLICATION_CODE = INSTANCE_ID.substring(1, 4).toUpperCase();
    public final static String INSTANCE_NODE = INSTANCE_ID.substring(4, 6);
}
