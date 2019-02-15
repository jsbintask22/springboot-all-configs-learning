---
title: 史上最全springboot配置，踩坑，注解，使用手册（持续更新中）
date: 2019-02-15 11:11
updated: 2019-02-15 11:11
tag: 
  - springboot
sourceId: springboot-all-configs
categories:
  - recommend
---
![cover](http://qiniu.jsbintask.cn/flux_config_by_skay20_d4ijq3x-fullview.jpg-blog_960_440.jpg)
<!-- more -->

## springboot
春节在家长了几斤肉，今天开始减肥了（工作了）。19年的第一篇文章，本篇文章将覆盖springboot所有配置（本人实际工作中所遇到问题以及学习总结），后续将会持续更新！话不多说，直接开始。

## 详细配置
注意：本演示全部基于**springboot**最新版本，现在是**`2.1.2.RELEASE`**，并且所有配置将全部使用yaml格式！

### 如何配置springboot监听端口？
1. 通过application.yml指定。（推荐）
```yaml
server:
  port: 8080
```
注意：如果使用`server.port=0`，将使用一个随机端口，可以控制台查看。
2. 指定jvm参数（启动多个项目调试时推荐使用）
idea中：**Edit Configurations -> VM options**
运行jar命令： `java -Dserver.port=8080 app.jar`
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo1.png)
3. 运行`java -jar`命令时指定
如： `java -jar app.jar --server.port=8080`
4. 通过代码指定：
```java
@Configuration
public class TomcatConfig {

    @Bean
    @Autowired
    public TomcatWebServerFactoryCustomizer factoryCustomizerAutoConfiguration(Environment environment, ServerProperties serverProperties) {
            serverProperties.setPort(8888);
        return new TomcatWebServerFactoryCustomizer(environment, serverProperties);
    }
}
```

### springboot中如何添加filter
1. 继承`Filter`并且将该类作为一个bean加入spring容器，使用`@Order`设置filter顺序
```java
@Component
@Order(1000)
public class FirstFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //to do something.
        
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```
注意！使用这种方式注册的filter将拦截所有请求，无法指定拦截url。
2. 使用`FilterRegistrationBean`类手动注册一个filter（推荐）
```java
public class SecondFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //to do something.

        System.out.println("SecondFilter.doFilter");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```
```java
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<SecondFilter> secondFilterFilterRegistrationBean() {
        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecondFilter());
        registrationBean.setOrder(10001);

        registrationBean.addUrlPatterns("/**");
        
        return registrationBean;
    }
}
```
3. 开启`@WebFilter`扫描。
在启动类上加上`@ServletComponentScan`注解，定义filter如下：
```java
@WebFilter(filterName = "ThridFilter", urlPatterns = "/**")
@Order(1002)
public class ThridFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("ThridFilter.doFilter");
        chain.doFilter(req, resp);
    }

}
```
采坑记录：该方式只在内置web容器下有效（打jar包），如果使用war包，将失效，有兴趣的可以看一看`@ServletComponentScan`注解说明。

### springboot如何添加servlet
1. 同上类似，使用`ServletRegistrationBean`
```java
@Bean
public ServletRegistrationBean servletRegistrationBean(){
    return new ServletRegistrationBean(new FooServlet(),"/foo/*");
}
```

### 如何配置jpa，数据源？
引入jpa依赖，application.yml中添加数据源信息
```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: jason
    url:  jdbc:mysql://localhost:3306/springboot-all-configs?useSSL=false

  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
```
采坑记录1：如果引入jpa但是不定义数据源将会启动失败
采坑记录2：最新版springboot默认mysql驱动为8.1，自定义参数较多，启动可能造成数据源初始化失败，建议降级mysql驱动版本，在pom文件中定义：
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo3.png)
```xml
<mysql.version>5.1.47</mysql.version>
```

