package net.cot_pr1.common;

import org.springframework.web.multipart.MultipartFile;

public class PhotoVo {
	 //photo_uploader.html?˜?´ì§??˜ form?ƒœê·¸ë‚´?— ì¡´ì¬?•˜?Š” file ?ƒœê·¸ì˜ nameëª…ê³¼ ?¼ì¹˜ì‹œì¼œì¤Œ
    private MultipartFile Filedata;
    //callback URL
    private String callback;
    //ì½œë°±?•¨?ˆ˜??
    private String callback_func;
 
    public MultipartFile getFiledata() {
        return Filedata;
    }
 
    public void setFiledata(MultipartFile filedata) {
        Filedata = filedata;
    }
 
    public String getCallback() {
        return callback;
    }
 
    public void setCallback(String callback) {
        this.callback = callback;
    }
 
    public String getCallback_func() {
        return callback_func;
    }
 
    public void setCallback_func(String callback_func) {
        this.callback_func = callback_func;
    }

}
