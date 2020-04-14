package com.lt.sys.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.lt.sys.Utils.GlobalUtil;
import com.lt.sys.Utils.HttpResult;
import com.lt.sys.Utils.ResultCode;
import com.lt.sys.annotation.UserLoginToken;
import com.lt.sys.dao.IUserRepository;
import com.lt.sys.entity.User;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;


/**
 * @author jinbin
 * @date 2018-07-08 20:41
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    IUserRepository iUserRepository;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        String uri = httpServletRequest.getRequestURI();
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (null == token || StringUtils.equals(token, "")) {
                    return error("无token，请重新登录", httpServletResponse);
                }
                // 获取 token 中的 user id
                String userId;
                String ip;
                try {
                	String tokenValue = JWT.decode(token).getAudience().get(0);
                	if(!tokenValue.contains("-"))
                		throw new RuntimeException("无效的token，请重新登录");

                	String[] split = tokenValue.split("-");
                	if(null == split || split.length != 2)
                		throw new RuntimeException("无效的token，请重新登录");

                	userId = split[0];
                	ip = split[1];
                }catch (Exception e) {
                	return error(e.getMessage(), httpServletResponse);
                }
                try {
	                String cliectIp =GlobalUtil.getCliectIp(httpServletRequest);
	                if(!StringUtils.equals(ip,cliectIp)) {
	                	throw new RuntimeException("无效的token，请重新登录");
	                }

	                String password = validation(Long.parseLong(userId),uri , cliectIp,object);
	                // 验证 token
	                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();//;
                    jwtVerifier.verify(token);
                } catch (Exception e) {
                	return error(e.getMessage(), httpServletResponse);
                }
                return true;
            }
        }
        return true;
    }

    private String validation(Long id,String uri,String ip,Object object) {

    	Optional<User> optional = iUserRepository.findById(id);

    	if(!optional.isPresent()) {
    		throw new RuntimeException("用户不存在");
    	}

    	User user = optional.get();
    	

    	
    		

//    	if(StringUtils.equals(discriminator, Store.class.getSimpleName())) {
//    		if(!uri.contains("/operation")) {
//    			throw new RuntimeException("权限不足");
//    		}
//    	}

    	
    	iUserRepository.save(user);

    	 // 验证是否有访问该方法的权限
        
            return user.getPassword();
        


    	//return user.getPassword();
	}

	public boolean error(String msg,HttpServletResponse response) {
    	try {
    		//设置缓存区编码为UTF-8编码格式
        	response.setCharacterEncoding("UTF-8");
        	//在响应中主动告诉浏览器使用UTF-8编码格式来接收数据
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
          //可以使用封装类简写Content-Type，使用该方法则无需使用setCharacterEncoding

            Object json = JSONObject.toJSON(HttpResult.failure(ResultCode.CLIENT_ERROR.getCode(),msg));//;
        	response.getWriter().print(json.toString());
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
