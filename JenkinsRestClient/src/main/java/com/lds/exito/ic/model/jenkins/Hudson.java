package com.lds.exito.ic.model.jenkins;

import java.util.List;

public class Hudson {
	
	private String assignedLabel;
	private String mode;
	private String nodeDescription;
	private String nodeName;
	private String numExecutors;
	private List<Job> jobs;
	
	@Override
	public String toString() {
		String str = "assignedLabel: " + assignedLabel
				+"\nmode: " + mode
				+"\nnodeDescription: " + nodeDescription
				+"\nnodeName: " + nodeName
				+"\nnumExecutors: " + numExecutors
				+"\njobs:";
		for(Job job : jobs){
			str += "\n" + job;
		}
		return str;
	}

	public String getAssignedLabel() {
		return assignedLabel;
	}

	public void setAssignedLabel(String assignedLabel) {
		this.assignedLabel = assignedLabel;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getNodeDescription() {
		return nodeDescription;
	}

	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNumExecutors() {
		return numExecutors;
	}

	public void setNumExecutors(String numExecutors) {
		this.numExecutors = numExecutors;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

}
