package com.bool.jutils.mbg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 自己构建的MyBatis构建生成器，用于生成Mybatis的相关增删查改，并增加了一些个性化的生成
 * @author bool 
 * @date 2018/9/10 18:23
 */
public class CmtGenerator implements CommentGenerator {


    private Properties properties;
    private boolean suppressAllComments;
    private boolean addRemarkComments;

    /**
     * 是否支持Swagger注解
     */
    private boolean swaggerSupport;

    /**
     * 是否对日期字段进行日期格式化
     */
    private boolean springDateFormat;

    /**
     * 默认构造方法
     */
    public CmtGenerator() {
        this.properties = new Properties();
        this.suppressAllComments = false;
        this.addRemarkComments = false;
        this.swaggerSupport = false;
        this.springDateFormat = false;
    }

    /**
     * 初始化配置方法
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {

        //初始化配置信息
        this.properties.putAll(properties);
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
        this.swaggerSupport = StringUtility.isTrue(properties.getProperty("swaggerSupport"));
        this.springDateFormat = StringUtility.isTrue(properties.getProperty("springDateFormat"));
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("* File " + compilationUnit.getType().getShortName() + ".java was generate with JUtils.");
        compilationUnit.addFileCommentLine("* @Author DAV");
        compilationUnit.addFileCommentLine("* @Date " + this.currentDate());
        compilationUnit.addFileCommentLine("*/");
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }


    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

        if (this.suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * @Title " + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @Description " + introspectedTable.getRemarks());
        innerClass.addJavaDocLine(" * @Author DAV");
        innerClass.addJavaDocLine(" * @Date " + this.currentDate());
        innerClass.addJavaDocLine(" */");
    }


    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        if ((this.suppressAllComments) || (!this.addRemarkComments)) {
            return;
        }

        //增加注释
        this.addClassComment(topLevelClass, introspectedTable);


        //日期格式化，导入SPRING日期格式化包
        if(springDateFormat){
            topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
        }
        //增加swagger注解支持
        if(swaggerSupport) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        }

    }


    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }


    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        if (this.suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");

        //增加时间格式化
        if(springDateFormat && field.getType().getFullyQualifiedName().equals(FullyQualifiedJavaType.getDateInstance().getFullyQualifiedName())){
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }

        //增加swaggerSupport支持
        if(swaggerSupport) {
            field.addAnnotation("@ApiModelProperty(\""+introspectedColumn.getRemarks()+"\")");
        }
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

        if (!this.suppressAllComments) {
            return;
        }

        //增加注释
        this.addClassComment(innerClass, introspectedTable);
    }

    /**
     * 获取当前日期
     * @return
     */
    private String currentDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}