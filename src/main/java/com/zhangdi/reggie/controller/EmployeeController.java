package com.zhangdi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangdi.reggie.common.R;
import com.zhangdi.reggie.entity.Employee;
import com.zhangdi.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /**Employee login 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){


//1.将页面提交的密码password进行md5加密;Encrypt the password submitted by the page with md5 encryption.
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//2.根据页面提交的用户名username查询数据;Query data based on the username submitted by the page.
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//3.如果没有查询到则返回登录失败结果;If no data is found, return a login failure result.
        if(emp==null){
            return R.error("Login Failed");
        }

//4.密码比对，如果不一致则返回登陆失败结果;Compare passwords, and if they do not match, return a login failure result.
        if (!emp.getPassword().equals(password)){
            return R.error("Login Failed");
        }

//5.查看员工状态，如果已为已禁用状态，则返回员工已禁用结果;Check the status of the employee, and if it is already disabled, return a result indicating that the employee is disabled.
        if(emp.getStatus()==0) {
            return R.error("Account Disabled");
        }
//6.登陆成功，将员工id存入session并返回登录成功结果;If login is successful, store the employee ID in the session and return a login success result.
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }
    /**
     * 员工退出 employee logout
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理session中保存的当前员工id
        request.getSession().removeAttribute("employee");
        return R.success("Logout Successfully");

    }

    @PostMapping
    public R<String> save (HttpServletRequest request,@RequestBody Employee employee){
        log.info("Add new employee,info:{}",employee.toString());

        //设置初始密码123456，需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置其他参数
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //获得当前登录用户id
        //Long empID = (Long)request.getSession().getAttribute("employee");
        //employee.setCreateUser(empID);
        //employee.setUpdateUser(empID);

        employeeService.save(employee);
        return R.success("New employee added successfully");


    }

    /**
     * 员工登录
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        long id = Thread.currentThread().getId();
        log.info("Thread Id is:{}",id);
        //Long empID = (long)request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empID);
        employeeService.updateById(employee);
        return R.success("Employee information updated successfully");
    }

    /**
     * 根据ID查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getByID(@PathVariable Long id){
        log.info("Query employee information by ID");
        Employee employee=employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("No corresponding employee information found");
    }

}
