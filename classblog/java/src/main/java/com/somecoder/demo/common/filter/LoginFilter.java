//package com.somecoder.demo.common.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.somecoder.demo.common.util.JwtUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author ：lishan
// * @date ：Created in 2021/1/28 14:00
// * @description：登录过滤器
// */
//@Component
//public class LoginFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        Map<String, Object> map = new HashMap<>();
//        String url = ((HttpServletRequest) servletRequest).getRequestURI();
//        if (url != null) {
//            //登录请求直接放行
//            /*
//
//             HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//             String servletPath = httpServletRequest.getServletPath();  //获取客户端所请求的脚本文件的文件路径
//
//            //不过滤处理登录页面、JS和CSS文件
//            if (!servletPath.equals("/login.jsp") && !servletPath.equals("/servlet/LoginServlet")
//                && !servletPath.endsWith(".js") && !servletPath.endsWith(".css"))
//        {
//            //省略校验代码...
//        }
//
//             */
//
//            System.out.println("++++++++++++++++++++++++++++++++");
//            System.out.println(url);
//            System.out.println("+==========================================");
//            if (
//
//                    true
////                    // 保证登录注册不用验证
////                    url.startsWith("/blog/login") || url.startsWith("/blog/register") ||
////                    // 保证js css文件不用验证
////                    url.endsWith(".js") || url.endsWith(".css") ||
////                    // 保证接口文档不用验证
////                    url.endsWith("/doc.html") || url.startsWith("/swagger-resources") || url.startsWith("/v3/api-docs")
//            ) {
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            } else {
//                //其他请求验证token
//                JwtUtils jwtUtils = new JwtUtils();
//                String token = ((HttpServletRequest) servletRequest).getHeader("token");
//                if (StringUtils.isNotBlank(token)) {
//                    //token验证结果
//                    int verify = jwtUtils.verify(token);
//                    if (verify != 1) {
//                        //验证失败
//                        if (verify == 2) {
//                            map.put("errorMsg", "token已过期");
//                        } else if (verify == 0) {
//                            map.put("errorMsg", "用户信息验证失败");
//                        }
//                    } else if (verify == 1) {
//                        //验证成功，放行
//                        filterChain.doFilter(servletRequest, servletResponse);
//                        return;
//                    }
//                } else {
//                    //token为空的返回
//                    map.put("errorMsg", "未携带token信息");
//                }
//            }
//            JSONObject jsonObject = new JSONObject(map);
//            servletResponse.setContentType("application/json");
//            //设置响应的编码
//            servletResponse.setCharacterEncoding("utf-8");
//            //响应
//            PrintWriter pw = servletResponse.getWriter();
//            pw.write(jsonObject.toString());
//            pw.flush();
//            pw.close();
//        }
//    }
//}
