package com.zhangdi.reggie.controller;

import com.zhangdi.reggie.common.R;
import com.zhangdi.reggie.dto.SetmealDto;
import com.zhangdi.reggie.service.CategoryService;
import com.zhangdi.reggie.service.SetmealDishService;
import com.zhangdi.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("Package Information:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("New package added successfully");
    }
}
