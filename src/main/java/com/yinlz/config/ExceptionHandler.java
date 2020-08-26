package com.yinlz.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局的系统异常处理json数据格式提示
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午1:01:22
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@RestControllerAdvice
public class ExceptionHandler implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(final HttpServletRequest request,final HttpServletResponse response,final Object object,final Exception exception){
		return null;
	}
}