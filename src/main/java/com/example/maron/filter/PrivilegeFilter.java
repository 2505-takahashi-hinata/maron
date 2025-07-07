package com.example.maron.filter;

import com.example.maron.controller.form.UserForm;
import com.example.maron.repository.entity.User;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PrivilegeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse,
                         FilterChain chain) throws IOException, ServletException {

        //引数で渡された型の変換
        HttpServletRequest request = (HttpServletRequest)httpRequest;
        HttpServletResponse response = (HttpServletResponse)httpResponse;

        //departmentIdが1(総務人事部)かチェック
        HttpSession session = request.getSession();
        UserForm user = (UserForm) session.getAttribute("loginUser");

        if (user.getDepartmentId() == 1){
            chain.doFilter(request, response);
        } else {
            session.setAttribute("message", "無効なアクセスです");
            response.sendRedirect("/maron/");
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }
}
