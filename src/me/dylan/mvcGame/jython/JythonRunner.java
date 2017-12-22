package me.dylan.mvcGame.jython;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.lang.reflect.Array;

/**
 * A class that can run python code.
 *
 * @author Dylan Gybels
 */
public class JythonRunner {
    private PythonInterpreter interpreter;
    private GameModel model;

    /**
     * Create a new python runner with the game model.
     *
     * @param model The game model.
     */
    public JythonRunner(GameModel model){
        interpreter = new PythonInterpreter();
        this.model = model;
    }

    /**
     * Compile the given code and replace this with the previously compiled code.
     * If there is an error return false and set the error in the error variable on the game model.
     *
     * @param code The code to compile.
     * @return If the compilation was successful.
     */
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

    /**
     * Run a method in the compiled code with the given arguments.
     * If there was an error return null and set the error in the error variable on the game model.
     *
     * @param methodName The name of the method to run.
     * @param args The arguments for the function.
     * @return The return of the method.
     */
    public PyObject runMethod(String methodName, Object... args){
        try{
            PyObject someFunc = interpreter.get(methodName);
            if(someFunc == null) return null;
            PyObject[] pyO = new PyObject[args.length];
            for(int i = 0; i < args.length; i++){
                pyO[i] = Py.java2py(args[i]);
            }
            return someFunc.__call__(pyO);
        }catch (Exception e){
            model.setError(model.getError() + e);
            return null;
        }
    }

    /**
     * Set a global variable in the code.
     *
     * @param name The name of the variable.
     * @param o The thing to set the variable on.
     */
    public void setVar(String name, Object o){
        interpreter.set(name, o);
    }

    /**
     * Set a hole bunch of float variables at once.
     *
     * @param names The names of the variables.
     * @param o The float values to set to the variables.
     */
    public void setVarArray(String[] names, float[] o){
        if(names.length != o.length)return;
        for(int i = 0; i < names.length; i++)
            setVar(names[i], o[i]);
    }

    /**
     * Get a global variable from the code.
     *
     * @param name The name of the variable.
     * @param type The type of the variable.
     * @param <T> The type of the variable.
     * @return The value of the variable.
     */
    public <T> T getVar(String name, Class<T> type){
        return interpreter.get(name, type);
    }

    /**
     * Get a hole bunch of variables at once from the code.
     * All of these variable need to have the same type.
     *
     * @param names The names of the variables.
     * @param type The type of the variables.
     * @param <T> The type of the variables.
     * @return The values of the variables in array form.
     */
    public <T> T[] getVarArray(String[] names, Class<T> type){
        T[] output = (T[]) Array.newInstance(type, names.length);
        for(int i = 0; i < names.length; i++)
            output[i] = getVar(names[i], type);
        return output;
    }

    /**
     * Cleanup the class.
     */
    public void distroy() {
        interpreter.cleanup();
        interpreter.close();
    }
}
