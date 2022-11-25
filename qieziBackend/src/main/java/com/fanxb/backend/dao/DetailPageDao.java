package com.fanxb.backend.dao;

import com.fanxb.backend.entity.po.DetailPagePo;
import com.fanxb.backend.entity.po.HostPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author fanxb
 * @date 2022/2/15 16:37
 */
@Mapper
public interface DetailPageDao {

    /***
     * 新增一个(uv,pv初始化为1)
     *
     * @param detailPagePo 页面详情
     *
     * @author fanxb
     * date 2022/2/15 16:39
     */
    @Insert("insert into detail_page(hostId,path,pv,uv) value(#{hostId},#{path},#{pv},#{uv})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertOne(DetailPagePo detailPagePo);

    /**
     * 获取uv,pv
     *
     * @param hostId hostId
     * @param path   path
     * @return {@link HostPo}
     * @author fanxb
     * date 2022/2/16 11:11
     */
    @Select("select id,hostId,path,uv,pv from detail_page where hostId=#{hostId} and path = #{path}")
    DetailPagePo getUvPvById(@Param("hostId") int hostId, @Param("path") String path);

    /**
     * 更新uv,pv
     *
     * @param id          id
     * @param uvIncrement uv增量
     * @author fanxb
     * date 2022/2/16 11:13
     */
    @Update("update detail_page set uv=uv+#{uvIncrement},pv=pv+1 where id=#{id}")
    void updateUvPv(@Param("id") int id, @Param("uvIncrement") int uvIncrement);

    /**
     * 根据hostId查询数据
     *
     * @param hostId hostId
     * @return java.util.List<com.fanxb.backend.entity.po.DetailPagePo>
     * @author fanxb
     * date 2022-11-25 15:47
     */
    @Select("select * from detail_page where hostId=#{hostId}")
    List<DetailPagePo> selectByHostId(int hostId);
}
