package org.limingnihao.application.imodel;

public class IVersionBean implements java.io.Serializable {

	private static final long serialVersionUID = 4790074937254717322L;
	private boolean success;
	private int versionId;
	private int versionCode;
	private int versionType;
	private String versionName;
	private String fileMd5;
	private String fileName;
	private String saveName;
	private String downUrl;

	@Override
	public String toString() {
		return "IVersionBean [success=" + success + ", versionId=" + versionId + ", versionCode=" + versionCode + ", versionName=" + versionName + ", fileMd5=" + fileMd5 + ", fileName=" + fileName
				+ ", saveName=" + saveName + ", downUrl=" + downUrl + "]";
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public int getVersionType() {
		return versionType;
	}

	public void setVersionType(int versionType) {
		this.versionType = versionType;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

}
