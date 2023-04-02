package service;

import pojo.Brand;
import pojo.PageBean;

import java.util.List;

public interface BrandService {
    List<Brand> selectAll();

    void add(Brand brand);

    Brand selectById(int id);

    void update(Brand brand);

    void delete(int id);

    //批量删除
    void deleteByIds(int[] ids);

    //分页查询功能
    PageBean<Brand> selectByPage(int currentPage,int pageSize);

    //分页条件查询功能
    PageBean<Brand> selectByPageAndCondition(int currentPage,int pageSize,Brand brand);
}
