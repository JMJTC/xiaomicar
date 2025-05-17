package com.example.xiaomicar.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;
/**
 * @Author: jmjtc
 * @CreateTime: 2025-05-14
 * @Description:
 * @Version: 1.0
 */

public class FastAutoGeneratorUtil {
    private static final String DIR_ROOT = "F:\\jmjtc\\Java_Study\\xiaomicar\\src\\main\\java";
    private static final String PKG_NAME = "com.example.xiaomicar";

    public static void autoGenerator() {
        //代码生成器新 https://baomidou.com/pages/779a6e/#%E5%AE%89%E8%A3%85

        //https://blog.csdn.net/wmj20001225/article/details/132595714

        //https://baomidou.com/pages/779a6e/#%E5%AE%89%E8%A3%85

        //https://blog.csdn.net/qq_42263280/article/details/126531993

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/xiaomicar", "root", "jmjtc666")
                //全局配置(GlobalConfig)
                .globalConfig(builder -> {
                    builder.disableOpenDir()//禁止打开输出目录	默认值:true
//				.outputDir(System.getProperty("user.dir")+"/src/main/java")//指定输出目录
                            .outputDir(DIR_ROOT)//指定输出目录
                            .author("jmj")
//				.enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                            .build();
                })
                //包配置(PackageConfig)
                .packageConfig(builder -> {
                    builder.parent(PKG_NAME)
//				.moduleName("sys") //父包模块名	默认值:无
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .controller("controller")
//                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/chinadragon/Desktop/intellij_idea_project/LearnElementUiAndSpringBoot/src/main/resources/mapper"))
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"))
                            .build();
                })

                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("vehicle_info", "battery_signal", "warning_rule", "warning_info")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .build();

                }).execute();

    }
}

