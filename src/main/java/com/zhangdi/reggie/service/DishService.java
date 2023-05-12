package com.zhangdi.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangdi.reggie.dto.DishDto;
import com.zhangdi.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品同时插入菜品对应的口味数据，需要操作两张表dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品信息同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

}
