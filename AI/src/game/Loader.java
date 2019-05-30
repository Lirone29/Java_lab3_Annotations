package game;

import annotations.difficultyLevel;
import annotations.game;
import supporters.difficultyEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Vector;

public class Loader {

    private String nameClass;
    Object strategyObject;
    Class<?> classStrategy = null;
    URLClassLoader urlClassLoader;
    Vector<String > classesAnnotatnions;
    private Constructor<?> strategyClassConstructor;
    Method[] methods;
    ArrayList<Annotation[]> annotations;
    private Method easyMethod, averageMethod, expertMethod;
    Object[] arguments;
    public static void main(String[] args) {}

    String path;
    public Loader(String path, String name) {

        path = path;
        classesAnnotatnions = new Vector();

        urlClassLoader = null;


        System.out.println("1s = " + path);
        path = path.replaceAll(name, "");

        path = path.replace("\\strategies", "");
        path = path.replace("\\", "\\\\");
        path = "file:/" + path + "/";

        name = name.replace(".class", "");
        this.nameClass = name;
        name = "strategies." + name;

        //URL
        try {
            urlClassLoader = new URLClassLoader(new URL[]{new URL(path)});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Klasa
            classStrategy = urlClassLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Klasa");
            e.printStackTrace();
        }
        try {
            //Konstruktor
            strategyClassConstructor = classStrategy.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            //Obiekt
            strategyObject = strategyClassConstructor.newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        if (classStrategy.isAnnotationPresent(game.class)) {
            System.out.println("PRESENT!!!");
        }

        methods = classStrategy.getDeclaredMethods();

        annotations = new ArrayList<>();

        arguments = new Object[] { strategyObject };

        for (Method method : methods) {
            if (method.isAnnotationPresent(difficultyLevel.class)) {
                switch (method.getAnnotation(difficultyLevel.class).level()) {
                    case expert:
                        expertMethod = method;
                        break;
                    case average:
                        averageMethod = method;
                        break;
                    case easy:
                        easyMethod = method;
                        break;
                }
            }
        }


    }
    public int opponentMove(Vector<String> stringButtonVectorTmp,int size, difficultyEnum diff){

        switch (diff) {
            case expert:
                try {
                    Object objectMethod =expertMethod.invoke(strategyObject,stringButtonVectorTmp,size);
                    return  ((Integer) objectMethod ).intValue();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case average:
                try {
                    return (int) averageMethod.invoke(strategyObject,stringButtonVectorTmp,size);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case easy:
                try {
                    return (int) easyMethod.invoke(strategyObject,stringButtonVectorTmp,size);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
        }
        return -1;
    }

    public String getName() {
        return nameClass;
    }

    public String getPath(){
        return path;
    }

}
