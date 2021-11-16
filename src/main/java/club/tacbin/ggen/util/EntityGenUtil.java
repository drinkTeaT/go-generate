package club.tacbin.ggen.util;

import club.tacbin.ggen.dto.GoCodeGenDTO;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;

import java.util.HashSet;

/**
 * @description:
 * @author: tacbin
 * @date: 2021-10-30
 **/
public class EntityGenUtil {
    public static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    public static final String username = "root";
    public static final String password = "tacbin@123";

    public static void doGen(GoCodeGenDTO goCodeGenDTO) {
        String[] tables = goCodeGenDTO.getTables().split(",");
        String projectPath = "./";
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(goCodeGenDTO.getUrl(), goCodeGenDTO.getUserName(), goCodeGenDTO.getPassword())
                .build();
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig)
                .global(new GlobalConfig.Builder()
                        .author("")
                        .fileOverride()
                        .openDir(false)
                        .outputDir(projectPath + goCodeGenDTO.getFilePath())
                        .build())
                .packageInfo(new PackageConfig.Builder()
                        .moduleName("")
                        .entity("entity")
                        .mapper("dto")
                        .xml("")
                        .controller("")
                        .parent("")
                        .build())
                .strategy(new StrategyConfig.Builder().addInclude(tables)
                        .enableSkipView()
                        .entityBuilder()
                        .nameConvert(new MyNameConverter())
                        .convertFileName(new MyFileNameConverter())
                        .enableLombok()
                        .build()).template(GeneratorBuilder.templateConfigBuilder().entity("template/entity.go.vm").mapper("template/dto.go.vm").build());;
        autoGenerator.execute();
    }


    private static class MyNameConverter implements INameConvert {

        @Override
        public String entityNameConvert(TableInfo tableInfo) {
            String name = tableInfo.getName();
//        return name;
            return NamingStrategy.capitalFirst(NamingStrategy.removePrefixAndCamel(name.substring(0), new HashSet<>()));
        }

        @Override
        public String propertyNameConvert(TableField field) {
            return NamingStrategy.removePrefixAndCamel(field.getName(), new HashSet<>());
        }
    }

    private static class MyFileNameConverter implements ConverterFileName {

        @Override
        public String convert(String entityName) {
            return entityName;
        }
    }
}
