package org.libsdl;

import java.nio.file.Path;

final public class SDL {
    /*JNI

    #include "SDL.h"

    */

    public static void load(Path libraryPath) {
        System.load(libraryPath.toAbsolutePath().toString());
    }

    // joystick.h

    public static native void SDL_JoystickClose(long ptr);/*
        SDL_JoystickClose((SDL_Joystick *) ptr);
    */


    public static native int SDL_JoystickCurrentPowerLevel(long ptr);/*
            return SDL_JoystickCurrentPowerLevel((SDL_Joystick *) ptr);
    */

    public static native int SDL_JoystickEventState(int state); /*
        return SDL_JoystickEventState(state);
    */

    public static native long SDL_JoystickFromInstanceID(int joyid); /*
        return (uintptr_t)SDL_JoystickFromInstanceID(joyid);
    */

    public static native boolean SDL_JoystickGetAttached(long ptr);/*
        SDL_Joystick* pad = (SDL_Joystick*) ptr;
        if (pad && SDL_JoystickGetAttached(pad)==SDL_TRUE) {
            return JNI_TRUE;
        }
        return JNI_FALSE;
    */


    public static native int SDL_JoystickGetAxis(long ptr, int axis); /*
        return SDL_JoystickGetAxis((SDL_Joystick*) ptr, axis);
    */

    // TODO
//    public static native int SDL_JoystickGetBall(long ptr, int ball); /*
//        int* dx;
//        int* dy;
//        SDL_JoystickGetBall((SDL_Joystick*) ptr, ball, dx, dy);
//        return SDL_JoystickGetAxis((SDL_Joystick*) ptr, axis);
//    */

    public static native int SDL_JoystickGetButton(long ptr, int button); /*
        return SDL_JoystickGetButton((SDL_Joystick*) ptr, button);
    */

    // TODO
    //SDL_JoystickGetDeviceGUID
    //TODO
    //SDL_JoystickGetGUID
    //TODO
    //SDL_JoystickGetGUIDFromString
    //TODO
    //SDL_JoystickGetGUIDString

    public static native int SDL_JoystickGetHat(long ptr, int hat); /*
        return SDL_JoystickGetHat((SDL_Joystick*) ptr, hat);
    */

    public static native int SDL_JoystickInstanceID(long ptr); /*
        return SDL_JoystickInstanceID((SDL_Joystick*) ptr);
    */

    public static native String SDL_JoystickName(long ptr); /*
        return  env->NewStringUTF(SDL_JoystickName((SDL_Joystick*) ptr));
    */

    public static native String SDL_JoystickNameForIndex(int i); /*
        return env->NewStringUTF(SDL_JoystickNameForIndex(i));
    */

    public static native int SDL_JoystickNumAxes(long ptr); /*
        return SDL_JoystickNumAxes((SDL_Joystick*) ptr);
    */

    public static native int SDL_JoystickNumBalls(long ptr); /*
        return SDL_JoystickNumBalls((SDL_Joystick*) ptr);
    */

    public static native int SDL_JoystickNumButtons(long ptr); /*
        return SDL_JoystickNumButtons((SDL_Joystick*) ptr);
    */


    public static native int SDL_JoystickNumHats(long ptr); /*
        return SDL_JoystickNumHats((SDL_Joystick*) ptr);
    */


    public static native long SDL_JoystickOpen(int i); /*
        return (uintptr_t)SDL_JoystickOpen(i);
    */

    public static native void SDL_JoystickUpdate();/*
         SDL_JoystickUpdate();
    */

    public static native int SDL_NumJoysticks(); /*
        return SDL_NumJoysticks();
    */

    public static native int SDL_JoystickGetDeviceInstanceID(int device); /*
        return SDL_JoystickGetDeviceInstanceID(device);
    */

    public static native int SDL_JoystickGetDeviceProductVersion(int device_id); /*
        return SDL_JoystickGetDeviceProductVersion(device_id);
     */

    public static native boolean SDL_JoystickRumble(long ptr, int leftMagnitude, int rightMagnitude, int duration_ms); /*
        return SDL_JoystickRumble((SDL_Joystick *)ptr, leftMagnitude, rightMagnitude,  duration_ms) == 0;
    */

    public static native boolean SDL_JoystickHasRumble(long ptr); /*
        return SDL_JoystickHasRumble((SDL_Joystick *)ptr);
    */

