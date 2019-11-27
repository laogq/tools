package com.anvy.tools.util;

import com.anvy.tools.common.constant.Constant;
import com.anvy.tools.vo.ResultVo;

public class ResUtil {

    public static ResultVo success(Object result){
        return new ResultVo(Constant.CODE_SUCCESS,Constant.INFO_SUCCESS,result);
    }

    public static ResultVo error(Object result){
        return new ResultVo(Constant.CODE_FAIL,Constant.LOGIN_FAIL,result);
    }

    public static ResultVo paramError(Object result){
        return new ResultVo(Constant.CODE_FAIL,Constant.LOGIN_FAIL,result);
    }
}
