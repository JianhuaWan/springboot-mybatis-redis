package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.CheckStatusResult;
import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.domain.Pic;

import java.util.List;

/**
 * 城市 DAO 接口类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
public interface PicDao {

    /**
     * 获取城市信息列表
     *
     * @return
     */
    List<Pic> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     *
     * @param id
     * @return
     */
    Pic findById(@Param("id") Long id);

    Long saveCity(Pic city);


    Long deleteCity(Long id);

    List<Pic> findData(@Param("index") int index, @Param("pageSize") int pagesize);

    List<Pic> findDatarand();

    List<Pic> findStatus();


}
