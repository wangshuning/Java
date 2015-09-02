package org.limingnihao.application.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.GroupDao;
import org.limingnihao.application.data.RoleDao;
import org.limingnihao.application.data.UserDao;
import org.limingnihao.application.data.model.GroupEntity;
import org.limingnihao.application.data.model.RoleEntity;
import org.limingnihao.application.data.model.UserEntity;
import org.limingnihao.application.data.model.UserGroupEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.UserService;
import org.limingnihao.application.service.exception.GroupNullPointerException;
import org.limingnihao.application.service.exception.MessageServiceErrorException;
import org.limingnihao.application.service.exception.PasswordErrorException;
import org.limingnihao.application.service.exception.RoleNullPointerException;
import org.limingnihao.application.service.exception.UserNullPointerException;
import org.limingnihao.application.service.exception.UserUsingException;
import org.limingnihao.application.service.exception.UsernameExistsException;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.RoleBean;
import org.limingnihao.application.service.model.UserBean;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.AesUtil;
import org.limingnihao.util.DateUtil;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PageUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Override
	public int createOrUpdate(Integer userId, String username, String nickname, String password, Integer userType, String groupIds, String roleIds, Integer useFlag) throws UsernameExistsException,
			GroupNullPointerException, RoleNullPointerException, MessageServiceErrorException {
		logger.info("createOrUpdate - userId=" + userId + ", username=" + username + ", nickname=" + nickname + ", password=" + password + ", userType=" + userType + ", groupIds=" + groupIds
				+ ", roleIds=" + roleIds + ", useFlag=" + useFlag);
		UserEntity userEntity = this.userDao.getEntity(userId);
		if (userEntity == null) {
			if (this.userDao.getEntityByUsername(username) != null) {
				throw new UsernameExistsException();
			}
			userEntity = new UserEntity();
			userEntity.setUsername(username); // 只有新增时设置用户名
			userEntity.setPassword(AesUtil.encrypt(ApplicationHelp.AES_PASSWORD, password));
			userEntity.setUserType(userType);
		}
		// 原来的分组
		else if (StringUtil.isBlank(groupIds) || "0".equals(groupIds)) {
			String groupOldIds = "";
			if (userEntity.getGroupList() != null && userEntity.getGroupList().size() > 0) {
				for (GroupEntity group : userEntity.getGroupList()) {
					groupOldIds += group.getGroupId() + ",";
				}
				groupIds = groupOldIds.substring(0, groupOldIds.length() - 1);
				logger.info("createOrUpdate - userId=" + userId + ", username=" + username + ", groupOldIds=" + groupIds);
			}
		}

		userEntity.setNickname(nickname);
		userEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userEntity.setUseFlag(useFlag);
		userEntity.setOnline(0);

		userEntity.getUserGroupList().clear();
		userEntity.getRoleList().clear();
		this.userDao.saveEntity(userEntity);

		if (!StringUtil.isBlank(groupIds) && !"0".equals(groupIds) && !StringUtil.isBlank(roleIds) && !"0".equals(roleIds)) {
			int[] groupIdArray = NumberUtil.parseInts(groupIds, ",");
			int[] roleIdArray = NumberUtil.parseInts(roleIds, ",");
			for (int i = 0; i < groupIdArray.length; i++) {
				// 分组
				GroupEntity groupEntity = this.groupDao.getEntity(groupIdArray[i]);
				if (groupEntity == null) {
					throw new GroupNullPointerException();
				}
				// 角色
				RoleEntity roleEntity = this.roleDao.getEntity(roleIdArray[i]);
				if (roleEntity == null) {
					throw new RoleNullPointerException();
				}
				UserGroupEntity egEntity = new UserGroupEntity();
				egEntity.setUserEntity(userEntity);
				egEntity.setGroupEntity(groupEntity);
				egEntity.setRoleEntity(roleEntity);
				userEntity.getUserGroupList().add(egEntity);
				userEntity.getRoleList().add(roleEntity);
			}
		}

		this.userDao.saveEntity(userEntity);
		return userEntity.getUserId();
	}

	@Override
	public void deleteEntity(Integer userId) throws UserNullPointerException, UserUsingException, MessageServiceErrorException {
		logger.info("deleteEntity - userId=" + userId);
		UserEntity userEntity = this.userDao.getEntity(userId);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		userEntity.getRoleList().clear();
		userEntity.getUserGroupList().clear();
		this.userDao.saveEntity(userEntity);
		this.userDao.deleteEntity(userEntity);
	}

	@Override
	public UserBean getBeanByUserId(Integer userId) {
		UserEntity entity = this.userDao.getEntity(userId);
		if (entity != null) {
			UserBean bean = new UserBean();
			bean.setUserId(entity.getUserId());
			bean.setUsername(entity.getUsername());
			bean.setNickname(entity.getNickname());
			bean.setUserType(entity.getUserType());
			bean.setUserTypeName(ApplicationHelp.getAttributeName("USER_TYPE", entity.getUserType().intValue()));
			bean.setCreateTime(DateUtil.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm"));
			bean.setLastTime(DateUtil.format(entity.getLastTime(), "yyyy-MM-dd HH:mm"));
			bean.setUseFlag(entity.getUseFlag());
			bean.setIsOnline(entity.getOnline());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());

			int[] groupIds = new int[entity.getGroupList().size()];
			String[] groupNames = new String[entity.getGroupList().size()];
			List<GroupBean> groupBeanList = new ArrayList<GroupBean>();
			for (int i = 0; i < entity.getGroupList().size(); i++) {
				groupIds[i] = entity.getGroupList().get(i).getGroupId();
				groupNames[i] = entity.getGroupList().get(i).getGroupName();
				GroupBean groupBean = new GroupBean();
				groupBean.setGroupId(groupIds[i]);
				groupBean.setGroupName(groupNames[i]);
				groupBeanList.add(groupBean);
			}
			bean.setGroupIds(groupIds);
			bean.setGroupNames(groupNames);
			bean.setGroupBeanList(groupBeanList);

			int[] roleIds = new int[entity.getRoleList().size()];
			String[] roleNames = new String[entity.getRoleList().size()];
			List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
			for (int i = 0; i < entity.getRoleList().size(); i++) {
				roleIds[i] = entity.getRoleList().get(i).getRoleId();
				roleNames[i] = entity.getRoleList().get(i).getRoleName();
				RoleBean roleBean = new RoleBean();
				roleBean.setRoleId(roleIds[i]);
				roleBean.setRoleName(roleNames[i]);
				roleBeanList.add(roleBean);
			}
			bean.setRoleIds(roleIds);
			bean.setRoleNames(roleNames);
			bean.setRoleBeanList(roleBeanList);
			return bean;
		}
		return null;
	}

	@Override
	public UserBean getBeanByUsername(String username) {
		UserEntity userEntity = this.userDao.getEntityByUsername(username);
		if (userEntity != null) {
			return this.getBeanByUserId(userEntity.getUserId());
		}
		return null;
	}

	@Override
	public ListBean<UserBean> getListBeanByGroupIdRoleIdNicknameUserType(Integer groupId, Integer roleId, String nickname, Integer userType, int pageNow, int pageSize, String pageAction) {
		ListBean<UserBean> listBean = new ListBean<UserBean>();
		listBean.setNumberTotal(this.userDao.getListByGroupIdRoleIdNicknameUserType_count(groupId, roleId, nickname, userType, null));
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize));// 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal()));// 操作后的当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<UserBean> beanList = new ArrayList<UserBean>();
		List<UserEntity> userEntityList = this.userDao.getListByGroupIdRoleIdNicknameUserType(groupId, roleId, nickname, userType, null, firstResult, maxResults);
		for (UserEntity userEntity : userEntityList) {
			UserBean bean = this.getBeanByUserId(userEntity.getUserId());
			beanList.add(bean);
		}
		listBean.setBeanList(beanList);
		return listBean;
	}

	@Override
	public List<UserBean> getListByGroupId(Integer groupId, String nickname, Integer userType) {
		List<UserBean> userBeanList = new ArrayList<UserBean>();
		List<UserEntity> userEntityList = this.userDao.getListByGroupIdRoleIdNicknameUserType(groupId, null, nickname, userType, UseFlagType.ENABLED.value(), 0, Integer.MAX_VALUE);
		for (UserEntity entity : userEntityList) {
			UserBean bean = new UserBean();
			bean.setUserId(entity.getUserId());
			bean.setUsername(entity.getUsername());
			bean.setNickname(entity.getNickname());
			bean.setUserType(entity.getUserType());
			bean.setUserTypeName(ApplicationHelp.getAttributeName("USER_TYPE", entity.getUserType()));
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			userBeanList.add(bean);
		}
		return userBeanList;
	}

	@Override
	public UserBean userLogin(String username, String password) throws UserNullPointerException, PasswordErrorException {
		UserEntity userEntity = this.userDao.getEntityByUsername(username);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		String aesPassword = AesUtil.encrypt(ApplicationHelp.AES_PASSWORD, password);
		logger.info("login - username=" + username + ", password=" + password + ", 结果=" + userEntity.getPassword().equals(aesPassword));
		if (!userEntity.getPassword().equals(aesPassword)) {
			throw new PasswordErrorException();
		}
		userEntity.setLastTime(new Timestamp(System.currentTimeMillis()));
		this.userDao.saveEntity(userEntity);

		// 用户详细信息
		return this.getBeanByUserId(userEntity.getUserId());
	}

	@Override
	public UserBean userLoginOut(String username) throws UserNullPointerException {
		UserEntity userEntity = this.userDao.getEntityByUsername(username);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		logger.info("loginOut - username=" + username);
		this.userDao.saveEntity(userEntity);
		// 用户详细信息
		return this.getBeanByUserId(userEntity.getUserId());
	}

	@Override
	public void userPasswordChange(Integer userId, String oldPassword, String newPassword) throws UserNullPointerException, PasswordErrorException, MessageServiceErrorException {
		logger.info("userPasswordChange - userId=" + userId + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword);
		UserEntity userEntity = this.userDao.getEntity(userId);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		if (!userEntity.getPassword().equals(AesUtil.encrypt(ApplicationHelp.AES_PASSWORD, oldPassword))) {
			throw new PasswordErrorException();
		}
		userEntity.setPassword(AesUtil.encrypt(ApplicationHelp.AES_PASSWORD, newPassword));
		this.userDao.saveEntity(userEntity);
	}

	@Override
	public void userPasswordReset(Integer userId, String password) throws UserNullPointerException, MessageServiceErrorException {
		logger.info("userPasswordReset - userId=" + userId + ", password=" + password);
		UserEntity userEntity = this.userDao.getEntity(userId);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		userEntity.setPassword(AesUtil.encrypt(ApplicationHelp.AES_PASSWORD, password));
		this.userDao.saveEntity(userEntity);
	}

}