### 生war包运行，部署在weblogic获取其它servlet容器中
1. 将tomcat从maven依赖中去掉，启动类继承`SpringBootServletInitializer`
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```
```java
@SpringBootApplication
@ServletComponentScan
public class SpringbootAllConfigsApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAllConfigsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootApplication.class);
    }
}
```
最后将pom文件打包方式修改为war：
`<packaging>war</packaging>`
使用idea打包：
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo2.png)

### 使用springboot默认生成的项目mvnw.cmd有什么用
1. 用作生产环境（linux）上没有maven环境自动下载maven，打包依赖使用
2. `没卵用，直接删除` 

### 如何设置项目的log level
1. 在`application.yml`中指定：
```yaml
logging:
  level:
    org:
      springframework:
        web: debug
```
2. 如果使用logback，添加一个`append`:
`<logger name="cn.jsbintask.springbootallconfigs.mapper" level="DEBUG"/>`

### springboot中访问配置文件中的配置？
1. 使用`@Value`注解
```java
@Value("${server.port}")
private int port;
```
2. 注入`Environment`实例，然后获取值
```java
@RestController
@RequestMapping
public class PropertiesController {
    @Autowired
    private Environment environment;
    
    @GetMapping("/path")
    public String getPath() {
        return environment.getProperty("server.port");
    }
}
```
3. 使用`ConfigurationProperties`注解，定义多个属性值
.yml:
```yaml
cn:
  jsbintask:
    name: jason
    age: 22
```
.Properties class:
```java
@ConfigurationProperties(prefix = "cn.jsbintask")
@EnableConfigurationProperties
@Component
@Data
public class JsbintaskProperties {
    private String name;
    private int age;
}
```
使用：
```java
@Autowired
private JsbintaskProperties jsbintaskProperties;
```

### 如何指定springboot启动类
1. 使用idea指定main class
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo4.png)
2. 在pom文件中指定启动类
```xml
<properties>
    <!-- The main class to start by executing java -jar -->
    <start-class>cn.jsbintask.springbootallconfigs.SpringbootAllConfigsApplication</start-class>
</properties>
```
3. 在springboot maven插件中指定：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>cn.jsbintask.springbootallconfigs</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### application.yml和bootstrap.yml的区别？
1. bootstrap配置文件先于application.yml配置加载，bootstrap配置属性一般只在`spring cloud`获取配置服务时使用，所以一般如果不使用`spring cloud`的话，优先使用`application.yml`.

### 如何定义rest服务？
1. 使用`@ResponseBody`注解配置**@RequestMapping**注解使用
2. 使用新注解`@RestController`配置**@XXXMaping(GetMapping)**使用。
```java
@RestController
@RequestMapping
public class HelloController {
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
```

### springboot中如何处理异常？
1. 使用`RestControllerAdvice`新注解以及`@ExceptionHanlder`处理异常
```java
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String hanld(Exception e) {
        return "error";
    }
}
```

### springboot使用restful服务如何自定错误白页？
当出现错误的时候，springboot默认使用一个自定义的错误页面，当使用rest服务时，我们希望自定义返回数据类型。
1. 实现ErrorController接口：
```java
@RestController
@RequestMapping
public class CustomErrorController implements ErrorController {
    public static final String ERROR_PATH = "/error";

    @RequestMapping(path = ERROR_PATH)
    public String error() {
        return "custom error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
```

### 如何定义静态资源访问映射（图片）
1. 开启@EnableWebMvc，继承WebMvcConfigurer类，添加映射
```java
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }
}
```
在static文件夹下新建images文件夹，添加一张图片，访问 `/images/gril.jpg`：
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo5.png)
采坑记录：上述映射和定位必须一一对应，如改成 **registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");**
url则需使用 /static/images/gril.jpg，其它静态资源同样使用此方法映射。

### springboot项目启动后如何自动运行指定代码（如初始化）？
1. 在main方法后面运行指定service：
```java
public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootAllConfigsApplication.class, args);

    /* way one to init */
    HelloController bean = applicationContext.getBean(HelloController.class);
    bean.init();
}
```
2. 使用`@EventListener`监听启动事件
```java
@Component
public class AppReadyListener {
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        System.out.println("AppReadyListener.init");
    }
}
```
查看控制台如下：
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo6.png)
3. 使用`@PostConstruct`注解
```java
@PostConstruct
public void postConstruct() {
    System.out.println("AppReadyListener.postConstruct");
}
```
4. 实现`ApplicationRunner`接口
```java
@Component
public class AppRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("AppRunner.run");
    }
}
```
采坑记录：方法1在`war`环境中不起作用，方法3可能过早执行（这个bean初始化后就执行），方法4，2为最佳实践

### 如何自定义模板引擎（freemarker）
1. 添加依赖，修改配置文件：
```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```
```yaml
spring:
  freemarker:
    template-loader-path: classpath:/templates/
    check-template-location: true
    charset: UTF-8
    enabled: true
    suffix: .html
