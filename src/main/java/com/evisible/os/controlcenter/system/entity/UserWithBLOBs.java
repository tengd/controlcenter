package com.evisible.os.controlcenter.system.entity;

public class UserWithBLOBs extends User {
    private byte[] headshot;

    private byte[] qrcode;

    public byte[] getHeadshot() {
        return headshot;
    }

    public void setHeadshot(byte[] headshot) {
        this.headshot = headshot;
    }

    public byte[] getQrcode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }
}