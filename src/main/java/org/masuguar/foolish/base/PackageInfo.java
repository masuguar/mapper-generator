package org.masuguar.foolish.base;

import org.codehaus.plexus.util.StringUtils;

public class PackageInfo {

    private String sourceRoot;

    private String resourceRoot;

    private String javaEntityPackage;

    private String javaMapperPackage;

    private String baseMapper;

    public String getBaseMapper() {
        return baseMapper;
    }

    public void setBaseMapper(String baseMapper) {
        this.baseMapper = baseMapper;
    }

    private String xmlMapperPackage;

    public String getJavaEntityPackage() {
        return javaEntityPackage;
    }

    public void setJavaEntityPackage(String javaEntityPackage) {
        this.javaEntityPackage = javaEntityPackage;
    }


    public String getSourceRoot() {
        return sourceRoot;
    }

    public void setSourceRoot(String sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public String getResourceRoot() {
        return resourceRoot;
    }

    public void setResourceRoot(String resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    public String getJavaMapperPackage() {
        return javaMapperPackage;
    }

    public void setJavaMapperPackage(String javaMapperPackage) {
        this.javaMapperPackage = javaMapperPackage;
    }

    public String getXmlMapperPackage() {
        if( StringUtils.isEmpty(xmlMapperPackage) ){
            return javaMapperPackage;
        }
        return xmlMapperPackage;
    }

    public void setXmlMapperPackage(String xmlMapperPackage) {
        if(StringUtils.isEmpty(xmlMapperPackage) ){
            xmlMapperPackage = this.javaMapperPackage;
        }
        this.xmlMapperPackage = xmlMapperPackage;
    }
}
