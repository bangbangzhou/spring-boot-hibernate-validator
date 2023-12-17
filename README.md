# 使用Hibernate Validator进行Spring Boot后端数据校验

>在Web应用程序中，后端数据校验是确保接收到有效和合法数据的重要步骤。**Spring Boot通过集成Hibernate Validator**，提供了一种简便且强大的方式来执行后端数据校验。Hibernate Validator是一个实现Java Bean Validation API的开源框架，它使得在Java应用程序中声明性地定义和执行验证规则变得更加容易。

![](https://files.mdnice.com/user/7954/d00a0d46-a4f6-4df1-b7dd-6ed57747a673.png)




## 1. hibernate-validator介绍

**什么是Hibernate Validator？**
>Hibernate Validator是一个用于Java Bean验证的框架，**它实现了Java Bean Validation API（JSR 380）规范**。该框架提供了一组注解和API，用于在应用程序中声明性地定义验证规则，并在运行时执行这些规则。Hibernate Validator广泛用于Spring框架和Java EE应用中，用于对数据进行验证和约束。

### 什么要数据校验？

**早期的网站，用户输入一个邮箱地址，需要将邮箱地址发送到服务端，服务端进行校验，校验成功后，给前端一个响应**

有了JavaScript后，校验工作可以放在前端去执行。**那么为什么还需要服务端校验呢**？ 因为前端传来的数据不可信。前端很容易获取到后端的接口，如果有人直接调用接口，就可能会出现非法数据，所以服务端也要数据校验。

总的来说：

- 前端校验：主要是提高用户体验
- 后端校验：主要是保证数据安全可靠

校验参数基本上是一个体力活，而且冗余代码繁多，也影响代码的可读性，我们需要一个比较优雅的方式来解决这个问题。Hibernate Validator 框架刚好解决了这个问题，可以以很优雅的方式实现参数的校验，让业务代码和校验逻辑分开,不再编写重复的校验逻辑。


**hibernate-validator优势**：

- 验证逻辑与业务逻辑之间进行了分离，降低了程序耦合度
- 统一且规范的验证方式，无需你再次编写重复的验证代码
- 你将更专注于你的业务，将这些繁琐的事情统统丢在一边


**hibernate-validator的maven坐标**：
```xml
<dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-validator</artifactId>
      <version>6.2.5.Final</version>
</dependency>
```

## 2.hibernate-validator常用注解
hibernate-validator提供的校验方式为在类的属性上加入相应的注解来达到校验的目的。hibernate-validator提供的用于校验的注解如下：

**@NotNull**:

- 作用： 检查值是否不为null。
- 示例： 
```java
 @NotNull(message = "用户名不能为空")
private String username;
```

**@NotBlank**:

- 作用： 检查值是否不为null且长度大于0。
示例：
```java

@NotBlank(message = "用户名不能为 blank") 
private String username;
```

**@Size**:

- 作用： 检查值的长度是否在指定范围内。
- 示例：
```java
 @Size(min = 6, max = 20, message = "密码长度应在6到20") 
 private String password;
```

**@Min**:

- 作用： 检查数字是否大于或等于指定的最小值。
示例： 
```java
@Min(value = 18, message = "年龄最小为18") 
private int age;
```

**@Max**:

- 作用： 检查数字是否小于或等于指定的最大值。
示例：
```java
 @Max(value = 100, message = "年龄最大为80") 
 private int age;

```

**@Email**:

- 作用： 检查字符串是否符合Email地址的格式。
示例：
```java
@Email(message="邮箱格式不正确")
private String email;

```

**@Pattern**:

- 作用： **使用正则表达式检查字符串是否匹配指定的模式（（。
示例： 
```java

@Pattern(regexp = "[a-zA-Z0-9]+", message = "只允许使用字母和数字字符") 
private String username;
```

**@NotEmpty**:

- 作用： 检查值是否不为null且不为空（对于字符串、集合、数组等）。
示例：
```java
 @NotEmpty(message = "不能为空") 
 private List<String> names;

```
**@Range**:

- 作用： 检查数字是否在指定范围内。
示例： 
```java
@Range(min = 1, max = 10, message = "数值必须在1和10之间") 
private int value;
```

**@CreditCardNumber**:

- 作用： 检查字符串是否符合信用卡号的格式。
示例： 
```java
@CreditCardNumber(message = "无效的信用卡号") 
private String creditCardNumber;

```

**@URL**:
- 作用： 检查字符串是否符合URL的格式。
示例： 
```java
@URL(message = "无效URL") 
private String website;
```


## 3. hibernate-validator入门案例
完整项目结构如下:

![](https://files.mdnice.com/user/7954/6de5810d-6a4e-481a-8d4b-3dc89d8237c0.png)
代码实现步骤：

### 【步骤一】:创建maven工程`spring-boot-hibernate-validator`并配置pom.xml文件
在Spring Boot中，Hibernate Validator可以轻松集成。**Spring Boot的spring-boot-starter-validation启动器已经包含了Hibernate Validator的依赖项，因此我们只需添加相应的注解和配置即可开始使用**

![](https://files.mdnice.com/user/7954/235ebea1-0f14-42a5-ab1f-5c855166ce27.png)


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>


    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.7.15</version>
    </parent>


    <groupId>com.zbbmeta</groupId>
    <artifactId>spring-boot-hibernate-validator</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 其他依赖 ... -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.20</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

### 【步骤二】:创建application.ym
```yml
server:
  port: 8888

```
### 【步骤三】:创建启动类
```java
@SpringBootApplication
public class HibernateValidatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(HibernateValidatorApplication.class,args);
    }
}

```

### 【步骤四】:使用Hibernate Validator进行数据校验
- 编写实体类
>首先，我们需要定义一个包含验证规则的实体类。在这里，我们创建一个简单的`Student`实体类，包含id、用户名、年龄和邮箱字段。
```java


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.constraints.*;

/**
 * @author springboot葵花宝典
 * @description: TODO
 */
@Data
public class Student {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    @Length(max = 50, message = "用户名长度不能超过50")
    private String username;

    @Max(value = 80,message = "年龄最大为80")
    @Min(value = 18,message = "年龄最小为18")
    private int age;


    @Email(message="邮箱格式不正确")
    private String email;
}

```

- 在Controller中使用Hibernate Validator验证
在Controller中使用@Valid注解来触发数据校验。
```java
@RestController
@RequestMapping("/student")
public class StudentController {

    //简单数据类型校验
    @RequestMapping("/delete")
    public String delete(@NotBlank(message = "id不能为空") String id){
        System.out.println("delete..." + id);
        return "OK";
    }

    //对象属性校验
    @RequestMapping("/save")
    public String save(@Validated Student student){
        System.out.println("save..." + student);
        return "OK";
    }
}

```

**启动项目，访问地址：http://localhost:8888/student/save，通过控制台输出可以看到已经可以进行数据校验了，如下**：

![](https://files.mdnice.com/user/7954/f8e3971b-a29d-4ac1-bac1-6db220496fcb.png)

<font color="red" size="4">通过上图发现，虽然进行校验，可是好像没有安装message进行信息报错，为了能够在页面友好的显示数据校验结果，可以通过全局异常处理来解决，创建全局异常处理类</font>

### 【步骤五】:创建全局异常处理类

```java


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
/**
 * @author springboot葵花宝典
 * @description: TODO
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class,BindException.class})
    public String validateException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        String msg = null;
        if(ex instanceof ConstraintViolationException){
            ConstraintViolationException constraintViolationException =
                    (ConstraintViolationException)ex;
            Set<ConstraintViolation<?>> violations =
                    constraintViolationException.getConstraintViolations();
            ConstraintViolation<?> next = violations.iterator().next();
            msg = next.getMessage();
        }else if(ex instanceof BindException){
            BindException bindException = (BindException)ex;
            msg = bindException.getBindingResult().getFieldError().getDefaultMessage();
        }
        return msg;
    }
}
```
**启动项目，访问地址：http://localhost:8888/student/save，通过控制台输出可以看到已经可以进行数据校验了，如下**：

![](https://files.mdnice.com/user/7954/a441051f-db14-45a1-8294-69600417a0b8.png)

**通过控制台的输出可以看到，校验框架将我们的多个属性都进行了数据校验（默认行为）**，<font color="red" size="4">如果我们希望只要有一个属性校验失败就直接返回提示信息，后面的属性不再进行校验了该如何实现呢？</font>

![](https://files.mdnice.com/user/7954/34c552f8-22a4-4bbf-b490-a4e97a985493.png)
### 【步骤六】:创建ValidatorConfiguration类，指定校验时使用快速失败返回模式
```java


import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author springboot葵花宝典
 * @description: TODO
 */
@Configuration
public class ValidatorConfiguration {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory =
                Validation.byProvider(HibernateValidator.class)
                        .configure()
                        //快速失败返回模式
                        .addProperty("hibernate.validator.fail_fast", "true")
                        .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * 开启快速返回
     * 如果参数校验有异常，直接抛异常，不会进入到 controller，使用全局异常拦截进行拦截
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor =
                new MethodValidationPostProcessor();
        /**设置validator模式为快速失败返回*/
        postProcessor.setValidator(validator());
        return postProcessor;
    }
}

```
**启动项目，访问地址：http://localhost:8888/student/save，通过控制台输出可以看到已经可以进行数据校验了，如下**：

![](https://files.mdnice.com/user/7954/a441051f-db14-45a1-8294-69600417a0b8.png)

<font color="red" size="4">通过控制台的输出可以看到，虽然我们输入的数据有多个都不符合校验规则，但是只有一个校验失败异常信息，这说明已经开启了快速失败返回模式。</font>


![](https://files.mdnice.com/user/7954/7c62c4e1-f7bd-4c77-b975-8421e45970d8.png)

![](https://files.mdnice.com/user/7954/d00a0d46-a4f6-4df1-b7dd-6ed57747a673.png)