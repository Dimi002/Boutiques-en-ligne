package com.ibrasoft.storeStackProd.response;

import java.io.Serializable;

public class UserPasswordChangeModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userId;
	private String oldPassword;
	private String newPassword;

	public UserPasswordChangeModel() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
