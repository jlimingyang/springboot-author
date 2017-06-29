package com.lostad.app.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.service.impl.BaseServiceImpl;
import com.lostad.app.common.util.HqlUtil;
import com.lostad.app.system.dao.RoleDao;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.service.ResourceService;
import com.lostad.app.system.service.IRoleService;

/**
 * <p>
 * 角色表  服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements IRoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private CommonService commonService;

	@Override
	public void saveOrUpdate(Role role) {
		if(role.getId() != null){
			Role dbRole = find(role.getId());
			dbRole.setName(role.getName());
			dbRole.setRemarks(role.getRemarks());
			update(dbRole);
		}else{
			save(role);
		}
	}

	
	
	@Override
	public void delete(String id) {
		Role role = find(id);
		Assert.state(!"administrator".equals(role.getRoleCode()),"超级管理员角色不能删除");
		super.delete(id);
	}

	@Override
	public void grant(String id, String[] resourceIds) {
		Role role = find(id);
		Assert.notNull(role, "角色不存在");
		
		Assert.state(!"administrator".equals(role.getRoleCode()),"超级管理员角色不能进行资源分配");
		Menu resource;
		Set<Menu> resources = new HashSet<Menu>();
		if(resourceIds != null){
			for (int i = 0; i < resourceIds.length; i++) {
				if(StringUtils.isBlank(resourceIds[i]) || "0".equals(resourceIds[i])){
					continue;
				}
				String rid = resourceIds[i];
				resource = resourceService.find(rid);
				resources.add(resource);
			}
		}
		role.setResources(resources);
		update(role);
	}



	@Override
	public IBaseSpringDataDao<Role, String> getBaseDao() {
		return roleDao;
	}



	@Override
	public List<Role> findRoles(Role param) {
		 StringBuilder hql = new StringBuilder();
		 hql.append(" from Role where delFlag=0 ");
		 List<Object> params = new ArrayList<>();
		 HqlUtil.genWhereCondition(hql, params, param);
		 List<Role> list = commonService.findHql(hql.toString(), params.toArray());
		return list;
	}
	
}
