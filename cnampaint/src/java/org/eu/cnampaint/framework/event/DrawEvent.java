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
 * $Id: DrawEvent.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework.event;

import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.GraphicObject;

import java.util.EventObject;


/**
 * Ev�nement surgissant au sein d'un dessin.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class DrawEvent extends EventObject {
    //~ Champs d'instance ------------------------------------------------------

    private GraphicObject graphicObject;

    //~ Constructeurs ----------------------------------------------------------

    public DrawEvent(final Draw source) {
        super(source);
    }


    public DrawEvent(final Draw source, final GraphicObject go) {
        this(source);
        this.graphicObject = go;
    }

    //~ M�thodes ---------------------------------------------------------------

    /**
     * Dessin � la source de l'�v�nement.
     *
     * @return dessin source de l'�v�nement
     */
    public Draw getDraw() {
        return (Draw) getSource();
    }


    /**
     * Objet graphique concern� par l'�v�nement, s'il y en a un.
     *
     * @return l'objet graphique concern�, ou <tt>null</tt>
     */
    public GraphicObject getGraphicObject() {
        return graphicObject;
    }
}