    public static native boolean SDL_JoystickRumbleTriggers(long ptr, int leftMagnitude, int rightMagnitude, int duration_ms); /*
        return SDL_JoystickRumbleTriggers((SDL_Joystick *)ptr, leftMagnitude, rightMagnitude,  duration_ms) == 0;
    */

    public static native boolean SDL_JoystickHasRumbleTriggers(long ptr); /*
        return SDL_JoystickHasRumbleTriggers((SDL_Joystick *)ptr);
    */

    public static native boolean SDL_JoystickHasLED(long ptr); /*
        return SDL_JoystickHasLED((SDL_Joystick *)ptr);
    */

    public static native boolean SDL_JoystickSetLED(long ptr, byte r, byte g, byte b); /*
        return SDL_JoystickSetLED((SDL_Joystick *)ptr, r, g, b) == 0;
    */

    public static native String SDL_GameControllerGetStringForAxis(int axis); /*
        return  env->NewStringUTF(SDL_GameControllerGetStringForAxis((SDL_GameControllerAxis)axis));
    */

    public static native String SDL_GameControllerGetStringForButton(int button); /*
        return  env->NewStringUTF(SDL_GameControllerGetStringForButton((SDL_GameControllerButton)button));
    */

    public static native int SDL_JoystickGetDeviceVendor(int device_index); /*
        return SDL_JoystickGetDeviceVendor(device_index);
    */

    public static native int SDL_JoystickGetVendor(long ptr); /*
        return SDL_JoystickGetVendor((SDL_Joystick*) ptr);
    */

    public static native int SDL_JoystickGetDeviceProduct(int device_index); /*
        return SDL_JoystickGetDeviceProduct(device_index);
    */

    public static native int SDL_JoystickGetProduct(long ptr); /*
        return SDL_JoystickGetProduct((SDL_Joystick*) ptr);
    */

    public static native String SDL_JoystickPathForIndex(int device_index); /*
        return env->NewStringUTF(SDL_JoystickNameForIndex(device_index));
    */

    // GameController.h

    public static final int SDL_CONTROLLER_BINDTYPE_NONE = 0,
            SDL_CONTROLLER_BINDTYPE_BUTTON = 1,
            SDL_CONTROLLER_BINDTYPE_AXIS = 2,
            SDL_CONTROLLER_BINDTYPE_HAT = 3;


    /**
     * The list of axes available from a controller
     * <p>
     * Thumbstick axis values range from SDL_JOYSTICK_AXIS_MIN to SDL_JOYSTICK_AXIS_MAX,
     * and are centered within ~8000 of zero, though advanced UI will allow users to set
     * or autodetect the dead zone, which varies between controllers.
     * <p>
     * Trigger axis values range from 0 to SDL_JOYSTICK_AXIS_MAX.
     */

    public static final int SDL_CONTROLLER_AXIS_INVALID = -1,
            SDL_CONTROLLER_AXIS_LEFTX = 0,
            SDL_CONTROLLER_AXIS_LEFTY = 1,
            SDL_CONTROLLER_AXIS_RIGHTX = 2,
            SDL_CONTROLLER_AXIS_RIGHTY = 3,
            SDL_CONTROLLER_AXIS_TRIGGERLEFT = 4,
            SDL_CONTROLLER_AXIS_TRIGGERRIGHT = 5,
            SDL_CONTROLLER_AXIS_MAX = 6;

    /**
     * The list of buttons available from a controller
     */

    public static final int SDL_CONTROLLER_BUTTON_INVALID = -1,
            SDL_CONTROLLER_BUTTON_A = 0,
            SDL_CONTROLLER_BUTTON_B = 1,
            SDL_CONTROLLER_BUTTON_X = 2,
            SDL_CONTROLLER_BUTTON_Y = 3,
            SDL_CONTROLLER_BUTTON_BACK = 4,
            SDL_CONTROLLER_BUTTON_GUIDE = 5,
            SDL_CONTROLLER_BUTTON_START = 6,
            SDL_CONTROLLER_BUTTON_LEFTSTICK = 7,
            SDL_CONTROLLER_BUTTON_RIGHTSTICK = 8,
            SDL_CONTROLLER_BUTTON_LEFTSHOULDER = 9,
            SDL_CONTROLLER_BUTTON_RIGHTSHOULDER = 10,
            SDL_CONTROLLER_BUTTON_DPAD_UP = 11,
            SDL_CONTROLLER_BUTTON_DPAD_DOWN = 12,
            SDL_CONTROLLER_BUTTON_DPAD_LEFT = 13,
            SDL_CONTROLLER_BUTTON_DPAD_RIGHT = 14,
            SDL_CONTROLLER_BUTTON_MAX = 15;


