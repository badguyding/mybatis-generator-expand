package org.patrick.mybatis.generator.expand.type;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 16:29
 */
public class TypeFullName {
    private String typePackageName;
    private String typeShortName;

    public TypeFullName(String typePackageName, String typeShortName) {
        this.typePackageName = typePackageName;
        this.typeShortName = typeShortName;
    }

    public TypeFullName(String typeFullName) {
        int index0 = typeFullName.lastIndexOf('.');
        this.typePackageName = typeFullName.substring(0, index0 < 0 ? 0 : index0);
        this.typeShortName = typeFullName.substring(index0 + 1, typeFullName.length());
    }

    public TypeFullName(FileFullName fileFullName) {
        this.typePackageName = fileFullName.getPathName().replaceAll("/", ".").replaceAll("\\\\", ".");
        this.typeShortName = fileFullName.getTypeShortName();
    }

    public String getTypeFullName() {
        return this.typePackageName + "." + this.typeShortName;
    }

    public String getTypePackageName() {
        return typePackageName;
    }

    public void setTypePackageName(String typePackageName) {
        this.typePackageName = typePackageName;
    }

    public String getTypeShortName() {
        return typeShortName;
    }

    public void setTypeShortName(String typeShortName) {
        this.typeShortName = typeShortName;
    }

    public TypeFullName replaceTypeShortName(String search, String replace) {
        if ((search != null) && (replace != null)) {
            this.typeShortName = this.typeShortName.replaceAll(search, replace);
        }
        return this;
    }

    public TypeFullName fixTypeShortName(String prefix, String suffix) {
        if (prefix != null) {
            this.typeShortName = (prefix + this.typeShortName);
        }
        if (suffix != null) {
            this.typeShortName += suffix;
        }
        return this;
    }
}
