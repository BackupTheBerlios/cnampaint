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
 * $Id: DelegatingAction.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.reflect.Method;

import javax.swing.AbstractAction;


/**
 * Impl�mentation de <tt>AbstractAction</tt> dont la m�thode
 * <tt>actionPerformed()</tt> est d�l�gu�e � un autre objet.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class DelegatingAction extends AbstractAction {
    //~ Champs d'instance ------------------------------------------------------

    private final ActionListener actionListener;
    private final Log            log = LogFactory.getLog(getClass());

    //~ Constructeurs ----------------------------------------------------------

    public DelegatingAction(final ActionListener actionListener) {
        super();
        this.actionListener = actionListener;
    }


    public DelegatingAction(final Object handler, final String methodName) {
        super();
        final Class handlerClass = handler.getClass();
        try {
            final Method method = handlerClass.getMethod(methodName, null);
            actionListener = new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (log.isDebugEnabled()) {
                                log.debug("Invocation de la m�thode " +
                                    methodName + " sur un objet de type " +
                                    handlerClass.getName());
                            }
                            try {
                                method.invoke(handler, null);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Impossible de trouver une m�thode sans param�tres nomm�e " +
                methodName + " dans la classe " + handlerClass.getName());
        }
    }

    //~ M�thodes ---------------------------------------------------------------

    public void actionPerformed(ActionEvent e) {
        if (actionListener == null) {
            if (log.isWarnEnabled()) {
                log.warn(
                    "Aucun actionListener configur� pour traiter l'�v�nement " +
                    e + ": aucune action effectu�e");
            }

            return;
        }
        actionListener.actionPerformed(e);
    }
}
