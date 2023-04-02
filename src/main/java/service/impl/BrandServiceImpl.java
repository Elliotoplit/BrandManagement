package service.impl;

import mapper.BrandMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Brand;
import pojo.PageBean;
import service.BrandService;
import util.sqlSessionFactoryUtils;

import java.util.List;

public class BrandServiceImpl implements BrandService {

    //1.调用BrandMapper.selectAll()
    SqlSessionFactory factory = sqlSessionFactoryUtils.getSqlSessionFactory();

    //查询所有
    public List<Brand> selectAll() {


        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法
        List<Brand> brands = mapper.selectAll();

        //5 关闭sqlSession
        sqlSession.close();

        //6 返回结果
        return brands;
    }


    //添加数据
    public void add(Brand brand) {

        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法
        mapper.add(brand);

        //5 提交事务
        sqlSession.commit();

        //6 关闭sqlSession
        sqlSession.close();
    }

    //查询(By Id)
    public Brand selectById(int id) {

        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法
        Brand brand = mapper.selectById(id);

        //5 关闭sqlSession
        sqlSession.close();

        //6 返回结果
        return brand;
    }


    //修改数据
    public void update(Brand brand) {

        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法
        mapper.update(brand);

        //5 提交事务
        sqlSession.commit();

        //5 关闭sqlSession
        sqlSession.close();
    }

    //删除数据
    public void delete(int id) {

        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法
        mapper.deleteById(id);

        //5 提交事务
        sqlSession.commit();

        //5 关闭sqlSession
        sqlSession.close();
    }

    //批量删除
    public void deleteByIds(int[] ids) {

        //2 获取sqlSession
        SqlSession sqlSession = factory.openSession();

        //3 获取mapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4 调用方法 批量删除
        mapper.deleteByIds(ids);
        //5 提交事务
        sqlSession.commit();

        //5 关闭sqlSession
        sqlSession.close();


    }


    public PageBean<Brand> selectByPage(int currentPage, int pageSize){

        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //计算开始索引
        int begin = (pageSize - 1) * currentPage;
        int size = pageSize;

        List<Brand> brands = mapper.selectByPage(begin, size);
        int total = mapper.selectTotal();
        PageBean<Brand> brandPageBean = new PageBean<>();

        //封装PageBean对象
        brandPageBean.setRows(brands);
        brandPageBean.setTotalCount(total);

        //释放资源
        sqlSession.close();

        return brandPageBean;
    }


    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        // 处理brand条件，模糊表达式
        String brandName = brand.getBrandName();
        if (brandName != null && brandName.length() > 0) {
            brand.setBrandName("%" + brandName + "%");
        }

        String companyName = brand.getCompanyName();
        if (companyName != null && companyName.length() > 0) {
            brand.setCompanyName("%" + companyName + "%");
        }

        //5. 查询当前页数据
        List<Brand> rows = mapper.selectByPageAndCondition(begin, size, brand);

        //6. 查询总记录数
        int totalCount = mapper.selectTotalByCondition(brand);

        //7. 封装PageBean对象
        PageBean<Brand> pageBean = new PageBean<>();

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        //8. 释放资源
        sqlSession.close();

        return pageBean;
    }

}
