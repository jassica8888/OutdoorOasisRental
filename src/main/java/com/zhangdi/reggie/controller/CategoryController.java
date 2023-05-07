package com.zhangdi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangdi.reggie.common.R;
import com.zhangdi.reggie.entity.Category;
import com.zhangdi.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String>save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("Category Added Successfully");
    }

    @GetMapping("/page")
    public R<Page>page(int page,int pageSize){
        log.info("page={},pageSize={},name={}",page,pageSize);
        //分页构造器
        Page<Category>pageInfo = new Page<>(page,pageSize);
        //条件构造器，查询按照sort排序
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加排序条件，根据sort排序
        queryWrapper.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("Delete category,id is:{}",ids);
        categoryService.remove(ids);
        return R.success("Category information deleted successfully");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("Update Category Information:{}",category);
        categoryService.updateById(category);

        return R.success("Category Information Updated Successfully");
    }

}
