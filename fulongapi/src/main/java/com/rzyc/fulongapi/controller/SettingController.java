package com.rzyc.fulongapi.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "系统设置")
@CrossOrigin("*")
@RequestMapping("setting")
@Controller
@Validated
public class SettingController extends BaseController{





}
