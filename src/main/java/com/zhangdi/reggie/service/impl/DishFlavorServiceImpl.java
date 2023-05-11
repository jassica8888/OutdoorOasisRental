package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.dto.DishDto;
import com.zhangdi.reggie.entity.DishFlavor;
import com.zhangdi.reggie.mapper.DishFlavorMapper;
import com.zhangdi.reggie.service.DishFlavorService;
import com.zhangdi.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {


}
