package net.cot_pr1.common;

import org.springframework.web.multipart.MultipartFile;

public class PhotoVo {
	 //photo_uploader.html??΄μ§?? form?κ·Έλ΄? μ‘΄μ¬?? file ?κ·Έμ nameλͺκ³Ό ?ΌμΉμμΌμ€
    private MultipartFile Filedata;
    //callback URL
    private String callback;
    //μ½λ°±?¨???
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
