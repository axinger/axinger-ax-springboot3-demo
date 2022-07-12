package com.ax.demo.service.impl;

import com.ax.demo.entity.Person;
import com.ax.demo.mapper.PersonMapper;
import com.ax.demo.service.PersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author xing
 * @description 针对表【t_person】的数据库操作Service实现
 * @createDate 2022-06-19 02:18:15
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person>
        implements PersonService {

}



