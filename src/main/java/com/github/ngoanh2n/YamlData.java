package com.github.ngoanh2n;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Reads Yaml file to Map, list of Map, Model, list of Models.<br>
 * Class of Model must be `public` and has `setter` methods.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings("unchecked")
public abstract class YamlData<Model> {
    /**
     * Reads Yaml file as {@linkplain Map}.
     *
     * @param name is the name of file. <br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @return {@linkplain Map} if the file exists; <br>
     * {@linkplain RuntimeError } if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromFile(String name) {
        return toMapFromFile(name, Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain Map}.
     *
     * @param name is the name of file. <br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @param cs   is a Charset
     * @return {@linkplain Map} if the file exists; <br>
     * {@linkplain RuntimeError } if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromFile(String name, Charset cs) {
        InputStream is = getInputStream(name);
        return toMapFromInputStream(is, cs);
    }

    /**
     * Reads Yaml file as {@linkplain Map}.
     *
     * @param name is the name of resource. <br>
     *             e.g. com/foo/File.yml
     * @return {@linkplain Map} if the file exists; <br>
     * {@linkplain RuntimeError } if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromResource(String name) {
        return toMapFromResource(name, Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain Map}.
     *
     * @param name is the name of resource. <br>
     *             e.g. com/foo/File.yml
     * @param cs   is a Charset
     * @return {@linkplain Map} if the file exists; <br>
     * {@linkplain RuntimeError } if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromResource(String name, Charset cs) {
        InputStream is = Resource.getInputStream(name);
        return toMapFromInputStream(is, cs);
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Map}.
     *
     * @param name is the name of file. <br>
     *             e.g. com/foo/File.yml
     * @return {@linkplain List} of {@linkplain Map} if the file exists;<br>
     * {@linkplain RuntimeError } otherwise.
     */
    public static List<Map<String, Object>> toMapsFromFile(String name) {
        return toMapsFromFile(name, Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Map}.
     *
     * @param name is the name of file. <br>
     *             e.g. com/foo/File.yml
     * @param cs   is a Charset
     * @return {@linkplain List} of {@linkplain Map} if the file exists;<br>
     * {@linkplain RuntimeError } otherwise.
     */
    public static List<Map<String, Object>> toMapsFromFile(String name, Charset cs) {
        InputStream is = getInputStream(name);
        return toMapsFromInputStream(is, cs);
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Map}.
     *
     * @param name is the name of resource. <br>
     *             e.g. com/foo/File.yml
     * @return {@linkplain List} of {@linkplain Map} if the file exists;<br>
     * {@linkplain RuntimeError } otherwise.
     */
    public static List<Map<String, Object>> toMapsFromResource(String name) {
        return toMapsFromResource(name, Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Map}.
     *
     * @param name is the name of resource. <br>
     *             e.g. com/foo/File.yml
     * @param cs   is a Charset
     * @return {@linkplain List} of {@linkplain Map} if the file exists;<br>
     * {@linkplain RuntimeError } otherwise.
     */
    public static List<Map<String, Object>> toMapsFromResource(String name, Charset cs) {
        InputStream is = Resource.getInputStream(name);
        return toMapsFromInputStream(is, cs);
    }

    //-------------------------------------------------------------------------------//

    private static Map<String, Object> toMapFromInputStream(InputStream is, Charset cs) {
        Object object = inputStreamToObject(is, cs);
        if (object instanceof LinkedHashMap) {
            return (Map<String, Object>) object;
        } else {
            throw new RuntimeError("It's list, use toMapsFrom() instead");
        }
    }

    private static List<Map<String, Object>> toMapsFromInputStream(InputStream is, Charset cs) {
        Object object = inputStreamToObject(is, cs);
        if (object instanceof ArrayList) {
            return (List<Map<String, Object>>) object;
        } else {
            return Collections.singletonList((Map<String, Object>) object);
        }
    }

    private static InputStream getInputStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException ignored) {
            throw new RuntimeError(String.format("File not found -> %s", fileName));
        }
    }

    private static Object inputStreamToObject(InputStream is, Charset cs) {
        InputStreamReader isr = new InputStreamReader(is, cs);
        Iterator<Object> iterator = new Yaml().loadAll(isr).iterator();
        if (iterator.hasNext()) return iterator.next();
        throw new RuntimeError("Yaml content is empty");
    }

    //-------------------------------------------------------------------------------//

    private InputStream _inputStream;
    private Class<Model> _modelClazz;

