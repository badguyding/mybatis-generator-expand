package org.patrick.mybatis.generator.expand;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.patrick.mybatis.generator.expand.type.FileFullName;
import org.patrick.mybatis.generator.expand.type.TypeFullName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 16:16
 */
public class SeparateUserCodePlugin extends PluginAdapterEnhance {
    public static final String TARGET_PACKAGE_PROPERTY_NAME = "separateUserCodePlugin.targetPackage";
    public static final String SEARCH_PROPERTY_NAME = "separateUserCodePlugin.search";
    public static final String REPLACE_PROPERTY_NAME = "separateUserCodePlugin.replace";
    public static final String PREFIX_PROPERTY_NAME = "separateUserCodePlugin.prefix";
    public static final String SUFFIX_PROPERTY_NAME = "separateUserCodePlugin.suffix";

    private List<GeneratedJavaFile> generatedJavaFileList = new ArrayList();
    private List<GeneratedXmlFile> generatedXmlFileList = new ArrayList();

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
        String userCodePackage = this.context.getJavaClientGeneratorConfiguration().getProperty(TARGET_PACKAGE_PROPERTY_NAME);
        if (userCodePackage == null) {
            userCodePackage = this.context.getJavaClientGeneratorConfiguration().getTargetPackage();
        }
        String userCodeSearch = this.context.getJavaClientGeneratorConfiguration().getProperty(SEARCH_PROPERTY_NAME);
        String userCodeReplace = this.context.getJavaClientGeneratorConfiguration().getProperty(REPLACE_PROPERTY_NAME);
        String userCodePrefix = this.context.getJavaClientGeneratorConfiguration().getProperty(PREFIX_PROPERTY_NAME);
        String userCodeSuffix = this.context.getJavaClientGeneratorConfiguration().getProperty(SUFFIX_PROPERTY_NAME);

        TypeFullName userInterfaceTypeFullName = new TypeFullName(userCodePackage, interfaze.getType().getShortName());

        userInterfaceTypeFullName.replaceTypeShortName(userCodeSearch, userCodeReplace).fixTypeShortName(userCodePrefix, userCodeSuffix);
        String userCodeTargetProject = this.context.getJavaClientGeneratorConfiguration().getTargetProject();
        File userInterfaceFile = new File(userCodeTargetProject + "/" + new FileFullName(userInterfaceTypeFullName, "java").getFileFullName());
        if (!userInterfaceFile.exists()) {
            Interface userInterface = new Interface(userInterfaceTypeFullName.getTypeFullName());
            userInterface.addImportedType(interfaze.getType());
            userInterface.addSuperInterface(interfaze.getType());
            userInterface.setVisibility(JavaVisibility.PUBLIC);
            this.generatedJavaFileList.add(new GeneratedJavaFile(userInterface, userCodeTargetProject, this.context.getJavaFormatter()));
        }
        String userMapperPackage = this.context.getSqlMapGeneratorConfiguration().getProperty(TARGET_PACKAGE_PROPERTY_NAME);
        if (userMapperPackage == null) {
            userMapperPackage = this.context.getSqlMapGeneratorConfiguration().getTargetPackage();
        }
        String userMapperSearch = this.context.getSqlMapGeneratorConfiguration().getProperty(SEARCH_PROPERTY_NAME);
        String userMapperReplace = this.context.getSqlMapGeneratorConfiguration().getProperty(REPLACE_PROPERTY_NAME);
        String userMapperPrefix = this.context.getSqlMapGeneratorConfiguration().getProperty(PREFIX_PROPERTY_NAME);
        String userMapperSuffix = this.context.getSqlMapGeneratorConfiguration().getProperty(SUFFIX_PROPERTY_NAME);
        TypeFullName userMapperTypeFullName = new TypeFullName(userMapperPackage, userInterfaceTypeFullName.getTypeShortName());
        userMapperTypeFullName.replaceTypeShortName(userMapperSearch, userMapperReplace).fixTypeShortName(userMapperPrefix, userMapperSuffix);
        String userMapperTargetProject = this.context.getSqlMapGeneratorConfiguration().getTargetProject();
        File userMapperFile = new File(userMapperTargetProject + "/" + new FileFullName(userMapperTypeFullName, "xml").getFileFullName());
        if (!userMapperFile.exists()) {
            Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            XmlElement rootElement = new XmlElement("mapper");
            rootElement.addAttribute(new Attribute("namespace", userInterfaceTypeFullName.getTypeFullName()));
            document.setRootElement(rootElement);
            this.generatedXmlFileList.add(new GeneratedXmlFile(document, new FileFullName(userMapperTypeFullName, "xml").getFileShortName(), userMapperPackage, userMapperTargetProject, false, this.context.getXmlFormatter()));
        }
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return this.generatedJavaFileList;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        return this.generatedXmlFileList;
    }
}
