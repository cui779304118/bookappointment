package com.cw.bookappointment.interceptor;

import com.cw.bookappointment.constant.Constant;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String requestPath = request.getServletPath();
        for(String uri : Constant.INTERCEPTOR_FILTER_PATH){
            if (requestPath.contains(uri)){
                flag = true;
            }
        }
        if (!flag){
            HttpSession session = request.getSession();
            String studentNum = (String) session.getAttribute("studentNum");
            System.out.println("studentNum: " + studentNum);
            if(StringUtils.isEmpty(studentNum)){
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return flag;
            }
            flag = true;
        }
        return flag;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
