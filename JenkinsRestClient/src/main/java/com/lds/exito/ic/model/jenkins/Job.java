package com.lds.exito.ic.model.jenkins;

import java.util.List;

public class Job {

	private String name;
	private String url;
	private String color;
	private boolean buildable;
	private boolean inQueue;
	private int nextBuildNumber;
	private boolean concurrentBuild;
	private List<Build> builds;

	private Build lastBuild;
	private Build lastCompletedBuild;
	private Build lastStableBuild;
	private Build lastSuccessfulBuild;
	
	@Override
	public String toString() {
		String str = "name: " + name + "\nurl: " + url + "\ncolor: " + color + "\nbuildable: " + buildable
				+ "\ninQueue: " + inQueue + "\nnextBuildNumber: " + nextBuildNumber + "\nconcurrentBuild: "
				+ concurrentBuild;
		str += "\n";
		str += "\nlastBuild: \n" + lastBuild + "\n";
		str += "\nlastCompletedBuild: \n" + lastCompletedBuild + "\n";
		str += "\nlastStableBuild: \n" + lastStableBuild + "\n";
		str += "\nlastSuccessfulBuild: \n" + lastSuccessfulBuild + "\n";
		str += "\n";
		for (Build build : builds) {
			str += build + "\n";
		}
		return str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isBuildable() {
		return buildable;
	}

	public void setBuildable(boolean buildable) {
		this.buildable = buildable;
	}

	public boolean isInQueue() {
		return inQueue;
	}

	public void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}

	public int getNextBuildNumber() {
		return nextBuildNumber;
	}

	public void setNextBuildNumber(int nextBuildNumber) {
		this.nextBuildNumber = nextBuildNumber;
	}

	public boolean isConcurrentBuild() {
		return concurrentBuild;
	}

	public void setConcurrentBuild(boolean concurrentBuild) {
		this.concurrentBuild = concurrentBuild;
	}

	public List<Build> getBuilds() {
		return builds;
	}

	public void setBuilds(List<Build> builds) {
		this.builds = builds;
	}

	public Build getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(Build lastBuild) {
		this.lastBuild = lastBuild;
	}

	public Build getLastCompletedBuild() {
		return lastCompletedBuild;
	}

	public void setLastCompletedBuild(Build lastCompletedBuild) {
		this.lastCompletedBuild = lastCompletedBuild;
	}

	public Build getLastStableBuild() {
		return lastStableBuild;
	}

	public void setLastStableBuild(Build lastStableBuild) {
		this.lastStableBuild = lastStableBuild;
	}

	public Build getLastSuccessfulBuild() {
		return lastSuccessfulBuild;
	}

	public void setLastSuccessfulBuild(Build lastSuccessfulBuild) {
		this.lastSuccessfulBuild = lastSuccessfulBuild;
	}
}
