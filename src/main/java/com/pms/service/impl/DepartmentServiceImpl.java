package com.pms.service.impl;

import com.pms.entity.Department;
import com.pms.mapper.DepartmentMapper;
import com.pms.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * All Department Details Service Implementation Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
