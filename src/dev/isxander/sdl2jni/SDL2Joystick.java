package dev.isxander.sdl2jni;

import org.libsdl.SDL;

import java.io.Closeable;

public class SDL2Joystick implements Closeable {
    private final long ptrJoystick;
    private boolean closed;

    public SDL2Joystick(int deviceIndex) {
        this.ptrJoystick = SDL.SDL_JoystickOpen(deviceIndex);
    }

    public boolean rumble(float leftMagnitude, float rightMagnitude, int length) {
        checkClosed();

        if (leftMagnitude < 0 || leftMagnitude > 1) throw new IllegalArgumentException("leftMagnitude must be between 0 and 1");
        if (rightMagnitude < 0 || rightMagnitude > 1) throw new IllegalArgumentException("rightMagnitude must be between 0 and 1");

        return SDL.SDL_JoystickRumble(ptrJoystick, (int) (leftMagnitude * 0xFFFF), (int) (rightMagnitude * 0xFFFF), length);
    }

    public PowerLevel getPowerLevel() {
        checkClosed();

        return switch (SDL.SDL_JoystickCurrentPowerLevel(ptrJoystick)) {
            case SDL.SDL_JOYSTICK_POWER_UNKNOWN -> PowerLevel.UNKNOWN;
            case SDL.SDL_JOYSTICK_POWER_EMPTY -> PowerLevel.EMPTY;
            case SDL.SDL_JOYSTICK_POWER_LOW -> PowerLevel.LOW;
            case SDL.SDL_JOYSTICK_POWER_MEDIUM -> PowerLevel.MEDIUM;
            case SDL.SDL_JOYSTICK_POWER_FULL -> PowerLevel.FULL;
            case SDL.SDL_JOYSTICK_POWER_WIRED -> PowerLevel.WIRED;
            case SDL.SDL_JOYSTICK_POWER_MAX -> PowerLevel.MAX;
            default -> throw new IllegalStateException("Unknown power level");
        };
    }

    private void checkClosed() {
        if (closed) throw new IllegalStateException("Joystick is closed");
    }

    @Override
    public void close() {
        SDL.SDL_JoystickClose(ptrJoystick);
        closed = true;
    }
}
