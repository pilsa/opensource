package com.pilsa.invest.framework.persistent.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.sql.Connection;
import java.time.LocalDateTime;

/**
 * The type Update interceptor.
 * (미사용)
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args={Connection.class, Integer.class}))
public class UpdateInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        MetaObject metaObject = MetaObject.forObject(handler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        String originalSql = ((String) metaObject.getValue("delegate.boundSql.sql"));

        if(!originalSql.toLowerCase().startsWith("update")){
            return invocation.proceed();
        }

        int position = originalSql.toLowerCase().indexOf("where");
        String firstPart = originalSql.substring(0, position);
        String lastPart = originalSql.substring(position);

        StringBuilder sb = new StringBuilder();
        sb.append(firstPart);

        if(!originalSql.toLowerCase().contains("inner join")){
            sb.append(", LST_CHNG_AT = '");
            sb.append(LocalDateTime.now());
        }else{
            
            sb.append(", a.LST_CHNG_AT = '");
            sb.append(LocalDateTime.now());
        }
        sb.append("' ");
        sb.append(lastPart);

        metaObject.setValue("delegate.boundSql.sql", sb.toString());

        return invocation.proceed();
    }

    public Object plugin(Object target){
        return Plugin.wrap(target, this);
    }
}
