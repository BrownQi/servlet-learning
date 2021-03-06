package cn.brownqi.dao;

import cn.brownqi.model.Order;
import cn.brownqi.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao {

    //使用dbutils操作数据库
    protected static QueryRunner queryRunner = new QueryRunner();

    /**
     * update() 方法用来执行：insert、update、delete语句
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return -1;
    }

    /**
     * 查询返回一个JavaBean的sql语句
     * @param type 返回的对象类型
     * @param sql SQL语句
     * @param args sql对应的参数
     * @param <T> 返回类型的泛型
     * @return
     */
    public <T> T queryForOne(Class<T> type,String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return null;
    }

    /**
     *  查询返回多个JavaBean的sql语句
     * @param type
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
   public <T> List<T> queryForList(Class<T> type,String sql ,Object...args){
       Connection connection = JdbcUtils.getConnection();
       try {
           return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
       } catch (SQLException e) {
           e.printStackTrace();
       }finally {
           JdbcUtils.close(connection);
       }
       return null;
   }

    /**
     *  查询所有
     * @param type
     * @param sql
     * @param <T>
     * @return
     */
    public <T> List<T> queryAll(Class<T> type,String sql){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return null;
    }


    /**
     * 执行返回一行一列的sql语句
     * @param sql
     * @param args
     * @return
     */
    public Object queryForSingleValue(String sql,Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return null;
    }
}
