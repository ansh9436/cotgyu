package net.cot_pr1.domain;

import java.util.Date;

public class WebReply {
	private Integer rnum;        // 댓글 번호
    private Integer bnum;        // 게시글 번호
    private String replytext;    // 댓글 내용
    private String replyer;        // 댓글 작성자
    private String userName;    // 댓글 작성자의 이름(회원의 이름)
    private Date date;        // 댓글 작성일자
    private int regroup;
	private int restep;
	private int reindent;
    private String replytag; //대댓글종류
  

	public String getReplytag() {
		return replytag;
	}
	public void setReplytag(String replytag) {
		this.replytag = replytag;
	}
	public int getRegroup() {
		return regroup;
	}
	public void setRegroup(int regroup) {
		this.regroup = regroup;
	}
	public int getRestep() {
		return restep;
	}
	public void setRestep(int restep) {
		this.restep = restep;
	}
	public int getReindent() {
		return reindent;
	}
	public void setReindent(int reindent) {
		this.reindent = reindent;
	}
	// Getter/Setter
    public Integer getRnum() {
        return rnum;
    }
    public void setRnum(Integer rnum) {
        this.rnum = rnum;
    }
    public Integer getBnum() {
        return bnum;
    }
    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }
    public String getReplytext() {
        return replytext;
    }
    public void setReplytext(String replytext) {
        this.replytext = replytext;
    }
    public String getReplyer() {
        return replyer;
    }
    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getdate() {
        return date;
    }
    public void setdate(Date date) {
        this.date = date;
    }

    
    // toString()
    @Override
    public String toString() {
        return "WebReply [rnum=" + rnum + ", bnum=" + bnum + ", replytext=" + replytext + ", replyer=" + replyer
                + ", userName=" + userName + ", date=" + date + ", regroup=" + regroup +", restep=" +restep+", reindent="+ reindent+"]";
    }
    
}