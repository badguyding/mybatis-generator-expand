package org.patrick.mybatis.generator.expand.type;

/**
 * <p></p>
 *
 * @author dl
 * @Date 2017/1/6 16:33
 */
public class FileFullName {
    private String pathName;
    private String typeShortName;
    private String extensionName;

    public FileFullName(String pathName, String typeShortName, String extensionName) {
        this.pathName = pathName;
        this.typeShortName = typeShortName;
        this.extensionName = extensionName;
    }

    public FileFullName(String fileFullName) {
        int index0 = fileFullName.lastIndexOf('/');
        int index1 = fileFullName.lastIndexOf('.');
        this.pathName = fileFullName.substring(0, index0 < 0 ? 0 : index0);
        this.typeShortName = fileFullName.substring(index0 + 1, index1 < index0 + 1 ? index0 + 1 : index1);
        this.extensionName = fileFullName.substring(index1 + 1, fileFullName.length());
    }

    public FileFullName(TypeFullName typeFullName, String extensionName) {
        this.pathName = typeFullName.getTypePackageName().replaceAll("\\.", "/");
        this.typeShortName = typeFullName.getTypeShortName();
        this.extensionName = extensionName;
    }

    public FileFullName(String pathName, String fileShortName) {
        int index0 = fileShortName.lastIndexOf('.');
        this.pathName = pathName;
        this.typeShortName = fileShortName.substring(0, index0 < 0 ? 0 : index0);
        this.extensionName = fileShortName.substring(index0 + 1, fileShortName.length());
    }

    public String getPathName() {
        return this.pathName;
    }

    public String getTypeShortName() {
        return this.typeShortName;
    }

    public String getExtensionName() {
        return this.extensionName;
    }

    public String getFileShortName() {
        return this.typeShortName + "." + this.extensionName;
    }

    public String getFileFullName() {
        return this.pathName + "/" + getFileShortName();
    }

    public FileFullName replaceTypeShortName(String search, String replace) {
        if ((search != null) && (replace != null)) {
            this.typeShortName = this.typeShortName.replaceAll(search, replace);
        }
        return this;
    }

    public FileFullName fixTypeShortName(String prefix, String suffix) {
        if (prefix != null) {
            this.typeShortName = (prefix + this.typeShortName);
        }
        if (suffix != null) {
            this.typeShortName += suffix;
        }
        return this;
    }
}
