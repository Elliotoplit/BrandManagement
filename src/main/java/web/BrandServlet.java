package web;


import com.alibaba.fastjson.JSON;
import pojo.Brand;
import pojo.PageBean;
import service.BrandService;
import service.impl.BrandServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    BrandService brandService = new BrandServiceImpl();

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用BrandService完成查询
        List<Brand> brands = brandService.selectAll();


        //2.将brands对象转为JSON字符串
        String s = JSON.toJSONString(brands);

        //3.处理输出类型和字符集 响应数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);

        System.out.println("brand  select all");
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //修改字符集
        request.setCharacterEncoding("UTF-8");
        //1 接收表单数据 封装为brand对象
        BufferedReader reader = request.getReader();
        String s = reader.readLine();//所有数据均在一行

        //2.转JSON为Brand对象
        Brand brand = JSON.parseObject(s, Brand.class);

        //3. 调用 brandService完成添加
        brandService.add(brand);

        //4 响应插入成功
        response.getWriter().write("success");

        System.out.println("brand add");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取待删除数据的id
        String id = request.getReader().readLine();

        //执行 brandService 里面的删除
        brandService.delete(Integer.parseInt(id));

        //返回 成功
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //修改字符集
        request.setCharacterEncoding("UTF-8");

        String s = request.getReader().readLine();
        Brand brand = JSON.parseObject(s, Brand.class);

        brandService.update(brand);

        response.getWriter().write("1");

    }

    public void selectById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收 id
        String s = request.getReader().readLine();
        int id = Integer.parseInt(s);
        Brand brand = brandService.selectById(id);
        String jsonString = JSON.toJSONString(brand);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收包含 ids 数组的JSON数据
        String s = request.getReader().readLine();

        //转JSON 为 int数组
        int[] ids = JSON.parseObject(s, int[].class);

        brandService.deleteByIds(ids);

        response.getWriter().write("success");
    }


    //分页查询
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收页号和 单页条目数   GET方法： url?currentPage=1&pageSize=5
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");

        int _currentPage = Integer.parseInt(currentPage);
        int _pageSize = Integer.parseInt(pageSize);

        //调用查询
        PageBean<Brand> pageBean = brandService.selectByPage(_currentPage, _pageSize);

        //转为JSON
        String s = JSON.toJSONString(pageBean);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }



//    分页条件查询
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理POST请求乱码问题
        request.setCharacterEncoding("utf-8");
        //接收页号和 单页条目数 并转为 int 类型   POST方法的url里添加数据： url?currentPage=1&pageSize=5
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        int _currentPage = Integer.parseInt(currentPage);
        int _pageSize = Integer.parseInt(pageSize);

        //接收brand对象 并解析为brand对象
        String s1 = request.getReader().readLine();


        Brand brand = JSON.parseObject(s1, Brand.class);
        //System.out.println(brand.getStatus() + "!!!");


        //调用查询
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(_currentPage, _pageSize,brand);

        //转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }



}
