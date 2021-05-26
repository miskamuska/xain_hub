package com.somecoder.demo.common.aop;

import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * 控制器统一异常处理
 * @author zuozhiwei
 */
@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    /**
     * 参数校验，还未进入到控制器中
     * @param request   请求
     * @param e         异常
     * @return          返回值
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> exceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e, ServletRequest servletRequest) {
        BindingResult bindingResult = e.getBindingResult();
        ApiResponse<Object> result = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                String error = fieldError.getDefaultMessage();
                result = ApiResponse.error(1001,error);
                StringBuilder logInfo = new StringBuilder();
                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) servletRequest;
                String requestBody = StringUtils.toEncodedString(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding()));
                logInfo.append("\n【body】:").append(requestBody);
                logInfo.append("\n【异常】:请求参数校验不通过");
                logInfo.append("\n【返回值】:").append(JsonUtil.toJson(result));
                request.setAttribute("log", logInfo);
            }
        }
        return result;
    }
}
