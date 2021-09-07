package com.pilsa.invest.framework.persistent.handelr;

import com.pilsa.invest.common.code.EnumCode;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

/**
 * The type Enum code type handler.
 * 마이바티스에서 EnumCode 사용하기 위해 정의함.
 *
 * @param <E> the type parameter
 * @author pilsa_home1
 */
@MappedTypes(EnumCode.class)
public class EnumCodeTypeHandler<E extends Enum<E> & EnumCode> implements TypeHandler<EnumCode> {

    private Class<E> type;

    /**
     * Instantiates a new Enum code type handler.
     *
     * @param type the type
     */
    public EnumCodeTypeHandler(Class<E> type){
        if(type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EnumCode enumCode, JdbcType jdbcType) throws SQLException {
        String val = enumCode != null ? (String) enumCode.getKey(): null;
        preparedStatement.setString(i, val);
    }

    @Override
    public EnumCode getResult(ResultSet resultSet, String s) throws SQLException {
        String key = resultSet != null ? resultSet.getString(s) : "";
        return EnumSet.allOf(type).stream().filter(type -> type.getKey().equals(key)).findFirst().orElse(null);
    }

    @Override
    public EnumCode getResult(ResultSet resultSet, int i) throws SQLException {
        String key = resultSet != null ? resultSet.getString(i) : "";
        return EnumSet.allOf(type).stream().filter(type -> type.getKey().equals(key)).findFirst().orElse(null);
    }

    @Override
    public EnumCode getResult(CallableStatement callableStatement, int i) throws SQLException {
        String key = callableStatement != null ? callableStatement.getString(i) : "";
        return EnumSet.allOf(type).stream().filter(type -> type.getKey().equals(key)).findFirst().orElse(null);
    }
}
