package org.limingnihao.application.service.model;

import java.util.Arrays;
import java.util.List;

import org.limingnihao.util.GsonUtil;

public class UserAuthorityBean extends UserBean {

	private String sessionUrl;
	private List<String> authorityFlagList;

	public List<String> getAuthorityFlagList() {
		return authorityFlagList;
	}

	public String getAuthorityFlagString() {
		return (this.authorityFlagList != null ? "" + Arrays.toString(this.authorityFlagList.toArray()) : "null");
	}

	public String getSessionUrl() {
		return sessionUrl;
	}

	public void setAuthorityFlagList(List<String> authorityFlagList) {
		this.authorityFlagList = authorityFlagList;
	}

	public void setSessionUrl(String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}

	public String toJson() {
		return GsonUtil.toJson(this);
	}

	public String toString() {
		return "UserAuthorityBean - userId=" + this.getUserId() + ", username=" + this.getUsername() + ", nickname=" + this.getNickname() + ", authorityFlagList="
				+ (this.authorityFlagList != null ? "" + Arrays.toString(this.authorityFlagList.toArray()) : "null");
	}
}
