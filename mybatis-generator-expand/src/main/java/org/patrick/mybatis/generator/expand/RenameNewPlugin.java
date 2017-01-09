package org.patrick.mybatis.generator.expand;

import org.mybatis.generator.api.IntrospectedTable;
import org.patrick.mybatis.generator.expand.type.FileFullName;
import org.patrick.mybatis.generator.expand.type.TypeFullName;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 18:27
 */
public class RenameNewPlugin extends PluginAdapterEnhance {
    public static final String SEARCH_PROPERTY_NAME = "renamePlugin.search";
    public static final String REPLACE_PROPERTY_NAME = "renamePlugin.replace";
    public static final String PREFIX_PROPERTY_NAME = "renamePlugin.prefix";
    public static final String SUFFIX_PROPERTY_NAME = "renamePlugin.suffix";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String contextSearch = this.context.getProperty(SEARCH_PROPERTY_NAME);
        String contextReplace = this.context.getProperty(REPLACE_PROPERTY_NAME);
        if ((contextSearch != null) && (contextReplace != null)) {
            replaceContext(introspectedTable, contextSearch, contextReplace);
        }

        String modelSearch = this.context.getJavaModelGeneratorConfiguration().getProperty(SEARCH_PROPERTY_NAME);
        String modelReplace = this.context.getJavaModelGeneratorConfiguration().getProperty(REPLACE_PROPERTY_NAME);
        if ((modelSearch != null) && (modelReplace != null)) {
            replaceModel(introspectedTable, modelSearch, modelReplace);
        }

        String sqlMapSearch = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.search");
        String sqlMapReplace = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.replace");
        if ((sqlMapSearch != null) && (sqlMapReplace != null)) {
            replaceSqlMap(introspectedTable, sqlMapSearch, sqlMapReplace);
        }

        String clientSearch = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.search");
        String clientReplace = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.replace");
        if ((clientSearch != null) && (clientReplace != null)) {
            replaceClient(introspectedTable, clientSearch, clientReplace);
        }

        String contextPrefix = this.context.getProperty(PREFIX_PROPERTY_NAME);
        String contextSuffix = this.context.getProperty(SUFFIX_PROPERTY_NAME);
        String modelPrefix = this.context.getJavaModelGeneratorConfiguration().getProperty(PREFIX_PROPERTY_NAME);
        String sqlMapPrefix = this.context.getSqlMapGeneratorConfiguration().getProperty(PREFIX_PROPERTY_NAME);
        String clientPrefix = this.context.getJavaClientGeneratorConfiguration().getProperty(PREFIX_PROPERTY_NAME);
        modelPrefix = contextPrefix != null ? contextPrefix : modelPrefix != null ? modelPrefix : "";
        sqlMapPrefix = contextPrefix != null ? contextPrefix : sqlMapPrefix != null ? sqlMapPrefix : "";
        clientPrefix = contextPrefix != null ? contextPrefix : clientPrefix != null ? clientPrefix : "";
        String modelSuffix = this.context.getJavaModelGeneratorConfiguration().getProperty(SUFFIX_PROPERTY_NAME);
        String sqlMapSuffix = this.context.getSqlMapGeneratorConfiguration().getProperty(SUFFIX_PROPERTY_NAME);
        String clientSuffix = this.context.getJavaClientGeneratorConfiguration().getProperty(SUFFIX_PROPERTY_NAME);

        modelSuffix = contextSuffix != null ? contextSuffix : modelSuffix != null ? modelSuffix : "";
        sqlMapSuffix = contextSuffix != null ? contextSuffix : sqlMapSuffix != null ? sqlMapSuffix : "";
        clientSuffix = contextSuffix != null ? contextSuffix : clientSuffix != null ? clientSuffix : "";

