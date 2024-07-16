package com.curcus.lms.model.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {
    public static Map<String,String> getError(Integer code) {
        Map<String, String> map = new HashMap<String, String>();
        switch (code) {
            case 1:
                map.put("MSG 1", "Vui lòng điền đầy đủ thông tin");
                break;
            case 2:
                map.put("MSG 2", "Người dùng đã tồn tại trong hệ thống");
                break;
            case 3:
                map.put("MSG 3", "Mã xác thực đã hết hiệu lực");
                break;
            case 8:
                map.put("MSG 8", "Tài khoản không tồn tại");
                break;
            case 9:
                map.put("MSG 9", "Incorrect password");
                break;
            case 10:
                map.put("MSG 10", "Course not found");
                break;
            // TODO case 4 - 22
            case 21:
                map.put("MSG 21", "Đã xảy ra lỗi, vui lòng thử lại sau");
                break;
            case 23:
                map.put("MSG 23", "Internal Server Error");
                break;
            case 24:
                map.put("MSG 24", "Permission Denied");
                break;
            default:
                map.put("MSG ?", "Lỗi không xác định");
                break;
        }
        return map;
    }
}
