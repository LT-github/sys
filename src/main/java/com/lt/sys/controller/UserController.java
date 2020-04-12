package com.lt.sys.controller;

import com.lt.sys.Utils.ContextHolderUtil;
import com.lt.sys.Utils.GlobalUtil;
import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.TokenUtil;
import com.lt.sys.controller.req.Login;
import com.lt.sys.dao.IUserRepository;
import com.lt.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("login")
    public HttpResult login(@RequestBody Login req){
        User dbUser = iUserRepository.findByUsername(req.getUsername());
        if(dbUser == null )
            throw new RuntimeException("登录失败,用户不存在");

        if(!StringUtils.equals(req.getPassword(), dbUser.getPassword()))
            throw new RuntimeException("登录失败，密码错误");

        String token = tokenUtil.getToken(dbUser, GlobalUtil.getCliectIp(ContextHolderUtil.getRequest()));

        return HttpResult.success(null,"登录成功");
    }

}
