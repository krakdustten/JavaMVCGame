package me.dylan.mvcGame.jython;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class JythonRunner {
    //TODO make runner for jython
    //TODO make error system
    //TODO make python code checker

    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys\n" +
                "\n" +
                "def funcName(tekst):\n" +
                //"    for x in range(0, 100):\n" +
                //"        print x\n" +
                "    print tekst\n" +
                "    tekst = \"wauw \" + tekst\n" +
                "    tekst = str(glob) + tekst\n" +
                "    return tekst\n");
// execute a function that takes a string and returns a string
        PyObject someFunc = interpreter.get("funcName");
        interpreter.set("glob", 50);
        long start = System.nanoTime();
        for(int i = 0; i < 10000; i++) {
            PyObject result = someFunc.__call__(new PyString("Test!" + i));
            String realResult = (String) result.__tojava__(String.class);
            System.out.println("out: " + realResult);
        }
        long time = System.nanoTime() - start;
        System.out.println(time / 1000000000.0);
    }
}