    public static final int SDL_HAT_CENTERED = 0x00,
            SDL_HAT_UP = 0x01,
            SDL_HAT_RIGHT = 0x02,
            SDL_HAT_DOWN = 0x04,
            SDL_HAT_LEFT = 0x08,
            SDL_HAT_RIGHTUP = (SDL_HAT_RIGHT|SDL_HAT_UP),
            SDL_HAT_RIGHTDOWN = (SDL_HAT_RIGHT|SDL_HAT_DOWN),
            SDL_HAT_LEFTUP = (SDL_HAT_LEFT|SDL_HAT_UP),
            SDL_HAT_LEFTDOWN =  (SDL_HAT_LEFT|SDL_HAT_DOWN);


    public static final int SDL_JOYSTICK_POWER_UNKNOWN = -1,
                SDL_JOYSTICK_POWER_EMPTY = 0,   /* <= 5% */
                SDL_JOYSTICK_POWER_LOW = 1,     /* <= 20% */
                SDL_JOYSTICK_POWER_MEDIUM = 2,  /* <= 70% */
                SDL_JOYSTICK_POWER_FULL = 3,    /* <= 100% */
                SDL_JOYSTICK_POWER_WIRED = 4,
                SDL_JOYSTICK_POWER_MAX = 5;

    public static final int SDL_CONTROLLER_TYPE_UNKNOWN = 0,
                SDL_CONTROLLER_TYPE_XBOX360 = 1,
                SDL_CONTROLLER_TYPE_XBOXONE = 2,
                SDL_CONTROLLER_TYPE_PS3 = 3,
                SDL_CONTROLLER_TYPE_PS4 = 4,
                SDL_CONTROLLER_TYPE_PS5 = 7,
                SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_PRO = 5,
                SDL_CONTROLLER_TYPE_VIRTUAL = 6,
                SDL_CONTROLLER_TYPE_AMAZON_LUNA = 8,
                SDL_CONTROLLER_TYPE_GOOGLE_STADIA = 9,
                SDL_CONTROLLER_TYPE_NVIDIA_SHIELD = 10,
                SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_LEFT = 11,
                SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_RIGHT = 12,
                SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_PAIR = 13;


    public static native int SDL_GameControllerAddMapping(String mappingString);/*
        return SDL_GameControllerAddMapping(mappingString);
    */

    // TODO //SDL_GameControllerAddMappingsFromRW

    public static native int SDL_GameControllerAddMappingsFromFile(String path); /*

     printf("NATIVE METHOD: loading from  \"%s\"\n", path);
     int result = SDL_GameControllerAddMappingsFromFile(path);
     printf("             result %d  %s\n", result, SDL_GetError());
        return result;
    */

    public static native void SDL_GameControllerClose(long ptr);/*
        SDL_GameControllerClose((SDL_GameController *) ptr);
    */


    public static native int SDL_GameControllerEventState(int state); /*
        return SDL_GameControllerEventState(state);
    */

    public static native long SDL_GameControllerFromInstanceID(int joyid); /*
        return (uintptr_t)SDL_GameControllerFromInstanceID(joyid);
    */

    public static native boolean SDL_GameControllerGetAttached(long ptr); /*
        SDL_GameController* pad = (SDL_GameController*) ptr;
        if (pad && SDL_GameControllerGetAttached(pad)==SDL_TRUE) {
            return JNI_TRUE;
        }
        return JNI_FALSE;
    */

    public static native int SDL_GameControllerGetAxis(long ptr, int axis); /*
        return SDL_GameControllerGetAxis((SDL_GameController*) ptr, (SDL_GameControllerAxis) axis);
    */

    public static native String SDL_JoystickGUIDString(long ptr); /*
          char guid[64];
          SDL_JoystickGetGUIDString(SDL_JoystickGetGUID((SDL_Joystick*)ptr), guid, sizeof (guid));
          return env->NewStringUTF(guid);
    */


    // TODO
//SDL_GameControllerGetAxisFromString
//SDL_GameControllerGetBindForAxis
//SDL_GameControllerGetBindForButton

    public static native int SDL_GameControllerGetButton(long ptr, int button); /*
        return SDL_GameControllerGetButton((SDL_GameController*) ptr, (SDL_GameControllerButton)button);
    */

