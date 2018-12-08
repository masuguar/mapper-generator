package org.masuguar.foolish.base;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;
import java.util.Properties;

/**
 * 用于自动生成通用Mapper的实体类和xml的插件
 * 这里面抽象出来一个类只是为了把参数抽离出来，没有其他意义
 * @author maxiuguo
 */
public abstract class AbstractGeneratorMojo extends AbstractMojo {
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    @Parameter(defaultValue = "${basedir}")
    protected String projectRootPath;

    @Parameter
    protected Connection connection;

    @Parameter
    protected PackageInfo packageInfo;

    @Parameter
    protected List<String> tables;

    @Parameter
    protected Properties entityMap;




}
