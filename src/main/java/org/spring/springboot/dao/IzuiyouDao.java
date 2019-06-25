package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.Izuiyou;

import java.util.List;

/**
 * 城市 DAO 接口类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
public interface IzuiyouDao {

    /**
     * 获取城市信息列表
     *
     * @return
     */
    List<Izuiyou> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     *
     * @param id
     * @return
     */
    Izuiyou findById(@Param("id") Long id);

    Long saveCity(Izuiyou city);


    Long deleteCity(Long id);

    List<Izuiyou> findData(@Param("index") int index, @Param("pageSize") int pagesize);

    List<Izuiyou> findDatarand();


}