    // TODO
    //SDL_GameControllerGetButtonFromString
    public static native long SDL_GameControllerGetJoystick(long ptr);/*
        return (uintptr_t)SDL_GameControllerGetJoystick((SDL_GameController*)ptr);
    */
    //SDL_GameControllerGetStringForAxis
    //SDL_GameControllerGetStringForButton
    //SDL_GameControllerMapping
    //SDL_GameControllerMappingForGUID


    public static native String SDL_GameControllerName(long ptr); /*
        return  env->NewStringUTF(SDL_GameControllerName((SDL_GameController*) ptr));
    */

    public static native String SDL_GameControllerNameForIndex(int i); /*
        return env->NewStringUTF(SDL_GameControllerNameForIndex(i));
    */

    public static native String SDL_GameControllerPath(long ptr); /*
        return env->NewStringUTF(SDL_GameControllerPath((SDL_GameController*) ptr));
    */

    public static native long SDL_GameControllerFromPlayerIndex(int i); /*
         return (uintptr_t)SDL_GameControllerFromPlayerIndex(i);
    */

    public static native int SDL_GameControllerGetType(long ptr); /*
        return SDL_GameControllerGetType((SDL_GameController *)ptr);
    */

    public static native int SDL_GameControllerGetPlayerIndex(long ptr); /*
        return SDL_GameControllerGetPlayerIndex((SDL_GameController *)ptr);
    */

    public static native void SDL_GameControllerSetPlayerIndex(long ptr, int player_index); /*
        SDL_GameControllerSetPlayerIndex((SDL_GameController *)ptr, player_index);
    */

    public static native long SDL_GameControllerOpen(int i); /*
        return (uintptr_t)SDL_GameControllerOpen(i);
    */

    public static native void SDL_GameControllerUpdate();/*
        SDL_GameControllerUpdate();
    */

    public static native boolean SDL_IsGameController(int joystick_index); /*
        return (SDL_IsGameController(joystick_index)==SDL_TRUE);
    */

    public static native boolean SDL_GameControllerRumble(long ptr, int low_frequency_rumble, int high_frequency_rumble, int duration_ms); /*
        return SDL_GameControllerRumble((SDL_GameController*) ptr, low_frequency_rumble, high_frequency_rumble, duration_ms)==0;
    */

    public static native boolean SDL_GameControllerHasRumble(long ptr); /*
        return SDL_GameControllerHasRumble((SDL_GameController*) ptr)==SDL_TRUE;
    */

    public static native boolean SDL_GameControllerRumbleTriggers(long ptr, int leftMagnitude, int rightMagnitude, int duration_ms); /*
        return SDL_GameControllerRumbleTriggers((SDL_GameController*) ptr, leftMagnitude, rightMagnitude,  duration_ms) == 0;
    */

    public static native boolean SDL_GameControllerHasRumbleTriggers(long ptr); /*
        return SDL_GameControllerHasRumbleTriggers((SDL_GameController*) ptr)==SDL_TRUE;
    */

    public static native int SDL_GameControllerSetSensorEnabled(long ptr, int sensor, boolean enabled); /*
        return SDL_GameControllerSetSensorEnabled((SDL_GameController*) ptr, (SDL_SensorType) sensor, (SDL_bool) enabled);
    */

    public static native boolean SDL_GameControllerHasSensor(long ptr, int sensor); /*
        return SDL_GameControllerHasSensor((SDL_GameController*) ptr, (SDL_SensorType) sensor);
    */

    public static native boolean SDL_GameControllerIsSensorEnabled(long ptr, int sensor); /*
        return (SDL_GameControllerIsSensorEnabled((SDL_GameController*) ptr, (SDL_SensorType) sensor)==SDL_TRUE);
    */

    public static native float SDL_GameControllerGetSensorDataRate(long ptr, int sensor); /*
        return SDL_GameControllerGetSensorDataRate((SDL_GameController*) ptr, (SDL_SensorType) sensor);
    */

    public static native int SDL_GameControllerGetSensorData(long ptr, int sensor, float[] data, int num_values); /*
        return SDL_GameControllerGetSensorData((SDL_GameController*) ptr, (SDL_SensorType) sensor, data, num_values);
    */

    //// SDL_sensor.h

    public static final int SDL_SENSOR_INVALID = -1,
            SDL_SENSOR_UNKNOWN = 0,
            SDL_SENSOR_ACCEL = 1,
            SDL_SENSOR_GYRO = 2,
            SDL_SENSOR_ACCEL_L = 3,
            SDL_SENSOR_GYRO_L = 4,
            SDL_SENSOR_ACCEL_R = 5,
            SDL_SENSOR_GYRO_R = 6;


