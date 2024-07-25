package com.pms.common.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/pms?serverTimezone=Asia/Kuala_Lumpur","root","1234")
                .globalConfig(builder -> {
                    builder.author("Sn0w_15") // Author
                //            .enableSwagger() // Enable Swagger
                            .fileOverride() // Enable Overwrite
                            .outputDir("C:\\Users\\Olaf\\Desktop\\Permission-Management-System\\pms-back\\src\\main\\java\\"); // Export Directory
                })
                .packageConfig(builder -> {
                    builder.parent("com.pms") // Parent Package
                            .moduleName("") // Parent Module
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\Olaf\\Desktop\\Permission-Management-System\\pms-back\\src\\main\\resources\\mapper")); // mapperXml generate Path
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle() // enable CamelCase
                            .enableRestStyle(); // Enable @RestController Interceptor
                    builder.addInclude("pms_role_permission") // <---- Configure Needed Table Name
                            .addTablePrefix("t_","pms_"); // <---- Configure Filter Table Prefix
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // Default Engine Template: Velocity
                .execute();
    }

}
