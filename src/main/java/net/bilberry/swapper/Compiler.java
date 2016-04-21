package net.bilberry.swapper;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compiler {
    public static Boolean execute(final String className, final String source) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        List<JavaFileObject> sources = new ArrayList<>();
        List<String> options = new ArrayList<>();

        sources.add(new StringSourceJavaFileObject(className, source));

        options.addAll(Arrays.asList("-classpath", applicationClassPath()));

        return compiler.getTask(null, null, null, options, null, sources).call();
    }

    public static String applicationClassPath() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }

        final String appClassPath = Compiler.class.getProtectionDomain().getCodeSource().getLocation().getFile()
                .replace("classes/" + Compiler.class.getCanonicalName().replace('.', '/') + ".class", "");

        String s = new StringBuilder()
                .append(appClassPath).append("classes/").append(":")
                .append(appClassPath).append("lib/*").append(":")
                .append(System.getProperty("java.class.path"))
                .toString();

        System.out.println(s);
        return s;
    }

    static class StringSourceJavaFileObject extends SimpleJavaFileObject {
        final String source;

        /**
         * Construct a SimpleJavaFileObject of the given kind and with the
         * given URI.
         */
        StringSourceJavaFileObject(final String className, final String source) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);

            this.source = source;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }
    }
}
