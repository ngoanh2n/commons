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
 * Read Yaml file.<br><br>
 *
 * <b>Static</b><br>
 * Read Yaml file to {@code Map}, {@code List of Maps}.
 *
 * <ol>
 *     <li>Read to Map
 *          <ul>
 *              <li>{@code Map<String, Object> map = YamlData.toMapFromResource("file.json")}</li>
 *              <li>{@code Map<String, Object> map = YamlData.toMapFromFile("src/test/resources/file.json")}</li>
 *          </ul>
 *     </li>
 *     <li>Read to List of Maps
 *          <ul>
 *              <li>{@code List<Map<String, Object>> maps = YamlData.toMapsFromResource("file.json")}</li>
 *              <li>{@code List<Map<String, Object>> maps = YamlData.toMapsFromFile("src/test/resources/file.json")}</li>
 *          </ul>
 *     </li>
 * </ol>
 *
 * <b>Inheritance</b><br>
 * Read Yaml file to {@code Model}, {@code List of Models}.<br>
 * Model class must be {@code public} and has {@code setter} methods.
 *
 * <ol>
 *     <li>Read to Model
 *          <dl>
 *              <dd>
 *                  Yaml: user.yml
 *                  <pre>{@code
 *                      username: usr1
 *                      notes:
 *                          - note1
 *                          - note2
 *                      companies:
 *                          - name: Com1
 *                            address: Addr1
 *                          - name: Com2
 *                            address: Addr2
 *                  }</pre>
 *                  Model: User.java
 *                  <pre>{@code
 *                      public class User extends YamlData<User> {
 *                          private String username;
 *                          private List<String> notes;
 *                          private List<Company> companies;
 *
 *                          ...GETTERS & SETTERS...
 *                      }
 *                  }</pre>
 *                  Model: Company.java
 *                  <pre>{@code
 *                      public class Company extends YamlData<Company> {
 *                          private String name;
 *                          private String address;
 *
 *                          ...GETTERS & SETTERS...
 *                      }
 *                  }</pre>
 *              </dd>
 *          </dl>
 *
 *          <ul>
 *              <li>Without annotation
 *                  <ul>
 *                      <li>{@code User user = new User().fromResource("user.yml").toModel()}</li>
 *                      <li>{@code User user = new User().fromFile("src/test/resources/user.yml").toModel()}</li>
 *                  </ul>
 *              </li>
 *
 *              <li>With annotation<br>
 *                  Attach {@code com.github.ngoanh2n.YamlFrom} annotation for {@code Model}.
 *                  <ul>
 *                      <li>{@link YamlFrom#resource()}
 *                           <pre>{@code
 *                               &#064;YamlFrom(resource = "user.yml")
 *                               public class User extends YamlData<User> {
 *                                 ...
 *                               }
 *                           }</pre>
 *                      </li>
 *                      <li>{@link YamlFrom#file()}
 *                           <pre>{@code
 *                               &#064;YamlFrom(file = "src/test/resources/user.yml")
 *                               public class User extends YamlData<User> {
 *                                 ...
 *                               }
 *                           }</pre>
 *                      </li>
 *                  </ul>
 *                  Overwrite value of {@code com.github.ngoanh2n.YamlFrom} annotation
 *                  by calling {@link #fromResource(String)} or {@link #fromFile(String)} method.
 *              </li>
 *          </ul>
 *     </li>
 *     <li>Read to List of Models
 *          <dl>
 *              <dd>
 *                  Yaml: accounts.yml
 *                  <pre>{@code
 *                      - username: usr1
 *                        password: pwd1
 *                      - username: usr2
 *                        password: pwd2
 *                  }</pre>
 *                  Model: User.java
 *                  <pre>{@code
 *                      public class User extends YamlData<User> {
 *                          private String username;
 *                          private String password;
 *
 *                          ...GETTERS & SETTERS...
 *                      }
 *                  }</pre>
 *              </dd>
 *          </dl>
 *
 *          <ul>
 *              <li>Without annotation
 *                  <ul>
 *                      <li>{@code List<User> users = new User().fromResource("users.yml").toModels()}</li>
 *                      <li>{@code List<User> users = new User().fromFile("src/test/resources/users.yml").toModels()}</li>
 *                  </ul>
 *              </li>
 *
 *              <li>With annotation<br>
 *                  Attach {@code com.github.ngoanh2n.YamlFrom} annotation for {@code Model}.
 *                  <ul>
 *                      <li>{@link YamlFrom#resource()}
 *                           <pre>{@code
 *                               &#064;YamlFrom(resource = "users.yml")
 *                               public class User extends YamlData<User> {
 *                                 ...
 *                               }
 *                           }</pre>
 *                      </li>
 *                      <li>{@link YamlFrom#file()}
 *                           <pre>{@code
 *                               &#064;YamlFrom(file = "src/test/resources/users.yml")
 *                               public class User extends YamlData<User> {
 *                                 ...
 *                               }
 *                           }</pre>
 *                      </li>
 *                  </ul>
 *                  Overwrite value of {@code com.github.ngoanh2n.YamlFrom} annotation
 *                  by calling {@link #fromResource(String)} or {@link #fromFile(String)} method.
 *              </li>
 *          </ul>
 *     </li>
 * </ol>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
@SuppressWarnings("unchecked")
public abstract class YamlData<Model> {
    private InputStream _inputStream;
    private Class<Model> _modelClazz;

    /**
     * Construct a new {@link YamlData} and get current Java Bean class.
     */
    public YamlData() {
        _modelClazz = getModelClazz();
    }

    //-------------------------------------------------------------------------------//

    /**
     * Read Yaml file as {@link Map}.
     *
     * @param name The name of file.<br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @return {@link Map} if the file exists.<br>
     * {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromFile(String name) {
        return toMapFromFile(name, Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link Map}.
     *
     * @param name The name of file.<br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @param cs   A {@link Charset}.
     * @return {@link Map} if the file exists.<br>
     * {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromFile(String name, Charset cs) {
        InputStream is = getInputStream(name);
        return toMapFromInputStream(is, cs);
    }

    /**
     * Read Yaml file as {@link Map}.
     *
     * @param name The name of resource.<br>
     *             e.g. com/foo/File.yml
     * @return {@link Map} if the file exists.<br>
     * {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromResource(String name) {
        return toMapFromResource(name, Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link Map}.
     *
     * @param name The name of resource.<br>
     *             e.g. com/foo/File.yml
     * @param cs   A {@link Charset}.
     * @return {@link Map} if the file exists.<br>
     * {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public static Map<String, Object> toMapFromResource(String name, Charset cs) {
        InputStream is = Resources.getInputStream(name);
        return toMapFromInputStream(is, cs);
    }

    /**
     * Read Yaml file as {@link List} of {@link Map}.
     *
     * @param name The name of file.<br>
     *             e.g. com/foo/File.yml
     * @return {@link List} of {@link Map} if the file exists.<br>
     * {@link RuntimeError } otherwise.
     */
    public static List<Map<String, Object>> toMapsFromFile(String name) {
        return toMapsFromFile(name, Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link List} of {@link Map}.
     *
     * @param name The name of file.<br>
     *             e.g. com/foo/File.yml
     * @param cs   A {@link Charset}.
     * @return {@link List} of {@link Map} if the file exists.<br>
     * {@link RuntimeError} otherwise.
     */
    public static List<Map<String, Object>> toMapsFromFile(String name, Charset cs) {
        InputStream is = getInputStream(name);
        return toMapsFromInputStream(is, cs);
    }

    /**
     * Read Yaml file as {@link List} of {@link Map}.
     *
     * @param name The name of resource.<br>
     *             e.g. com/foo/File.yml
     * @return {@link List} of {@link Map} if the file exists.<br>
     * {@link RuntimeError} otherwise.
     */
    public static List<Map<String, Object>> toMapsFromResource(String name) {
        return toMapsFromResource(name, Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link List} of {@link Map}.
     *
     * @param name The name of resource.<br>
     *             e.g. com/foo/File.yml
     * @param cs   A {@link Charset}.
     * @return {@link List} of {@link Map} if the file exists.<br>
     * {@link RuntimeError} otherwise.
     */
    public static List<Map<String, Object>> toMapsFromResource(String name, Charset cs) {
        InputStream is = Resources.getInputStream(name);
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

    /**
     * Set the path name of Yaml file.
     *
     * @param name The name of Yaml file.<br>
     *             e.g. src/test/resources/com/foo/File.yml
     * @return The {@link Model}.
     */
    public Model fromFile(String name) {
        _inputStream = getInputStream(name);
        return (Model) this;
    }

    /**
     * Set resource name to get the Yaml file.
     *
     * @param name The name of resource.<br>
     *             e.g. com/foo/File.yml
     * @return The {@link Model}.
     */
    public Model fromResource(String name) {
        _inputStream = Resources.getInputStream(name);
        return (Model) this;
    }

    /**
     * Read Yaml file as {@link Model}.
     *
     * @return {@link Model} if the file exists.<br>
     * Throws {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
     */
    public Model toModel() {
        return toModel(Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link Model}.
     *
     * @param cs A {@link Charset}.
     * @return {@link Model} if the file exists.<br>
     * Throws {@link RuntimeError} if the file doesn't exist or read multiple object as single object.
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
     * Read Yaml file as {@link List} of {@link Model}.
     *
     * @return {@link List} of {@link Model} if the file exists.<br>
     * {@link List} of {@link Model} with one element when trying to single object as multiple object.
     * Throws {@link RuntimeError} if the file doesn't exist.
     */
    public List<Model> toModels() {
        return toModels(Charset.defaultCharset());
    }

    /**
     * Read Yaml file as {@link List} of {@link Model}.
     *
     * @param cs A {@link Charset}.
     * @return {@link List} of {@link Model} if the file exists.<br>
     * {@link List} of {@link Model} with one element when trying to single object as multiple object.
     * Throws {@link RuntimeError} if the file doesn't exist.
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
                        _inputStream = Resources.getInputStream(yamlFrom.resource());
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