```
接着添加hello.html在templates文件夹下：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>templates</title>
</head>
<body>
<h2>hello from jsbintask blog.</h2>
</body>
</html>
```
添加一个controller，映射到该模板
```java
@Controller
@RequestMapping("/templates")
public class TemplateController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
```
启动项目，访问`/templates/hello`：
![springboot](https://raw.githubusercontent.com/jsbintask22/static/master/springboot/demo7.png)

### springboot如何自定义首页面，如何自定义视图
1. 同上定义模板引擎
2. 实现**WebMvcConfigurer**接口，添加视图映射
```java
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //前面为访问路径( '/'即为主页面)，后面为视图模板位置（如本例配置的为 templates下）。
        registry.addViewController("/").setViewName("home");
    }
}
```

### 配置文件中如何添加数组属性?
1. 使用'-'每行一个属性
```yaml
key:
  - value1
  - value2
```
2. 使用','号隔开每个值
```yaml
key: value1, value2
```

### 使用jpa作数据查询时，发现实体类的id不能序列化？
这是springboot默认处理了，id不作序列化处理，可以添加如下配置：
```java
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
    }
}
```
采坑记录：最新版springboot已经默认作了此配置。

### springboot修改默认的favicon图标
1. 同上，继承WebMvcConfigure配置类，添加如下配置：
```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");

    registry.addResourceHandler("/favicon.ico").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/images/favicon.jpg");
}
```

### springboot的api如何编写单元测试？
建立在已有的springboot项目上，例如helloController，测试 `/hello`，编写测试类：
```java
@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServletContext servletContext;

    @Test
    public void test1() throws Exception{
        MvcResult end = mockMvc.perform(requestBuilder("/hello"))
                .andExpect(mvcResult -> {
                    if (mvcResult.getResponse().getStatus() != 200) {
                        throw new RuntimeException("failed.");
                    }
                })
                .andExpect(result -> {
                    if (!result.getResponse().getContentType().contains("json")) {
                        throw new RuntimeException("failed");
                    }
                }).andReturn();

        System.out.println(end);
    }

    private RequestBuilder requestBuilder(String uri) {
        return MockMvcRequestBuilders.get(URI.create(uri)).accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8");
    }
}
```
其中api看名字便知。

### springboot如何为api配置安全访问？
1. 参考系列文章：[springsecurity整合springboot从入门到源码解析](https://jsbintask.cn/tags/springsecurity/)

### springboot整合各种消息队列？
1. 参考系列文章：[springboot整合各种消息队列](https://jsbintask.cn/tags/jms/)（持续更新中）

### 未完待续
今天暂时更新到这，此文章会一直更新！ 或者你有感兴趣的配置，欢迎留言！




## 总结
未完待续！
例子源码：[https://github.com/jsbintask22/springboot-all-configs-learning.git](https://github.com/jsbintask22/springboot-all-configs-learning.git)，欢迎fork，star学习修改。
本文原创地址：[https://jsbintask.cn/2019/02/15/springboot/springboot-all-configs/](https://jsbintask.cn/2019/02/15/springboot/springboot-all-configs/)，转载请注明出处。
如果你觉得本文对你有用，欢迎关注，分享。这里只有干货！