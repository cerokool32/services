package com.lds.exito.ic.model.jenkins;

public class Build {

	private int number;
	private String url;
	private BuildDetail detail;

	@Override
	public String toString() {
		String str = "number: " + number + "\nurl: " + url + "\ndetail: " + detail;
		return str;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BuildDetail getDetail() {
		return detail;
	}

	public void setDetail(BuildDetail detail) {
		this.detail = detail;
	}
}
