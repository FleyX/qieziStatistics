package com.fanxb.backend.dao;

import com.fanxb.backend.entity.po.HostDayPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author fanxb
 */
@Mapper
public interface HostDayDao {
    /**
     * 获取id
     *
     * @param hostId hostId
     * @param dayNum day
     * @author fanxb
     */
    @Select("select id from host_day where hostId=#{hostId} and dayNum=#{dayNum}")
    Integer getId(@Param("hostId") int hostId, @Param("dayNum") int dayNum);

    /**
     * 插入一条数据
     *
     * @param hostDayPo po
     * @author fanxb
     */
    @Insert("insert into host_day(hostId,dayNum,pv,uv) value(#{hostId},#{dayNum},0,0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(HostDayPo hostDayPo);

    /**
     * 更新pv,uv
     *
     * @param id          id
     * @param pvIncrement pv增量
     * @param uvIncrement uv增量
     * @author fanxb
     */
    @Update("update host_day set uv=uv+#{uvIncrement},pv=pv+#{pvIncrement} where id=#{id}")
    void updatePvUv(@Param("id") int id, @Param("pvIncrement") int pvIncrement, @Param("uvIncrement") int uvIncrement);

    /**
     * 根据hostId查询
     *
     * @param hostId hostId
     * @return java.util.List<com.fanxb.backend.entity.po.HostDayPo>
     * @author fanxb
     * date 2022-11-25 15:41
     */
    @Select("select * from host_day where hostId=#{hostId}")
    List<HostDayPo> selectByHostId(int hostId);
}