        fixModel(introspectedTable, modelPrefix, modelSuffix);
        fixSqlMap(introspectedTable, sqlMapPrefix, sqlMapSuffix);
        fixclient(introspectedTable, clientPrefix, clientSuffix);
    }

    private void fixclient(IntrospectedTable introspectedTable, String clientPrefix, String clientSuffix) {
        introspectedTable.setMyBatis3JavaMapperType(new TypeFullName(introspectedTable.getMyBatis3JavaMapperType()).fixTypeShortName(clientPrefix, clientSuffix).getTypeFullName());
        introspectedTable.setMyBatis3SqlProviderType(new TypeFullName(introspectedTable.getMyBatis3SqlProviderType()).fixTypeShortName(clientPrefix, clientSuffix).getTypeFullName());
        introspectedTable.setDAOInterfaceType(new TypeFullName(introspectedTable.getDAOInterfaceType()).fixTypeShortName(clientPrefix, clientSuffix).getTypeFullName());
        introspectedTable.setDAOImplementationType(new TypeFullName(introspectedTable.getDAOImplementationType()).fixTypeShortName(clientPrefix, clientSuffix).getTypeFullName());
    }

    private void fixSqlMap(IntrospectedTable introspectedTable, String sqlMapPrefix, String sqlMapSuffix) {
        introspectedTable.setMyBatis3XmlMapperFileName(new FileFullName(introspectedTable.getMyBatis3XmlMapperFileName()).fixTypeShortName(sqlMapPrefix, sqlMapSuffix).getFileShortName());
        introspectedTable.setIbatis2SqlMapFileName(new FileFullName(introspectedTable.getIbatis2SqlMapFileName()).fixTypeShortName(sqlMapPrefix, sqlMapSuffix).getFileShortName());
    }

    private void fixModel(IntrospectedTable introspectedTable, String modelPrefix, String modelSuffix) {
        introspectedTable.setBaseRecordType(new TypeFullName(introspectedTable.getBaseRecordType()).fixTypeShortName(modelPrefix, modelSuffix).getTypeFullName());
        introspectedTable.setExampleType(new TypeFullName(introspectedTable.getExampleType()).fixTypeShortName(modelPrefix, modelSuffix).getTypeFullName());
        introspectedTable.setRecordWithBLOBsType(new TypeFullName(introspectedTable.getRecordWithBLOBsType()).fixTypeShortName(modelPrefix, modelSuffix).getTypeFullName());
        introspectedTable.setPrimaryKeyType(new TypeFullName(introspectedTable.getPrimaryKeyType()).fixTypeShortName(modelPrefix, modelSuffix).getTypeFullName());
    }

    private void replaceContext(IntrospectedTable introspectedTable, String contextSearch, String contextReplace) {
        replaceModel(introspectedTable, contextSearch, contextReplace);
        replaceSqlMap(introspectedTable, contextSearch, contextReplace);
        replaceClient(introspectedTable, contextSearch, contextReplace);
    }

    private void replaceClient(IntrospectedTable introspectedTable, String contextSearch, String contextReplace) {
        introspectedTable.setMyBatis3JavaMapperType(new TypeFullName(introspectedTable.getMyBatis3JavaMapperType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setMyBatis3SqlProviderType(new TypeFullName(introspectedTable.getMyBatis3SqlProviderType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setDAOInterfaceType(new TypeFullName(introspectedTable.getDAOInterfaceType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setDAOImplementationType(new TypeFullName(introspectedTable.getDAOImplementationType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
    }

    private void replaceSqlMap(IntrospectedTable introspectedTable, String contextSearch, String contextReplace) {
        introspectedTable.setMyBatis3XmlMapperFileName(new TypeFullName(introspectedTable.getMyBatis3XmlMapperFileName()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setIbatis2SqlMapFileName(new TypeFullName(introspectedTable.getIbatis2SqlMapFileName()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
    }

    private void replaceModel(IntrospectedTable introspectedTable, String contextSearch, String contextReplace) {
        introspectedTable.setBaseRecordType(new TypeFullName(introspectedTable.getBaseRecordType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setExampleType(new TypeFullName(introspectedTable.getExampleType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setRecordWithBLOBsType(new TypeFullName(introspectedTable.getRecordWithBLOBsType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
        introspectedTable.setPrimaryKeyType(new TypeFullName(introspectedTable.getPrimaryKeyType()).replaceTypeShortName(contextSearch, contextReplace).getTypeFullName());
    }
}
