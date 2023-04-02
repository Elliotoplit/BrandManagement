package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.User;

public interface UserMapper {
     //登录账号
     @Select("SELECT * from tb_user where username = #{username} and password = #{password}")
     User select(@Param("username") String username, @Param("password") String password);

     //判断账号是否被注册
     @Select("SELECT * from tb_user where username = #{username}")
     User selectByUserName(String username);

     //注册账号
     @Insert("INSERT INTO tb_user(username, password) values (#{username},#{password})")
     Boolean add(User user);

}