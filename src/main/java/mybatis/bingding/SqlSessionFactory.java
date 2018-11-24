package mybatis.bingding;

import mybatis.parsing.XNode;
import mybatis.parsing.XPathParser;
import mybatis.util.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * 类名称: SqlSessionFactory
 * 功能描述:
 * 日期:  2018/11/22 16:34
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class SqlSessionFactory {

    private final Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public int insert(String sql) {
        System.out.println ( " execute insert ");
        System.out.println (" execute sql : " + sql);
        return 1;
    }

    public Object select(String sql) {
        System.out.println (" execute select ");
        System.out.println (" execute sql : " + sql);
        return new Object ();
    }
}


