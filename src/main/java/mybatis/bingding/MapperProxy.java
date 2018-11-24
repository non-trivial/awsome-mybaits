package mybatis.bingding;

import mybatis.dynamicProxy.FlyInterface;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称: MapperProxy
 * 功能描述:
 * 日期:  2018/11/9 14:37
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final Class<T> mapperInterface;

    // MapperMethod缓存
    private final Map<Method, MapperMethod> methodCache;

    private final SqlSessionFactory sqlSessionFactory;

    public MapperProxy(Class<T> mapperInterface, Map<Method, MapperMethod> methodCache, SqlSessionFactory sqlSessionFactory) {
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        MapperMethod mt = cachedMapperMethod (method);
        return mt.execute (sqlSessionFactory);
    }

    // 查找缓存
    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get (method);
        if (mapperMethod == null) {
            // 缓存没有命中，新增
            mapperMethod = new MapperMethod (mapperInterface, method,sqlSessionFactory.getConfiguration ());
            methodCache.put (method, mapperMethod);
        }
        return mapperMethod;
    }

}
