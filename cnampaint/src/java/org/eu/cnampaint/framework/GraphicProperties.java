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
 * $Id: GraphicProperties.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import org.apache.commons.beanutils.BeanUtils;

import java.awt.Color;

import java.io.Serializable;


/**
 * Propriétés graphiques d'une forme.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class GraphicProperties extends BaseObject implements Serializable,
    Cloneable {
    //~ Champs d'instance ------------------------------------------------------

    private Color   backgroundColor = Color.ORANGE;
    private Color   borderColor  = Color.BLACK;
    private boolean filling      = false;
    private float   transparency = 0.5f;
    private int     borderWidth  = 1;

    //~ Méthodes ---------------------------------------------------------------

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    /**
     * Couleur de fond, utilisée si <tt>isFilling()</tt> renvoie <tt>true</tt>.
     *
     * @return couleur de fond
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }


    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }


    /**
     * Couleur du contour.
     *
     * @return couleur du contour
     */
    public Color getBorderColor() {
        return borderColor;
    }


    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }


    /**
     * Largeur du contour.
     *
     * @return largeur du contour
     */
    public int getBorderWidth() {
        return borderWidth;
    }


    public void setFilling(boolean filling) {
        this.filling = filling;
    }


    /**
     * Retourne <tt>true</tt> si la forme doit être remplie.
     *
     * @return <tt>true</tt> si la forme doit être remplie
     */
    public boolean isFilling() {
        return filling;
    }


    public void setTransparency(float transparency) {
        if ((transparency < 0) || (transparency > 1)) {
            throw new IllegalArgumentException(
                "transparency < 0 || transparency > 1");
        }
        this.transparency = transparency;
    }


    /**
     * Transparence de la forme, dont la valeur est comprise entre 0 et 1.
     *
     * @return transparence
     */
    public float getTransparency() {
        return transparency;
    }


    public Object clone() {
        try {
            return BeanUtils.cloneBean(this);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du clonage", e);
        }
    }
}
