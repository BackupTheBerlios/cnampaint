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
 * $Id: DrawArea.java,v 1.2 2005/01/04 09:21:32 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.GraphicObject;
import org.eu.cnampaint.framework.GraphicProperties;
import org.eu.cnampaint.framework.Layer;
import org.eu.cnampaint.framework.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;


/**
 * Zone de dessin.
 *
 * @author alex
 * @version $Revision: 1.2 $, $Date: 2005/01/04 09:21:32 $
 */
public class DrawArea extends JComponent {
    //~ Champs d'instance ------------------------------------------------------

    private final Log                    log                      = LogFactory.getLog(getClass());
    private Draw                         draw                     = new Draw();
    private GraphicObject                currentGraphicObject;
    private GraphicProperties            defaultGraphicProperties = new GraphicProperties();
    private final Map                    mouseBehaviors           = new HashMap(3);
    private final Map                    selectedGraphicObjects   = new HashMap(1);
    private MouseBehavior                lastMouseBehavior;
    private final Random                 random                   = new Random();
    private final SelectionKeyListener   selectionKeyListener     = new SelectionKeyListener();
    private final SelectionMouseListener selectionMouseListener   = new SelectionMouseListener();
    private ToolType                     toolType;
    private boolean                      antialiasing             = true;
    private boolean                      creationMode;
    private boolean                      draggingSelection;

    //~ Constructeurs ----------------------------------------------------------

    public DrawArea() {
        super();

        setOpaque(true);

        // le fond de la zone de dessin est blanc
        // TODO implémenter la transparence
        setBackground(Color.WHITE);

        // configuration des comportements de la souris en fonction
        // du type d'objet graphique dessiné
        mouseBehaviors.put(ToolType.LINE, new LineMouseBehavior(this));
        mouseBehaviors.put(ToolType.CIRCLE, new CircleMouseBehavior(this));
        mouseBehaviors.put(ToolType.SQUARE, new SquareMouseBehavior(this));

        // comportement par défaut
        mouseBehaviors.put(null, new RectangularMouseBehavior(this));

        setToolType(ToolType.RECTANGLE);
    }

