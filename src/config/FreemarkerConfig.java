package config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Objects;

public class FreemarkerConfig {
    private FreemarkerConfig() {}

    private static final Configuration freemarker = initFreemarker();

    private static Configuration initFreemarker() {
        try{
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
            configuration.setDirectoryForTemplateLoading(new File("static/templates"));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            configuration.setFallbackOnNullLoopVariable(false);
            return configuration;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] render(String templateFile, Object dataModel){
        try(
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Writer writer = new OutputStreamWriter(outputStream)
        )
        {
            Template template = Objects.requireNonNull(freemarker).getTemplate(templateFile);
            template.process(dataModel, writer);
            return outputStream.toByteArray();
        } catch (IOException | TemplateException e) {
            System.err.println("Error rendering template: " + templateFile + " - " + e.getMessage());
            e.printStackTrace();
            return new byte[0];
        }
    }

}
