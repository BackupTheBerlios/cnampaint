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
 * $Id: ActionFactory.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;


/**
 * Classe utilitaire servant à la création et/ou l'initialisation d'un objet
 * <tt>Action</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public final class ActionFactory {
    //~ Constructeurs ----------------------------------------------------------

    private ActionFactory() {
    }

    //~ Méthodes ---------------------------------------------------------------

    public static Action createAction(final String name,
        final String description, final Icon icon, final KeyStroke accelerator,
        final int mnemonic) {
        return initializeAction(new NoOpAction(), name, description, icon,
            accelerator, mnemonic);
    }


    public static Action createAction(final String name,
        final String description, final Icon icon, final KeyStroke accelerator,
        final int mnemonic, final ActionListener actionListener) {
        return initializeAction(new DelegatingAction(actionListener), name,
            description, icon, accelerator, mnemonic);
    }


    public static Action createAction(final Action action,
        final ActionListener actionListener) {
        final Integer mnemonic = (Integer) action.getValue(Action.MNEMONIC_KEY);

        return createAction((String) action.getValue(Action.NAME),
            (String) action.getValue(Action.SHORT_DESCRIPTION),
            (Icon) action.getValue(Action.SMALL_ICON),
            (KeyStroke) action.getValue(Action.ACCELERATOR_KEY),
            (mnemonic != null) ? mnemonic.intValue()
                               : MnemonicUtils.NO_MNEMONIC, action);
    }


    public static Action createAction(final Action action,
        final Object actionHandler, final String methodName) {
        final Integer mnemonic = (Integer) action.getValue(Action.MNEMONIC_KEY);

        return createAction((String) action.getValue(Action.NAME),
            (String) action.getValue(Action.SHORT_DESCRIPTION),
            (Icon) action.getValue(Action.SMALL_ICON),
            (KeyStroke) action.getValue(Action.ACCELERATOR_KEY),
            (mnemonic != null) ? mnemonic.intValue()
                               : MnemonicUtils.NO_MNEMONIC,
            new DelegatingAction(actionHandler, methodName));
    }


    public static Action createAction(final String name,
        final String description, final Icon icon, final KeyStroke accelerator,
        final int mnemonic, final Object actionHandler, final String methodName) {
        return createAction(name, description, icon, accelerator, mnemonic,
            new DelegatingAction(actionHandler, methodName));
    }


    private static Action initializeAction(final Action action,
        final String name, final String description, final Icon icon,
        final KeyStroke accelerator, final int mnemonic) {
        action.putValue(Action.NAME, name);
        action.putValue(Action.SHORT_DESCRIPTION, description);
        action.putValue(Action.SMALL_ICON, icon);
        action.putValue(Action.ACCELERATOR_KEY, accelerator);

        if (mnemonic != MnemonicUtils.NO_MNEMONIC) {
            action.putValue(Action.MNEMONIC_KEY, new Integer(mnemonic));
        }

        return action;
    }
}
