package test.data;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.GroupDao;
import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.RoleDao;
import org.limingnihao.application.data.UserDao;
import org.limingnihao.application.data.model.GroupEntity;
import org.limingnihao.application.data.model.RegionEntity;
import org.limingnihao.application.data.model.RoleEntity;
import org.limingnihao.application.data.model.UserEntity;
import org.limingnihao.application.data.model.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestUserDao {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RegionDao regionDao;

	@Test
	public void test() {
	}

	@Test
	@Transactional
	public void save() {
		RoleEntity role = new RoleEntity();
		role.setRoleName("role_1");
		role.setSystemType(1);
		role.setUseFlag(1);
		this.roleDao.saveEntity(role);

		RegionEntity region = new RegionEntity();
		region.setRegionName("region_1");
		region.setParentEntity(null);
		region.setSequence(1);
		region.setUseFlag(1);

		GroupEntity group = new GroupEntity();
		group.setGroupName("group_1");
		group.setParentEntity(null);
		group.setSequence(1);
		group.setDescription("");
		group.setUseFlag(1);
		group.setRegionEntity(region);
		region.getGroupList().add(group);
		this.regionDao.saveEntity(region);
		this.groupDao.saveEntity(group);

		UserEntity user1 = new UserEntity();
		user1.setUsername("user_1");
		user1.setNickname("user_1");
		user1.setPassword("user_1");
		user1.setUserType(1);
		user1.setUseFlag(1);
		user1.setCreateTime(new Timestamp(System.currentTimeMillis()));
		user1.setLastTime(new Timestamp(System.currentTimeMillis()));

		UserGroupEntity ug = new UserGroupEntity();
		ug.setGroupEntity(group);
		ug.setRoleEntity(role);
		ug.setUserEntity(user1);
		user1.getUserGroupList().add(ug);
		this.userDao.saveEntity(user1);

		UserEntity user = this.userDao.getEntity(user1.getUserId());
		List<UserGroupEntity> userGroupList = user.getUserGroupList();
		for (UserGroupEntity e : userGroupList) {
			System.out.println("userGroupList - userId=" + e.getUserEntity().getUserId() + ", groupId=" + e.getGroupEntity().getGroupId() + ", roleId=" + e.getRoleEntity().getRoleId());
		}
	}

	// @Test @Transactional
	public void get() {
		UserEntity u = this.userDao.getEntity(5);
		List<UserGroupEntity> userGroupList = u.getUserGroupList();
		for (UserGroupEntity e : userGroupList) {
			System.out.println("userGroupList - userId=" + e.getUserEntity().getUserId() + ", groupId=" + e.getGroupEntity().getGroupId() + ", roleId=" + e.getRoleEntity().getRoleId());
		}
		List<GroupEntity> groupList = u.getGroupList();
		for (GroupEntity e : groupList) {
			System.out.println("groupList - groupId=" + e.getGroupId());
		}
		List<RoleEntity> roleList = u.getRoleList();
		for (RoleEntity e : roleList) {
			System.out.println("roleList - roleId=" + e.getRoleId());
		}

	}

}
