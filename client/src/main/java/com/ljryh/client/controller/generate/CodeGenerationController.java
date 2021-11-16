package com.ljryh.client.controller.generate;

import com.ljryh.client.entity.generate.ColumnData;
import com.ljryh.client.entity.generate.TableData;
import com.ljryh.client.service.generate.ICodeGenerationService;
import com.ljryh.common.entity.CallResult;
import com.ljryh.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author ljryh
 * @date 2021/5/24 13:59
 */
@Slf4j
@RestController
@RequestMapping("/sys/generate")
public class CodeGenerationController {

    @Resource
    private ICodeGenerationService codeGenerationService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CallResult findAll(@RequestBody TableData entity/*, HttpServletRequest request*/) {
        return CallResult.success(codeGenerationService.getTablesData(entity));
    }

    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody TableData entity) {
        List<ColumnData> result = codeGenerationService.getColumnData(entity);
        if (result != null) {
            return CallResult.success(result);
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/generateCode", method = RequestMethod.POST)
    public Object generateCode(@RequestBody String parameter) throws Exception {

//        String sp1 = System.getProperty("user.dir");
//        System.out.println("sp1 = " + sp1);


//        ApplicationHome home = new ApplicationHome(getClass());
//        File sysfile = home.getSource();
//        String jarPath = sysfile.getParentFile().getParentFile().toString();
//        String clsAsResource = getClass().getName().replace('.', '\\');
//
//        System.out.println(getPathFromClass(this.getClass()));

        Map<String, Object> map = JacksonUtil.jsonToMap(parameter);

        List<ColumnData> list = JacksonUtil.jsonToList(JacksonUtil.objToJson(map.get("data")), ColumnData.class);

        codeGenerationService.generateCode(list,map.get("tableName").toString());

        return CallResult.fail();
    }

    private String getPathFromClass(Class cls) throws IOException {
        String path = null;
        if (cls == null) {
            throw new NullPointerException();
        }
        URL url = getClassLocationURL(cls);
        if (url != null) {
            path = url.getPath();
            if ("jar".equalsIgnoreCase(url.getProtocol())) {
                try {
                    path = new URL(path).getPath();
                } catch (MalformedURLException e) {
                }
                int location = path.indexOf("!/");
                if (location != -1) {
                    path = path.substring(0, location);
                }
            }
            File file = new File(path.replaceAll("%20", " "));
            path = file.getCanonicalPath();
        }
        return path;
    }

    /**
     * 获取类的class文件位置的URL。这个方法是本类最基础的方法，供其它方法调用。
     */
    private URL getClassLocationURL(final Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("class that input is null");
        }
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                result = cs.getLocation();
            }
            if (result != null) {
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")) {
                            result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
                        } else if (new File(result.getFile()).isDirectory()) {
                            result = new URL(result, clsAsResource);
                        }
                    } catch (MalformedURLException ignore) {
                    }
                }
            }
        }
        if (result == null) {
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
        }
        return result;
    }
}
