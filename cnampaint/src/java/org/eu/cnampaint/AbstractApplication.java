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
 * $Id: AbstractApplication.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implémentation abstraite de l'interface <tt>Application</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public abstract class AbstractApplication implements Application {
    //~ Champs d'instance ------------------------------------------------------

    private final Log   log   = LogFactory.getLog(getClass());
    private final Model model = new Model();

    //~ Méthodes ---------------------------------------------------------------

    public Model getModel() {
        return model;
    }


    public void dispose() {
        if (log.isInfoEnabled()) {
            log.info("Libération des ressources");
        }
    }


    public void fatal(Throwable e) {
        if (log.isFatalEnabled()) {
            log.fatal("Erreur fatale", e);
        }
        System.exit(1);
    }


    public void init() {
        if (log.isInfoEnabled()) {
            log.info("Initialisation");
        }
    }


    public void start() {
        if (log.isInfoEnabled()) {
            log.info("Démarrage");
        }
    }


    public void stop() {
        if (log.isInfoEnabled()) {
            log.info("Arrêt");
        }
    }
}
