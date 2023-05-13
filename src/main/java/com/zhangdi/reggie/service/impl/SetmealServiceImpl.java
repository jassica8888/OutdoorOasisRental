package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.dto.SetmealDto;
import com.zhangdi.reggie.entity.Setmeal;
import com.zhangdi.reggie.entity.SetmealDish;
import com.zhangdi.reggie.mapper.SetmealMapper;
import com.zhangdi.reggie.service.SetmealDishService;
import com.zhangdi.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时需要保存套餐和菜品的关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){
        //保存套餐基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes=setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }
}
