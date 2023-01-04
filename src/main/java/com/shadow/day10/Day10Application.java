package com.shadow.day10;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName Day10Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/4 19:45
 * @Version 1.0
 **/
public class Day10Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        // 准备测试Request
        HttpServletRequest multiRequest = mockRequest();

        // 要点1 控制器方法封装成为HandlerMethod
        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), Controller.class.getMethod("test", String.class, String.class, int.class, String.class, MultipartFile.class, int.class, String.class, String.class, String.class, HttpServletRequest.class, User.class, User.class));

        // 要点2 准备对象绑定与类型转换
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null,null);

        // 要点3 准备ModelAndViewContainer用来存储中间的Model结果
        ModelAndViewContainer container = new ModelAndViewContainer();

        // 参数解析组合模式,一次判断解析器是否支持
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        composite.addResolvers(
                new RequestParamMethodArgumentResolver(beanFactory, false),
                new PathVariableMethodArgumentResolver(),
                new RequestHeaderMethodArgumentResolver(beanFactory),
                new ServletCookieValueMethodArgumentResolver(beanFactory),
                new ExpressionValueMethodArgumentResolver(beanFactory),
                new ServletRequestMethodArgumentResolver(),
                new ServletModelAttributeMethodProcessor(false),
               // new RequestResponseBodyMethodProcessor(new ArrayList<>(Arrays.asList(new MappingJackson2HttpMessageConverter()))),
                new ServletModelAttributeMethodProcessor(true),
                new RequestParamMethodArgumentResolver(beanFactory, true)
        );
        // 要点4 解析每个参数值
        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
            // 加入RequestParam解析器
            RequestParamMethodArgumentResolver resolver = new RequestParamMethodArgumentResolver(beanFactory, false);
            String annotations = Arrays.stream(parameter.getParameterAnnotations()).map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining());
            String str = annotations.length() > 0 ? "@" + annotations + " " : " ";
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            if (composite.supportsParameter(parameter)) {
                Object o = composite.resolveArgument(parameter, container, new ServletWebRequest(multiRequest), factory);
                System.out.println(o.getClass());
                System.out.println("[" + parameter.getParameterIndex() + "]" + str + parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName() + "-->" + o);
            } else {
                System.out.println("[" + parameter.getParameterIndex() + "]" + str + parameter.getParameterType().getSimpleName() + " " + parameter.getParameterName());
            }
        }
    }

    private static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> urlTemplateVariables = new AntPathMatcher()
                .extractUriTemplateVariables("/test/{id}", "/test/123");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, urlTemplateVariables);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "张三");
        request.setParameter("age", "18");
        return new StandardServletMultipartResolver().resolveMultipart(request);
    }

    static class Controller {
        public void test(
                @RequestParam("name1") String name1, //解决param参数名不匹配
                String name2,
                @RequestParam("age") int age,
                @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1,
                @RequestParam("file") MultipartFile file,
                @PathVariable("id") int id, // /test/{id};
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2,
                HttpServletRequest request, // /name=zhangsan&age=18
                User user2, // /name=zhangsan&age=18
                @RequestBody User user3 // json 请求体中的
        ) {
        }
    }

    static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
