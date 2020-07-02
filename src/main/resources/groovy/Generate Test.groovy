//import com.intellij.database.model.DasTable
//import com.intellij.database.util.Case
//import com.intellij.database.util.DasUtil
//
//import java.text.SimpleDateFormat
//
///*
// * Available context bindings:
// *   SELECTION   Iterable<DasObject>
// *   PROJECT     project
// *   FILES       files helper
// */
//
//// ============= 常量区域 =================
//useLogicDelete = true; /* 启用逻辑删除 */
//useLombok = true;/*是否启用lombok*/
//useSerializable = true;/*是否启用实体序列化*/
//
///* 类型转换 ,负责将数据库类型转换为java类型,转换逻辑取自 mybatis */
//typeMapping = [
//        (~/(?i)real|decimal|numeric/)        : "BigDecimal",
//        (~/(?i)bigint/)                      : "Long",
//        (~/(?i)tinyint/)                     : "Byte", /* 根据自己的需求,可以考虑转换为Boolean*/
//        (~/(?i)int/)                         : "Integer",
//        (~/(?i)enum/)                        : "String", /* 枚举统一转换为字符串*/
//        (~/(?i)float|double/)                : "Double", /* 根据自己的需求可以考虑其他类型*/
//        (~/(?i)datetime|timestamp|date|time/): "Date",
//        (~/(?i)/)                            : "String" /*其余的统一转成字符串*/
//]
//
//
///* java 别名映射 ,负责导包*/
//javaAliasMap = [
//        "BigDecimal"  : "java.math.BigDecimal",
//        "Date"        : "java.util.Date",
//        "Getter"      : "lombok.Getter",
//        "Setter"      : "lombok.Setter",
//        "ToString"    : "lombok.ToString",
//        "Serializable": "java.io.Serializable",
//        "Table"       : "javax.persistence.Table",
//        "Id"          : "javax.persistence.Id",
//        "Column"      : "javax.persistence.Column",
//]
//
//
///* =========  运行时属性定义 ============*/
//// 代码生成路径,在脚本中通过弹出文件框进行选择
//String packageName = ""
///* 待引入的java类*/
//importClass = [];
///* 待使用注解 */
//usesAnnotations = [];
//
//
///* 脚本入口 */
///* 选择文件目录 ,并执行生成代码操作 */
//FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
//    SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
//}
//
//
//def generate(table, dir) {
//    // step1: 获取包名
//    packageName = getPackageName(dir)
//    // step2: 获取当前表名对应的类名称
//    def className = javaName(table.getName(), true) + "DTO"
//    // step3: 加载lombok注解
//    if (useLombok) {
//        ["Data"].each() {
//            convertAliasesAndImportClasses(it)
//            usesAnnotations << it;
//        };
//    }
//    // step4: 加载序列化
//    if (useSerializable) {
//        convertAliasesAndImportClasses("Serializable")
//    }
//    // step5: 加载自定义类注解
//    loadCustomAnnotationToClass(table, className, dir);
//
//    // step6: 处理所有数据列定义
//    def properties = processDataColumnDefinition(table);
//
//    // step7: 生成代码
//    new File(dir as File, className + ".java").withPrintWriter("utf-8") {
//        out ->
//            generate(out, className, properties, table)
//    }
//}
//
//
//// 生成实体类代码
//def generate(out, className, properties, table) {
//    // step 1: 输出包名
//    out.println "package $packageName"
//    out.println ""
//    out.println ""
//    // step2: 导入类
//    importClass.each() {
//        out.println "import ${it};"
//    }
//    out.println ""
//
//    // 补充:生成注释
//    generateClassComments(out, table)
//
//    // step3: 生成类注解
//    usesAnnotations.each() {
//        out.println "@${it}"
//    }
//    // step4: 生成类名
//    out.print "public class $className"
//    if (useSerializable) {
//        out.println(" implements Serializable {")
//        out.println ""
//        /* 生成序列化ID*/
//        out.println genSerialID()
//    } else {
//        out.println "{"
//    }
//    out.println ""
//    // step5: 处理每个属性定义
//    properties.each() {
//        // step5.1: 输出注释
//        if (isNotEmpty(it.comment)) {
//            out.println "\t/**"
//            out.println "\t * ${it.comment.toString()}"
//            out.println "\t */"
//        }
//        //step5.2: 输出注解
//        it.annos.each() {
//            out.println "\t@${it}"
//            //out.println "ceshi"
//        }
//        /*step5.3: 输入字段内容*/
//        out.println "\tprivate ${it.type} ${it.name};"
//    }
//
//    // step6: 生成getter/setter方法
//    if (!useLombok) {
//        // 在未启用lombok的情况下生成getter/setter方法
//        properties.each() {
//            out.println ""
//            out.println "  public ${it.type} get${it.name.capitalize()}() {"
//            out.println "    return ${it.name};"
//            out.println "  }"
//            out.println ""
//            out.println "  public void set${it.name.capitalize()}(${it.type} ${it.name}) {"
//            out.println "    this.${it.name} = ${it.name};"
//            out.println "  }"
//            out.println ""
//        }
//    }
//
//    // step7: 结尾
//    out.println "}"
//}
//
//def processDataColumnDefinition(table) {
//    // 加载当前数据库主键
//    def primaryKey = ""
//    def prKey = DasUtil.getPrimaryKey(table);
//    if (prKey != null) {
//        def keyRef = prKey.getColumnsRef();
//        if (keyRef != null) {
//            def it = keyRef.iterate();
//            while (it.hasNext()) {
//                // 默认只处理单主键
//                primaryKey = it.next();
//            }
//            primaryKey = javaName(primaryKey, false);
//        }
//    }
//
//    // 依次处理每一行数据列
//    DasUtil.getColumns(table).reduce([]) { properties, col ->
//        // 获取JDBC类型
//        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
//        // 将JDBC类型映射为JAVA类型
//        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
//        // 判断该类型是否需要导包
//        convertAliasesAndImportClasses(typeStr);
//        // 将列名称转换为字段名称
//        def javaName = javaName(col.getName(), false);
//        // 当前列是否为主键
//        def isPrimaryKey = javaName.equals(primaryKey);
//        // 是否为逻辑删除标记
//        def isLogicDeleteFlag = isLogicDelete(javaName);
//
//        def property = [
//                name   : javaName,  /* java属性名*/
//                colName: col.getName(), /*jdbc列名*/
//                type   : typeStr, /*java类型*/
//                comment: col.getComment(),/*注释*/
//                isKey  : isPrimaryKey, /*是否为数据表主键*/
//                isDel  : isLogicDeleteFlag, /*是否是逻辑删除标志*/
//                annos  : [] /*需要添加的属性注解*/
//        ]
//        loadCustomAnnotationsToProperty(property,col.getName());
//        properties << property;
//    }
//}
//
//def javaName(str, capitalize) {
//    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
//            .collect { Case.LOWER.apply(it).capitalize() }
//            .join("")
//            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
//    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
//}
//
//static def isNotEmpty(content) {
//    return content != null && content.toString().trim().length() > 0
//}
//
//static String genSerialID() {
//    return "\tprivate static final long serialVersionUID =  " + Math.abs(new Random().nextLong()) + "L;"
//}
//// 生成包名
//static def getPackageName(dir) {
//    return dir.toString().replaceAll("\\\\", ".").replaceAll("/", ".").replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
//}
//
///**
// * 判断一个java属性是否为逻辑删除
// * [根据自己的需求重构该测试]
// * @param property 属性名称
// * @return
// */
//boolean isLogicDelete(property) {
//    return useLogicDelete && property.equalsIgnoreCase("DeleteFlag") || property.equalsIgnoreCase("DelFlag")
//}
//
///**
// * 导入lombok的类
// */
//void importLombokPackage() {
//    if (useLombok) {
//        ["Data"].each() {
//            doImportClass(it);
//        };
//    }
//}
//
///**
// * 导入一个包名称
// * @param className
// */
//void doImportClass(className) {
//    if (importClass.count { it -> it.equalsIgnoreCase(className) } == 0) {
//        importClass << className;
//    }
//}
//
//void convertAliasesAndImportClasses(className) {
//    def entry = javaAliasMap.find { p, t -> p.equalsIgnoreCase(className) };
//    if (entry == null) {
//        return;
//    }
//    def fullName = entry.value
//    if (isNotEmpty(fullName)) {
//        doImportClass(fullName);
//    }
//}
//
//void loadCustomAnnotationToClass(table, className, dir) {
//    // 根据自己的需求实现,加载自定义的注解,比如JPA注解
//
//}
//
//void loadCustomAnnotationsToProperty(property,colName) {
//    // 根据自己的需求实现,加载自定义属性注解,比如这里,我使用了自定义的注解
//    if (property.isDel) {
//        convertAliasesAndImportClasses("Logic");
//        property.annos << "Logic";
//    }
//    if (property.isKey) {
//        convertAliasesAndImportClasses("Id");
//        property.annos << "Id";
//    }
//    convertAliasesAndImportClasses("Column");
//    property.annos << "Column(name = \"" + colName + "\")";
//}
//
//void generateClassComments(out, table) {
//    /* 生成类注释 */
//    ["/**",
//     " * ",
//     " * @Description ${table.getComment()}",
//     " * @author QuJiaQi",
//     " * @version 1.0",
//     " * @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date()),
//     " */"
//    ].each() {
//        out.println "${it}"
//    }
//}