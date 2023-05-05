package com.zhangdi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangdi.reggie.entity.Employee;
import com.zhangdi.reggie.mapper.EmployeeMapper;
import com.zhangdi.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


}
