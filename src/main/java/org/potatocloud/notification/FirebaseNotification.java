package org.potatocloud.notification;

public class FirebaseNotification {

	private String appId;

	private String projectId;

	public String getAppId() {
		return appId;
	}

	public FirebaseNotification appId(String appId) {
		this.appId = appId;
		return this;
	}

	public String getProjectId() {
		return projectId;
	}

	public FirebaseNotification projectId(String projectId) {
		this.projectId = projectId;
		return this;
	}

	public void registerClient() {

	}

	public void pushToClient() {

	}

}
