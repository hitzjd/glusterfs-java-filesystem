package com.peircean.glusterfs;

import com.peircean.libgfapi_jni.internal.GLFS;

import java.io.IOException;
import java.nio.file.attribute.FileAttributeView;

public class GlusterFileAttributeView implements FileAttributeView {

    private GlusterFileSystem fileSystem;
    private GlusterPath path;

    public GlusterFileAttributeView(GlusterFileSystem fileSystem, GlusterPath path) {
        this.fileSystem = fileSystem;
        this.path = path;
    }

    @Override
    public String name() {
        return "Gluster";
    }

    public int setOwnerAndGroup(int uid, int gid) throws IOException {
        if (uid < 0) {
            return -1;
        }
        if (gid == -1) {
            gid = uid;
        }
        return GLFS.glfs_chown(fileSystem.getVolptr(), path.toUri().getPath(), uid, gid);
    }
}
