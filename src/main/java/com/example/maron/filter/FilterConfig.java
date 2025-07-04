package com.example.maron.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoginFilter());
        //ログインフィルターが必要なURLテキストブロックに置換
        bean.addUrlPatterns("/maron/");//ホーム画面
        bean.addUrlPatterns("/newMessage");//新規投稿
        bean.addUrlPatterns("/userEdit/*");//ユーザー編集画面
        bean.addUrlPatterns("/user");//ユーザー管理画面
        bean.addUrlPatterns("/SignUp");//ユーザー登録画面
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<PrivilegeFilter> privilegeFilter() {
        FilterRegistrationBean<PrivilegeFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new PrivilegeFilter());
        //ログインフィルターが必要なURLテキストブロックに置換
        bean.addUrlPatterns("/userEdit/*");//ユーザー編集画面
        bean.addUrlPatterns("/user");//ユーザー管理画面
        bean.addUrlPatterns("/SignUp");//ユーザー登録画面
        bean.setOrder(2);
        return bean;
    }
}
