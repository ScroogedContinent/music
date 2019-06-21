package cn.org.scrooged.base.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/21 13:55
 */
@Configuration
public class KeyNameConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        FieldBeanSerializerModifier modifier = new FieldBeanSerializerModifier();
        preHandle(modifier);
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(modifier));
        return objectMapper;
    }

    private static void preHandle(FieldBeanSerializerModifier modifier) {
        modifier.addHandle(new DefaultKeyNameHandler(keyNameProducer()));
    }

    private static KeyNameProducer keyNameProducer(){
        return no -> {
            if ("Yes_or_no".equals(no)) {
                return new IKeyName[]{build("1", "是"), build("0", "否")};
            }
            return new IKeyName[0];
        };
    }

    private static IKeyName[] buildSameArrey(String...codes) {
        return Arrays.stream(codes).map(KeyNameConfig::buildSame).toArray(IKeyName[]::new);
    }

    private static IKeyName buildSame(String code) {
        return build(code, code);
    }

    private static IKeyName build(String code, String name) {
        return new IKeyName() {
            @Override
            public Serializable getCode() {
                return code;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
