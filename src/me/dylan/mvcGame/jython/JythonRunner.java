package me.dylan.mvcGame.jython;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.lang.reflect.Array;

public class JythonRunner {
    private PythonInterpreter interpreter;
    private GameModel model;

    public JythonRunner(GameModel model){
        interpreter = new PythonInterpreter();
        this.model = model;
    }

    public boolean compileCode(String code){
        code = "import sys\\n" + code;

        try{
            interpreter.cleanup();
            interpreter.close();
            interpreter = new PythonInterpreter();
            interpreter.exec(code);
            return true;
        }catch (Exception e){
            model.setError(model.getError() + e);
            return false;
        }
    }

    public PyObject runMethod(String methodName, Object... args){
        try{
            PyObject someFunc = interpreter.get(methodName);
            if(someFunc == null) return null;
            PyObject[] pyO = new PyObject[args.length];
            for(int i = 0; i < args.length; i++){
                pyO[i] = Py.java2py(args[i]);
            }
            PyObject result = someFunc.__call__(pyO);
            return result;
        }catch (Exception e){
            model.setError(model.getError() + e);
            return null;
        }
    }

    public void setVar(String name, Object o){
        interpreter.set(name, o);
    }

    public <T> void setVarArray(String[] names, T[] o){
        if(names.length != o.length)return;
        for(int i = 0; i < names.length; i++)
            setVar(names[i], o[i]);
    }

    public void setVarArray(String[] names, float[] o){
        if(names.length != o.length)return;
        for(int i = 0; i < names.length; i++)
            setVar(names[i], o[i]);
    }

    public <T> T getVar(String name, Class<T> type){
        return interpreter.get(name, type);
    }

    public <T> T[] getVarArray(String[] names, Class<T> type){
        T[] output = (T[]) Array.newInstance(type, names.length);
        for(int i = 0; i < names.length; i++)
            output[i] = getVar(names[i], type);
        return output;
    }


    /*public static void main(String[] args) {
        JythonRunner runner = new JythonRunner();

        String code = "\n" +
                "def funcName(tekst):\n" +
                "    global MotorL\n" +
                "    tekst = \"wauw \" + tekst\n" +
                "    tekst = str(glob) + tekst\n" +
                "    MotorL = MotorL + glob\n" +
                "    return tekst\n";

        System.out.println(runner.compileCode(code));
        runner.setVar("glob", 50);
        runner.setVar("MotorL", 0);

        long start = System.nanoTime();
        for(int i = 0; i < 10000; i++) {
            PyObject result = runner.runMethod("funcName", ("Test!" + i));
            String realResult = (String) result.__tojava__(String.class);
            System.out.println("out: " + realResult);
            System.out.println("Mot: " + runner.getVar("MotorL", Integer.class));
        }
        long time = System.nanoTime() - start;
        System.out.println(time / 1000000000.0);
    }*/
}
