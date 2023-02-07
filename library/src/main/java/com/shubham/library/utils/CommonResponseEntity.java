package com.shubham.library.utils;


import com.shubham.library.constant.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service("commonResponseEntity")
public class CommonResponseEntity {
    private static final Logger logger = LoggerFactory.getLogger(CommonResponseEntity.class);

    public LinkedHashMap<String, Object> ResponseEntityWithMap(Object status, Object msg,
                                                               Object code) {
        LinkedHashMap<String, Object> resultMap = null;
        resultMap = new LinkedHashMap<String, Object>();
        resultMap.put(ApplicationConstants.REQUEST_STATUS, status);
        resultMap.put(ApplicationConstants.RESPONSE_MSG, msg);
        resultMap.put(ApplicationConstants.RESPONSE_CODE, code);
        logger.info("Result :::::: " + resultMap);

        return resultMap;
    }

    public LinkedHashMap<String, Object> ResponseEntityWithMapAndData(Object status, Object msg, Object code,
                                                                      Object data) {
        LinkedHashMap<String, Object> resultMap = null;
        resultMap = new LinkedHashMap<String, Object>();
        resultMap.put(ApplicationConstants.REQUEST_STATUS, status);
        resultMap.put(ApplicationConstants.RESPONSE_MSG, msg);
        resultMap.put(ApplicationConstants.RESPONSE_CODE, code);
        resultMap.put(ApplicationConstants.RESPONSE_DATA, data);

        logger.info("Result :::::: " + resultMap);
        return resultMap;
    }

}
