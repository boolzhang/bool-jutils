package com.bool.jutils.mbg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

public class MbCommentGenerator implements CommentGenerator {


    /** 配置文件. */
    private Properties properties;

    /** 父类的时间. */
    private boolean suppressDate;

    /** 父类的所有注释. */
    private boolean suppressAllComments;

    /** 添加表格的注释。如果suppressAllComments为true，则忽略此选项*/
    private boolean addRemarkComments;

    /** 时间格式 **/
    private SimpleDateFormat dateFormat;

    /**
     * 构造方法
     */
    public MbCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    /*
     * Java文件加注释
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("* "+compilationUnit.getType().getShortName()+".java");
        compilationUnit.addFileCommentLine("* Copyright(C) 2018-2025 深圳前海易联科技有限公司");
        compilationUnit.addFileCommentLine("* @Author BoolZhang");
        compilationUnit.addFileCommentLine("* @Date "+sdf.format(new Date())+"");
        compilationUnit.addFileCommentLine("*/");



    }

    /**
     * Mybatis的Mapper.xml文件里面的注释。
     *
     * @param xmlElement
     *            the xml element
     */
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }
    }

    /*
     * 调用此方法为根元素的第一个子节点添加注释。 此方法可用于添加
     * 一般文件注释（如版权声明）。 但是，请注意，XML文件合并功能不会处理
     * 这个注释。 如果反复运行生成器，则只保留初始运行的注释。
     */
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


        System.out.println("+++++++++++++++++++字段类型："+field.getType().getFullyQualifiedName());
        //如果是时间类型的，增加注解
        if(field.getType().equals(FullyQualifiedJavaType.getDateInstance())){
            field.addJavaDocLine("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
        }

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    /*
     * 从properties配置文件中添加此实例的属性
     *
     */
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    /**
     *  此方法用于添加自定义javadoc标签。 如果您不希望包含Javadoc标签，您可能不会执行任何操作 -
     *  但是，如果不包含Javadoc标签，那么eclipse插件的Java合并功能就会中断。
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * 获取时间
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    /*
     * 类注释
     */
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * @Title "+introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @Description "+introspectedTable.getRemarks());
        innerClass.addJavaDocLine(" * @Author BoolZhang");
        innerClass.addJavaDocLine(" * @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        innerClass.addJavaDocLine(" */");


    }

    /*
     * 实体类注释
     *
     *  为模型类添加注释。 Java代码合并应该
     *  通知不要删除整个class，万一有任何class
     *  已经做出了改变。 所以这个方法会永远使用“不要删除”注释。
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }

        topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
    }

    /*
     * 枚举注释
     */
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum.addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$
        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        addJavadocTag(innerEnum, false);
        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /*
     * 字段注释
     */
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {

        if (suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * "+introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");

        //增加日期相关的注解
        if(field.getType().getFullyQualifiedName().equals(FullyQualifiedJavaType.getDateInstance().getFullyQualifiedName())){
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }
    }

    /*
     * 字段注释
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

    /*
     * 普通方法注释,mapper接口中方法
     */
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        sb.append(" * ");
        if (method.isConstructor()) {
            sb.append(" 构造查询条件");
        }
        String method_name = method.getName();
        if ("setOrderByClause".equals(method_name)) {
            sb.append(" 设置排序字段");
        } else if ("setDistinct".equals(method_name)) {
            sb.append(" 设置过滤重复数据");
        } else if ("getOredCriteria".equals(method_name)) {
            sb.append(" 获取当前的查询条件实例");
        } else if ("isDistinct".equals(method_name)) {
            sb.append(" 是否过滤重复数据");
        } else if ("getOrderByClause".equals(method_name)) {
            sb.append(" 获取排序字段");
        } else if ("createCriteria".equals(method_name)) {
            sb.append(" 创建一个查询条件");
        } else if ("createCriteriaInternal".equals(method_name)) {
            sb.append(" 内部构建查询条件对象");
        } else if ("clear".equals(method_name)) {
            sb.append(" 清除查询条件");
        } else if ("countByExample".equals(method_name)) {
            sb.append(" 根据指定的条件获取数据库记录数");
        } else if ("deleteByExample".equals(method_name)) {
            sb.append(" 根据指定的条件删除数据库符合条件的记录");
        } else if ("deleteByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            sb.append(" 新写入数据库记录");
        } else if ("insertSelective".equals(method_name)) {
            sb.append(" 动态字段,写入数据库记录");
        } else if ("selectByExample".equals(method_name)) {
            sb.append(" 根据指定的条件查询符合条件的数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            sb.append(" 根据指定主键获取一条数据库记录");
        } else if ("updateByExampleSelective".equals(method_name)) {
            sb.append(" 动态根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByExample".equals(method_name)) {
            sb.append(" 根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKeySelective".equals(method_name)) {
            sb.append(" 动态字段,根据主键来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键来更新符合条件的数据库记录");
        }
        method.addJavaDocLine(sb.toString());

        final List<Parameter> parameterList = method.getParameters();
        if (!parameterList.isEmpty()) {
            method.addJavaDocLine(" *");
            if ("or".equals(method_name)) {
                sb.append(" 增加或者的查询条件,用于构建或者查询");
            }
        } else {
            if ("or".equals(method_name)) {
                sb.append(" 创建一个新的或者查询条件");
            }
        }
        String paramterName;
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param "); //$NON-NLS-1$
            paramterName = parameter.getName();
            sb.append(paramterName);
            if ("orderByClause".equals(paramterName)) {
                sb.append(" 排序字段"); //$NON-NLS-1$
            } else if ("distinct".equals(paramterName)) {
                sb.append(" 是否过滤重复数据");
            } else if ("criteria".equals(paramterName)) {
                sb.append(" 过滤条件实例");
            }
            method.addJavaDocLine(sb.toString());
        }

        method.addJavaDocLine(" */");

    }


    /**
     * 类注释
     */
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * @Title "+introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @Description "+introspectedTable.getRemarks());
        innerClass.addJavaDocLine(" * @Author BoolZhang");
        innerClass.addJavaDocLine(" * @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        innerClass.addJavaDocLine(" */");

    }
}