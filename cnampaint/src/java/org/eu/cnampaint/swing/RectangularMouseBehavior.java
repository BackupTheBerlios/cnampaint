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
 * $Id: RectangularMouseBehavior.java,v 1.2 2005/01/25 15:57:15 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.framework.GraphicObject;
import org.eu.cnampaint.framework.GraphicProperties;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;


/**
 * Comportement de souris pour un rectangle.
 *
 * @author alex
 * @version $Revision: 1.2 $, $Date: 2005/01/25 15:57:15 $
 */
public class RectangularMouseBehavior implements MouseBehavior {
    //~ Champs d'instance ------------------------------------------------------

    protected final DrawArea drawArea;
    private final Log        log    = LogFactory.getLog(getClass());
    private int              startX;
    private int              startY;

    //~ Constructeurs ----------------------------------------------------------

    public RectangularMouseBehavior(final DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    //~ Méthodes ---------------------------------------------------------------

    public void mouseClicked(MouseEvent e) {
    }


    public void mouseDragged(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e) || !drawArea.isCreationMode()) {
            return;
        }
        updateDraw(startX, startY, e.getX(), e.getY());
    }


    public void mouseEntered(MouseEvent e) {
    }


    public void mouseExited(MouseEvent e) {
    }


    public void mouseMoved(MouseEvent e) {
    }


    public void mousePressed(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e) ||
                (drawArea.getToolType() == null)) {
            return;
        }
        startX     = e.getX();
        startY     = e.getY();
        startDraw(e.getX(), e.getY());
    }


    public void mouseReleased(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e) || !drawArea.isCreationMode()) {
            return;
        }
        endDraw(e.getX(), e.getY());
    }


    protected void endDraw(int x, int y) {
        drawArea.addGraphicObject(drawArea.getCurrentGraphicObject());
        drawArea.setCreationMode(false);
        drawArea.repaint();
    }


    protected void startDraw(int x, int y) {
        GraphicObject graphicObject = null;
        try {
            graphicObject = (GraphicObject) drawArea.getToolType().getType()
                                                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'instanciation", e);
        }

        graphicObject.setX(x);
        graphicObject.setY(y);

        // clonage des propriétés graphiques
        final GraphicProperties graphicProperties = (GraphicProperties) drawArea.getDefaultGraphicProperties()
                                                                                .clone();
        graphicObject.setGraphicProperties(graphicProperties);

        drawArea.setCurrentGraphicObject(graphicObject);
        drawArea.setCreationMode(true);
    }


    protected void updateDraw(int startX, int startY, int x, int y) {
        int x1 = startX;
        int y1 = startY;
        int x2 = x;
        int y2 = y;

        // on traite le cas où l'utilisateur dessine "à l'envers" :
        // le point actuel est situé avant sur X ou Y par rapport au point de départ
        if (x2 < x1) {
            final int t = x2;
            x2     = x1;
            x1     = t;
        }
        if (y2 < y1) {
            final int t = y2;
            y2     = y1;
            y1     = t;
        }

        final GraphicObject graphicObject = drawArea.getCurrentGraphicObject();
        graphicObject.setX(x1);
        graphicObject.setY(y1);
        graphicObject.setWidth(x2 - x1);
        graphicObject.setHeight(y2 - y1);

        drawArea.repaint();
    }
}
