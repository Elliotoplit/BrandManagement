package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*登录验证的过滤器*/
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        //1.将ServletRequest类型强转为HttpServletRequest 以获取session
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        //2,判断访问资源是否与登录注册有关
        String[] urls = {"register.html","login.html","/user/","/element-ui/","/css/","/imgs/","/js/","/checkCodeServlet",};//登录
        //获取当前访问的资源路径
        String url = req.getRequestURL().toString();
        //循环判断该url是否包含白名单里的字符串
        for(String u:urls){
            if (url.contains(u)){
                //找到了白名单资源 直接放行
                chain.doFilter(request,response);
                return;
            }
        }

        //3 与登录注册无关的资源 判断是否处于登录状态
        if (user != null){
            //用户登录过,放行
            chain.doFilter(request, response);
        }else {
            //用户暂未登录 返回登录页面
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/login.html");
        }

    }
}
