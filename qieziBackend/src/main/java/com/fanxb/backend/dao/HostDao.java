package com.fanxb.backend.dao;

import com.fanxb.backend.entity.po.HostPo;
import org.apache.ibatis.annotations.*;

/**
 * @author fanxb
 * @date 2022/2/15 16:37
 */
@Mapper
public interface HostDao {

    /***
     * 新增一个
     *
     * @param host host
     *
     * @author fanxb
     * date 2022/2/15 16:39
     */
    @Insert("insert into host(`key`,secret,name,host,pv,uv) value(#{key},#{secret},#{name},#{host},0,0)")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertOne(HostPo host);

    /**
     * 根据key获取id
     *
     * @param key key
     * @author fanxb
     * date 2022/2/16 10:33
     */
    @Select("select id from host where `key` = #{key}")
    Integer getIdByKey(String key);

    /**
     * 获取uv,pv
     *
     * @param id id
     * @return {@link HostPo}
     * @author fanxb
     * date 2022/2/16 11:11
     */
    @Select("select id,uv,pv from host where id=#{id}")
    HostPo getUvPvById(int id);

    /**
     * 更新uv,pv
     *
     * @param id          id
     * @param uvIncrement uv增量
     * @author fanxb
     * date 2022/2/16 11:13
     */
    @Update("update host set uv=uv+#{uvIncrement},pv=pv+1 where id=#{id}")
    void updateUvPv(@Param("id") int id, @Param("uvIncrement") int uvIncrement);
}