    //~ Méthodes ---------------------------------------------------------------

    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
    }


    public boolean isAntialiasing() {
        return antialiasing;
    }


    public void setCreationMode(boolean creationMode) {
        this.creationMode = creationMode;

        if (log.isDebugEnabled()) {
            if (creationMode) {
                log.debug("Mode création activé");
            } else {
                log.debug("Mode création désactivé");
            }
        }

        if (!creationMode) {
            setCurrentGraphicObject(null);
        }
    }


    public boolean isCreationMode() {
        return creationMode;
    }


    public void setCurrentGraphicObject(GraphicObject currentGraphicObject) {
        this.currentGraphicObject = currentGraphicObject;
    }


    public GraphicObject getCurrentGraphicObject() {
        return currentGraphicObject;
    }


    public void setDefaultGraphicProperties(
        GraphicProperties defaultGraphicProperties) {
        this.defaultGraphicProperties = defaultGraphicProperties;
        log.info(defaultGraphicProperties);
    }


    public GraphicProperties getDefaultGraphicProperties() {
        return defaultGraphicProperties;
    }


    public void setDraw(Draw draw) {
        this.draw = draw;
        selectedGraphicObjects.clear();

        // la taille du nouveau dessin est peut-être différente :
        // il faut s'y adapter 
        revalidate();
    }


    public Draw getDraw() {
        return draw;
    }


    public Dimension getPreferredSize() {
        return (draw != null)
        ? new Dimension(draw.getWidth(), draw.getHeight())
        : super.getPreferredSize();
    }


    public void setToolType(ToolType graphicObjectType) {
        this.toolType = graphicObjectType;

        removeMouseListener(lastMouseBehavior);
        removeMouseMotionListener(lastMouseBehavior);

        if (graphicObjectType == ToolType.NONE) {
            if (log.isDebugEnabled()) {
                log.debug("Mode sélection activé");
            }
            lastMouseBehavior = null;
            addMouseListener(selectionMouseListener);
            addMouseMotionListener(selectionMouseListener);
            addKeyListener(selectionKeyListener);
            requestFocusInWindow();
        } else {
            MouseBehavior mouseBehavior = (MouseBehavior) mouseBehaviors.get(graphicObjectType);
            if (mouseBehavior == null) {
                if (log.isDebugEnabled()) {
                    log.debug(
                        "Aucun comportement de souris configuré pour le type d'outil " +
                        graphicObjectType +
                        ": utilisation du comportement par défaut");
                }

                mouseBehavior = (MouseBehavior) mouseBehaviors.get(null);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Installation du comportement de souris " +
                        mouseBehavior.getClass().getName() +
                        " pour le type d'outil: " + graphicObjectType);
                }
            }

            // désactivation du mode sélection
            removeMouseListener(selectionMouseListener);
            removeMouseMotionListener(selectionMouseListener);
            removeKeyListener(selectionKeyListener);
            selectedGraphicObjects.clear();

            lastMouseBehavior = mouseBehavior;
            addMouseListener(mouseBehavior);
            addMouseMotionListener(mouseBehavior);
        }

        repaint();
    }


    public ToolType getToolType() {
        return toolType;
    }


    public void addGraphicObject(GraphicObject graphicObject) {
        final Layer layer = (Layer) draw.getLayers().get(draw.getActiveLayerIndex());
        if (log.isDebugEnabled()) {
            log.debug("Ajout de l'objet graphique dans la couche nommée " +
                layer.getName() + ": " + graphicObject);
        }

        // affectation d'un id unique à l'objet graphique
        graphicObject.setId(new Long(random.nextLong()));

        layer.getGraphicObjects().add(graphicObject);
        draw.fireGraphicObjectAdded(graphicObject);
    }


    protected void paintComponent(Graphics g) {
        // http://java.sun.com/docs/books/tutorial/uiswing/14painting/practice.html
        // comme indiqué dans le tutorial Java, on dessine le fond si le
        // composant est opaque
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (getDraw() == null) {
            if (log.isDebugEnabled()) {
                log.debug("getDraw() == null");
            }

            return;
        }

        final Graphics2D g2 = (Graphics2D) g.create();
        if (antialiasing) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        }
        getDraw().paint(g2);

        if ((getCurrentGraphicObject() != null) && isCreationMode()) {
            if (log.isDebugEnabled()) {
                log.debug("Affichage de la forme en cours de création");
            }
            getCurrentGraphicObject().paint(g2);
        }

        if (ToolType.NONE.equals(getToolType())) {
            // mode sélection
            // dessin d'un cadre autour de chaque objet graphique sélectionné
            // configuration du contexte graphique pour le dessin des objets sélectionnés
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f }, 0));

            for (final Iterator i = selectedGraphicObjects.values().iterator();
                    i.hasNext();) {
                final GraphicObject go     = (GraphicObject) i.next();
                final Rectangle     bounds = go.getBounds();
                g2.draw(new Rectangle(bounds.x - 1, bounds.y - 1,
                        bounds.width + 2, bounds.height + 2));
            }
        }

        g2.dispose();
    }


    private void deleteSelectedGraphicObjects() {
        final Layer layer = (Layer) getDraw().getLayers().get(getDraw()
                                                                  .getActiveLayerIndex());
        for (final Iterator i = selectedGraphicObjects.values().iterator();
                i.hasNext();) {
            final GraphicObject go = (GraphicObject) i.next();
            layer.getGraphicObjects().remove(go);
            i.remove();
        }
        repaint();
    }

    //~ Classes ----------------------------------------------------------------

    private class SelectionKeyListener extends KeyAdapter {
        //~ Méthodes -----------------------------------------------------------

        public void keyReleased(KeyEvent evt) {
            switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                deleteSelectedGraphicObjects();

                break;

            default:
                return;
            }
        }
    }


    private class SelectionMouseListener implements MouseListener,
        MouseMotionListener {
        //~ Initialisateurs et champs de classe --------------------------------

        private static final int NOT_INITIALIZED = -1;

        //~ Champs d'instance --------------------------------------------------

        private int lastX = NOT_INITIALIZED;
        private int lastY = NOT_INITIALIZED;

        //~ Méthodes -----------------------------------------------------------

        public void mouseClicked(MouseEvent evt) {
        }


        public void mouseDragged(MouseEvent evt) {
            if (!SwingUtilities.isLeftMouseButton(evt)) {
                return;
            }

            if (lastX == NOT_INITIALIZED) {
                lastX = evt.getX();
            }
            if (lastY == NOT_INITIALIZED) {
                lastY = evt.getY();
            }

            // vecteur de translation
            final int offsetX = evt.getX() - lastX;
            final int offsetY = evt.getY() - lastY;

            for (final Iterator i = selectedGraphicObjects.values().iterator();
                    i.hasNext();) {
                // déplacement de l'objet graphique selon le vecteur de translation
                final GraphicObject go = (GraphicObject) i.next();
                go.offset(offsetX, offsetY);
            }

            repaint();

            // sauvegarde des coordonnées actuelles pour le prochain déplacement
            lastX     = evt.getX();
            lastY     = evt.getY();

            draggingSelection = true;
        }


        public void mouseEntered(MouseEvent evt) {
        }


        public void mouseExited(MouseEvent evt) {
        }


        public void mouseMoved(MouseEvent evt) {
        }


        public void mousePressed(MouseEvent evt) {
            if (!SwingUtilities.isLeftMouseButton(evt)) {
                return;
            }

            lastX     = NOT_INITIALIZED;
            lastY     = NOT_INITIALIZED;
        }


        public void mouseReleased(MouseEvent evt) {
            if (!SwingUtilities.isLeftMouseButton(evt)) {
                return;
            }

            if (draggingSelection || !ToolType.NONE.equals(getToolType())) {
                draggingSelection = false;

                return;
            }

            final GraphicObject go = getDraw().getGraphicObjectAt(evt.getX(),
                    evt.getY());
            if (go == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Aucun objet graphique contenu dans la sélection");
                }

                return;
            }
            if (selectedGraphicObjects.get(go.getId()) != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Objet graphique supprimé de la sélection: " +
                        go);
                }
                selectedGraphicObjects.remove(go.getId());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Objet graphique ajouté à la sélection: " + go);
                }
                selectedGraphicObjects.put(go.getId(), go);
            }

            repaint();
        }
    }
}
