# Mapper Generator Maven插件
## 功能说明
是一款代码生成插件，在使用[Mybatis通用Mapper](https://gitee.com/free/Mapper)时,可以自动生成实体类和xml文件。
主要优点就是方便和项目集成，配置简单,生成的代码直接在项目的指定路径下
## 配置说明
所有的配置项都在下面,别忘了 depencies中的驱动要改成实际使用的
```xml
    <plugin>
        <groupId>org.masuguar.foolish</groupId>
        <artifactId>mapper-generator-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <!--数据库连接信息-->
            <connection>
                <jdbcUrl>jdbc:sqlite::resource:db/show-code.db</jdbcUrl>
                <driverClass>org.sqlite.JDBC</driverClass>
                <username>xxx</username>
                <password>yyy</password>
            </connection>
            <packageInfo>
                <!-- java源码路径,配置为相对路径，相对于pom所在的文件夹 -->
                <sourceRoot>modules\business\src\main\java</sourceRoot>
                <!-- 资源文件路径，也是配置为相对路径 -->
                <resourceRoot>start\src\main\resources</resourceRoot>
                <!-- 将要生成的实体类的所在包 -->
                <javaEntityPackage>org.masuguar.foolish.resposity.entity</javaEntityPackage>
                <!-- 将要生成的Dao接口文件所在包 -->
                <javaMapperPackage>org.masuguar.foolish.resposity.mapper</javaMapperPackage>
                <!-- 所有生成Mapper的基类，全限定名 -->
                <baseMapper>com.masuguar.foolish.base.BaseMapper</baseMapper>
                <!-- 将要生成的xml所在包,为空时，将取值javaMapperPackage -->
                <xmlMapperPackage>mapping</xmlMapperPackage>
            </packageInfo>
            <!-- 所有的表名 -->
            <tables>
                <param>user_info</param>
                <param>city</param>
            </tables>
            <!-- 默认情况下，生成的实体类为表名称的驼峰命名
                如果需要额外设置表的实体类的名称，可以按如下配置
                <表名称>实体类名称</表名称>
             -->
            <entityMap>
                <city>CityInfo</city>
            </entityMap>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>org.xerial</groupId>
                <artifactId>sqlite-jdbc</artifactId>
                <version>3.25.2</version>
            </dependency>
        </dependencies>
    </plugin>
```
