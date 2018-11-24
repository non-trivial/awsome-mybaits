package mybatis.bingding;

import mybatis.dynamicInterfaceProxy.MapperInterface;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 类名称: MapperMethod
 * 功能描述:
 * 日期:  2018/11/9 16:01
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class MapperMethod<T> {

    private final SqlCommand command;

    private final Configuration configuration;

    public MapperMethod(Class<T> mapperInterface, Method method, Configuration configuration) {

        this.configuration = configuration;
        String nameSpace = mapperInterface.getName ();
        method.getName ();
        MappedStatement mappedStatement = configuration.mappedStatements.get (mapperInterface.getName () + "." + method.getName ());
        command = new SqlCommand (mappedStatement.getName (), mappedStatement.getType (), mappedStatement.getSql ());
    }

    public Object execute(SqlSessionFactory sqlSessionFactory ) {
        Object result ;
        switch (command.getType ()) {
            case INSERT:
                result = sqlSessionFactory.insert (command.getSql ());
                break;

            case SELECT:
                result = sqlSessionFactory.select (command.getSql ());
                break;

            default:
                // do nothing
                result = null;
        }
        return result;
    }

    public static class SqlCommand {

        private final String name;

        private final SqlCommandType type;

        private final String sql;

        public SqlCommand(String name, SqlCommandType type,String sql) {
            this.name = name;
            this.type = type;
            this.sql = sql;
        }

        public String getName() {
            return name;
        }

        public String getSql() {
            return sql;
        }

        public SqlCommandType getType() {
            return type;
        }
    }

}
