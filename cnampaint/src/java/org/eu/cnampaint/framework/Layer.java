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
 * $Id: Layer.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Calque, contenant des <tt>GraphicObject</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class Layer extends AbstractGraphicObject {
    //~ Champs d'instance ------------------------------------------------------

    private List    graphicObjects = new ArrayList();
    private String  name;
    private boolean hidden;
    private boolean selected;

    //~ Constructeurs ----------------------------------------------------------

    public Layer() {
        this("no-name");
    }


    public Layer(final String name) {
        super(0, 0, 256, 256);
        setName(name);
    }

    //~ Méthodes ---------------------------------------------------------------

    /**
     * Retourne l'objet graphique présent aux coordonnées (x,y).
     *
     * @param x coordonnée X
     * @param y coordonnée Y
     *
     * @return un objet graphique présent sur (x,y), ou <tt>null</tt>
     */
    public GraphicObject getGraphicObjectAt(int x, int y) {
        GraphicObject graphicObject = null;

        for (final Iterator i = getGraphicObjects().iterator(); i.hasNext();) {
            final GraphicObject go = (GraphicObject) i.next();
            if (go == null) {
                continue;
            }

            final Rectangle bounds = go.getBounds();
            if (bounds.contains(x, y)) {
                graphicObject = go;
            }
        }

        return graphicObject;
    }


    public void setGraphicObjects(List graphicObjects) {
        this.graphicObjects = graphicObjects;
    }


    /**
     * Liste des objets graphiques présents dans le calque.
     *
     * @return liste des objets graphiques
     */
    public List getGraphicObjects() {
        return graphicObjects;
    }


    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }


    /**
     * Retourne <tt>true</tt> si le calque est caché, auquel cas il n'est pas
     * dessiné.
     *
     * @return <tt>true</tt> si le calque est caché
     */
    public boolean isHidden() {
        return hidden;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * Nom du calque, destiné à faciliter la distinction entre plusieurs
     * calques.
     *
     * @return nom
     */
    public String getName() {
        return name;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Retourne <tt>true</tt> si le calque est actuellement sélectionné.
     *
     * @return <tt>true</tt> si le calque est sélectionné
     */
    public boolean isSelected() {
        return selected;
    }


    public void paint(Graphics g) {
        if (isHidden()) {
            return;
        }
        int index = 0;
        for (final Iterator i = getGraphicObjects().iterator(); i.hasNext();
                ++index) {
            final GraphicObject go = (GraphicObject) i.next();
            if (go == null) {
                continue;
            }
            go.paint(g);
        }
    }
}
