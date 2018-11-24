package mybatis.bingding;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类名称: MapperProxyFactroy
 * 功能描述:
 * 日期:  2018/11/22 15:23
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class MapperProxyFactroy<T> {

    private final Class<T> mapperInterface;

    // 缓存
    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<> ();

    public MapperProxyFactroy(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }


    public T newInstance(SqlSessionFactory sqlSessionFactory){
        final MapperProxy<T> mapperProxy = new MapperProxy<> (mapperInterface, methodCache,sqlSessionFactory);
        return (T)Proxy.newProxyInstance (mapperInterface.getClassLoader (), new Class[]{mapperInterface}, mapperProxy);
    }

}
