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
 * $Id: GraphicObject.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import java.awt.Rectangle;

import java.io.Serializable;


/**
 * Interface définissant un objet graphique.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public interface GraphicObject extends Paintable, Serializable {
    //~ Méthodes ---------------------------------------------------------------

    /**
     * Retourne un <tt>Rectangle</tt> contenant la forme graphique.
     *
     * @return un <tt>Rectangle</tt> contenant la forme graphique
     */
    Rectangle getBounds();


    void setGraphicProperties(GraphicProperties graphicProperties);


    /**
     * Propriétés graphiques de la forme à appliquer lors d'un affichage écran.
     *
     * @return propriétés graphiques
     */
    GraphicProperties getGraphicProperties();


    void setHeight(int height);


    /**
     * Hauteur.
     *
     * @return hauteur
     */
    int getHeight();


    void setId(Long id);


    /**
     * Identifiant de la forme. Cette valeur sert à identifier de manière
     * unique la forme. La valeur de cette identifiant doit être initialisée
     * explicitement avec <tt>setId()</tt>.
     *
     * @return identifiant de la forme, ou <tt>null</tt> si non initialisé
     */
    Long getId();


    void setWidth(int width);


    /**
     * Largeur.
     *
     * @return largeur
     */
    int getWidth();


    void setX(int x);


    /**
     * Coordonnée sur X.
     *
     * @return coordonnée X
     */
    int getX();


    void setY(int y);


    /**
     * Coordonnée sur Y.
     *
     * @return coordonnée Y
     */
    int getY();


    /**
     * Déplacer la forme selon un vecteur de translation.
     *
     * @param offsetX décalage sur X
     * @param offsetY décalage sur Y
     */
    void offset(int offsetX, int offsetY);
}
