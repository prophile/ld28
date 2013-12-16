package uk.co.alynn.one.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    private final Map<String, Runnable> _bindings;

    public void bind(String code, Runnable run) {
        _bindings.put(code, run);
    }

    private static String mappedKey(int keycode) {
        return "" + keycode;
    }

    public InputHandler() {
        _bindings = new HashMap<String, Runnable>();
    }

    @Override
    public boolean keyDown(int keycode) {
        String key = mappedKey(keycode);
        return runBinding(key);
    }

    private boolean runBinding(String key) {
        Runnable target = _bindings.get(key);
        System.err.println(key);
        if (target != null) {
            target.run();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        // ignore
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // ignore
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return runBinding("click");
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // ignore
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // ignore
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // ignore
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // ignore
        return false;
    }

}
