package com.gsh.shoppingmall.util;

import com.gsh.shoppingmall.entity.ApiResult;
/**
    * @Title: ResultUtil
    * @Package com.gsh.shoppingmall.util
    * @Description:   统一返回工具类
    * @author gsh
    * @date 2019/1/2 17:15
    */
public class ResultUtil {
    /**
     * 请求成功返回
     * @param object
     * @return
     */
    public static ApiResult success(Object object){
        ApiResult msg=new ApiResult();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        return msg;
    }
    public static ApiResult success(){
        return success(null);
    }
 
    public static ApiResult error(Integer code,String resultmsg){
        ApiResult msg=new ApiResult();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        return msg;
    }
 
 
}