    ///// SDL.h

    public static native int SDL_Init(int flags); /*
        return SDL_Init(flags);
    */

    public static native int SDL_InitSubSystem(int flags); /*
        return SDL_InitSubSystem(flags);
    */


    public static native void SDL_QuitSubSystem(int flags); /*
        SDL_QuitSubSystem(flags);
    */

    public static native int SDL_WasInit(int flags); /*
        return SDL_WasInit(flags);
    */

    /**
     *  This function cleans up all initialized subsystems. You should
     *  call it upon all exit conditions.
     */
    public static native void SDL_Quit(); /*
        SDL_Quit();
    */

    public static native boolean SDL_SetHint(String name, String value); /*
       return (SDL_SetHint(name, value)==SDL_TRUE);
    */

//    public static int SDL_INIT_TIMER = SDL_INIT_TIMER();
//
//    private static native int SDL_INIT_TIMER(); /*
//        return SDL_INIT_TIMER;
//    */

//    public static int SDL_INIT_AUDIO = SDL_INIT_AUDIO();
//
//    private static native int SDL_INIT_AUDIO(); /*
//        return SDL_INIT_AUDIO;
//    */

//    public static int SDL_INIT_VIDEO = SDL_INIT_VIDEO();
//
//    private static native int SDL_INIT_VIDEO(); /*
//        return SDL_INIT_VIDEO;
//    */
//
//    public static int SDL_INIT_JOYSTICK = SDL_INIT_JOYSTICK();
//
//    private static native int SDL_INIT_JOYSTICK(); /*
//        return SDL_INIT_JOYSTICK;
//    */
//
//    public static int SDL_INIT_HAPTIC = SDL_INIT_HAPTIC();
//
//    private static native int SDL_INIT_HAPTIC(); /*
//        return SDL_INIT_HAPTIC;
//    */
//
//    public static int SDL_INIT_GAMECONTROLLER = SDL_INIT_GAMECONTROLLER();
//
//    private static native int SDL_INIT_GAMECONTROLLER(); /*
//        return SDL_INIT_GAMECONTROLLER;
//    */
//
//    public static int SDL_INIT_EVENTS = SDL_INIT_EVENTS();
//
//    private static native int SDL_INIT_EVENTS(); /*
//        return SDL_INIT_EVENTS;
//    */
//
//    public static int SDL_INIT_SENSOR = SDL_INIT_SENSOR();
//
//    private static native int SDL_INIT_SENSOR(); /*
//        return SDL_INIT_SENSOR;
//    */
//
//    public static int SDL_INIT_NOPARACHUTE = SDL_INIT_NOPARACHUTE();
//
//    private static native int SDL_INIT_NOPARACHUTE(); /*
//        return SDL_INIT_NOPARACHUTE;
//    */
//
//    public static int SDL_INIT_EVERYTHING = SDL_INIT_EVERYTHING();
//
//    private static native int SDL_INIT_EVERYTHING(); /*
//        return SDL_INIT_EVERYTHING;
//    */

    public static native String SDL_GetError(); /*
        return env->NewStringUTF(SDL_GetError());
    */


    public static native void SDL_PumpEvents(); /*
        SDL_PumpEvents();
    */

    public static native int SDL_JOYSTICK_AXIS_MIN(); /*
        return SDL_JOYSTICK_AXIS_MIN;
    */

    public static native int SDL_JOYSTICK_AXIS_MAX(); /*
        return SDL_JOYSTICK_AXIS_MAX;
    */





    public static native boolean SDL_PollEvent(Event jobj); /*
        SDL_Event event;
        if (SDL_PollEvent(&event)){
            jclass class_Event = env->GetObjectClass(jobj);
            jfieldID id_type = env->GetFieldID(class_Event, "type", "I");
            jfieldID id_timestamp = env->GetFieldID(class_Event, "timestamp", "I");
            env->SetIntField(jobj, id_type, event.type);
            env->SetIntField(jobj, id_timestamp, event.common.timestamp);
            return JNI_TRUE;
        }
        return JNI_FALSE;
     */

//    */
//
//    public static native int SDL_PollEventResult(); /*
//        return event
//    */

