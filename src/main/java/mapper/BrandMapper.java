package mapper;

import org.apache.ibatis.annotations.*;
import pojo.Brand;
import pojo.PageBean;

import java.util.List;

public interface BrandMapper {
    /*查询所有*/
    @Select("SELECT * FROM tb_brand")
    @ResultMap("brandResultMap")
    List<Brand> selectAll();

    /*插入数据*/
    @Insert("insert into tb_brand(id,brand_name, company_name, ordered, description, status) " +
            "values(null,#{brandName},#{companyName},#{ordered},#{description},#{status})" )
    @ResultMap("brandResultMap")
    void add(Brand brand);

    /*以id查询数据*/
    @Select("SELECT * FROM tb_brand WHERE id = #{id}")
    @ResultMap("brandResultMap")
    Brand selectById(int id);

    //修改数据
    @Update("UPDATE tb_brand set brand_name = #{brandName},company_name = #{companyName},ordered = #{ordered},description = #{description},status = #{status} where id = #{id}")
    @ResultMap("brandResultMap")
    void update(Brand brand);

    //删除数据
    @Delete("DELETE from tb_brand where id = #{id}")
    void deleteById(int id);

    //批量删除 数组参数需要使用注解指明使用标识符
    void deleteByIds(@Param("ids") int[] ids);

    //分页查询
    @Select("select * from tb_brand limit #{begin},#{size}")
    @ResultMap("brandResultMap")
    List<Brand> selectByPage(@Param("begin") int begin, @Param("size")int size);

    //数据库总条目数
    @Select("select count(*) from tb_brand")
    int selectTotal();

    //分页条件查询
    List<Brand> selectByPageAndCondition(@Param("begin") int begin, @Param("size")int size,@Param("brand") Brand Brand);

    //查询符合条件的总记录数
    int selectTotalByCondition(Brand brand);
}
