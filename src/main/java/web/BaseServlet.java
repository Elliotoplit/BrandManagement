package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*替换HttpServlet：根据请求的最后一段路径进行请求分发 */

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String requestURI = req.getRequestURI();

        //2.获取方法名（最后一段路径） brand-Axios-...-demo/brand/selectAll
        //2.1 返回最后一个 ”/“ 的索引位置
        int index = requestURI.lastIndexOf('/');
        //2.2 根据索引提取出（index+1）到末尾的字符串：index +1 排除了 ”/“ 符号
        String methodName = requestURI.substring(index + 1);

        //3 执行方法
        //3.1 获取对应的brandServlet / UserServlet 字节码对象 Class
        //this代表继承并该类的子类（BrandServlet or UserServlet..）。在继承体系中，子类调用父类方法，this指的是子类
        Class<? extends BaseServlet> aClass = this.getClass();

        //3.2 获取方法 Method 对象
        try {
            Method method = aClass.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //3.3 执行
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
