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
 * $Id: MainFrame.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.Application;
import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.GraphicProperties;
import org.eu.cnampaint.framework.ToolType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Enumeration;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;


/**
 * Fenêtre principale de l'application.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class MainFrame extends JFrame {
    //~ Champs d'instance ------------------------------------------------------

    private final ActionManager actionManager;
    private final Application   application;
    private final DrawArea      drawArea      = new DrawArea();
    private final Log           log           = LogFactory.getLog(getClass());
    private final MessageHelper messageHelper = new MessageHelper();

    //~ Constructeurs ----------------------------------------------------------

    public MainFrame(final Application application,
        final ActionManager actionManager) {
        this.application       = application;
        this.actionManager     = actionManager;
    }

    //~ Méthodes ---------------------------------------------------------------

    public void init() {
        addWindowListener(new MyWindowListener());
        application.getModel().addPropertyChangeListener(new MyPropertyChangeListener());

        drawArea.setMinimumSize(new Dimension(320, 200));

        setTitle(application.getMessageSource().getMessage("app.titre"));
        setIconImage(new ImageIcon(getClass().getResource(application.getMessageSource()
                                                                     .getMessage("app.icon"))).getImage());

        drawArea.setDraw(application.getModel().getDraw());

        buildUI();
    }


    private static void addElementsToMenu(Enumeration elements, JMenu menu) {
        while (elements.hasMoreElements()) {
            final JMenuItem menuItem = (JMenuItem) elements.nextElement();
            menu.add(menuItem);
        }
    }


    private void buildUI() {
        drawArea.setBorder(BorderFactory.createEtchedBorder());

        final JPanel drawAreaPanel = new JPanel(new FlowLayout(
                    FlowLayout.LEFT, 0, 0));
        drawAreaPanel.setMaximumSize(drawArea.getDraw().getSize());
        drawAreaPanel.add(drawArea);

        final JScrollPane drawAreaScrollPane = new JScrollPane(drawAreaPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        final JPanel      panel = new JPanel(new BorderLayout());
        panel.add(drawAreaScrollPane, BorderLayout.CENTER);
        panel.add(createToolBar(), BorderLayout.NORTH);
        setContentPane(panel);

        setJMenuBar(createMenuBar());
    }


    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        JMenu          menu = new JMenu(actionManager.getAction("menu.fichier"));
        menu.add(actionManager.getAction("nouveauDessin"));
        menu.add(actionManager.getAction("ouvrirDessin"));
        menu.add(actionManager.getAction("fermerDessin"));
        menu.addSeparator();
        menu.add(actionManager.getAction("enregistrerDessin"));
        menu.add(actionManager.getAction("enregistrerSousDessin"));
        menu.addSeparator();
        menu.add(actionManager.getAction("exporterDessin"));
        menu.addSeparator();
        menu.add(actionManager.getAction("quitter"));
        menuBar.add(menu);

        final ButtonGroup toolButtonGroup = new ButtonGroup();
        toolButtonGroup.add(new JRadioButtonMenuItem(actionManager.getAction(
                    "outilLigne")));
        final JMenuItem rectangleMenuItem = new JRadioButtonMenuItem(actionManager.getAction(
                    "outilRectangle"));
        rectangleMenuItem.setSelected(true);
        toolButtonGroup.add(rectangleMenuItem);
        toolButtonGroup.add(new JRadioButtonMenuItem(actionManager.getAction(
                    "outilCarre")));
        toolButtonGroup.add(new JRadioButtonMenuItem(actionManager.getAction(
                    "outilEllipse")));
        toolButtonGroup.add(new JRadioButtonMenuItem(actionManager.getAction(
                    "outilCercle")));
        toolButtonGroup.add(new JRadioButtonMenuItem(actionManager.getAction(
                    "outilSelection")));

        menu = new JMenu(actionManager.getAction("menu.forme"));
        addElementsToMenu(toolButtonGroup.getElements(), menu);
        menu.insertSeparator(5);
        menuBar.add(menu);

        menu = new JMenu(actionManager.getAction("menu.attribut"));
        menu.add(actionManager.getAction("epaisseurContour"));
        menu.add(actionManager.getAction("couleurContour"));
        menu.add(actionManager.getAction("couleurFond"));
        menu.add(actionManager.getAction("transparence"));
        menu.addSeparator();
        menu.add(new JCheckBoxMenuItem(actionManager.getAction("formePleine")));
        menuBar.add(menu);

        menu = new JMenu(actionManager.getAction("menu.aide"));
        menu.add(actionManager.getAction("aPropos"));
        menuBar.add(menu);

        return menuBar;
    }


    private JToolBar createToolBar() {
        final JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(actionManager.getAction("nouveauDessin"));
        toolBar.add(actionManager.getAction("ouvrirDessin"));
        toolBar.add(actionManager.getAction("enregistrerDessin"));

        return toolBar;
    }

    //~ Classes ----------------------------------------------------------------

    private class MyPropertyChangeListener implements PropertyChangeListener {
        //~ Méthodes -----------------------------------------------------------

        public void propertyChange(PropertyChangeEvent evt) {
            final String name = evt.getPropertyName();
            if ("draw".equals(name)) {
                final Draw draw = (Draw) evt.getNewValue();
                drawArea.setDraw(draw);
                drawArea.repaint();
            } else if ("toolType".equals(name)) {
                final ToolType got = (ToolType) evt.getNewValue();
                drawArea.setToolType(got);
            } else if ("graphicProperties".equals(name)) {
                final GraphicProperties graphicProperties = (GraphicProperties) evt.getNewValue();
                drawArea.setDefaultGraphicProperties(graphicProperties);
            }
        }
    }


    private class MyWindowListener extends WindowAdapter {
        //~ Méthodes -----------------------------------------------------------

        public void windowClosing(WindowEvent evt) {
            final Action action = actionManager.getAction("quitter");
            action.actionPerformed(new ActionEvent(this,
                    ActionEvent.ACTION_LAST + 1,
                    (String) action.getValue(Action.ACTION_COMMAND_KEY)));
        }
    }
}
