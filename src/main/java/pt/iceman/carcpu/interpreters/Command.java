package pt.iceman.carcpu.interpreters;

/**
 * Created by iceman on 18/07/16.
 */
public class Command {
    private Class clazz;
    private byte[] values;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public byte[] getValues() {
        return values;
    }

    public void setValues(byte[] values) {
        this.values = values;
    }
}
