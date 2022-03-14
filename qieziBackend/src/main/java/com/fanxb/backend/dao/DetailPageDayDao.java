package com.fanxb.backend.dao;

import com.fanxb.backend.entity.po.DetailPageDayPo;
import com.fanxb.backend.entity.po.HostDayPo;
import org.apache.ibatis.annotations.*;

/**
 * @author fanxb
 */
@Mapper
public interface DetailPageDayDao {
    /**
     * 获取id
     *
     * @param detailPageId id
     * @param dayNum          day
     * @author fanxb
     */
    @Select("select id from detail_page_day where detailPageId=#{detailPageId} and dayNum=#{dayNum}")
    Integer getId(@Param("detailPageId") int detailPageId, @Param("dayNum") int dayNum);

    /**
     * 插入一条数据
     *
     * @param po po
     * @author fanxb
     */
    @Insert("insert into detail_page_day(detailPageId,dayNum,pv,uv) value(#{detailPageId},#{dayNum},0,0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(DetailPageDayPo po);

    /**
     * 更新pv,uv
     *
     * @param id          id
     * @param pvIncrement pv增量
     * @param uvIncrement uv增量
     * @author fanxb
     */
    @Update("update detail_page_day set uv=uv+#{uvIncrement},pv=pv+#{pvIncrement} where id=#{id}")
    void updatePvUv(@Param("id") int id, @Param("pvIncrement") int pvIncrement, @Param("uvIncrement") int uvIncrement);
}
