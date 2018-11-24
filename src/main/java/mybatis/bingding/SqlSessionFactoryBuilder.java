package mybatis.bingding;

import mybatis.parsing.XNode;
import mybatis.parsing.XPathParser;
import mybatis.util.Resources;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * 类名称: SqlSessionFactoryBuilder
 * 功能描述:
 * 日期:  2018/11/23 16:44
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class SqlSessionFactoryBuilder {

        public SqlSessionFactory buider() {

            String resource = "mybatis/bingding/BindingMapper.xml";
            final Reader reader;
            try {
                // 获取xml文件，得到Reader对象
                reader = Resources.getResourceAsReader (resource);
                XPathParser parser = new XPathParser (reader);
                // 获取mapper元素，并转换为XNode对象
                XNode xNode = parser.evalNode ("/mapper");
                // 生成SqlSeesionFactory对象
                return parsePendingStatements (xNode);
            } catch (IOException e) {
                e.printStackTrace ();
                throw new RuntimeException (e);
            }
        }

        private SqlSessionFactory parsePendingStatements(XNode root) {

            // 创建一个SqlSessionFactory对象，并对xnode解析，生成MappedStatement，然后通过namespace+id生成key，放入Configuration的缓存中
            // 把获取到的Xnode加入到
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactory (new Configuration ());
            String namespace = root.getStringAttribute ("namespace");
            List<XNode> xNodes = root.evalNodes ("select|insert");
            for (XNode xNode : xNodes) {
                sqlSessionFactory.getConfiguration ().addMappedStatements (xNode, namespace);
            }
            return sqlSessionFactory;
        }

        public static void main(String[] args) {

            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder ();
            SqlSessionFactory sessionFactory = builder.buider ();
            MapperProxyFactroy mapperProxyFactroy = new MapperProxyFactroy (BindingMapper.class);
            BindingMapper bindingMapper = (BindingMapper)mapperProxyFactroy.newInstance (sessionFactory);
            int insert = bindingMapper.insert ();
            Object select = bindingMapper.select ();
        }

}
