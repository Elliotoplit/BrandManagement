package service.impl;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.User;
import service.UserService;
import util.sqlSessionFactoryUtils;


public class UserServiceImpl implements UserService {
    //1.获取工厂
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryUtils.getSqlSessionFactory();

    //用户登录
    public User login(User user){
        //2 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4.执行获取
        User u = mapper.select(user.getUsername(),user.getPassword());

        //5.释放资源
        sqlSession.close();

        return u;
    }
    //用户注册
    public Boolean register(User user){
        //2 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4.执行查询
        User select = mapper.selectByUserName(user.getUsername());

        if (select == null){
            //账号未被注册 插入数据
            mapper.add(user);
            //提交事务
            sqlSession.commit();
        }

        sqlSession.close();
        return select == null;
    }

    //判断用户名是否被注册
    public int checkName(String username){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.selectByUserName(username);
        sqlSession.close();
        return  user == null?1:0;
    }

}
