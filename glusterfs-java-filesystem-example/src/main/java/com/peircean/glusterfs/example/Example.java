package com.peircean.glusterfs.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

/**
 * @author <a href="http://about.me/louiszuckerman">Louis Zuckerman</a>
 */
public class Example {
    public static FileSystemProvider getProvider(String scheme) {
        for (FileSystemProvider fsp : FileSystemProvider.installedProviders()) {
            if (fsp.getScheme().equals(scheme)) {
                return fsp;
            }
        }
        throw new IllegalArgumentException("No provider found for scheme: " + scheme);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(getProvider("gluster").toString());

        String testUri = "gluster://127.0.2.1:foo/baz";
        Path path = Paths.get(testUri);
        // TODO: Paths does not return a GlusterPath, but rather a UnixPath, when given a gluster URI.  Why?
        System.out.println(path.getFileSystem().toString());

        System.out.println(path.toString());

        FileSystem fileSystem = FileSystems.newFileSystem(new URI(testUri), null);
        FileStore store = fileSystem.getFileStores().iterator().next();
        System.out.println("TOTAL SPACE: " + store.getTotalSpace());
        System.out.println("USABLE SPACE: " + store.getUsableSpace());
        System.out.println("UNALLOCATED SPACE: " + store.getUnallocatedSpace());
        System.out.println(fileSystem.toString());

        Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(posixFilePermissions);

        Path glusterPath = fileSystem.getPath("/baz");
        Files.createFile(glusterPath, attrs);
        fileSystem.close();
    }
}