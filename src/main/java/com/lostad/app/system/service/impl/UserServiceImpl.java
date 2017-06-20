package com.lostad.app.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.common.dao.JdbcDao;
import com.lostad.app.common.dao.JpaDao;
import com.lostad.app.common.service.impl.BaseServiceImpl;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.IRoleService;
import com.lostad.app.system.service.IUserService;
import com.lostad.app.system.util.UserUtils;

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 * 
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements
		IUserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private JpaDao jpaDao;
	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private IRoleService roleService;

	@Override
	public IBaseSpringDataDao<User, String> getBaseDao() {
		return userDao;
	}
	@Override
	public User findByUserName(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public void saveOrUpdate(User user) {
		if (user.getId() != null) {
			update(user);
		} else {
			save(user);
		}
	}
	

	@Override
	public void delete(String id) {
		User user = find(id);
		Assert.state(!"admin".equals(user.getUsername()), "超级管理员用户不能删除");
		super.delete(id);
	}

	@Override
	public void grant(String id, String[] roleIds) {
		User user = find(id);
		Assert.notNull(user, "用户不存在");
		Assert.state(!"admin".equals(user.getUsername()), "超级管理员用户不能修改管理角色");
		Role role;
		Set<Role> roles = new HashSet<Role>();
		if (roleIds != null) {
			for (int i = 0; i < roleIds.length; i++) {
				String rid = roleIds[i];
				role = roleService.find(rid);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		update(user);
	}
	@Override
	public Set<String>  findMyPermitions() {
		
		String userId = UserUtils.getCurrUser().getId();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT DISTINCT r.permission as permission from sys_user u,sys_user_role ur,sys_role_resource rr,sys_resource r ")
		.append(" where u.id = ur.user_id and ur.role_id = rr.role_id and r.permission is not null and r.permission !='' ")
		.append(" and u.id=? ");
		
		List<String> list = jdbcDao.queryListBySql(sb.toString(),userId);
		Set<String> s = new HashSet<>();
		for(String m:list){
			String code =m;
			if(!code.equals("")){
				s.add(code);
			}
		}
		
		return s;
	}
	@Override
	public Set<String> findMyRoles() {
		String userId = UserUtils.getCurrUser().getId();
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select r.role_code as roleCode from sys_role r,sys_user_role ur  ")
		.append(" where r.id = ur.role_id and r.del_flag=0 ")
		.append(" and ur.user_id=? ");
		
		List<String> list = jdbcDao.queryListBySql(sb.toString(),userId);
		Set<String> s = new HashSet<>();
		for(String m:list){
			String code = m;
			if(!code.equals("")){
				s.add(code);
			}
		}
		
		return s;
	}
}
