package org.masuguar.foolish;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.masuguar.foolish.base.AbstractGeneratorMojo;
import org.masuguar.foolish.base.Connection;
import org.masuguar.foolish.base.PackageInfo;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * Maven自定义插件的开发比较简单
 * 参考如下的链接即可
 * http://maven.apache.org/guides/plugin/guide-java-plugin-development.html
 * https://blog.csdn.net/u012620150/article/details/78652624
 * @author maxiuguo
 *
 */

@Mojo( name = "generate")
public class MapperGeneratorMojo extends AbstractGeneratorMojo
{
    public void execute() throws MojoExecutionException {
        getLog().info("================== ♤♤♤♤♤♤  MapperGeneratorMojo::generate start  ♤♤♤♤♤♤================== ");
        getLog().info("============================= project basedir = " + projectRootPath );
        if( tables == null || tables.size() == 0 ){
            getLog().error("配置文件中没有读取到表信息，生成失败");
            return;
        }
        for( String table : tables ){
            String entityName = entityMap.getProperty(table);
            generateTableModel(table,entityName);
        }
        getLog().info("================== ♤♤♤♤♤♤  MapperGeneratorMojo::generate end    ♤♤♤♤♤♤================== ");
    }
    public void generateTableModel(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJdbcConnectionConfiguration( connection );
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        setPackageInfo(context,projectRootPath+"/",packageInfo);
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) {
            tableConfiguration.setDomainObjectName(modelName);
        }
        context.addTableConfiguration(tableConfiguration);
        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
            getLog().info("生成Model和Mapper成功，tableName = " + tableName );
        } catch (Exception e) {
            getLog().error("MapperGeneratorMojo::generateTableModel occuer error, tableName = " +tableName ,e);
        }

    }

    private static void setPackageInfo(Context context, String projectRootPath, PackageInfo packageInfo) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(
                "tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers",packageInfo.getBaseMapper() );
        context.addPluginConfiguration(pluginConfiguration);
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(projectRootPath +
                packageInfo.getSourceRoot() );
        javaModelGeneratorConfiguration.setTargetPackage(packageInfo.getJavaEntityPackage());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(projectRootPath +
                packageInfo.getResourceRoot());
        sqlMapGeneratorConfiguration.setTargetPackage(packageInfo.getXmlMapperPackage());
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(projectRootPath +
                packageInfo.getSourceRoot());
        javaClientGeneratorConfiguration.setTargetPackage(packageInfo.getJavaMapperPackage());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }

    private static JDBCConnectionConfiguration getJdbcConnectionConfiguration( Connection connection ) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(connection.getJdbcUrl());
        jdbcConnectionConfiguration.setUserId(connection.getUsername());
        jdbcConnectionConfiguration.setPassword(connection.getPassword());
        jdbcConnectionConfiguration.setDriverClass(connection.getDriverClass());
        return jdbcConnectionConfiguration;
    }
}
