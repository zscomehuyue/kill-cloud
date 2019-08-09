
## Spring-boot打成可执行jar的方法

+ 项目继承 **spring-boot-starter-parent**
```
  <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
  </parent> 
```

+ build
  增加如下内容，并增加mainClass标签，增加执行主类：**example.EurekaServer**
``` 
   <plugin>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-maven-plugin</artifactId>
       <configuration>
           <mainClass>com.el.example.EurekaServer</mainClass>
       </configuration>
       <executions>
           <execution>
             <goals>
                <goal>repackage</goal>
             </goals>
           </execution>
       </executions>
   </plugin>
   
```
+ 执行mavn命令 **spring-boot:repackage**
```
 mvn clean package  spring-boot:repackage
 
```
## gradle create pom 
+ 
+ 
+ 
+ 
+ 
+ 
+ 
+ 

