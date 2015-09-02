package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.GroupDao;
import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.UserDao;
import org.limingnihao.application.data.model.GroupEntity;
import org.limingnihao.application.data.model.RegionEntity;
import org.limingnihao.application.service.GroupService;
import org.limingnihao.application.service.exception.GroupNameExistsException;
import org.limingnihao.application.service.exception.GroupNullPointerException;
import org.limingnihao.application.service.exception.GroupUsingException;
import org.limingnihao.application.service.exception.RegionNullPointerException;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.GroupTreeBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.type.MoveControlType;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

	public static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RegionDao regionDao;

	@Override
	public int createOrUpdate(Integer groupId, String groupName, String description, Integer parentId, Integer regionId, Integer useFlag) throws GroupNameExistsException {
		logger.info("createOrUpdate - groupId=" + groupId + ", groupName=" + groupName + ", description=" + description + ", parentId=" + parentId + ", regionId=" + regionId + ", useFlag=" + useFlag);
		GroupEntity groupEntity = this.groupDao.getEntity(groupId);
		if (groupId == null || groupId.intValue() == 0) {
			groupEntity = new GroupEntity();
			groupEntity.setSequence(-1);
		}

		// 重名判断
		GroupEntity oldEntity = this.groupDao.getEntityByGroupNameParentId(groupName, parentId);
		if ((groupId == null && oldEntity != null) || (groupId != null && oldEntity != null && groupId.intValue() != oldEntity.getGroupId().intValue())) {
			throw new GroupNameExistsException();
		}

		groupEntity.setGroupName(groupName);
		groupEntity.setDescription(description);
		groupEntity.setParentEntity(this.groupDao.getEntity(parentId));
		groupEntity.setRegionEntity(this.regionDao.getEntity(regionId));
		groupEntity.setUseFlag(useFlag);
		this.groupDao.saveEntity(groupEntity);
		return groupEntity.getGroupId();
	}

	@Override
	public void deleteGroup(Integer groupId) throws GroupUsingException {
		// 判断组织结构下面是否有用户
		int userCount = this.userDao.getListByGroupIdRoleIdNicknameUserType_count(groupId, null, null, null, null);
		if (userCount > 0) {
			throw new GroupUsingException();
		}

		// 判断组织结构下面是否有子组织结构
		GroupEntity group = this.groupDao.getEntity(groupId);
		if (group.getChildrenList() != null && !group.getChildrenList().isEmpty()) {
			throw new GroupUsingException();
		}
		this.groupDao.deleteEntity(group);
	}

	@Override
	public GroupBean getBeanByGroupId(Integer groupId) {
		GroupEntity entity = this.groupDao.getEntity(groupId);
		if (entity != null) {
			GroupBean bean = new GroupBean();
			bean.setGroupId(entity.getGroupId());
			bean.setGroupName(entity.getGroupName());
			bean.setDescription(entity.getDescription());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setRegionId(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionId() : 0);
			bean.setRegionName(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionName() : "");
			bean.setParentId((entity.getParentEntity() != null ? entity.getParentEntity().getGroupId() : 1));
			bean.setParentName((entity.getParentEntity() != null ? entity.getParentEntity().getGroupName() : ""));
			return bean;
		}
		return null;
	}

	@Override
	public List<GroupBean> getListAndChildrenByParentId(Integer parentId) {
		List<GroupBean> groupBeanList = new ArrayList<GroupBean>();
		List<GroupEntity> groupEntityList = this.groupDao.getListByParentGroupId(parentId, UseFlagType.ENABLED.value(), 0, Integer.MAX_VALUE);
		for (GroupEntity entity : groupEntityList) {
			GroupBean bean = new GroupBean();
			bean.setGroupId(entity.getGroupId());
			bean.setGroupName(entity.getGroupName());
			bean.setSequence(entity.getSequence());
			bean.setDescription(entity.getDescription());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setRegionId(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionId() : 0);
			bean.setRegionName(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionName() : "");
			bean.setParentId((entity.getParentEntity() != null ? entity.getParentEntity().getGroupId() : 1));
			bean.setParentName((entity.getParentEntity() != null ? entity.getParentEntity().getGroupName() : ""));
			if (entity.getChildrenList() != null && entity.getChildrenList().size() > 0) {
				bean.setChildrenList(this.getListAndChildrenByParentId(entity.getGroupId()));
			}
			groupBeanList.add(bean);
		}
		return groupBeanList;
	}

	@Override
	public ListBean<GroupBean> getListBeanByRoot(int pageNow, int pageSize, String pageAction) {
		ListBean<GroupBean> listBean = new ListBean<GroupBean>();
		listBean.setNumberTotal(this.groupDao.getListByRoot_count());// 总个数
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize));// 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal()));// 操作后的当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<GroupBean> groupBeanList = new ArrayList<GroupBean>();
		List<GroupEntity> groupEntityList = this.groupDao.getListByRoot(firstResult, maxResults);
		for (int i = 0; i < groupEntityList.size(); i++) {
			GroupBean bean = new GroupBean();
			bean.setGroupId(groupEntityList.get(i).getGroupId());
			bean.setGroupName(groupEntityList.get(i).getGroupName());
			bean.setGroupType(groupEntityList.get(i).getGroupType());
			bean.setDescription(groupEntityList.get(i).getDescription());
			if (groupEntityList.get(i).getParentEntity() != null) {
				bean.setParentId(groupEntityList.get(i).getParentEntity().getGroupId());
				bean.setParentName(groupEntityList.get(i).getParentEntity().getGroupName());
			}
			if (groupEntityList.get(i).getRegionEntity() != null) {
				bean.setRegionId(groupEntityList.get(i).getRegionEntity().getRegionId());
				bean.setRegionName(groupEntityList.get(i).getRegionEntity().getRegionName());
			}
			bean.setSequence(groupEntityList.get(i).getSequence());
			groupBeanList.add(bean);
		}
		listBean.setBeanList(groupBeanList);
		return listBean;
	}

	@Override
	public List<GroupTreeBean> getTreeBeanListByParentId(Integer parentId, Integer useFlag) {
		List<GroupTreeBean> treeList = new ArrayList<GroupTreeBean>();
		List<GroupEntity> groupEntityList = this.groupDao.getListByParentGroupId(parentId, useFlag, 0, Integer.MAX_VALUE);
		for (GroupEntity entity : groupEntityList) {
			GroupTreeBean bean = new GroupTreeBean();
			bean.setGroupId(entity.getGroupId());
			bean.setGroupName(entity.getGroupName());
			bean.setDescription(entity.getDescription());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setRegionId(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionId() : 0);
			bean.setRegionName(entity.getRegionEntity() != null ? entity.getRegionEntity().getRegionName() : "");
			bean.setParentId((entity.getParentEntity() != null ? entity.getParentEntity().getGroupId() : 1));
			bean.setParentName((entity.getParentEntity() != null ? entity.getParentEntity().getGroupName() : ""));
			bean.setLeaf(entity.getChildrenList().isEmpty());
			treeList.add(bean);
		}
		return treeList;
	}

	@Override
	public void updateRegion(Integer groupId, Integer regionId) throws GroupNullPointerException, RegionNullPointerException {
		GroupEntity groupEntity = this.groupDao.getEntity(groupId);
		if (groupEntity == null) {
			throw new GroupNullPointerException();
		}
		RegionEntity regionEntity = this.regionDao.getEntity(regionId);
		if (regionEntity == null) {
			throw new RegionNullPointerException();
		}
		groupEntity.setRegionEntity(regionEntity);
		this.groupDao.saveEntity(groupEntity);
	}

	@Override
	public void updateSequence(Integer groupId, Integer parentId, Integer moveType) {
		List<GroupEntity> list = this.groupDao.getListByParentGroupId(parentId, null, 0, Integer.MAX_VALUE);
		for (int i = 0; i < list.size(); i++) {
			GroupEntity entity = list.get(i);
			entity.setSequence(i);
			// 向上
			if (entity.getGroupId().intValue() == groupId.intValue() && MoveControlType.UP.value() == moveType.intValue() && i > 0) {
				GroupEntity temp = list.get(i - 1);
				entity.setSequence(i - 1);
				temp.setSequence(i);
				this.groupDao.saveEntity(temp);
			}
			// 向下
			else if (entity.getGroupId().intValue() == groupId.intValue() && MoveControlType.DOWN.value() == moveType.intValue() && i < list.size() - 1) {
				GroupEntity temp = list.get(i + 1);
				entity.setSequence(i + 1);
				temp.setSequence(i);
				i++;
				this.groupDao.saveEntity(temp);
			}
			this.groupDao.saveEntity(entity);
		}
	}

	@Override
	public ListBean<GroupBean> getListBeanByParentIdAndGroupType(Integer parentId, Integer groupType) {
		ListBean<GroupBean> listBean = new ListBean<GroupBean>();

		List<GroupBean> groupBeanList = new ArrayList<GroupBean>();
		List<GroupEntity> groupEntityList = this.groupDao.getListByParentIdAndGroupType(parentId, groupType);
		for (int i = 0; i < groupEntityList.size(); i++) {
			GroupBean bean = new GroupBean();
			bean.setGroupId(groupEntityList.get(i).getGroupId());
			bean.setGroupName(groupEntityList.get(i).getGroupName());
			bean.setGroupType(groupEntityList.get(i).getGroupType());
			bean.setDescription(groupEntityList.get(i).getDescription());
			if (groupEntityList.get(i).getParentEntity() != null) {
				bean.setParentId(groupEntityList.get(i).getParentEntity().getGroupId());
				bean.setParentName(groupEntityList.get(i).getParentEntity().getGroupName());
			}
			if (groupEntityList.get(i).getRegionEntity() != null) {
				bean.setRegionId(groupEntityList.get(i).getRegionEntity().getRegionId());
				bean.setRegionName(groupEntityList.get(i).getRegionEntity().getRegionName());
			}
			bean.setSequence(groupEntityList.get(i).getSequence());
			groupBeanList.add(bean);
		}
		listBean.setBeanList(groupBeanList);
		return listBean;
	}
}
