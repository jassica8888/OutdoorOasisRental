package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.entity.Category;
import com.zhangdi.reggie.mapper.CategoryMapper;
import com.zhangdi.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
