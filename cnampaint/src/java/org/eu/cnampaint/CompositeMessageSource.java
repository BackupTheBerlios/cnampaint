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
 * $Id: CompositeMessageSource.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint;

import java.util.Collection;
import java.util.Iterator;


/**
 * Implémentation d'un <tt>MessageSource</tt> permettant l'aggréation de
 * plusieurs <tt>MessageSource</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class CompositeMessageSource extends AbstractMessageSource {
    //~ Champs d'instance ------------------------------------------------------

    private final Collection messageSources;

    //~ Constructeurs ----------------------------------------------------------

    public CompositeMessageSource(final Collection messageSources) {
        if (messageSources == null) {
            throw new IllegalArgumentException("messageSources est requis");
        }
        this.messageSources = messageSources;
    }

    //~ Méthodes ---------------------------------------------------------------

    /**
     * Retourne le premier message indexé sur <tt>key</tt> disponible parmi la
     * liste des <tt>MessageSource</tt> enregistrés.
     *
     * @param key clé
     *
     * @return message, ou <tt>null</tt>
     */
    public String getMessage(String key) {
        for (final Iterator i = messageSources.iterator(); i.hasNext();) {
            final MessageSource messageSource = (MessageSource) i.next();
            final String        msg = messageSource.getMessage(key);
            if (msg != null) {
                return msg;
            }
        }

        return null;
    }
}
