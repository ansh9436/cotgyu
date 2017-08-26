package net.cot_pr1.domain;

import java.util.Date;

public class WebBoard {
	private int bnum;
	private String title;
	private String content;
	private String writer;
	private Date date;
	private int hit;
	private String userName;
	private int recnt; //댓글 수
	private String boardtag; //최신글 구분을 위한 글 종류 
	
	public String getBoardtag() {
		return boardtag;
	}
	public void setBoardtag(String boardtag) {
		this.boardtag = boardtag;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRecnt() {
		return recnt;
	}

	public void setRecnt(int recnt) {
		this.recnt = recnt;
	}
	
	@Override
	public String toString() {
		return "WebBoard [bnum=" + bnum + ", title=" + title + ", content=" + content + ", writer=" + writer + ", date="
				+ date + ", hit=" + hit + ", userName=" + userName + ", recnt=" + recnt + ", boardtag="+ boardtag+ "]";
	}
	
	

}