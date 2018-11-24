package mybatis.bingding;

import mybatis.bingding.SqlCommandType;

/**
 * 类名称: MappedStatement
 * 功能描述:
 * 日期:  2018/11/22 17:53
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class MappedStatement {

    private final String name;

    private final SqlCommandType type;

    private final String sql;


    public MappedStatement(String name, SqlCommandType type, String sql) {
        this.name = name;
        this.type = type;
        this.sql = sql;
    }

    public String getName() {
        return name;
    }

    public SqlCommandType getType() {
        return type;
    }

    public String getSql() {
        return sql;
    }
}
