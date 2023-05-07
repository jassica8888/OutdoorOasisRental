package com.zhangdi.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangdi.reggie.entity.Dish;
import com.zhangdi.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
