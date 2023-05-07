package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.common.CustomException;
import com.zhangdi.reggie.entity.Category;
import com.zhangdi.reggie.entity.Dish;
import com.zhangdi.reggie.entity.Setmeal;
import com.zhangdi.reggie.mapper.CategoryMapper;
import com.zhangdi.reggie.mapper.SetmealMapper;
import com.zhangdi.reggie.service.CategoryService;
import com.zhangdi.reggie.service.DishService;
import com.zhangdi.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要进行判断是否关联菜品或套餐
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联菜品，如果已经关联，抛出一个业务异常
        if(count1>0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("Equipments are associated with this category, deletion is not allowed.");

        }
        //查询当前分类是否关联套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            //已经关联套餐，抛出一个业务异常
            throw new CustomException("Packages are associated with this category, deletion is not allowed.");

        }

        //正常删除分类
        super.removeById(id);
    }
}
