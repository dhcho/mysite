package com.douzone.mysite.vo;

public class SiteVo {
	private String title;
	private String welcome;
	private String profile;
	private String description;
	
	public String getTitle() {
		return (title != null) ? title : "MySite";
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcome() {
		return (welcome != null) ? welcome : "안녕하세요. 더존비즈온의 mysite에 오신 것을 환영합니다.";
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getProfile() {
		return (profile != null) ? profile : "/assets/images/douzone.png";
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDescription() {
		return (description != null) ? description : "이 사이트는 웹 프로그래밍 실습과제 예제 사이트입니다.\r\n"
				+ "메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.";
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "SiteVo [title=" + title + ", welcome=" + welcome + ", profile=" + profile + ", description="
				+ description + "]";
	}
	
}
