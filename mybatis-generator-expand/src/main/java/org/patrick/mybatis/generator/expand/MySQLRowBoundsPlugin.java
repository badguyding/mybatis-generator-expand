package org.patrick.mybatis.generator.expand;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.patrick.mybatis.generator.expand.type.FullyQualifiedJavaTypes;
import org.patrick.mybatis.generator.expand.type.TypeFullName;
import org.patrick.mybatis.generator.expand.util.GeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 19:05
 */
public class MySQLRowBoundsPlugin extends PluginAdapterEnhance {
    public static final String BASE_EXAMPLE_TYPE_SHORT_NAME = "BaseExample";
    private Map<FullyQualifiedTable, List<XmlElement>> elementsToAdd;

    public MySQLRowBoundsPlugin() {
        this.elementsToAdd = new HashMap();
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        List javaFiles = new ArrayList(1);
        TopLevelClass baseExampleClass = new TopLevelClass(getBaseExampleType());
        baseExampleClass.setVisibility(JavaVisibility.PUBLIC);
        GeneratorUtils.addPropertyToClass(baseExampleClass, FullyQualifiedJavaTypes.LONG, "start", true, true);
        GeneratorUtils.addPropertyToClass(baseExampleClass, FullyQualifiedJavaTypes.LONG, "limit", true, true);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(baseExampleClass, this.context.getJavaModelGeneratorConfiguration().getTargetProject(), this.context.getJavaFormatter());

        javaFiles.add(generatedJavaFile);
        return javaFiles;
    }

    private FullyQualifiedJavaType getBaseExampleType() {
        TypeFullName baseExampleType = new TypeFullName(this.context.getJavaModelGeneratorConfiguration().getTargetPackage(), "BaseExample");

        String contextSearch = this.context.getProperty("renamePlugin.search");
        String contextReplace = this.context.getProperty("renamePlugin.replace");
        baseExampleType.replaceTypeShortName(contextSearch, contextReplace);
        String modelSearch = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.search");
        String modelReplace = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.replace");
        baseExampleType.replaceTypeShortName(modelSearch, modelReplace);
        String modelPrefix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.prefix");
        String modelSuffix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.suffix");
        if (modelPrefix == null) {
            modelPrefix = this.context.getProperty("renamePlugin.prefix");
        }
        if (modelSuffix == null) {
            modelSuffix = this.context.getProperty("renamePlugin.suffix");
        }
        baseExampleType.fixTypeShortName(modelPrefix, modelSuffix);
        return new FullyQualifiedJavaType(baseExampleType.getTypeFullName());
    }
}
