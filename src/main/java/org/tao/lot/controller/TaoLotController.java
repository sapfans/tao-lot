package org.tao.lot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tao.lot.response.CodeEnum;
import org.tao.lot.response.ResponseResult;
import org.tao.lot.service.PiService;
import org.tao.lot.service.PortMapService;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class TaoLotController {
    Logger logger = LoggerFactory.getLogger(TaoLotController.class);
  

    @Value(value = "${server.port}")
    private  int internalPort;
    
    @Autowired
    PortMapService portMapService;
    
    @RequestMapping(value = "/upnp/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询映射信息")
    public ResponseResult remainingsumGet() {
        ResponseResult responseResult = new ResponseResult();
        JSONObject rst = new JSONObject();
        rst.put("externalIpAddress", portMapService.getExternalIPAddress());
        rst.put("externalPort", portMapService.getExternalPort());
        rst.put("localIPAddress", portMapService.getInternalPort());
        rst.put("internalPort", portMapService.getInternalPort());
        responseResult.setData(rst);
        responseResult.setCode(CodeEnum.Success.getCode());
        return responseResult;
    }

    
    @RequestMapping(value = "/lot/example/light/open", method = RequestMethod.GET)
    @ApiOperation(value = "打开灯泡开关")
    public ResponseResult openLight() {
        ResponseResult responseResult = new ResponseResult();
        PiService.myLed.high();
        responseResult.setMessage("light is opened. state is "+PiService.myLed.getState());
        responseResult.setCode(CodeEnum.Success.getCode());
        return responseResult;
    }
   
    @RequestMapping(value = "/lot/example/light/close", method = RequestMethod.GET)
    @ApiOperation(value = "关闭灯泡开关")
    public ResponseResult closeLight() {
        ResponseResult responseResult = new ResponseResult();
        PiService.myLed.low();
        responseResult.setMessage("light is closed. state is "+PiService.myLed.getState());
        responseResult.setCode(CodeEnum.Success.getCode());
        return responseResult;
    }
    
    }