    public static native long SDL_hid_open(int vendor_id, int product_id, String serial_number); /*
        if (!serial_number) {
            return (jlong) SDL_hid_open((unsigned short) vendor_id, (unsigned short) product_id, NULL);
        }
        const jchar* nativeSerialNumber = env->GetStringChars(serial_number, NULL);
        long result = SDL_hid_open((unsigned short) vendor_id, (unsigned short) product_id, nativeSerialNumber);
        env->ReleaseStringChars(serial_number, nativeSerialNumber);
        return (jlong) result;
    */

    /**
     * Write an Output report to a HID device.
     *
     * The first byte of `data` must contain the Report ID. For devices which only
     * support a single report, this must be set to 0x0. The remaining bytes
     * contain the report data. Since the Report ID is mandatory, calls to
     * SDL_hid_write() will always contain one more byte than the report contains.
     * For example, if a hid report is 16 bytes long, 17 bytes must be passed to
     * SDL_hid_write(), the Report ID (or 0x0, for devices with a single report),
     * followed by the report data (16 bytes). In this example, the length passed
     * in would be 17.
     *
     * SDL_hid_write() will send the data on the first OUT endpoint, if one
     * exists. If it does not, it will send the data through the Control Endpoint
     * (Endpoint 0).
     *
     * @param ptr A device handle returned from SDL_hid_open().
     * @param data The data to send, including the report number as the first
     *             byte.
     * @param size The length in bytes of the data to send.
     * @return the actual number of bytes written and -1 on error.
     *
     * @since This function is available since SDL 2.0.18.
     */
    public static native int SDL_hid_write(long ptr, byte[] data, int size); /*
        jbyte* nativeData = env->GetByteArrayElements(data, NULL);
        int result = SDL_hid_write((SDL_hid_device*) ptr, nativeData, size);
        env->ReleaseByteArrayElements(data, nativeData, JNI_ABORT);
        return (jint) result;
    */

    /**
     * Read an Input report from a HID device.
     *
     * Input reports are returned to the host through the INTERRUPT IN endpoint.
     * The first byte will contain the Report number if the device uses numbered
     * reports.
     *
     * @param ptr A device handle returned from SDL_hid_open().
     * @param data A buffer to put the read data into.
     * @param size The number of bytes to read. For devices with multiple
     *               reports, make sure to read an extra byte for the report
     *               number.
     * @return the actual number of bytes read and -1 on error. If no packet was
     *          available to be read and the handle is in non-blocking mode, this
     *          function returns 0.
     *
     * @since This function is available since SDL 2.0.18.
     */
    public static native int SDL_hid_read(long ptr, byte[] data, int size); /*
        jbyte* nativeData = env->GetByteArrayElements(data, NULL);
        int result = SDL_hid_read((SDL_hid_device*) ptr, nativeData, size);
        env->ReleaseByteArrayElements(data, nativeData, JNI_ABORT);
        return (jint) result;
    */

    public static native int SDL_hid_set_nonblocking(long ptr, boolean nonblock); /*
        return SDL_hid_set_nonblocking((SDL_hid_device*) ptr, (SDL_bool) nonblock);
    */

    public static native int SDL_hid_send_feature_report(long ptr, byte[] data, int size); /*
        jbyte* nativeData = env->GetByteArrayElements(data, NULL);
        int result = SDL_hid_send_feature_report((SDL_hid_device*) ptr, nativeData, size);
        env->ReleaseByteArrayElements(data, nativeData, JNI_ABORT);
        return (jint) result;
    */

    public static native int SDL_hid_get_feature_report(long ptr, byte[] data, int size); /*
        jbyte* nativeData = env->GetByteArrayElements(data, NULL);
        int result = SDL_hid_get_feature_report((SDL_hid_device*) ptr, nativeData, size);
        env->ReleaseByteArrayElements(data, nativeData, JNI_ABORT);
        return (jint) result;
    */

    public static native void SDL_hid_close(long ptr); /*
        SDL_hid_close((SDL_hid_device*) ptr);
    */

    public record HidDeviceInfo(
            String path,
            int vendor_id,
            int product_id,
            String serial_number,
            int release_number,
            String manufacturer_string,
            String product_string,
            int usage_page,
            int usage,
            int interface_number,
            int interface_class,
            int interface_subclass,
            int interface_protocol
    ) { }

    public static class Event{
         public static int SDL_JOYDEVICEADDED=0x605;
         public static int SDL_JOYDEVICEREMOVED=0x606;
         public int type;
         public int timestamp;
    }

}
