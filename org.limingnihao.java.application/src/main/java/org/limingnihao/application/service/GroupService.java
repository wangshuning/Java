package org.limingnihao.application.service;

import java.util.List;

import org.limingnihao.application.service.exception.GroupNameExistsException;
import org.limingnihao.application.service.exception.GroupNullPointerException;
import org.limingnihao.application.service.exception.GroupUsingException;
import org.limingnihao.application.service.exception.RegionNullPointerException;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.GroupTreeBean;
import org.limingnihao.application.service.model.ListBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupService {

	/**
	 * 新增或更新
	 */
	public abstract int createOrUpdate(Integer groupId, String groupName, String description, Integer parentId, Integer regionId, Integer useFlag) throws GroupNameExistsException;

	/**
	 * 删除分组
	 */
	public abstract void deleteGroup(Integer groupId) throws GroupUsingException;

	/**
	 * 获取分组信息
	 */
	public abstract GroupBean getBeanByGroupId(Integer groupId);

	/**
	 * 获取分组列表 - 根据父ID
	 */
	public abstract List<GroupBean> getListAndChildrenByParentId(Integer parentId);

	/**
	 * 获取分组列表 - 根节点
	 */
	public abstract ListBean<GroupBean> getListBeanByRoot(int pageNow, int pageSize, String pageAction);

	/**
	 * 获取列表 - 树结构数据 - 根据父ID、状态
	 */
	public abstract List<GroupTreeBean> getTreeBeanListByParentId(Integer parentId, Integer useFlag);

	/**
	 * 修改区域
	 */
	public abstract void updateRegion(Integer groupId, Integer regionId) throws GroupNullPointerException, RegionNullPointerException;

	/**
	 * 修改组排序
	 */
	public abstract void updateSequence(Integer groupId, Integer parentId, Integer moveType);

	/**
	 * 获得列表-根据父ID和groupType
	 */
	public abstract ListBean<GroupBean> getListBeanByParentIdAndGroupType(Integer parentId, Integer groupType);
}
