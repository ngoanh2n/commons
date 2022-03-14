package com.github.ngoanh2n;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.ngoanh2n.Resources.getInputStream;

/**
 * Yaml file to Map, Model, List of Models
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings("unchecked")
public class YamlData<Model> {

    private String resourceName;
    private Class<Model> modelClazz;

    public YamlData() {
        modelClazz = getModelClazz();
    }

    public static Map<String, Object> toMap(String fromResource) {
        InputStream is = getInputStream(fromResource);
        return new Yaml().loadAs(is, Map.class);
    }

    public Model fromResource(String resourceName) {
        this.resourceName = resourceName;
        return (Model) this;
    }

    public Model toModel() {
        InputStream is = getInputStream(resourceName);
        return new Yaml().loadAs(is, modelClazz);
    }

    public List<Model> toModels() {
        ObjectMapper om = new ObjectMapper();
        List<Model> models = new ArrayList<>();
        InputStream is = getInputStream(resourceName);
        Iterable<Object> objects = new Yaml().loadAs(is, List.class);
        objects.forEach(object -> models.add(om.convertValue(object, modelClazz)));
        return models;
    }

    private Class<Model> getModelClazz() {
        if (modelClazz == null) {
            if (getClass().getName().equals(YamlData.class.getName())) {
                throw new RuntimeError("Could not read for generic type without model");
            } else {
                // Current model is generic
                Type superType = getClass().getGenericSuperclass();
                Type paramType = ((ParameterizedType) superType).getActualTypeArguments()[0];

                try {
                    if (paramType.toString().split(" ").length > 1) {
                        // Specific model
                        String modelName = paramType.toString().split(" ")[1];
                        modelClazz = (Class<Model>) Class.forName(modelName);
                    } else {
                        // Generic model
                        TypeVariable<?> varType = ((TypeVariable<?>) paramType);
                        String modelName = varType.getGenericDeclaration().toString().split(" ")[1];
                        modelClazz = (Class<Model>) Class.forName(modelName);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeError("Could not find model class", e);
                }
            }
        }
        return modelClazz;
    }
}
