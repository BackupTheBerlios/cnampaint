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
 * $Id: MessageSourceActionLoader.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.MessageSource;

import java.awt.event.ActionListener;

import java.net.URL;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


/**
 * Implémentation de <tt>ActionLoader</tt> basée sur un objet
 * <tt>MessageSource</tt>. Les propriétés d'une action sont initialisées à
 * partir des messages suivants :
 * 
 * <ul>
 * <li>
 * <tt>"id"</tt> : nom
 * </li>
 * <li>
 * <tt>id</tt>.desc : description
 * </li>
 * <li>
 * <tt>id</tt>.icon : chemin vers l'image servant d'icône
 * </li>
 * <li>
 * <tt>id</tt>.accel : combinaison clavier
 * </li>
 * </ul>
 * 
 * Le nom de l'action peut contenir le caractère '&amp;' qui indique le
 * caractère dit "mnemonic", c'est-à-dire celui qui est représenté par un
 * caractère souligné.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class MessageSourceActionLoader implements ActionLoader {
    //~ Initialisateurs et champs de classe ------------------------------------

    private static final String MNEMONIC = "&";

    //~ Champs d'instance ------------------------------------------------------

    private final Log           log           = LogFactory.getLog(getClass());
    private final MessageSource messageSource;

    //~ Constructeurs ----------------------------------------------------------

    public MessageSourceActionLoader(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //~ Méthodes ---------------------------------------------------------------

    public Action load(String id) {
        final String title       = messageSource.getMessage(id);
        final String name        = MnemonicUtils.removeMnemonicHint(title);
        final String description = messageSource.getMessage(id + ".desc");

        Icon         icon    = null;
        final String iconUrl = messageSource.getMessage(id + ".icon");
        if (iconUrl != null) {
            final URL url = getClass().getResource(iconUrl);
            if (url != null) {
                icon = new ImageIcon(url);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Image introuvable: " + iconUrl);
                }
            }
        }

        final KeyStroke accel = KeyStroke.getKeyStroke(messageSource.getMessage(id +
                    ".accel"));

        final int       mnemonic = MnemonicUtils.extractMnemonic(title);

        return ActionFactory.createAction(name, description, icon, accel,
            mnemonic);
    }


    public Action load(String id, ActionListener actionListener) {
        return ActionFactory.createAction(load(id), actionListener);
    }


    public Action load(String id, Object actionHandler, String methodName) {
        return ActionFactory.createAction(load(id), actionHandler, methodName);
    }
}
