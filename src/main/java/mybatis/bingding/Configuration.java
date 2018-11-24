package mybatis.bingding;

import mybatis.parsing.XNode;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 类名称: Configuration
 * 功能描述:
 * 日期:  2018/11/19 18:13
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class Configuration {


    // sql语句
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<> ();

    public void addMappedStatements(XNode node,String nameSpace) {

        SqlCommandType sqlCommandType = SqlCommandType.valueOf (node.getName ().toUpperCase (Locale.ENGLISH));
        MappedStatement mappedStatement = new MappedStatement (nameSpace + "." + node.getStringAttribute ("id"),sqlCommandType,node.getStringBody ());
        mappedStatements.put (nameSpace + "." + node.getStringAttribute ("id"), mappedStatement);
    }

}
