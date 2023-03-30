package com.rzyc.fulongapi.config;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.common.utils.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.common.utils.jasypt.JasyptUtils.decyptPwd;

/**
 * @ClassName MybatisPlus逆向生成工具
 * @Description TODO
 * @Author
 * @Data 2021/7/14 14:04
 * @Version 1.0
 **/
public class CodeGenerator {
    /**
     *
     * 读取控制台内容
     *
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    public static void main(String[] args) {
        // 实例化代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //当前程序根目录
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/fulongapi/src/main/java");
        gc.setAuthor("");
        //设置时间类型
        gc.setDateType(DateType.ONLY_DATE);
        // 自定义service生成的名字，用于删除自动生成的I前缀
        gc.setServiceName("%sService");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true) ; // 设置sql片段
        gc.setBaseResultMap(true);  // resultMap
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://121.40.106.103:3306/hyfulong_platform?useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("rzyc");
        dsc.setPassword("admin@rzyc2022.com##");
        mpg.setDataSource(dsc);

        // 包配置（生成的entity、controller、service等包名）
        PackageConfig pc = new PackageConfig();
        pc.setEntity("model");
        pc.setController("");
        pc.setService("");
        pc.setServiceImpl("");
        // pc.setModuleName(scanner("模块名"));
        pc.setParent("com.rzyc.fulongapi");
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置(mapper.xml的文件地址)
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath+"/fulongapi/src/main/resources/mapper/"+tableInfo.getEntityName()+"Mapper"+StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置默认模板
        TemplateConfig templateConfig = new TemplateConfig();
        //取消默认mapper的生成地址
        templateConfig.setXml(null);
        templateConfig.setController("");
        templateConfig.setService("");
        templateConfig.setServiceImpl("");
        mpg.setTemplate(templateConfig);

        // 数据库表策略设置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(false);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(false);

        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
