package org.spring.springboot.service;

import org.spring.springboot.domain.Bsbdj;
import org.spring.springboot.domain.CheckStatusResult;
import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.domain.Pic;

import java.util.List;

/**
 * 城市业务逻辑接口类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
public interface PicService {

    /**
     * 查询所有城市
     *
     * @return
     */
    List<Izuiyou> findALLCity();

    /**
     * 根据城市 ID,查询城市信息
     *
     * @param id
     * @return
     */
    Izuiyou findCityById(Long id);

    /**
     * 新增城市信息
     *
     * @param city
     * @return
     */
    Long saveCity(Pic city);

    void saveBdj(Bsbdj bsbdj);


    /**
     * 根据城市 ID,删除城市信息
     *
     * @param id
     * @return
     */
    Long deleteCity(Long id);

    List<Izuiyou> getUserInfoFromAuthority();

    Bsbdj getBdjFromAuthority();


    List<Izuiyou> queryIzuiyou(int index, int pageSize);

    List<Izuiyou> queryIzuiyourand();

    List<Pic> findStatus();


}