    //-------------------------------------------------------------------------------//

    /**
     * Constructs and gets current Java Bean class
     */
    public YamlData() {
        _modelClazz = getModelClazz();
    }

    /**
     * Sets the path name of Yaml file.
     *
     * @param name is the name of Yaml file. <br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @return the current instance of {@linkplain Model}.
     */
    public Model fromFile(String name) {
        _inputStream = getInputStream(name);
        return (Model) this;
    }

    /**
     * Sets resource name to get the Yaml file.
     *
     * @param name is the name of resource. <br>
     *             e.g. com/foo/File.yml
     * @return the current instance of {@linkplain Model}.
     */
    public Model fromResource(String name) {
        _inputStream = Resource.getInputStream(name);
        return (Model) this;
    }

    /**
     * Reads Yaml file as {@linkplain Model}.
     *
     * @return {@linkplain Model} if the file exists; <br>
     * Throws {@linkplain RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public Model toModel() {
        return toModel(Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain Model}.
     *
     * @param cs is a Charset.
     * @return {@linkplain Model} if the file exists; <br>
     * Throws {@linkplain RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public Model toModel(Charset cs) {
        InputStream is = getInputStream();
        Object object = inputStreamToObject(is, cs);

        if (object instanceof LinkedHashMap) {
            return mapToModel((Map<String, Object>) object);
        } else {
            throw new RuntimeError("It's list, use toModels() instead");
        }
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Model}.
     *
     * @return {@linkplain List} of {@linkplain Model} if the file exists;<br>
     * {@linkplain List} of {@linkplain Model} with one element when trying to single object as multiple object.
     * Throws {@linkplain RuntimeError} if the file doesn't exist.
     */
    public List<Model> toModels() {
        return toModels(Charset.defaultCharset());
    }

    /**
     * Reads Yaml file as {@linkplain List} of {@linkplain Model}.
     *
     * @param cs is a .
     * @return {@linkplain List} of {@linkplain Model} if the file exists;<br>
     * {@linkplain List} of {@linkplain Model} with one element when trying to single object as multiple object.
     * Throws {@linkplain RuntimeError} if the file doesn't exist.
     */
    public List<Model> toModels(Charset cs) {
        InputStream is = getInputStream();
        Object object = inputStreamToObject(is, cs);
        List<Model> models = new ArrayList<>();

        if (object instanceof ArrayList) {
            List<Map<String, Object>> maps = (List<Map<String, Object>>) object;
            maps.forEach(map -> models.add(mapToModel(map)));
        } else {
            models.add(mapToModel((Map<String, Object>) object));
        }
        return models;
    }

    //-------------------------------------------------------------------------------//

    private Class<Model> getModelClazz() {
        if (_modelClazz == null) {
            if (getClass().getName().equals(YamlData.class.getName())) {
                throw new RuntimeError("Could not read YamlData without model");
            } else {
                // Current model is generic
                Type superType = getClass().getGenericSuperclass();
                Type paramType = ((ParameterizedType) superType).getActualTypeArguments()[0];

                try {
                    if (paramType.toString().split(" ").length > 1) {
                        // Specific model
                        String modelName = paramType.toString().split(" ")[1];
                        _modelClazz = (Class<Model>) Class.forName(modelName);
                    } else {
                        // Generic model
                        TypeVariable<?> varType = ((TypeVariable<?>) paramType);
                        String modelName = varType.getGenericDeclaration().toString().split(" ")[1];
                        _modelClazz = (Class<Model>) Class.forName(modelName);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeError("Could not find model class", e);
                }
            }
        }
        return _modelClazz;
    }

    private InputStream getInputStream() {
        if (_inputStream == null) {
            YamlFrom yamlFrom = getClass().getAnnotation(YamlFrom.class);

            if (yamlFrom != null) {
                if (!yamlFrom.file().isEmpty()) {
                    _inputStream = getInputStream(yamlFrom.file());
                }
                if (_inputStream == null) {
                    if (!yamlFrom.resource().isEmpty()) {
                        _inputStream = Resource.getInputStream(yamlFrom.resource());
                    }
                }
            }
        }

        if (_inputStream != null) {
            return _inputStream;
        } else {
            throw new RuntimeError("Use @YamlFrom or call fromFile()/fromResource()");
        }
    }

    private Model mapToModel(Map<String, Object> map) {
        return new ObjectMapper().convertValue(map, _modelClazz);
    }
}
