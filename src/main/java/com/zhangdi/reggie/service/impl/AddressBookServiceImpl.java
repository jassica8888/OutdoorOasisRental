package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.entity.AddressBook;
import com.zhangdi.reggie.mapper.AddressBookMapper;
import com.zhangdi.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
