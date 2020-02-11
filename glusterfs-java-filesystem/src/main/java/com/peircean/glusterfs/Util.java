package com.peircean.glusterfs;

import java.nio.file.LinkOption;

public class Util {
    static boolean followLinks(LinkOption... var0) {
        boolean var1 = true;
        LinkOption[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            LinkOption var5 = var2[var4];
            if (var5 != LinkOption.NOFOLLOW_LINKS) {
                if (var5 == null) {
                    throw new NullPointerException();
                }

                throw new AssertionError("Should not get here");
            }

            var1 = false;
        }

        return var1;
    }
}
