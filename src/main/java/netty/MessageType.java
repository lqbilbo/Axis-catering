package netty;

public enum MessageType {

    LOGIN_REQ((byte) 1),
    LOGIN_RESP((byte) 2),
    HEARTBEAT_REQ((byte)5),
    HEARTBEAT_RESP((byte)6);

    private byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }

}
