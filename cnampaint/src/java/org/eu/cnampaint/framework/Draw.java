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
 * $Id: Draw.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import org.eu.cnampaint.framework.event.DrawEvent;
import org.eu.cnampaint.framework.event.DrawListener;

import java.awt.Dimension;
import java.awt.Graphics;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Dessin, contenant des calques.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class Draw extends BaseObject implements Paintable, Serializable {
    //~ Champs d'instance ------------------------------------------------------

    private transient List drawListeners;
    private List           layers           = new ArrayList();
    private int            activeLayerIndex;
    private int            height           = 256;
    private int            width            = 256;

    //~ Constructeurs ----------------------------------------------------------

    public Draw() {
        super();
        layers.add(new Layer("Fond"));
    }


    public Draw(final int width, final int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    //~ Méthodes ---------------------------------------------------------------

    public void setActiveLayerIndex(int activeLayerIndex) {
        this.activeLayerIndex = activeLayerIndex;
    }


    /**
     * Index du calque actif. Le calque actif est obtenu avec
     * <tt>getLayers().get(getActiveLayerIndex())</tt>.
     *
     * @return index du calque actif
     */
    public int getActiveLayerIndex() {
        return activeLayerIndex;
    }


    /**
     * Retourne l'objet graphique présent, dans le calque actif, aux
     * coordonnées <tt>x</tt> et <tt>y</tt>. Obtient le résultat grâce à la
     * méthode <tt>getGraphicObjectAt()</tt> de <tt>Layer</tt>.
     *
     * @param x coordonnée X
     * @param y coordonnée Y
     *
     * @return l'objet graphique dans le calque actif présent sur (x,y), ou
     *         <tt>null</tt>
     */
    public GraphicObject getGraphicObjectAt(int x, int y) {
        final Layer layer = (Layer) getLayers().get(getActiveLayerIndex());

        return layer.getGraphicObjectAt(x, y);
    }


    public void setHeight(int height) {
        this.height = height;
    }


    /**
     * Hauteur.
     *
     * @return hauteur
     */
    public int getHeight() {
        return height;
    }


    public void setLayers(List layers) {
        this.layers = layers;
    }


    /**
     * Calques du dessin.
     *
     * @return une liste de <tt>Layer</tt>
     */
    public List getLayers() {
        return layers;
    }


    /**
     * Dimension du dessin.
     *
     * @return un objet <tt>Dimension</tt> décrivant la dimension du dessin
     */
    public Dimension getSize() {
        return new Dimension(width, height);
    }


    public void setWidth(int width) {
        this.width = width;
    }


    /**
     * Largeur.
     *
     * @return largeur
     */
    public int getWidth() {
        return width;
    }


    /**
     * Ajout d'un <tt>DrawListener</tt>, qui sera averti des évènements
     * relatifs au dessin.
     *
     * @param l <tt>DrawListener</tt> à ajouter
     */
    public void addDrawListener(DrawListener l) {
        if (drawListeners == null) {
            drawListeners = new ArrayList();
        }
        drawListeners.add(l);
    }


    /**
     * Méthode à appeler après qu'un objet graphique est été ajouté à un
     * calque. Propage l'évènement parmi les <tt>DrawListener</tt>
     * enregistrés.
     *
     * @param graphicObject objet graphique ajouté
     */
    public void fireGraphicObjectAdded(GraphicObject graphicObject) {
        if (drawListeners == null) {
            return;
        }

        DrawEvent evt = null;

        final int length = drawListeners.size();
        for (int i = 0; i < length; ++i) {
            final DrawListener drawListener = (DrawListener) drawListeners.get(i);
            if (evt == null) {
                evt = new DrawEvent(this, graphicObject);
            }
            drawListener.graphicObjectAdded(evt);
        }
    }


    public void paint(Graphics g) {
        if (getLayers() == null) {
            return;
        }

        int index = 0;
        for (final Iterator i = getLayers().iterator(); i.hasNext(); ++index) {
            final Layer layer = (Layer) i.next();
            if ((layer == null)) {
                continue;
            }
            layer.paint(g);
        }
    }
}
