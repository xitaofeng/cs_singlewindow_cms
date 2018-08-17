package com.jspxcms.core.web.fore;

import static com.jspxcms.core.security.CmsAuthenticationFilter.FALLBACK_URL_PARAM;
import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;
import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_USERNAME_PARAM;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.jspxcms.customer.constants.CustomerConst;
import com.jspxcms.customer.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

/**
 * LoginController
 * 
 * @author liufang
 * 
 */
@Controller
public class LoginController {
	public static final String LOGIN_URL = "/login";
	public static final String LOGIN_TEMPLATE = "sys_member_login.html";
	public static final String LOGIN_INCLUDE_TEMPLATE = "sys_member_login_include.html";
	public static final String LOGIN_AJAX_TEMPLATE = "sys_member_login_ajax.html";

	@RequestMapping(value = { LOGIN_URL,
			Constants.SITE_PREFIX_PATH + "" + LOGIN_URL })
	public String login(String fallbackUrl, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		modelMap.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);
		return site.getTemplate(LOGIN_TEMPLATE);
	}

	@RequestMapping(value = { "/login_include",
			Constants.SITE_PREFIX_PATH + "/login_include" })
	public String loginInclude(String fallbackUrl, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		Servlets.setNoCacheHeader(response);
		modelMap.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);
		return site.getTemplate(LOGIN_INCLUDE_TEMPLATE);
	}

	@RequestMapping(value = { "/login_ajax",
			Constants.SITE_PREFIX_PATH + "/login_ajax" })
	public String loginAjax(String fallbackUrl, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		Servlets.setNoCacheHeader(response);
		modelMap.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);
		return site.getTemplate(LOGIN_AJAX_TEMPLATE);
	}

	@RequestMapping(value = { "/login",
			Constants.SITE_PREFIX_PATH + "/login" }, method = RequestMethod.POST)
	public String loginFail(
			@RequestParam(DEFAULT_USERNAME_PARAM) String username,
			String fallbackUrl, HttpServletRequest request,
			RedirectAttributes ra) {
		Object errorName = request
				.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (errorName != null) {
			ra.addFlashAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, errorName);
		}
		ra.addFlashAttribute(DEFAULT_USERNAME_PARAM, username);
		ra.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);
		return "redirect:login";
	}

	/**
	 * 前台登录页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/login2",
			Constants.SITE_PREFIX_PATH + "/login2" }, method = RequestMethod.GET)
	public String loginFail2( HttpServletRequest request) {

		return "login2";
	}

	/**
	 * 自定义的前台登录执行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doLogin")
	public String doLogin(HttpServletRequest request) {
		String username = request.getParameter("username");
		request.getSession().setAttribute("LOGIN_USER",username);
		String password = request.getParameter("password");

		HashMap<String, String> params = new HashMap<>();
		params.put("username",username);
		params.put("password",password);
		try{
			//第一步,获取TGT
			String s = HttpUtil.doPost(CustomerConst.URL4TGT, params);
			String tgt = s.substring(s.indexOf("TGT-"));
			tgt = tgt.substring(0,tgt.indexOf("\""));

			params.clear();
			params.put("service",CustomerConst.SELF_URL);
			//第二步,通过tgt获取st
			String st = HttpUtil.doPost(CustomerConst.URL4TGT + "/" + tgt, params);

			//第三步,通过st获取用户信息
			String url4userProfile = CustomerConst.URL4USER_INFO + "?ticket=" + st + "&service=" + CustomerConst.SELF_URL;
			String userInfo = HttpUtil.doPost(url4userProfile, null);

			System.out.println(userInfo);
			return "redirect:" + CustomerConst.SELF_URL;
		}catch (Exception e){
			System.out.println(e.getMessage());
			return "redirect:" + CustomerConst.SELF_URL + "/login2?errMsg=" + "username_or_password_error";
		}

	}

	/**
	 * 自定义前台用户的注销
	 * @param session
	 * @return
	 */
	@RequestMapping("/foreLogout")
	@ResponseBody
	public String foreLogout(HttpSession session){
		JSONObject resultObj = new JSONObject();
		session.invalidate();
		resultObj.put("code",0);
		resultObj.put("message","注销成功");
		return resultObj.toJSONString();
	}

	/**
	 * 判断用户是否登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/checkForeLogin")
	@ResponseBody
	public String checkForeLogin(HttpSession session){

		JSONObject resultObj = new JSONObject();
		String username = (String) session.getAttribute("LOGIN_USER");
		if(StringUtils.isNoneBlank(username)){
			resultObj.put("code",0);
			resultObj.put("message","用户已登录");
		} else {
			resultObj.put("code",-1);
			resultObj.put("message","用户未登录");
		}

		return resultObj.toJSONString();
	}
}
