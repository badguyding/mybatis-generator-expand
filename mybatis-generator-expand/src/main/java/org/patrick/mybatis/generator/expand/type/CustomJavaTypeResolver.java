package org.patrick.mybatis.generator.expand.type;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 19:18
 */
public class CustomJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final String CUSTOM_TYPE_MAP_PROPERTY_NAME = "customTypeMap";
    protected Map<Integer, FullyQualifiedJavaType> customTypeMap;

    public CustomJavaTypeResolver() {
        this.customTypeMap = new HashMap();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        String p = this.properties.getProperty("customTypeMap");
        if (p != null) {
            String[] mapStrings = p.split(",");
            if (mapStrings.length > 0)
                for (String mapString : mapStrings) {
                    String[] jdbcAndJava = mapString.split(":");
                    if (jdbcAndJava.length >= 2)
                        this.customTypeMap.put(Integer.valueOf(JdbcTypeNameTranslator.getJdbcType(jdbcAndJava[0].trim())), new FullyQualifiedJavaType(jdbcAndJava[1].trim()));
                }
        }
    }

    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer = (FullyQualifiedJavaType) this.customTypeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
        return answer != null ? answer : super.calculateJavaType(introspectedColumn);
    }
}
