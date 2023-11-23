package com.github.axinger.service.impl;


import org.springframework.stereotype.Service;
import com.github.axinger.service.EmployeeService;
import com.github.axinger.domain.Employee;
import com.github.axinger.mapper.EmployeeMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}