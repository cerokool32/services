package com.lds.exito.ic.model.jenkins;

public class BuildDetail {

	private boolean building;
	private String displayName;
	private String duration;
	private String estimatedDuration;
	private String fullDisplayName;
	private int id;
	private String keepLog;
	private String queueId;
	private String result;
	private String timestamp;

	@Override
	public String toString() {
		String str = "building: " + building + "\ndisplayName: " + displayName + "\nduration: " + duration
				+ "\nestimatedDuration: " + estimatedDuration + "\nfulldisplayName: " + fullDisplayName + "\nid: " + id
				+ "\nkeepLog: " + keepLog + "\nqueueId: " + queueId + "\nresult: " + result + "\ntimestamp: "
				+ timestamp;
		return str;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public String getFullDisplayName() {
		return fullDisplayName;
	}

	public void setFullDisplayName(String fullDisplayName) {
		this.fullDisplayName = fullDisplayName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeepLog() {
		return keepLog;
	}

	public void setKeepLog(String keepLog) {
		this.keepLog = keepLog;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
