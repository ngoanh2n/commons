package com.github.ngoanh2n;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * Yaml modelling to Object, Model, Stream
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings("unchecked")
public class YamlModel<T> {

    private String path;
    private Class<T> modelClazz;
    private final ObjectMapper objectMapper;

    public YamlModel() {
        this.modelClazz = getModelClazz();
        this.objectMapper = new ObjectMapper();
    }

    public YamlModel(Class<T> modelClazz) {
        this.modelClazz = modelClazz;
        this.objectMapper = new ObjectMapper();
    }

    public T useResource(String resourceName) {
        path = Resources.getPath(resourceName).toString();
        return (T) this;
    }

    public String toJson() {
        Map<String, Object> map = objectMapper.convertValue(this, Map.class);
        return new Gson().toJson(map);
    }

    public T toModel(Object object) {
        return objectMapper.convertValue(object, modelClazz);
    }

    public Object readAsObject() {
        try (InputStream is = new FileInputStream(path)) {
            return new Yaml().loadAs(is, Object.class);
        } catch (IOException ignored) {/* Can't happen*/}
    }

    private void read() {

    }

    private Class<T> getModelClazz() {
        if (modelClazz == null) {
            if (getClass().getName().equals(YamlModel.class.getName())) {
                throw new RuntimeError("Could not read for generic type without model");
            } else {
                // Current model is generic
                Type superType = getClass().getGenericSuperclass();
                Type paramType = ((ParameterizedType) superType).getActualTypeArguments()[0];

                try {
                    if (paramType.toString().split(" ").length > 1) {
                        // Specific model
                        String modelName = paramType.toString().split(" ")[1];
                        modelClazz = (Class<T>) Class.forName(modelName);
                    } else {
                        // Generic model
                        TypeVariable<?> varType = ((TypeVariable<?>) paramType);
                        String modelName = varType.getGenericDeclaration().toString().split(" ")[1];
                        modelClazz = (Class<T>) Class.forName(modelName);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeError("Could not find model class", e);
                }
            }
        }
        return modelClazz;
    }
}
