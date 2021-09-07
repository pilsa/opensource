package com.pilsa.invest.common.code;

import java.io.Serializable;

/**
 * The interface Enum code.
 */
public interface EnumCode extends Serializable {

    Object getKey();
    String getValue();
}
