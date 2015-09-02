package org.limingnihao.application.service.model;

public class VersionBean {

	private int versionId;
	private int versionType;
	private String versionTypeName;
	private int versionCode;
	private String versionName;
	private int deviceType;
	private String deviceTypeName;
	private String description;
	private String uploadTime;
	private String downUrl;
	private String saveName;
	private String fileName;
	private String fileType;
	private String fileSize;
	private String fileMd5;

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public int getVersionType() {
		return versionType;
	}

	public void setVersionType(int versionType) {
		this.versionType = versionType;
	}

	public String getVersionTypeName() {
		return versionTypeName;
	}

	public void setVersionTypeName(String versionTypeName) {
		this.versionTypeName = versionTypeName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	@Override
	public String toString() {
		return "VersionBean [versionId=" + versionId + ", versionType=" + versionType + ", versionType=" + versionType + ", versionTypeName=" + versionTypeName + ", versionCode=" + versionCode
				+ ", versionName=" + versionName + ", downUrl=" + downUrl + ", fileName=" + fileName + ", saveName=" + saveName + ", fileType=" + fileType + ", fileSize=" + fileSize + ", fileMd5="
				+ fileMd5 + ", description=" + description + ", uploadTime=" + uploadTime + ", deviceType=" + deviceType + ", deviceTypeName=" + deviceTypeName + "]";
	}

}
