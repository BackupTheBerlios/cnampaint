/*
 * Copyright (c) 2005, Alexandre ROMAN
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of the CnamPaint project nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: DefaultFileFilter.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;


/**
 * Impl�mentation d'un filtre de fichiers bas� sur des extensions.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class DefaultFileFilter extends FileFilter {
    //~ Champs d'instance ------------------------------------------------------

    private final List         extensions  = new ArrayList(1);
    private final String       description;
    private java.io.FileFilter fileFilter;

    //~ Constructeurs ----------------------------------------------------------

    public DefaultFileFilter(final String description) {
        if (description == null) {
            throw new IllegalArgumentException("description est requis");
        }
        this.description = description;
    }

    //~ M�thodes ---------------------------------------------------------------

    public String getDescription() {
        return description;
    }


    public boolean accept(File f) {
        return fileFilter.accept(f);
    }


    public void addExtension(String extension) {
        extensions.add(extension);
        fileFilter = FileFilterUtils.orFileFilter(FileFilterUtils.directoryFileFilter(),
                new SuffixFileFilter(extensions));
    }
}
