package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.entity.ShoppingCart;
import com.zhangdi.reggie.mapper.ShoppingCartMapper;
import com.zhangdi.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
