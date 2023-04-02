package web;



import com.alibaba.fastjson.JSON;
import pojo.User;
import service.UserService;
import service.impl.UserServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    //用户登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取登录用户
        String s = request.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        //2. 调用 userService完成查询
        User u = userService.login(user);

        //3 判断逻辑
        if (u == null ){
            //登陆失败
            response.getWriter().write("fail");
        }else {
            //登录成功
            response.getWriter().write("success");

            //将用户名 存入session以便于在每个页面都可以获取到session里的数据
            HttpSession session = request.getSession();
            session.setAttribute("user",user);

            //获取是否保存密码
            String remember = request.getParameter("remember");
            //保存账号密码 cookie
            if("true".equals(remember)){
                //勾选了记住密码
                Cookie c_username = new Cookie("username", user.getUsername());
                Cookie c_password = new Cookie("password", user.getPassword());

                //设置存货时间 一周
                c_password.setMaxAge(60*60*24*7);
                c_username.setMaxAge(60*60*24*7);

                //发送
                response.addCookie(c_username);
                response.addCookie(c_password);
            }

        }
    }



    //用户注册
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 判断机器人

        // 获取用户填写的验证码
        String checkCode = request.getParameter("checkCode");
        // 获取验证码准值

        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
        //判断验证码是否一致
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            //忽略大小写 如果不一致 返回验证码错误标识符: codeErr 并终止当前方法
            response.getWriter().write("codeErr");
            return;
        }

        //1 接收表单JSON数据 解析为user对象

        String s = request.getReader().readLine();
        User user = JSON.parseObject(s, User.class);




        //2. 判断账号是否被注册 flag?用户名可用:用户名已被注册
        boolean flag = userService.register(user);
        if (flag){
            //注册成功
            response.getWriter().write("success");
        }else {
            //用户名已被注册
            response.getWriter().write("fail");
        }

    }


    /*检查用户名是否可用*/
    public void username(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.获取注册时填写的用户名
        String username = request.getParameter("username");

        //2.service层执行查询并返回数字 1：允许注册 0:禁止注册
        int flag = userService.checkName(username);

        //3.返回结果 将int类型的flag转为String
        response.getWriter().write(String.valueOf(flag));
    }

    //自动填充密码
    public void cookieUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //如果之前勾选过记住密码且密码在cookie中未过期
        String username ="";
        String password ="";
        for (Cookie cookie : request.getCookies()) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
                continue;
            }
            if ("password".equals(cookie.getName())){
                password = cookie.getValue();
            }
        }
        //账号密码封装为user对象并转为json返回
        User cookieUser = new User();
        cookieUser.setUsername(username);
        cookieUser.setPassword(password);

        String jsonString = JSON.toJSONString(cookieUser);
        response.getWriter().write(jsonString);
    }



}
