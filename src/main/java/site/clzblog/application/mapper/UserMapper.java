package site.clzblog.application.mapper;


import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import site.clzblog.application.cache.MyBatisRedisCache;
import site.clzblog.application.entity.UserEntity;

@Mapper
@CacheNamespace(implementation = MyBatisRedisCache.class)
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    UserEntity selectById(Long id);
}
